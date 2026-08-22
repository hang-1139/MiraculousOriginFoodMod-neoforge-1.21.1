package com.hang.miraculousori.potion;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.effect.ModMobEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(Registries.POTION, MiraculousOriginFoodMod.MODID);

    // ===== 基础药水（等级 I，持续 30 秒） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_POTION =
            POTIONS.register("invigorate",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 30, 0)));

    // ===== 延时药水（等级 I，持续 60 秒，红石延长一次） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_LONG =
            POTIONS.register("invigorate_long",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 60, 0)));

    // ===== 延时药水 II（等级 I，持续 90 秒，延时两次） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_LONGER =
            POTIONS.register("invigorate_longer",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 90, 0)));

    // ===== 延时药水 III（等级 I，持续 120 秒，延时三次） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_LONGEST =
            POTIONS.register("invigorate_longest",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 120, 0)));

    // ===== 升级药水（等级 II，持续 30 秒，荧石粉升级一次） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_STRONG =
            POTIONS.register("invigorate_strong",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 30, 1)));

    // ===== 升级药水 II（等级 III，持续 30 秒，升级两次） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_STRONGER =
            POTIONS.register("invigorate_stronger",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 30, 2)));

    // ===== 升级药水 III（等级 IV，持续 30 秒，升级三次） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_STRONGEST =
            POTIONS.register("invigorate_strongest",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 30, 3)));

    // ===== 延时 + 升级组合药水（等级 II，持续 60 秒） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_LONG_STRONG =
            POTIONS.register("invigorate_long_strong",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 60, 1)));

    // ===== 延时 + 升级组合药水 II（等级 III，持续 90 秒） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_LONGER_STRONGER =
            POTIONS.register("invigorate_longer_stronger",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 90, 2)));

    // ===== 延时 + 升级组合药水 III（等级 IV，持续 120 秒，最大组合） =====
    public static final DeferredHolder<Potion, Potion> INVIGORATE_LONGEST_STRONGEST =
            POTIONS.register("invigorate_longest_strongest",
                    () -> new Potion(new MobEffectInstance(ModMobEffects.INVIGORATE, 20 * 120, 3)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}