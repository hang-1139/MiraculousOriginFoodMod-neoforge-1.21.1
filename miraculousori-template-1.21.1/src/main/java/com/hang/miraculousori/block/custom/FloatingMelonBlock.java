package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.advancement.ModTriggers;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.entity.custom.FloatingMelonEntity;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class FloatingMelonBlock extends Block {

    public FloatingMelonBlock(Properties properties) {
        super(properties);
    }

    /**
     * 计划刻：检测下方是否为浮瓜苗，否则转为实体
     */
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        boolean shouldSpawnEntity = false;

        // 如果下方不是浮瓜苗，则生成实体
        if (!belowState.is(ModBlocks.FLOATING_MELON_CROP.get())) {
            shouldSpawnEntity = true;
        } else {
            // 是浮瓜苗，检查是否完全成熟（age == 7）
            int age = belowState.getValue(ModBlocks.FLOATING_MELON_CROP.get().getAgeProperty());
            if (age != 7) {
                shouldSpawnEntity = true;
            }
        }

        if (shouldSpawnEntity) {
            // 移除方块（不产生掉落）
            level.removeBlock(pos, false);
            // 生成飞浮瓜实体
            FloatingMelonEntity.spawnFloatingMelon(level, pos);

            // ========== 🏆 触发进度：它飞走了 ==========
            // 给附近 16 格内的所有玩家触发
            double radius = 16.0;
            AABB aabb = new AABB(pos).inflate(radius);
            List<Player> players = level.getEntitiesOfClass(Player.class, aabb);
            for (Player player : players) {
                if (player instanceof ServerPlayer serverPlayer) {
                    ModTriggers.FLOAT_MELON.get().trigger(serverPlayer);
                }
            }
        } else {
            // 否则继续调度下一个 tick（每 tick 检测一次）
            level.scheduleTick(pos, this, 1);
        }
    }

    /**
     * 当方块被放置时，立即启动计划刻
     */
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            // 调度第一个 tick
            level.scheduleTick(pos, this, 1);
        }
    }

    /**
     * 玩家破坏时的掉落逻辑（由代码控制）
     * 创造模式不产生任何掉落
     */
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        // 如果是创造模式玩家，只破坏方块（播放效果），不产生掉落
        if (player.isCreative()) {
            level.destroyBlock(pos, false);
            return state;
        }

        // 非创造模式：由代码控制掉落（与之前逻辑相同）
        if (!level.isClientSide) {
            ItemStack tool = player.getMainHandItem();
            boolean hasSilkTouch = EnchantmentHelper.getItemEnchantmentLevel(
                    level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
                    tool
            ) > 0;

            if (hasSilkTouch) {
                // 精准采集：掉落方块自身
                popResource(level, pos, new ItemStack(ModBlocks.FLOATING_MELON_BLOCK.get()));
            } else {
                // 非精准采集：掉落 1 个碎浮瓜 + 1 个种子
                popResource(level, pos, new ItemStack(ModItems.BROKEN_FLOATING_GOURD.get()));
                popResource(level, pos, new ItemStack(ModItems.FLOATING_MELON_SEEDS.get()));
            }
        }

        // 破坏方块（播放效果，不触发战利品表）
        level.destroyBlock(pos, false);
        return state;
    }

    /**
     * 辅助：弹出一个物品到世界中
     */
    public static void popResource(Level level, BlockPos pos, ItemStack stack) {
        if (level instanceof ServerLevel serverLevel) {
            net.minecraft.world.entity.item.ItemEntity itemEntity = new net.minecraft.world.entity.item.ItemEntity(
                    serverLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack
            );
            serverLevel.addFreshEntity(itemEntity);
        }
    }
}