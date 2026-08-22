package com.hang.miraculousori.item;

import com.hang.miraculousori.component.ModDataComponents;
import com.hang.miraculousori.component.PleasureComponent;
import com.hang.miraculousori.effect.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VerySweetBreadItem extends Item {
    public VerySweetBreadItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        // 1. 使用 .get() 获取 DataComponentType
        PleasureComponent pleasure = stack.get(ModDataComponents.PLEASURE_DATA.get());
        if (pleasure != null && entity != null) {
            // 2. 使用 ModMobEffects.PLEASURE（DeferredHolder）作为 Holder
            entity.addEffect(new MobEffectInstance(ModMobEffects.PLEASURE, pleasure.duration(), pleasure.amplifier()));
        }
        return result;
    }
}