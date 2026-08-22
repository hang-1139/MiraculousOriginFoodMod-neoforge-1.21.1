package com.hang.miraculousori.compat.jei;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.recipe.LiquidFillingRecipe;
import com.hang.miraculousori.recipe.MillRecipe;
import com.hang.miraculousori.recipe.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static final ResourceLocation PLUGIN_UID =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "jei_plugin");

    public static final RecipeType<MillRecipe> MILL_RECIPE_TYPE =
            RecipeType.create(MiraculousOriginFoodMod.MODID, "mill", MillRecipe.class);

    public static final RecipeType<LiquidFillingRecipe> LIQUID_FILLING_RECIPE_TYPE =
            RecipeType.create(MiraculousOriginFoodMod.MODID, "liquid_filling", LiquidFillingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new MillRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new LiquidFillingRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        // 磨台配方
        List<RecipeHolder<MillRecipe>> millHolders = recipeManager.getAllRecipesFor(ModRecipeTypes.MILL.get());
        List<MillRecipe> millRecipes = millHolders.stream().map(RecipeHolder::value).toList();
        registration.addRecipes(MILL_RECIPE_TYPE, millRecipes);

        // 液体填充配方
        List<RecipeHolder<LiquidFillingRecipe>> fillHolders = recipeManager.getAllRecipesFor(ModRecipeTypes.LIQUID_FILLING.get());
        List<LiquidFillingRecipe> fillRecipes = fillHolders.stream().map(RecipeHolder::value).toList();
        registration.addRecipes(LIQUID_FILLING_RECIPE_TYPE, fillRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // 两种配方共享同一个磨台方块
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.MILL_BLOCK.get()), MILL_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.MILL_BLOCK.get()), LIQUID_FILLING_RECIPE_TYPE);
    }
}