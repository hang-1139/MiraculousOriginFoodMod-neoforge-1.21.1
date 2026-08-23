package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.component.ModDataComponents;
import com.hang.miraculousori.component.OwnerComponent;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

    // ----- 持久数据键（仅用于进度计数，不用于标记是否已获得） -----
    private static final String KEY_EAT_COUNT = "mod_god_eat_count";
    private static final String KEY_HUNGER_TIMER = "mod_god_hunger_timer";

    // ----- 常量 -----
    private static final int REQUIRED_EAT_COUNT = 22;
    private static final int REQUIRED_HUNGER_TICKS = 20 * 60 * 42; // 42分钟
    private static final int STUFFED_LEVEL = 3;

    // ================================================================
    // 1. 神之瞥视：检测进度 mod_gaze 是否完成
    // ================================================================
    @SubscribeEvent
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;
        if (player.isSpectator()) return;

        // 检查进度是否已完成
        if (isAdvancementDone(player, "god_gaze")) return;

        MobEffectInstance stuffed = player.getEffect(ModMobEffects.STUFFED);
        if (stuffed == null || stuffed.getAmplifier() < STUFFED_LEVEL) {
            player.getPersistentData().putInt(KEY_EAT_COUNT, 0);
            return;
        }

        ItemStack itemStack = event.getItem();
        if (itemStack.getItem().getFoodProperties(itemStack, entity) == null) {
            return;
        }

        int count = player.getPersistentData().getInt(KEY_EAT_COUNT) + 1;
        player.getPersistentData().putInt(KEY_EAT_COUNT, count);

        if (count >= REQUIRED_EAT_COUNT) {
            // 给予物品并授予进度
            giveItem(player, ModItems.GOD_GAZE.get());
            grantAdvancement(player, "god_gaze", "has_god_gaze");
            player.getPersistentData().putInt(KEY_EAT_COUNT, 0);
        }
    }

    // 死亡时重置进食计数和饥饿计时
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;
        player.getPersistentData().putInt(KEY_EAT_COUNT, 0);
        player.getPersistentData().putInt(KEY_HUNGER_TIMER, 0);
    }

    // ================================================================
    // 2. 饥灾咒刑：检测进度 nether_curse 是否完成
    // ================================================================
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (serverPlayer.isSpectator()) return;

        // 检查进度是否已完成
        if (isAdvancementDone(serverPlayer, "nether_curse")) return;

        int foodLevel = serverPlayer.getFoodData().getFoodLevel();
        float saturation = serverPlayer.getFoodData().getSaturationLevel();

        if (foodLevel == 0 && saturation == 0) {
            int timer = serverPlayer.getPersistentData().getInt(KEY_HUNGER_TIMER) + 1;
            serverPlayer.getPersistentData().putInt(KEY_HUNGER_TIMER, timer);
            if (timer >= REQUIRED_HUNGER_TICKS) {
                // 给予物品并授予进度
                giveItem(serverPlayer, ModItems.HUNGER_CURSE_PUNISHMENT.get());
                grantAdvancement(serverPlayer, "nether_curse", "has_curse_punishment");
                serverPlayer.getPersistentData().putInt(KEY_HUNGER_TIMER, 0);
            }
        } else {
            serverPlayer.getPersistentData().putInt(KEY_HUNGER_TIMER, 0);
        }
    }

    // ================================================================
    // 3. 终末之言：检测进度 end_speech 是否完成，并基于 seenCredits
    // ================================================================
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (serverPlayer.isSpectator()) return;

        ResourceKey<Level> from = event.getFrom();
        ResourceKey<Level> to = event.getTo();
        if (from == Level.END && to == Level.OVERWORLD) {
            if (serverPlayer.seenCredits) {
                // 检查进度是否已完成
                if (isAdvancementDone(serverPlayer, "end_speech")) return;
                // 给予终末之言并授予进度
                giveItem(serverPlayer, ModItems.ENDING_SPEECH.get());
                grantAdvancement(serverPlayer, "end_speech", "has_speech");
            }
        }
    }

    // ================================================================
    // 辅助方法
    // ================================================================

    /**
     * 检查玩家是否已完成指定进度
     */
    private static boolean isAdvancementDone(ServerPlayer player, String advancementId) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, advancementId);
        AdvancementHolder adv = player.server.getAdvancements().get(id);
        if (adv == null) return false;
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(adv);
        return progress.isDone();
    }

    /**
     * 授予玩家指定进度（通过 criterion）
     */
    private static void grantAdvancement(ServerPlayer player, String advancementId, String criterion) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, advancementId);
        AdvancementHolder adv = player.server.getAdvancements().get(id);
        if (adv != null) {
            player.getAdvancements().award(adv, criterion);
        }
    }

    /**
     * 给予物品并设置所有者
     */
    private static void giveItem(ServerPlayer player, net.minecraft.world.item.Item item) {
        if (item == null) return;
        ItemStack stack = new ItemStack(item);
        stack.set(ModDataComponents.OWNER.get(), new OwnerComponent(player.getName().getString()));
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}