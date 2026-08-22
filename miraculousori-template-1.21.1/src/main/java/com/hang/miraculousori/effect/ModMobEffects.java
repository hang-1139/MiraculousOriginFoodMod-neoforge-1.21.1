package com.hang.miraculousori.effect;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, MiraculousOriginFoodMod.MODID);

    // 过饱腹（有益，颜色金黄）
    public static final DeferredHolder<MobEffect, OverfullEffect> OVERFULL =
            MOB_EFFECTS.register("overfull",
                    () -> new OverfullEffect(MobEffectCategory.BENEFICIAL, 0xFFD700));

    // 饥饿附伤（有害，颜色深红）
    public static final DeferredHolder<MobEffect, HungerAfflictionEffect> HUNGER_AFFLICTION =
            MOB_EFFECTS.register("hunger_affliction",
                    () -> new HungerAfflictionEffect(MobEffectCategory.BENEFICIAL, 0x8B0000));

    // 吃撑了（有害，颜色暗紫）
    public static final DeferredHolder<MobEffect, StuffedEffect> STUFFED =
            MOB_EFFECTS.register("stuffed",
                    () -> new StuffedEffect(MobEffectCategory.HARMFUL, 0x9C5820));

    // 饱食溶解（有害，颜色灰绿）
    public static final DeferredHolder<MobEffect, SaturationDecayEffect> SATURATION_DECAY =
            MOB_EFFECTS.register("saturation_decay",
                    () -> new SaturationDecayEffect(MobEffectCategory.HARMFUL, 0x808080));

    // 愉悦（有益，颜色粉色）
    public static final DeferredHolder<MobEffect, PleasureEffect> PLEASURE =
            MOB_EFFECTS.register("pleasure",
                    () -> new PleasureEffect(MobEffectCategory.BENEFICIAL, 0xFF69B4));

    // 伤害导致的传送（中性，颜色紫色）
    public static final DeferredHolder<MobEffect, TeleportOnDamageEffect> TELEPORT_ON_DAMAGE =
            MOB_EFFECTS.register("teleport_on_damage",
                    () -> new TeleportOnDamageEffect(MobEffectCategory.BENEFICIAL, 0x9B59B6)); // 紫色

    //  振奋（有益，颜色橙黄）
    public static final DeferredHolder<MobEffect, InvigorateEffect> INVIGORATE =
            MOB_EFFECTS.register("invigorate",
                    () -> new InvigorateEffect(MobEffectCategory.BENEFICIAL, 0xFFAA00));

    // 跃进（瞬发冲刺，颜色亮蓝）
    public static final DeferredHolder<MobEffect, DashEffect> DASH =
            MOB_EFFECTS.register("dash",
                    () -> new DashEffect(MobEffectCategory.BENEFICIAL, 0xf9ff80));

    // 劳动（中性，颜色灰蓝）
    public static final DeferredHolder<MobEffect, WorkingEffect> WORKING =
            MOB_EFFECTS.register("working",
                    () -> new WorkingEffect(MobEffectCategory.NEUTRAL, 0x888888));

    //  反重力（中性，颜色紫色）
    public static final DeferredHolder<MobEffect, ReversedGravityEffect> REVERSED_GRAVITY =
            MOB_EFFECTS.register("reversed_gravity",
                    () -> new ReversedGravityEffect(MobEffectCategory.NEUTRAL, 0x8A2BE2)); // 紫色
}