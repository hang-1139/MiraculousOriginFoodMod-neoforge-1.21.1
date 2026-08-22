package com.hang.miraculousori.effect;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class HungerAfflictionEffect extends MobEffect {

    public HungerAfflictionEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true; // 保持效果激活
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // 逻辑由事件处理，这里无需操作
        return true;
    }

    /**
     * 监听 Pre 事件，在伤害计算完成但未实际扣血前修改伤害值。
     */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        // 仅服务端执行
        if (event.getEntity().level().isClientSide) {
            return;
        }

        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        if (!(attacker instanceof Player player)) {
            return;
        }

        // 检查玩家是否拥有此效果
        MobEffectInstance effectInstance = player.getEffect(ModMobEffects.HUNGER_AFFLICTION);
        if (effectInstance == null) {
            return;
        }

        int amplifier = effectInstance.getAmplifier();
        int level = amplifier + 1;                     // 等级 I → 1, II → 2, ...
        float saturation = player.getFoodData().getSaturationLevel();
        float missing = 20.0F - saturation;
        if (missing <= 0) {
            return; // 饱食已满，无加成
        }

        float bonus = missing * level;
        if (bonus > 0) {
            float currentDamage = event.getNewDamage();  // 获取当前最终伤害值
            event.setNewDamage(currentDamage + bonus);   // 增加伤害
        }
    }
}