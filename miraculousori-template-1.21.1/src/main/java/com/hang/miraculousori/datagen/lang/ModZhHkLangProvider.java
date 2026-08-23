package com.hang.miraculousori.datagen.lang;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.entity.ModEntities;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModZhHkLangProvider extends LanguageProvider {
    public ModZhHkLangProvider(PackOutput output) {
        super(output, MiraculousOriginFoodMod.MODID, "zh_hk");
    }

    @Override
    protected void addTranslations() {
        /* 普通物品 */
        add(ModItems.SATURATION_TOTEM.get(), "§p飽和圖騰");
        add(ModItems.FOOD_GOD_BLESSING_AMULET.get(), "§6食神之佑");
        add(ModItems.GOD_GAZE.get(), "§4神之瞥視");
        add(ModItems.HUNGER_CURSE_PUNISHMENT.get(), "§4飢災咒刑");
        add(ModItems.HUNGER_CURSE_ODE.get(), "§0飢饉詛頌");
        add(ModItems.HUNGER_TOTEM.get(), "§7飢饉圖騰");
        add(ModItems.ENDING_SPEECH.get(), "§d終末之言");
        add(ModItems.END_ROAD_RUNE.get(), "§b末途符文");
        add(ModItems.END_NEW_PATH.get(), "§5終幕新途");

        add(ModItems.RAW_BAGUETTE.get(), "生法棍");
        add(ModItems.RAW_POTATO_PATTY.get(), "生土豆餅");

        add(ModItems.WHEAT_FLOUR.get(), "小麥麵粉");
        add(ModItems.WHEAT_DOUGH.get(), "白麵糰");
        add(ModItems.SOUL_WHEAT.get(), "纏魂麥穗");
        add(ModItems.NETHER_SOUL_WHEAT_FLOUR.get(), "纏魂麥麵粉");
        add(ModItems.NETHER_SOUL_WHEAT_DOUGH.get(), "纏魂麥麵糰");
        add(ModItems.SUGAR_CUBE.get(), "方糖");
        add(ModItems.CARAMEL.get(), "焦糖");
        add(ModItems.WET_CLAY_BALL.get(), "濕潤的粘土球");
        add(ModItems.COOKIE_DOUGH.get(), "雞蛋麵糰");
        add(ModItems.RAW_COOKIE.get(), "生餅乾");
        add(ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get(), "纏魂麥生餅乾");
        add(ModItems.COOKIE_MOLD.get(), "模具");
        add(ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get(), "有小麥餅乾胚的模具");
        add(ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get(), "有纏魂麥餅乾胚的模具");
        add(ModItems.BLAZE_INCENSE_POWDER.get(), "烈焰香粉");
        add(ModItems.NETHER_INCENSE_POWDER.get(), "下界香粉");
        add(ModItems.CRIMSON_FRUIT_POWDER.get(), "猩紅果實粉");
        add(ModItems.WARPED_FRUIT_POWDER.get(), "詭異果實粉");
        add(ModItems.NETHER_SOUL_WHEAT_SEEDS.get(), "纏魂麥種");
        add(ModItems.DIVINE_UPGRADE_TEMPLATE.get(), "§b神性模板");
        add(ModItems.BEETROOT_PULP.get(), "甜菜根渣");
        add(ModItems.SWEET_BERRY_PULP.get(), "漿果渣");
        add(ModItems.SUGARCANE_PULP.get(), "甘蔗渣");
        add(ModItems.GLOW_BERRY_PULP.get(), "發光漿果渣");
        add(ModItems.COCOA_BUTTER_MOLD.get(), "含可可脂模具");
        add(ModItems.WATER_MOLD.get(), "含水模具");
        add(ModItems.DARK_CHOCOLATE_PASTE_MOLD.get(), "含黑巧克力模具");
        add(ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get(), "含白巧克力模具");
        add(ModItems.WITHERED_SOULWEED.get(), "幽魂枯草");
        add(ModItems.CRUSHED_SALT.get(), "粗鹽");
        add(ModItems.SALT.get(), "鹽");
        add(ModItems.FLOATING_MELON_SEEDS.get(), "浮瓜種子");

        add(ModItems.VEGETABLE_CLAY_CAKE.get(), "摻菜粘土餅");
        add(ModItems.CLAY_CAKE.get(), "粘土餅");
        add(ModItems.SWEET_CLAY_CAKE.get(), "摻糖粘土餅");
        add(ModItems.ENDER_CLAY_CAKE.get(), "末影粘土餅");

        /* 食品 */
        add(ModItems.VEGETABLE_COOKED_CLAY_CAKE.get(), "摻菜土餅");
        add(ModItems.SWEET_COOKED_CLAY_CAKE.get(), "摻糖土餅");
        add(ModItems.COOKED_CLAY_CAKE.get(), "熟土餅");
        add(ModItems.ENDER_COOKED_CLAY_CAKE.get(), "末影土餅");
        add(ModItems.WHEAT_COOKIE.get(), "小麥餅乾");
        add(ModItems.COMPRESSED_WHEAT_BISCUIT.get(), "壓縮小麥餅乾");
        add(ModItems.NETHER_CLAY_CAKE.get(), "下界土餅");
        add(ModItems.NETHER_SOUL_WHEAT_COOKIE.get(), "纏魂麥餅乾");
        add(ModItems.WARPED_FRUIT.get(), "詭異果實");
        add(ModItems.CRIMSON_FRUIT.get(), "猩紅果實");
        add(ModItems.VERY_SWEET_BREAD.get(), "齁甜麵包");
        add(ModItems.WHEAT_BREAD.get(), "小麥麵包");
        add(ModItems.HONEY_BREAD.get(), "蜂蜜麵包");
        add(ModItems.BAGUETTE.get(), "法棍");
        add(ModItems.LARGE_BAGUETTE_HALF.get(), "大半法棍");
        add(ModItems.SMALL_BAGUETTE_HALF.get(), "小半法棍");
        add(ModItems.CHINESE_SUGAR_FREE_BREAD.get(), "饅頭");
        add(ModItems.MASHED_POTATO.get(), "土豆泥");
        add(ModItems.COOKED_POTATO_PATTY.get(), "土豆餅");
        add(ModItems.DARK_CHOCOLATE.get(), "黑巧克力");
        add(ModItems.WHITE_CHOCOLATE.get(), "白巧克力");
        add(ModItems.LEAPING_FRUIT.get(), "躍進果");
        add(ModItems.FLOATING_MELON.get(), "浮瓜");
        add(ModItems.BROKEN_FLOATING_GOURD.get(), "破浮瓜");
        add(ModItems.FLOATING_MELON_JAM.get(), "浮瓜果醬");
        add(ModItems.FLOATING_MELON_PIE.get(), "浮瓜派");

        /* 飲品 */
        add(ModItems.WATER_GLASS.get(), "一杯水");
        add(ModItems.APPLE_JUICE_BOTTLE.get(), "瓶裝蘋果汁");
        add(ModItems.APPLE_JUICE_GLASS.get(), "杯裝蘋果汁");
        add(ModItems.APPLE_PULP.get(), "蘋果渣");
        add(ModItems.WATERMELON_JUICE_BOTTLE.get(), "瓶裝西瓜汁");
        add(ModItems.WATERMELON_JUICE_GLASS.get(), "杯裝西瓜汁");
        add(ModItems.WATERMELON_PULP.get(), "西瓜渣");
        add(ModItems.CARROT_JUICE_BOTTLE.get(), "瓶裝胡蘿蔔汁");
        add(ModItems.CARROT_JUICE_GLASS.get(), "杯裝胡蘿蔔汁");
        add(ModItems.CARROT_PULP.get(), "胡蘿蔔渣");
        add(ModItems.COCOA_BOTTLE.get(), "瓶裝脫脂可可飲");
        add(ModItems.COCOA_GLASS.get(), "杯裝脫脂可可飲");
        add(ModItems.COCOA_POWDER.get(), "脫脂可可粉");
        add(ModItems.GLASS_CUP.get(), "玻璃杯");
        add(ModItems.SUGARCANE_JUICE_BOTTLE.get(), "瓶裝甘蔗汁");
        add(ModItems.BEETROOT_JUICE_BOTTLE.get(), "瓶裝甜菜根汁");
        add(ModItems.SUGARCANE_JUICE_GLASS.get(), "杯裝甘蔗汁");
        add(ModItems.BEETROOT_JUICE_GLASS.get(), "杯裝甜菜根汁");
        add(ModItems.SWEET_BERRY_JUICE_BOTTLE.get(), "瓶裝甜漿果汁");
        add(ModItems.GLOW_BERRY_JUICE_BOTTLE.get(), "瓶裝發光漿果汁");
        add(ModItems.SWEET_BERRY_JUICE_GLASS.get(), "杯裝甜漿果汁");
        add(ModItems.GLOW_BERRY_JUICE_GLASS.get(), "杯裝發光漿果汁");
        add(ModItems.WATERMELON_JUICE_BUCKET.get(), "桶裝西瓜汁");
        add(ModItems.SWEET_BERRY_JUICE_BUCKET.get(), "桶裝甜漿果汁");
        add(ModItems.GLOW_BERRY_JUICE_BUCKET.get(), "桶裝發光漿果汁");
        add(ModItems.APPLE_JUICE_BUCKET.get(), "桶裝蘋果汁");
        add(ModItems.CARROT_JUICE_BUCKET.get(), "桶裝胡蘿蔔汁");
        add(ModItems.BEETROOT_JUICE_BUCKET.get(), "桶裝甜菜根汁");
        add(ModItems.SUGARCANE_JUICE_BUCKET.get(), "桶裝甘蔗汁");
        add(ModItems.COCOA_BUCKET.get(), "桶裝脫脂可可飲");

        /* 方塊 */
        add(ModBlocks.SUGAR_BLOCK.get(), "糖塊");
        add(ModBlocks.WARPED_VINE_PLATFORM.get(), "詭異藤平台");
        add(ModBlocks.MILL_BLOCK.get(), "磨臺");
        add(ModBlocks.WITHERED_SOULWEED.get(), "幽魂枯草");
        add(ModBlocks.SALT_ROCK.get(), "鹽岩");
        add(ModBlocks.DEEPSLATE_SALT_ROCK.get(), "深層鹽岩");
        add(ModBlocks.FLOATING_MELON_BLOCK.get(), "浮瓜塊");
        add(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), "躍進果植株-蔓延根系");
        add(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get(), "躍進果植株-根系");
        add(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get(), "躍進果植株-頂端");

        /* 藥水效果 */
        add(ModMobEffects.STUFFED.get(), "吃撐了");
        add(ModMobEffects.OVERFULL.get(), "過飽");
        add(ModMobEffects.HUNGER_AFFLICTION.get(), "飢餓附傷");
        add(ModMobEffects.SATURATION_DECAY.get(), "飽食溶解");
        add(ModMobEffects.PLEASURE.get(), "愉悅");
        add(ModMobEffects.TELEPORT_ON_DAMAGE.get(), "受傷傳送");
        add(ModMobEffects.INVIGORATE.get(), "來勁了");
        add(ModMobEffects.DASH.get(), "躍進");
        add(ModMobEffects.WORKING.get(), "勞作");
        add(ModMobEffects.REVERSED_GRAVITY.get(), "重力反轉");

        /* 實體 */
        add(ModEntities.FLOATING_MELON.get(), "浮瓜");

        /* 創造標籤頁 */
        add("itemGroup.miraculous_origin_food_tab", "芥源奇食");
        add("itemGroup.miraculous_origin_food_the_nether_tab", "芥源奇食 | 下界");
        add("itemGroup.miraculous_origin_food_the_end_tab", "芥源奇食 | 末地");

        /* 容器標題文本 */
        add("container.miraculousori.mill", "磨臺");

        /* 村民職業 */
        add("entity.minecraft.villager.miraculousori.miller", "磨坊主");

        /* 藥水瓶 */
        add("item.minecraft.potion.effect.invigorate", "來勁藥水");
        add("item.minecraft.potion.effect.invigorate_long", "來勁藥水（延時）");
        add("item.minecraft.potion.effect.invigorate_longer", "來勁藥水（延時 II）");
        add("item.minecraft.potion.effect.invigorate_longest", "來勁藥水（延時 III）");
        add("item.minecraft.potion.effect.invigorate_strong", "來勁藥水 II");
        add("item.minecraft.potion.effect.invigorate_stronger", "來勁藥水 III");
        add("item.minecraft.potion.effect.invigorate_strongest", "來勁藥水 IV");
        add("item.minecraft.potion.effect.invigorate_long_strong", "來勁藥水（延時 + II）");
        add("item.minecraft.potion.effect.invigorate_longer_stronger", "來勁藥水（延時 II + III）");
        add("item.minecraft.potion.effect.invigorate_longest_strongest", "來勁藥水（延時 III + IV）");

        add("item.minecraft.splash_potion.effect.invigorate", "噴濺型來勁藥水");
        add("item.minecraft.splash_potion.effect.invigorate_long", "噴濺型來勁藥水（延時）");
        add("item.minecraft.splash_potion.effect.invigorate_longer", "噴濺型來勁藥水（延時 II）");
        add("item.minecraft.splash_potion.effect.invigorate_longest", "噴濺型來勁藥水（延時 III）");
        add("item.minecraft.splash_potion.effect.invigorate_strong", "噴濺型來勁藥水 II");
        add("item.minecraft.splash_potion.effect.invigorate_stronger", "噴濺型來勁藥水 III");
        add("item.minecraft.splash_potion.effect.invigorate_strongest", "噴濺型來勁藥水 IV");
        add("item.minecraft.splash_potion.effect.invigorate_long_strong", "噴濺型來勁藥水（延時 + II）");
        add("item.minecraft.splash_potion.effect.invigorate_longer_stronger", "噴濺型來勁藥水（延時 II + III）");
        add("item.minecraft.splash_potion.effect.invigorate_longest_strongest", "噴濺型來勁藥水（延時 III + IV）");

        add("item.minecraft.lingering_potion.effect.invigorate", "滯留型來勁藥水");
        add("item.minecraft.lingering_potion.effect.invigorate_long", "滯留型來勁藥水（延時）");
        add("item.minecraft.lingering_potion.effect.invigorate_longer", "滯留型來勁藥水（延時 II）");
        add("item.minecraft.lingering_potion.effect.invigorate_longest", "滯留型來勁藥水（延時 III）");
        add("item.minecraft.lingering_potion.effect.invigorate_strong", "滯留型來勁藥水 II");
        add("item.minecraft.lingering_potion.effect.invigorate_stronger", "滯留型來勁藥水 III");
        add("item.minecraft.lingering_potion.effect.invigorate_strongest", "滯留型來勁藥水 IV");
        add("item.minecraft.lingering_potion.effect.invigorate_long_strong", "滯留型來勁藥水（延時 + II）");
        add("item.minecraft.lingering_potion.effect.invigorate_longer_stronger", "滯留型來勁藥水（延時 II + III）");
        add("item.minecraft.lingering_potion.effect.invigorate_longest_strongest", "滯留型來勁藥水（延時 III + IV）");

        add("item.minecraft.tipped_arrow.effect.invigorate", "來勁之箭");
        add("item.minecraft.tipped_arrow.effect.invigorate_long", "來勁之箭（延時）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longer", "來勁之箭（延時 II）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longest", "來勁之箭（延時 III）");
        add("item.minecraft.tipped_arrow.effect.invigorate_strong", "來勁之箭 II");
        add("item.minecraft.tipped_arrow.effect.invigorate_stronger", "來勁之箭 III");
        add("item.minecraft.tipped_arrow.effect.invigorate_strongest", "來勁之箭 IV");
        add("item.minecraft.tipped_arrow.effect.invigorate_long_strong", "來勁之箭（延時 + II）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longer_stronger", "來勁之箭（延時 II + III）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longest_strongest", "來勁之箭（延時 III + IV）");

        /* 進度與成就 */
        add("advancements.miraculousori.money_makes_the_miller_grind.title", "有錢能使鬼推磨");
        add("advancements.miraculousori.money_makes_the_miller_grind.description", "讓磨坊主給你拉磨");
        add("advancements.miraculousori.food_question.title", "「食物？」");
        add("advancements.miraculousori.food_question.description", "獲得一個土餅");
        add("advancements.miraculousori.wheres_the_eggshell.title", "雞蛋殼呢？");
        add("advancements.miraculousori.wheres_the_eggshell.description", "製作餅乾麵糰時，把雞蛋殼也加裡面");
        add("advancements.miraculousori.super_compression.title", "超級壓縮");
        add("advancements.miraculousori.super_compression.description", "用巨大的力量徒手將9個小麥餅乾壓縮");
        add("advancements.miraculousori.did_i_really_eat.title", "我真的吃東西了嗎？");
        add("advancements.miraculousori.did_i_really_eat.description", "吃下還不如不吃的餅乾");
        add("advancements.miraculousori.more_honey.title", "再來點蜂蜜");
        add("advancements.miraculousori.more_honey.description", "給小麥麵包加一點蜂蜜");
        add("advancements.miraculousori.it_flew_away.title", "它飛走了");
        add("advancements.miraculousori.it_flew_away.description", "放飛「氣球」");
        add("advancements.miraculousori.what_a_fine_loaf.title", "真是把好......包");
        add("advancements.miraculousori.what_a_fine_loaf.description", "製作一件能吃的「武器」");
        add("advancements.miraculousori.heavenly_injustice.title", "天理難容");
        add("advancements.miraculousori.heavenly_injustice.description", "遭天譴");
        add("advancements.miraculousori.earth_cannon.title", "地球大炮");
        add("advancements.miraculousori.earth_cannon.description", "利用重力，把自己發射出去");
        add("advancements.miraculousori.press_residue.title", "壓榨剩餘渣汁");
        add("advancements.miraculousori.press_residue.description", "狠狠榨汁");
        add("advancements.miraculousori.god_gaze.title", "祂投下注視");
        add("advancements.miraculousori.god_gaze.description", "吸引祂的視線，追尋飽和");
        add("advancements.miraculousori.blessing_amulet.title", "佑護加身");
        add("advancements.miraculousori.blessing_amulet.description", "製作出食神之佑");
        add("advancements.miraculousori.nether_root.title", "俺尋思這玩意能吃");
        add("advancements.miraculousori.nether_root.description", "在下界發現新的食物");
        add("advancements.miraculousori.nether_energy.title", "下界小伙能量大");
        add("advancements.miraculousori.nether_energy.description", "燙燙的烈焰粉");
        add("advancements.miraculousori.nether_seed.title", "枯死之魂的生機");
        add("advancements.miraculousori.nether_seed.description", "靈魂沙上的作物");
        add("advancements.miraculousori.nether_wheat.title", "下界特殊物種");
        add("advancements.miraculousori.nether_wheat.description", "讓纏魂麥結實");
        add("advancements.miraculousori.nether_curse.title", "祂降下咒罰");
        add("advancements.miraculousori.nether_curse.description", "感受飢餓");
        add("advancements.miraculousori.nether_ode.title", "詛咒亦有其形");
        add("advancements.miraculousori.nether_ode.description", "是詛咒亦是力量");
        add("advancements.miraculousori.root.title", "芥源奇食");
        add("advancements.miraculousori.root.description", "美食之旅已啟程");
        add("advancements.miraculousori.end_root.title", "末地美食家");
        add("advancements.miraculousori.end_root.description", "在末地發現新的瓜種");
        add("advancements.miraculousori.end_melon.title", "吃完這個會不會飛起來？");
        add("advancements.miraculousori.end_melon.description", "看著飛浮瓜掉落的物品陷入了沉思");
        add("advancements.miraculousori.end_jump.title", "近程折躍");
        add("advancements.miraculousori.end_jump.description", "吃下躍進果，然後短距離躍遷");
        add("advancements.miraculousori.end_plant.title", "末地石裡的植物");
        add("advancements.miraculousori.end_plant.description", "讓躍進果植株重新富有生機");
        add("advancements.miraculousori.end_speech.title", "聆音終末");
        add("advancements.miraculousori.end_speech.description", "見證終末之詩後再次從末地回到主世界");
        add("advancements.miraculousori.end_journey.title", "末地之旅");
        add("advancements.miraculousori.end_journey.description", "踏上末地的旅途，是結束也是新的開始");

        /* JEI 適配 */
        add("jei.category.miraculousori.mill", "磨臺研磨");
        add("jei.category.miraculousori.liquid_filling", "液體盛取");

        /* 液體名稱 */
        add("fluid.minecraft.water", "水");
        add("fluid.miraculousori.apple_juice", "蘋果汁");
        add("fluid.miraculousori.watermelon_juice", "西瓜汁");
        add("fluid.miraculousori.carrot_juice", "胡蘿蔔汁");
        add("fluid.miraculousori.sugarcane_juice", "甘蔗汁");
        add("fluid.miraculousori.beetroot_juice", "甜菜根汁");
        add("fluid.miraculousori.sweet_berry_juice", "甜漿果汁");
        add("fluid.miraculousori.glow_berry_juice", "發光漿果汁");
        add("fluid.miraculousori.cocoa_butter", "可可脂");

        /* 物品屬性 */
        add("tooltip.miraculousori.owner", "所有者: %s");
        add("tooltip.miraculousori.no_owner", "無主之物");
        add("message.miraculousori.stolen_divine", "-<神罰將至>-");

        /* 死亡消息 */
        add("death.attack.bloat", "%1$s撐死了");
        add("death.attack.bloat.player", "%1$s在%2$s的投餵下撐死了");
    }
}
