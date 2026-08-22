package com.hang.miraculousori.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WarpedVinePlatformBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);

    public WarpedVinePlatformBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // 必须依附于藤蔓
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.getBlock() instanceof WarpedVinesHeadBlock
                || above.getBlock() instanceof WarpedVinesPlantBlock;
    }

    // 🔑 每 tick 检测上方是否为空气，若是则自毁
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        // 检查上方是否为空气或非藤蔓方块
        BlockState above = level.getBlockState(pos.above());
        if (!(above.getBlock() instanceof WarpedVinesHeadBlock) && !(above.getBlock() instanceof WarpedVinesPlantBlock)) {
            level.destroyBlock(pos, false); // 无掉落
        } else {
            // 继续调度下一次检测
            level.scheduleTick(pos, this, 20); // 每秒检测一次
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 20); // 启动计划刻
        }
    }

    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return false;
    }
}