package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModRightClickHandler {

    // ==================== 炼药锅交互（包含所有物品） ====================
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        // 检查是否为有水位属性的炼药锅
        if (!(state.getBlock() instanceof AbstractCauldronBlock)
                || !state.hasProperty(BlockStateProperties.LEVEL_CAULDRON)) {
            return;
        }

        int currentLevel = state.getValue(BlockStateProperties.LEVEL_CAULDRON);
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);

        boolean handled = false;

        // ----- 优先处理主手 -----
        if (mainHand.is(ModItems.WATER_MOLD.get())) {
            handled = handleWaterMoldToCookieMold(player, mainHand, level, pos, state, currentLevel);
        } else if (mainHand.is(ModItems.GLASS_CUP.get())) {
            handled = handleGlassCupToWaterGlass(player, mainHand, level, pos, state, currentLevel);
        } else if (mainHand.is(ModItems.WATER_GLASS.get())) {
            handled = handleWaterGlassToGlassCup(player, mainHand, level, pos, state, currentLevel);
        } else if (isValidIngredient(mainHand)) {
            handled = handleCauldronInteraction(player, mainHand, level, pos, state, currentLevel);
        }

        // ----- 若主手未处理，尝试副手 -----
        if (!handled && !offHand.isEmpty()) {
            if (offHand.is(ModItems.WATER_MOLD.get())) {
                handled = handleWaterMoldToCookieMold(player, offHand, level, pos, state, currentLevel);
            } else if (offHand.is(ModItems.GLASS_CUP.get())) {
                handled = handleGlassCupToWaterGlass(player, offHand, level, pos, state, currentLevel);
            } else if (offHand.is(ModItems.WATER_GLASS.get())) {
                handled = handleWaterGlassToGlassCup(player, offHand, level, pos, state, currentLevel);
            } else if (isValidIngredient(offHand)) {
                handled = handleCauldronInteraction(player, offHand, level, pos, state, currentLevel);
            }
        }

        if (handled) {
            event.setCanceled(true);
        }
    }

    // ----- 判断是否为通用原料（面粉、粘土球、饼干模具）-----
    private static boolean isValidIngredient(ItemStack stack) {
        return stack.is(ModItems.WHEAT_FLOUR.get())
                || stack.is(ModItems.NETHER_SOUL_WHEAT_FLOUR.get())
                || stack.is(Items.CLAY_BALL)
                || stack.is(ModItems.COOKIE_MOLD.get());
    }

    // ----- 通用吸水处理（消耗水 → 给予产物）-----
    private static boolean handleCauldronInteraction(Player player, ItemStack stack, Level level,
                                                     BlockPos pos, BlockState state, int currentLevel) {
        Item product = getProductFromCauldron(stack.getItem());
        if (product == null) {
            return false;
        }
        if (currentLevel < 1) {
            return false;
        }
        int newLevel = currentLevel - 1;
        setCauldronLevel(level, pos, state, newLevel);

        if (!player.isCreative()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(
                        stack == player.getItemInHand(InteractionHand.MAIN_HAND) ?
                                InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                        ItemStack.EMPTY
                );
            }
        }

        giveProduct(player, new ItemStack(product));
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    // ----- 原料 → 产物映射（含饼干模具）-----
    private static Item getProductFromCauldron(Item input) {
        if (input == ModItems.WHEAT_FLOUR.get()) {
            return ModItems.WHEAT_DOUGH.get();
        } else if (input == ModItems.NETHER_SOUL_WHEAT_FLOUR.get()) {
            return ModItems.NETHER_SOUL_WHEAT_DOUGH.get();
        } else if (input == Items.CLAY_BALL) {
            return ModItems.WET_CLAY_BALL.get();
        } else if (input == ModItems.COOKIE_MOLD.get()) {
            return ModItems.WATER_MOLD.get();
        }
        return null;
    }

    // ----- 玻璃杯吸水：消耗水，得到含水玻璃杯 -----
    private static boolean handleGlassCupToWaterGlass(Player player, ItemStack stack, Level level,
                                                      BlockPos pos, BlockState state, int currentLevel) {
        if (currentLevel < 2) {
            return false;
        }
        int newLevel = currentLevel - 2;
        setCauldronLevel(level, pos, state, newLevel);

        if (!player.isCreative()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(
                        stack == player.getItemInHand(InteractionHand.MAIN_HAND) ?
                                InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                        ItemStack.EMPTY
                );
            }
        }

        giveProduct(player, new ItemStack(ModItems.WATER_GLASS.get()));
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    // ----- 含水玻璃杯注水：消耗含水杯，增加水位，得到空杯 -----
    private static boolean handleWaterGlassToGlassCup(Player player, ItemStack stack, Level level,
                                                      BlockPos pos, BlockState state, int currentLevel) {
        if (currentLevel > 1) {
            return false;
        }
        int newLevel = currentLevel + 2;
        setCauldronLevel(level, pos, state, newLevel);

        if (!player.isCreative()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(
                        stack == player.getItemInHand(InteractionHand.MAIN_HAND) ?
                                InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                        ItemStack.EMPTY
                );
            }
        }

        giveProduct(player, new ItemStack(ModItems.GLASS_CUP.get()));
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    // ----- 新增：含水饼干模具注水 → 得到空模具 -----
    private static boolean handleWaterMoldToCookieMold(Player player, ItemStack stack, Level level,
                                                       BlockPos pos, BlockState state, int currentLevel) {
        if (currentLevel > 2) {   // 最大水位为3，加1不能超过3
            return false;
        }
        int newLevel = currentLevel + 1;
        setCauldronLevel(level, pos, state, newLevel);

        if (!player.isCreative()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(
                        stack == player.getItemInHand(InteractionHand.MAIN_HAND) ?
                                InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                        ItemStack.EMPTY
                );
            }
        }

        giveProduct(player, new ItemStack(ModItems.COOKIE_MOLD.get()));
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    // ----- 辅助：设置炼药锅水位 -----
    private static void setCauldronLevel(Level level, BlockPos pos, BlockState state, int newLevel) {
        BlockState newState;
        if (newLevel <= 0) {
            newState = Blocks.CAULDRON.defaultBlockState();
        } else {
            newState = state.setValue(BlockStateProperties.LEVEL_CAULDRON, newLevel);
        }
        level.setBlock(pos, newState, 3);
    }

    // ----- 辅助：给予物品（背包或掉落）-----
    private static void giveProduct(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    // ==================== 面团塑型交互（原有） ====================
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        Player player = event.getEntity();
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);

        if (mainHand.is(ModItems.COOKIE_MOLD.get()) && isDough(offHand)) {
            if (handleDoughMolding(player, offHand, level)) {
                event.setCanceled(true);
            }
            return;
        }
        if (isDough(mainHand) && offHand.is(ModItems.COOKIE_MOLD.get())) {
            if (handleDoughMolding(player, mainHand, level)) {
                event.setCanceled(true);
            }
        }
    }

    private static boolean isDough(ItemStack stack) {
        return stack.is(ModItems.COOKIE_DOUGH.get())
                || stack.is(ModItems.NETHER_SOUL_WHEAT_DOUGH.get());
    }

    private static boolean handleDoughMolding(Player player, ItemStack doughStack, Level level) {
        Item product = null;
        if (doughStack.is(ModItems.COOKIE_DOUGH.get())) {
            product = ModItems.RAW_COOKIE.get();
        } else if (doughStack.is(ModItems.NETHER_SOUL_WHEAT_DOUGH.get())) {
            product = ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get();
        }
        if (product == null) {
            return false;
        }

        if (!player.isCreative()) {
            doughStack.shrink(1);
            if (doughStack.isEmpty()) {
                player.setItemInHand(
                        doughStack == player.getItemInHand(InteractionHand.MAIN_HAND) ?
                                InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                        ItemStack.EMPTY
                );
            }
        }

        giveProduct(player, new ItemStack(product));
        return true;
    }
}