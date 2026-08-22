package com.hang.miraculousori.entity.blockentity;

import com.hang.miraculousori.advancement.PressResidueTrigger;
import com.hang.miraculousori.block.custom.MillBlock;
import com.hang.miraculousori.effect.ModMobEffects; // 新增导入
import com.hang.miraculousori.fluid.ModFluids;
import com.hang.miraculousori.item.ModItems;
import com.hang.miraculousori.menu.MillMenu;
import com.hang.miraculousori.recipe.LiquidFillingRecipe;
import com.hang.miraculousori.recipe.LiquidFillingRecipeInput;
import com.hang.miraculousori.recipe.MillRecipe;
import com.hang.miraculousori.recipe.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.effect.MobEffectInstance; // 新增导入
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class MillBlockEntity extends BaseContainerBlockEntity
        implements WorldlyContainer, StackedContentsCompatible, RecipeInput {

    // ==================== 槽位常量 ====================
    public static final int SLOT_INPUT = 0;
    public static final int SLOT_FLUID_CONTAINER = 1;   // 弃用，保留占位
    public static final int SLOT_OUTPUT_1 = 2;
    public static final int SLOT_OUTPUT_2 = 3;
    public static final int SLOT_TANK1_INPUT = 4;
    public static final int SLOT_TANK1_OUTPUT = 5;
    public static final int SLOT_TANK2_INPUT = 6;
    public static final int SLOT_TANK2_OUTPUT = 7;
    public static final int SLOT_COUNT = 8;

    private int getContainerCapacity(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.BUCKET) return ModFluids.BUCKET_CAPACITY;
        if (item == Items.GLASS_BOTTLE) return ModFluids.GLASS_BOTTLE_CAPACITY;
        if (item == ModItems.GLASS_CUP.get()) return ModFluids.GLASS_CUP_CAPACITY;
        if (item == ModItems.COOKIE_MOLD.get()) return ModFluids.MOLD_CAPACITY;
        return 0;
    }

    public static final int MAX_WATER = 1000;

    private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private final FluidSlot tank1 = new FluidSlot(MAX_WATER);
    private final FluidSlot tank2 = new FluidSlot(MAX_WATER);
    private int progress = 0;
    private int maxProgress = 200;
    private float experience = 0.0F;
    private int foodCost = 0;
    @Nullable
    private MillRecipe currentRecipe = null;
    @Nullable
    private Player grindingPlayer = null;
    private int openPlayerCount = 0;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> tank1.getAmount();
                case 3 -> tank1.getCapacity();
                case 4 -> tank2.getAmount();
                case 5 -> tank2.getCapacity();
                case 6 -> ModFluids.getFluidId(tank1.getFluidType());
                case 7 -> ModFluids.getFluidId(tank2.getFluidType());
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (level != null && level.isClientSide) return;
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
                case 2 -> tank1.setAmount(value);
                case 3 -> { /* 只读 */ }
                case 4 -> tank2.setAmount(value);
                case 5 -> { /* 只读 */ }
            }
        }

        @Override
        public int getCount() {
            return 8;
        }
    };

    public MillBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MILL_BLOCK_ENTITY.get(), pos, state);
    }

    // ==================== 容器基础方法 ====================
    @Override
    protected NonNullList<ItemStack> getItems() { return this.items; }
    @Override
    protected void setItems(NonNullList<ItemStack> items) { this.items = items; this.setChanged(); }
    @Override
    public ItemStack getItem(int index) { return index >= 0 && index < items.size() ? items.get(index) : ItemStack.EMPTY; }
    @Override
    public int size() { return items.size(); }
    @Override
    public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override
    public int getContainerSize() { return SLOT_COUNT; }
    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(items, slot, amount);
        if (!stack.isEmpty()) this.setChanged();
        return stack;
    }
    @Override
    public ItemStack removeItemNoUpdate(int slot) { return ContainerHelper.takeItem(items, slot); }
    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());
        this.setChanged();

        if (slot == SLOT_TANK1_INPUT || slot == SLOT_TANK2_INPUT) {
            handleLiquidInputSlot(slot);
        }
        if (slot == SLOT_INPUT) {
            checkRecipeAndStart();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }
    @Override
    public void clearContent() { items.clear(); this.setChanged(); }

    // ==================== WorldlyContainer ====================
    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.UP) {
            return new int[]{SLOT_INPUT};
        } else if (side == Direction.DOWN) {
            return new int[]{SLOT_OUTPUT_1, SLOT_OUTPUT_2, SLOT_TANK1_OUTPUT, SLOT_TANK2_OUTPUT};
        } else { // 水平方向
            return new int[]{SLOT_TANK1_INPUT, SLOT_TANK2_INPUT};
        }
    }
    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        if (side == Direction.UP) {
            return slot == SLOT_INPUT;
        } else if (side == Direction.DOWN) {
            return false;
        } else { // 水平
            if (slot == SLOT_TANK1_INPUT || slot == SLOT_TANK2_INPUT) {
                return isValidContainer(stack);
            }
            return false;
        }
    }
    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        if (side == Direction.DOWN) {
            return slot == SLOT_OUTPUT_1 || slot == SLOT_OUTPUT_2 ||
                    slot == SLOT_TANK1_OUTPUT || slot == SLOT_TANK2_OUTPUT;
        }
        return false;
    }

    @Override
    public void fillStackedContents(StackedContents helper) {
        for (ItemStack stack : items) helper.accountStack(stack);
    }

    // ==================== 菜单相关 ====================
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.miraculousori.mill");
    }
    @Override
    protected AbstractContainerMenu createMenu(int id, net.minecraft.world.entity.player.Inventory inv) {
        return new MillMenu(id, inv, this, dataAccess);
    }

    // ==================== NBT ====================
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
        tag.putFloat("Experience", experience);
        tag.putInt("FoodCost", foodCost);
        tag.put("Tank1", tank1.writeNBT(registries, new CompoundTag()));
        tag.put("Tank2", tank2.writeNBT(registries, new CompoundTag()));
        tag.putInt("OpenPlayerCount", openPlayerCount);
    }
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
        progress = tag.getInt("Progress");
        maxProgress = tag.getInt("MaxProgress");
        experience = tag.getFloat("Experience");
        foodCost = tag.getInt("FoodCost");
        tank1.readNBT(registries, tag.getCompound("Tank1"));
        tank2.readNBT(registries, tag.getCompound("Tank2"));
        openPlayerCount = tag.getInt("OpenPlayerCount");
    }

    // ==================== 数据包同步 ====================
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    // ==================== 流体槽类 ====================
    public class FluidSlot {
        private String fluidType = "";
        private int amount = 0;
        private final int capacity;
        public FluidSlot(int capacity) { this.capacity = capacity; }
        public boolean isEmpty() { return amount == 0 || fluidType.isEmpty(); }
        public int getAmount() { return amount; }
        public int getCapacity() { return capacity; }
        public String getFluidType() { return fluidType; }
        public void setAmount(int amount) {
            this.amount = Math.min(amount, capacity);
            if (this.amount <= 0) { this.fluidType = ""; this.amount = 0; }
            setChanged();
        }
        public void setFluid(String fluidType, int amount) {
            this.fluidType = fluidType;
            this.amount = Math.min(amount, capacity);
            if (this.amount <= 0) { this.fluidType = ""; this.amount = 0; }
            setChanged();
        }
        public int fill(String fluidType, int maxFill) {
            if (isEmpty()) {
                int fill = Math.min(maxFill, capacity);
                this.fluidType = fluidType;
                this.amount = fill;
                setChanged();
                return fill;
            } else if (this.fluidType.equals(fluidType)) {
                int space = capacity - amount;
                int fill = Math.min(maxFill, space);
                amount += fill;
                setChanged();
                return fill;
            } else {
                return 0;
            }
        }
        public int drain(int maxDrain) {
            if (isEmpty()) return 0;
            int drained = Math.min(amount, maxDrain);
            amount -= drained;
            if (amount == 0) fluidType = "";
            setChanged();
            return drained;
        }
        public CompoundTag writeNBT(HolderLookup.Provider registries, CompoundTag tag) {
            tag.putString("FluidType", fluidType);
            tag.putInt("Amount", amount);
            return tag;
        }
        public void readNBT(HolderLookup.Provider registries, CompoundTag tag) {
            fluidType = tag.getString("FluidType");
            amount = tag.getInt("Amount");
        }
    }

    public void processGrindingByVillager(@Nullable Villager villager) {
        if (level == null || level.isClientSide) return;

        // 如果没有当前配方，尝试匹配
        if (currentRecipe == null) {
            checkRecipeAndStart();
            if (currentRecipe == null) {
                return;
            }
        }

        // 检查是否能继续加工（绕过 grindingPlayer 检查）
        if (!currentRecipe.matches(this, level)) {
            stopProcessing(false);
            return;
        }
        if (!canFitOutputs(currentRecipe)) {
            return;
        }
        if (!canAcceptLiquid(currentRecipe.getLiquidOutput(), currentRecipe.getLiquidAmount())) {
            return;
        }

        // ===== 计算磨制速度 =====
        int speed = 1;
        if (villager != null) {
            MobEffectInstance effect = villager.getEffect(ModMobEffects.INVIGORATE);
            if (effect != null) {
                speed = effect.getAmplifier() + 1; // 等级 I → 2, II → 3, ...
            }
        }
        progress += speed;

        setActive(true, level, worldPosition);
        if (progress >= maxProgress) {
            finishProcessingByVillager();
        }
        setChanged();
    }

    /**
     * 村民完成磨制
     */
    private void finishProcessingByVillager() {
        if (currentRecipe == null || level == null) return;

        // 消耗原料
        ItemStack input = getItem(SLOT_INPUT);
        input.shrink(currentRecipe.getIngredientCount());

        // 产出液体
        String liquidType = currentRecipe.getLiquidOutput();
        int liquidAmount = currentRecipe.getLiquidAmount();
        if (liquidAmount > 0 && liquidType != null && !liquidType.isEmpty()) {
            int filled1 = tank1.fill(liquidType, liquidAmount);
            liquidAmount -= filled1;
            if (liquidAmount > 0) tank2.fill(liquidType, liquidAmount);
        }

        // 产出物品
        List<ItemStack> outputs = currentRecipe.getOutputs();
        if (!outputs.isEmpty()) {
            ItemStack out1 = outputs.get(0);
            ItemStack out2 = outputs.size() > 1 ? outputs.get(1) : ItemStack.EMPTY;

            // 放入输出槽1
            ItemStack slot1 = getItem(SLOT_OUTPUT_1);
            if (slot1.isEmpty() || (ItemStack.isSameItemSameComponents(slot1, out1) && slot1.getCount() + out1.getCount() <= slot1.getMaxStackSize())) {
                if (slot1.isEmpty()) setItem(SLOT_OUTPUT_1, out1.copy());
                else slot1.grow(out1.getCount());
            } else {
                // 放入输出槽2
                ItemStack slot2 = getItem(SLOT_OUTPUT_2);
                if (slot2.isEmpty() || (ItemStack.isSameItemSameComponents(slot2, out1) && slot2.getCount() + out1.getCount() <= slot2.getMaxStackSize())) {
                    if (slot2.isEmpty()) setItem(SLOT_OUTPUT_2, out1.copy());
                    else slot2.grow(out1.getCount());
                }
            }
            // 处理第二个输出（如果有）
            if (!out2.isEmpty()) {
                ItemStack slot2 = getItem(SLOT_OUTPUT_2);
                if (slot2.isEmpty() || (ItemStack.isSameItemSameComponents(slot2, out2) && slot2.getCount() + out2.getCount() <= slot2.getMaxStackSize())) {
                    if (slot2.isEmpty()) setItem(SLOT_OUTPUT_2, out2.copy());
                    else slot2.grow(out2.getCount());
                }
            }
        }

        progress = 0;
        currentRecipe = null;
        setActive(false, level, worldPosition);
        setChanged();
        checkRecipeAndStart();
    }

    public FluidSlot getTank1() { return tank1; }
    public FluidSlot getTank2() { return tank2; }

    // ==================== 液体交互槽处理 ====================
    private void handleLiquidInputSlot(int slot) {
        if (level == null || level.isClientSide) return;
        ItemStack container = getItem(slot);
        if (container.isEmpty()) return;

        FluidSlot tank = (slot == SLOT_TANK1_INPUT) ? tank1 : tank2;
        if (tank.isEmpty()) return;

        String fluidType = tank.getFluidType();
        int available = tank.getAmount();

        LiquidFillingRecipeInput input = new LiquidFillingRecipeInput(container, fluidType);
        Optional<RecipeHolder<LiquidFillingRecipe>> recipeOpt = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.LIQUID_FILLING.get(), input, level);
        if (recipeOpt.isEmpty()) return;

        LiquidFillingRecipe recipe = recipeOpt.get().value();
        int fluidNeeded = recipe.getFluidAmount();
        if (available < fluidNeeded) return;

        int maxCount = Math.min(container.getCount(), available / fluidNeeded);
        if (maxCount <= 0) return;

        int takeCount = maxCount;
        int totalDrain = takeCount * fluidNeeded;
        tank.drain(totalDrain);

        ItemStack filledItem = recipe.getResult().copy();
        filledItem.setCount(takeCount);

        container.shrink(takeCount);
        if (container.isEmpty()) {
            setItem(slot, ItemStack.EMPTY);
        }

        int outputSlot = (slot == SLOT_TANK1_INPUT) ? SLOT_TANK1_OUTPUT : SLOT_TANK2_OUTPUT;
        ItemStack existing = getItem(outputSlot);
        if (existing.isEmpty()) {
            setItem(outputSlot, filledItem);
        } else if (ItemStack.isSameItemSameComponents(existing, filledItem) &&
                existing.getCount() + filledItem.getCount() <= existing.getMaxStackSize()) {
            existing.grow(filledItem.getCount());
        } else {
            if (grindingPlayer != null) {
                if (!grindingPlayer.getInventory().add(filledItem)) {
                    grindingPlayer.drop(filledItem, false);
                }
            } else {
                Block.popResource(level, worldPosition, filledItem);
            }
        }
        setChanged();
    }

    public boolean isValidContainer(ItemStack stack) {
        return getContainerCapacity(stack) > 0;
    }

    // ==================== 磨制逻辑 ====================
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) return;
        if (openPlayerCount == 0) return;

        // 每tick检测液体输入槽
        handleLiquidInputSlot(SLOT_TANK1_INPUT);
        handleLiquidInputSlot(SLOT_TANK2_INPUT);

        if (currentRecipe != null && !currentRecipe.matches(this, level)) {
            stopProcessing(false);
            return;
        }
        if (currentRecipe == null) {
            checkRecipeAndStart();
            if (currentRecipe == null) {
                setActive(false, level, pos);
                return;
            }
        }
        if (!canProcess()) {
            stopProcessing(false);
            setActive(false, level, pos);
            return;
        }

        // ===== 振奋效果加速 =====
        int speed = 1;
        if (grindingPlayer != null) {
            MobEffectInstance effect = grindingPlayer.getEffect(ModMobEffects.INVIGORATE);
            if (effect != null) {
                speed = effect.getAmplifier() + 1;  // 等级 I → 2, 等级 II → 3, ...
            }
        }
        progress += speed;

        setActive(true, level, pos);
        if (progress >= maxProgress) {
            finishProcessing();
        }
        setChanged();
    }

    private void checkRecipeAndStart() {
        if (level == null) return;
        ItemStack input = getItem(SLOT_INPUT);
        if (input.isEmpty()) {
            currentRecipe = null;
            progress = 0;
            return;
        }
        Optional<RecipeHolder<MillRecipe>> holderOpt = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.MILL.get(), this, level);
        if (holderOpt.isPresent()) {
            MillRecipe millRecipe = holderOpt.get().value();
            if (!canAcceptLiquid(millRecipe.getLiquidOutput(), millRecipe.getLiquidAmount())) return;
            if (!canFitOutputs(millRecipe)) return;
            currentRecipe = millRecipe;
            maxProgress = millRecipe.getGrindingTime();
            experience = millRecipe.getExperience();
            foodCost = millRecipe.getFoodCost();
        } else {
            currentRecipe = null;
            progress = 0;
        }
    }

    private boolean canFitOutputs(MillRecipe recipe) {
        List<ItemStack> outputs = recipe.getOutputs();
        if (outputs.isEmpty()) return true;
        ItemStack slot1 = getItem(SLOT_OUTPUT_1);
        ItemStack slot2 = getItem(SLOT_OUTPUT_2);
        ItemStack out1 = outputs.get(0);
        boolean canFit1 = slot1.isEmpty() || (ItemStack.isSameItemSameComponents(slot1, out1) && slot1.getCount() + out1.getCount() <= slot1.getMaxStackSize());
        boolean canFit2 = slot2.isEmpty() || (ItemStack.isSameItemSameComponents(slot2, out1) && slot2.getCount() + out1.getCount() <= slot2.getMaxStackSize());
        if (!canFit1 && !canFit2) return false;
        if (outputs.size() > 1) {
            ItemStack out2 = outputs.get(1);
            boolean canFitOut2 = slot2.isEmpty() || (ItemStack.isSameItemSameComponents(slot2, out2) && slot2.getCount() + out2.getCount() <= slot2.getMaxStackSize());
            if (!canFitOut2) return false;
        }
        return true;
    }

    private boolean canAcceptLiquid(String liquidType, int amount) {
        if (liquidType == null || liquidType.isEmpty() || amount <= 0) return true;
        if (tank1.isEmpty() || tank1.getFluidType().equals(liquidType)) {
            if (tank1.getAmount() + amount <= tank1.getCapacity()) return true;
        }
        if (tank2.isEmpty() || tank2.getFluidType().equals(liquidType)) {
            if (tank2.getAmount() + amount <= tank2.getCapacity()) return true;
        }
        return false;
    }

    public boolean canProcess() {
        if (currentRecipe == null || level == null) return false;
        if (!canAcceptLiquid(currentRecipe.getLiquidOutput(), currentRecipe.getLiquidAmount())) return false;
        if (!canFitOutputs(currentRecipe)) return false;
        if (grindingPlayer == null) return false;
        return hasEnoughFood(grindingPlayer);
    }

    private boolean hasEnoughFood(Player player) {
        FoodData food = player.getFoodData();
        return food.getFoodLevel() + food.getSaturationLevel() >= foodCost;
    }

    /**
     * Shift+右键取水逻辑：优先取水槽1，若为空则取水槽2
     * 也通过配方查找实现
     */
    public boolean extractFluid(ItemStack held, Player player) {
        if (level == null || level.isClientSide) return false;

        FluidSlot targetTank = null;
        if (!tank1.isEmpty()) {
            targetTank = tank1;
        } else if (!tank2.isEmpty()) {
            targetTank = tank2;
        } else {
            return false;
        }

        String fluidType = targetTank.getFluidType();
        int available = targetTank.getAmount();

        LiquidFillingRecipeInput input = new LiquidFillingRecipeInput(held, fluidType);
        Optional<RecipeHolder<LiquidFillingRecipe>> recipeOpt = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.LIQUID_FILLING.get(), input, level);
        if (recipeOpt.isEmpty()) return false;

        LiquidFillingRecipe recipe = recipeOpt.get().value();
        int fluidNeeded = recipe.getFluidAmount();
        if (available < fluidNeeded) return false;

        int maxCanTake = available / fluidNeeded;
        if (maxCanTake == 0) return false;
        int takeCount = Math.min(maxCanTake, held.getCount());
        int totalDrain = takeCount * fluidNeeded;
        targetTank.drain(totalDrain);

        ItemStack filled = recipe.getResult().copy();
        filled.setCount(takeCount);

        held.shrink(takeCount);
        if (held.isEmpty()) {
            player.setItemInHand(player.getUsedItemHand(), filled);
        } else {
            if (!player.getInventory().add(filled)) {
                player.drop(filled, false);
            }
        }
        setChanged();
        return true;
    }

    private void consumeFood(Player player) {
        FoodData food = player.getFoodData();
        float sat = food.getSaturationLevel();
        int remaining = foodCost;
        if (sat >= remaining) {
            food.setSaturation(sat - remaining);
        } else {
            food.setSaturation(0);
            remaining -= sat;
            food.setFoodLevel(Math.max(0, food.getFoodLevel() - remaining));
        }
    }

    private void finishProcessing() {
        if (currentRecipe == null || level == null) return;

        if (grindingPlayer != null && !grindingPlayer.isCreative()) {
            consumeFood(grindingPlayer);
        } else if (grindingPlayer == null) {
            stopProcessing(true);
            return;
        }
        ItemStack input = getItem(SLOT_INPUT);
        input.shrink(currentRecipe.getIngredientCount());
        String liquidType = currentRecipe.getLiquidOutput();
        int liquidAmount = currentRecipe.getLiquidAmount();
        if (liquidAmount > 0 && liquidType != null && !liquidType.isEmpty()) {
            int filled1 = tank1.fill(liquidType, liquidAmount);
            liquidAmount -= filled1;
            if (liquidAmount > 0) tank2.fill(liquidType, liquidAmount);
        }
        List<ItemStack> outputs = currentRecipe.getOutputs();
        if (!outputs.isEmpty()) {
            ItemStack out1 = outputs.get(0);
            ItemStack out2 = outputs.size() > 1 ? outputs.get(1) : ItemStack.EMPTY;
            ItemStack slot1 = getItem(SLOT_OUTPUT_1);
            if (slot1.isEmpty() || (ItemStack.isSameItemSameComponents(slot1, out1) && slot1.getCount() + out1.getCount() <= slot1.getMaxStackSize())) {
                if (slot1.isEmpty()) setItem(SLOT_OUTPUT_1, out1.copy());
                else slot1.grow(out1.getCount());
            } else {
                ItemStack slot2 = getItem(SLOT_OUTPUT_2);
                if (slot2.isEmpty() || (ItemStack.isSameItemSameComponents(slot2, out1) && slot2.getCount() + out1.getCount() <= slot2.getMaxStackSize())) {
                    if (slot2.isEmpty()) setItem(SLOT_OUTPUT_2, out1.copy());
                    else slot2.grow(out1.getCount());
                }
            }
            if (!out2.isEmpty()) {
                ItemStack slot2 = getItem(SLOT_OUTPUT_2);
                if (slot2.isEmpty() || (ItemStack.isSameItemSameComponents(slot2, out2) && slot2.getCount() + out2.getCount() <= slot2.getMaxStackSize())) {
                    if (slot2.isEmpty()) setItem(SLOT_OUTPUT_2, out2.copy());
                    else slot2.grow(out2.getCount());
                }
            }
        }
        if (grindingPlayer != null) {
            int exp = (int) experience;
            if (exp > 0) grindingPlayer.giveExperiencePoints(exp);
        }
        progress = 0;
        currentRecipe = null;
        setActive(false, level, worldPosition);
        setChanged();
        checkRecipeAndStart();
        if (grindingPlayer instanceof ServerPlayer serverPlayer) {
            PressResidueTrigger.trigger(serverPlayer);
        }
    }

    private void stopProcessing(boolean resetProgress) {
        if (resetProgress) progress = 0;
        currentRecipe = null;
        setActive(false, level, worldPosition);
        setChanged();
    }

    private void setActive(boolean active, Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getValue(MillBlock.ACTIVE) != active) {
            level.setBlock(pos, state.setValue(MillBlock.ACTIVE, active), 3);
        }
    }

    // ==================== 玩家计数 ====================
    public void incrementOpeners() {
        openPlayerCount++;
        setChanged();
    }
    public void decrementOpeners() {
        openPlayerCount--;
        if (openPlayerCount < 0) openPlayerCount = 0;
        setChanged();
        if (openPlayerCount == 0) grindingPlayer = null;
    }
    public void setGrindingPlayer(Player player) { this.grindingPlayer = player; }
    public ContainerData getContainerData() { return dataAccess; }
    @Nullable
    public MillRecipe getCurrentRecipe() { return currentRecipe; }

    public void dropContents() {
        if (level != null) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack stack = items.get(i);
                if (!stack.isEmpty()) {
                    Block.popResource(level, worldPosition, stack);
                    items.set(i, ItemStack.EMPTY);
                }
            }
            setChanged();
        }
    }

    public int getAnalogOutputSignal() {
        int nonEmpty = 0;
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) nonEmpty++;
        }
        return Math.min(nonEmpty, 15);
    }
}