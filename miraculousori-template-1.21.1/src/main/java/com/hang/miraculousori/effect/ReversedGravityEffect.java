package com.hang.miraculousori.effect;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ReversedGravityEffect extends MobEffect {

    private static final ResourceLocation GRAVITY_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "reversed_gravity");

    public ReversedGravityEffect(MobEffectCategory category, int color) {
        super(category, color);
        // 将重力属性设为负值：0.08 + (-0.16) = -0.08
        this.addAttributeModifier(
                Attributes.GRAVITY,
                GRAVITY_MODIFIER_ID,
                -0.16, // 基值
                AttributeModifier.Operation.ADD_VALUE
        );
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true; // 每 tick 维持属性
    }
}