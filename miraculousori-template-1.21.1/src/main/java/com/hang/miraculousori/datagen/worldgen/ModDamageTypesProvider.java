package com.hang.miraculousori.datagen.worldgen;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypesProvider {
    public static final ResourceKey<DamageType> BLOAT = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "bloat")
    );

    public static void bootstrap(BootstrapContext<DamageType> context) {
        // 使用显式参数：scaling = NEVER，彻底禁用击退
        context.register(BLOAT, new DamageType(
                "bloat",           // 消息ID
                DamageScaling.NEVER, // 🆕 缩放类型设为 NEVER，避免任何击退
                0.0F               // 饥饿消耗系数
        ));
    }
}