package com.hang.miraculousori.component;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MiraculousOriginFoodMod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<PleasureComponent>> PLEASURE_DATA =
            DATA_COMPONENT_TYPES.register("pleasure_data",
                    () -> DataComponentType.<PleasureComponent>builder()
                            .persistent(PleasureComponent.CODEC)
                            .build()
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<PotionEnhancementData>> POTION_ENHANCEMENT =
            DATA_COMPONENT_TYPES.register("potion_enhancement",
                    () -> DataComponentType.<PotionEnhancementData>builder()
                            .persistent(PotionEnhancementData.CODEC)
                            .networkSynchronized(PotionEnhancementData.STREAM_CODEC)
                            .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<OwnerComponent>> OWNER =
            DATA_COMPONENT_TYPES.register("owner",
                    () -> DataComponentType.<OwnerComponent>builder()
                            .persistent(OwnerComponent.CODEC)
                            .networkSynchronized(OwnerComponent.STREAM_CODEC)
                            .build()
            );
}