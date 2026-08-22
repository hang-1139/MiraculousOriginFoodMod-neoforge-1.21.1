package com.hang.miraculousori.item;


import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.item.custom.BaguetteItem;
import com.hang.miraculousori.item.custom.LargeBaguetteHalfItem;
import com.hang.miraculousori.item.custom.SmallBaguetteHalfItem;
import com.hang.miraculousori.item.custom.drink.DrinkBottleItem;
import com.hang.miraculousori.item.custom.drink.DrinkBucketItem;
import com.hang.miraculousori.item.custom.drink.DrinkGlassItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.hang.miraculousori.MiraculousOriginFoodMod.LOGGER;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MiraculousOriginFoodMod.MODID);

    /* 注册普通物品 */
    public static final DeferredItem<Item> FOOD_GOD_BLESSING_AMULET =  // 食神之佑
            ITEMS.register("god/food_god_blessing_amulet", () -> new Item(new Item.Properties()
                    .stacksTo(1))); // 神相关
    public static final DeferredItem<Item> GOD_GAZE =  // 神之瞥视
            ITEMS.register("god/god_gaze", () -> new Item(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredItem<Item> HUNGER_CURSE_PUNISHMENT =  // 饥饿诅咒惩罚
            ITEMS.register("god/hunger_curse_punishment", () -> new Item(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredItem<Item> HUNGER_CURSE_ODE =  // 饥饿诅咒颂歌
            ITEMS.register("god/hunger_curse_ode", () -> new Item(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredItem<Item> ENDING_SPEECH =  // 终末之言
            ITEMS.register("god/ending_speech", () -> new Item(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredItem<Item> END_NEW_PATH = // 终幕新途
            ITEMS.register("god/end_new_path", () -> new Item(new Item.Properties()
                    .stacksTo(1)));

    public static final DeferredItem<Item> SATURATION_TOTEM =  // 饱和图腾
            ITEMS.register("misc/saturation_totem", () -> new Item(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredItem<Item> HUNGER_TOTEM =  // 饥饿图腾
            ITEMS.register("misc/hunger_totem", () -> new Item(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredItem<Item> END_ROAD_RUNE = // 末途符文
            ITEMS.register("misc/end_road_rune", () -> new Item(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredItem<Item> WHEAT_FLOUR =  // 小麦面粉
            ITEMS.register("misc/wheat_flour", () -> new Item(new Item.Properties())); // misc 杂项
    public static final DeferredItem<Item> WHEAT_DOUGH =  // 小麦面团
            ITEMS.register("misc/wheat_dough", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SUGAR_CUBE =  // 方糖
            ITEMS.register("misc/sugar_cube", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CARAMEL =  // 焦糖
            ITEMS.register("misc/caramel", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLAZE_INCENSE_POWDER =  // 烈焰香粉
            ITEMS.register("misc/blaze_incense_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NETHER_INCENSE_POWDER =  // 下界香粉
            ITEMS.register("misc/nether_incense_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CRIMSON_FRUIT_POWDER =  // 红色果实香粉
            ITEMS.register("misc/crimson_fruit_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WARPED_FRUIT_POWDER =  // 畸形果实香粉
            ITEMS.register("misc/warped_fruit_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NETHER_SOUL_WHEAT_FLOUR =  // 下界灵魂小麦面粉
            ITEMS.register("misc/nether_soul_wheat_flour", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NETHER_SOUL_WHEAT_DOUGH =  // 下界灵魂小麦面团
            ITEMS.register("misc/nether_soul_wheat_dough", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DIVINE_UPGRADE_TEMPLATE =  // 神圣升级模板
            ITEMS.register("misc/divine_upgrade_template", () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> GLASS_CUP =  // 玻璃杯
            ITEMS.register("misc/glass_cup", () -> new Item(new Item.Properties()
                    .stacksTo(64)));
    public static final DeferredItem<Item> APPLE_PULP = // 苹果渣
            ITEMS.register("misc/apple_pulp", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WATERMELON_PULP = // 西瓜渣
            ITEMS.register("misc/watermelon_pulp", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CARROT_PULP =  // 胡萝卜渣
            ITEMS.register("misc/carrot_pulp", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BEETROOT_PULP =  // 甜菜根渣
            ITEMS.register("misc/beetroot_pulp", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SWEET_BERRY_PULP =  // 甜浆果渣
            ITEMS.register("misc/sweet_berry_pulp", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GLOW_BERRY_PULP =  // 发光浆果渣
            ITEMS.register("misc/glow_berry_pulp", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SUGARCANE_PULP =  // 甘蔗渣
            ITEMS.register("misc/sugarcane_pulp", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COCOA_POWDER = // 巧克力粉
            ITEMS.register("misc/cocoa_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COCOA_BUTTER_MOLD = // 巧克力奶油模具
            ITEMS.register("misc/cocoa_butter_mold", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WATER_MOLD = // 含水模具
            ITEMS.register("misc/water_mold", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DARK_CHOCOLATE_PASTE_MOLD = // 黑巧克力浆模具
            ITEMS.register("misc/dark_chocolate_paste_mold", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_FILLED_MOLD = // 白巧克力填充模具
            ITEMS.register("misc/white_chocolate_filled_mold", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CRUSHED_SALT = // 粗盐
            ITEMS.register("misc/crushed_salt", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SALT = // 盐
            ITEMS.register("misc/salt", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FLOATING_MELON_JAM =    // 浮瓜果酱
            ITEMS.register("misc/floating_melon_jam", () -> new Item(new Item.Properties()));

    // egg


    public static final DeferredItem<Item> RAW_BAGUETTE =  // 生法棍
            ITEMS.register("bread/raw_baguette", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_POTATO_PATTY =  // 生土豆饼
            ITEMS.register("bread/raw_potato_patty", () -> new Item(new Item.Properties()));

    public static final DeferredItem<ItemNameBlockItem> WITHERED_SOULWEED = // 下界野草
            ITEMS.register("plant/withered_soulweed",
                    () -> new ItemNameBlockItem(ModBlocks.WITHERED_SOULWEED.get(),
                            new Item.Properties())
            );
    public static final DeferredItem<Item> NETHER_SOUL_WHEAT_SEEDS = // 下界灵魂小麦种子
            ITEMS.register("plant/nether_soul_wheat_seeds",
                    () -> new ItemNameBlockItem(ModBlocks.NETHER_SOUL_WHEAT.get(),
                            new Item.Properties())
            );
    public static final DeferredItem<Item> FLOATING_MELON_SEEDS = // 悬浮南瓜种子
            ITEMS.register("plant/floating_melon_seeds",
                    () -> new ItemNameBlockItem(ModBlocks.FLOATING_MELON_CROP.get(),
                            new Item.Properties())
            );
    public static final DeferredItem<Item> SOUL_WHEAT =  // 下界灵魂小麦
            ITEMS.register("plant/soul_wheat", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COOKIE_DOUGH =  // 饼干面团
            ITEMS.register("cookie/cookie_dough", () -> new Item(new Item.Properties())); // 饼干相关
    public static final DeferredItem<Item> RAW_COOKIE = // 生饼干
            ITEMS.register("cookie/raw_cookie", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COOKIE_MOLD =  // 饼干模具
            ITEMS.register("cookie/cookie_mold", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WHEAT_DOUGH_FILLED_COOKIE_MOLD =  // 有饼干胚的模具
            ITEMS.register("cookie/wheat_dough_filled_cookie_mold", () -> new Item(new Item.Properties()
                    .craftRemainder(ModItems.COOKIE_MOLD.get()) ));
    public static final DeferredItem<Item> RAW_NETHER_SOUL_WHEAT_COOKIE =  // 生下界灵魂小麦饼干
            ITEMS.register("cookie/raw_nether_soul_wheat_cookie", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NETHER_SOUL_WHEAT_COOKIE_MOLD =  // 下界灵魂小麦饼干模具
            ITEMS.register("cookie/nether_soul_wheat_cookie_mold", () -> new Item(new Item.Properties()
                    .craftRemainder(ModItems.COOKIE_MOLD.get()) ));

    public static final DeferredItem<Item> VEGETABLE_CLAY_CAKE =  // 掺菜粘土饼
            ITEMS.register("clay/vegetable_clay_cake", () -> new Item(new Item.Properties())); // 粘土相关
    public static final DeferredItem<Item> WET_CLAY_BALL =  // 湿润的粘土球
            ITEMS.register("clay/wet_clay_ball", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CLAY_CAKE =  // 粘土饼
            ITEMS.register("clay/clay_cake", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SWEET_CLAY_CAKE =  // 甜粘土饼
            ITEMS.register("clay/sweet_clay_cake", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ENDER_CLAY_CAKE =  // 末影粘土饼
            ITEMS.register("clay/ender_clay_cake", () -> new Item(new Item.Properties()));

    /* 注册食品 */
    public static final DeferredItem<Item> MASHED_POTATO =  // 土豆泥
            ITEMS.register("misc/mashed_potato", () -> new Item(new Item.Properties().food(ModFoods.MASHED_POTATO)));
    public static final DeferredItem<Item> DARK_CHOCOLATE =  // 黑巧克力
            ITEMS.register("misc/dark_chocolate", () -> new Item(new Item.Properties().food(ModFoods.DARK_CHOCOLATE)));
    public static final DeferredItem<Item> WHITE_CHOCOLATE =  // 白巧克力
            ITEMS.register("misc/white_chocolate", () -> new Item(new Item.Properties().food(ModFoods.WHITE_CHOCOLATE)));

    public static final DeferredItem<Item> VEGETABLE_COOKED_CLAY_CAKE =  // 烹饪后的蔬菜粘土蛋糕
            ITEMS.register("clay/vegetable_cooked_clay_cake", () -> new Item(new Item.Properties().food(ModFoods.VEGETABLE_COOKED_CLAY_CAKE)));
    public static final DeferredItem<Item> SWEET_COOKED_CLAY_CAKE =  // 烹饪后的甜粘土蛋糕
            ITEMS.register("clay/sweet_cooked_clay_cake", () -> new Item(new Item.Properties().food(ModFoods.SWEET_COOKED_CLAY_CAKE)));
    public static final DeferredItem<Item> COOKED_CLAY_CAKE =  // 烹饪后的粘土蛋糕
            ITEMS.register("clay/cooked_clay_cake", () -> new Item(new Item.Properties().food(ModFoods.COOKED_CLAY_CAKE)));
    public static final DeferredItem<Item> ENDER_COOKED_CLAY_CAKE =  // 烹饪后的末影粘土蛋糕
            ITEMS.register("clay/ender_cooked_clay_cake", () -> new Item(new Item.Properties().food(ModFoods.ENDER_COOKED_CLAY_CAKE)));
    public static final DeferredItem<Item> NETHER_CLAY_CAKE =  // 烹饪后的下界粘土蛋糕
            ITEMS.register("clay/nether_clay_cake", () -> new Item(new Item.Properties().food(ModFoods.NETHER_CLAY_CAKE)));

    public static final DeferredItem<Item> WHEAT_COOKIE =  // 小麦饼干
            ITEMS.register("cookie/wheat_cookie", () -> new Item(new Item.Properties().food(ModFoods.WHEAT_COOKIE)));
    public static final DeferredItem<Item> COMPRESSED_WHEAT_BISCUIT =  // 压缩小麦饼干
            ITEMS.register("cookie/compressed_wheat_biscuit", () -> new Item(new Item.Properties().food(ModFoods.COMPRESSED_WHEAT_BISCUIT)));
    public static final DeferredItem<Item> NETHER_SOUL_WHEAT_COOKIE =  // 下界灵魂小麦饼干
            ITEMS.register("cookie/nether_soul_wheat_cookie", () -> new Item(new Item.Properties().food(ModFoods.NETHER_SOUL_WHEAT_COOKIE)));

    public static final DeferredItem<ItemNameBlockItem> CRIMSON_FRUIT =  // 红色果实
            ITEMS.register("plant/crimson_fruit",
                    () -> new ItemNameBlockItem(ModBlocks.CRIMSON_VINES.get(),
                            new Item.Properties().food(ModFoods.CRIMSON_FRUIT))
            );
    public static final DeferredItem<ItemNameBlockItem> WARPED_FRUIT =  // 畸形果实
            ITEMS.register("plant/warped_fruit",
                    () -> new ItemNameBlockItem(ModBlocks.WARPED_VINES_HEAD.get(),
                    new Item.Properties().food(ModFoods.WARPED_FRUIT))
            );
    public static final DeferredItem<Item> LEAPING_FRUIT =  // 跳跃果实
            ITEMS.register("plant/leaping_fruit", () -> new Item(new Item.Properties().food(ModFoods.LEAPING_FRUIT)));
    public static final DeferredItem<Item> FLOATING_MELON =  // 浮动的西瓜
            ITEMS.register("plant/floating_melon", () -> new Item(new Item.Properties().food(ModFoods.FLOATING_MELON)));
    public static final DeferredItem<Item> BROKEN_FLOATING_GOURD =  // 破损的悬浮南瓜
            ITEMS.register("plant/broken_floating_gourd", () -> new Item(new Item.Properties().food(ModFoods.BROKEN_FLOATING_GOURD)));

    // 浮瓜派（可食用）
    public static final DeferredItem<Item> FLOATING_MELON_PIE =
            ITEMS.register("pie/floating_melon_pie", () -> new Item(new Item.Properties()
                    .food(ModFoods.FLOATING_MELON_PIE)));



    public static final DeferredItem<VerySweetBreadItem> VERY_SWEET_BREAD = // 非常甜的面包
            ITEMS.register("bread/very_sweet_bread",
                    () -> new VerySweetBreadItem(new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(6)
                                    .saturationModifier(0.6F)
                                    .build())
                            .stacksTo(1)
                    ));
    public static final DeferredItem<Item> WHEAT_BREAD = // 小麦面包
            ITEMS.register("bread/wheat_bread", () -> new Item(new Item.Properties().food(ModFoods.WHEAT_BREAD)));
    public static final DeferredItem<Item> CHINESE_SUGAR_FREE_BREAD = // 中式无糖面包
            ITEMS.register("bread/chinese_sugar_free_bread", () -> new Item(new Item.Properties().food(ModFoods.CHINESE_SUGAR_FREE_BREAD)));
    public static final DeferredItem<Item> HONEY_BREAD = // 蜂蜜面包
            ITEMS.register("bread/honey_bread", () -> new Item(new Item.Properties().food(ModFoods.HONEY_BREAD)));
    public static final DeferredItem<BaguetteItem> BAGUETTE =
            ModItems.ITEMS.register("bread/baguette",
                    () -> new BaguetteItem(new Item.Properties()
                            .stacksTo(16)
                            .food(ModFoods.BAGUETTE)));
    public static final DeferredItem<LargeBaguetteHalfItem> LARGE_BAGUETTE_HALF =
            ModItems.ITEMS.register("bread/large_baguette_half",
                    () -> new LargeBaguetteHalfItem(new Item.Properties()
                            .stacksTo(32)
                            .food(ModFoods.LARGE_BAGUETTE_HALF)));
    public static final DeferredItem<SmallBaguetteHalfItem> SMALL_BAGUETTE_HALF =
            ModItems.ITEMS.register("bread/small_baguette_half",
                    () -> new SmallBaguetteHalfItem(new Item.Properties()
                            .food(ModFoods.SMALL_BAGUETTE_HALF)));
    public static final DeferredItem<Item> COOKED_POTATO_PATTY =  // 烹饪后的土豆饼
            ITEMS.register("bread/cooked_potato_patty", () -> new Item(new Item.Properties().food(ModFoods.COOKED_POTATO_PATTY)));


    /* 注册饮品 */
    // -- 水 --
    public static final DeferredItem<DrinkBottleItem> WATER_GLASS =
            ITEMS.register("drink/water_bottle",
                    () -> new DrinkBottleItem(new Item.Properties()
                            .stacksTo(16),
                            ModFoods.WATER_GLASS.nutrition(),
                            0.0F,                                   // saturationModifier
                            ModFoods.WATER_GLASS.effects()));

    // —— 苹果 ——
    public static final DeferredItem<DrinkBottleItem> APPLE_JUICE_BOTTLE =
            ITEMS.register("drink/apple_juice_bottle",
                    () -> new DrinkBottleItem(new Item.Properties().stacksTo(16),
                            ModFoods.APPLE_JUICE_BOTTLE.nutrition(),
                            0.6F,
                            ModFoods.APPLE_JUICE_BOTTLE.effects()));

    public static final DeferredItem<DrinkGlassItem> APPLE_JUICE_GLASS =
            ITEMS.register("drink/apple_juice_glass",
                    () -> new DrinkGlassItem(new Item.Properties().stacksTo(16),
                            ModFoods.APPLE_JUICE_GLASS.nutrition(),
                            0.6F,
                            ModFoods.APPLE_JUICE_GLASS.effects()));

    public static final DeferredItem<DrinkBucketItem> APPLE_JUICE_BUCKET =
            ITEMS.register("drink/apple_juice_bucket",
                    () -> new DrinkBucketItem(new Item.Properties().stacksTo(1),
                            ModFoods.APPLE_JUICE_BUCKET.nutrition(),
                            0.6F,
                            ModFoods.APPLE_JUICE_BUCKET.effects()));

    // —— 西瓜 ——
    public static final DeferredItem<DrinkBottleItem> WATERMELON_JUICE_BOTTLE =
            ITEMS.register("drink/watermelon_juice_bottle",
                    () -> new DrinkBottleItem(new Item.Properties().stacksTo(16),
                            ModFoods.WATERMELON_JUICE_BOTTLE.nutrition(),
                            0.6F,
                            ModFoods.WATERMELON_JUICE_BOTTLE.effects()));

    public static final DeferredItem<DrinkGlassItem> WATERMELON_JUICE_GLASS =
            ITEMS.register("drink/watermelon_juice_glass",
                    () -> new DrinkGlassItem(new Item.Properties().stacksTo(16),
                            ModFoods.WATERMELON_JUICE_GLASS.nutrition(),
                            0.6F,
                            ModFoods.WATERMELON_JUICE_GLASS.effects()));

    public static final DeferredItem<DrinkBucketItem> WATERMELON_JUICE_BUCKET =
            ITEMS.register("drink/watermelon_juice_bucket",
                    () -> new DrinkBucketItem(new Item.Properties().stacksTo(1),
                            ModFoods.WATERMELON_JUICE_BUCKET.nutrition(),
                            0.6F,
                            ModFoods.WATERMELON_JUICE_BUCKET.effects()));

    // —— 胡萝卜 ——
    public static final DeferredItem<DrinkBottleItem> CARROT_JUICE_BOTTLE =
            ITEMS.register("drink/carrot_juice_bottle",
                    () -> new DrinkBottleItem(new Item.Properties().stacksTo(16),
                            ModFoods.CARROT_JUICE_BOTTLE.nutrition(),
                            0.6F,
                            ModFoods.CARROT_JUICE_BOTTLE.effects()));

    public static final DeferredItem<DrinkGlassItem> CARROT_JUICE_GLASS =
            ITEMS.register("drink/carrot_juice_glass",
                    () -> new DrinkGlassItem(new Item.Properties().stacksTo(16),
                            ModFoods.CARROT_JUICE_GLASS.nutrition(),
                            0.6F,
                            ModFoods.CARROT_JUICE_GLASS.effects()));

    public static final DeferredItem<DrinkBucketItem> CARROT_JUICE_BUCKET =
            ITEMS.register("drink/carrot_juice_bucket",
                    () -> new DrinkBucketItem(new Item.Properties().stacksTo(1),
                            ModFoods.CARROT_JUICE_BUCKET.nutrition(),
                            0.6F,
                            ModFoods.CARROT_JUICE_BUCKET.effects()));

    // —— 可可 ——
    public static final DeferredItem<DrinkBottleItem> COCOA_BOTTLE =
            ITEMS.register("drink/cocoa_bottle",
                    () -> new DrinkBottleItem(new Item.Properties().stacksTo(16),
                            ModFoods.COCOA_BOTTLE.nutrition(),
                            0.3F,
                            ModFoods.COCOA_BOTTLE.effects()));

    public static final DeferredItem<DrinkGlassItem> COCOA_GLASS =
            ITEMS.register("drink/cocoa_glass",
                    () -> new DrinkGlassItem(new Item.Properties().stacksTo(16),
                            ModFoods.COCOA_GLASS.nutrition(),
                            0.3F,
                            ModFoods.COCOA_GLASS.effects()));

    public static final DeferredItem<DrinkBucketItem> COCOA_BUCKET =
            ITEMS.register("drink/cocoa_bucket",
                    () -> new DrinkBucketItem(new Item.Properties().stacksTo(1),
                            ModFoods.COCOA_BUCKET.nutrition(),
                            0.3F,
                            ModFoods.COCOA_BUCKET.effects()));

    // —— 甘蔗 ——
    public static final DeferredItem<DrinkGlassItem> SUGARCANE_JUICE_GLASS =
            ITEMS.register("drink/sugarcane_juice_glass",
                    () -> new DrinkGlassItem(new Item.Properties().stacksTo(16),
                            ModFoods.SUGARCANE_JUICE_GLASS.nutrition(),
                            0.0F,
                            ModFoods.SUGARCANE_JUICE_GLASS.effects()));

    public static final DeferredItem<DrinkBottleItem> SUGARCANE_JUICE_BOTTLE =
            ITEMS.register("drink/sugarcane_juice_bottle",
                    () -> new DrinkBottleItem(new Item.Properties().stacksTo(16),
                            ModFoods.SUGARCANE_JUICE_BOTTLE.nutrition(),
                            0.0F,
                            ModFoods.SUGARCANE_JUICE_BOTTLE.effects()));

    public static final DeferredItem<DrinkBucketItem> SUGARCANE_JUICE_BUCKET =
            ITEMS.register("drink/sugarcane_juice_bucket",
                    () -> new DrinkBucketItem(new Item.Properties().stacksTo(1),
                            ModFoods.SUGARCANE_JUICE_BUCKET.nutrition(),
                            0.0F,
                            ModFoods.SUGARCANE_JUICE_BUCKET.effects()));

    // —— 甜菜根 ——
    public static final DeferredItem<DrinkBottleItem> BEETROOT_JUICE_BOTTLE =
            ITEMS.register("drink/beetroot_juice_bottle",
                    () -> new DrinkBottleItem(new Item.Properties().stacksTo(16),
                            ModFoods.BEETROOT_JUICE_BOTTLE.nutrition(),
                            0.6F,
                            ModFoods.BEETROOT_JUICE_BOTTLE.effects()));

    public static final DeferredItem<DrinkGlassItem> BEETROOT_JUICE_GLASS =
            ITEMS.register("drink/beetroot_juice_glass",
                    () -> new DrinkGlassItem(new Item.Properties().stacksTo(16),
                            ModFoods.BEETROOT_JUICE_GLASS.nutrition(),
                            0.6F,
                            ModFoods.BEETROOT_JUICE_GLASS.effects()));

    public static final DeferredItem<DrinkBucketItem> BEETROOT_JUICE_BUCKET =
            ITEMS.register("drink/beetroot_juice_bucket",
                    () -> new DrinkBucketItem(new Item.Properties().stacksTo(1),
                            ModFoods.BEETROOT_JUICE_BUCKET.nutrition(),
                            0.6F,
                            ModFoods.BEETROOT_JUICE_BUCKET.effects()));

    // —— 浆果 ——
    public static final DeferredItem<DrinkGlassItem> SWEET_BERRY_JUICE_GLASS =
            ITEMS.register("drink/sweet_berry_juice_glass",
                    () -> new DrinkGlassItem(new Item.Properties().stacksTo(16),
                            ModFoods.SWEET_BERRY_JUICE_GLASS.nutrition(),
                            0.4F,
                            ModFoods.SWEET_BERRY_JUICE_GLASS.effects()));

    public static final DeferredItem<DrinkBottleItem> SWEET_BERRY_JUICE_BOTTLE =
            ITEMS.register("drink/sweet_berry_juice_bottle",
                    () -> new DrinkBottleItem(new Item.Properties().stacksTo(16),
                            ModFoods.SWEET_BERRY_JUICE_BOTTLE.nutrition(),
                            0.4F,
                            ModFoods.SWEET_BERRY_JUICE_BOTTLE.effects()));

    public static final DeferredItem<DrinkBucketItem> SWEET_BERRY_JUICE_BUCKET =
            ITEMS.register("drink/sweet_berry_juice_bucket",
                    () -> new DrinkBucketItem(new Item.Properties().stacksTo(1),
                            ModFoods.SWEET_BERRY_JUICE_BUCKET.nutrition(),
                            0.4F,
                            ModFoods.SWEET_BERRY_JUICE_BUCKET.effects()));

    public static final DeferredItem<DrinkGlassItem> GLOW_BERRY_JUICE_GLASS =
            ITEMS.register("drink/glow_berry_juice_glass",
                    () -> new DrinkGlassItem(new Item.Properties().stacksTo(16),
                            ModFoods.GLOW_BERRY_JUICE_GLASS.nutrition(),
                            0.4F,
                            ModFoods.GLOW_BERRY_JUICE_GLASS.effects()));

    public static final DeferredItem<DrinkBottleItem> GLOW_BERRY_JUICE_BOTTLE =
            ITEMS.register("drink/glow_berry_juice_bottle",
                    () -> new DrinkBottleItem(new Item.Properties().stacksTo(16),
                            ModFoods.GLOW_BERRY_JUICE_BOTTLE.nutrition(),
                            0.4F,
                            ModFoods.GLOW_BERRY_JUICE_BOTTLE.effects()));

    public static final DeferredItem<DrinkBucketItem> GLOW_BERRY_JUICE_BUCKET =
            ITEMS.register("drink/glow_berry_juice_bucket",
                    () -> new DrinkBucketItem(new Item.Properties().stacksTo(1),
                            ModFoods.GLOW_BERRY_JUICE_BUCKET.nutrition(),
                            0.4F,
                            ModFoods.GLOW_BERRY_JUICE_BUCKET.effects()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        LOGGER.info("Registering Mod Items for " + MiraculousOriginFoodMod.MODID);
    }
}
