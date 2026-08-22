package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.component.ModDataComponents;
import com.hang.miraculousori.component.OwnerComponent;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModGodItemHandler {

    // ----- 持久数据键 -----
    private static final String KEY_HAS_GAZE = "mod_god_has_gaze";
    private static final String KEY_HAS_HUNGER_PUNISHMENT = "mod_god_has_hunger_punishment";
    private static final String KEY_HAS_ENDING_SPEECH = "mod_god_has_ending_speech";
    private static final String KEY_EAT_COUNT = "mod_god_eat_count";
    private static final String KEY_HUNGER_TIMER = "mod_god_hunger_timer";   // ticks
    private static final String KEY_HAS_SEEN_ENDING = "mod_god_seen_ending"; // 用于终末之言

    // ----- 常量 -----
    private static final int REQUIRED_EAT_COUNT = 22;
    private static final int REQUIRED_HUNGER_TICKS = 20*60*42; // 22分钟 = 44000 ticks
    private static final int STUFFED_LEVEL = 3;   // 等级4，amplifier=3

    // ================================================================
    // 1. 神之瞥视：在“吃撑了”效果大于等于4下连续吃22个食物，不死亡
    // ================================================================
    @SubscribeEvent
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;
        if (player.isSpectator()) return;

        // 已获得则不再处理
        if (player.getPersistentData().getBoolean(KEY_HAS_GAZE)) return;

        // 检查是否有吃撑了效果（等级4，即amplifier=3）
        MobEffectInstance stuffed = player.getEffect(ModMobEffects.STUFFED);
        if (stuffed == null || stuffed.getAmplifier() < STUFFED_LEVEL) {
            // 没有正确的效果，重置计数（要求连续吃，所以一旦中断就重置）
            player.getPersistentData().putInt(KEY_EAT_COUNT, 0);
            return;
        }

        // 检查吃的物品是否为食物
        ItemStack itemStack = event.getItem();
        if (itemStack.getItem().getFoodProperties(itemStack, entity) == null) {
            return;
        }

        // 增加计数
        int count = player.getPersistentData().getInt(KEY_EAT_COUNT) + 1;
        player.getPersistentData().putInt(KEY_EAT_COUNT, count);

        if (count >= REQUIRED_EAT_COUNT) {
            // 给予神之瞥视（自动绑定所有者）
            giveItem(player, ModItems.GOD_GAZE.get());
            player.getPersistentData().putBoolean(KEY_HAS_GAZE, true);
            player.getPersistentData().putInt(KEY_EAT_COUNT, 0); // 重置
        }
    }

    // 死亡时重置进食计数和饥饿计时
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;
        // 重置进度
        player.getPersistentData().putInt(KEY_EAT_COUNT, 0);
        player.getPersistentData().putInt(KEY_HUNGER_TIMER, 0);
    }

    // ================================================================
    // 2. 饥灾咒刑：饱食度=0且饱和度=0时坚持22分钟不死
    // ================================================================
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (serverPlayer.isSpectator()) return;

        // 已获得则不再检测
        if (serverPlayer.getPersistentData().getBoolean(KEY_HAS_HUNGER_PUNISHMENT)) return;

        int foodLevel = serverPlayer.getFoodData().getFoodLevel();
        float saturation = serverPlayer.getFoodData().getSaturationLevel();

        // 条件：饱食度 = 0 且 饱和度 = 0
        if (foodLevel == 0 && saturation == 0) {
            int timer = serverPlayer.getPersistentData().getInt(KEY_HUNGER_TIMER) + 1;
            serverPlayer.getPersistentData().putInt(KEY_HUNGER_TIMER, timer);
            if (timer >= REQUIRED_HUNGER_TICKS) {
                // 给予饥灾咒刑（自动绑定所有者）
                giveItem(serverPlayer, ModItems.HUNGER_CURSE_PUNISHMENT.get());
                serverPlayer.getPersistentData().putBoolean(KEY_HAS_HUNGER_PUNISHMENT, true);
                serverPlayer.getPersistentData().putInt(KEY_HUNGER_TIMER, 0);
            }
        } else {
            // 条件不满足，重置计时
            serverPlayer.getPersistentData().putInt(KEY_HUNGER_TIMER, 0);
        }
    }

    // ================================================================
    // 3. 终末之言：首次从末地返回主世界（通过终末之泉）
    // ================================================================
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (serverPlayer.isSpectator()) return;

        // 已获得则忽略
        if (serverPlayer.getPersistentData().getBoolean(KEY_HAS_ENDING_SPEECH)) return;

        // 检查是否从末地（THE_END）回到主世界（OVERWORLD）
        ResourceKey<Level> from = event.getFrom();
        ResourceKey<Level> to = event.getTo();
        if (from == Level.END && to == Level.OVERWORLD) {
            // 给予终末之言（自动绑定所有者）
            giveItem(serverPlayer, ModItems.ENDING_SPEECH.get());
            serverPlayer.getPersistentData().putBoolean(KEY_HAS_ENDING_SPEECH, true);
            serverPlayer.getPersistentData().putBoolean(KEY_HAS_SEEN_ENDING, true);
        }
    }

    // ================================================================
    // 辅助方法：给予物品并设置所有者
    // ================================================================
    private static void giveItem(ServerPlayer player, net.minecraft.world.item.Item item) {
        if (item == null) return;
        ItemStack stack = new ItemStack(item);
        // 设置所有者（绑定当前玩家）
        stack.set(ModDataComponents.OWNER.get(), new OwnerComponent(player.getName().getString()));
        // 尝试加入背包，若背包满则掉落在脚边
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}