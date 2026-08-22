package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModCraftingHandler {

    // ==================== 右键物品事件（合成与取出） ====================
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        Player player = event.getEntity();
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);

        boolean handled = false;

        // ----- 优先处理取出（含物品模具 → 产物 + 空模具） -----
        if (isFilledMold(mainHand)) {
            handled = handleExtract(player, mainHand);
        } else if (isFilledMold(offHand)) {
            handled = handleExtract(player, offHand);
        }

        // ----- 若未处理取出，尝试合成 -----
        if (!handled) {
            handled = handleCrafting(player, mainHand, offHand);
        }

        if (handled) {
            event.setCanceled(true);
        }
    }

    // ==================== 取出逻辑 ====================

    /**
     * 判断是否为可提取的含物品模具
     */
    private static boolean isFilledMold(ItemStack stack) {
        Item item = stack.getItem();
        return item == ModItems.DARK_CHOCOLATE_PASTE_MOLD.get()
                || item == ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get()
                || item == ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get()
                || item == ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get();
    }

    /**
     * 执行取出：消耗模具，给予产物 + 空模具（COOKIE_MOLD）
     */
    private static boolean handleExtract(Player player, ItemStack moldStack) {
        Item product = getProductFromFilledMold(moldStack.getItem());
        if (product == null) {
            return false;
        }

        // 非创造模式消耗模具
        if (!player.isCreative()) {
            moldStack.shrink(1);
            if (moldStack.isEmpty()) {
                // 清空手中物品（由调用者处理，但这里我们直接设置）
                InteractionHand hand = (moldStack == player.getItemInHand(InteractionHand.MAIN_HAND)) ?
                        InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
                player.setItemInHand(hand, ItemStack.EMPTY);
            }
        }

        // 给予产物
        giveProduct(player, new ItemStack(product));
        // 给予空模具（COOKIE_MOLD）
        giveProduct(player, new ItemStack(ModItems.COOKIE_MOLD.get()));

        return true;
    }

    /**
     * 含物品模具 → 产物映射
     */
    private static Item getProductFromFilledMold(Item mold) {
        if (mold == ModItems.DARK_CHOCOLATE_PASTE_MOLD.get()) {
            return ModItems.DARK_CHOCOLATE.get();
        } else if (mold == ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get()) {
            return ModItems.WHITE_CHOCOLATE.get();
        } else if (mold == ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get()) {
            return ModItems.RAW_COOKIE.get();
        } else if (mold == ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get()) {
            return ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get();
        }
        return null;
    }

    // ==================== 合成逻辑 ====================

    /**
     * 尝试合成：检查主副手是否匹配合成配方
     */
    private static boolean handleCrafting(Player player, ItemStack mainHand, ItemStack offHand) {
        // 配方定义：主手、副手 → 产物
        // 注意处理双向（主副手可互换）
        if (isCookieDoughAndMold(mainHand, offHand)) {
            return performCraft(player, mainHand, offHand, ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get());
        } else if (isNetherCookieDoughAndMold(mainHand, offHand)) {
            return performCraft(player, mainHand, offHand, ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get());
        }
        return false;
    }

    /**
     * 检查是否为普通面团 + 模具（双向）
     */
    private static boolean isCookieDoughAndMold(ItemStack stack1, ItemStack stack2) {
        return (stack1.is(ModItems.COOKIE_DOUGH.get()) && stack2.is(ModItems.COOKIE_MOLD.get()))
                || (stack1.is(ModItems.COOKIE_MOLD.get()) && stack2.is(ModItems.COOKIE_DOUGH.get()));
    }

    /**
     * 检查是否为缠魂麦面团 + 模具（双向）
     */
    private static boolean isNetherCookieDoughAndMold(ItemStack stack1, ItemStack stack2) {
        return (stack1.is(ModItems.NETHER_SOUL_WHEAT_DOUGH.get()) && stack2.is(ModItems.COOKIE_MOLD.get()))
                || (stack1.is(ModItems.COOKIE_MOLD.get()) && stack2.is(ModItems.NETHER_SOUL_WHEAT_DOUGH.get()));
    }

    /**
     * 执行合成：消耗主副手各一个，给予产物
     */
    private static boolean performCraft(Player player, ItemStack mainHand, ItemStack offHand, Item product) {
        if (player.isCreative()) {
            // 创造模式不消耗，但给予产物
            giveProduct(player, new ItemStack(product));
            return true;
        }

        // 消耗两个物品（各减1）
        if (!mainHand.isEmpty()) {
            mainHand.shrink(1);
            if (mainHand.isEmpty()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }
        if (!offHand.isEmpty()) {
            offHand.shrink(1);
            if (offHand.isEmpty()) {
                player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
        }

        giveProduct(player, new ItemStack(product));
        return true;
    }

    // ==================== 辅助方法 ====================

    /**
     * 给予玩家物品（背包优先，否则掉落）
     */
    private static void giveProduct(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}