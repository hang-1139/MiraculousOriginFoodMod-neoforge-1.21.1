package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.component.ModDataComponents;
import com.hang.miraculousori.component.OwnerComponent;
import com.hang.miraculousori.datagen.tags.ModItemTagsProvider;
import com.hang.miraculousori.entity.custom.DivineItemEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class DivineItemHandler {

    // 存储正在受惩罚的玩家及其计时器（单位：tick）
    private static final Map<UUID, Integer> PUNISH_TIMERS = new HashMap<>();
    private static final int PUNISH_INTERVAL = 5 * 20; // 5秒

    // ==================== 替换掉落物为 DivineItemEntity ====================
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;
        if (itemEntity instanceof DivineItemEntity) return;

        ItemStack stack = itemEntity.getItem();
        if (stack.is(ModItemTagsProvider.DIVINE)) {
            DivineItemEntity divine = new DivineItemEntity(
                    event.getLevel(),
                    itemEntity.getX(),
                    itemEntity.getY(),
                    itemEntity.getZ(),
                    stack
            );
            divine.setPickUpDelay(itemEntity.hasPickUpDelay() ? 100 : 0);
            divine.setUnlimitedLifetime();

            event.getLevel().addFreshEntity(divine);
            event.setCanceled(true);
            itemEntity.discard();
        }
    }

    // ==================== 防止雷劈 ====================
    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof DivineItemEntity) {
            event.setCanceled(true);
        }
    }

    // ==================== 拾取绑定所有者 + 惩罚检查（使用 Pre 确保在拾取前处理） ====================
    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event) {
        Player player = event.getPlayer();
        ItemEntity itemEntity = event.getItemEntity();
        if (itemEntity == null) return;
        ItemStack stack = itemEntity.getItem();
        if (!stack.is(ModItemTagsProvider.DIVINE)) return;

        // 创造模式玩家不触发绑定和惩罚
        if (player.isCreative()) return;

        OwnerComponent owner = stack.get(ModDataComponents.OWNER.get());
        if (owner == null) {
            // 无主 → 绑定当前玩家
            stack.set(ModDataComponents.OWNER.get(), new OwnerComponent(player.getName().getString()));
        } else {
            // 已有主人 → 判断是否与拾取者一致
            String ownerName = owner.ownerName();
            if (!ownerName.equals(player.getName().getString())) {
                // 非主人拾取 → 启动惩罚（在 tick 中处理）
                PUNISH_TIMERS.put(player.getUUID(), 0);
                // 首次提示（只在拾取时显示一次）
                player.sendSystemMessage(Component.translatable("message.miraculousori.stolen_divine").withStyle(ChatFormatting.RED));
            }
        }
        // 允许正常拾取（不取消事件）
    }

    // ==================== 每 Tick 检查惩罚（遍历整个背包） ====================
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        if (player.isCreative() || player.isSpectator()) return; // 创造/旁观者不触发

        UUID uuid = player.getUUID();

        // 检查玩家背包中是否存在非自己所有的神级物品（包括盔甲栏和副手）
        boolean hasIllegalDivine = false;
        // 检查主背包（包括快捷栏，36格）
        for (ItemStack stack : player.getInventory().items) {
            if (isIllegalDivine(stack, player)) {
                hasIllegalDivine = true;
                break;
            }
        }
        if (!hasIllegalDivine) {
            // 检查盔甲栏（4格）
            for (ItemStack stack : player.getInventory().armor) {
                if (isIllegalDivine(stack, player)) {
                    hasIllegalDivine = true;
                    break;
                }
            }
        }
        if (!hasIllegalDivine) {
            // 检查副手（1格）
            for (ItemStack stack : player.getInventory().offhand) {
                if (isIllegalDivine(stack, player)) {
                    hasIllegalDivine = true;
                    break;
                }
            }
        }

        // 如果背包中没有非法神级物品，移除惩罚
        if (!hasIllegalDivine) {
            if (PUNISH_TIMERS.containsKey(uuid)) {
                PUNISH_TIMERS.remove(uuid);
            }
            return;
        }

        // 有非法物品，启动或更新计时器
        int timer = PUNISH_TIMERS.getOrDefault(uuid, 0) + 1;
        PUNISH_TIMERS.put(uuid, timer);

        if (timer >= PUNISH_INTERVAL) {
            // 执行惩戒（每5秒触发一次）
            Level level = player.level();
            if (level instanceof ServerLevel serverLevel) {
                // 切换天气为雷雨（持续6000 tick）
                serverLevel.setWeatherParameters(0, 6000, true, true);

                // 召唤闪电
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
                lightning.setPos(player.getX(), player.getY(), player.getZ());
                lightning.setDamage(5.0F);
                serverLevel.addFreshEntity(lightning);

                // 每次惩戒时显示一次警告消息
                player.sendSystemMessage(Component.translatable("message.miraculousori.stolen_divine").withStyle(ChatFormatting.RED));
            }
            // 重置计时器
            PUNISH_TIMERS.put(uuid, 0);
        }
    }

    /**
     * 判断一个物品是否为非自己所有的神级物品
     */
    private static boolean isIllegalDivine(ItemStack stack, Player player) {
        if (stack.isEmpty() || !stack.is(ModItemTagsProvider.DIVINE)) return false;
        OwnerComponent owner = stack.get(ModDataComponents.OWNER.get());
        if (owner == null) return false; // 无主，视为合法（已绑定则会有主人）
        return !owner.ownerName().equals(player.getName().getString());
    }

    // ==================== 工具提示显示所有者（支持翻译） ====================
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.is(ModItemTagsProvider.DIVINE)) return;

        OwnerComponent owner = stack.get(ModDataComponents.OWNER.get());
        Component ownerText;
        if (owner != null) {
            ownerText = Component.translatable("tooltip.miraculousori.owner", owner.ownerName()).withStyle(ChatFormatting.GOLD);
        } else {
            ownerText = Component.translatable("tooltip.miraculousori.no_owner").withStyle(ChatFormatting.GRAY);
        }
        event.getToolTip().add(ownerText);
    }
}