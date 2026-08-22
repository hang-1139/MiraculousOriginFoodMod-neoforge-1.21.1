package com.hang.miraculousori.particle;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class DashManager {

    // NBT 键
    private static final String KEY_DASH_STEPS = "dash_steps_remaining";
    private static final String KEY_DASH_STEP_X = "dash_step_x";
    private static final String KEY_DASH_STEP_Y = "dash_step_y";
    private static final String KEY_DASH_STEP_Z = "dash_step_z";
    private static final String KEY_DASH_PARTICLES_PER_POINT = "dash_particles_per_point";

    private static final int TOTAL_STEPS = 4; // 0.2 秒，4 tick

    public static void startDash(ServerPlayer player, double distance, int particlesPerPoint) {
        if (distance <= 0) {
            return;
        }

        Vec3 lookVec = player.getLookAngle();
        Vec3 limitedLook = limitPitch(lookVec);
        Vec3 step = limitedLook.scale(distance / TOTAL_STEPS);

        var data = player.getPersistentData();
        data.putInt(KEY_DASH_STEPS, TOTAL_STEPS);
        data.putDouble(KEY_DASH_STEP_X, step.x);
        data.putDouble(KEY_DASH_STEP_Y, step.y);
        data.putDouble(KEY_DASH_STEP_Z, step.z);
        data.putInt(KEY_DASH_PARTICLES_PER_POINT, particlesPerPoint);
    }

    private static Vec3 limitPitch(Vec3 lookVec) {
        double xzLength = Math.sqrt(lookVec.x * lookVec.x + lookVec.z * lookVec.z);
        if (xzLength < 1e-6) {
            return new Vec3(1, 0, 0);
        }
        double pitchRad = Math.atan2(lookVec.y, xzLength);
        double maxPitchRad = Math.toRadians(30);
        double newY;
        if (pitchRad > maxPitchRad) {
            newY = xzLength * Math.tan(maxPitchRad);
        } else if (pitchRad < 0) {
            newY = 0;
        } else {
            newY = lookVec.y;
        }
        double newX = lookVec.x;
        double newZ = lookVec.z;
        double length = Math.sqrt(newX * newX + newY * newY + newZ * newZ);
        if (length < 1e-6) {
            return new Vec3(1, 0, 0);
        }
        return new Vec3(newX / length, newY / length, newZ / length);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide || !player.isAlive()) {
            return;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        var data = player.getPersistentData();
        if (!data.contains(KEY_DASH_STEPS)) {
            return;
        }

        int stepsLeft = data.getInt(KEY_DASH_STEPS);
        if (stepsLeft <= 0) {
            clearDashData(data);
            return;
        }

        double stepX = data.getDouble(KEY_DASH_STEP_X);
        double stepY = data.getDouble(KEY_DASH_STEP_Y);
        double stepZ = data.getDouble(KEY_DASH_STEP_Z);
        int particlesPerPoint = data.getInt(KEY_DASH_PARTICLES_PER_POINT);

        // 记录移动前位置
        Vec3 startPos = serverPlayer.position();

        double newX = serverPlayer.getX() + stepX;
        double newY = serverPlayer.getY() + stepY;
        double newZ = serverPlayer.getZ() + stepZ;

        double oldX = serverPlayer.getX();
        double oldY = serverPlayer.getY();
        double oldZ = serverPlayer.getZ();

        // 尝试移动
        serverPlayer.teleportTo(newX, newY, newZ);
        serverPlayer.syncPacketPositionCodec(newX, newY, newZ);

        if (serverPlayer.isInWall()) {
            // 卡墙回退
            serverPlayer.teleportTo(oldX, oldY, oldZ);
            serverPlayer.syncPacketPositionCodec(oldX, oldY, oldZ);
            clearDashData(data);
            return;
        }

        // 移动成功后，记录终点位置
        Vec3 endPos = serverPlayer.position();

        // 生成路径粒子（沿线段每格采样）
        TrailManager.spawnLineParticles(serverPlayer, startPos, endPos, particlesPerPoint);

        // 更新剩余步数
        stepsLeft--;
        if (stepsLeft <= 0) {
            clearDashData(data);
        } else {
            data.putInt(KEY_DASH_STEPS, stepsLeft);
        }
    }

    private static void clearDashData(net.minecraft.nbt.CompoundTag data) {
        data.remove(KEY_DASH_STEPS);
        data.remove(KEY_DASH_STEP_X);
        data.remove(KEY_DASH_STEP_Y);
        data.remove(KEY_DASH_STEP_Z);
        data.remove(KEY_DASH_PARTICLES_PER_POINT);
    }
}