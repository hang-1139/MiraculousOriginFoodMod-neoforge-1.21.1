package com.hang.miraculousori.datagen.data_maps;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(NeoForgeDataMaps.COMPOSTABLES)
            .add(getKey(ModItems.APPLE_PULP.get()), new Compostable(0.65F), false) // 苹果渣
            .add(getKey(ModItems.WATERMELON_PULP.get()), new Compostable(0.65F), false) // 西瓜渣
            .add(getKey(ModItems.CARROT_PULP.get()), new Compostable(0.65F), false) // 胡萝卜渣
            .add(getKey(ModItems.BEETROOT_PULP.get()), new Compostable(0.65F), false) // 甜菜根渣
            .add(getKey(ModItems.SWEET_BERRY_PULP.get()), new Compostable(0.65F), false) // 浆果渣
            .add(getKey(ModItems.SUGARCANE_PULP.get()), new Compostable(0.65F), false) // 甘蔗渣
            .add(getKey(ModItems.GLOW_BERRY_PULP.get()), new Compostable(0.65F), false) // 发光浆果渣

                .add(getKey(ModItems.WARPED_FRUIT.get()), new Compostable(0.3F), false) // 畸形 fruit
                .add(getKey(ModItems.CRIMSON_FRUIT.get()), new Compostable(0.3F), false) // 猩红 fruit
            .add(getKey(ModItems.WARPED_FRUIT_POWDER.get()), new Compostable(0.3F), false) // 畸形 fruit powder
            .add(getKey(ModItems.CRIMSON_FRUIT_POWDER.get()), new Compostable(0.3F), false) // 猩红
            .add(getKey(ModItems.COCOA_POWDER.get()), new Compostable(0.3F), false) // 可可粉
        ;
    }

    // 辅助方法：获取物品的 ResourceKey
    private ResourceKey<Item> getKey(Item item) {
        return ResourceKey.create(
                net.minecraft.core.registries.Registries.ITEM,
                ResourceLocation.parse(MiraculousOriginFoodMod.MODID + ":" +
                        net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item).getPath())
        );
    }
}