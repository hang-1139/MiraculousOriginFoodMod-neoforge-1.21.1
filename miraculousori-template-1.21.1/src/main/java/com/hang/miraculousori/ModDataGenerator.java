package com.hang.miraculousori;

import com.hang.miraculousori.datagen.Advancement.ModAdvancementProvider;
import com.hang.miraculousori.datagen.data_maps.ModDataMapProvider;
import com.hang.miraculousori.datagen.lang.ModEnUsLangProvider;
import com.hang.miraculousori.datagen.lang.ModZhCnLangProvider;
import com.hang.miraculousori.datagen.loot.ModBlockLootTablesProvider;
import com.hang.miraculousori.datagen.models.ModBlockStatesProvider;
import com.hang.miraculousori.datagen.models.ModItemModelsProvider;
import com.hang.miraculousori.datagen.recipes.ModRecipesProvider;
import com.hang.miraculousori.datagen.tags.ModBlockTagsProvider;
import com.hang.miraculousori.datagen.tags.ModDamageTypeTagsProvider;
import com.hang.miraculousori.datagen.tags.ModItemTagsProvider;
import com.hang.miraculousori.datagen.tags.ModPointTagProvider;
import com.hang.miraculousori.datagen.worldgen.ModWorldGenProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTablesProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        generator.addProvider(event.includeServer(), new ModRecipesProvider(packOutput, lookupProvider));

        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPointTagProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeServer(), new ModItemModelsProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBlockStatesProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEnUsLangProvider(packOutput));
        generator.addProvider(event.includeServer(), new ModZhCnLangProvider(packOutput));

        generator.addProvider(event.includeServer(), new ModDataMapProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeServer(), new ModAdvancementProvider(
                packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeServer(), new ModDamageTypeTagsProvider(
                packOutput, lookupProvider, existingFileHelper));
    }
}