package com.hang.miraculousori.datagen.worldgen;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifierProvider {
    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, name));
    }

    public static final ResourceKey<BiomeModifier> ADD_SALT_ROCK_ORE = createKey("add_salt_rock_ore");
    public static final ResourceKey<BiomeModifier> ADD_DEEPSLATE_SALT_ROCK_ORE = createKey("add_deepslate_salt_rock_ore");

    public static final ResourceKey<BiomeModifier> ADD_SOULWEED = createKey("add_soulweed");
    public static final ResourceKey<BiomeModifier> ADD_CRIMSON_VINES = createKey("add_crimson_vines");
    public static final ResourceKey<BiomeModifier> ADD_WARPED_VINE = createKey("add_warped_vine");

    public static final ResourceKey<BiomeModifier> ADD_FLOATING_MELON = createKey("add_floating_melon");
    public static final ResourceKey<BiomeModifier> ADD_LEAPING_FRUIT_CLUSTER = createKey("add_leaping_fruit_cluster");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureLookup = context.lookup(Registries.PLACED_FEATURE);

        // ===== 主世界生物群系 =====
        HolderSet.Named<Biome> overworldBiomes = biomeLookup.get(BiomeTags.IS_OVERWORLD).orElseThrow();

        // ===== 下界 =====
        HolderSet.Named<Biome> netherBiomes = biomeLookup.get(BiomeTags.IS_NETHER).orElseThrow();
        Holder<Biome> warpedForest = biomeLookup.get(ResourceKey.create(Registries.BIOME,
                ResourceLocation.withDefaultNamespace("warped_forest"))).orElseThrow();
        HolderSet<Biome> warpedForestBiomes = HolderSet.direct(warpedForest);

        HolderSet.Named<Biome> endBiomes = biomeLookup.get(BiomeTags.IS_END).orElseThrow();

        Holder<Biome> endHighlands = biomeLookup.get(ResourceKey.create(Registries.BIOME, ResourceLocation.withDefaultNamespace("end_highlands"))).orElseThrow();
        Holder<Biome> endMidlands = biomeLookup.get(ResourceKey.create(Registries.BIOME, ResourceLocation.withDefaultNamespace("end_midlands"))).orElseThrow();
        Holder<Biome> endBarrens = biomeLookup.get(ResourceKey.create(Registries.BIOME, ResourceLocation.withDefaultNamespace("end_barrens"))).orElseThrow();
        Holder<Biome> smallEndIslands = biomeLookup.get(ResourceKey.create(Registries.BIOME, ResourceLocation.withDefaultNamespace("small_end_islands"))).orElseThrow();
        HolderSet<Biome> endOuterIslands = HolderSet.direct(endHighlands, endMidlands, endBarrens, smallEndIslands);

        // ----- 岩盐 -----
        context.register(ADD_SALT_ROCK_ORE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        overworldBiomes,
                        HolderSet.direct(placedFeatureLookup.getOrThrow(ModPlacedFeaturesProvider.SALT_ROCK_ORE_PLACED_KEY)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                ));

        // ----- 深层岩盐 -----
        context.register(ADD_DEEPSLATE_SALT_ROCK_ORE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        overworldBiomes,
                        HolderSet.direct(placedFeatureLookup.getOrThrow(ModPlacedFeaturesProvider.DEEPSLATE_SALT_ROCK_ORE_PLACED_KEY)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                ));

        // 枯魂野草 → 所有下界生物群系
        context.register(ADD_SOULWEED,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        netherBiomes,
                        HolderSet.direct(placedFeatureLookup.getOrThrow(ModPlacedFeaturesProvider.SOULWEED_PLACED_KEY)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));

        context.register(ADD_CRIMSON_VINES,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        netherBiomes,
                        HolderSet.direct(placedFeatureLookup.getOrThrow(ModPlacedFeaturesProvider.CRIMSON_VINES_PLACED_KEY)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));


        context.register(ADD_WARPED_VINE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        warpedForestBiomes,
                        HolderSet.direct(placedFeatureLookup.getOrThrow(ModPlacedFeaturesProvider.WARPED_VINE_PLACED_KEY)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));
        // 浮空瓜 → 所有末地生物群系
        context.register(ADD_FLOATING_MELON,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        endOuterIslands,
                        HolderSet.direct(placedFeatureLookup.getOrThrow(ModPlacedFeaturesProvider.FLOATING_MELON_PLACED_KEY)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));

        // 跃进果簇 → 末地外岛
        context.register(ADD_LEAPING_FRUIT_CLUSTER,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        endOuterIslands,
                        HolderSet.direct(placedFeatureLookup.getOrThrow(ModPlacedFeaturesProvider.LEAPING_FRUIT_CLUSTER_PLACED_KEY)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));
    }
}