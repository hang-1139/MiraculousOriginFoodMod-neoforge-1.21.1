package com.hang.miraculousori.effect;

import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.entity.blockentity.MillBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class WorkingEffect extends MobEffect {

    private static final int MILL_INTERVAL = 2; // 每2 tick执行一次磨制

    public WorkingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof Villager villager)) return true;
        Level level = villager.level();
        if (level.isClientSide) return true;

        BlockPos millPos = findNearestMill(villager);
        if (millPos == null) return true;

        double distSq = villager.blockPosition().distSqr(millPos);
        if (distSq > 2.25) {
            villager.getNavigation().moveTo(
                    millPos.getX() + 0.5,
                    millPos.getY(),
                    millPos.getZ() + 0.5,
                    0.8
            );
            return true;
        }

        villager.lookAt(
                net.minecraft.commands.arguments.EntityAnchorArgument.Anchor.EYES,
                Vec3.atCenterOf(millPos)
        );

        if (villager.tickCount % MILL_INTERVAL == 0) {
            BlockEntity be = level.getBlockEntity(millPos);
            if (be instanceof MillBlockEntity millBE) {
                // 传入 villager 实例以检测振奋效果
                millBE.processGrindingByVillager(villager);
            }
        }

        return true;
    }

    private BlockPos findNearestMill(Villager villager) {
        Level level = villager.level();
        BlockPos villagerPos = villager.blockPosition();
        int radius = 8;
        BlockPos nearest = null;
        double nearestDistSq = Double.MAX_VALUE;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos checkPos = villagerPos.offset(dx, dy, dz);
                    if (level.getBlockState(checkPos).is(ModBlocks.MILL_BLOCK.get())) {
                        double distSq = villagerPos.distSqr(checkPos);
                        if (distSq < nearestDistSq) {
                            nearestDistSq = distSq;
                            nearest = checkPos;
                        }
                    }
                }
            }
        }
        return nearest;
    }
}