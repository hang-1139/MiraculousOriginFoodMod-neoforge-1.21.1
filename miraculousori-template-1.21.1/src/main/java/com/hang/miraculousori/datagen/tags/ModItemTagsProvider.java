package com.hang.miraculousori.datagen.tags;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MiraculousOriginFoodMod.MODID, existingFileHelper);
    }

    public static final net.minecraft.tags.TagKey<net.minecraft.world.item.Item> DIVINE =
            net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "divine"));

    public static final TagKey<Item> ASSIMILABLE_TEMPLATE =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "assimilable_template"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(DIVINE).add(
                ModItems.GOD_GAZE.get(),
                ModItems.FOOD_GOD_BLESSING_AMULET.get(),
                ModItems.HUNGER_CURSE_PUNISHMENT.get(),
                ModItems.HUNGER_CURSE_ODE.get(),
                ModItems.ENDING_SPEECH.get(),
                ModItems.END_ROAD_RUNE.get(),
                ModItems.END_NEW_PATH.get()
        );

        tag(ASSIMILABLE_TEMPLATE).add(
                Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
                Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE
                // 如果新版本有新增模板，记得补
        );

    }
}
