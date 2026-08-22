package com.hang.miraculousori.effect;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class PleasureEffect extends MobEffect {

    private static final ResourceLocation SPEED_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "pleasure_speed");
    private static final ResourceLocation DAMAGE_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "pleasure_damage");

    public PleasureEffect(MobEffectCategory category, int color) {
        super(category, color);

        // 速度加成：每级 +20% 移动速度 (等级 I: +20%, II: +40%, ...)
        Int2DoubleFunction speedCurve = amplifier -> 0.2 * (amplifier + 1);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_ID,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, speedCurve);

        // 攻击力加成：每级 +3 点近战伤害 (等级 I: +3, II: +6, ...)
        Int2DoubleFunction damageCurve = amplifier -> 3.0 * (amplifier + 1);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_ID,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE, damageCurve);
    }
}