package com.hang.miraculousori.menu;

import com.hang.miraculousori.entity.blockentity.MillBlockEntity;
import com.hang.miraculousori.fluid.ModFluids;
import com.hang.miraculousori.recipe.MillRecipe;
import com.hang.miraculousori.recipe.ModRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class MillMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;
    private final Level level;
    private final MillBlockEntity blockEntity;

    public String getTank1FluidType() {
        int id = data.get(6);
        return ModFluids.getFluidType(id);
    }
    public String getTank2FluidType() {
        int id = data.get(7);
        return ModFluids.getFluidType(id);
    }

    public MillMenu(int id, Inventory inv, ContainerData data) {
        this(id, inv, new SimpleContainer(MillBlockEntity.SLOT_COUNT), data);
    }

    public MillMenu(int id, Inventory inv, Container container, ContainerData data) {
        super(ModMenuTypes.MILL_MENU.get(), id);
        this.container = container;
        this.data = data;
        this.level = inv.player.level();
        this.blockEntity = container instanceof MillBlockEntity be ? be : null;

        if (blockEntity != null && !level.isClientSide) {
            blockEntity.incrementOpeners();
            blockEntity.setGrindingPlayer(inv.player);
        }

        // ---- 原有槽位 ----
        this.addSlot(new Slot(container, MillBlockEntity.SLOT_INPUT, 74, 7) {
            @Override
            public boolean mayPlace(ItemStack stack) { return true; }
        });
        // 弃用槽
        this.addSlot(new Slot(container, MillBlockEntity.SLOT_FLUID_CONTAINER, -1000, -1000) {
            @Override
            public boolean mayPlace(ItemStack stack) { return false; }
        });
        this.addSlot(new Slot(container, MillBlockEntity.SLOT_OUTPUT_1, 56, 52) {
            @Override
            public boolean mayPlace(ItemStack stack) { return false; }
        });
        this.addSlot(new Slot(container, MillBlockEntity.SLOT_OUTPUT_2, 93, 52) {
            @Override
            public boolean mayPlace(ItemStack stack) { return false; }
        });

        // ---- 新增液体交互槽 ----
        // 水槽1：输入 (左边) , 输出 (右边)
        this.addSlot(new Slot(container, MillBlockEntity.SLOT_TANK1_INPUT, 18, 11) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return blockEntity != null && blockEntity.isValidContainer(stack);
            }
        });
        this.addSlot(new Slot(container, MillBlockEntity.SLOT_TANK1_OUTPUT, 18, 52) {
            @Override
            public boolean mayPlace(ItemStack stack) { return false; }
            @Override
            public boolean mayPickup(Player player) { return true; }
        });

        // 水槽2：输入 (右边) , 输出 (更右边)
        this.addSlot(new Slot(container, MillBlockEntity.SLOT_TANK2_INPUT, 130, 11) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return blockEntity != null && blockEntity.isValidContainer(stack);
            }
        });
        this.addSlot(new Slot(container, MillBlockEntity.SLOT_TANK2_OUTPUT, 130, 52) {
            @Override
            public boolean mayPlace(ItemStack stack) { return false; }
            @Override
            public boolean mayPickup(Player player) { return true; }
        });

        // ---- 玩家背包 ----
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }

        this.addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) { return container.stillValid(player); }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < MillBlockEntity.SLOT_COUNT) {
                if (!this.moveItemStackTo(itemstack1, MillBlockEntity.SLOT_COUNT, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // 尝试放入原料槽或液体输入槽
                if (!this.moveItemStackTo(itemstack1, MillBlockEntity.SLOT_INPUT, MillBlockEntity.SLOT_INPUT + 1, false) &&
                        !this.moveItemStackTo(itemstack1, MillBlockEntity.SLOT_TANK1_INPUT, MillBlockEntity.SLOT_TANK1_INPUT + 1, false) &&
                        !this.moveItemStackTo(itemstack1, MillBlockEntity.SLOT_TANK2_INPUT, MillBlockEntity.SLOT_TANK2_INPUT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (itemstack1.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }

    // 移除点击水槽的按钮处理（不再使用）
    @Override
    public boolean clickMenuButton(Player player, int id) {
        return false;
    }

    // ========== 数据访问 ==========
    public ContainerData getData() { return data; }
    public int getProgress() { return data.get(0); }
    public int getMaxProgress() { return data.get(1); }
    public int getTank1Amount() { return data.get(2); }
    public int getTank1Capacity() { return data.get(3); }
    public int getTank2Amount() { return data.get(4); }
    public int getTank2Capacity() { return data.get(5); }

    public float getProgressRatio() {
        int max = getMaxProgress();
        return max > 0 ? (float) getProgress() / max : 0;
    }
    public boolean isActive() { return getProgress() > 0; }

    public boolean hasRecipe() {
        if (blockEntity == null || level == null) return false;
        ItemStack input = blockEntity.getItem(MillBlockEntity.SLOT_INPUT);
        if (input.isEmpty()) return false;
        Optional<MillRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.MILL.get(), blockEntity, level)
                .map(holder -> holder.value());
        return recipe.isPresent();
    }
    public boolean canProcess() {
        if (blockEntity == null) return false;
        return blockEntity.canProcess();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (blockEntity != null && !level.isClientSide) {
            blockEntity.decrementOpeners();
        }
    }
}