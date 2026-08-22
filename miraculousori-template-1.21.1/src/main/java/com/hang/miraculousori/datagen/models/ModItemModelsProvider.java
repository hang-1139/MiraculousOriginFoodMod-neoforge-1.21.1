package com.hang.miraculousori.datagen.models;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MiraculousOriginFoodMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.FOOD_GOD_BLESSING_AMULET.get()); // 食神之佑
        basicItem(ModItems.GOD_GAZE.get()); // 神之瞥视
        basicItem(ModItems.HUNGER_CURSE_PUNISHMENT.get()); // 饥饿诅咒惩罚
        basicItem(ModItems.SATURATION_TOTEM.get()); // 饱和图腾
        basicItem(ModItems.HUNGER_CURSE_ODE.get()); // 饥饿诅咒颂
        basicItem(ModItems.HUNGER_TOTEM.get()); // 饥饿图腾
        basicItem(ModItems.ENDING_SPEECH.get()); // 终末之言
        basicItem(ModItems.END_ROAD_RUNE.get()); // 末途符文
        basicItem(ModItems.END_NEW_PATH.get()); // 末路新径

        basicItem(ModItems.RAW_BAGUETTE.get()); // 生法棍

        basicItem(ModItems.WHEAT_FLOUR.get()); // 小麦面粉
        basicItem(ModItems.WHEAT_DOUGH.get()); // 小麦面团
        basicItem(ModItems.NETHER_SOUL_WHEAT_FLOUR.get()); // 下界灵魂小麦面粉
        basicItem(ModItems.NETHER_SOUL_WHEAT_DOUGH.get()); // 下界灵魂小麦面团
        basicItem(ModItems.SUGAR_CUBE.get()); // 方糖
        basicItem(ModItems.CARAMEL.get()); // 焦糖
        basicItem(ModItems.WET_CLAY_BALL.get()); // 湿润的粘土球
        basicItem(ModItems.COOKIE_DOUGH.get()); // 饼干面团
        basicItem(ModItems.RAW_COOKIE.get()); // 生饼干
        basicItem(ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get()); // 生下界灵魂小麦饼干
        basicItem(ModItems.COOKIE_MOLD.get()); // 饼干模具
        basicItem(ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get()); // 有小麦饼干胚的模具
        basicItem(ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get()); // 下界灵魂小麦饼干模具
        basicItem(ModItems.BLAZE_INCENSE_POWDER.get()); // 烈焰香粉
        basicItem(ModItems.NETHER_INCENSE_POWDER.get()); // 下界香粉
        basicItem(ModItems.CRIMSON_FRUIT_POWDER.get()); // 红色果实香粉
        basicItem(ModItems.WARPED_FRUIT_POWDER.get()); // 畸形果实香粉
        basicItem(ModItems.NETHER_SOUL_WHEAT_SEEDS.get()); // 下界灵魂小麦种子
        basicItem(ModItems.SOUL_WHEAT.get()); // 下界灵魂小麦
        basicItem(ModItems.NETHER_SOUL_WHEAT_FLOUR.get()); // 下界灵魂小麦面粉
        basicItem(ModItems.NETHER_SOUL_WHEAT_DOUGH.get()); // 下界灵魂小麦面团
        basicItem(ModItems.DIVINE_UPGRADE_TEMPLATE.get()); // 神圣升级模板
        basicItem(ModItems.CRUSHED_SALT.get()); // 粗盐
        basicItem(ModItems.SALT.get()); // 盐

        basicItem(ModItems.VEGETABLE_CLAY_CAKE.get()); // 掺菜粘土饼
        basicItem(ModItems.CLAY_CAKE.get()); // 粘土饼
        basicItem(ModItems.SWEET_CLAY_CAKE.get()); // 甜粘土饼
        basicItem(ModItems.ENDER_CLAY_CAKE.get()); // 末影粘土饼

        basicItem(ModItems.VEGETABLE_COOKED_CLAY_CAKE.get()); // 烹饪后的蔬菜粘土蛋糕
        basicItem(ModItems.COOKED_CLAY_CAKE.get()); // 烹饪后的粘土蛋糕
        basicItem(ModItems.SWEET_COOKED_CLAY_CAKE.get()); // 烹饪后的甜粘土蛋糕
        basicItem(ModItems.WHEAT_COOKIE.get()); // 小麦饼干
        basicItem(ModItems.COMPRESSED_WHEAT_BISCUIT.get()); // 压实的小麦饼干
        basicItem(ModItems.ENDER_COOKED_CLAY_CAKE.get()); // 末影烹饪后的粘土蛋糕
        basicItem(ModItems.NETHER_CLAY_CAKE.get()); // 下界粘土饼
        basicItem(ModItems.NETHER_SOUL_WHEAT_COOKIE.get()); // 下界灵魂小麦饼干
        basicItem(ModItems.WARPED_FRUIT.get()); // 畸形果实
        basicItem(ModItems.CRIMSON_FRUIT.get()); // 红色果实
        basicItem(ModItems.LEAPING_FRUIT.get()); // 跳跃果实
        basicItem(ModItems.FLOATING_MELON.get()); // 浮动的西瓜
        basicItem(ModItems.BROKEN_FLOATING_GOURD.get()); // 破损的悬浮南瓜
        basicItem(ModItems.FLOATING_MELON_SEEDS.get()); // 浮动的西瓜种子
        basicItem(ModItems.FLOATING_MELON_JAM.get()); // 浮瓜果酱
        basicItem(ModItems.FLOATING_MELON_PIE.get()); // 浮瓜派

        basicItem(ModItems.VERY_SWEET_BREAD.get()); // 极其甜美的面包
        basicItem(ModItems.WHEAT_BREAD.get()); // 小麦面包
        basicItem(ModItems.HONEY_BREAD.get()); // 蜂蜜面包
        basicItem(ModItems.BAGUETTE.get()); // 法棍
        basicItem(ModItems.LARGE_BAGUETTE_HALF.get()); // 大法棍半块
        basicItem(ModItems.SMALL_BAGUETTE_HALF.get()); // 小法棍半块
        basicItem(ModItems.CHINESE_SUGAR_FREE_BREAD.get()); // 中式无糖面包

        // 懒得分类
        basicItem(ModItems.GLASS_CUP.get()); // 玻璃杯
        basicItem(ModItems.APPLE_PULP.get()); // 苹果泥
        basicItem(ModItems.WATERMELON_PULP.get()); // 西瓜泥
        basicItem(ModItems.CARROT_PULP.get()); // 甜菜泥
        basicItem(ModItems.BEETROOT_PULP.get()); // 畸形果实泥
        basicItem(ModItems.SWEET_BERRY_PULP.get()); // 红色果实泥
        basicItem(ModItems.GLOW_BERRY_PULP.get()); // 发光果实泥
        basicItem(ModItems.SUGARCANE_PULP.get()); // 糖 cane 泥
        basicItem(ModItems.COCOA_POWDER.get()); // 可可粉
        basicItem(ModItems.COCOA_BUTTER_MOLD.get()); // 可可黄油模具
        basicItem(ModItems.WATER_MOLD.get()); // 水模具
        basicItem(ModItems.DARK_CHOCOLATE_PASTE_MOLD.get()); // 深色巧克力浆模具
        basicItem(ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get()); // 白色巧克力填充模具
        basicItem(ModItems.WATER_GLASS.get()); // 水杯
        basicItem(ModItems.APPLE_JUICE_BOTTLE.get()); // 苹果汁瓶
        basicItem(ModItems.APPLE_JUICE_GLASS.get()); // 苹果汁玻璃杯
        basicItem(ModItems.APPLE_JUICE_BUCKET.get()); // 苹果汁桶
        basicItem(ModItems.WATERMELON_JUICE_BOTTLE.get()); // 西瓜果汁瓶
        basicItem(ModItems.WATERMELON_JUICE_GLASS.get()); // 西瓜果汁玻璃杯
        basicItem(ModItems.WATERMELON_JUICE_BUCKET.get()); // 西瓜果汁桶
        basicItem(ModItems.CARROT_JUICE_BOTTLE.get()); // 甜菜果汁瓶
        basicItem(ModItems.CARROT_JUICE_GLASS.get()); // 甜菜果汁玻璃杯
        basicItem(ModItems.CARROT_JUICE_BUCKET.get()); // 甜菜果汁桶
        basicItem(ModItems.COCOA_BOTTLE.get()); // 可可黄油瓶
        basicItem(ModItems.COCOA_GLASS.get()); // 可可黄油玻璃杯
        basicItem(ModItems.COCOA_BUCKET.get()); // 可可黄油桶
        basicItem(ModItems.SUGARCANE_JUICE_GLASS.get()); // 糖 cane 玻璃杯
        basicItem(ModItems.SUGARCANE_JUICE_BOTTLE.get()); // 糖 cane 瓶
        basicItem(ModItems.SUGARCANE_JUICE_BUCKET.get()); // 糖 cane 桶
        basicItem(ModItems.BEETROOT_JUICE_BOTTLE.get()); // 畸形果实果汁瓶
        basicItem(ModItems.BEETROOT_JUICE_GLASS.get()); // 畸形果实果汁玻璃杯
        basicItem(ModItems.BEETROOT_JUICE_BUCKET.get()); // 畸形果实果汁桶
        basicItem(ModItems.SWEET_BERRY_JUICE_GLASS.get()); // 红色果实果汁玻璃杯
        basicItem(ModItems.SWEET_BERRY_JUICE_BOTTLE.get()); // 红色果实果汁瓶
        basicItem(ModItems.SWEET_BERRY_JUICE_BUCKET.get()); // 红色果实果汁桶
        basicItem(ModItems.GLOW_BERRY_JUICE_GLASS.get()); // 发光果实果汁玻璃杯
        basicItem(ModItems.GLOW_BERRY_JUICE_BOTTLE.get()); // 发光果实果汁瓶
        basicItem(ModItems.GLOW_BERRY_JUICE_BUCKET.get()); // 发光果实果汁桶
        basicItem(ModItems.COOKED_POTATO_PATTY.get()); // 烹饪后的土豆饼
        basicItem(ModItems.DARK_CHOCOLATE.get()); // 深色巧克力
        basicItem(ModItems.WHITE_CHOCOLATE.get()); // 白色巧克力
        basicItem(ModItems.MASHED_POTATO.get()); // 煮熟的土豆
        basicItem(ModItems.RAW_POTATO_PATTY.get()); // 生土豆饼

    }
}
