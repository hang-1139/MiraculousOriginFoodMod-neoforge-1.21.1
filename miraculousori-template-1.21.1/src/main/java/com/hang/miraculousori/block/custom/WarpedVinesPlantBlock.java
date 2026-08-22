package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.hang.miraculousori.block.custom.WarpedVinesHeadBlock.HEAD_TYPE;

public class WarpedVinesPlantBlock extends GrowingPlantBodyBlock implements WarpedVines {
    public static final MapCodec<WarpedVinesPlantBlock> CODEC = simpleCodec(WarpedVinesPlantBlock::new);
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public WarpedVinesPlantBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BERRIES, false)
                .setValue(BOTTOM, false)
                .setValue(TOP, false)
                .setValue(BELOW_AIR, false)
                .setValue(BELOW_HEAD, false)
                .setValue(HEAD_TYPE, HeadType.MIDDLE));
    }

    @Override
    public MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return CODEC;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) ModBlocks.WARPED_VINES_HEAD.get();
    }

    @Override
    protected BlockState updateHeadAfterConvertedFromBody(BlockState headState, BlockState bodyState) {
        return bodyState.setValue(BERRIES, headState.getValue(BERRIES));
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.WARPED_FRUIT.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return WarpedVines.use(player, state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BERRIES, BOTTOM, TOP, BELOW_AIR, BELOW_HEAD);
        builder.add(HEAD_TYPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // ========== 更新状态：顶部判断支持地狱岩和诡异菌岩 ==========
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());

        boolean isTopConnected = above.is(Blocks.NETHERRACK) || above.is(Blocks.WARPED_NYLIUM);
        boolean isBottom = !(below.getBlock() instanceof WarpedVinesPlantBlock);
        boolean isBelowAir = below.isAir();
        boolean isBelowHead = below.getBlock() instanceof WarpedVinesHeadBlock;

        state = state.setValue(TOP, isTopConnected)
                .setValue(BOTTOM, isBottom)
                .setValue(BELOW_AIR, isBelowAir)
                .setValue(BELOW_HEAD, isBelowHead);

        if (isTopConnected) {
            state = state.setValue(BERRIES, false); // 顶部连接段不结果
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    // ========== 联动破坏（保持不变） ==========
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockPos current = pos.above();
            BlockState aboveState = level.getBlockState(current);
            int totalAge = 0;

            while (aboveState.getBlock() instanceof WarpedVinesPlantBlock) {
                totalAge++;
                current = current.above();
                aboveState = level.getBlockState(current);
            }

            if (aboveState.getBlock() instanceof WarpedVinesHeadBlock) {
                totalAge += aboveState.getValue(WarpedVinesHeadBlock.AGE);
                level.destroyBlock(current, true);
                BlockState newHead = ModBlocks.WARPED_VINES_HEAD.get().defaultBlockState()
                        .setValue(WarpedVinesHeadBlock.AGE, Math.min(totalAge, 9))
                        .setValue(WarpedVines.BERRIES, false)
                        .setValue(HEAD_TYPE, HeadType.TOP);
                level.setBlock(current, newHead, 3);
            }

            BlockPos down = pos.below();
            while (level.getBlockState(down).getBlock() instanceof WarpedVinesPlantBlock) {
                level.destroyBlock(down, false);
                down = down.below();
            }
            if (level.getBlockState(down).getBlock() == ModBlocks.WARPED_VINE_PLATFORM.get()) {
                level.destroyBlock(down, false);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}