package com.hang.miraculousori.item;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.hang.miraculousori.MiraculousOriginFoodMod.LOGGER;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> MIRACULOUS_ORIGIN_FOOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MiraculousOriginFoodMod.MODID);

    public static final Supplier<CreativeModeTab> MIRACULOUS_ORIGIN_FOOD_TAB = MIRACULOUS_ORIGIN_FOOD_TABS.register("miraculous_origin_food_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.WET_CLAY_BALL.get()))
            .title(Component.translatable("itemGroup.miraculous_origin_food_tab"))
            .displayItems((parameters, output) -> {
                /* 食品 */
                output.accept(ModItems.VEGETABLE_COOKED_CLAY_CAKE.get()); // 熟的掺菜土饼
                output.accept(ModItems.SWEET_COOKED_CLAY_CAKE.get()); // 甜味的熟土饼
                output.accept(ModItems.COOKED_CLAY_CAKE.get()); // 熟的 clay 糕
                output.accept(ModItems.WHEAT_COOKIE.get()); // 饼干
                output.accept(ModItems.COMPRESSED_WHEAT_BISCUIT.get()); // 压缩的小麦饼干
                output.accept(ModItems.VERY_SWEET_BREAD.get()); // 非常甜的面包
                output.accept(ModItems.CHINESE_SUGAR_FREE_BREAD.get()); // 中式无糖面包
                output.accept(ModItems.WHEAT_BREAD.get()); // 小麦面包
                output.accept(ModItems.HONEY_BREAD.get()); // 蜂蜜面包
                output.accept(ModItems.RAW_BAGUETTE.get()); // 生法棍
                output.accept(ModItems.BAGUETTE.get()); // 法棍
                output.accept(ModItems.LARGE_BAGUETTE_HALF.get()); // 大法棍的一半
                output.accept(ModItems.SMALL_BAGUETTE_HALF.get()); // 小法棍的一半
                output.accept(ModItems.MASHED_POTATO.get()); // 土豆泥
                output.accept(ModItems.RAW_POTATO_PATTY.get()); // 生的土豆饼
                output.accept(ModItems.COOKED_POTATO_PATTY.get()); // 熟的土豆饼
                output.accept(ModItems.DARK_CHOCOLATE.get()); // 深色巧克力
                output.accept(ModItems.WHITE_CHOCOLATE.get()); // 白色巧克力

                /* 饮品 */
                output.accept(ModItems.APPLE_JUICE_BOTTLE.get()); // 苹果汁瓶
                output.accept(ModItems.WATERMELON_JUICE_BOTTLE.get()); // 西瓜果汁瓶
                output.accept(ModItems.CARROT_JUICE_BOTTLE.get()); // 胡萝卜果汁瓶
                output.accept(ModItems.COCOA_BOTTLE.get()); // 可可脂瓶
                output.accept(ModItems.SUGARCANE_JUICE_BOTTLE.get()); // 甘蔗汁瓶
                output.accept(ModItems.BEETROOT_JUICE_BOTTLE.get()); // 甜菜根汁瓶
                output.accept(ModItems.SWEET_BERRY_JUICE_BOTTLE.get()); // 甜莓果汁瓶
                output.accept(ModItems.GLOW_BERRY_JUICE_BOTTLE.get()); // 发光莓果汁瓶
                output.accept(ModItems.WATER_GLASS.get()); // 水杯
                output.accept(ModItems.APPLE_JUICE_GLASS.get()); // 苹果汁杯
                output.accept(ModItems.WATERMELON_JUICE_GLASS.get()); // 西瓜果汁杯
                output.accept(ModItems.CARROT_JUICE_GLASS.get()); // 胡萝卜果汁杯
                output.accept(ModItems.COCOA_GLASS.get()); // 可可脂杯
                output.accept(ModItems.SUGARCANE_JUICE_GLASS.get()); // 甘蔗汁杯
                output.accept(ModItems.BEETROOT_JUICE_GLASS.get()); // 甜菜根汁杯
                output.accept(ModItems.SWEET_BERRY_JUICE_GLASS.get()); // 甜莓果汁杯
                output.accept(ModItems.GLOW_BERRY_JUICE_GLASS.get()); // 发光莓果汁杯
                output.accept(ModItems.WATERMELON_JUICE_BUCKET.get()); // 西瓜果汁桶
                output.accept(ModItems.SWEET_BERRY_JUICE_BUCKET.get()); // 甜莓果汁桶
                output.accept(ModItems.GLOW_BERRY_JUICE_BUCKET.get()); // 发光莓果汁桶
                output.accept(ModItems.APPLE_JUICE_BUCKET.get()); // 苹果汁桶
                output.accept(ModItems.CARROT_JUICE_BUCKET.get()); // 胡萝卜果汁桶
                output.accept(ModItems.COCOA_BUCKET.get()); // 可可脂桶
                output.accept(ModItems.BEETROOT_JUICE_BUCKET.get()); // 甜菜根汁桶
                output.accept(ModItems.SUGARCANE_JUICE_BUCKET.get()); // 甘蔗汁桶

                /* 普通物品 */
                output.accept(ModItems.DIVINE_UPGRADE_TEMPLATE.get()); // 神圣升级模板
                output.accept(ModItems.SATURATION_TOTEM.get()); // 饱和图腾
                output.accept(ModItems.FOOD_GOD_BLESSING_AMULET.get()); // 食神之佑
                output.accept(ModItems.GOD_GAZE.get()); // 神之注视
                output.accept(ModItems.WHEAT_FLOUR.get()); // 小麦粉
                output.accept(ModItems.WHEAT_DOUGH.get()); // 小麦面团
                output.accept(ModItems.SUGAR_CUBE.get()); // 方糖
                output.accept(ModItems.CARAMEL.get()); // 焦糖
                output.accept(ModItems.COOKIE_DOUGH.get()); // 饼干面团
                output.accept(ModItems.RAW_COOKIE.get()); // 生饼干
                output.accept(ModItems.COOKIE_MOLD.get()); // 饼干模具
//                output.accept(ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get()); // 饼干面团填充模具
//                output.accept(ModItems.COCOA_BUTTER_MOLD.get()); // 可可脂模具
//                output.accept(ModItems.WATER_MOLD.get()); // 水模具
//                output.accept(ModItems.DARK_CHOCOLATE_PASTE_MOLD.get()); // 深色巧克力浆模具
//                output.accept(ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get()); // 白色巧克力填充模具
                output.accept(ModItems.WET_CLAY_BALL.get()); // 湿 clay 球
                output.accept(ModItems.VEGETABLE_CLAY_CAKE.get()); // 蔬菜 clay 糕
                output.accept(ModItems.CLAY_CAKE.get()); // clay 糕
                output.accept(ModItems.SWEET_CLAY_CAKE.get()); // 甜味 clay 糕
                output.accept(ModItems.GLASS_CUP.get()); // 玻璃杯
                output.accept(ModItems.WATERMELON_PULP.get()); // 西瓜渣
                output.accept(ModItems.APPLE_PULP.get()); // 苹果渣
                output.accept(ModItems.CARROT_PULP.get()); // 胡萝卜渣
                output.accept(ModItems.COCOA_POWDER.get()); // 可可粉
                output.accept(ModItems.BEETROOT_PULP.get()); // 甜菜根渣
                output.accept(ModItems.SWEET_BERRY_PULP.get()); // 蓝莓渣
                output.accept(ModItems.GLOW_BERRY_PULP.get()); // 发光莓果汁渣
                output.accept(ModItems.SUGARCANE_PULP.get()); // 甘蔗渣
                output.accept(ModItems.CRUSHED_SALT.get()); // 碎盐
                output.accept(ModItems.SALT.get()); // 盐

                /* 方块 */
                output.accept(ModBlocks.SUGAR_BLOCK.get());
                output.accept(ModBlocks.MILL_BLOCK.get());
                output.accept(ModBlocks.SALT_ROCK.get());
                output.accept(ModBlocks.DEEPSLATE_SALT_ROCK.get());

            }).build());

    public static final Supplier<CreativeModeTab> MIRACULOUS_ORIGIN_FOOD_THE_NETHER_TAB = MIRACULOUS_ORIGIN_FOOD_TABS.register("miraculous_origin_food_the_nether_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.BLAZE_INCENSE_POWDER.get()))
            .title(Component.translatable("itemGroup.miraculous_origin_food_the_nether_tab"))
            .displayItems((parameters, output) -> {
                /* 食品 */
                output.accept(ModItems.NETHER_SOUL_WHEAT_COOKIE.get()); // 炼狱灵魂小麦饼干
                output.accept(ModItems.NETHER_CLAY_CAKE.get()); // 炼狱的 clay 糕

                /* 普通物品 */
                output.accept(ModItems.HUNGER_TOTEM.get()); // 饥饿图腾
                output.accept(ModItems.HUNGER_CURSE_PUNISHMENT.get()); // 饥饿诅咒惩罚
                output.accept(ModItems.HUNGER_CURSE_ODE.get()); // 饥饿诅咒颂
                output.accept(ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get()); // 炼狱灵魂小麦生饼干
//                output.accept(ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get()); // 炼狱灵魂小麦饼干模具
                output.accept(ModItems.BLAZE_INCENSE_POWDER.get()); // 炼狱熏香粉
                output.accept(ModItems.NETHER_INCENSE_POWDER.get()); // 下界香粉
                output.accept(ModItems.CRIMSON_FRUIT.get()); // 炼獄果实
                output.accept(ModItems.CRIMSON_FRUIT_POWDER.get()); // 炼狱果实粉
                output.accept(ModItems.WARPED_FRUIT.get()); // 異界果實
                output.accept(ModItems.WARPED_FRUIT_POWDER.get()); // 異界果實粉
                output.accept(ModItems.NETHER_SOUL_WHEAT_SEEDS.get()); // 炼狱灵魂小麦种子
                output.accept(ModItems.NETHER_SOUL_WHEAT_FLOUR.get()); // 炼狱灵魂小麦粉
                output.accept(ModItems.SOUL_WHEAT.get()); // 炼狱灵魂小麦
                output.accept(ModItems.NETHER_SOUL_WHEAT_DOUGH.get()); // 炼狱灵魂小麦面团

                /* 方块 */
                output.accept(ModBlocks.WITHERED_SOULWEED.get()); //

            }).withTabsBefore(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "miraculous_origin_food_tab"))
            .build());

    public static final Supplier<CreativeModeTab> MIRACULOUS_ORIGIN_FOOD_THE_END_TAB = MIRACULOUS_ORIGIN_FOOD_TABS.register("miraculous_origin_food_the_end_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.ENDER_COOKED_CLAY_CAKE.get()))
            .title(Component.translatable("itemGroup.miraculous_origin_food_the_end_tab"))
            .displayItems((parameters, output) -> {
                /* 食品 */
                output.accept(ModItems.ENDER_COOKED_CLAY_CAKE.get()); // 末影的熟土饼
                output.accept(ModItems.LEAPING_FRUIT.get()); // 跳跃果实
                output.accept(ModItems.FLOATING_MELON_SEEDS.get()); // 浮動的西瓜種子
                output.accept(ModItems.FLOATING_MELON.get()); // 浮動的西瓜
                output.accept(ModItems.BROKEN_FLOATING_GOURD.get()); // 破損的Floating Gourd
                output.accept(ModItems.FLOATING_MELON_PIE.get()); // 浮瓜派

                /* 普通物品 */
                output.accept(ModItems.ENDING_SPEECH.get()); // 終末的言葉
                output.accept(ModItems.END_ROAD_RUNE.get());
                output.accept(ModItems.END_NEW_PATH.get());
                output.accept(ModItems.ENDER_CLAY_CAKE.get()); // 末影的 clay 糕
                output.accept(ModItems.FLOATING_MELON_JAM.get()); // 浮瓜果酱


                /* 方块 */
                output.accept(ModBlocks.FLOATING_MELON_BLOCK.get());
                output.accept(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get());
                output.accept(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get());
                output.accept(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get());

            }).withTabsBefore(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "miraculous_origin_food_the_nether_tab"))
            .build());

    public static void register(IEventBus eventBus) {
        MIRACULOUS_ORIGIN_FOOD_TABS.register(eventBus);
        LOGGER.info("Registering Mod Creative Mode Tabs for " + MiraculousOriginFoodMod.MODID);
    }
}
