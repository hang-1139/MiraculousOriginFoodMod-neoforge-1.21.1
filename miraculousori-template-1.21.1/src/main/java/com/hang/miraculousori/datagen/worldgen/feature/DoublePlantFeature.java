package com.hang.miraculousori.datagen.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class DoublePlantFeature extends Feature<SimpleBlockConfiguration> {
    public DoublePlantFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        BlockState state = context.config().toPlace().getState(context.random(), origin);

        if (!(state.getBlock() instanceof DoublePlantBlock)) {
            return false;
        }

        // 确保放置的是 LOWER 部分
        BlockState lowerState = state.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
        BlockPos upperPos = origin.above();
        BlockState upperState = lowerState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);

        // 检查下方是否可放置（灵魂沙或灵魂土）
        BlockState belowState = level.getBlockState(origin.below());
        if (!belowState.is(Blocks.SOUL_SAND) && !belowState.is(Blocks.SOUL_SOIL)) {
            return false;
        }

        // 检查上方是否为空
        if (!level.getBlockState(upperPos).isAir()) {
            return false;
        }

        // 放置两个方块
        level.setBlock(origin, lowerState, 2);
        level.setBlock(upperPos, upperState, 2);

        return true;
    }
}