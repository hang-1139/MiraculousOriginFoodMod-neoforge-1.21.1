package com.hang.miraculousori.village;

import com.google.common.collect.ImmutableSet;
import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagerProfessions {
    public static final DeferredRegister<VillagerProfession> PROFESSIONS =
            DeferredRegister.create(Registries.VILLAGER_PROFESSION, MiraculousOriginFoodMod.MODID);

    // 磨坊主职业：工作站点为磨台 POI，持有物品为面粉（示例）
    public static final DeferredHolder<VillagerProfession, VillagerProfession> MILLER =
            PROFESSIONS.register("miller",
                    () -> new VillagerProfession(
                            "miller",
                            holder -> holder.is(ModPoiTypes.MILL_POI.getKey()),
                            holder -> holder.is(ModPoiTypes.MILL_POI.getKey()),
                            ImmutableSet.of(), // 可选的兴趣物品
                            ImmutableSet.of(),
                            SoundEvents.VILLAGER_WORK_MASON // 工作音效（可替换）
                    ));
}