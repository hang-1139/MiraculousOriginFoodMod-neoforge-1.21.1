package com.hang.miraculousori.event;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.entity.ModEntities;
import com.hang.miraculousori.entity.custom.FloatingMelonEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModEventHandlers {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FLOATING_MELON.get(), FloatingMelonEntity.createAttributes().build());
    }
}