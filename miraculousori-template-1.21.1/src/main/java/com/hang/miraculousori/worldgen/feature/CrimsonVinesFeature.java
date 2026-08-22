package com.hang.miraculousori.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import static com.hang.miraculousori.block.custom.CrimsonVinesBlock.AGE;
import static com.hang.miraculousori.block.custom.CrimsonVinesBlock.HALF;

public class CrimsonVinesFeature extends Feature<NoneFeatureConfiguration> {

    public CrimsonVinesFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        var random = context.random();

        int count = 0;
        int attempts = 0;
        int maxAttempts = 20;
        int maxPerCluster = 3;   // 每簇最多3个藤蔓

        while (count < maxPerCluster && attempts < maxAttempts) {
            attempts++;
            // 在 5x3x5 范围内随机偏移
            int dx = random.nextInt(7) - 2;
            int dz = random.nextInt(7) - 2;
            int dy = random.nextInt(3) - 1;
            BlockPos pos = origin.offset(dx, dy, dz);

            if (canPlaceAt(level, pos)) {
                placeCrimsonVines(level, pos);
                count++;
            }
        }

        return count > 0;
    }

    /**
     * 检查是否可以在此位置放置猩红藤丛
     * 条件：上方为下界疣块，且当前位置和下方一格为空（可替换）
     */
    private boolean canPlaceAt(LevelAccessor level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        // 上方必须是下界疣块
        if (!aboveState.is(Blocks.NETHER_WART_BLOCK)) {
            return false;
        }

        // 当前位置和下方一格必须为空（可替换）
        return level.getBlockState(pos).canBeReplaced() &&
                level.getBlockState(pos.below()).canBeReplaced();
    }

    /**
     * 放置猩红藤丛（顶部 UPPER + 底部 LOWER），AGE 设为最大值 4（成熟）
     */
    private void placeCrimsonVines(LevelAccessor level, BlockPos pos) {
        BlockState vineState = com.hang.miraculousori.block.ModBlocks.CRIMSON_VINES.get()
                .defaultBlockState()
                .setValue(AGE, 4); // 成熟

        // 放置顶部（UPPER）
        level.setBlock(pos, vineState.setValue(HALF, DoubleBlockHalf.UPPER), 2);
        // 放置底部（LOWER）
        level.setBlock(pos.below(), vineState.setValue(HALF, DoubleBlockHalf.LOWER), 2);
    }
}