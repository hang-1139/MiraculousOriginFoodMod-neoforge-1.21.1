package com.hang.miraculousori.worldgen;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.worldgen.feature.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, MiraculousOriginFoodMod.MODID);

    public static final DeferredHolder<Feature<?>, SoulweedFeature> SOULWEED =
            FEATURES.register("soulweed", () -> new SoulweedFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, CrimsonVinesFeature> CRIMSON_VINES =
            FEATURES.register("crimson_vines", () -> new CrimsonVinesFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, FloatingMelonFeature> FLOATING_MELON =
            FEATURES.register("floating_melon", () -> new FloatingMelonFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, WarpedVineFeature> WARPED_VINE =
            FEATURES.register("warped_vine", () -> new WarpedVineFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, LeapingFruitClusterFeature> LEAPING_FRUIT_CLUSTER =
            FEATURES.register("leaping_fruit_cluster", () -> new LeapingFruitClusterFeature(NoneFeatureConfiguration.CODEC));
}