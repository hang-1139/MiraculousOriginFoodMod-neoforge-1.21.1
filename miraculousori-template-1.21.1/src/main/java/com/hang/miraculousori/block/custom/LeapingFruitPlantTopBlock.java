package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.advancement.LeapingFruitActivateTrigger;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class LeapingFruitPlantTopBlock extends LeapingFruitPlantBlock {
    public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");

    public LeapingFruitPlantTopBlock(Properties properties, float dropChance, boolean useFortune) {
        super(properties, dropChance, useFortune);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(CAN_GROW, false)
                .setValue(REMAINING_GROWTH, 0)
                .setValue(MAXED_OUT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CAN_GROW);
    }

    @Override
    public EnumSet<Direction> getConnectingDirections(BlockState state) {
        return EnumSet.of(state.getValue(FACING).getOpposite());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
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

    // ========== 激活交互 ==========
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;

        if (state.getValue(CAN_GROW)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        Direction facing = state.getValue(FACING);
        BlockPos belowPos = pos.relative(facing.getOpposite());
        if (!level.getBlockState(belowPos).is(Blocks.END_STONE)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!stack.is(ModItems.LEAPING_FRUIT.get())) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int totalFruit = 0;
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        if (main.is(ModItems.LEAPING_FRUIT.get())) totalFruit += main.getCount();
        if (off.is(ModItems.LEAPING_FRUIT.get())) totalFruit += off.getCount();

        if (totalFruit < 4 && !player.isCreative()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!player.isCreative()) {
            int toRemove = 4;
            if (main.is(ModItems.LEAPING_FRUIT.get())) {
                int take = Math.min(toRemove, main.getCount());
                main.shrink(take);
                toRemove -= take;
            }
            if (toRemove > 0 && off.is(ModItems.LEAPING_FRUIT.get())) {
                off.shrink(toRemove);
            }
        }

        // 生成第一个 ROOT，随机剩余生长次数
        int remaining = MIN_SEGMENT_LENGTH + level.random.nextInt(MAX_SEGMENT_LENGTH - MIN_SEGMENT_LENGTH + 1);
        BlockState newRoot = ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get().defaultBlockState()
                .setValue(FACING, facing)
                .setValue(IS_NATURAL, true)
                .setValue(REMAINING_GROWTH, remaining)
                .setValue(MAXED_OUT, false);
        level.setBlock(belowPos, newRoot, 3);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(belowPos, ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get(), 5);
        }

        level.setBlock(pos, state.setValue(CAN_GROW, true), 3);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(pos, this, 1);
            // 生成激活粒子效果
            spawnActivationParticles(serverLevel, pos);
        }

        if (player instanceof ServerPlayer serverPlayer) {
            LeapingFruitActivateTrigger.trigger(serverPlayer);
        }
        return ItemInteractionResult.SUCCESS;
    }

    // ========== 粒子效果（环绕方块） ==========
    private void spawnActivationParticles(ServerLevel level, BlockPos pos) {
        // 金黄色 Dust 粒子
        Vector3f color = new Vector3f(1.0f, 0.8f, 0.0f); // 金黄
        float scale = 0.5f;
        DustParticleOptions options = new DustParticleOptions(color, scale);
        RandomSource random = level.random;
        // 围绕方块四周生成粒子，持续3秒（60 tick）由随机刻控制，这里仅生成一批
        for (int i = 0; i < 30; i++) {
            double xOffset = (random.nextDouble() - 0.5) * 1.2;
            double yOffset = (random.nextDouble() - 0.5) * 1.2 + 0.5;
            double zOffset = (random.nextDouble() - 0.5) * 1.2;
            level.sendParticles(options,
                    pos.getX() + 0.5 + xOffset,
                    pos.getY() + 0.5 + yOffset,
                    pos.getZ() + 0.5 + zOffset,
                    1, 0, 0, 0, 0);
        }
    }

    // ========== 每刻段长巡检（硬上限改为25） ==========
    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(CAN_GROW) || !state.getValue(IS_NATURAL)) return;
        scanAndTrimSegments(level, pos);
        level.scheduleTick(pos, this, 1);
    }

    private void scanAndTrimSegments(ServerLevel level, BlockPos topPos) {
        Set<BlockPos> visited = new HashSet<>();
        java.util.Queue<BlockPos> queue = new java.util.ArrayDeque<>();
        queue.add(topPos);
        visited.add(topPos);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            BlockState currentState = level.getBlockState(current);
            if (!(currentState.getBlock() instanceof LeapingFruitPlantBlock)) continue;

            if (currentState.getBlock() instanceof LeapingFruitPlantTopBlock ||
                    currentState.getBlock() instanceof LeapingFruitPlantSpreadingRootBlock) {
                Direction facing = currentState.getValue(FACING);
                int length = 0;
                BlockPos walk = current;
                while (true) {
                    BlockPos next = walk.relative(facing.getOpposite());
                    BlockState nextState = level.getBlockState(next);
                    if (nextState.getBlock() instanceof LeapingFruitPlantRootBlock && nextState.getValue(IS_NATURAL)) {
                        length++;
                        walk = next;
                        if (length > MAX_SEGMENT_LENGTH) {
                            // 超长，将最远端的 ROOT 转为 SPREADING_ROOT（并设置 MAXED_OUT=false）
                            BlockState spreading = ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get().defaultBlockState()
                                    .setValue(FACING, nextState.getValue(FACING))
                                    .setValue(IS_NATURAL, true)
                                    .setValue(REMAINING_GROWTH, 0)
                                    .setValue(MAXED_OUT, false);
                            level.setBlock(walk, spreading, 3);
                            level.scheduleTick(walk, ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), 1);
                            break;
                        }
                    } else {
                        break;
                    }
                }
                for (Direction dir : Direction.values()) {
                    BlockPos neighbor = current.relative(dir);
                    if (visited.contains(neighbor)) continue;
                    BlockState neighborState = level.getBlockState(neighbor);
                    LeapingFruitPlantBlock block = (LeapingFruitPlantBlock) currentState.getBlock();
                    if (block.isValidNeighbor(neighborState, level, neighbor, dir)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    // ========== 头部重选 ==========
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && state.getValue(IS_NATURAL)) {
            ServerLevel serverLevel = (ServerLevel) level;
            BlockPos newTop = findNearestTop(serverLevel, pos);
            if (newTop != null && !newTop.equals(pos)) {
                BlockState newTopState = level.getBlockState(newTop);
                if (newTopState.getBlock() instanceof LeapingFruitPlantTopBlock) {
                    level.setBlock(newTop, newTopState.setValue(CAN_GROW, true), 3);
                    serverLevel.scheduleTick(newTop, this, 1);
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide && state.getValue(CAN_GROW) && state.getValue(IS_NATURAL)) {
            level.scheduleTick(pos, this, 1);
        }
    }
}