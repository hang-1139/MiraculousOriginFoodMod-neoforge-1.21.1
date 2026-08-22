package com.hang.miraculousori.particle;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Vector3f;

import java.util.Random;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModParticleEffects {

    private static final Random RANDOM = new Random();

    // ===== 食神之佑颜色 =====
    private static final Vector3f COLOR_GOD_BLESSING_1 = intToVec3f(0xFFB51F); // 橙黄
    private static final Vector3f COLOR_GOD_BLESSING_2 = intToVec3f(0xFFFFFF); // 白色

    // ===== 饥馑诅颂颜色 =====
    private static final Vector3f COLOR_CURSE_ODE_1 = intToVec3f(0x7F8583);    // 灰绿
    private static final Vector3f COLOR_CURSE_ODE_2 = intToVec3f(0x740001);    // 深红

    // ===== END_NEW_PATH 颜色 =====
    private static final Vector3f COLOR_END_PATH_1 = intToVec3f(0xB526DE);     // 紫色
    private static final Vector3f COLOR_END_PATH_2 = intToVec3f(0xEEEBB7);     // 米白

    // 粒子大小（0.01 = 1 像素，0.5 = 50 像素）
    private static final float PARTICLE_SCALE = 0.45f;

    // 环绕半径（格）
    private static final double RADIUS = 0.8;

    // 每 tick 生成粒子数
    private static final int PARTICLE_COUNT = 10;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        // 检查主手和副手
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        // ---- 食神之佑 ----
        boolean hasBlessing = mainHand.is(ModItems.FOOD_GOD_BLESSING_AMULET.get()) ||
                offHand.is(ModItems.FOOD_GOD_BLESSING_AMULET.get());

        // ---- 饥馑诅颂 ----
        boolean hasCurse = mainHand.is(ModItems.HUNGER_CURSE_ODE.get()) ||
                offHand.is(ModItems.HUNGER_CURSE_ODE.get());
        // ---- 终幕新途 ----
        boolean hasEndPath = mainHand.is(ModItems.END_NEW_PATH.get()) ||
                offHand.is(ModItems.END_NEW_PATH.get());

        // 生成对应的粒子效果
        if (hasBlessing) {
            spawnParticles(serverPlayer, COLOR_GOD_BLESSING_1, COLOR_GOD_BLESSING_2);
        }

        if (hasCurse) {
            spawnParticles(serverPlayer, COLOR_CURSE_ODE_1, COLOR_CURSE_ODE_2);
        }

        if (hasEndPath) {
            spawnParticles(serverPlayer, COLOR_END_PATH_1, COLOR_END_PATH_2);
        }
    }

    /**
     * 生成环绕粒子的核心方法
     * @param player 目标玩家
     * @param color1 第一种颜色
     * @param color2 第二种颜色（两种颜色交替出现）
     */
    private static void spawnParticles(ServerPlayer player, Vector3f color1, Vector3f color2) {
        ServerLevel level = player.serverLevel();
        Vec3 pos = player.position();
        double height = player.getBbHeight();

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            // 角度均匀分布（增加随机偏移让环不那么完美）
            double angle = (2 * Math.PI / PARTICLE_COUNT) * i + (RANDOM.nextDouble() - 0.5) * 0.5;
            double xOffset = Math.cos(angle) * RADIUS;
            double zOffset = Math.sin(angle) * RADIUS;

            // 高度：从脚底到头顶随机
            double yOffset = RANDOM.nextDouble() * height - 0.2;

            // 随机选择颜色（交替出现）
            Vector3f color = RANDOM.nextBoolean() ? color1 : color2;

            DustParticleOptions options = new DustParticleOptions(color, PARTICLE_SCALE);
            level.sendParticles(
                    options,
                    pos.x + xOffset,
                    pos.y + yOffset,
                    pos.z + zOffset,
                    1,
                    0.0, 0.0, 0.0,
                    0.0
            );
        }
    }

    /**
     * 将十六进制颜色转换为 Vector3f（RGB 分量 0~1）
     */
    private static Vector3f intToVec3f(int color) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        return new Vector3f(r, g, b);
    }
}