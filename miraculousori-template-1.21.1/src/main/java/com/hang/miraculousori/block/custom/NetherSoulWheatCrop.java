package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class NetherSoulWheatCrop extends CropBlock {
    public static final MapCodec<NetherSoulWheatCrop> CODEC = simpleCodec(NetherSoulWheatCrop::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final int FIRST_STAGE_AGE = 5;
    public static final int SECOND_STAGE_AGE = 2;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };

    public NetherSoulWheatCrop(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
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
        return ModItems.NETHER_SOUL_WHEAT_SEEDS;
    }

    @Override
    protected boolean mayPlaceOn(BlockState groundState, BlockGetter level, BlockPos pos) {
        return groundState.is(Blocks.SOUL_SAND) || groundState.is(Blocks.SOUL_SOIL);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this)
                    && below.getValue(HALF) == DoubleBlockHalf.LOWER
                    && below.getValue(AGE) >= FIRST_STAGE_AGE;
        } else {
            BlockState ground = level.getBlockState(pos.below());
            return this.mayPlaceOn(ground, level, pos.below());
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (facing.getAxis() == Direction.Axis.Y) {
            if (half == DoubleBlockHalf.LOWER && facing == Direction.UP) {
                int age = state.getValue(AGE);
                BlockState above = level.getBlockState(currentPos.above());
                if (age >= FIRST_STAGE_AGE) {
                    if (!above.is(this) || above.getValue(HALF) != DoubleBlockHalf.UPPER || above.getValue(AGE) != age) {
                        BlockState newAbove = this.defaultBlockState()
                                .setValue(AGE, age)
                                .setValue(HALF, DoubleBlockHalf.UPPER);
                        level.setBlock(currentPos.above(), newAbove, 3);
                    }
                } else {
                    if (above.is(this) && above.getValue(HALF) == DoubleBlockHalf.UPPER) {
                        level.setBlock(currentPos.above(), Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            } else if (half == DoubleBlockHalf.UPPER && facing == Direction.DOWN) {
                BlockState below = level.getBlockState(currentPos.below());
                if (!below.is(this) || below.getValue(HALF) != DoubleBlockHalf.LOWER || below.getValue(AGE) < FIRST_STAGE_AGE) {
                    return Blocks.AIR.defaultBlockState();
                }
                int belowAge = below.getValue(AGE);
                if (state.getValue(AGE) != belowAge) {
                    return state.setValue(AGE, belowAge);
                }
            }
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    // ==================== 生长逻辑（快速） ====================
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return;

        int age = state.getValue(AGE);
        if (age < this.getMaxAge()) {
            // 提高生长概率：每 tick 约 1/3 概率生长
            if (random.nextInt(3) == 0) {
                int newAge = Math.min(age + 1, this.getMaxAge());
                level.setBlock(pos, state.setValue(AGE, newAge), 2);
                if (newAge >= FIRST_STAGE_AGE) {
                    BlockPos abovePos = pos.above();
                    BlockState above = level.getBlockState(abovePos);
                    if (above.isAir()) {
                        BlockState newAbove = this.defaultBlockState()
                                .setValue(AGE, newAge)
                                .setValue(HALF, DoubleBlockHalf.UPPER);
                        level.setBlock(abovePos, newAbove, 3);
                    } else if (above.is(this) && above.getValue(HALF) == DoubleBlockHalf.UPPER) {
                        level.setBlock(abovePos, above.setValue(AGE, newAge), 3);
                    }
                }
            }
        }
    }

    // ==================== 核心：完全由代码控制掉落 ====================
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            DoubleBlockHalf half = state.getValue(HALF);
            if (half == DoubleBlockHalf.LOWER) {
                // ----- 底部被破坏 -----
                BlockPos abovePos = pos.above();
                BlockState aboveState = level.getBlockState(abovePos);
                int age = state.getValue(AGE);

                // 1. 如果顶部存在，处理顶部掉落
                if (aboveState.is(this) && aboveState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    if (age == 7) {
                        // 成熟：顶部掉落 1-3 个缠魂麦
                        int count = 1 + level.random.nextInt(3);
                        popResource(level, abovePos, new ItemStack(ModItems.SOUL_WHEAT.get(), count));
                    }
                    // 移除顶部
                    level.setBlock(abovePos, Blocks.AIR.defaultBlockState(), 35);
                }

                // 2. 底部掉落 1 个种子（无论年龄）
                popResource(level, pos, new ItemStack(ModItems.NETHER_SOUL_WHEAT_SEEDS.get(), 1));

            } else {
                // ----- 顶部被破坏 -----
                BlockPos belowPos = pos.below();
                BlockState belowState = level.getBlockState(belowPos);
                if (belowState.is(this) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                    int age = belowState.getValue(AGE);
                    if (age == 7) {
                        // 成熟：顶部掉落 1-3 个缠魂麦
                        int count = 1 + level.random.nextInt(3);
                        popResource(level, pos, new ItemStack(ModItems.SOUL_WHEAT.get(), count));
                    }
                    // 未成熟：不掉落任何物品
                    // 底部回退到 SECOND_STAGE_AGE
                    level.setBlock(belowPos, belowState.setValue(AGE, SECOND_STAGE_AGE), 3);
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        // 不调用父类，防止战利品表干扰（所有掉落由 playerWillDestroy 处理）
        super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int age = state.getValue(AGE);
        return SHAPE_BY_AGE[Math.min(age, SHAPE_BY_AGE.length - 1)];
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return false; // 骨粉无效
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        // 完全无操作，即使使用骨粉也不会触发生长
    }
}