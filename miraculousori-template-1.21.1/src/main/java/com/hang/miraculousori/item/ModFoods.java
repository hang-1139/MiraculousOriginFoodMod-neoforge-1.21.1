package com.hang.miraculousori.item;

import com.hang.miraculousori.effect.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    /* 食物 */
    public static final FoodProperties VEGETABLE_COOKED_CLAY_CAKE = (new FoodProperties.Builder()).nutrition(6).saturationModifier(10.0F).build(); // 烹饪后的蔬菜粘土蛋糕
    public static final FoodProperties SWEET_COOKED_CLAY_CAKE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(10.0F) // 烹饪后的甜粘土蛋糕
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*30,0, false, false, true), 1.0F).build(); // 烹饪后的甜粘土蛋糕
    public static final FoodProperties COOKED_CLAY_CAKE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(10.0F).build(); // 烹饪后的粘土蛋糕
    public static final FoodProperties ENDER_COOKED_CLAY_CAKE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(10.0F)
            .effect(() -> new MobEffectInstance(ModMobEffects.TELEPORT_ON_DAMAGE, 20*60,0, false, false, true), 1.0F).build(); // 烹饪后的末影粘土蛋糕
    public static final FoodProperties NETHER_CLAY_CAKE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(10.0F) // 烹饪后的地狱粘土蛋糕
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20*15,0, false, false, true), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 20*15,0, false, false, true), 1.0F)
            .effect(() -> new MobEffectInstance(ModMobEffects.HUNGER_AFFLICTION, 20*60*2,0, false, false, true), 1.0F).build();

    public static final FoodProperties NETHER_SOUL_WHEAT_COOKIE = (new FoodProperties.Builder()).nutrition(8).saturationModifier(1.6F)
            .effect(() -> new MobEffectInstance(ModMobEffects.SATURATION_DECAY, 20*60*2,0, false, false, true), 1.0F).build(); // 烂魂小麦饼干
    public static final FoodProperties WHEAT_COOKIE = (new FoodProperties.Builder()).nutrition(6).saturationModifier(1.6F)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 20*40,0, false, false, true), 1.0F).build(); // 饼干
    public static final FoodProperties COMPRESSED_WHEAT_BISCUIT = (new FoodProperties.Builder()).nutrition(20).saturationModifier(1.0F) // 压缩饼干
            .effect(() -> new MobEffectInstance(ModMobEffects.OVERFULL, 20*60*2,0, false, false, true), 1.0F)
            .effect(() -> new MobEffectInstance(ModMobEffects.STUFFED, 20*60*2,3, false, false, true), 1.0F).build();

    public static final FoodProperties CRIMSON_FRUIT = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.6F).alwaysEdible() // 猩红果实
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20*15,0, false, false, true), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 20*8, 0,false, false, true), 0.7F)
            .effect(() -> new MobEffectInstance(MobEffects.HARM, 1,0, false, false, false), 0.3F).build();
    public static final FoodProperties WARPED_FRUIT = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.6F).alwaysEdible() // 诡异果实
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 20*15,0, false, false, true), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 20*7,0, false, false, true), 0.7F)
            .effect(() -> new MobEffectInstance(MobEffects.HARM, 1,0, false, false, false), 0.3F).build();
    public static final FoodProperties LEAPING_FRUIT = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.5F).alwaysEdible()
            .fast()// 跃进果实
            .effect(() -> new MobEffectInstance(ModMobEffects.DASH, 1,0, false, false, true), 1.0F).build();
    public static final FoodProperties FLOATING_MELON = (new FoodProperties.Builder()).nutrition(8).saturationModifier(0.6F) // 浮瓜
            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 20*15*4,0, false, false, true), 1.0F).build();
    public static final FoodProperties BROKEN_FLOATING_GOURD = (new FoodProperties.Builder()).nutrition(8).saturationModifier(0.6F).build(); // 破损的浮瓜

    public static final FoodProperties WHEAT_BREAD = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.6F).build(); // 小麦面包

    public static final FoodProperties HONEY_BREAD = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.6F) // 蜂蜜面包
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*30, 1,false, false, true), 0.7F).build();

    public static final FoodProperties BAGUETTE = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.6F).build(); //法棍
    public static final FoodProperties LARGE_BAGUETTE_HALF = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.6F).build(); //大半法棍
    public static final FoodProperties SMALL_BAGUETTE_HALF = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.6F).build(); //小半法棍

    public static final FoodProperties CHINESE_SUGAR_FREE_BREAD = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.6F).build(); //中国无糖面包
    public static final FoodProperties MASHED_POTATO = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.6F).build(); //土豆泥
    public static final FoodProperties COOKED_POTATO_PATTY = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(ModMobEffects.INVIGORATE, 20*40, 0,false, false, true), 1.0F)
            .effect(() -> new MobEffectInstance(ModMobEffects.INVIGORATE, 20*40, 2,false, false, true), 0.3F).build(); //土豆饼
    public static final FoodProperties DARK_CHOCOLATE = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.4F).build(); //黑巧克力
    public static final FoodProperties WHITE_CHOCOLATE = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.4F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*40, 1,false, false, true), 0.7F).build(); //白巧克力

    public static final FoodProperties FLOATING_MELON_PIE = (new FoodProperties.Builder())
            .nutrition(16)
            .saturationModifier(0.45F)
            .effect(() -> new MobEffectInstance(ModMobEffects.REVERSED_GRAVITY, 26 * 20, 0), 1.0F) // 26秒反转重力
            .build();

    /* 饮品 */
    // —— 水 ——
    public static final FoodProperties WATER_GLASS = (new FoodProperties.Builder())
            .nutrition(0).saturationModifier(0F).build();
    // —— 苹果 ——
    public static final FoodProperties APPLE_JUICE_BOTTLE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.6F).build();
    public static final FoodProperties APPLE_JUICE_GLASS = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.6F).build();
    public static final FoodProperties APPLE_JUICE_BUCKET = (new FoodProperties.Builder())
            .nutrition(16).saturationModifier(0.6F).build();

    // —— 西瓜 ——
    public static final FoodProperties WATERMELON_JUICE_BOTTLE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.6F).build();
    public static final FoodProperties WATERMELON_JUICE_GLASS = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.6F).build();
    public static final FoodProperties WATERMELON_JUICE_BUCKET = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.6F).build();

    // —— 胡萝卜 ——
    public static final FoodProperties CARROT_JUICE_BOTTLE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.6F).build();
    public static final FoodProperties CARROT_JUICE_GLASS = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.6F).build();
    public static final FoodProperties CARROT_JUICE_BUCKET = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(0.6F).build();
    // —— 可可 ——
    // 瓶装可可脂
    public static final FoodProperties COCOA_BOTTLE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3F).build();
    public static final FoodProperties COCOA_GLASS = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties COCOA_BUCKET = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.3F).build();
    //-甘蔗-
    public static final FoodProperties SUGARCANE_JUICE_GLASS = (new FoodProperties.Builder())
            .nutrition(0).saturationModifier(0F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*30,0, false, false, true), 1.0F).build();
    public static final FoodProperties SUGARCANE_JUICE_BOTTLE = (new FoodProperties.Builder())
            .nutrition(0).saturationModifier(0F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*60,0, false, false, true), 1.0F).build();
    public static final FoodProperties SUGARCANE_JUICE_BUCKET = (new FoodProperties.Builder())
            .nutrition(0).saturationModifier(0F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*60*2,0, false, false, true), 1.0F).build();
    // —— 甜菜根汁 ——
    public static final FoodProperties BEETROOT_JUICE_BOTTLE = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*20,0, false, false, true), 1.0F).build();
    public static final FoodProperties BEETROOT_JUICE_GLASS = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*40,0, false, false, true), 1.0F).build();
    public static final FoodProperties BEETROOT_JUICE_BUCKET = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*80*2,0, false, false, true), 1.0F).build();
    // —— 浆果 ——
    public static final FoodProperties SWEET_BERRY_JUICE_GLASS = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.4F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*20,0, false, false, true), 1.0F).build();
    public static final FoodProperties SWEET_BERRY_JUICE_BOTTLE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.4F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*40,0, false, false, true), 1.0F).build();
    public static final FoodProperties SWEET_BERRY_JUICE_BUCKET = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.4F)
            .effect(() -> new MobEffectInstance(ModMobEffects.PLEASURE, 20*80,0, false, false, true), 1.0F).build();
    public static final FoodProperties GLOW_BERRY_JUICE_GLASS = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.4F).build();
    public static final FoodProperties GLOW_BERRY_JUICE_BOTTLE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.4F).build();
    public static final FoodProperties GLOW_BERRY_JUICE_BUCKET = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.4F).build();
}

