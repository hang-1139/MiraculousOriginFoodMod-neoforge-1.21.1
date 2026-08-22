package com.hang.miraculousori.particle;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Random;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class TrailManager {

    private static final Random RANDOM = new Random();

    private static final int COLOR_1 = 0xFFF9E921;
    private static final int COLOR_2 = 0xFFf9f35f;
    private static final int COLOR_3 = 0xFFf9f6c0;

    private static final Vector3f COLOR_VEC_1 = intToVec3f(COLOR_1);
    private static final Vector3f COLOR_VEC_2 = intToVec3f(COLOR_2);
    private static final Vector3f COLOR_VEC_3 = intToVec3f(COLOR_3);

    private static final double COLOR_1_WEIGHT = 0.5;
    private static final double COLOR_2_WEIGHT = 0.3;
    private static final double COLOR_3_WEIGHT = 0.2;

    // 拖尾功能
    private static final String KEY_DURATION = "trail_duration";
    private static final String KEY_COUNT = "trail_count";
    private static final String KEY_CURRENT_TICK = "trail_current_tick";

    // ===== 拖尾 API（保留） =====
    public static void startTrail(@NotNull ServerPlayer player, int durationTicks, int countPerTick) {
        if (durationTicks <= 0 || countPerTick <= 0) return;
        var data = player.getPersistentData();
        data.putInt(KEY_DURATION, durationTicks);
        data.putInt(KEY_COUNT, countPerTick);
        data.putInt(KEY_CURRENT_TICK, 0);
    }

    public static void startTestTrail(@NotNull ServerPlayer player) {
        startTrail(player, 300, 3);
    }

    public static void stopTrail(@NotNull Player player) {
        var data = player.getPersistentData();
        data.remove(KEY_DURATION);
        data.remove(KEY_COUNT);
        data.remove(KEY_CURRENT_TICK);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide || !player.isAlive()) return;
        var data = player.getPersistentData();
        if (!data.contains(KEY_DURATION)) return;
        int remaining = data.getInt(KEY_DURATION);
        if (remaining <= 0) {
            data.remove(KEY_DURATION);
            data.remove(KEY_COUNT);
            data.remove(KEY_CURRENT_TICK);
            return;
        }
        int count = data.getInt(KEY_COUNT);
        if (player instanceof ServerPlayer serverPlayer) {
            spawnTrailParticles(serverPlayer, count);
        }
        int current = data.getInt(KEY_CURRENT_TICK) + 1;
        if (current >= remaining) {
            data.remove(KEY_DURATION);
            data.remove(KEY_COUNT);
            data.remove(KEY_CURRENT_TICK);
        } else {
            data.putInt(KEY_CURRENT_TICK, current);
        }
    }

    // ===== 沿线段每格生成粒子 =====
//    public static void spawnLineParticles(@NotNull ServerPlayer player, Vec3 start, Vec3 end, int countPerPoint) {
//        if (countPerPoint <= 0) return;
//        ServerLevel level = player.serverLevel();
//        Vec3 dir = end.subtract(start);
//        double length = dir.length();
//        if (length < 1e-6) return;
//        Vec3 step = dir.normalize();
//        // 采样点数：按每格1个，至少1个
//        int points = (int) Math.ceil(length);
//        // 如果长度小于1，至少生成起点和终点？这里只生成整数格位置，起点和终点可能不刚好在整数格，但为了效果，我们沿直线均匀采样 points 个点（包括起点和终点）
//        // 更精确：从起点到终点，每格一个点。我们按距离从0到length，步长1.0，但起点不一定在整数格，所以直接按比例采样 points 个点。
//        // 简单做法：按 points 个点均匀分布，包括起点和终点。
//        for (int i = 0; i < points; i++) {
//            double t = (double) i / (points - 1);
//            Vec3 pos = start.add(step.scale(t * length));
//            spawnTrailParticlesAt(level, pos, countPerPoint);
//        }
//        // 确保终点也被包括（如果 points=1 则起点=终点，但长度>0，所以至少2点）
//        // 如果 length < 1，points=1，则只生成起点（但也会生成终点因为 i=0 就是起点，但我们希望终点也生成）
//        // 改进：使用循环从0到length，步长1.0，最后加上终点。
//    }

    // 更准确的实现：按每格距离采样，并包含终点
    public static void spawnLineParticles2(@NotNull ServerPlayer player, Vec3 start, Vec3 end, int countPerPoint) {
        if (countPerPoint <= 0) return;
        ServerLevel level = player.serverLevel();
        Vec3 dir = end.subtract(start);
        double length = dir.length();
        if (length < 1e-6) return;
        Vec3 step = dir.scale(1.0 / length); // 单位方向
        // 从0到length，步长1.0，生成点
        double d = 0.0;
        while (d < length) {
            Vec3 pos = start.add(step.scale(d));
            spawnTrailParticlesAt(level, pos, countPerPoint);
            d += 1.0;
        }
        // 确保终点被生成（如果终点不是整数格，上一个循环不会到终点，所以强制生成终点）
        spawnTrailParticlesAt(level, end, countPerPoint);
    }

    // 使用更易读的实现
    public static void spawnLineParticles(@NotNull ServerPlayer player, Vec3 start, Vec3 end, int countPerPoint) {
        if (countPerPoint <= 0) return;
        ServerLevel level = player.serverLevel();
        Vec3 delta = end.subtract(start);
        double length = delta.length();
        if (length < 0.01) return;
        Vec3 unit = delta.scale(1.0 / length);
        // 从起点开始，每1.0格采样一个点，最后强制包含终点
        for (double d = 0; d < length; d += 1.0) {
            Vec3 pos = start.add(unit.scale(d));
            spawnTrailParticlesAt(level, pos, countPerPoint);
        }
        // 生成终点
        spawnTrailParticlesAt(level, end, countPerPoint);
    }

    // 在单个位置生成 count 个粒子（颜色随机，大小固定测试值）
    private static void spawnTrailParticlesAt(ServerLevel level, Vec3 pos, int count) {
        float scale = 10f; // 测试大小，后续可调整
        for (int i = 0; i < count; i++) {
            Vector3f color = pickColorVec();
            DustParticleOptions options = new DustParticleOptions(color, scale);
            // 随机偏移
            double spread = 0.5;
            double xOff = (RANDOM.nextDouble() - 0.5) * spread;
            double yOff = (RANDOM.nextDouble() - 0.5) * spread;
            double zOff = (RANDOM.nextDouble() - 0.5) * spread;
            level.sendParticles(options,
                    pos.x + xOff, pos.y + yOff, pos.z + zOff,
                    1, 0, 0, 0, 0);
        }
    }

    // 单点生成
    public static void spawnParticles(@NotNull ServerPlayer player, int count) {
        spawnTrailParticles(player, count);
    }

    private static void spawnTrailParticles(ServerPlayer player, int count) {
        ServerLevel level = player.serverLevel();
        Vec3 pos = player.position();
        double height = player.getBbHeight();
        double minY = pos.y();
        double maxY = pos.y() + height;
        float scale = 0.1f;
        for (int i = 0; i < count; i++) {
            double y = minY + RANDOM.nextDouble() * height;
            double xOffset = (RANDOM.nextDouble() - 0.5) * 0.5;
            double zOffset = (RANDOM.nextDouble() - 0.5) * 0.5;
            double x = pos.x() + xOffset;
            double z = pos.z() + zOffset;
            Vector3f color = pickColorVec();
            DustParticleOptions options = new DustParticleOptions(color, scale);
            level.sendParticles(options, x, y, z, 1, 0, 0, 0, 0);
        }
    }

    private static Vector3f pickColorVec() {
        double rand = RANDOM.nextDouble();
        if (rand < COLOR_1_WEIGHT) return COLOR_VEC_1;
        else if (rand < COLOR_1_WEIGHT + COLOR_2_WEIGHT) return COLOR_VEC_2;
        else return COLOR_VEC_3;
    }

    private static Vector3f intToVec3f(int color) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        return new Vector3f(r, g, b);
    }
}