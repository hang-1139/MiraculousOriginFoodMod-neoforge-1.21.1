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

public class LeapingFruitPlantRootBlock extends LeapingFruitPlantBlock {

    public LeapingFruitPlantRootBlock(Properties properties, float dropChance, boolean useFortune) {
        super(properties, dropChance, useFortune);
    }

    @Override
    public EnumSet<Direction> getConnectingDirections(BlockState state) {
        Direction facing = state.getValue(FACING);
        return EnumSet.of(facing, facing.getOpposite());
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

    // ========== 生长核心逻辑 ==========
    private void attemptGrow(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(MAXED_OUT)) return; // 已满，不再生长

        Direction growDir = state.getValue(FACING).getOpposite();
        BlockPos targetPos = pos.relative(growDir);
        BlockState targetState = level.getBlockState(targetPos);

        // 只有末地石可以生长
        if (targetState.is(Blocks.END_STONE)) {
            int remaining = state.getValue(REMAINING_GROWTH);
            if (remaining <= 0) {
                // 理论上不会发生，因为 MAXED_OUT 会阻止
                return;
            }
            int newRemaining = remaining - 1;
            BlockState newRoot = ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get().defaultBlockState()
                    .setValue(FACING, state.getValue(FACING))
                    .setValue(IS_NATURAL, true)
                    .setValue(REMAINING_GROWTH, newRemaining)
                    .setValue(MAXED_OUT, newRemaining <= 0); // 如果新剩余为0，标记为已满
            level.setBlock(targetPos, newRoot, 3);
            // 新ROOT调度一个计划刻（用于后续转变）
            level.scheduleTick(targetPos, this, 10);
            return;
        }

        // 遇到空气 → 自身转变为 TOP（立即）
        if (targetState.isAir()) {
            turnToTop(level, pos, state);
            return;
        }

        // 其他方块（非末地石、非空气）→ 无反应（不生长也不转变）
    }

    // ========== 随机刻 ==========
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(IS_NATURAL)) return;
        if (state.getValue(MAXED_OUT)) return;
        attemptGrow(level, pos, state, random);
    }

    // ========== 计划刻 ==========
    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(IS_NATURAL)) return;

        int remaining = state.getValue(REMAINING_GROWTH);
        boolean maxedOut = state.getValue(MAXED_OUT);

        // 如果已满，执行节点转变（70% SPREADING，30% TOP）
        if (maxedOut) {
            // 检查是否为末端（下方没有子ROOT）
            Direction growDir = state.getValue(FACING).getOpposite();
            BlockPos below = pos.relative(growDir);
            boolean hasChild = level.getBlockState(below).getBlock() instanceof LeapingFruitPlantRootBlock &&
                    level.getBlockState(below).getValue(IS_NATURAL);
            if (!hasChild) {
                // 转变判定
                if (random.nextDouble() < SPREADING_CHANCE) {
                    BlockState spreading = ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get().defaultBlockState()
                            .setValue(FACING, state.getValue(FACING))
                            .setValue(IS_NATURAL, true)
                            .setValue(REMAINING_GROWTH, 0)
                            .setValue(MAXED_OUT, false);
                    level.setBlock(pos, spreading, 3);
                    level.scheduleTick(pos, ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), 1);
                } else {
                    turnToTop(level, pos, state);
                }
            }
            return;
        }

        // 剩余生长次数 > 0：尝试生长一节（与随机刻类似，但保证即使随机刻未触发也能生长）
        // 但为了符合“每个生长刻度才完成一次生长”，我们让计划刻也调用 attemptGrow，但随机刻已经会调用，这里不再重复，
        // 改为定期调度自身，以便后续处理转变。
        // 如果剩余 > 0 但未满，尝试生长
        attemptGrow(level, pos, state, random);

        // 继续调度自身（延迟稍长，主要用于转变判定）
        level.scheduleTick(pos, this, 20 + random.nextInt(80));
    }

    // ========== 辅助 ==========
    private void turnToTop(ServerLevel level, BlockPos pos, BlockState state) {
        BlockState top = ModBlocks.LEAPING_FRUIT_PLANT_TOP.get().defaultBlockState()
                .setValue(FACING, state.getValue(FACING).getOpposite())
                .setValue(IS_NATURAL, true)
                .setValue(REMAINING_GROWTH, 0)
                .setValue(MAXED_OUT, false)
                .setValue(LeapingFruitPlantTopBlock.CAN_GROW, false);
        level.setBlock(pos, top, 3);
    }

    // 查找顶部节点（保留，但可能不再需要）
    private BlockPos findTop(ServerLevel level, BlockPos start) {
        BlockPos current = start;
        Direction facing = level.getBlockState(start).getValue(FACING);
        while (true) {
            BlockPos up = current.relative(facing);
            BlockState upState = level.getBlockState(up);
            if (!(upState.getBlock() instanceof LeapingFruitPlantBlock)) break;
            if (upState.getBlock() instanceof LeapingFruitPlantTopBlock ||
                    upState.getBlock() instanceof LeapingFruitPlantSpreadingRootBlock) {
                return up;
            }
            if (upState.getBlock() instanceof LeapingFruitPlantRootBlock && upState.getValue(IS_NATURAL)) {
                current = up;
                facing = upState.getValue(FACING);
            } else {
                break;
            }
        }
        return start;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide && state.getValue(IS_NATURAL)) {
            level.scheduleTick(pos, this, 10);
        }
    }
}