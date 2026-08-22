package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.item.ModItems;
import com.hang.miraculousori.village.ModVillagerProfessions;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import javax.annotation.Nullable;
import java.util.List;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModVillagerTrades {

    // 价格浮动系数（控制价格波动范围，值越小波动越小）
    private static final float PRICE_MULTIPLIER_08 = 0.8f;
    private static final float PRICE_MULTIPLIER_09 = 0.9f;
    private static final float PRICE_MULTIPLIER_07 = 0.7f;
    private static final float PRICE_MULTIPLIER_06 = 0.6f;
    private static final float PRICE_MULTIPLIER_10 = 1.0f;

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        VillagerProfession profession = event.getType();

        if (profession == ModVillagerProfessions.MILLER.get()) {
            addMillerTrades(event);
        }

        if (profession == VillagerProfession.FARMER) {
            addFarmerApprenticeTrades(event);
        }
    }

    // ==================== 磨坊主交易 ====================
    private static void addMillerTrades(VillagerTradesEvent event) {
        // ----- 新手（等级 1） -----
        List<VillagerTrades.ItemListing> level1 = event.getTrades().get(1);
        // 4小麦 + 1绿宝石 → 8小麦面粉
        level1.add(new ItemsAndEmeraldsForItems(
                new ItemStack(Items.WHEAT, 4),          // 输入物品1：4个小麦
                new ItemStack(Items.EMERALD, 1),        // 输入物品2：1个绿宝石
                new ItemStack(ModItems.WHEAT_FLOUR.get(), 8), // 输出物品：8个小麦面粉
                12,                                     // 最大交易次数
                1,                                      // 每次交易获得的经验值
                PRICE_MULTIPLIER_08                     // 价格浮动系数
        ));
        // 1糖块 + 1绿宝石 → 8糖
        level1.add(new ItemsAndEmeraldsForItems(
                new ItemStack(ModBlocks.SUGAR_BLOCK.get(), 1),
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(Items.SUGAR, 8),
                12, 1, PRICE_MULTIPLIER_08
        ));
        // 2方糖 + 1绿宝石 → 8糖
        level1.add(new ItemsAndEmeraldsForItems(
                new ItemStack(ModItems.SUGAR_CUBE.get(), 2),
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(Items.SUGAR, 8),
                12, 1, PRICE_MULTIPLIER_08
        ));
        // 1绿宝石 → 4小麦面粉
        level1.add(new ItemsForEmeralds(ModItems.WHEAT_FLOUR.get(), 1, 4, 12, 1, PRICE_MULTIPLIER_08));

        // ----- 学徒（等级 2） -----
        List<VillagerTrades.ItemListing> level2 = event.getTrades().get(2);
        // 1绿宝石 → 1脱脂可可粉
        level2.add(new ItemsForEmeralds(ModItems.COCOA_POWDER.get(), 1, 1, 12, 2, PRICE_MULTIPLIER_09));
        // 4粗盐 + 1绿宝石 → 8盐
        level2.add(new ItemsAndEmeraldsForItems(
                new ItemStack(ModItems.CRUSHED_SALT.get(), 4),
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(ModItems.SALT.get(), 8),
                12, 2, PRICE_MULTIPLIER_08
        ));
        // 1绿宝石 → 12西瓜渣
        level2.add(new ItemsForEmeralds(ModItems.WATERMELON_PULP.get(), 1, 12, 12, 2, PRICE_MULTIPLIER_08));
        // 1绿宝石 → 16苹果渣
        level2.add(new ItemsForEmeralds(ModItems.APPLE_PULP.get(), 1, 16, 12, 2, PRICE_MULTIPLIER_08));
        // 1绿宝石 → 23甜浆果渣
        level2.add(new ItemsForEmeralds(ModItems.SWEET_BERRY_PULP.get(), 1, 23, 12, 2, PRICE_MULTIPLIER_09));
        // 1绿宝石 → 21发光浆果渣
        level2.add(new ItemsForEmeralds(ModItems.GLOW_BERRY_PULP.get(), 1, 21, 12, 2, PRICE_MULTIPLIER_09));
        // 1绿宝石 → 12甜菜根渣
        level2.add(new ItemsForEmeralds(ModItems.BEETROOT_PULP.get(), 1, 12, 12, 2, PRICE_MULTIPLIER_06));
        // 1绿宝石 → 10胡萝卜渣
        level2.add(new ItemsForEmeralds(ModItems.CARROT_PULP.get(), 1, 10, 12, 2, PRICE_MULTIPLIER_07));
        // 1绿宝石 → 10甘蔗渣
        level2.add(new ItemsForEmeralds(ModItems.SUGARCANE_PULP.get(), 1, 10, 12, 2, PRICE_MULTIPLIER_07));

        // ----- 老手（等级 3） -----
        List<VillagerTrades.ItemListing> level3 = event.getTrades().get(3);
        // 组1：果汁瓶（4-5 绿宝石）
        level3.add(new ItemsForEmeralds(ModItems.SWEET_BERRY_JUICE_BOTTLE.get(), 4, 1, 8, 3, PRICE_MULTIPLIER_08));
        level3.add(new ItemsForEmeralds(ModItems.SUGARCANE_JUICE_BOTTLE.get(), 5, 1, 8, 3, PRICE_MULTIPLIER_08));
        level3.add(new ItemsForEmeralds(ModItems.BEETROOT_JUICE_BOTTLE.get(), 4, 1, 8, 3, PRICE_MULTIPLIER_08));
        // 组2：果汁瓶（8-10 绿宝石）
        level3.add(new ItemsForEmeralds(ModItems.SWEET_BERRY_JUICE_BOTTLE.get(), 8, 1, 6, 3, PRICE_MULTIPLIER_08));
        level3.add(new ItemsForEmeralds(ModItems.SUGARCANE_JUICE_BOTTLE.get(), 10, 1, 6, 3, PRICE_MULTIPLIER_08));
        level3.add(new ItemsForEmeralds(ModItems.BEETROOT_JUICE_BOTTLE.get(), 8, 1, 6, 3, PRICE_MULTIPLIER_08));

        // ----- 专家（等级 4） -----
        List<VillagerTrades.ItemListing> level4 = event.getTrades().get(4);
        level4.add(new ItemsForEmeralds(ModItems.SWEET_BERRY_JUICE_BUCKET.get(), 16, 1, 6, 4, PRICE_MULTIPLIER_08));
        level4.add(new ItemsForEmeralds(ModItems.SUGARCANE_JUICE_BUCKET.get(), 20, 1, 6, 4, PRICE_MULTIPLIER_08));
        level4.add(new ItemsForEmeralds(ModItems.BEETROOT_JUICE_BUCKET.get(), 16, 1, 6, 4, PRICE_MULTIPLIER_08));

        // ----- 大师（等级 5） -----
        List<VillagerTrades.ItemListing> level5 = event.getTrades().get(5);
        level5.add(new ItemsForEmeralds(ModItems.WARPED_FRUIT_POWDER.get(), 4, 1, 6, 5, PRICE_MULTIPLIER_09));
        level5.add(new ItemsForEmeralds(ModItems.CRIMSON_FRUIT_POWDER.get(), 4, 1, 6, 5, PRICE_MULTIPLIER_09));
        level5.add(new ItemsForEmeralds(ModItems.NETHER_SOUL_WHEAT_FLOUR.get(), 5, 1, 6, 5, PRICE_MULTIPLIER_09));
        level5.add(new ItemsForEmeralds(ModItems.NETHER_SOUL_WHEAT_FLOUR.get(), 5, 1, 6, 5, PRICE_MULTIPLIER_09));

    }

    // ==================== 农民学徒额外交易 ====================
    private static void addFarmerApprenticeTrades(VillagerTradesEvent event) {
        List<VillagerTrades.ItemListing> apprentice = event.getTrades().get(2);

        // 村民买入残渣，玩家获得绿宝石
        // 参数含义：物品, 卖出数量, 获得绿宝石数, 最大交易次数, 经验值, 价格浮动
        apprentice.add(new EmeraldForItems(ModItems.WATERMELON_PULP.get(), 14, 1, 12, 2, PRICE_MULTIPLIER_08));
        apprentice.add(new EmeraldForItems(ModItems.APPLE_PULP.get(), 16, 1, 12, 2, PRICE_MULTIPLIER_08));
        apprentice.add(new EmeraldForItems(ModItems.SWEET_BERRY_PULP.get(), 25, 1, 12, 2, PRICE_MULTIPLIER_09));
        apprentice.add(new EmeraldForItems(ModItems.GLOW_BERRY_PULP.get(), 23, 1, 12, 2, PRICE_MULTIPLIER_09));
        apprentice.add(new EmeraldForItems(ModItems.BEETROOT_PULP.get(), 14, 1, 12, 2, PRICE_MULTIPLIER_06));
        apprentice.add(new EmeraldForItems(ModItems.CARROT_PULP.get(), 10, 1, 12, 2, PRICE_MULTIPLIER_07));
        apprentice.add(new EmeraldForItems(ModItems.SUGARCANE_PULP.get(), 10, 1, 12, 2, PRICE_MULTIPLIER_07));
    }


    /**
     * 交易方向：玩家卖出物品 → 获得绿宝石
     * 参数：item（玩家提供的物品）, itemCount（数量）, emeraldCount（获得的绿宝石数）, maxUses, xp, priceMultiplier
     */
    private record EmeraldForItems(Item item, int itemCount, int emeraldCount, int maxUses, int xp, float priceMultiplier)
            implements VillagerTrades.ItemListing {
        @Nullable
        @Override
        public MerchantOffer getOffer(net.minecraft.world.entity.Entity trader, net.minecraft.util.RandomSource random) {
            return new MerchantOffer(
                    new ItemCost(item, itemCount),
                    new ItemStack(Items.EMERALD, emeraldCount),
                    maxUses, xp, priceMultiplier
            );
        }
    }

    /**
     * 用绿宝石换物品（玩家提供绿宝石，获得物品）
     */
    private record ItemsForEmeralds(Item result, int emeraldCost, int count, int maxUses, int xp, float priceMultiplier)
            implements VillagerTrades.ItemListing {
        @Nullable
        @Override
        public MerchantOffer getOffer(net.minecraft.world.entity.Entity trader, net.minecraft.util.RandomSource random) {
            return new MerchantOffer(
                    new ItemCost(Items.EMERALD, emeraldCost),
                    new ItemStack(result, count),
                    maxUses, xp, priceMultiplier
            );
        }
    }

    /**
     * 交易方向：玩家支付绿宝石 → 获得物品
     * 参数：result（获得的物品）, emeraldCost（花费绿宝石数）, count（获得物品数量）, maxUses, xp, priceMultiplier
     */
    private static class ItemsAndEmeraldsForItems implements VillagerTrades.ItemListing {
        private final ItemStack inputItem;
        private final ItemStack emeraldCost;
        private final ItemStack outputItem;
        private final int maxUses;
        private final int xp;
        private final float priceMultiplier;

        public ItemsAndEmeraldsForItems(ItemStack inputItem, ItemStack emeraldCost, ItemStack outputItem,
                                        int maxUses, int xp, float priceMultiplier) {
            this.inputItem = inputItem.copy();
            this.emeraldCost = emeraldCost.copy();
            this.outputItem = outputItem.copy();
            this.maxUses = maxUses;
            this.xp = xp;
            this.priceMultiplier = priceMultiplier;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(net.minecraft.world.entity.Entity trader, net.minecraft.util.RandomSource random) {
            // 第一个价格：输入物品
            ItemCost cost1 = new ItemCost(inputItem.getItem(), inputItem.getCount());
            // 第二个价格：绿宝石（可选）
            ItemCost cost2 = new ItemCost(emeraldCost.getItem(), emeraldCost.getCount());
            return new MerchantOffer(
                    cost1,
                    java.util.Optional.of(cost2),
                    outputItem.copy(),
                    maxUses,
                    xp,
                    priceMultiplier
            );
        }
    }
}