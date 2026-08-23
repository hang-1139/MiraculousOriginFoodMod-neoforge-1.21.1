package com.hang.miraculousori.datagen.lang;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.entity.ModEntities;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLzhLangProvider extends LanguageProvider {
    public ModLzhLangProvider(PackOutput output) {
        super(output, MiraculousOriginFoodMod.MODID, "lzh");
    }

    @Override
    protected void addTranslations() {

        /* 普通物品 */
        add(ModItems.SATURATION_TOTEM.get(), "§p飽飫圖騰");
        add(ModItems.FOOD_GOD_BLESSING_AMULET.get(), "§6食神之佑");
        add(ModItems.GOD_GAZE.get(), "§4神之瞥視");
        add(ModItems.HUNGER_CURSE_PUNISHMENT.get(), "§4饑饉咒刑");
        add(ModItems.HUNGER_CURSE_ODE.get(), "§0饑饉詛頌");
        add(ModItems.HUNGER_TOTEM.get(), "§7饑饉圖騰");
        add(ModItems.ENDING_SPEECH.get(), "§d終末之言");
        add(ModItems.END_ROAD_RUNE.get(), "§b末途符文");
        add(ModItems.END_NEW_PATH.get(), "§5終幕新途");

        add(ModItems.RAW_BAGUETTE.get(), "生法棍");
        add(ModItems.RAW_POTATO_PATTY.get(), "生薯餅");

        add(ModItems.WHEAT_FLOUR.get(), "小麥麵粉");
        add(ModItems.WHEAT_DOUGH.get(), "白麵團");
        add(ModItems.SOUL_WHEAT.get(), "纏魂麥穗");
        add(ModItems.NETHER_SOUL_WHEAT_FLOUR.get(), "纏魂麥麵粉");
        add(ModItems.NETHER_SOUL_WHEAT_DOUGH.get(), "纏魂麥麵團");
        add(ModItems.SUGAR_CUBE.get(), "方糖");
        add(ModItems.CARAMEL.get(), "焦糖");
        add(ModItems.WET_CLAY_BALL.get(), "濕潤粘土球");
        add(ModItems.COOKIE_DOUGH.get(), "雞子麵團");
        add(ModItems.RAW_COOKIE.get(), "生餅乾");
        add(ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get(), "纏魂麥生餅乾");
        add(ModItems.COOKIE_MOLD.get(), "模具");
        add(ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get(), "含小麥餅胚之模");
        add(ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get(), "含纏魂麥餅胚之模");
        add(ModItems.BLAZE_INCENSE_POWDER.get(), "烈焰香粉");
        add(ModItems.NETHER_INCENSE_POWDER.get(), "下界香粉");
        add(ModItems.CRIMSON_FRUIT_POWDER.get(), "猩紅果實粉");
        add(ModItems.WARPED_FRUIT_POWDER.get(), "詭異果實粉");
        add(ModItems.NETHER_SOUL_WHEAT_SEEDS.get(), "纏魂麥種");
        add(ModItems.DIVINE_UPGRADE_TEMPLATE.get(), "§b神性模板");
        add(ModItems.BEETROOT_PULP.get(), "甜菜根渣");
        add(ModItems.SWEET_BERRY_PULP.get(), "漿果渣");
        add(ModItems.SUGARCANE_PULP.get(), "甘蔗渣");
        add(ModItems.GLOW_BERRY_PULP.get(), "螢光漿果渣");
        add(ModItems.COCOA_BUTTER_MOLD.get(), "含可可脂之模");
        add(ModItems.WATER_MOLD.get(), "含水之模");
        add(ModItems.DARK_CHOCOLATE_PASTE_MOLD.get(), "含黑巧克力之模");
        add(ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get(), "含白巧克力之模");
        add(ModItems.WITHERED_SOULWEED.get(), "幽魂枯草");
        add(ModItems.CRUSHED_SALT.get(), "粗鹽");
        add(ModItems.SALT.get(), "鹽");
        add(ModItems.FLOATING_MELON_SEEDS.get(), "浮瓜種");

        add(ModItems.VEGETABLE_CLAY_CAKE.get(), "雜菜粘土餅");
        add(ModItems.CLAY_CAKE.get(), "粘土餅");
        add(ModItems.SWEET_CLAY_CAKE.get(), "摻糖粘土餅");
        add(ModItems.ENDER_CLAY_CAKE.get(), "終界粘土餅");

        /* 食品 */
        add(ModItems.VEGETABLE_COOKED_CLAY_CAKE.get(), "雜菜土餅");
        add(ModItems.SWEET_COOKED_CLAY_CAKE.get(), "摻糖土餅");
        add(ModItems.COOKED_CLAY_CAKE.get(), "熟土餅");
        add(ModItems.ENDER_COOKED_CLAY_CAKE.get(), "終界土餅");
        add(ModItems.WHEAT_COOKIE.get(), "小麥餅乾");
        add(ModItems.COMPRESSED_WHEAT_BISCUIT.get(), "壓縮小麥餅乾");
        add(ModItems.NETHER_CLAY_CAKE.get(), "下界土餅");
        add(ModItems.NETHER_SOUL_WHEAT_COOKIE.get(), "纏魂麥餅乾");
        add(ModItems.WARPED_FRUIT.get(), "詭異果實");
        add(ModItems.CRIMSON_FRUIT.get(), "猩紅果實");
        add(ModItems.VERY_SWEET_BREAD.get(), "極甜麵包");
        add(ModItems.WHEAT_BREAD.get(), "小麥麵包");
        add(ModItems.HONEY_BREAD.get(), "蜂蜜麵包");
        add(ModItems.BAGUETTE.get(), "法棍");
        add(ModItems.LARGE_BAGUETTE_HALF.get(), "大半法棍");
        add(ModItems.SMALL_BAGUETTE_HALF.get(), "小半法棍");
        add(ModItems.CHINESE_SUGAR_FREE_BREAD.get(), "饅頭");
        add(ModItems.MASHED_POTATO.get(), "薯泥");
        add(ModItems.COOKED_POTATO_PATTY.get(), "薯餅");
        add(ModItems.DARK_CHOCOLATE.get(), "黑巧克力");
        add(ModItems.WHITE_CHOCOLATE.get(), "白巧克力");
        add(ModItems.LEAPING_FRUIT.get(), "躍進果");
        add(ModItems.FLOATING_MELON.get(), "浮瓜");
        add(ModItems.BROKEN_FLOATING_GOURD.get(), "破浮瓜");
        add(ModItems.FLOATING_MELON_JAM.get(), "浮瓜果醬");
        add(ModItems.FLOATING_MELON_PIE.get(), "浮瓜派");

        /* 飲品 */
        add(ModItems.WATER_GLASS.get(), "盞水");
        add(ModItems.APPLE_JUICE_BOTTLE.get(), "瓶裝蘋果汁");
        add(ModItems.APPLE_JUICE_GLASS.get(), "盞蘋果汁");
        add(ModItems.APPLE_PULP.get(), "蘋果渣");
        add(ModItems.WATERMELON_JUICE_BOTTLE.get(), "瓶裝西瓜汁");
        add(ModItems.WATERMELON_JUICE_GLASS.get(), "盞西瓜汁");
        add(ModItems.WATERMELON_PULP.get(), "西瓜渣");
        add(ModItems.CARROT_JUICE_BOTTLE.get(), "瓶裝胡蘿蔔汁");
        add(ModItems.CARROT_JUICE_GLASS.get(), "盞胡蘿蔔汁");
        add(ModItems.CARROT_PULP.get(), "胡蘿蔔渣");
        add(ModItems.COCOA_BOTTLE.get(), "瓶裝脫脂可可飲");
        add(ModItems.COCOA_GLASS.get(), "盞脫脂可可飲");
        add(ModItems.COCOA_POWDER.get(), "脫脂可可粉");
        add(ModItems.GLASS_CUP.get(), "玻璃盞");
        add(ModItems.SUGARCANE_JUICE_BOTTLE.get(), "瓶裝甘蔗汁");
        add(ModItems.BEETROOT_JUICE_BOTTLE.get(), "瓶裝甜菜根汁");
        add(ModItems.SUGARCANE_JUICE_GLASS.get(), "盞甘蔗汁");
        add(ModItems.BEETROOT_JUICE_GLASS.get(), "盞甜菜根汁");
        add(ModItems.SWEET_BERRY_JUICE_BOTTLE.get(), "瓶裝甜漿果汁");
        add(ModItems.GLOW_BERRY_JUICE_BOTTLE.get(), "瓶裝螢光漿果汁");
        add(ModItems.SWEET_BERRY_JUICE_GLASS.get(), "盞甜漿果汁");
        add(ModItems.GLOW_BERRY_JUICE_GLASS.get(), "盞螢光漿果汁");
        add(ModItems.WATERMELON_JUICE_BUCKET.get(), "桶裝西瓜汁");
        add(ModItems.SWEET_BERRY_JUICE_BUCKET.get(), "桶裝甜漿果汁");
        add(ModItems.GLOW_BERRY_JUICE_BUCKET.get(), "桶裝螢光漿果汁");
        add(ModItems.APPLE_JUICE_BUCKET.get(), "桶裝蘋果汁");
        add(ModItems.CARROT_JUICE_BUCKET.get(), "桶裝胡蘿蔔汁");
        add(ModItems.BEETROOT_JUICE_BUCKET.get(), "桶裝甜菜根汁");
        add(ModItems.SUGARCANE_JUICE_BUCKET.get(), "桶裝甘蔗汁");
        add(ModItems.COCOA_BUCKET.get(), "桶裝脫脂可可飲");

        /* 方塊 */
        add(ModBlocks.SUGAR_BLOCK.get(), "糖塊");
        add(ModBlocks.WARPED_VINE_PLATFORM.get(), "詭異藤臺");
        add(ModBlocks.MILL_BLOCK.get(), "磨臺");
        add(ModBlocks.WITHERED_SOULWEED.get(), "幽魂枯草");
        add(ModBlocks.SALT_ROCK.get(), "鹽岩");
        add(ModBlocks.DEEPSLATE_SALT_ROCK.get(), "深層鹽岩");
        add(ModBlocks.FLOATING_MELON_BLOCK.get(), "浮瓜塊");
        add(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), "躍進果株-蔓延根系");
        add(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get(), "躍進果株-根系");
        add(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get(), "躍進果株-頂端");

        /* 藥水效果 */
        add(ModMobEffects.STUFFED.get(), "飽飫");
        add(ModMobEffects.OVERFULL.get(), "過飽");
        add(ModMobEffects.HUNGER_AFFLICTION.get(), "饑餓附傷");
        add(ModMobEffects.SATURATION_DECAY.get(), "飽食消解");
        add(ModMobEffects.PLEASURE.get(), "愉悅");
        add(ModMobEffects.TELEPORT_ON_DAMAGE.get(), "傷而傳送");
        add(ModMobEffects.INVIGORATE.get(), "振奮");
        add(ModMobEffects.DASH.get(), "躍進");
        add(ModMobEffects.WORKING.get(), "勞作");
        add(ModMobEffects.REVERSED_GRAVITY.get(), "重力倒轉");

        /* 實體 */
        add(ModEntities.FLOATING_MELON.get(), "浮瓜");

        /* 創造標籤頁 */
        add("itemGroup.miraculous_origin_food_tab", "芥源奇食");
        add("itemGroup.miraculous_origin_food_the_nether_tab", "芥源奇食 | 下界");
        add("itemGroup.miraculous_origin_food_the_end_tab", "芥源奇食 | 終界");

        /* 容器標題文本 */
        add("container.miraculousori.mill", "磨臺");

        /* 村民職業 */
        add("entity.minecraft.villager.miraculousori.miller", "磨坊主");

        /* 藥水瓶 */
        add("item.minecraft.potion.effect.invigorate", "振奮藥水");
        add("item.minecraft.potion.effect.invigorate_long", "振奮藥水（延時）");
        add("item.minecraft.potion.effect.invigorate_longer", "振奮藥水（延時 II）");
        add("item.minecraft.potion.effect.invigorate_longest", "振奮藥水（延時 III）");
        add("item.minecraft.potion.effect.invigorate_strong", "振奮藥水 II");
        add("item.minecraft.potion.effect.invigorate_stronger", "振奮藥水 III");
        add("item.minecraft.potion.effect.invigorate_strongest", "振奮藥水 IV");
        add("item.minecraft.potion.effect.invigorate_long_strong", "振奮藥水（延時 + II）");
        add("item.minecraft.potion.effect.invigorate_longer_stronger", "振奮藥水（延時 II + III）");
        add("item.minecraft.potion.effect.invigorate_longest_strongest", "振奮藥水（延時 III + IV）");

        add("item.minecraft.splash_potion.effect.invigorate", "噴濺振奮藥水");
        add("item.minecraft.splash_potion.effect.invigorate_long", "噴濺振奮藥水（延時）");
        add("item.minecraft.splash_potion.effect.invigorate_longer", "噴濺振奮藥水（延時 II）");
        add("item.minecraft.splash_potion.effect.invigorate_longest", "噴濺振奮藥水（延時 III）");
        add("item.minecraft.splash_potion.effect.invigorate_strong", "噴濺振奮藥水 II");
        add("item.minecraft.splash_potion.effect.invigorate_stronger", "噴濺振奮藥水 III");
        add("item.minecraft.splash_potion.effect.invigorate_strongest", "噴濺振奮藥水 IV");
        add("item.minecraft.splash_potion.effect.invigorate_long_strong", "噴濺振奮藥水（延時 + II）");
        add("item.minecraft.splash_potion.effect.invigorate_longer_stronger", "噴濺振奮藥水（延時 II + III）");
        add("item.minecraft.splash_potion.effect.invigorate_longest_strongest", "噴濺振奮藥水（延時 III + IV）");

        add("item.minecraft.lingering_potion.effect.invigorate", "滯留振奮藥水");
        add("item.minecraft.lingering_potion.effect.invigorate_long", "滯留振奮藥水（延時）");
        add("item.minecraft.lingering_potion.effect.invigorate_longer", "滯留振奮藥水（延時 II）");
        add("item.minecraft.lingering_potion.effect.invigorate_longest", "滯留振奮藥水（延時 III）");
        add("item.minecraft.lingering_potion.effect.invigorate_strong", "滯留振奮藥水 II");
        add("item.minecraft.lingering_potion.effect.invigorate_stronger", "滯留振奮藥水 III");
        add("item.minecraft.lingering_potion.effect.invigorate_strongest", "滯留振奮藥水 IV");
        add("item.minecraft.lingering_potion.effect.invigorate_long_strong", "滯留振奮藥水（延時 + II）");
        add("item.minecraft.lingering_potion.effect.invigorate_longer_stronger", "滯留振奮藥水（延時 II + III）");
        add("item.minecraft.lingering_potion.effect.invigorate_longest_strongest", "滯留振奮藥水（延時 III + IV）");

        add("item.minecraft.tipped_arrow.effect.invigorate", "振奮之箭");
        add("item.minecraft.tipped_arrow.effect.invigorate_long", "振奮之箭（延時）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longer", "振奮之箭（延時 II）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longest", "振奮之箭（延時 III）");
        add("item.minecraft.tipped_arrow.effect.invigorate_strong", "振奮之箭 II");
        add("item.minecraft.tipped_arrow.effect.invigorate_stronger", "振奮之箭 III");
        add("item.minecraft.tipped_arrow.effect.invigorate_strongest", "振奮之箭 IV");
        add("item.minecraft.tipped_arrow.effect.invigorate_long_strong", "振奮之箭（延時 + II）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longer_stronger", "振奮之箭（延時 II + III）");
        add("item.minecraft.tipped_arrow.effect.invigorate_longest_strongest", "振奮之箭（延時 III + IV）");

        /* 進度與成就 */
        add("advancements.miraculousori.money_makes_the_miller_grind.title", "有錢能使鬼推磨");
        add("advancements.miraculousori.money_makes_the_miller_grind.description", "令磨坊主為汝推磨");
        add("advancements.miraculousori.food_question.title", "食乎？");
        add("advancements.miraculousori.food_question.description", "獲一土餅");
        add("advancements.miraculousori.wheres_the_eggshell.title", "雞子之殼何在？");
        add("advancements.miraculousori.wheres_the_eggshell.description", "製餅乾麵團時，并雞子殼入之");
        add("advancements.miraculousori.super_compression.title", "極致壓縮");
        add("advancements.miraculousori.super_compression.description", "以巨力徒手壓縮九小麥餅乾");
        add("advancements.miraculousori.did_i_really_eat.title", "吾果食乎？");
        add("advancements.miraculousori.did_i_really_eat.description", "食之不如不食之餅乾");
        add("advancements.miraculousori.more_honey.title", "再添蜂蜜");
        add("advancements.miraculousori.more_honey.description", "加蜂蜜少許於小麥麵包");
        add("advancements.miraculousori.it_flew_away.title", "其飛去矣");
        add("advancements.miraculousori.it_flew_away.description", "縱「氣球」飛去");
        add("advancements.miraculousori.what_a_fine_loaf.title", "誠乃好……包也");
        add("advancements.miraculousori.what_a_fine_loaf.description", "製一可食之「兵刃」");
        add("advancements.miraculousori.heavenly_injustice.title", "天理難容");
        add("advancements.miraculousori.heavenly_injustice.description", "遭天譴");
        add("advancements.miraculousori.earth_cannon.title", "大地之砲");
        add("advancements.miraculousori.earth_cannon.description", "藉重力，自射而出");
        add("advancements.miraculousori.press_residue.title", "壓榨餘渣汁");
        add("advancements.miraculousori.press_residue.description", "猛力榨汁");
        add("advancements.miraculousori.god_gaze.title", "彼投下注視");
        add("advancements.miraculousori.god_gaze.description", "引彼視線，求飽和");
        add("advancements.miraculousori.blessing_amulet.title", "佑護加身");
        add("advancements.miraculousori.blessing_amulet.description", "製得食神之佑");
        add("advancements.miraculousori.nether_root.title", "吾思此物可食");
        add("advancements.miraculousori.nether_root.description", "於下界得新食");
        add("advancements.miraculousori.nether_energy.title", "下界小子力甚偉");
        add("advancements.miraculousori.nether_energy.description", "熾熱烈焰粉");
        add("advancements.miraculousori.nether_seed.title", "枯魂之生機");
        add("advancements.miraculousori.nether_seed.description", "靈魂沙上之稼穡");
        add("advancements.miraculousori.nether_wheat.title", "下界異種");
        add("advancements.miraculousori.nether_wheat.description", "令纏魂麥結實");
        add("advancements.miraculousori.nether_curse.title", "彼降咒罰");
        add("advancements.miraculousori.nether_curse.description", "感饑餓");
        add("advancements.miraculousori.nether_ode.title", "詛咒亦有形");
        add("advancements.miraculousori.nether_ode.description", "是詛咒亦為力");
        add("advancements.miraculousori.root.title", "芥源奇食");
        add("advancements.miraculousori.root.description", "美食之途已啟");
        add("advancements.miraculousori.end_root.title", "終界美食家");
        add("advancements.miraculousori.end_root.description", "於終界得新瓜種");
        add("advancements.miraculousori.end_melon.title", "食此或飛乎？");
        add("advancements.miraculousori.end_melon.description", "觀浮瓜所落之物，陷入沉思");
        add("advancements.miraculousori.end_jump.title", "近程折躍");
        add("advancements.miraculousori.end_jump.description", "食躍進果，遂短距躍遷");
        add("advancements.miraculousori.end_plant.title", "終界石中之植");
        add("advancements.miraculousori.end_plant.description", "令躍進果株復甦");
        add("advancements.miraculousori.end_speech.title", "聆音終末");
        add("advancements.miraculousori.end_speech.description", "見證終末之詩後，復自終界歸主世");
        add("advancements.miraculousori.end_journey.title", "終界之旅");
        add("advancements.miraculousori.end_journey.description", "踏上終界之途，乃終亦乃新始");

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
        add("fluid.miraculousori.glow_berry_juice", "螢光漿果汁");
        add("fluid.miraculousori.cocoa_butter", "可可脂");

        /* 物品屬性 */
        add("tooltip.miraculousori.owner", "所有者：%s");
        add("tooltip.miraculousori.no_owner", "無主之物");
        add("message.miraculousori.stolen_divine", "-<神罰將至>-");

        /* 死亡消息 */
        add("death.attack.bloat", "%1$s飽斃");
        add("death.attack.bloat.player", "%1$s於%2$s餵食之下飽斃");
    }
}