package com.hang.miraculousori.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import static com.hang.miraculousori.block.ModBlocks.FLOATING_MELON_BLOCK;
import static com.hang.miraculousori.block.ModBlocks.FLOATING_MELON_CROP;
import static com.hang.miraculousori.block.custom.FloatingMelonCropBlock.AGE;
import static com.hang.miraculousori.block.custom.FloatingMelonCropBlock.HAS_BLOCK;

public class FloatingMelonFeature extends Feature<NoneFeatureConfiguration> {

    public FloatingMelonFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        var random = context.random();

        // 每簇最多生成 1~2 个浮瓜（稀疏）
        int count = 0;
        int attempts = 0;
        int maxAttempts = 20;
        int maxPerCluster = 1 + random.nextInt(2); // 1 或 2

        while (count < maxPerCluster && attempts < maxAttempts) {
            attempts++;
            // 在 7x3x7 范围内随机偏移
            int dx = random.nextInt(7) - 3;
            int dz = random.nextInt(7) - 3;
            int dy = random.nextInt(3) - 1;
            BlockPos pos = origin.offset(dx, dy, dz);

            if (canPlaceAt(level, pos)) {
                placeFloatingMelon(level, pos);
                count++;
            }
        }

        return count > 0;
    }

    /**
     * 检查是否可以放置浮瓜
     * 条件：下方为末地石，当前位置和上方一格为空（可替换）
     */
    private boolean canPlaceAt(LevelAccessor level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        // 下方必须是末地石
        if (!belowState.is(Blocks.END_STONE)) {
            return false;
        }

        // 当前位置和上方一格必须为空（可替换）
        if (!level.getBlockState(pos).canBeReplaced() ||
                !level.getBlockState(pos.above()).canBeReplaced()) {
            return false;
        }

        return true;
    }

    /**
     * 放置浮瓜苗（成熟）和浮瓜块
     */
    private void placeFloatingMelon(LevelAccessor level, BlockPos pos) {
        // 浮瓜苗：age=7（成熟），has_block=true
        BlockState cropState = FLOATING_MELON_CROP.get()
                .defaultBlockState()
                .setValue(AGE, 7)
                .setValue(HAS_BLOCK, true);

        // 浮瓜块
        BlockState melonState = FLOATING_MELON_BLOCK.get().defaultBlockState();

        // 放置浮瓜苗
        level.setBlock(pos, cropState, 2);
        // 放置浮瓜块
        level.setBlock(pos.above(), melonState, 2);
    }
}