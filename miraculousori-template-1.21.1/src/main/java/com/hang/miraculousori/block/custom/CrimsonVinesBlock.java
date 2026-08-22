package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CrimsonVinesBlock extends BushBlock {
    public static final MapCodec<CrimsonVinesBlock> CODEC = simpleCodec(CrimsonVinesBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    public CrimsonVinesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(AGE, 0)
                        .setValue(HALF, DoubleBlockHalf.UPPER)
        );
    }

    @Override
    public MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (half == DoubleBlockHalf.UPPER) {
            BlockState above = level.getBlockState(pos.above());
            return above.is(Blocks.NETHERRACK) || above.is(Blocks.NETHER_WART_BLOCK) || above.is(Blocks.CRIMSON_NYLIUM);
        } else {
            BlockState above = level.getBlockState(pos.above());
            return above.is(this) && above.getValue(HALF) == DoubleBlockHalf.UPPER;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        DoubleBlockHalf half = state.getValue(HALF);

        if (half == DoubleBlockHalf.UPPER) {
            BlockPos belowPos = pos.below();
            BlockState below = level.getBlockState(belowPos);

            if (!below.is(this) || below.getValue(HALF) != DoubleBlockHalf.LOWER) {
                if (level.getBlockState(belowPos).isAir()) {
                    BlockState newBelow = this.defaultBlockState()
                            .setValue(AGE, state.getValue(AGE))
                            .setValue(HALF, DoubleBlockHalf.LOWER);
                    level.setBlock(belowPos, newBelow, 3);
                } else {
                    return Blocks.AIR.defaultBlockState();
                }
            } else {
                if (below.getValue(AGE) != state.getValue(AGE)) {
                    level.setBlock(belowPos, below.setValue(AGE, state.getValue(AGE)), 3);
                }
            }
        } else {
            BlockState above = level.getBlockState(pos.above());
            if (!above.is(this) || above.getValue(HALF) != DoubleBlockHalf.UPPER) {
                return Blocks.AIR.defaultBlockState();
            }
            int aboveAge = above.getValue(AGE);
            if (state.getValue(AGE) != aboveAge) {
                return state.setValue(AGE, aboveAge);
            }
        }
        return super.updateShape(state, facing, facingState, level, pos, facingPos);
    }

    // ==================== 生长逻辑（高概率，快速生长） ====================
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);

        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) return;

        int age = state.getValue(AGE);
        if (age >= 4) {
            level.scheduleTick(pos, this, 20 + random.nextInt(20));
            return;
        }

        // 生长概率：约每 5 秒（100 tick）生长一段
        if (random.nextInt(100) == 0) {
            int newAge = age + 1;
            level.setBlock(pos, state.setValue(AGE, newAge), 2);

            BlockPos belowPos = pos.below();
            BlockState below = level.getBlockState(belowPos);
            if (below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER) {
                level.setBlock(belowPos, below.setValue(AGE, newAge), 2);
            } else if (level.getBlockState(belowPos).isAir()) {
                BlockState newBelow = this.defaultBlockState()
                        .setValue(AGE, newAge)
                        .setValue(HALF, DoubleBlockHalf.LOWER);
                level.setBlock(belowPos, newBelow, 3);
            }
        }

        // 每 10~20 tick 检测一次
        level.scheduleTick(pos, this, 10 + random.nextInt(10));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            level.scheduleTick(pos, this, 10 + level.random.nextInt(10));
            BlockPos belowPos = pos.below();
            if (level.getBlockState(belowPos).isAir()) {
                BlockState newBelow = this.defaultBlockState()
                        .setValue(AGE, state.getValue(AGE))
                        .setValue(HALF, DoubleBlockHalf.LOWER);
                level.setBlock(belowPos, newBelow, 3);
            }
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        int age = state.getValue(AGE);
        if (age < 3) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        BlockPos topPos, bottomPos;
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            topPos = pos;
            bottomPos = pos.below();
        } else {
            topPos = pos.above();
            bottomPos = pos;
        }

        BlockState topState = level.getBlockState(topPos);
        BlockState bottomState = level.getBlockState(bottomPos);

        if (!topState.is(this) || !bottomState.is(this)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int drops = (age == 3) ? 1 : 2;
        if (drops > 0) {
            popResource(level, pos, new ItemStack(ModItems.CRIMSON_FRUIT.get(), drops));
        }

        level.setBlock(topPos, topState.setValue(AGE, 0), 3);
        level.setBlock(bottomPos, bottomState.setValue(AGE, 0), 3);

        level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            DoubleBlockHalf half = state.getValue(HALF);
            BlockPos otherPos = (half == DoubleBlockHalf.UPPER) ? pos.below() : pos.above();
            BlockState otherState = level.getBlockState(otherPos);

            if (otherState.is(this)) {
                dropFruitIfAny(level, otherPos, otherState);
                level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
            }

            dropFruitIfAny(level, pos, state);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    private void dropFruitIfAny(Level level, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age == 3 || age == 4) {
            popResource(level, pos, new ItemStack(ModItems.CRIMSON_FRUIT.get(), 1));
        }
    }
}