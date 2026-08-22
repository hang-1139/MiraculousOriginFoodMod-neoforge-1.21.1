package com.hang.miraculousori.effect;

import com.hang.miraculousori.particle.DashManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DashEffect extends MobEffect {

    public DashEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }
        if (player.level().isClientSide) {
            return;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        // 距离 = 3 + 3 * amplifier （等级 I: 3, II: 6, III: 9 ...）
        double distance = 3.0 + 3.0 * amplifier;
        // 每格粒子数固定为 3
        DashManager.startDash(serverPlayer, distance, 3);
    }
}