package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WarpedVinesHeadBlock extends GrowingPlantHeadBlock implements WarpedVines {
    public static final MapCodec<WarpedVinesHeadBlock> CODEC = simpleCodec(WarpedVinesHeadBlock::new);
    public static final EnumProperty<HeadType> HEAD_TYPE = EnumProperty.create("head_type", HeadType.class);

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 9;
    private static final float CHANCE_OF_BERRIES_ON_GROWTH = 0.4F; // 仅用于身体
    private static final float CHANCE_OF_PLATFORM = 0.5F;
    private static final int MAX_BERRIES = 4;
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public WarpedVinesHeadBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(BERRIES, false)      // 头部永远无浆果
                .setValue(HEAD_TYPE, HeadType.TOP)
                .setValue(CAN_PLATFORM, true));
    }

    @Override
    public MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    // ========== 禁用骨粉 ==========
    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        // 无操作
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.WARPED_VINES_PLANT.get();
    }

    @Override
    protected BlockState updateBodyAfterConvertedFromHead(BlockState headState, BlockState bodyState) {
        return bodyState.setValue(BERRIES, headState.getValue(BERRIES));
    }

    @Override
    protected BlockState getGrowIntoState(BlockState state, RandomSource random) {
        return super.getGrowIntoState(state, random)
                .setValue(BERRIES, false);  // 强制 false
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.WARPED_FRUIT.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BERRIES, HEAD_TYPE, CAN_PLATFORM);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // ========== 放置检测：支持地狱岩和诡异菌岩 ==========
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState above = level.getBlockState(pos.above());
        if (!above.is(Blocks.NETHERRACK) && !above.is(Blocks.WARPED_NYLIUM)) {
            return null;
        }
        if (!canGrowInto(level.getBlockState(pos.below()))) {
            return null;
        }
        boolean canPlatform = level.random.nextFloat() >= 0.25F;
        return this.defaultBlockState()
                .setValue(AGE, 0)
                .setValue(HEAD_TYPE, HeadType.TOP)
                .setValue(CAN_PLATFORM, canPlatform);
    }

    // ========== 存活判定：支持地狱岩和诡异菌岩 ==========
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.is(Blocks.NETHERRACK) || above.is(Blocks.WARPED_NYLIUM)
                || above.getBlock() instanceof WarpedVinesHeadBlock
                || above.getBlock() instanceof WarpedVinesPlantBlock;
    }

    // ========== 更新状态：顶部判断扩展 ==========
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockState newState = super.updateShape(state, direction, neighborState, level, pos, neighborPos);

        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());

        HeadType type;
        if (above.is(Blocks.NETHERRACK) || above.is(Blocks.WARPED_NYLIUM)) {
            type = HeadType.TOP;
        } else if (above.getBlock() instanceof WarpedVinesPlantBlock) {
            if (below.isAir()) {
                type = HeadType.BOTTOM_NO_PLATFORM;
            } else {
                type = HeadType.BOTTOM_WITH_PLATFORM;
            }
        } else {
            type = HeadType.MIDDLE;
        }

        return newState.setValue(HEAD_TYPE, type).setValue(BERRIES, false);
    }

    private HeadType getHeadType(LevelAccessor level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        if (above.is(Blocks.NETHERRACK) || above.is(Blocks.WARPED_NYLIUM)) {
            return HeadType.TOP;
        }
        BlockState below = level.getBlockState(pos.below());
        if (below.isAir()) {
            return HeadType.BOTTOM_NO_PLATFORM;
        } else if (below.getBlock() == ModBlocks.WARPED_VINE_PLATFORM.get()) {
            return HeadType.BOTTOM_WITH_PLATFORM;
        }
        return HeadType.MIDDLE;
    }

    // ========== 生长逻辑 ==========
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int maxLength = getMaxLength(pos);
        int currentAge = state.getValue(AGE);

        manageBerries(level, pos, random);

        if (currentAge >= maxLength) {
            if (state.getValue(CAN_PLATFORM)) {
                if (random.nextFloat() < CHANCE_OF_PLATFORM) {
                    BlockPos below = pos.below();
                    if (level.getBlockState(below).isAir()) {
                        level.setBlock(below, ModBlocks.WARPED_VINE_PLATFORM.get().defaultBlockState(), 3);
                    }
                }
            }
            HeadType type = getHeadType(level, pos);
            if (state.getValue(HEAD_TYPE) != type) {
                level.setBlock(pos, state.setValue(HEAD_TYPE, type), 3);
            }
            return;
        }

        BlockPos belowPos = pos.below();
        if (!canGrowInto(level.getBlockState(belowPos))) {
            return;
        }

        int newAge = currentAge + 1;
        BlockState newHeadState = this.defaultBlockState()
                .setValue(AGE, newAge)
                .setValue(BERRIES, false)
                .setValue(HEAD_TYPE, HeadType.MIDDLE)
                .setValue(CAN_PLATFORM, state.getValue(CAN_PLATFORM));
        level.setBlock(belowPos, newHeadState, 3);

        BlockState above = level.getBlockState(pos.above());
        boolean isTopConnected = above.is(Blocks.NETHERRACK) || above.is(Blocks.WARPED_NYLIUM) || above.getBlock() instanceof WarpedVinesHeadBlock;

        BlockState bodyState = ModBlocks.WARPED_VINES_PLANT.get().defaultBlockState()
                .setValue(BERRIES, false)
                .setValue(TOP, isTopConnected);
        level.setBlock(pos, bodyState, 3);

        if (newAge >= maxLength) {
            if (state.getValue(CAN_PLATFORM)) {
                if (random.nextFloat() < CHANCE_OF_PLATFORM) {
                    BlockPos platformPos = belowPos.below();
                    if (level.getBlockState(platformPos).isAir()) {
                        level.setBlock(platformPos, ModBlocks.WARPED_VINE_PLATFORM.get().defaultBlockState(), 3);
                    }
                }
            }
            HeadType type = getHeadType(level, belowPos);
            level.setBlock(belowPos, newHeadState.setValue(HEAD_TYPE, type), 3);
        }

        manageBerries(level, belowPos, random);
    }

    private int getMaxLength(BlockPos pos) {
        int hash = Math.abs(pos.hashCode());
        return MIN_LENGTH + (hash % (MAX_LENGTH - MIN_LENGTH + 1));
    }

    private void manageBerries(ServerLevel level, BlockPos headPos, RandomSource random) {
        List<BlockPos> validPositions = new ArrayList<>();
        BlockPos current = headPos.above();
        while (level.getBlockState(current).getBlock() instanceof WarpedVinesPlantBlock) {
            BlockState state = level.getBlockState(current);
            if (!state.getValue(WarpedVines.TOP)) {
                validPositions.add(current);
            }
            current = current.above();
        }

        int berryCount = 0;
        for (BlockPos pos : validPositions) {
            if (level.getBlockState(pos).getValue(BERRIES)) {
                berryCount++;
            }
        }

        if (berryCount >= MAX_BERRIES) {
            return;
        }

        final float CHANCE_PER_BERRY = 1.0F / 6.0F;
        for (BlockPos pos : validPositions) {
            if (berryCount >= MAX_BERRIES) {
                break;
            }
            BlockState state = level.getBlockState(pos);
            if (!state.getValue(BERRIES) && random.nextFloat() < CHANCE_PER_BERRY) {
                level.setBlock(pos, state.setValue(BERRIES, true), 3);
                berryCount++;
            }
        }
    }

    // 联动破坏（保持不变）
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockPos current = pos.below();
            while (level.getBlockState(current).getBlock() instanceof WarpedVinesPlantBlock) {
                level.destroyBlock(current, false);
                current = current.below();
            }
            if (level.getBlockState(current).getBlock() == ModBlocks.WARPED_VINE_PLATFORM.get()) {
                level.destroyBlock(current, false);
            }
            level.scheduleTick(pos, this, 1);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        if (level.getBlockState(pos).isAir()) {
            BlockState above = level.getBlockState(pos.above());
            if (above.is(Blocks.NETHERRACK) || above.is(Blocks.WARPED_NYLIUM) || above.getBlock() instanceof WarpedVinesHeadBlock || above.getBlock() instanceof WarpedVinesPlantBlock) {
                boolean canPlatform = random.nextFloat() >= 0.25F;
                BlockState newState = this.defaultBlockState()
                        .setValue(AGE, 0)
                        .setValue(BERRIES, false)
                        .setValue(HEAD_TYPE, HeadType.TOP)
                        .setValue(CAN_PLATFORM, canPlatform);
                level.setBlock(pos, newState, 3);
            }
        }
    }
}