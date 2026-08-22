package com.hang.miraculousori.advancement;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, MiraculousOriginFoodMod.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, MillerGrindTrigger> MILLER_GRIND =
            TRIGGERS.register("miller_grind", MillerGrindTrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, HoldingBothTrigger> HOLDING_BOTH =
            TRIGGERS.register("holding_both", HoldingBothTrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, FloatMelonTrigger> FLOAT_MELON =
            TRIGGERS.register("float_melon", FloatMelonTrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, ReversedGravityTrigger> REVERSED_GRAVITY =
            TRIGGERS.register("reversed_gravity", ReversedGravityTrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, PressResidueTrigger> PRESS_RESIDUE =
            TRIGGERS.register("press_residue", PressResidueTrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, LeapingFruitActivateTrigger> LEAPING_FRUIT_ACTIVATE =
            TRIGGERS.register("leaping_fruit_activate", LeapingFruitActivateTrigger::new);
}