package com.hang.miraculousori.entity.custom;

import com.hang.miraculousori.entity.ModEntities;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DivineItemEntity extends ItemEntity {

    // 龙息生成计时器（单位：tick）
    private int breathTimer = 0;

    // ===== 构造函数 =====
    public DivineItemEntity(EntityType<? extends ItemEntity> type, Level level) {
        super(type, level);
        this.initDivineProperties();
    }

    public DivineItemEntity(Level level, double x, double y, double z, ItemStack stack) {
        super(ModEntities.DIVINE_ITEM.get(), level);
        this.setItem(stack);
        this.setPos(x, y, z);
        this.initDivineProperties();
    }

    private void initDivineProperties() {
        // 悬浮（无重力）
        this.setNoGravity(true);

        // 发光描边（白边）
        this.setGlowingTag(true);

        // 无敌（免疫所有伤害）
        this.setInvulnerable(true);

        // 无限寿命
        try {
            this.setUnlimitedLifetime(); // ItemEntity.INFINITE_LIFETIME = -32768
        } catch (NoSuchMethodError | NoClassDefFoundError e) {
            this.lifespan = Integer.MAX_VALUE;
        }

        // 无拾取延迟
        this.setNoPickUpDelay();
    }

    // ===== 防火 =====
    @Override
    public boolean fireImmune() {
        return true;
    }

    // ===== 伤害免疫 =====
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    // ===== 防止掉入虚空 + 龙息生成 =====
    @Override
    public void tick() {
        super.tick();

        // 防止掉入虚空
        if (this.getY() < -64.0D) {
            this.setPos(this.getX(), -64.0D, this.getZ());
            this.setDeltaMovement(0, 0, 0);
        }

        // 确保无限寿命
        try {
            if (this.getAge() >= 6000) {
                this.setUnlimitedLifetime();
            }
        } catch (NoSuchMethodError e) {
            // 忽略
        }

        // ----- 龙息生成逻辑（仅服务端） -----
        if (!this.level().isClientSide) {
            ItemStack stack = this.getItem();
            // 检查物品是否为 END_NEW_PATH（终幕新途）
            if (stack.is(ModItems.END_NEW_PATH.get())) {
                breathTimer++;
                if (breathTimer >= 200) { // 10秒 = 200 ticks
                    breathTimer = 0;
                    spawnDragonBreath();
                }
            } else {
                // 如果不是该物品，重置计时器（防止切换物品后立即生成）
                breathTimer = 0;
            }
        }
    }

    /**
     * 在物品位置生成龙息云（AreaEffectCloud）
     * 持续 8 秒（160 ticks），半径 3 格，效果为瞬间伤害
     */
    private void spawnDragonBreath() {
        AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        cloud.setRadius(3.0F);                      // 初始半径
        cloud.setRadiusPerTick(-0.01F);            // 每 tick 缩小，使云逐渐消散（可选）
        cloud.setDuration(160);                    // 持续 8 秒（160 ticks）
        cloud.setWaitTime(10);                     // 延迟生效时间（原版默认 10）
        cloud.setParticle(net.minecraft.core.particles.ParticleTypes.DRAGON_BREATH);
        // 设置效果：瞬间伤害（等级 1）
        cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 3, 0));

        // 生成到世界
        this.level().addFreshEntity(cloud);
    }
}