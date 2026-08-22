package com.hang.miraculousori.effect;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class InvigorateEffect extends MobEffect {

    private static final ResourceLocation JUMP_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "invigorate_jump");

    public InvigorateEffect(MobEffectCategory category, int color) {
        super(category, color);

        // 跳跃高度提升：每级 +0.1 格（基础 0.42 + 0.1 * level）
        // 使用 ADD_VALUE 直接增加属性值
        this.addAttributeModifier(
                Attributes.JUMP_STRENGTH,
                JUMP_MODIFIER_ID,
                AttributeModifier.Operation.ADD_VALUE,
                amplifier -> 0.1 * (amplifier + 1)  // level = amplifier + 1
        );
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // 效果不需要每 tick 执行逻辑（属性修饰符自动生效，事件监听处理其他）
        return false;
    }

    /**
     * 监听伤害结算前事件，增加近战伤害（仅限于玩家近战攻击）
     */
    @SubscribeEvent
    public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
        // 仅服务端执行
        if (event.getEntity().level().isClientSide) {
            return;
        }

        DamageSource source = event.getSource();
        // 判断直接攻击实体是否为玩家（排除弓箭、魔法等间接攻击）
        if (!(source.getDirectEntity() instanceof Player attacker)) {
            return;
        }

        // 检查攻击者是否拥有此效果
        MobEffectInstance effectInstance = attacker.getEffect(ModMobEffects.INVIGORATE);
        if (effectInstance == null) {
            return;
        }

        int amplifier = effectInstance.getAmplifier();
        int level = amplifier + 1; // 等级 I → 1, II → 2, ...
        if (level <= 1) {
            return; // 等级 I 无伤害加成
        }

        // 额外伤害 = 2 * (等级 - 1)
        float bonus = 2.0F * (level - 1);
        if (bonus > 0) {
            event.setNewDamage(event.getNewDamage() + bonus);
        }
    }

    /**
     * 监听摔落事件，减少等效摔落高度（类似跳跃提升效果）
     */
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        // 仅服务端执行
        if (entity.level().isClientSide) {
            return;
        }

        if (!(entity instanceof Player player)) {
            return;
        }

        // 检查玩家是否拥有此效果
        MobEffectInstance effectInstance = player.getEffect(ModMobEffects.INVIGORATE);
        if (effectInstance == null) {
            return;
        }

        int amplifier = effectInstance.getAmplifier();
        int level = amplifier + 1; // 等级 I → 1, II → 2, ...

        // 将摔落距离减少 level 格（最小为0）
        float newDistance = event.getDistance() - level;
        if (newDistance < 0) {
            newDistance = 0;
        }
        event.setDistance(newDistance);
    }
}