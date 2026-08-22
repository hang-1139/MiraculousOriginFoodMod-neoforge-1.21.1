package com.hang.miraculousori.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SoulweedFeature extends Feature<NoneFeatureConfiguration> {

    public SoulweedFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        var random = context.random();

        // 尝试生成最多 3 棵枯魂野草（每簇）
        int count = 0;
        int attempts = 0;
        int maxAttempts = 16;

        while (count < 4 && attempts < maxAttempts) {
            attempts++;
            // 在 5x3x5 范围内随机偏移
            int dx = random.nextInt(5) - 2;
            int dz = random.nextInt(5) - 2;
            int dy = random.nextInt(3) - 1;
            BlockPos pos = origin.offset(dx, dy, dz);

            if (canPlaceAt(level, pos)) {
                placeDoublePlant(level, pos);
                count++;
            }
        }

        return count > 0;
    }

    /**
     * 检查是否可以在此位置放置枯魂野草
     * 条件：下方为灵魂沙或灵魂土，上方两格为空
     */
    private boolean canPlaceAt(LevelAccessor level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        // 允许灵魂沙、灵魂土或下界岩
        if (!belowState.is(Blocks.SOUL_SAND) &&
                !belowState.is(Blocks.SOUL_SOIL)) {
            return false;
        }
        // 上方两格为空（可替换）
        if (!level.getBlockState(pos).canBeReplaced() ||
                !level.getBlockState(pos.above()).canBeReplaced()) {
            return false;
        }
        return true;
    }

    /**
     * 放置双格植物（下半部分 + 上半部分）
     */
    private void placeDoublePlant(LevelAccessor level, BlockPos pos) {
        BlockState plantState = com.hang.miraculousori.block.ModBlocks.WITHERED_SOULWEED.get().defaultBlockState();

        // 放置下半部分
        level.setBlock(pos, plantState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 2);
        // 放置上半部分
        level.setBlock(pos.above(), plantState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 2);
    }
}