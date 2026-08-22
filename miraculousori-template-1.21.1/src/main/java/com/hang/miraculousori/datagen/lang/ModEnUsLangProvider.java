package com.hang.miraculousori.datagen.lang;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.entity.ModEntities;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, MiraculousOriginFoodMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {

        /* 普通物品 */
        add(ModItems.SATURATION_TOTEM.get(), "§9Saturation Totem");
        add(ModItems.FOOD_GOD_BLESSING_AMULET.get(), "§6Food God Blessing Amulet");
        add(ModItems.GOD_GAZE.get(), "§cGod Gaze");
        add(ModItems.HUNGER_CURSE_PUNISHMENT.get(), "§4Hunger Curse Punishment");
        add(ModItems.HUNGER_CURSE_ODE.get(), "§0Hunger Curse Ode");
        add(ModItems.HUNGER_TOTEM.get(), "§7Hunger Totem");
        add(ModItems.ENDING_SPEECH.get(), "§dEnding Speech");
        add(ModItems.END_ROAD_RUNE.get(), "§bEnd Road Rune");
        add(ModItems.END_NEW_PATH.get(), "§5End New Path");

        add(ModItems.RAW_BAGUETTE.get(), "Raw Baguette");
        add(ModItems.RAW_POTATO_PATTY.get(), "Raw Potato Patty");

        add(ModItems.WHEAT_FLOUR.get(), "Wheat Flour");
        add(ModItems.WHEAT_DOUGH.get(), "Wheat Dough");
        add(ModItems.SOUL_WHEAT.get(), "Soul Wheat");
        add(ModItems.NETHER_SOUL_WHEAT_FLOUR.get(), "Nether Soul Wheat Flour");
        add(ModItems.NETHER_SOUL_WHEAT_DOUGH.get(), "Nether Soul Wheat Dough");
        add(ModItems.SUGAR_CUBE.get(), "Sugar Cube");
        add(ModItems.CARAMEL.get(), "Caramel");
        add(ModItems.WET_CLAY_BALL.get(), "Wet Clay Ball");
        add(ModItems.COOKIE_DOUGH.get(), "Cookie Dough");
        add(ModItems.RAW_COOKIE.get(), "Raw Cookie");
        add(ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get(), "Raw Nether Soul Wheat Cookie");
        add(ModItems.COOKIE_MOLD.get(), "Cookie Mold");
        add(ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get(), "Wheat Dough Filled Cookie Mold");
        add(ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get(), "Nether Soul Wheat Cookie Mold");
        add(ModItems.BLAZE_INCENSE_POWDER.get(), "Blaze Incense Powder");
        add(ModItems.NETHER_INCENSE_POWDER.get(), "Nether Incense Powder");
        add(ModItems.CRIMSON_FRUIT_POWDER.get(), "Crimson Fruit Powder");
        add(ModItems.WARPED_FRUIT_POWDER.get(), "Warped Fruit Powder");
        add(ModItems.NETHER_SOUL_WHEAT_SEEDS.get(), "Nether Soul Wheat Seeds");
        add(ModItems.DIVINE_UPGRADE_TEMPLATE.get(), "§bDivine Upgrade Template");
        add(ModItems.BEETROOT_PULP.get(), "Beetroot Pulp");
        add(ModItems.SWEET_BERRY_PULP.get(), "Berry Pulp");
        add(ModItems.SUGARCANE_PULP.get(), "Sugar Cane Pulp");
        add(ModItems.GLOW_BERRY_PULP.get(), "Glow Berry Pulp");
        add(ModItems.COCOA_BUTTER_MOLD.get(), "Cocoa Butter Mold");
        add(ModItems.WATER_MOLD.get(), "Water Mold");
        add(ModItems.DARK_CHOCOLATE_PASTE_MOLD.get(), "Dark Chocolate Paste Mold");
        add(ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get(), "White Chocolate Filled Mold");
        add(ModItems.WITHERED_SOULWEED.get(), "Withered Soulweed");
        add(ModItems.CRUSHED_SALT.get(), "Crushed Salt");
        add(ModItems.SALT.get(), "Salt");
        add(ModItems.FLOATING_MELON_SEEDS.get(), "Floating Melon Seeds");

        add(ModItems.VEGETABLE_CLAY_CAKE.get(), "Vegetable Clay Cake");
        add(ModItems.CLAY_CAKE.get(), "Clay Cake");
        add(ModItems.SWEET_CLAY_CAKE.get(), "Sweet Clay Cake");
        add(ModItems.ENDER_CLAY_CAKE.get(), "Ender Clay Cake");

        /* 食品 */
        add(ModItems.VEGETABLE_COOKED_CLAY_CAKE.get(), "Vegetable Cooked Clay Cake");
        add(ModItems.SWEET_COOKED_CLAY_CAKE.get(), "Sweet Cooked Clay Cake");
        add(ModItems.COOKED_CLAY_CAKE.get(), "Cooked Clay Cake");
        add(ModItems.ENDER_COOKED_CLAY_CAKE.get(), "Ender Cooked Clay Cake");
        add(ModItems.WHEAT_COOKIE.get(), "Wheat Cookie");
        add(ModItems.NETHER_CLAY_CAKE.get(), "Nether Clay Cake");
        add(ModItems.NETHER_SOUL_WHEAT_COOKIE.get(), "Nether Soul Wheat Cookie");
        add(ModItems.WARPED_FRUIT.get(), "Warped Fruit");
        add(ModItems.CRIMSON_FRUIT.get(), "Crimson Fruit");
        add(ModItems.COMPRESSED_WHEAT_BISCUIT.get(), "Compressed Wheat Biscuit");
        add(ModItems.VERY_SWEET_BREAD.get(), "Very Sweet Bread");
        add(ModItems.WHEAT_BREAD.get(), "Wheat Bread");
        add(ModItems.HONEY_BREAD.get(), "Honey Bread");
        add(ModItems.BAGUETTE.get(), "Baguette");
        add(ModItems.LARGE_BAGUETTE_HALF.get(), "Large Baguette Half");
        add(ModItems.SMALL_BAGUETTE_HALF.get(), "Small Baguette Half");
        add(ModItems.CHINESE_SUGAR_FREE_BREAD.get(), "Chinese Sugar-Free Bread");
        add(ModItems.MASHED_POTATO.get(), "Mashed Potato");
        add(ModItems.COOKED_POTATO_PATTY.get(), "Cooked Potato Patty");
        add(ModItems.DARK_CHOCOLATE.get(), "Dark Chocolate");
        add(ModItems.WHITE_CHOCOLATE.get(), "White Chocolate");
        add(ModItems.LEAPING_FRUIT.get(), "Leaping Fruit");
        add(ModItems.FLOATING_MELON.get(), "Floating Melon");
        add(ModItems.BROKEN_FLOATING_GOURD.get(), "Broken Floating Gourd");
        add(ModItems.FLOATING_MELON_JAM.get(), "Floating Melon Jam");
        add(ModItems.FLOATING_MELON_PIE.get(), "Floating Melon Pie");

        /* 饮品 */
        add(ModItems.WATER_GLASS.get(), "Water Glass");
        add(ModItems.APPLE_JUICE_BOTTLE.get(), "Apple Juice Bottle");
        add(ModItems.APPLE_JUICE_GLASS.get(), "Apple Juice Glass");
        add(ModItems.APPLE_PULP.get(), "Apple Pulp");
        add(ModItems.WATERMELON_JUICE_BOTTLE.get(), "Watermelon Juice Bottle");
        add(ModItems.WATERMELON_JUICE_GLASS.get(), "Watermelon Juice Glass");
        add(ModItems.WATERMELON_PULP.get(), "Watermelon Pulp");
        add(ModItems.CARROT_JUICE_BOTTLE.get(), "Carrot Juice Bottle");
        add(ModItems.CARROT_JUICE_GLASS.get(), "Carrot Juice Glass");
        add(ModItems.CARROT_PULP.get(), "Carrot Pulp");
        add(ModItems.COCOA_BOTTLE.get(), "Cocoa Butter Bottle");
        add(ModItems.COCOA_GLASS.get(), "Cocoa Butter Glass");
        add(ModItems.COCOA_POWDER.get(), "Cocoa Powder");
        add(ModItems.GLASS_CUP.get(), "Glass Cup");
        add(ModItems.SUGARCANE_JUICE_BOTTLE.get(), "Sugar Cane Juice Bottle");
        add(ModItems.BEETROOT_JUICE_BOTTLE.get(), "Beetroot Juice Bottle");
        add(ModItems.SUGARCANE_JUICE_GLASS.get(), "Sugar Cane Juice Glass");
        add(ModItems.BEETROOT_JUICE_GLASS.get(), "Beetroot Juice Glass");
        add(ModItems.SWEET_BERRY_JUICE_BOTTLE.get(), "Sweet Berry Juice Bottle");
        add(ModItems.GLOW_BERRY_JUICE_BOTTLE.get(), "Glow Berry Juice Bottle");
        add(ModItems.SWEET_BERRY_JUICE_GLASS.get(), "Sweet Berry Juice Glass");
        add(ModItems.GLOW_BERRY_JUICE_GLASS.get(), "Glow Berry Juice Glass");
        add(ModItems.WATERMELON_JUICE_BUCKET.get(), "Watermelon Juice Bucket");
        add(ModItems.SWEET_BERRY_JUICE_BUCKET.get(), "Sweet Berry Juice Bucket");
        add(ModItems.GLOW_BERRY_JUICE_BUCKET.get(), "Glow Berry Juice Bucket");
        add(ModItems.APPLE_JUICE_BUCKET.get(), "Apple Juice Bucket");
        add(ModItems.CARROT_JUICE_BUCKET.get(), "Carrot Juice Bucket");
        add(ModItems.BEETROOT_JUICE_BUCKET.get(), "Beetroot Juice Bucket");
        add(ModItems.SUGARCANE_JUICE_BUCKET.get(), "Sugar Cane Juice Bucket");
        add(ModItems.COCOA_BUCKET.get(), "Cocoa Butter Bucket");

        /* 方块 */
        add(ModBlocks.SUGAR_BLOCK.get(), "Sugar Block");
        add(ModBlocks.WARPED_VINE_PLATFORM.get(), "Warped Vine Platform");
        add(ModBlocks.MILL_BLOCK.get(), "Mill Block");
        add(ModBlocks.WITHERED_SOULWEED.get(), "Withered Soulweed Block");
        add(ModBlocks.SALT_ROCK.get(), "Salt Rock");
        add(ModBlocks.DEEPSLATE_SALT_ROCK.get(), "Deepslate Salt Rock");
        add(ModBlocks.FLOATING_MELON_BLOCK.get(), "Floating Melon Block");
        add(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), "Leaping Fruit Plant - Spreading Root");
        add(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get(), "Leaping Fruit Plant - Root");
        add(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get(), "Leaping Fruit Plant - Top");

        /* 药水效果 */
        add(ModMobEffects.STUFFED.get(), "Stuffing");
        add(ModMobEffects.OVERFULL.get(), "Overfull");
        add(ModMobEffects.HUNGER_AFFLICTION.get(), "Hunger Affliction");
        add(ModMobEffects.SATURATION_DECAY.get(), "Saturation Decay");
        add(ModMobEffects.PLEASURE.get(), "Pleasure");
        add(ModMobEffects.TELEPORT_ON_DAMAGE.get(), "Teleport on Damage");
        add(ModMobEffects.INVIGORATE.get(), "Invigorate");
        add(ModMobEffects.DASH.get(), "Dash");
        add(ModMobEffects.WORKING.get(), "Working");
        add(ModMobEffects.REVERSED_GRAVITY.get(), "Reversed Gravity");

        /* 实体 */
        add(ModEntities.FLOATING_MELON.get(), "Floating Melon");

        /* 药水瓶*/
        add("item.minecraft.potion.effect.invigorate", "Invigorate Potion");
        add("item.minecraft.potion.effect.invigorate_long", "Invigorate Potion (Delay)");
        add("item.minecraft.potion.effect.invigorate_longer", "Invigorate Potion (Delay II)");
        add("item.minecraft.potion.effect.invigorate_longest", "Invigorate Potion (Delay III)");
        add("item.minecraft.potion.effect.invigorate_strong", "Invigorate Potion II");
        add("item.minecraft.potion.effect.invigorate_stronger", "Invigorate Potion III");
        add("item.minecraft.potion.effect.invigorate_strongest", "Invigorate Potion IV");
        add("item.minecraft.potion.effect.invigorate_long_strong", "Invigorate Potion (Delay + II)");
        add("item.minecraft.potion.effect.invigorate_longer_stronger", "Invigorate Potion (Delay II + III)");
        add("item.minecraft.potion.effect.invigorate_longest_strongest", "Invigorate Potion (Delay III + IV)");

        add("item.minecraft.splash_potion.effect.invigorate", "Splash Invigorate Potion");
        add("item.minecraft.splash_potion.effect.invigorate_long", "Splash Invigorate Potion (Delay)");
        add("item.minecraft.splash_potion.effect.invigorate_longer", "Splash Invigorate Potion (Delay II)");
        add("item.minecraft.splash_potion.effect.invigorate_longest", "Splash Invigorate Potion (Delay III)");
        add("item.minecraft.splash_potion.effect.invigorate_strong", "Splash Invigorate Potion II");
        add("item.minecraft.splash_potion.effect.invigorate_stronger", "Splash Invigorate Potion III");
        add("item.minecraft.splash_potion.effect.invigorate_strongest", "Splash Invigorate Potion IV");
        add("item.minecraft.splash_potion.effect.invigorate_long_strong", "Splash Invigorate Potion (Delay + II)");
        add("item.minecraft.splash_potion.effect.invigorate_longer_stronger", "Splash Invigorate Potion (Delay II + III)");
        add("item.minecraft.splash_potion.effect.invigorate_longest_strongest", "Splash Invigorate Potion (Delay III + IV)");

        add("item.minecraft.lingering_potion.effect.invigorate", "Lingering Invigorate Potion");
        add("item.minecraft.lingering_potion.effect.invigorate_long", "Lingering Invigorate Potion (Delay)");
        add("item.minecraft.lingering_potion.effect.invigorate_longer", "Lingering Invigorate Potion (Delay II)");
        add("item.minecraft.lingering_potion.effect.invigorate_longest", "Lingering Invigorate Potion (Delay III)");
        add("item.minecraft.lingering_potion.effect.invigorate_strong", "Lingering Invigorate Potion II");
        add("item.minecraft.lingering_potion.effect.invigorate_stronger", "Lingering Invigorate Potion III");
        add("item.minecraft.lingering_potion.effect.invigorate_strongest", "Lingering Invigorate Potion IV");
        add("item.minecraft.lingering_potion.effect.invigorate_long_strong", "Lingering Invigorate Potion (Delay + II)");
        add("item.minecraft.lingering_potion.effect.invigorate_longer_stronger", "Lingering Invigorate Potion (Delay II + III)");
        add("item.minecraft.lingering_potion.effect.invigorate_longest_strongest", "Lingering Invigorate Potion (Delay III + IV)");

        add("item.minecraft.tipped_arrow.effect.invigorate", "Arrow of Invigorate");
        add("item.minecraft.tipped_arrow.effect.invigorate_long", "Arrow of Invigorate (Delay)");
        add("item.minecraft.tipped_arrow.effect.invigorate_longer", "Arrow of Invigorate (Delay II)");
        add("item.minecraft.tipped_arrow.effect.invigorate_longest", "Arrow of Invigorate (Delay III)");
        add("item.minecraft.tipped_arrow.effect.invigorate_strong", "Arrow of Invigorate II");
        add("item.minecraft.tipped_arrow.effect.invigorate_stronger", "Arrow of Invigorate III");
        add("item.minecraft.tipped_arrow.effect.invigorate_strongest", "Arrow of Invigorate IV");
        add("item.minecraft.tipped_arrow.effect.invigorate_long_strong", "Arrow of Invigorate (Delay + II)");
        add("item.minecraft.tipped_arrow.effect.invigorate_longer_stronger", "Arrow of Invigorate (Delay II + III)");
        add("item.minecraft.tipped_arrow.effect.invigorate_longest_strongest", "Arrow of Invigorate (Delay III + IV)");

        /* 创造标签页 */
        add("itemGroup.miraculous_origin_food_tab", "Miraculous Origin Food");
        add("itemGroup.miraculous_origin_food_the_nether_tab", "Miraculous Origin Food | The Nether");
        add("itemGroup.miraculous_origin_food_the_end_tab", "Miraculous Origin Food | The End");

        /*容器标题文本*/
        add("container.miraculousori.mill", "Mill");

        /* 村民职业 */
        add("entity.minecraft.villager.miraculousori.miller", "Miller");

        /* 成就与进度 */
        add("advancements.miraculousori.money_makes_the_miller_grind.title", "Money Makes the Miller Grind");
        add("advancements.miraculousori.money_makes_the_miller_grind.description", "Get the miller to work for you");
        add("advancements.miraculousori.food_question.title", "Food?");
        add("advancements.miraculousori.food_question.description", "Obtain a clay cake");
        add("advancements.miraculousori.wheres_the_eggshell.title", "Where's the Eggshell?");
        add("advancements.miraculousori.wheres_the_eggshell.description", "Put eggshells in the cookie dough");
        add("advancements.miraculousori.super_compression.title", "Super Compression");
        add("advancements.miraculousori.super_compression.description", "Compress 9 wheat cookies by hand with great force");
        add("advancements.miraculousori.did_i_really_eat.title", "Did I Really Eat?");
        add("advancements.miraculousori.did_i_really_eat.description", "Eat a cookie that is better not eaten");
        add("advancements.miraculousori.more_honey.title", "More Honey");
        add("advancements.miraculousori.more_honey.description", "Add some honey to wheat bread");
        add("advancements.miraculousori.it_flew_away.title", "It Flew Away");
        add("advancements.miraculousori.it_flew_away.description", "Let go of the 'balloon'");
        add("advancements.miraculousori.what_a_fine_loaf.title", "What a Fine... Loaf?");
        add("advancements.miraculousori.what_a_fine_loaf.description", "Make an edible 'weapon'");
        add("advancements.miraculousori.heavenly_injustice.title", "Heavenly Injustice");
        add("advancements.miraculousori.heavenly_injustice.description", "Provoke divine punishment");
        add("advancements.miraculousori.earth_cannon.title", "Earth Cannon");
        add("advancements.miraculousori.earth_cannon.description", "Use gravity to launch yourself");
        add("advancements.miraculousori.press_residue.title", "Press the Residue");
        add("advancements.miraculousori.press_residue.description", "Squeeze out every last drop");
        add("advancements.miraculousori.god_gaze.title", "He Casts His Gaze");
        add("advancements.miraculousori.god_gaze.description", "Attract His attention, seek saturation");
        add("advancements.miraculousori.blessing_amulet.title", "Blessing Upon You");
        add("advancements.miraculousori.blessing_amulet.description", "Craft the Food God Blessing Amulet");
        add("advancements.miraculousori.nether_root.title", "I Think This Stuff Is Edible");
        add("advancements.miraculousori.nether_root.description", "Discover new food in the Nether");
        add("advancements.miraculousori.nether_energy.title", "Nether Lad Has Great Energy");
        add("advancements.miraculousori.nether_energy.description", "Hot hot blaze powder");
        add("advancements.miraculousori.nether_seed.title", "Life in the Withered Soul");
        add("advancements.miraculousori.nether_seed.description", "Crop on soul sand");
        add("advancements.miraculousori.nether_wheat.title", "Nether Special Species");
        add("advancements.miraculousori.nether_wheat.description", "Let the Soul Wheat bear fruit");
        add("advancements.miraculousori.nether_curse.title", "He Casts the Curse");
        add("advancements.miraculousori.nether_curse.description", "Feel the hunger");
        add("advancements.miraculousori.nether_ode.title", "The Curse Takes Form");
        add("advancements.miraculousori.nether_ode.description", "It's both a curse and power");
        add("advancements.miraculousori.root.title", "Miraculous Origin Food");
        add("advancements.miraculousori.root.description", "The food journey begins");
        add("advancements.miraculousori.end_root.title", "End Gourmet");
        add("advancements.miraculousori.end_root.description", "Discover new melon seeds in the End");
        add("advancements.miraculousori.end_melon.title", "Will I Fly After Eating This?");
        add("advancements.miraculousori.end_melon.description", "Staring at the item dropped by the floating melon, lost in thought");
        add("advancements.miraculousori.end_jump.title", "Short-Range Leap");
        add("advancements.miraculousori.end_jump.description", "Eat a Leaping Fruit, then short-range leap");
        add("advancements.miraculousori.end_plant.title", "Plant in the End Stone");
        add("advancements.miraculousori.end_plant.description", "Revitalize the Leaping Fruit Plant");
        add("advancements.miraculousori.end_speech.title", "Echo of the End");
        add("advancements.miraculousori.end_speech.description", "Return to the overworld after witnessing the End Poem");
        add("advancements.miraculousori.end_journey.title", "End Journey");
        add("advancements.miraculousori.end_journey.description", "Embark on a journey to the End, an end and a new beginning");

        /* JEI 适配 */
        add("jei.category.miraculousori.mill", "Mill Grinding");
        add("jei.category.miraculousori.liquid_filling", "Liquid Filling");

        /* 液体名称 */
        add("fluid.minecraft.water", "Water");
        add("fluid.miraculousori.apple_juice", "Apple Juice");
        add("fluid.miraculousori.watermelon_juice", "Watermelon Juice");
        add("fluid.miraculousori.carrot_juice", "Carrot Juice");
        add("fluid.miraculousori.sugarcane_juice", "Sugarcane Juice");
        add("fluid.miraculousori.beetroot_juice", "Beetroot Juice");
        add("fluid.miraculousori.sweet_berry_juice", "Sweet Berry Juice");
        add("fluid.miraculousori.glow_berry_juice", "Glow Berry Juice");
        add("fluid.miraculousori.cocoa_butter", "Cocoa Butter");

        /* 物品属性 */
        add("tooltip.miraculousori.owner", "Owner: %s");
        add("tooltip.miraculousori.no_owner", "No Owner");
        add("message.miraculousori.stolen_divine", "You are not worthy! Divine punishment incoming!");

        /* 死亡消息 */
        add("death.attack.bloat", "%1$s was bloated to death");
        add("death.attack.bloat.player", "%1$s was force-fed to death by %2$s");
    }
}
