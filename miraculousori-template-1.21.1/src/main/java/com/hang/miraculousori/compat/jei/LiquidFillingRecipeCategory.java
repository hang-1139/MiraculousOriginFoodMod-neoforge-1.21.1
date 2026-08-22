package com.hang.miraculousori.compat.jei;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.fluid.ModFluids;
import com.hang.miraculousori.recipe.LiquidFillingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class LiquidFillingRecipeCategory implements IRecipeCategory<LiquidFillingRecipe> {

    // ===== 背景贴图=====
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "textures/gui/jei_liquid_filling.png");

    // ===== 液体纹理来源=====
    private static final ResourceLocation MILL_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "textures/gui/mill.png");

    // ===== 液体纹理映射=====
    private static final Map<String, int[]> LIQUID_TEXTURES = Map.of(
            ModFluids.WATER,            new int[]{192, 4},
            ModFluids.COCOA_BUTTER,     new int[]{203, 4},
            ModFluids.APPLE_JUICE,      new int[]{236, 4},
            ModFluids.WATERMELON_JUICE, new int[]{247, 4},
            ModFluids.SUGARCANE_JUICE,  new int[]{236, 66},
            ModFluids.CARROT_JUICE,     new int[]{214, 4},
            ModFluids.SWEET_BERRY_JUICE,new int[]{247, 66},
            ModFluids.GLOW_BERRY_JUICE, new int[]{225, 66},
            ModFluids.BEETROOT_JUICE,   new int[]{247, 128}
    );
    private static final int LIQUID_TEXTURE_WIDTH = 7;
    private static final int LIQUID_TEXTURE_HEIGHT = 58;

    // ===== 界面尺寸 =====
    private final int width = 100;
    private final int height = 80;

    // 水槽位置
    private static final int TANK_X = 85;
    private static final int TANK_Y = 9;
    private static final int TANK_HEIGHT = 57;

    private final IDrawable icon;

    public LiquidFillingRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.MILL_BLOCK.get()));
    }

    @Override
    public RecipeType<LiquidFillingRecipe> getRecipeType() {
        return JEIPlugin.LIQUID_FILLING_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category.miraculousori.liquid_filling");
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, LiquidFillingRecipe recipe, IFocusGroup focuses) {
        // 原料槽（容器）
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 6)
                .addIngredients(recipe.getContainer());

        // 产物槽（盛满的容器）
        builder.addSlot(RecipeIngredientRole.OUTPUT, 62, 53)
                .addItemStack(recipe.getResult());
    }

    @Override
    public void draw(LiquidFillingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // 1. 绘制背景
        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, width, height, width, height);

        // 2. 绘制水槽中的液体柱（从 mill.png 截取）
        String fluidType = recipe.getFluid();
        int amount = recipe.getFluidAmount();
        if (!fluidType.isEmpty() && amount > 0) {
            renderTank(guiGraphics, TANK_X, TANK_Y, amount, 1000, fluidType);
        }

        // 3. 显示液体名称和数量
        String fluidKey = fluidType.replace(':', '.');
        Component fluidName = Component.translatable("fluid." + fluidKey);
        String text = fluidName.getString() + " " + amount + " mB";
        guiGraphics.drawString(Minecraft.getInstance().font, text, 3, 69, 0x404040, false);
    }

    // ===== 水槽渲染（使用 MILL_TEXTURE） =====
    private void renderTank(GuiGraphics graphics, int x, int y, int amount, int capacity, String fluidType) {
        if (amount <= 0 || fluidType == null || fluidType.isEmpty()) return;
        int height = (int) ((float) amount / capacity * TANK_HEIGHT);
        if (height <= 0) return;

        int[] uv = LIQUID_TEXTURES.get(fluidType);
        if (uv == null) return;

        // 关键修改：从 MILL_TEXTURE 截取，而不是从 TEXTURE
        graphics.blit(MILL_TEXTURE,
                x,
                y + TANK_HEIGHT - height,
                uv[0],
                uv[1] + LIQUID_TEXTURE_HEIGHT - height,
                LIQUID_TEXTURE_WIDTH,
                height);
    }
}