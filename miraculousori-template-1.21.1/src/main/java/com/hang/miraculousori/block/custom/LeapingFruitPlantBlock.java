package com.hang.miraculousori.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public abstract class LeapingFruitPlantBlock extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty IS_NATURAL = BooleanProperty.create("is_natural");
    public static final IntegerProperty REMAINING_GROWTH = IntegerProperty.create("remaining_growth", 0, 25);
    public static final BooleanProperty MAXED_OUT = BooleanProperty.create("maxed_out");  // 新增：段已满

    // 全局参数
    public static final int MIN_SEGMENT_LENGTH = 2;
    public static final int MAX_SEGMENT_LENGTH = 25;
    public static final double SPREADING_CHANCE = 0.15;
    public static final double BONE_MEAL_CHANCE = 0.15;

    private final float dropChance;
    private final boolean useFortune;

    public LeapingFruitPlantBlock(Properties properties, float dropChance, boolean useFortune) {
        super(properties);
        this.dropChance = dropChance;
        this.useFortune = useFortune;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.UP)
                .setValue(IS_NATURAL, false)
                .setValue(REMAINING_GROWTH, 0)
                .setValue(MAXED_OUT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, IS_NATURAL, REMAINING_GROWTH, MAXED_OUT);
    }

    public abstract EnumSet<Direction> getConnectingDirections(BlockState state);

    public boolean isValidNeighbor(BlockState neighborState, LevelReader level, BlockPos neighborPos, Direction direction) {
        if (!(neighborState.getBlock() instanceof LeapingFruitPlantBlock neighbor)) return false;
        if (!neighborState.getValue(IS_NATURAL)) return false;
        return neighbor.canConnectTo(neighborState, direction.getOpposite());
    }

    public boolean canConnectTo(BlockState state, Direction direction) {
        return getConnectingDirections(state).contains(direction);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // 保持原有朝向逻辑不变（由子类重写）
        return this.defaultBlockState();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return state;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // 子类重写
    }

    // ----- 辅助方法 -----
    public int getSegmentLengthFromRoot(ServerLevel level, BlockPos rootPos) {
        BlockState state = level.getBlockState(rootPos);
        if (!(state.getBlock() instanceof LeapingFruitPlantRootBlock)) return 0;
        Direction facing = state.getValue(FACING);
        int length = 0;
        BlockPos current = rootPos;
        while (true) {
            BlockPos up = current.relative(facing);
            BlockState upState = level.getBlockState(up);
            if (!(upState.getBlock() instanceof LeapingFruitPlantBlock)) break;
            if (upState.getBlock() instanceof LeapingFruitPlantTopBlock ||
                    upState.getBlock() instanceof LeapingFruitPlantSpreadingRootBlock) {
                break;
            }
            if (upState.getBlock() instanceof LeapingFruitPlantRootBlock && upState.getValue(IS_NATURAL)) {
                length++;
                current = up;
                facing = upState.getValue(FACING);
            } else {
                break;
            }
        }
        return length;
    }

    public int countNodes(ServerLevel level, BlockPos startPos) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(startPos);
        visited.add(startPos);
        int count = 0;
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            BlockState state = level.getBlockState(pos);
            if (!(state.getBlock() instanceof LeapingFruitPlantBlock)) continue;
            if (state.getBlock() instanceof LeapingFruitPlantTopBlock ||
                    state.getBlock() instanceof LeapingFruitPlantSpreadingRootBlock) {
                count++;
            }
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = pos.relative(dir);
                if (visited.contains(neighbor)) continue;
                BlockState neighborState = level.getBlockState(neighbor);
                LeapingFruitPlantBlock block = (LeapingFruitPlantBlock) state.getBlock();
                if (block.isValidNeighbor(neighborState, level, neighbor, dir)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return count;
    }

    public BlockPos findNearestTop(ServerLevel level, BlockPos origin) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(origin);
        visited.add(origin);
        BlockPos nearest = null;
        int minDist = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof LeapingFruitPlantTopBlock && state.getValue(IS_NATURAL)) {
                int dist = (int) Math.sqrt(pos.distSqr(origin));
                if (dist < minDist) {
                    minDist = dist;
                    nearest = pos;
                }
            }
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = pos.relative(dir);
                if (visited.contains(neighbor)) continue;
                BlockState neighborState = level.getBlockState(neighbor);
                if (neighborState.getBlock() instanceof LeapingFruitPlantBlock &&
                        neighborState.getValue(IS_NATURAL)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return nearest;
    }

    // 移除全局极限检查（改用段长度）
    public boolean checkGlobalLimits(ServerLevel level, BlockPos startPos, Direction growDir, boolean isNode) {
        return true; // 不再限制总节点数
    }

    public float getDropChance() { return dropChance; }
    public boolean useFortune() { return useFortune; }
}