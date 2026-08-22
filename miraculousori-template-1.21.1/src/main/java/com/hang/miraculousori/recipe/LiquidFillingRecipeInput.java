package com.hang.miraculousori.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record LiquidFillingRecipeInput(ItemStack container, String fluid) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? container : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 1;
    }
}