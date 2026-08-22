package com.hang.miraculousori.effect;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Random;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class TeleportOnDamageEffect extends MobEffect {

    private static final int COOLDOWN_TICKS = 10;
    private static final int MAX_ATTEMPTS = 64;
    private static final Random RANDOM = new Random();

    public TeleportOnDamageEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        Level level = event.getEntity().level();
        if (level.isClientSide()) {
            return;
        }

        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        MobEffectInstance effect = player.getEffect(ModMobEffects.TELEPORT_ON_DAMAGE);
        if (effect == null) {
            return;
        }

        long lastTeleport = player.getPersistentData().getLong("teleport_cooldown");
        long currentTick = level.getGameTime();
        if (currentTick - lastTeleport < COOLDOWN_TICKS) {
            return;
        }

        int amplifier = effect.getAmplifier();
        int levelModifier = amplifier + 1;
        int radius = 15 * levelModifier;

        if (tryTeleport(player, radius)) {
            player.getPersistentData().putLong("teleport_cooldown", currentTick);
        }
    }

    private static boolean tryTeleport(ServerPlayer player, int radius) {
        Level level = player.level();
        Vec3 oldPos = player.position();

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            double angle = RANDOM.nextDouble() * 2 * Math.PI;
            double distance = RANDOM.nextDouble() * radius;
            double dx = Math.cos(angle) * distance;
            double dz = Math.sin(angle) * distance;
            double dy = (RANDOM.nextDouble() - 0.5) * 2 * radius;

            double newX = oldPos.x() + dx;
            double newY = oldPos.y() + dy;
            double newZ = oldPos.z() + dz;

            BlockPos targetPos = BlockPos.containing(newX, newY, newZ);

            if (isSafePosition(level, targetPos, player)) {
                // 传送
                player.teleportTo(newX, newY, newZ);
                // 音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

                // 粒子效果（原位置 + 新位置）
                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 32; i++) {
                        double particleX = oldPos.x() + (RANDOM.nextDouble() - 0.5) * 2.0;
                        double particleY = oldPos.y() + RANDOM.nextDouble() * 2.0;
                        double particleZ = oldPos.z() + (RANDOM.nextDouble() - 0.5) * 2.0;
                        serverLevel.sendParticles(ParticleTypes.PORTAL,
                                particleX, particleY, particleZ,
                                1, 0.0, 0.0, 0.0, 0.0);
                    }
                    for (int i = 0; i < 32; i++) {
                        double particleX = newX + (RANDOM.nextDouble() - 0.5) * 2.0;
                        double particleY = newY + RANDOM.nextDouble() * 2.0;
                        double particleZ = newZ + (RANDOM.nextDouble() - 0.5) * 2.0;
                        serverLevel.sendParticles(ParticleTypes.PORTAL,
                                particleX, particleY, particleZ,
                                1, 0.0, 0.0, 0.0, 0.0);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 检查目标位置是否安全（不卡在方块内，下方有固体方块，不是液体）
     */
    private static boolean isSafePosition(Level level, BlockPos pos, LivingEntity entity) {
        // 检查下方方块是否为固体（可站立）
        BlockPos below = pos.below();
        if (!level.getBlockState(below).isSolid()) {
            return false;
        }

        // 构建玩家碰撞箱（宽 0.6，高 1.8，居中于方块中心）
        double width = 0.6;
        double height = 1.8;
        double x = pos.getX() + 0.5 - width / 2.0;
        double y = pos.getY();
        double z = pos.getZ() + 0.5 - width / 2.0;
        AABB aabb = new AABB(x, y, z, x + width, y + height, z + width);

        // 检查碰撞（使用 Shapes.create 将 AABB 转为 VoxelShape）
        if (!level.isUnobstructed(entity, Shapes.create(aabb))) {
            return false;
        }

        // 检查位置是否在液体中（避免传送到水里或岩浆）
        if (level.getFluidState(pos).isSource()) {
            return false;
        }

        return true;
    }
}