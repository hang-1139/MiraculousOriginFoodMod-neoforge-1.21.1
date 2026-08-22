package com.hang.miraculousori.recipe;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.component.ModDataComponents;
import com.hang.miraculousori.component.PleasureComponent;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.fml.common.Mod;

public class VerySweetBreadRecipe implements CraftingRecipe {
    private final CraftingBookCategory category;

    // 构造函数
    public VerySweetBreadRecipe(CraftingBookCategory category) {
        this.category = category;
    }

    // ========== 必须实现的方法 ==========

    /**
     * 检查输入是否匹配配方。
     * @param input 合成格输入
     * @param level 世界
     * @return true 如果匹配
     */
    @Override
    public boolean matches(CraftingInput input, Level level) {
        int sugarSlots = 0, sugarCubeSlots = 0, honeySlots = 0;
        boolean hasBread = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.is(Items.SUGAR)) sugarSlots++;
            else if (stack.is(ModItems.CARAMEL.get())) sugarCubeSlots++;
            else if (stack.is(Items.HONEY_BOTTLE)) honeySlots++;
            else if (stack.is(ModItems.WHEAT_BREAD)) hasBread = true;
            else return false; // 其他物品不允许
        }
        int totalSlots = sugarSlots + sugarCubeSlots + honeySlots;
        return hasBread && totalSlots <= 7 && totalSlots >= 1 && honeySlots >= 1 && honeySlots <= 3;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        int sugarSlots = 0, sugarCubeSlots = 0, honeySlots = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.is(Items.SUGAR)) sugarSlots++;
            else if (stack.is(ModItems.CARAMEL.get())) sugarCubeSlots++;
            else if (stack.is(Items.HONEY_BOTTLE)) honeySlots++;
        }
        int baseDuration = 20 * 30; // 30秒
        int duration = baseDuration + 20 * 10 * sugarSlots + 20 * 50 * sugarCubeSlots;
        int amplifier = honeySlots - 1; // 0 ~ 2

        ItemStack result = new ItemStack(ModItems.VERY_SWEET_BREAD.get());
        result.set(ModDataComponents.PLEASURE_DATA.get(), new PleasureComponent(duration, amplifier));
        return result;
    }

    /**
     * 判断配方是否能在指定尺寸的合成格中合成（用于工作台匹配）。
     */
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2; // 至少2格
    }

    /**
     * 获取配方的结果物品（用于显示或预览，不包含动态组件）。
     * 这里返回一个基础物品，实际合成时使用 assemble 动态生成。
     */
    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return new ItemStack(ModItems.VERY_SWEET_BREAD.get());
    }

    /**
     * 返回配方序列化器（必须）。
     */
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.VERY_SWEET_BREAD_SERIALIZER.get();
    }

    /**
     * 返回配方的分类（用于配方书）。
     */
    @Override
    public CraftingBookCategory category() {
        return this.category;
    }

    // ========== 可选重写（推荐） ==========

    /**
     * 处理蜂蜜瓶返还玻璃瓶。
     */
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.is(Items.HONEY_BOTTLE)) {
                remaining.set(i, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return remaining;
    }

    /**
     * 是否特殊配方（如无法在工作台直接合成），一般返回 false。
     */
    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.of(Items.BREAD));
        ingredients.add(Ingredient.of(Items.SUGAR, ModItems.CARAMEL.get(), Items.HONEY_BOTTLE));
        return ingredients;
    }
}