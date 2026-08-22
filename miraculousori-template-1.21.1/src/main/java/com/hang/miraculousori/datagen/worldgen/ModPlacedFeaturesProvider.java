package com.hang.miraculousori.datagen.worldgen;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.worldgen.ModFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeaturesProvider {
    public static final ResourceKey<PlacedFeature> SALT_ROCK_ORE_PLACED_KEY = createKey("salt_rock_ore_placed");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_SALT_ROCK_ORE_PLACED_KEY = createKey("deepslate_salt_rock_ore_placed");

    public static final ResourceKey<PlacedFeature> SOULWEED_PLACED_KEY = createKey("soulweed_placed");
    public static final ResourceKey<PlacedFeature> CRIMSON_VINES_PLACED_KEY = createKey("crimson_vines_placed");
    public static final ResourceKey<PlacedFeature> WARPED_VINE_PLACED_KEY = createKey("warped_vine_placed");

    public static final ResourceKey<PlacedFeature> FLOATING_MELON_PLACED_KEY = createKey("floating_melon_placed");
    public static final ResourceKey<PlacedFeature> LEAPING_FRUIT_CLUSTER_PLACED_KEY = createKey("leaping_fruit_cluster_placed");


    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // 岩盐：在 Y=32 到 Y=80 之间生成，每区块尝试8次，大小12
        context.register(SALT_ROCK_ORE_PLACED_KEY,
                new PlacedFeature(configuredFeatures.getOrThrow(ModConfiguredFeaturesProvider.SALT_ROCK_ORE_KEY),
                        List.of(
                                CountPlacement.of(5),  // 每区块尝试8次
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(80)),
                                BiomeFilter.biome()
                        )));

        // 深层岩盐：在 Y=-64 到 Y=16 之间生成，每区块尝试4次，大小8
        context.register(DEEPSLATE_SALT_ROCK_ORE_PLACED_KEY,
                new PlacedFeature(configuredFeatures.getOrThrow(ModConfiguredFeaturesProvider.DEEPSLATE_SALT_ROCK_ORE_KEY),
                        List.of(
                                CountPlacement.of(2),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16)),
                                BiomeFilter.biome()
                        )));

        // 枯魂野草放置特征
        context.register(SOULWEED_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeaturesProvider.SOULWEED_KEY),
                        List.of(
                                CountPlacement.of(10),      // 从 6 改为 12
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(20),   // 从 32 改为 20
                                        VerticalAnchor.absolute(126)
                                ),
                                BiomeFilter.biome()
                        )
                ));

        // 猩红藤丛放置特征
        context.register(CRIMSON_VINES_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeaturesProvider.CRIMSON_VINES_KEY),
                        List.of(
                                CountPlacement.of(20),   // 每区块尝试4次
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(1),
                                        VerticalAnchor.absolute(126)
                                ),
                                BiomeFilter.biome()
                        )
                ));

        context.register(WARPED_VINE_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeaturesProvider.WARPED_VINE_KEY),
                        List.of(
                                CountPlacement.of(6),   // 每区块尝试6次
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(32),
                                        VerticalAnchor.absolute(126)
                                ),
                                BiomeFilter.biome()
                        )
                ));

        // 浮瓜放置特征
        context.register(FLOATING_MELON_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeaturesProvider.FLOATING_MELON_KEY),
                        List.of(
                                CountPlacement.of(2),   // 每区块尝试2次（稀疏）
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(40),
                                        VerticalAnchor.absolute(80)
                                ),
                                BiomeFilter.biome()
                        )
                ));

        // 跃进果簇放置特征（极低概率）
        context.register(LEAPING_FRUIT_CLUSTER_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeaturesProvider.LEAPING_FRUIT_CLUSTER_KEY),
                        List.of(
                                // 每区块尝试一次，但只允许1/20的概率生效，即平均每20个区块出现一个簇
                                RarityFilter.onAverageOnceEvery(20),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(40),
                                        VerticalAnchor.absolute(80)
                                ),
                                BiomeFilter.biome()
                        )
                ));
    }
}