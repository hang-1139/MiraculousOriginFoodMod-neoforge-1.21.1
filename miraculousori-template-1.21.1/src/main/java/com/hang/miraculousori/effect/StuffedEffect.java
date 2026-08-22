package com.hang.miraculousori.effect;

import com.hang.miraculousori.damage.ModDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StuffedEffect extends MobEffect {

    public StuffedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            if (player.isUsingItem()) {
                ItemStack useItem = player.getUseItem();
                boolean isEdible = !useItem.isEmpty()
                        && useItem.getItem().getFoodProperties(useItem, player) != null;
                if (isEdible) {
                    float damage = 1.0F * (amplifier + 1);
                    DamageSource bloatSource = ModDamageTypes.bloatDamage(player.level());
                    player.hurt(bloatSource, damage);
                }
            }
        }
        return true;
    }
}