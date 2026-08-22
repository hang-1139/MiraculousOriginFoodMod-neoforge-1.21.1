package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.block.custom.LeapingFruitPlantBlock;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class LeapingFruitPlantInteractionHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        // 只处理我们的三种方块
        if (!(block instanceof LeapingFruitPlantBlock)) {
            return;
        }

        // 必须手持斧子
        if (!stack.is(ItemTags.AXES)) {
            return;
        }

        int count = 0;
        LeapingFruitPlantBlock plantBlock = (LeapingFruitPlantBlock) block;

        if (block == ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get()) {
            // 根系：70% 概率掉落 1 个
            if (level.random.nextFloat() < plantBlock.getDropChance()) {
                count = 1;
            }
        } else if (block == ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get()) {
            // 蔓延根系：30% 概率掉落 1 个
            if (level.random.nextFloat() < plantBlock.getDropChance()) {
                count = 1;
            }
        } else if (block == ModBlocks.LEAPING_FRUIT_PLANT_TOP.get()) {
            // 顶端：基础 1~4 + 时运额外
            int base = 1 + level.random.nextInt(4);
            int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(
                    level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
                    stack
            );
            int extra = level.random.nextInt(fortuneLevel + 1);
            count = base + extra;
        }

        // 生成掉落物
        if (count > 0) {
            Block.popResource(level, pos, new ItemStack(ModItems.LEAPING_FRUIT.get(), count));
        }

        // 替换为末地石
        level.setBlock(pos, Blocks.END_STONE.defaultBlockState(), 3);
        level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

        // 消耗斧子耐久 1 点
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(event.getHand()));

        // 取消原事件，避免其他交互
        event.setCanceled(true);
    }
}