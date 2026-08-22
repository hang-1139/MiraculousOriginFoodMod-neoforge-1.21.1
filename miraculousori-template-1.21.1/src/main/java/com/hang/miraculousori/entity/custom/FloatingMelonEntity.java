package com.hang.miraculousori.entity.custom;

import com.hang.miraculousori.entity.ModEntities;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FloatingMelonEntity extends LivingEntity {

    private static final float RISE_SPEED = 0.025f;
    private static final float MAX_HEIGHT = 114.0f;
    private static final EntityDimensions DIMENSIONS = EntityDimensions.fixed(1.0f, 1.0f);

    public FloatingMelonEntity(EntityType<? extends FloatingMelonEntity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.blocksBuilding = true;
    }

    public FloatingMelonEntity(Level level, double x, double y, double z) {
        this(ModEntities.FLOATING_MELON.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setCustomName(null);
        this.setCustomNameVisible(false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            if (this.getY() >= MAX_HEIGHT) {
                this.convertToDrop();
                return;
            }

            this.setDeltaMovement(this.getDeltaMovement().add(0, RISE_SPEED, 0));
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.0, 1.0));
        }
    }

    private void convertToDrop() {
        if (this.level() instanceof ServerLevel serverLevel) {
            ItemStack melon = new ItemStack(ModItems.FLOATING_MELON.get());
            BlockPos pos = this.blockPosition();
            net.minecraft.world.entity.item.ItemEntity itemEntity = new net.minecraft.world.entity.item.ItemEntity(
                    serverLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, melon
            );
            serverLevel.addFreshEntity(itemEntity);

            this.playBreakSound();
            this.discard();
        }
    }

    private void playBreakSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide) return false;
        if (this.isRemoved()) return false;
        if (this.isInvulnerableTo(source)) return false;

        // 如果是创造模式玩家攻击，直接清除，不产生掉落物
        Entity attacker = source.getEntity();
        if (attacker instanceof Player player && player.isCreative()) {
            this.discard();
            return true;
        }

        // 其他情况：生成掉落物
        this.convertToDrop();
        return true;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void doPush(Entity entity) {
        super.doPush(entity);
    }

    @Override
    protected void pushEntities() {
        super.pushEntities();
    }

    @Override
    public boolean attackable() {
        return false;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return DIMENSIONS;
    }

    @Override
    public Iterable<ItemStack> getHandSlots() {
        return java.util.Collections.emptyList();
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return java.util.Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 1.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    // ===== 辅助生成方法 =====
    public static void spawnFloatingMelon(Level level, Vec3 pos) {
        FloatingMelonEntity entity = new FloatingMelonEntity(level, pos.x, pos.y, pos.z);
        level.addFreshEntity(entity);
    }

    public static void spawnFloatingMelon(Level level, BlockPos pos) {
        spawnFloatingMelon(level, Vec3.atCenterOf(pos));
    }
}