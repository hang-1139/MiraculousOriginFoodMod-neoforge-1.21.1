package com.hang.miraculousori.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import static com.hang.miraculousori.block.ModBlocks.*;
import static com.hang.miraculousori.block.custom.HeadType.TOP;
import static com.hang.miraculousori.block.custom.WarpedVines.BERRIES;
import static com.hang.miraculousori.block.custom.WarpedVines.CAN_PLATFORM;
import static com.hang.miraculousori.block.custom.WarpedVinesHeadBlock.AGE;
import static com.hang.miraculousori.block.custom.WarpedVinesHeadBlock.HEAD_TYPE;

public class WarpedVineFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 9;
    private static final float CHANCE_PLATFORM = 0.5F;
    private static final float CHANCE_BERRY = 1.0F / 6.0F;

    public WarpedVineFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int count = 0;
        int attempts = 0;
        int maxAttempts = 16;
        int maxPerCluster = 2;

        while (count < maxPerCluster && attempts < maxAttempts) {
            attempts++;
            int dx = random.nextInt(5) - 2;
            int dz = random.nextInt(5) - 2;
            int dy = random.nextInt(3) - 1;
            BlockPos pos = origin.offset(dx, dy, dz);

            if (canPlaceAt(level, pos)) {
                int length = MIN_LENGTH + random.nextInt(MAX_LENGTH - MIN_LENGTH + 1);
                placeWarpedVine(level, pos, length, random);
                count++;
            }
        }

        return count > 0;
    }

    private boolean canPlaceAt(LevelAccessor level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        if (!aboveState.is(Blocks.NETHERRACK) && !aboveState.is(Blocks.WARPED_NYLIUM)) {
            return false;
        }
        return level.getBlockState(pos).canBeReplaced();
    }

    private void placeWarpedVine(LevelAccessor level, BlockPos pos, int length, RandomSource random) {
        // 1. 放置头部（顶部，接触下界岩）
        boolean canPlatform = random.nextFloat() < CHANCE_PLATFORM;
        BlockState headState = WARPED_VINES_HEAD.get()
                .defaultBlockState()
                .setValue(AGE, 0)
                .setValue(HEAD_TYPE, TOP)
                .setValue(BERRIES, false)
                .setValue(CAN_PLATFORM, canPlatform);
        level.setBlock(pos, headState, 2);

        // 2. 放置身体块，记录底部位置
        BlockPos currentPos = pos.below();
        BlockPos bottomPos = null;
        for (int i = 0; i < length - 1; i++) {
            if (!level.getBlockState(currentPos).canBeReplaced()) {
                break;
            }

            boolean isBottom = (i == length - 2);
            boolean isBerry = !isBottom && random.nextFloat() < CHANCE_BERRY;

            BlockState bodyState = WARPED_VINES_PLANT.get()
                    .defaultBlockState()
                    .setValue(BERRIES, isBerry)
                    .setValue(WARPED_VINES_PLANT.get().TOP, false)
                    .setValue(WARPED_VINES_PLANT.get().BOTTOM, isBottom)
                    .setValue(WARPED_VINES_PLANT.get().BELOW_AIR, false)   // 临时，稍后根据平台更新
                    .setValue(WARPED_VINES_PLANT.get().BELOW_HEAD, false);

            level.setBlock(currentPos, bodyState, 2);

            if (isBottom) {
                bottomPos = currentPos;
            }

            currentPos = currentPos.below();
        }

        // 如果没有底部位置（长度不足），直接返回
        if (bottomPos == null) return;

        // 3. 决定是否生成平台
        boolean placePlatform = canPlatform && random.nextFloat() < CHANCE_PLATFORM;
        BlockPos platformPos = bottomPos.below();  // 底部身体块的正下方

        if (placePlatform && level.getBlockState(platformPos).canBeReplaced()) {
            // 生成平台
            level.setBlock(platformPos, WARPED_VINE_PLATFORM.get().defaultBlockState(), 2);
            // 更新底部身体块的 BELOW_AIR = false（下方有平台）
            level.setBlock(bottomPos, level.getBlockState(bottomPos)
                    .setValue(WARPED_VINES_PLANT.get().BELOW_AIR, false), 2);
        } else {
            // 无平台，底部悬空
            level.setBlock(bottomPos, level.getBlockState(bottomPos)
                    .setValue(WARPED_VINES_PLANT.get().BELOW_AIR, true), 2);
        }
    }
}