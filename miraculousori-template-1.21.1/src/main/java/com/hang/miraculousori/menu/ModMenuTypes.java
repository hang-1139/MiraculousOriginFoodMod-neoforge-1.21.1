package com.hang.miraculousori.menu;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.entity.blockentity.MillBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, MiraculousOriginFoodMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<MillMenu>> MILL_MENU =
            MENU_TYPES.register("mill_menu",
                    () -> new MenuType<>(
                            (IContainerFactory<MillMenu>) (id, inv, data) -> {
                                return new MillMenu(id, inv,
                                        new SimpleContainer(MillBlockEntity.SLOT_COUNT),
                                        new SimpleContainerData(8));
                            },
                            FeatureFlags.VANILLA_SET
                    ));
}