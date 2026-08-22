package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class LeapingFruitPlantSpreadingRootBlock extends LeapingFruitPlantBlock {

    public LeapingFruitPlantSpreadingRootBlock(Properties properties, float dropChance, boolean useFortune) {
        super(properties, dropChance, useFortune);
    }

    @Override
    public EnumSet<Direction> getConnectingDirections(BlockState state) {
        return EnumSet.allOf(Direction.class);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // 保持原有朝向逻辑
        Direction clickedFace = context.getClickedFace();
        boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        Direction facing;
        if (clickedFace == Direction.UP) {
            facing = sneaking ? Direction.UP : Direction.DOWN;
        } else if (clickedFace == Direction.DOWN) {
            facing = sneaking ? Direction.DOWN : Direction.UP;
        } else {
            facing = context.getHorizontalDirection();
            if (sneaking) facing = facing.getOpposite();
        }
        return this.defaultBlockState().setValue(FACING, facing);
    }

    // ========== 分支扩展（每个方向尝试生长） ==========
    private void spread(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        Direction parentFacing = state.getValue(FACING);
        // 遍历所有方向（除父方向）
        for (Direction dir : Direction.values()) {
            if (dir == parentFacing) continue;
            BlockPos targetPos = pos.relative(dir);
            BlockState targetState = level.getBlockState(targetPos);

            if (targetState.is(Blocks.END_STONE)) {
                // 生成新 ROOT，随机剩余生长次数 2~25
                int remaining = MIN_SEGMENT_LENGTH + random.nextInt(MAX_SEGMENT_LENGTH - MIN_SEGMENT_LENGTH + 1);
                BlockState newRoot = ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get().defaultBlockState()
                        .setValue(FACING, dir.getOpposite()) // 新ROOT的FACING指向父方向（即朝向该方向）
                        .setValue(IS_NATURAL, true)
                        .setValue(REMAINING_GROWTH, remaining)
                        .setValue(MAXED_OUT, remaining <= 0); // 不会 <=0
                level.setBlock(targetPos, newRoot, 3);
                level.scheduleTick(targetPos, ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get(), 5);
            } else if (targetState.isAir()) {
                // 空气 → 在该方向生成一个 TOP（终止端）
                BlockState top = ModBlocks.LEAPING_FRUIT_PLANT_TOP.get().defaultBlockState()
                        .setValue(FACING, dir.getOpposite()) // TOP的FACING反向指向父方向（即朝向该方向的反向？ 实际上TOP的FACING应指向其父方向，即dir的反向？）
                        // 为了使TOP的底面指向父节点，我们设置FACING为dir（让生长方向反向？）需要分析：
                        // TOP的FACING表示其顶面朝向，连接面是FACING的反向。我们想让TOP的底面（连接面）面向父节点，即连接面应朝向 pos 方向，
                        // 所以 FACING 应设为 dir 的反向（即指向父节点方向）。但 dir 是从 pos 到 targetPos 的方向，所以父节点在 pos，目标在 targetPos，
                        // 我们需要TOP的连接面朝向 pos，即 FACING.getOpposite() = pos 方向，所以 FACING = dir。
                        // 但为了统一，我们设置 FACING = dir。
                        .setValue(FACING, dir)
                        .setValue(IS_NATURAL, true)
                        .setValue(REMAINING_GROWTH, 0)
                        .setValue(MAXED_OUT, false)
                        .setValue(LeapingFruitPlantTopBlock.CAN_GROW, false);
                level.setBlock(targetPos, top, 3);
            }
            // 其他方块忽略
        }
    }

    // ========== 随机刻 ==========
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(IS_NATURAL)) return;
        spread(level, pos, state, random);
    }

    // ========== 计划刻 ==========
    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(IS_NATURAL)) return;
        spread(level, pos, state, random);
        level.scheduleTick(pos, this, 20 + random.nextInt(80));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide && state.getValue(IS_NATURAL)) {
            level.scheduleTick(pos, this, 1);
        }
    }
}