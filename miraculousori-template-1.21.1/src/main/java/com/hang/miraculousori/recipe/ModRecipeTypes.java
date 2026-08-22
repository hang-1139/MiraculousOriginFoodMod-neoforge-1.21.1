package com.hang.miraculousori.recipe;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, MiraculousOriginFoodMod.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<MillRecipe>> MILL =
            RECIPE_TYPES.register("mill", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "mill";
                }
            });

    // 新增液体填充配方类型
    public static final DeferredHolder<RecipeType<?>, RecipeType<LiquidFillingRecipe>> LIQUID_FILLING =
            RECIPE_TYPES.register("liquid_filling", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "liquid_filling";
                }
            });
}