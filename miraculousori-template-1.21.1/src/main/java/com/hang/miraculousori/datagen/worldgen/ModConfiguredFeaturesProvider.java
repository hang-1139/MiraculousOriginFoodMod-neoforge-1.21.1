package com.hang.miraculousori.datagen.worldgen;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.worldgen.ModFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeaturesProvider {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SALT_ROCK_ORE_KEY = createKey("salt_rock_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_SALT_ROCK_ORE_KEY = createKey("deepslate_salt_rock_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SOULWEED_KEY = createKey("soulweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_VINES_KEY = createKey("crimson_vines");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WARPED_VINE_KEY = createKey("warped_vine");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOATING_MELON_KEY = createKey("floating_melon");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEAPING_FRUIT_CLUSTER_KEY = createKey("leaping_fruit_cluster");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> blockLookup = context.lookup(Registries.BLOCK);

        // 岩盐：替换石头（包括深板岩？不，我们只替换石头）
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        // 深层岩盐：替换深板岩
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        // 盐岩矿脉大小：10-16
        List<OreConfiguration.TargetBlockState> saltTargets = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.SALT_ROCK.get().defaultBlockState())
        );
        // 深层岩盐矿脉大小：6-12
        List<OreConfiguration.TargetBlockState> deepslateSaltTargets = List.of(
                OreConfiguration.target(deepslateReplaceable, ModBlocks.DEEPSLATE_SALT_ROCK.get().defaultBlockState())
        );

        context.register(SALT_ROCK_ORE_KEY,
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(saltTargets, 12))); // 大小12
        context.register(DEEPSLATE_SALT_ROCK_ORE_KEY,
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(deepslateSaltTargets, 8)));
        // 枯魂野草
        context.register(SOULWEED_KEY,
                new ConfiguredFeature<>(ModFeatures.SOULWEED.get(), NoneFeatureConfiguration.INSTANCE));

        context.register(CRIMSON_VINES_KEY,
                new ConfiguredFeature<>(ModFeatures.CRIMSON_VINES.get(), NoneFeatureConfiguration.INSTANCE));

        context.register(WARPED_VINE_KEY,
                new ConfiguredFeature<>(ModFeatures.WARPED_VINE.get(), NoneFeatureConfiguration.INSTANCE));

        context.register(FLOATING_MELON_KEY,
                new ConfiguredFeature<>(ModFeatures.FLOATING_MELON.get(), NoneFeatureConfiguration.INSTANCE));

        context.register(LEAPING_FRUIT_CLUSTER_KEY,
                new ConfiguredFeature<>(ModFeatures.LEAPING_FRUIT_CLUSTER.get(), NoneFeatureConfiguration.INSTANCE));
    }
}