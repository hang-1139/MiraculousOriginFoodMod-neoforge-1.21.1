package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FloatingMelonCropBlock extends CropBlock {
    public static final MapCodec<FloatingMelonCropBlock> CODEC = simpleCodec(FloatingMelonCropBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    public static final BooleanProperty HAS_BLOCK = BooleanProperty.create("has_block");

    // 碰撞箱按 age 分组
    private static final VoxelShape SHAPE_0_1 = Block.box(0, 0, 0, 16, 2, 16);
    private static final VoxelShape SHAPE_2_4 = Block.box(0, 0, 0, 16, 6, 16);
    private static final VoxelShape SHAPE_5_7 = Block.box(0, 0, 0, 16, 12, 16);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            SHAPE_0_1,   // 0-1
            SHAPE_0_1,
            SHAPE_2_4,
            SHAPE_2_4,
            SHAPE_2_4,
            SHAPE_5_7,  // 5-6
            SHAPE_5_7,
            Block.box(0, 0, 0, 16, 16, 16)   // 7
    };

    public FloatingMelonCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(HAS_BLOCK, false));
    }

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HAS_BLOCK);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.FLOATING_MELON_SEEDS.get();
    }

    // ========== 种植条件：只允许末地石（且无视光照） ==========
    @Override
    protected boolean mayPlaceOn(BlockState groundState, BlockGetter level, BlockPos pos) {
        return groundState.is(Blocks.END_STONE);
    }

    // ========== 存活判定：重写以移除光照检查 ==========
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // 只检查下方是否为末地石，完全不检查光照
        return level.getBlockState(pos.below()).is(Blocks.END_STONE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int age = state.getValue(AGE);
        return SHAPE_BY_AGE[Math.min(age, SHAPE_BY_AGE.length - 1)];
    }

    protected static float getGrowthSpeed(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    // ========== 即时检测上方浮瓜块是否消失 ==========
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && state.getValue(AGE) >= 7 && state.getValue(HAS_BLOCK)) {
            if (!level.getBlockState(pos.above()).is(ModBlocks.FLOATING_MELON_BLOCK.get())) {
                return state.setValue(AGE, 2).setValue(HAS_BLOCK, false);
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    // ========== 生长逻辑 ==========
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        boolean hasBlock = state.getValue(HAS_BLOCK);

        if (age >= 7 && hasBlock) {
            BlockPos abovePos = pos.above();
            if (!level.getBlockState(abovePos).is(ModBlocks.FLOATING_MELON_BLOCK.get())) {
                level.setBlock(pos, state.setValue(AGE, 2).setValue(HAS_BLOCK, false), 2);
            }
            return;
        }

        if (age < getMaxAge()) {
            float f = getGrowthSpeed(state, level, pos);   // 恒为 1.0
            if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                int newAge = Math.min(age + 1, getMaxAge());
                BlockState newState = state.setValue(AGE, newAge);
                if (newAge >= 7) {
                    BlockPos abovePos = pos.above();
                    if (level.getBlockState(abovePos).isAir()) {
                        level.setBlock(abovePos, ModBlocks.FLOATING_MELON_BLOCK.get().defaultBlockState(), 3);
                        newState = newState.setValue(HAS_BLOCK, true);
                    } else {
                        newState = newState.setValue(HAS_BLOCK, false);
                    }
                }
                level.setBlock(pos, newState, 2);
            }
        } else if (age >= 7 && !hasBlock) {
            BlockPos abovePos = pos.above();
            if (level.getBlockState(abovePos).isAir()) {
                level.setBlock(abovePos, ModBlocks.FLOATING_MELON_BLOCK.get().defaultBlockState(), 3);
                level.setBlock(pos, state.setValue(HAS_BLOCK, true), 2);
            }
        }
    }

    // ========== 骨粉行为（概率增加2-5级） ==========
    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        boolean hasBlock = state.getValue(HAS_BLOCK);

        if (age >= 7 && hasBlock) {
            return;
        }

        int luckLevel = 0; // 可扩展
        double chance = 0.2 + 0.1 * luckLevel;
        if (random.nextDouble() < chance) {
            int increment = 2 + random.nextInt(4);
            int newAge = Math.min(age + increment, getMaxAge());
            BlockState newState = state.setValue(AGE, newAge);
            if (newAge >= 7) {
                BlockPos abovePos = pos.above();
                if (level.getBlockState(abovePos).isAir()) {
                    level.setBlock(abovePos, ModBlocks.FLOATING_MELON_BLOCK.get().defaultBlockState(), 3);
                    newState = newState.setValue(HAS_BLOCK, true);
                } else {
                    newState = newState.setValue(HAS_BLOCK, false);
                }
            }
            level.setBlock(pos, newState, 2);
        }
    }
}