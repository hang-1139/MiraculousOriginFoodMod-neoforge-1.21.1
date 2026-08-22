package com.hang.miraculousori.screen;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.fluid.ModFluids;
import com.hang.miraculousori.menu.MillMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Map;

public class MillScreen extends AbstractContainerScreen<MillMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "textures/gui/mill.png");

    // 水槽位置
    private static final int TANK1_X = 40;
    private static final int TANK1_Y = 10;
    private static final int TANK2_X = 116;
    private static final int TANK2_Y = 10;
    private static final int TANK_WIDTH = 8;
    private static final int TANK_HEIGHT = 58;

    // 箭头
    private static final int ARROW_X = 75;
    private static final int ARROW_Y = 28;
    private static final int ARROW_WIDTH = 14;
    private static final int ARROW_HEIGHT = 18;
    private static final int PROGRESS_U = 176;
    private static final int PROGRESS_V = 66;
    private static final int CANT_CRAFT_U = 200;
    private static final int CANT_CRAFT_V = 66;

    // 液体纹理映射
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

    private static final Map<String, String> LIQUID_DISPLAY_NAMES = Map.of(
            ModFluids.WATER,            "水",
            ModFluids.COCOA_BUTTER,     "可可脂",
            ModFluids.APPLE_JUICE,      "苹果汁",
            ModFluids.WATERMELON_JUICE, "西瓜汁",
            ModFluids.SUGARCANE_JUICE,  "甘蔗汁",
            ModFluids.CARROT_JUICE,     "胡萝卜汁",
            ModFluids.SWEET_BERRY_JUICE,"甜浆果汁",
            ModFluids.GLOW_BERRY_JUICE, "发光浆果汁",
            ModFluids.BEETROOT_JUICE,   "甜菜根汁"
    );

    public MillScreen(MillMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 188;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // 渲染水槽液体
        renderTank(graphics, TANK1_X, TANK1_Y, menu.getTank1Amount(), menu.getTank1Capacity(),
                menu.getTank1FluidType());
        renderTank(graphics, TANK2_X, TANK2_Y, menu.getTank2Amount(), menu.getTank2Capacity(),
                menu.getTank2FluidType());

        // 渲染箭头
        renderArrowContent(graphics);
    }

    private void renderTank(GuiGraphics graphics, int x, int y, int amount, int capacity, String fluidType) {
        if (amount <= 0 || fluidType == null || fluidType.isEmpty()) return;
        int height = (int) ((float) amount / capacity * TANK_HEIGHT);
        if (height <= 0) return;
        int[] uv = LIQUID_TEXTURES.get(fluidType);
        if (uv == null) return;
        graphics.blit(TEXTURE,
                this.leftPos + x,
                this.topPos + y + TANK_HEIGHT - height,
                uv[0],
                uv[1] + LIQUID_TEXTURE_HEIGHT - height,
                LIQUID_TEXTURE_WIDTH,
                height);
    }

    private void renderArrowContent(GuiGraphics graphics) {
        int x = this.leftPos + ARROW_X;
        int y = this.topPos + ARROW_Y;
        if (menu.isActive() && menu.getProgressRatio() > 0) {
            int progressHeight = (int) (menu.getProgressRatio() * ARROW_HEIGHT);
            if (progressHeight > 0) {
                graphics.blit(TEXTURE, x, y, PROGRESS_U, PROGRESS_V, ARROW_WIDTH, progressHeight);
            }
        } else {
            if (!menu.hasRecipe() || !menu.canProcess()) {
                graphics.blit(TEXTURE, x, y, CANT_CRAFT_U, CANT_CRAFT_V, ARROW_WIDTH, ARROW_HEIGHT);
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
        renderTankTooltips(graphics, mouseX, mouseY);
    }

    private void renderTankTooltips(GuiGraphics graphics, int mouseX, int mouseY) {
        if (isMouseOverTank(mouseX, mouseY, TANK1_X, TANK1_Y)) {
            int amount = menu.getTank1Amount();
            int cap = menu.getTank1Capacity();
            String type = menu.getTank1FluidType();
            String name = getLiquidDisplayName(type);
            graphics.renderTooltip(this.font,
                    Component.literal(String.format("%s %d / %d mB", name, amount, cap)),
                    mouseX, mouseY);
            return;
        }
        if (isMouseOverTank(mouseX, mouseY, TANK2_X, TANK2_Y)) {
            int amount = menu.getTank2Amount();
            int cap = menu.getTank2Capacity();
            String type = menu.getTank2FluidType();
            String name = getLiquidDisplayName(type);
            graphics.renderTooltip(this.font,
                    Component.literal(String.format("%s %d / %d mB", name, amount, cap)),
                    mouseX, mouseY);
        }
    }

    private boolean isMouseOverTank(int mouseX, int mouseY, int tankX, int tankY) {
        int x = this.leftPos + tankX;
        int y = this.topPos + tankY;
        return mouseX >= x && mouseX < x + TANK_WIDTH &&
                mouseY >= y && mouseY < y + TANK_HEIGHT;
    }

    private String getLiquidDisplayName(String fluidType) {
        if (fluidType == null || fluidType.isEmpty()) return "空";
        return LIQUID_DISPLAY_NAMES.getOrDefault(fluidType, fluidType);
    }

    // 移除鼠标点击水槽的处理（不再需要）
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
        graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040, false);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2 - 25;
        this.inventoryLabelY = this.imageHeight - 116;
    }
}