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

import static com.hang.miraculousori.block.ModBlocks.LEAPING_FRUIT_PLANT_TOP;
import static com.hang.miraculousori.block.custom.LeapingFruitPlantBlock.FACING;
import static com.hang.miraculousori.block.custom.LeapingFruitPlantBlock.IS_NATURAL;
import static com.hang.miraculousori.block.custom.LeapingFruitPlantBlock.REMAINING_GROWTH;
import static com.hang.miraculousori.block.custom.LeapingFruitPlantBlock.MAXED_OUT;
import static com.hang.miraculousori.block.custom.LeapingFruitPlantTopBlock.CAN_GROW;

public class LeapingFruitClusterFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MIN_CLUSTER_SIZE = 5;
    private static final int MAX_CLUSTER_SIZE = 7;
    private static final int SEARCH_RADIUS = 5;   // 水平搜索半径
    private static final int VERTICAL_RANGE = 3;  // 垂直偏移范围（允许上下3格）
    private static final int MAX_ATTEMPTS = 30;

    public LeapingFruitClusterFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int clusterSize = MIN_CLUSTER_SIZE + random.nextInt(MAX_CLUSTER_SIZE - MIN_CLUSTER_SIZE + 1);
        int placed = 0;
        int attempts = 0;

        while (placed < clusterSize && attempts < MAX_ATTEMPTS) {
            attempts++;
            // 在水平半径内随机偏移
            int dx = random.nextInt(SEARCH_RADIUS * 2 + 1) - SEARCH_RADIUS;
            int dz = random.nextInt(SEARCH_RADIUS * 2 + 1) - SEARCH_RADIUS;
            // 垂直偏移在 -VERTICAL_RANGE ~ +VERTICAL_RANGE
            int dy = random.nextInt(VERTICAL_RANGE * 2 + 1) - VERTICAL_RANGE;
            BlockPos targetPos = origin.offset(dx, dy, dz);

            if (canPlaceAt(level, targetPos)) {
                // 随机选择朝向（水平方向或向上）
                Direction facing = Direction.values()[random.nextInt(Direction.values().length)];
                // 为了让植株更自然，避免朝下（因为顶端一般不朝下）
                while (facing == Direction.DOWN) {
                    facing = Direction.values()[random.nextInt(Direction.values().length)];
                }

                BlockState state = LEAPING_FRUIT_PLANT_TOP.get().defaultBlockState()
                        .setValue(FACING, facing)
                        .setValue(IS_NATURAL, true)      // 自然生成
                        .setValue(CAN_GROW, false)       // 未被激活
                        .setValue(REMAINING_GROWTH, 0)
                        .setValue(MAXED_OUT, false);

                level.setBlock(targetPos, state, 2);
                placed++;
            }
        }

        return placed > 0;
    }

    private boolean canPlaceAt(LevelAccessor level, BlockPos pos) {
        // 下方必须是末地石
        if (!level.getBlockState(pos.below()).is(Blocks.END_STONE)) {
            return false;
        }
        // 自身必须可替换（为空）
        return level.getBlockState(pos).canBeReplaced();
    }
}