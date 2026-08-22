package com.hang.miraculousori.datagen.lang;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.entity.ModEntities;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, MiraculousOriginFoodMod.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {

        /* 普通物品 */
        add(ModItems.SATURATION_TOTEM.get(), "§p饱和图腾");
        add(ModItems.FOOD_GOD_BLESSING_AMULET.get(), "§6食神之佑");
        add(ModItems.GOD_GAZE.get(), "§4神之瞥视");
        add(ModItems.HUNGER_CURSE_PUNISHMENT.get(), "§4饥灾咒刑");
        add(ModItems.HUNGER_CURSE_ODE.get(), "§0饥馑诅颂");
        add(ModItems.HUNGER_TOTEM.get(), "§7饥馑图腾");
        add(ModItems.ENDING_SPEECH.get(), "§d终末之言");
        add(ModItems.END_ROAD_RUNE.get(), "§b末途符文");
        add(ModItems.END_NEW_PATH.get(), "§5终幕新途");

        add(ModItems.RAW_BAGUETTE.get(), "生法棍");
        add(ModItems.RAW_POTATO_PATTY.get(), "生土豆饼");

        add(ModItems.WHEAT_FLOUR.get(), "小麦面粉");
        add(ModItems.WHEAT_DOUGH.get(), "白面团");
        add(ModItems.SOUL_WHEAT.get(), "缠魂麦穗");
        add(ModItems.NETHER_SOUL_WHEAT_FLOUR.get(), "缠魂麦面粉");
        add(ModItems.NETHER_SOUL_WHEAT_DOUGH.get(), "缠魂麦面团");
        add(ModItems.SUGAR_CUBE.get(), "方糖");
        add(ModItems.CARAMEL.get(), "焦糖");
        add(ModItems.WET_CLAY_BALL.get(), "湿润的粘土球");
        add(ModItems.COOKIE_DOUGH.get(), "鸡蛋面团");
        add(ModItems.RAW_COOKIE.get(), "生饼干");
        add(ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get(), "缠魂麦生饼干");
        add(ModItems.COOKIE_MOLD.get(), "模具");
        add(ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get(), "有小麦饼干胚的模具");
        add(ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get(), "有缠魂麦饼干胚的模具");
        add(ModItems.BLAZE_INCENSE_POWDER.get(), "烈焰香粉");
        add(ModItems.NETHER_INCENSE_POWDER.get(), "下界香粉");
        add(ModItems.CRIMSON_FRUIT_POWDER.get(), "猩红果实粉");
        add(ModItems.WARPED_FRUIT_POWDER.get(), "诡异果实粉");
        add(ModItems.NETHER_SOUL_WHEAT_SEEDS.get(), "缠魂麦种");
        add(ModItems.DIVINE_UPGRADE_TEMPLATE.get(), "§b神性模板");
        add(ModItems.BEETROOT_PULP.get(), "甜菜根渣");
        add(ModItems.SWEET_BERRY_PULP.get(), "浆果渣");
        add(ModItems.SUGARCANE_PULP.get(), "甘蔗渣");
        add(ModItems.GLOW_BERRY_PULP.get(), "发光浆果渣");
        add(ModItems.COCOA_BUTTER_MOLD.get(), "含可可脂模具");
        add(ModItems.WATER_MOLD.get(), "含水模具");
        add(ModItems.DARK_CHOCOLATE_PASTE_MOLD.get(), "含黑巧克力模具");
        add(ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get(), "含白巧克力模具");
        add(ModItems.WITHERED_SOULWEED.get(), "幽魂枯草");
        add(ModItems.CRUSHED_SALT.get(), "粗盐");
        add(ModItems.SALT.get(), "盐");
        add(ModItems.FLOATING_MELON_SEEDS.get(), "浮瓜种子");

        add(ModItems.VEGETABLE_CLAY_CAKE.get(), "掺菜粘土饼");
        add(ModItems.CLAY_CAKE.get(), "粘土饼");
        add(ModItems.SWEET_CLAY_CAKE.get(), "掺糖粘土饼");
        add(ModItems.ENDER_CLAY_CAKE.get(), "末影粘土饼");

        /* 食品 */
        add(ModItems.VEGETABLE_COOKED_CLAY_CAKE.get(), "掺菜土饼");
        add(ModItems.SWEET_COOKED_CLAY_CAKE.get(), "掺糖土饼");
        add(ModItems.COOKED_CLAY_CAKE.get(), "熟土饼");
        add(ModItems.ENDER_COOKED_CLAY_CAKE.get(), "末影土饼");
        add(ModItems.WHEAT_COOKIE.get(), "小麦饼干");
        add(ModItems.COMPRESSED_WHEAT_BISCUIT.get(), "压缩小麦饼干");
        add(ModItems.NETHER_CLAY_CAKE.get(), "下界土饼");
        add(ModItems.NETHER_SOUL_WHEAT_COOKIE.get(), "缠魂麦饼干");
        add(ModItems.WARPED_FRUIT.get(), "诡异果实");
        add(ModItems.CRIMSON_FRUIT.get(), "猩红果实");
        add(ModItems.VERY_SWEET_BREAD.get(), "齁甜面包");
        add(ModItems.WHEAT_BREAD.get(), "小麦面包");
        add(ModItems.HONEY_BREAD.get(), "蜂蜜面包");
        add(ModItems.BAGUETTE.get(), "法棍");
        add(ModItems.LARGE_BAGUETTE_HALF.get(), "大半法棍");
        add(ModItems.SMALL_BAGUETTE_HALF.get(), "小半法棍");
        add(ModItems.CHINESE_SUGAR_FREE_BREAD.get(), "馒头");
        add(ModItems.MASHED_POTATO.get(), "土豆泥");
        add(ModItems.COOKED_POTATO_PATTY.get(), "土豆饼");
        add(ModItems.DARK_CHOCOLATE.get(), "黑巧克力");
        add(ModItems.WHITE_CHOCOLATE.get(), "白巧克力");
        add(ModItems.LEAPING_FRUIT.get(), "跃进果");
        add(ModItems.FLOATING_MELON.get(), "浮瓜");
        add(ModItems.BROKEN_FLOATING_GOURD.get(), "破浮瓜");
        add(ModItems.FLOATING_MELON_JAM.get(), "浮瓜果酱");
        add(ModItems.FLOATING_MELON_PIE.get(), "浮瓜派");

        /* 饮品 */
        add(ModItems.WATER_GLASS.get(), "一杯水");
        add(ModItems.APPLE_JUICE_BOTTLE.get(), "瓶装苹果汁");
        add(ModItems.APPLE_JUICE_GLASS.get(), "杯装苹果汁");
        add(ModItems.APPLE_PULP.get(), "苹果渣");
        add(ModItems.WATERMELON_JUICE_BOTTLE.get(), "瓶装西瓜汁");
        add(ModItems.WATERMELON_JUICE_GLASS.get(), "杯装西瓜汁");
        add(ModItems.WATERMELON_PULP.get(), "西瓜渣");
        add(ModItems.CARROT_JUICE_BOTTLE.get(), "瓶装胡萝卜汁");
        add(ModItems.CARROT_JUICE_GLASS.get(), "杯装胡萝卜汁");
        add(ModItems.CARROT_PULP.get(), "胡萝卜渣");
        add(ModItems.COCOA_BOTTLE.get(), "瓶装脱脂可可饮");
        add(ModItems.COCOA_GLASS.get(), "杯装脱脂可可饮");
        add(ModItems.COCOA_POWDER.get(), "脱脂可可粉");
        add(ModItems.GLASS_CUP.get(), "玻璃杯");
        add(ModItems.SUGARCANE_JUICE_BOTTLE.get(), "瓶装甘蔗汁");
        add(ModItems.BEETROOT_JUICE_BOTTLE.get(), "瓶装甜菜根汁");
        add(ModItems.SUGARCANE_JUICE_GLASS.get(), "杯装甘蔗汁");
        add(ModItems.BEETROOT_JUICE_GLASS.get(), "杯装甜菜根汁");
        add(ModItems.SWEET_BERRY_JUICE_BOTTLE.get(), "瓶装甜浆果汁");
        add(ModItems.GLOW_BERRY_JUICE_BOTTLE.get(), "瓶装发光浆果汁");
        add(ModItems.SWEET_BERRY_JUICE_GLASS.get(), "杯装甜浆果汁");
        add(ModItems.GLOW_BERRY_JUICE_GLASS.get(), "杯装发光浆果汁");
        add(ModItems.WATERMELON_JUICE_BUCKET.get(), "桶装西瓜汁");
        add(ModItems.SWEET_BERRY_JUICE_BUCKET.get(), "桶装甜浆果汁");
        add(ModItems.GLOW_BERRY_JUICE_BUCKET.get(), "桶装发光浆果汁");
        add(ModItems.APPLE_JUICE_BUCKET.get(), "桶装苹果汁");
        add(ModItems.CARROT_JUICE_BUCKET.get(), "桶装胡萝卜汁");
        add(ModItems.BEETROOT_JUICE_BUCKET.get(), "桶装甜菜根汁");
        add(ModItems.SUGARCANE_JUICE_BUCKET.get(), "桶装甘蔗汁");
        add(ModItems.COCOA_BUCKET.get(), "桶装脱脂可可饮");

        /* 方块 */
        add(ModBlocks.SUGAR_BLOCK.get(), "糖块");
        add(ModBlocks.WARPED_VINE_PLATFORM.get(), "诡异藤平台");
        add(ModBlocks.MILL_BLOCK.get(), "磨台");
        add(ModBlocks.WITHERED_SOULWEED.get(), "幽魂枯草");
        add(ModBlocks.SALT_ROCK.get(), "盐岩");
        add(ModBlocks.DEEPSLATE_SALT_ROCK.get(), "深层盐岩");
        add(ModBlocks.FLOATING_MELON_BLOCK.get(), "浮瓜块");
        add(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), "跃进果植株-蔓延根系");
        add(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get(), "跃进果植株-根系");
        add(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get(), "跃进果植株-顶端");

        /* 药水效果 */
        add(ModMobEffects.STUFFED.get(), "吃撑了");
        add(ModMobEffects.OVERFULL.get(), "过饱");
        add(ModMobEffects.HUNGER_AFFLICTION.get(), "饥饿附伤");
        add(ModMobEffects.SATURATION_DECAY.get(), "饱食溶解");
        add(ModMobEffects.PLEASURE.get(), "愉悦");
        add(ModMobEffects.TELEPORT_ON_DAMAGE.get(), "受伤传送");
        add(ModMobEffects.INVIGORATE.get(), "来劲了");
        add(ModMobEffects.DASH.get(), "跃进");
        add(ModMobEffects.WORKING.get(), "劳作");
        add(ModMobEffects.REVERSED_GRAVITY.get(), "重力反转");

        /* 实体 */
        add(ModEntities.FLOATING_MELON.get(), "浮瓜");

        /* 创造标签页 */
        add("itemGroup.miraculous_origin_food_tab", "芥源奇食");
        add("itemGroup.miraculous_origin_food_the_nether_tab", "芥源奇食 | 下界");
        add("itemGroup.miraculous_origin_food_the_end_tab", "芥源奇食 | 末地");

        /*容器标题文本*/
        add("container.miraculousori.mill", "磨台");

        /* 村民职业 */
        add("entity.minecraft.villager.miraculousori.miller", "磨坊主");

        /* 药水瓶*/
        add("item.minecraft.potion.effect.invigorate", "来劲药水");
        add("item.minecraft.potion.effect.invigorate_long", "来劲药水（延时）");
        add("item.minecraft.potion.effect.invigorate_longer", "来劲药水（延时 II）");
        add("item.minecraft.potion.effect.invigorate_longest", "来劲药水（延时 III）");
        add("item.minecraft.potion.effect.invigorate_strong", "来劲药水 II");
        add("item.minecraft.potion.effect.invigorate_stronger", "来劲药水 III");
        add("item.minecraft.potion.effect.invigorate_strongest", "来劲药水 IV");
        add("item.minecraft.potion.effect.invigorate_long_strong", "来劲药水（延时 + II）");
        add("item.minecraft.potion.effect.invigorate_longer_stronger", "来劲药水（延时 II + III）");
        add("item.minecraft.potion.effect.invigorate_longest_strongest", "来劲药水（延时 III + IV）");

        add("item.minecraft.splash_potion.effect.invigorate", "喷溅型来劲药水");
        add("item.minecraft.splash_potion.effect.invigorate_long", "喷溅型来劲药水（延时）");
        add("item.minecraft.splash_potion.effect.invigorate_longer", "喷溅型来劲药水（延时 II）");
        add("item.minecraft.splash_potion.effect.invigorate_longest", "喷溅型来劲药水（延时 III）");
        add("item.minecraft.splash_potion.effect.invigorate_strong", "喷溅型来劲药水 II");
        add("item.minecraft.splash_potion.effect.invigorate_stronger", "喷溅型来劲药水 III");
        add("item.minecraft.splash_potion.effect.invigorate_strongest", "喷溅型来劲药水 IV");
        add("item.minecraft.splash_potion.effect.invigorate_long_strong", "喷溅型来劲药水（延时 + II）");
        add("item.minecraft.splash_potion.effect.invigorate_longer_stronger", "喷溅型来劲药水（延时 II + III）");
        add("item.minecraft.splash_potion.effect.invigorate_longest_strongest", "喷溅型来劲药水（延时 III + IV）");

        add("item.minecraft.lingering_potion.effect.invigorate", "滞留型来劲药水");
        add("item.minecraft.lingering_potion.effect.invigorate_long", "滞留型来劲药水（延时）");
        add("item.minecraft.lingering_potion.effect.invigorate_longer", "滞留型来劲药水（延时 II）");
        add("item.minecraft.lingering_potion.effect.invigorate_longest", "滞留型来劲药水（延时 III）");
        add("item.minecraft.lingering_potion.effect.invigorate_strong", "滞留型来劲药水 II");
        add("item.minecraft.lingering_potion.effect.invigorate_stronger", "滞留型来劲药水 III");
        add("item.minecraft.lingering_potion.effect.invigorate_strongest", "滞留型来劲药水 IV");
        add("item.minecraft.lingering_potion.effect.invigorate_long_strong", "滞留型来劲药水（延时 + II）");
        add("item.minecraft.lingering_potion.effect.invigorate_longer_stronger", "滞留型来劲药水（延时 II + III）");
        add("item.minecraft.lingering_potion.effect.invigorate_longest_strongest", "滞留型来劲药水（延时 III + IV）");

        add("item.minecraft.tipped_arrow.effect.invigorate", "来劲之箭");
        add("item.minecraft.tipped_arrow.effect.invigorate_long", "来劲之箭（延时）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longer", "来劲之箭（延时 II）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longest", "来劲之箭（延时 III）");
        add("item.minecraft.tipped_arrow.effect.invigorate_strong", "来劲之箭 II");
        add("item.minecraft.tipped_arrow.effect.invigorate_stronger", "来劲之箭 III");
        add("item.minecraft.tipped_arrow.effect.invigorate_strongest", "来劲之箭 IV");
        add("item.minecraft.tipped_arrow.effect.invigorate_long_strong", "来劲之箭（延时 + II）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longer_stronger", "来劲之箭（延时 II + III）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longest_strongest", "来劲之箭（延时 III + IV）");

        /* 进度与成就 */
        add("advancements.miraculousori.money_makes_the_miller_grind.title", "有钱能使鬼推磨");
        add("advancements.miraculousori.money_makes_the_miller_grind.description", "让磨坊主给你拉磨");
        add("advancements.miraculousori.food_question.title", "“食物？”");
        add("advancements.miraculousori.food_question.description", "获得一个土饼");
        add("advancements.miraculousori.wheres_the_eggshell.title", "鸡蛋壳呢？");
        add("advancements.miraculousori.wheres_the_eggshell.description", "制作饼干面团时，把鸡蛋壳也加里面");
        add("advancements.miraculousori.super_compression.title", "超级压缩");
        add("advancements.miraculousori.super_compression.description", "用巨大的力量徒手将9个小麦饼干压缩");
        add("advancements.miraculousori.did_i_really_eat.title", "我真的吃东西了吗？");
        add("advancements.miraculousori.did_i_really_eat.description", "吃下还不如不吃的饼干");
        add("advancements.miraculousori.more_honey.title", "再来点蜂蜜");
        add("advancements.miraculousori.more_honey.description", "给小麦面包加一点蜂蜜");
        add("advancements.miraculousori.it_flew_away.title", "它飞走了");
        add("advancements.miraculousori.it_flew_away.description", "放飞“气球”");
        add("advancements.miraculousori.what_a_fine_loaf.title", "真是把好......包");
        add("advancements.miraculousori.what_a_fine_loaf.description", "制作一件能吃的“武器”");
        add("advancements.miraculousori.heavenly_injustice.title", "天理难容");
        add("advancements.miraculousori.heavenly_injustice.description", "遭天谴");
        add("advancements.miraculousori.earth_cannon.title", "地球大炮");
        add("advancements.miraculousori.earth_cannon.description", "利用重力，把自己发射出去");
        add("advancements.miraculousori.press_residue.title", "压榨剩余渣汁");
        add("advancements.miraculousori.press_residue.description", "狠狠榨汁");
        add("advancements.miraculousori.god_gaze.title", "祂投下注视");
        add("advancements.miraculousori.god_gaze.description", "吸引祂的视线，追寻饱和");
        add("advancements.miraculousori.blessing_amulet.title", "佑护加身");
        add("advancements.miraculousori.blessing_amulet.description", "制作出食神之佑");
        add("advancements.miraculousori.nether_root.title", "俺寻思这玩意能吃");
        add("advancements.miraculousori.nether_root.description", "在下界发现新的食物");
        add("advancements.miraculousori.nether_energy.title", "下界小伙能量大");
        add("advancements.miraculousori.nether_energy.description", "烫烫的烈焰粉");
        add("advancements.miraculousori.nether_seed.title", "枯死之魂的生机");
        add("advancements.miraculousori.nether_seed.description", "灵魂沙上的作物");
        add("advancements.miraculousori.nether_wheat.title", "下界特殊物种");
        add("advancements.miraculousori.nether_wheat.description", "让缠魂麦结实");
        add("advancements.miraculousori.nether_curse.title", "祂降下咒罚");
        add("advancements.miraculousori.nether_curse.description", "感受饥饿");
        add("advancements.miraculousori.nether_ode.title", "诅咒亦有其形");
        add("advancements.miraculousori.nether_ode.description", "是诅咒亦是力量");
        add("advancements.miraculousori.root.title", "芥源奇食");
        add("advancements.miraculousori.root.description", "美食之旅已启程");
        add("advancements.miraculousori.end_root.title", "末地美食家");
        add("advancements.miraculousori.end_root.description", "在末地发现新的瓜种");
        add("advancements.miraculousori.end_melon.title", "吃完这个会不会飞起来？");
        add("advancements.miraculousori.end_melon.description", "看着飞浮瓜掉落的物品陷入了沉思");
        add("advancements.miraculousori.end_jump.title", "近程折跃");
        add("advancements.miraculousori.end_jump.description", "吃下跃进果，然后短距离跃迁");
        add("advancements.miraculousori.end_plant.title", "末地石里的植物");
        add("advancements.miraculousori.end_plant.description", "让跃进果植株重新富有生机");
        add("advancements.miraculousori.end_speech.title", "聆音终末");
        add("advancements.miraculousori.end_speech.description", "见证终末之诗后再次从末地回到主世界");
        add("advancements.miraculousori.end_journey.title", "末地之旅");
        add("advancements.miraculousori.end_journey.description", "踏上末地的旅途，是结束也是新的开始");

        /* JEI 适配 */
        add("jei.category.miraculousori.mill", "磨台研磨");
        add("jei.category.miraculousori.liquid_filling", "液体盛取");

        /* 液体名称 */
        add("fluid.minecraft.water", "水");
        add("fluid.miraculousori.apple_juice", "苹果汁");
        add("fluid.miraculousori.watermelon_juice", "西瓜汁");
        add("fluid.miraculousori.carrot_juice", "胡萝卜汁");
        add("fluid.miraculousori.sugarcane_juice", "甘蔗汁");
        add("fluid.miraculousori.beetroot_juice", "甜菜根汁");
        add("fluid.miraculousori.sweet_berry_juice", "甜浆果汁");
        add("fluid.miraculousori.glow_berry_juice", "发光浆果汁");
        add("fluid.miraculousori.cocoa_butter", "可可脂");

        /* 物品属性 */
        add("tooltip.miraculousori.owner", "所有者: %s");
        add("tooltip.miraculousori.no_owner", "无主之物");
        add("message.miraculousori.stolen_divine", "-<神罚将至>-");

        /* 死亡消息 */
        add("death.attack.bloat", "%1$s撑死了");
        add("death.attack.bloat.player", "%1$s在%2$s的投喂下撑死了");
    }
}