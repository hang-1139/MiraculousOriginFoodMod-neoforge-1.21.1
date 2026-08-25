package com.hang.miraculousori.effect;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
        int radius = 20 + amplifier * 5;

        if (tryTeleport(player, radius, event.getSource())) {
            player.getPersistentData().putLong("teleport_cooldown", currentTick);
        }
    }

    private static boolean tryTeleport(ServerPlayer player, int radius, net.minecraft.world.damagesource.DamageSource source) {
        Level level = player.level();
        Vec3 oldPos = player.position();

        // ----- 0. 虚空掉落：只要 y < -64，立即执行垂直搜索（忽略伤害类型） -----
        if (player.getY() < -64) {
            BlockPos target = findVerticalSafeGround(player.blockPosition(), player, level);
            if (target != null) {
                return teleportTo(player, target, oldPos, level);
            }
            // 若当前列无安全点，则随机微调 X/Z 1~5 格，最多尝试 20 次
            for (int attempt = 0; attempt < 20; attempt++) {
                int dx = RANDOM.nextInt(5) + 1;
                int dz = RANDOM.nextInt(5) + 1;
                if (RANDOM.nextBoolean()) dx = -dx;
                if (RANDOM.nextBoolean()) dz = -dz;
                BlockPos adjusted = player.blockPosition().offset(dx, 0, dz);
                BlockPos target2 = findVerticalSafeGround(adjusted, player, level);
                if (target2 != null) {
                    return teleportTo(player, target2, oldPos, level);
                }
            }
            return false;
        }

        // ----- 1. 火焰伤害：优先找水 -----
        if (source.is(DamageTypeTags.IS_FIRE)) {
            BlockPos waterPos = findNearestOpenWater(player, radius);
            if (waterPos != null) {
                return teleportTo(player, waterPos, oldPos, level);
            }
            // 无水则继续
        }

        // ----- 2. 摔落伤害（非虚空）：不触发传送 -----
        if (source.is(DamageTypeTags.IS_FALL)) {
            return false;
        }

        // ----- 3. 其他伤害类型：普通瞬移 -----
        BlockPos target = findSafeGround(player, radius);
        if (target != null) {
            return teleportTo(player, target, oldPos, level);
        }
        return false;
    }

    // ========== 垂直搜索：在给定 X/Z 列向上寻找第一个安全方块 ==========

    private static BlockPos findVerticalSafeGround(BlockPos start, ServerPlayer player, Level level) {
        int x = start.getX();
        int z = start.getZ();
        int y = (int) Math.round(player.getY());
        int maxY = level.getMaxBuildHeight() - 1;
        for (int i = y; i < maxY; i++) {
            BlockPos check = new BlockPos(x, i, z);
            BlockState state = level.getBlockState(check);
            if (isSafeBlock(state, level, check)) {
                BlockPos above = check.above();
                if (level.getBlockState(above).isAir()) {
                    if (isPositionSafe(level, above, player)) {
                        return above;
                    }
                }
            }
        }
        return null;
    }

    // ========== 普通瞬移：随机水平 + 寻找地面 ==========

    private static BlockPos findSafeGround(ServerPlayer player, int radius) {
        Level level = player.level();
        Vec3 center = player.position();
        int startY = (int) Math.round(center.y);

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            double angle = RANDOM.nextDouble() * 2 * Math.PI;
            double distance = RANDOM.nextDouble() * radius;
            int dx = (int) Math.round(Math.cos(angle) * distance);
            int dz = (int) Math.round(Math.sin(angle) * distance);

            int targetX = (int) Math.round(center.x()) + dx;
            int targetZ = (int) Math.round(center.z()) + dz;

            BlockPos pos = new BlockPos(targetX, startY, targetZ);
            BlockState state = level.getBlockState(pos);
            if (state.isAir()) {
                for (int y = startY - 1; y >= level.getMinBuildHeight(); y--) {
                    BlockPos check = new BlockPos(targetX, y, targetZ);
                    BlockState belowState = level.getBlockState(check);
                    if (!belowState.isAir()) {
                        if (isSafeBlock(belowState, level, check)) {
                            BlockPos above = check.above();
                            if (level.getBlockState(above).isAir() && isPositionSafe(level, above, player)) {
                                return above;
                            }
                        }
                        break;
                    }
                }
            } else {
                for (int y = startY + 1; y < level.getMaxBuildHeight(); y++) {
                    BlockPos check = new BlockPos(targetX, y, targetZ);
                    BlockState aboveState = level.getBlockState(check);
                    if (aboveState.isAir()) {
                        BlockPos below = check.below();
                        BlockState belowState = level.getBlockState(below);
                        if (isSafeBlock(belowState, level, below) && isPositionSafe(level, check, player)) {
                            return check;
                        }
                        break;
                    }
                }
            }

            int adjustX = RANDOM.nextInt(3) + 1;
            int adjustZ = RANDOM.nextInt(3) + 1;
            if (RANDOM.nextBoolean()) adjustX = -adjustX;
            if (RANDOM.nextBoolean()) adjustZ = -adjustZ;
            targetX += adjustX;
            targetZ += adjustZ;
        }
        return null;
    }

    // ========== 火焰伤害：找最近露天水源 ==========

    private static BlockPos findNearestOpenWater(ServerPlayer player, int radius) {
        Level level = player.level();
        BlockPos center = player.blockPosition();
        double minDistSq = Double.MAX_VALUE;
        BlockPos best = null;

        for (int i = 0; i < 100; i++) {
            int dx = RANDOM.nextInt(radius * 2 + 1) - radius;
            int dz = RANDOM.nextInt(radius * 2 + 1) - radius;
            int dy = RANDOM.nextInt(10) - 5;
            BlockPos pos = center.offset(dx, dy, dz);
            if (level.getBlockState(pos).getBlock() == Blocks.WATER) {
                if (level.getBlockState(pos.above()).isAir()) {
                    double distSq = center.distSqr(pos);
                    if (distSq < minDistSq) {
                        minDistSq = distSq;
                        best = pos;
                    }
                }
            }
        }
        return best != null ? best.above() : null;
    }

    // ========== 安全方块判定 ==========

    private static boolean isSafeBlock(BlockState state, Level level, BlockPos pos) {
        if (state.getBlock() == Blocks.FIRE) return false;
        if (state.getBlock() == Blocks.SOUL_FIRE) return false;
        if (state.getBlock() == Blocks.LAVA) return false;
        if (state.getBlock() == Blocks.MAGMA_BLOCK) return false;
        if (state.getBlock() == Blocks.POWDER_SNOW) return false;
        if (state.getBlock() == Blocks.WATER) return false;
        if (state.getBlock() == Blocks.CAMPFIRE) return false;
        if (state.getBlock() == Blocks.SOUL_CAMPFIRE) return false;
        if (state.getBlock() == Blocks.SOUL_SAND) return false;
        return state.isSolid();
    }

    // ========== 碰撞箱安全 ==========

    private static boolean isPositionSafe(Level level, BlockPos pos, LivingEntity entity) {
        double width = 0.6;
        double height = 1.8;
        double x = pos.getX() + 0.5 - width / 2.0;
        double y = pos.getY();
        double z = pos.getZ() + 0.5 - width / 2.0;
        AABB aabb = new AABB(x, y, z, x + width, y + height, z + width);
        return level.isUnobstructed(entity, Shapes.create(aabb)) && !level.getFluidState(pos).isSource();
    }

    // ========== 执行传送 ==========

    private static boolean teleportTo(ServerPlayer player, BlockPos targetPos, Vec3 oldPos, Level level) {
        double x = targetPos.getX() + 0.5;
        double y = targetPos.getY();
        double z = targetPos.getZ() + 0.5;
        player.teleportTo(x, y, z);
        level.playSound(null, x, y, z, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 32; i++) {
                double px = oldPos.x() + (RANDOM.nextDouble() - 0.5) * 2.0;
                double py = oldPos.y() + RANDOM.nextDouble() * 2.0;
                double pz = oldPos.z() + (RANDOM.nextDouble() - 0.5) * 2.0;
                serverLevel.sendParticles(ParticleTypes.PORTAL, px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
            }
            for (int i = 0; i < 32; i++) {
                double px = x + (RANDOM.nextDouble() - 0.5) * 2.0;
                double py = y + RANDOM.nextDouble() * 2.0;
                double pz = z + (RANDOM.nextDouble() - 0.5) * 2.0;
                serverLevel.sendParticles(ParticleTypes.PORTAL, px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
            }
        }
        return true;
    }
}