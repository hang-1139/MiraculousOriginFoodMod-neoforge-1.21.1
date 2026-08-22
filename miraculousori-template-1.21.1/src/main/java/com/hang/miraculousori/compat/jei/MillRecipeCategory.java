package com.hang.miraculousori.compat.jei;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.fluid.ModFluids;
import com.hang.miraculousori.recipe.MillRecipe;
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

public class MillRecipeCategory implements IRecipeCategory<MillRecipe> {

    // ===== 背景贴图（使用 jei_liquid_filling.png） =====
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "textures/gui/jei_mill.png");

    // ===== 自定义鸡腿贴图 =====
    private static final ResourceLocation FOOD_EMPTY =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "textures/gui/hud/food_empty.png");
    private static final ResourceLocation FOOD_HALF =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "textures/gui/hud/food_half.png");
    private static final ResourceLocation FOOD_FULL =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "textures/gui/hud/food_full.png");

    // ===== 背景尺寸 =====
    private final int width = 180;
    private final int height = 90;

    // ===== 水槽 =====
    private static final int TANK_X = 154;
    private static final int TANK_Y = 7;
    private static final int TANK_WIDTH = 8;
    private static final int TANK_HEIGHT = 58;

    // ===== 液体纹理（从 mill.png 截取） =====
    private static final ResourceLocation MILL_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "textures/gui/mill.png");

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
    private static final int LIQUID_TEXTURE_WIDTH = 8;
    private static final int LIQUID_TEXTURE_HEIGHT = 58;

    // ===== 图标 =====
    private final IDrawable icon;

    public MillRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.MILL_BLOCK.get()));
    }

    @Override
    public RecipeType<MillRecipe> getRecipeType() {
        return JEIPlugin.MILL_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category.miraculousori.mill");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MillRecipe recipe, IFocusGroup focuses) {
        // 原料槽
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 30)
                .addIngredients(recipe.getIngredient());

        // 产物槽（最多两个）
        var outputs = recipe.getOutputs();
        if (!outputs.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 20)
                    .addItemStack(outputs.get(0));
        }
        if (outputs.size() > 1) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 50)
                    .addItemStack(outputs.get(1));
        }
    }

    @Override
    public void draw(MillRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, width, height);

        // 2. 水槽液体
        String liquid = recipe.getLiquidOutput();
        int amount = recipe.getLiquidAmount();
        if (!liquid.isEmpty() && amount > 0) {
            renderTank(guiGraphics, TANK_X, TANK_Y, amount, 1000, liquid);

            // 液体名称和数量
            String fluidKey = liquid.replace(':', '.');
            Component fluidName = Component.translatable("fluid." + fluidKey);
            String text = fluidName.getString() + " " + amount + " mB";
            guiGraphics.drawString(Minecraft.getInstance().font, text, TANK_X - 10, TANK_Y - 10, 0x404040, false);
        }

        // 3. 饱食度消耗（使用自定义贴图）
        int foodCost = recipe.getFoodCost();
        if (foodCost > 0) {
            drawFoodCost(guiGraphics, foodCost);
        }
    }

    // ===== 水槽渲染 =====
    private void renderTank(GuiGraphics graphics, int x, int y, int amount, int capacity, String fluidType) {
        if (amount <= 0 || fluidType == null || fluidType.isEmpty()) return;
        int height = (int) ((float) amount / capacity * TANK_HEIGHT);
        if (height <= 0) return;

        int[] uv = LIQUID_TEXTURES.get(fluidType);
        if (uv == null) return;

        graphics.blit(MILL_TEXTURE,
                x,
                y + TANK_HEIGHT - height,
                uv[0],
                uv[1] + LIQUID_TEXTURE_HEIGHT - height,
                LIQUID_TEXTURE_WIDTH,
                height);
    }

    // ===== 绘制饱食度（使用自定义纹理） =====
    private void drawFoodCost(GuiGraphics guiGraphics, int foodCost) {
        int full = foodCost / 2;
        int half = foodCost % 2;

        int iconSize = 9;
        int spacing = 1;
        int totalIcons = full + half;

        int startX = 74;
        int y = 24;

        // 先绘制空鸡腿（底）
        for (int i = 0; i < totalIcons; i++) {
            guiGraphics.blit(FOOD_EMPTY,
                    startX + i * (iconSize + spacing), y,
                    0, 0,
                    iconSize, iconSize,
                    iconSize, iconSize);
        }
        // 绘制满鸡腿
        for (int i = 0; i < full; i++) {
            guiGraphics.blit(FOOD_FULL,
                    startX + i * (iconSize + spacing), y,
                    0, 0,
                    iconSize, iconSize,
                    iconSize, iconSize);
        }
        // 绘制半鸡腿（如果有）
        if (half > 0) {
            guiGraphics.blit(FOOD_HALF,
                    startX + full * (iconSize + spacing), y,
                    0, 0,
                    iconSize, iconSize,
                    iconSize, iconSize);
        }
    }
}