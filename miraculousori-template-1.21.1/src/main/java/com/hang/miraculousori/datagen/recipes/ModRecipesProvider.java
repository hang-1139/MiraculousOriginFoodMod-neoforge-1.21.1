package com.hang.miraculousori.datagen.recipes;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.fluid.ModFluids;
import com.hang.miraculousori.item.ModItems;
import com.hang.miraculousori.recipe.LiquidFillingRecipe;
import com.hang.miraculousori.recipe.MillRecipe;
import com.hang.miraculousori.recipe.VerySweetBreadRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    // protected static final List<ItemLike> WHEAT_ORE = List.of(ModItems.COOKED_CLAY_CAKE, ModItems.CLAY_CAKE);

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);
        /* 工作台 */
        // 无序配方：小麦面团 + 糖 + 鸡蛋 = 饼干面团
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COOKIE_DOUGH)
                .requires(ModItems.WHEAT_DOUGH.get())
                .requires(Items.SUGAR)
                .requires(Items.EGG)
                .unlockedBy("has_wheat_dough", has(ModItems.WHEAT_DOUGH.get()))
                .save(recipeOutput);

        // 无序配方：饼干面团 + 饼干模具 = 饼干面团填充饼干模具（解锁条件：拥有饼干模具）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD)
                .requires(ModItems.COOKIE_DOUGH.get())
                .requires(ModItems.COOKIE_MOLD.get())
                .unlockedBy("has_cookie_mold", has(ModItems.COOKIE_MOLD.get()))
                .save(recipeOutput);

        // 无序配方：饼干面团 + 饼干模具 = 饼干面团填充饼干模具（解锁条件：拥有饼干模具）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_COOKIE)
                .requires(ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get())
                .unlockedBy("has_wheat_dough_filled_cookie_mold", has(ModItems.WHEAT_DOUGH_FILLED_COOKIE_MOLD.get()))
                .save(recipeOutput);

        // 无序配方：下界灵魂小麦面团 + 饼干模具 = 下界灵魂小麦面团填充饼干模具（解锁条件：拥有饼干模具）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD)
                .requires(ModItems.NETHER_SOUL_WHEAT_DOUGH.get())
                .requires(ModItems.COOKIE_MOLD.get())
                .unlockedBy("has_nether_soul_wheat_dough", has(ModItems.COOKIE_MOLD.get()))
                .save(recipeOutput);

        // 无序配方：下界灵魂小麦面团 + 饼干模具 = 下界灵魂小麦面团填充饼干模具（解锁条件：拥有饼干模具）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE)
                .requires(ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get())
                .unlockedBy("has_nether_soul_wheat_dough", has(ModItems.NETHER_SOUL_WHEAT_COOKIE_MOLD.get()))
                .save(recipeOutput);

        // 无序配方：下界香粉 = 猩红果粉 + 诡异果粉
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NETHER_INCENSE_POWDER.get())
                .requires(ModItems.CRIMSON_FRUIT_POWDER.get())
                .requires(ModItems.WARPED_FRUIT_POWDER.get())
                .unlockedBy("has_crimson_fruit_powder", has(ModItems.CRIMSON_FRUIT_POWDER.get()))
                .save(recipeOutput);

        // 5. VEGETABLE_CLAY_CAKE * 2 = 湿润的粘土球 + 土豆 + 胡萝卜 + 甜菜根
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VEGETABLE_CLAY_CAKE.get(), 2)
                .requires(ModItems.WET_CLAY_BALL.get())
                .requires(Items.POTATO)
                .requires(Items.CARROT)
                .requires(Items.BEETROOT)
                .unlockedBy("has_wet_clay_ball", has(ModItems.WET_CLAY_BALL.get()))
                .save(recipeOutput);

        // 6. CLAY_CAKE = 湿润的粘土球
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CLAY_CAKE.get())
                .requires(ModItems.WET_CLAY_BALL.get())
                .unlockedBy("has_wet_clay_ball", has(ModItems.WET_CLAY_BALL.get()))
                .save(recipeOutput);

        // 7. SWEET_CLAY_CAKE (方式1) = 湿润的粘土球 + 糖×4
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SWEET_CLAY_CAKE.get())
                .requires(ModItems.WET_CLAY_BALL.get())
                .requires(Items.SUGAR)
                .requires(Items.SUGAR)
                .requires(Items.SUGAR)
                .requires(Items.SUGAR)
                .unlockedBy("has_wet_clay_ball", has(ModItems.WET_CLAY_BALL.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":sweet_clay_cake_from_sugar_4");

        // 8. SWEET_CLAY_CAKE (方式2) = 湿润的粘土球 + 方糖
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SWEET_CLAY_CAKE.get())
                .requires(ModItems.WET_CLAY_BALL.get())
                .requires(ModItems.SUGAR_CUBE.get())
                .unlockedBy("has_wet_clay_ball", has(ModItems.WET_CLAY_BALL.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":sweet_clay_cake_from_sugar_cube");

        // 9. ENDER_CLAY_CAKE = 湿润的粘土球 + 紫颂果 + 末影眼
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDER_CLAY_CAKE.get())
                .requires(ModItems.WET_CLAY_BALL.get())
                .requires(Items.CHORUS_FRUIT)
                .requires(Items.ENDER_EYE)
                .unlockedBy("has_wet_clay_ball", has(ModItems.WET_CLAY_BALL.get()))
                .save(recipeOutput);

        // 10. BLAZE_INCENSE_POWDER = 下界香粉 + 烈焰粉
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLAZE_INCENSE_POWDER.get())
                .requires(ModItems.NETHER_INCENSE_POWDER.get())
                .requires(Items.BLAZE_POWDER)
                .unlockedBy("has_nether_incense_powder", has(ModItems.NETHER_INCENSE_POWDER.get()))
                .save(recipeOutput);

        // 11. NETHER_CLAY_CAKE (方式1) = 湿润的粘土球 + 烈焰香粉
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NETHER_CLAY_CAKE.get())
                .requires(ModItems.WET_CLAY_BALL.get())
                .requires(ModItems.BLAZE_INCENSE_POWDER.get())
                .unlockedBy("has_wet_clay_ball", has(ModItems.WET_CLAY_BALL.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":nether_clay_cake_from_blaze_incense");

        // 12. NETHER_CLAY_CAKE (方式2) = 湿润的粘土球 + 下界香粉 + 烈焰粉
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NETHER_CLAY_CAKE.get())
                .requires(ModItems.WET_CLAY_BALL.get())
                .requires(ModItems.NETHER_INCENSE_POWDER.get())
                .requires(Items.BLAZE_POWDER)
                .unlockedBy("has_wet_clay_ball", has(ModItems.WET_CLAY_BALL.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":nether_clay_cake_from_nether_incense_blaze");

        // 无序：13. RAW_POTATO_PATTY = 煮熟的土豆 + 小麦面粉
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_POTATO_PATTY.get())
                .requires(ModItems.MASHED_POTATO.get())
                .requires(ModItems.WHEAT_FLOUR.get())
                .requires(ModItems.SALT.get(), 2)
                .unlockedBy("has_mashed_potato", has(ModItems.MASHED_POTATO.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":raw_potato_patty_from_mashed_potato_and_wheat_flour");

        // 无序：14. COCOA_BOTTLE = 可可粉×2 + 瓶子
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COCOA_BOTTLE.get())
                .requires(ModItems.COCOA_POWDER.get(),2)
                .requires(Items.POTION)
                .unlockedBy("has_cocoa_powder", has(ModItems.COCOA_POWDER.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":cocoa_bottle_from_cocoa_powder_and_potion");

        // 无序：15. COCOA_GLASS = 可可粉×4 + 水玻璃
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COCOA_GLASS.get())
                .requires(ModItems.COCOA_POWDER.get(), 4)
                .requires(ModItems.WATER_GLASS.get())
                .unlockedBy("has_cocoa_powder", has(ModItems.COCOA_POWDER.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":cocoa_glass_from_cocoa_powder_and_water_glass");

        // 无序：16. COCOA_BUCKET = 可可粉×8 + 桶
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COCOA_BUCKET.get())
                .requires(ModItems.COCOA_POWDER.get(), 8)
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_cocoa_powder", has(ModItems.COCOA_POWDER.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":cocoa_bucket_from_cocoa_powder_and_bucket");

        // 无序：17. 可可粉*2 + 模具 = 黑巧模具
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DARK_CHOCOLATE_PASTE_MOLD.get())
                .requires(ModItems.COCOA_POWDER.get(),2)
                .requires(ModItems.WATER_MOLD.get())
                .unlockedBy("has_cocoa_powder", has(ModItems.COCOA_POWDER.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":water_mold_from_cocoa_powder_and_cookie_mold");

        // 无序：18. WHITE_CHOCOLATE_FILLED_MOLD = 糖×4 + 可可脂模具×4 + 牛奶桶×4
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get(), 4)
                .requires(Items.SUGAR, 4)
                .requires(ModItems.COCOA_BUTTER_MOLD.get(), 4)
                .requires(Items.MILK_BUCKET)
                .unlockedBy("has_cocoa_butter_mold", has(ModItems.COCOA_BUTTER_MOLD.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":white_chocolate_filled_mold_from_sugar_and_cocoa_butter_mold");

        // 无序：18. WHITE_CHOCOLATE_FILLED_MOLD = 方糖 + 可可模具×4 + 牛奶桶
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WHITE_CHOCOLATE_FILLED_MOLD.get())
                .requires(ModItems.SUGAR_CUBE)
                .requires(ModItems.COCOA_BUTTER_MOLD.get(), 4)
                .requires(Items.MILK_BUCKET)
                .unlockedBy("has_cocoa_butter_mold", has(ModItems.COCOA_BUTTER_MOLD.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":white_chocolate_filled_mold_from_sugar_cube_and_cocoa_butter_mold");

        // 19. 浮瓜饼 = 糖 + 浮瓜酱×4 + 鸡蛋 + 浮瓜
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLOATING_MELON_PIE.get())
                .requires(Items.SUGAR)
                .requires(ModItems.FLOATING_MELON_JAM.get(), 2)
                .requires(Items.EGG)
                .requires(ModItems.FLOATING_MELON)
                .unlockedBy("floating_melon_jam", has(ModItems.FLOATING_MELON_JAM.get()))
                .save(recipeOutput, MiraculousOriginFoodMod.MODID + ":float_melon_pie_from_floating_melon_jam");



        // 自定义配方（齁甜面包）
        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "bread/very_sweet_bread");
        recipeOutput.accept(recipeId, new VerySweetBreadRecipe(CraftingBookCategory.MISC), null);



        // 2. 有序配方：tpt 形状（t=铁粒，p=铁锭）=> 饼干模具
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COOKIE_MOLD)
                .pattern("tpt")
                .define('t', Items.IRON_NUGGET)
                .define('p', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        // 2. 有序配方：### 形状（#=小麦饼干）=> 压实的小麦饼干
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.COMPRESSED_WHEAT_BISCUIT)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.WHEAT_COOKIE)
                .unlockedBy("has_wheat_cookie", has(ModItems.WHEAT_COOKIE.get()))
                .save(recipeOutput);

        // 2. 有序配方：## 形状（#=糖）=> 方糖
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SUGAR_CUBE)
                .pattern("##")
                .pattern("##")
                .define('#',Items.SUGAR)
                .unlockedBy("has_sugar", has(Items.SUGAR))
                .save(recipeOutput);

        // 2. 有序配方：## 形状（#=糖）=> 方糖
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SUGAR_BLOCK)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.SUGAR_CUBE)
                .unlockedBy("has_sugar_cube", has(ModItems.SUGAR_CUBE.get()))
                .save(recipeOutput);

        // 2. 有序配方：## 形状（#=糖）=> 方糖
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RAW_BAGUETTE)
                .pattern("#  ")
                .pattern(" # ")
                .pattern("  #")
                .define('#', ModItems.WHEAT_DOUGH)
                .unlockedBy("has_wheat_dough", has(ModItems.WHEAT_DOUGH.get()))
                .save(recipeOutput);

        // 3. 有序配方：饱和图腾
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SATURATION_TOTEM)
                .pattern("#v#")
                .pattern("cts")
                .pattern("bbb")
                .define('#', ModItems.CARAMEL)
                .define('v', ModItems.VEGETABLE_COOKED_CLAY_CAKE)
                .define('c', ModItems.COOKED_CLAY_CAKE)
                .define('t', Items.TOTEM_OF_UNDYING)
                .define('s', ModItems.SWEET_COOKED_CLAY_CAKE)
                .define('b', ModItems.COMPRESSED_WHEAT_BISCUIT)
                .unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING))
                .save(recipeOutput);

        // 4. 有序配方：饥饿图腾
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HUNGER_TOTEM)
                .pattern("#n#")
                .pattern("ntn")
                .pattern("#n#")
                .define('#', ModItems.NETHER_SOUL_WHEAT_COOKIE)
                .define('n', ModItems.NETHER_CLAY_CAKE)
                .define('t', Items.TOTEM_OF_UNDYING)
                .unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING))
                .save(recipeOutput);

        // 5. 有序配方：mill
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MILL_BLOCK)
                .pattern("sss")
                .pattern("bib")
                .pattern("mmm")
                .define('s', Items.STONE_SLAB)
                .define('b', Items.BUCKET)
                .define('i', Items.IRON_BARS)
                .define('m', Items.SMOOTH_STONE_SLAB)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput);

        // 6. 有序配方：杯子
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLASS_CUP, 5)
                .pattern("g g")
                .pattern("g g")
                .pattern(" g ")
                .define('g', Items.GLASS)
                .unlockedBy("has_glass", has(Items.GLASS))
                .save(recipeOutput);

        // 7. 有序配方： 端路符文
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.END_ROAD_RUNE, 1)
                .pattern("#a#")
                .pattern("ete")
                .pattern("l#l")
                .define('a', Items.DRAGON_HEAD)
                .define('e', ModItems.ENDER_COOKED_CLAY_CAKE)
                .define('t', Items.TOTEM_OF_UNDYING)
                .define('l', ModItems.LEAPING_FRUIT)
                .define('#', Items.DRAGON_BREATH)
                .unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING))
                .save(recipeOutput);


        /* 烤制食品 */
        oreCampfireAndSmoker(recipeOutput, // 熟土饼
                List.of(ModItems.CLAY_CAKE.get()),
                RecipeCategory.FOOD,
                ModItems.COOKED_CLAY_CAKE.get(),
                0.35f,
                100,
                "cooked_clay_cake");

        oreCampfireAndSmoker(recipeOutput, // 熟蔬菜土饼
                List.of(ModItems.VEGETABLE_CLAY_CAKE.get()),
                RecipeCategory.FOOD,
                ModItems.VEGETABLE_COOKED_CLAY_CAKE.get(),
                0.35f,
                100,
                "vegetable_cooked_clay_cake");

        oreCampfireAndSmoker(recipeOutput, // 熟甜土饼
                List.of(ModItems.SWEET_CLAY_CAKE.get()),
                RecipeCategory.FOOD,
                ModItems.SWEET_COOKED_CLAY_CAKE.get(),
                0.35f,
                100,
                "sweet_cooked_clay_cake");

        oreCampfireAndSmoker(recipeOutput, // 熟末土饼
                List.of(ModItems.ENDER_CLAY_CAKE.get()),
                RecipeCategory.FOOD,
                ModItems.ENDER_COOKED_CLAY_CAKE.get(),
                0.35f,
                100,
                "ender_cooked_clay_cake");

        oreCampfireAndSmoker(recipeOutput, // 烤制小麦饼干
                List.of(ModItems.RAW_COOKIE.get()),
                RecipeCategory.FOOD,
                ModItems.WHEAT_COOKIE.get(),
                0.35f,
                100,
                "cooked_wheat_cookie");

        oreCampfireAndSmoker(recipeOutput, // 烤制下界灵魂小麦饼干
                List.of(ModItems.RAW_NETHER_SOUL_WHEAT_COOKIE.get()),
                RecipeCategory.FOOD,
                ModItems.NETHER_SOUL_WHEAT_COOKIE.get(),
                0.35f,
                100,
                "nether_soul_wheat_cookie");

        oreCampfireAndSmoker(recipeOutput, // 焦糖
                List.of(ModItems.SUGAR_CUBE.get()),
                RecipeCategory.MISC,
                ModItems.CARAMEL.get(),
                0.35f,
                100,
                "caramel");

        oreCampfireAndSmoker(recipeOutput, // 小麦面包
                List.of(ModItems.COOKIE_DOUGH.get()),
                RecipeCategory.MISC,
                ModItems.WHEAT_BREAD.get(),
                0.35f,
                100,
                "wheat_bread");

        oreCampfireAndSmoker(recipeOutput, // 糖-free小麦面包
                List.of(ModItems.WHEAT_DOUGH.get()),
                RecipeCategory.MISC,
                ModItems.CHINESE_SUGAR_FREE_BREAD.get(),
                0.35f,
                100,
                "chinese_sugar_free_bread");

        oreCampfireAndSmoker(recipeOutput, // 法棍
                List.of(ModItems.RAW_BAGUETTE.get()),
                RecipeCategory.MISC,
                ModItems.BAGUETTE.get(),
                0.35f,
                200,
                "baguette");

        oreCampfireAndSmoker(recipeOutput, // 烤制土豆饼
                List.of(ModItems.RAW_POTATO_PATTY.get()),
                RecipeCategory.MISC,
                ModItems.COOKED_POTATO_PATTY.get(),
                0.35f,
                100,
                "cooked_potato_patty");


        /* 锻造台配方 */
        // 食物神祝福护身符
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DIVINE_UPGRADE_TEMPLATE),
                        Ingredient.of(ModItems.SATURATION_TOTEM),                  // base（基础物品）
                        Ingredient.of(ModItems.GOD_GAZE),                     // addition（附加材料）
                        RecipeCategory.COMBAT,                                    // 分类
                        ModItems.FOOD_GOD_BLESSING_AMULET.get()                                // result（结果）
                )
                .unlocks("has_divine_upgrade_template", has(ModItems.DIVINE_UPGRADE_TEMPLATE))      // 解锁条件
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "food_god_blessing_amulet"));

        // 食物神祝福护身符
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DIVINE_UPGRADE_TEMPLATE),
                        Ingredient.of(ModItems.HUNGER_TOTEM),
                        Ingredient.of(ModItems.HUNGER_CURSE_PUNISHMENT),
                        RecipeCategory.COMBAT,
                        ModItems.HUNGER_CURSE_ODE.get()
                )
                .unlocks("has_divine_upgrade_template", has(ModItems.DIVINE_UPGRADE_TEMPLATE))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "hunger_curse_ode"));

        // 食物神祝福护身符
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DIVINE_UPGRADE_TEMPLATE),
                        Ingredient.of(ModItems.END_ROAD_RUNE),
                        Ingredient.of(ModItems.ENDING_SPEECH),
                        RecipeCategory.COMBAT,
                        ModItems.END_NEW_PATH.get()
                )
                .unlocks("has_divine_upgrade_template", has(ModItems.DIVINE_UPGRADE_TEMPLATE))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "end_new_path"));

        /*磨台*/
        // 小麦 → 小麦面粉 × 2，无液体产出，经验 4
        List<ItemStack> outputs = new ArrayList<>();
        outputs.add(new ItemStack(ModItems.WHEAT_FLOUR.get(), 2));
        millRecipe(
                recipeOutput,
                Items.WHEAT, 1,
                outputs,
                null, 0,
                4.0f,
                200,
                2,
                "wheat_flour",
                "wheat_flour"
        );

        // 猩红果 → 猩红果粉 × 1，无液体产出，经验 1
        List<ItemStack> outputs1 = new ArrayList<>();
        outputs1.add(new ItemStack(ModItems.WARPED_FRUIT_POWDER.get(), 1));
        millRecipe(
                recipeOutput,
                ModItems.WARPED_FRUIT, 1,
                outputs1,
                null, 0,
                1.0f,
                100,
                1,
                "warped_fruit_powder",
                "warped_fruit_powder"
        );

        // 诡异果实 → 猩红果粉 × 1，无液体产出，经验 1
        List<ItemStack> outputs7 = new ArrayList<>();
        outputs7.add(new ItemStack(ModItems.CRIMSON_FRUIT_POWDER.get(), 1));
        millRecipe(
                recipeOutput,
                ModItems.CRIMSON_FRUIT, 1,
                outputs7,
                null, 0,
                1.0f,
                100,
                1,
                "crimson_fruit_powder",
                "crimson_fruit_powder"
        );

        // 苹果 → 苹果渣 × 1，苹果汁115mb产出，经验 2
        List<ItemStack> outputs2 = new ArrayList<>();
        outputs2.add(new ItemStack(ModItems.APPLE_PULP.get(), 1));
        millRecipe(
                recipeOutput,
                Items.APPLE, 1,
                outputs2,
                ModFluids.APPLE_JUICE, 125,
                2.0f,
                100,
                1,
                "apple_pulp",
                "apple_pulp"
        );

        // 胡萝卜 → 胡萝卜渣 × 1，胡萝卜汁250mb产出，经验 4
        List<ItemStack> outputs3 = new ArrayList<>();
        outputs3.add(new ItemStack(ModItems.CARROT_PULP.get(), 1));
        millRecipe(
                recipeOutput,
                Items.CARROT, 1,
                outputs3,
                ModFluids.CARROT_JUICE, 250,
                4.0f,
                200,
                2,
                "carrot_pulp",
                "carrot_pulp"
        );

        // 巧克力豆 → 巧克力粉 × 1，巧克力酱50mb产出，经验 0.2
        List<ItemStack> outputs4 = new ArrayList<>();
        outputs4.add(new ItemStack(ModItems.COCOA_POWDER.get(), 1));
        millRecipe(
                recipeOutput,
                Items.COCOA_BEANS, 1,
                outputs4,
                ModFluids.COCOA_BUTTER, 50,
                0.2f,
                40,
                1,
                "cocoa_powder",
                "cocoa_powder"
        );

        // 甜菜根 → 甜菜根渣 × 1，甜菜根汁250mb产出，经验 4
        List<ItemStack> outputs5 = new ArrayList<>();
        outputs5.add(new ItemStack(ModItems.BEETROOT_PULP.get(), 1));
        millRecipe(
                recipeOutput,
                Items.BEETROOT, 1,
                outputs5,
                ModFluids.BEETROOT_JUICE, 250,
                4.0f,
                200,
                2,
                "beetroot_pulp",
                "beetroot_pulp"
        );

        // 甜浆果 → 甜浆果渣 × 1，甜浆果汁50mb产出，经验 0.2
        List<ItemStack> outputs6 = new ArrayList<>();
        outputs6.add(new ItemStack(ModItems.SWEET_BERRY_PULP.get(), 1));
        millRecipe(
                recipeOutput,
                Items.SWEET_BERRIES, 1,
                outputs6,
                ModFluids.SWEET_BERRY_JUICE, 50,
                0.2f,
                40,
                1,
                "sweet_berry_pulp",
                "sweet_berry_pulp"
        );

        // 甜浆果 → 甜浆果渣 × 1，甜浆果汁50mb产出，经验 0.2
        List<ItemStack> outputs8 = new ArrayList<>();
        outputs8.add(new ItemStack(ModItems.SUGARCANE_PULP.get(), 1));
        millRecipe(
                recipeOutput,
                Items.SUGAR_CANE, 1,
                outputs8,
                ModFluids.SUGARCANE_JUICE, 250,
                4.0f,
                200,
                2,
                "sugarcane_pulp",
                "sugarcane_pulp"
        );

        // 发光浆果 → 发光浆果渣 × 1，发光浆果汁50mb产出，经验 0.2
        List<ItemStack> outputs9 = new ArrayList<>();
        outputs9.add(new ItemStack(ModItems.GLOW_BERRY_PULP.get(), 1));
        millRecipe(
                recipeOutput,
                Items.GLOW_BERRIES, 1,
                outputs9,
                ModFluids.GLOW_BERRY_JUICE, 50,
                0.2f,
                40,
                1,
                "glow_berry_pulp",
                "glow_berry_pulp"
        );

        // 灵魂小麦 → 灵魂小麦面粉 × 4，无液体产出，经验 4
        List<ItemStack> outputs10 = new ArrayList<>();
        outputs10.add(new ItemStack(ModItems.NETHER_SOUL_WHEAT_FLOUR.get(), 4));
        millRecipe(
                recipeOutput,
                ModItems.SOUL_WHEAT, 1,
                outputs10,
                null, 0,
                4.0f,
                200,
                2,
                "nether_soul_wheat_flour",
                "nether_soul_wheat_flour"
        );

        // 水果西瓜 → 水果西瓜渣 × 1，水果西瓜汁115mb产出，经验 2
        List<ItemStack> outputs11 = new ArrayList<>();
        outputs11.add(new ItemStack(ModItems.WATERMELON_PULP.get(), 1));
        millRecipe(
                recipeOutput,
                Items.MELON_SLICE, 1,
                outputs11,
                ModFluids.WATERMELON_JUICE, 125,
                2.0f,
                100,
                1,
                "watermelon_pulp",
                "watermelon_pulp"
        );

        // 土豆 → 土豆泥 × 1，无液体产出，经验 4
        List<ItemStack> outputs12 = new ArrayList<>();
        outputs12.add(new ItemStack(ModItems.MASHED_POTATO.get(), 1));
        millRecipe(
                recipeOutput,
                Items.POTATO, 1,
                outputs12,
                null, 0,
                4.0f,
                100,
                1,
                "mashed_potato",
                "mashed_potato"
        );

        // 粉碎的盐 → 盐 × 1，无液体产出，经验 4
        List<ItemStack> outputs13 = new ArrayList<>();
        outputs13.add(new ItemStack(ModItems.SALT.get(), 2));
        millRecipe(
                recipeOutput,
                ModItems.CRUSHED_SALT, 1,
                outputs13,
                null, 0,
                4.0f,
                100,
                1,
                "salt",
                "salt"
        );

        // 浮动的西瓜 → 浮动的西瓜果酱 × 2，破碎的浮动南瓜 × 1，无液体产出，经验 1
        List<ItemStack> outputs14 = new ArrayList<>();
        outputs14.add(new ItemStack(ModItems.FLOATING_MELON_JAM.get(), 2));
        outputs14.add(new ItemStack(ModItems.BROKEN_FLOATING_GOURD.get(), 1));
        millRecipe(
                recipeOutput,
                ModItems.FLOATING_MELON, 1,
                outputs14,
                null, 0,
                1.0f,
                100,
                1,
                "float_melon",
                "float_melon"
        );

        // 糖块 → 糖 × 4，无液体产出，经验 0
        List<ItemStack> outputs15 = new ArrayList<>();
        outputs15.add(new ItemStack(Items.SUGAR, 4));
        millRecipe(
                recipeOutput,
                ModItems.SUGAR_CUBE, 1,
                outputs15,
                null, 0,
                0.0f,
                10,
                1,
                "sugar_cube",
                "sugar_cube"
        );

        // 糖块 → 糖 × 4，无液体产出，经验 0
        List<ItemStack> outputs16 = new ArrayList<>();
        outputs16.add(new ItemStack(Items.SUGAR, 8));
        millRecipe(
                recipeOutput,
                ModBlocks.SUGAR_BLOCK, 1,
                outputs16,
                null, 0,
                0.0f,
                20,
                2,
                "sugar_block",
                "sugar_block"
        );

        /* 注册液体盛取配方 */
        buildLiquidFillingRecipes(recipeOutput);

    }

    protected static void oreSmelting(
            RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group
    ) {
        oreCooking(
                recipeOutput,
                RecipeSerializer.SMELTING_RECIPE,
                SmeltingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime,
                group,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(
            RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group
    ) {
        oreCooking(
                recipeOutput,
                RecipeSerializer.BLASTING_RECIPE,
                BlastingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime,
                group,
                "_from_blasting"
        );
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput recipeOutput,
            RecipeSerializer<T> serializer,
            AbstractCookingRecipe.Factory<T> recipeFactory,
            List<ItemLike> ingredients,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int cookingTime,
            String group,
            String suffix
    ) {
        for (ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, serializer, recipeFactory)
                    .group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput,  MiraculousOriginFoodMod.MODID + ":" + getItemName(result) + suffix + "_" + getItemName(itemlike));
        }
    }

    protected static void oreCampfireAndSmoker(
            RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result,
            float experience, int cookingTime, String group) {
        // 注册烟熏炉配方（序列化器为 SMOKING_RECIPE，后缀 _from_smoking）
        oreCooking(recipeOutput,
                RecipeSerializer.SMOKING_RECIPE,
                SmokingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime,
                group,
                "_from_smoking");
        // 注册营火配方（序列化器为 CAMPFIRE_COOKING_RECIPE，后缀 _from_campfire）
        oreCooking(recipeOutput,
                RecipeSerializer.CAMPFIRE_COOKING_RECIPE,
                CampfireCookingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime * 3,
                group,
                "_from_campfire");
    }

    /**
     * 注册磨台配方（完整版，支持液体输出）
     *
     * @param recipeOutput    配方输出对象
     * @param ingredient      原料物品
     * @param ingredientCount 消耗原料数量
     * @param outputs         产物列表（ItemStack 列表，最多两种）
     * @param liquidOutput    产出液体标识符（如 "minecraft:water"），无液体传 null 或 ""
     * @param liquidAmount    产出液体量（单位 mB），无液体传 0
     * @param experience      产出经验值
     * @param grindingTime    磨制时间（tick）
     * @param foodCost        消耗饥饿值（先扣饱和）
     * @param group           配方分组（可留空）
     * @param recipeName      配方文件名（不含扩展名）
     */
    protected static void millRecipe(
            RecipeOutput recipeOutput,
            ItemLike ingredient,
            int ingredientCount,
            List<ItemStack> outputs,
            String liquidOutput,
            int liquidAmount,
            float experience,
            int grindingTime,
            int foodCost,
            String group,
            String recipeName
    ) {
        MillRecipe millRecipe = new MillRecipe(
                Ingredient.of(ingredient),
                ingredientCount,
                outputs,
                liquidOutput == null ? "" : liquidOutput,
                liquidAmount,
                experience,
                grindingTime,
                foodCost,
                group
        );
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                MiraculousOriginFoodMod.MODID,
                "mill/" + recipeName
        );
        recipeOutput.accept(id, millRecipe, null);
    }

    // ==================== 液体填充配方生成 ====================
    private void buildLiquidFillingRecipes(RecipeOutput recipeOutput) {
        // 液体定义：
        // 参数：液体名称，是否桶，是否瓶，是否杯，是否模具，桶容量，瓶容量，杯容量，模具容量
        // 容量值只在对应布尔为 true 时有效
        addLiquid(recipeOutput, "water", true, true, true, false, 1000, 250, 500, 0);
        addLiquid(recipeOutput, "apple_juice", true, true, true, false, 1000, 250, 500, 0);
        addLiquid(recipeOutput, "watermelon_juice", true, true, true, false, 1000, 250, 500, 0);
        addLiquid(recipeOutput, "carrot_juice", true, true, true, false, 1000, 250, 500, 0);
        addLiquid(recipeOutput, "sugarcane_juice", true, true, true, false, 1000, 250, 500, 0);
        addLiquid(recipeOutput, "beetroot_juice", true, true, true, false, 1000, 250, 500, 0);
        addLiquid(recipeOutput, "sweet_berry_juice", true, true, true, false, 1000, 250, 500, 0);
        addLiquid(recipeOutput, "glow_berry_juice", true, true, true, false, 1000, 250, 500, 0);
        // 可可脂：只有模具
        addLiquid(recipeOutput, "cocoa_butter", false, false, false, true, 0, 0, 0, 250);
    }

    /**
     * 为一种液体注册四种容器的配方（通过布尔值控制）
     * @param recipeOutput 输出对象
     * @param fluidName 液体名称（用于构建 fluid ID 和文件名）
     * @param registerBucket 是否注册桶装
     * @param registerBottle 是否注册瓶装
     * @param registerCup 是否注册杯装
     * @param registerMold 是否注册模具
     * @param bucketAmount 桶消耗量（mB）
     * @param bottleAmount 瓶消耗量（mB）
     * @param cupAmount 杯消耗量（mB）
     * @param moldAmount 模具消耗量（mB）
     */
    private void addLiquid(RecipeOutput recipeOutput, String fluidName,
                           boolean registerBucket, boolean registerBottle, boolean registerCup, boolean registerMold,
                           int bucketAmount, int bottleAmount, int cupAmount, int moldAmount) {
        // 构建流体 ID
        String fluid = fluidName.equals("water") ? "minecraft:water" : "miraculousori:" + fluidName;

        List<ContainerMapping> mappings = new ArrayList<>();
        if (registerBucket && bucketAmount > 0) {
            mappings.add(new ContainerMapping(
                    Ingredient.of(Items.BUCKET),
                    getResultItem(fluidName, "bucket"),
                    bucketAmount,
                    "bucket"
            ));
        }
        if (registerBottle && bottleAmount > 0) {
            mappings.add(new ContainerMapping(
                    Ingredient.of(Items.GLASS_BOTTLE),
                    getResultItem(fluidName, "bottle"),
                    bottleAmount,
                    "bottle"
            ));
        }
        if (registerCup && cupAmount > 0) {
            mappings.add(new ContainerMapping(
                    Ingredient.of(ModItems.GLASS_CUP.get()),
                    getResultItem(fluidName, "glass"),
                    cupAmount,
                    "glass"
            ));
        }
        if (registerMold && moldAmount > 0) {
            mappings.add(new ContainerMapping(
                    Ingredient.of(ModItems.COOKIE_MOLD.get()),
                    getResultItem(fluidName, "mold"),
                    moldAmount,
                    "mold"
            ));
        }

        // 生成每个映射的配方
        for (ContainerMapping mapping : mappings) {
            saveLiquidFilling(recipeOutput,
                    mapping.container,
                    fluid,
                    mapping.amount,
                    mapping.result,
                    fluidName + "_" + mapping.suffix
            );
        }
    }

    /**
     * 根据液体名和容器类型获取对应的产物物品
     * 注意：这里需要根据你的实际物品注册名进行映射
     */
    private ItemStack getResultItem(String fluidName, String containerType) {
        String itemKey = fluidName + "_" + containerType;
        return switch (itemKey) {
            // 水
            case "water_bucket" -> new ItemStack(Items.WATER_BUCKET);
            case "water_bottle" -> new ItemStack(ModItems.WATER_GLASS.get()); // 注意：你的 WATER_GLASS 是瓶装水
            case "water_glass" -> new ItemStack(ModItems.WATER_GLASS.get());  // 如果没有单独杯装水，可复用
            // 苹果汁
            case "apple_juice_bucket" -> new ItemStack(ModItems.APPLE_JUICE_BUCKET.get());
            case "apple_juice_bottle" -> new ItemStack(ModItems.APPLE_JUICE_BOTTLE.get());
            case "apple_juice_glass" -> new ItemStack(ModItems.APPLE_JUICE_GLASS.get());
            // 西瓜汁
            case "watermelon_juice_bucket" -> new ItemStack(ModItems.WATERMELON_JUICE_BUCKET.get());
            case "watermelon_juice_bottle" -> new ItemStack(ModItems.WATERMELON_JUICE_BOTTLE.get());
            case "watermelon_juice_glass" -> new ItemStack(ModItems.WATERMELON_JUICE_GLASS.get());
            // 胡萝卜汁
            case "carrot_juice_bucket" -> new ItemStack(ModItems.CARROT_JUICE_BUCKET.get());
            case "carrot_juice_bottle" -> new ItemStack(ModItems.CARROT_JUICE_BOTTLE.get());
            case "carrot_juice_glass" -> new ItemStack(ModItems.CARROT_JUICE_GLASS.get());
            // 甘蔗汁
            case "sugarcane_juice_bucket" -> new ItemStack(ModItems.SUGARCANE_JUICE_BUCKET.get());
            case "sugarcane_juice_bottle" -> new ItemStack(ModItems.SUGARCANE_JUICE_BOTTLE.get());
            case "sugarcane_juice_glass" -> new ItemStack(ModItems.SUGARCANE_JUICE_GLASS.get());
            // 甜菜根汁
            case "beetroot_juice_bucket" -> new ItemStack(ModItems.BEETROOT_JUICE_BUCKET.get());
            case "beetroot_juice_bottle" -> new ItemStack(ModItems.BEETROOT_JUICE_BOTTLE.get());
            case "beetroot_juice_glass" -> new ItemStack(ModItems.BEETROOT_JUICE_GLASS.get());
            // 甜浆果汁
            case "sweet_berry_juice_bucket" -> new ItemStack(ModItems.SWEET_BERRY_JUICE_BUCKET.get());
            case "sweet_berry_juice_bottle" -> new ItemStack(ModItems.SWEET_BERRY_JUICE_BOTTLE.get());
            case "sweet_berry_juice_glass" -> new ItemStack(ModItems.SWEET_BERRY_JUICE_GLASS.get());
            // 发光浆果汁
            case "glow_berry_juice_bucket" -> new ItemStack(ModItems.GLOW_BERRY_JUICE_BUCKET.get());
            case "glow_berry_juice_bottle" -> new ItemStack(ModItems.GLOW_BERRY_JUICE_BOTTLE.get());
            case "glow_berry_juice_glass" -> new ItemStack(ModItems.GLOW_BERRY_JUICE_GLASS.get());
            // 可可脂模具
            case "cocoa_butter_mold" -> new ItemStack(ModItems.COCOA_BUTTER_MOLD.get());
            default -> ItemStack.EMPTY;
        };
    }

    private void saveLiquidFilling(RecipeOutput output, Ingredient container, String fluid, int amount, ItemStack result, String name) {
        LiquidFillingRecipe recipe = new LiquidFillingRecipe(container, fluid, amount, result);
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "liquid_filling/" + name);
        output.accept(id, recipe, null);
    }

    // 内部辅助类：容器映射
    private record ContainerMapping(Ingredient container, ItemStack result, int amount, String suffix) {}

    /**
     * 注册药水酿造配方（酿造台配方）
     * @param recipeOutput  配方输出对象
     * @param inputPotion   输入药水（从酿造台获得）
     * @param ingredient    酿造材料（物品）
     * @param outputPotion  输出药水
     * @param recipeName    配方文件名
     */
    protected static void brewingRecipe(
            RecipeOutput recipeOutput,
            Potion inputPotion,
            ItemLike ingredient,
            Potion outputPotion,
            String recipeName
    ){}
}
