package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.block.custom.LeapingFruitPlantBlock;
import com.hang.miraculousori.block.custom.LeapingFruitPlantRootBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.joml.Vector3f;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class LeapingFruitPlantBonemealHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide) return;

        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if (!stack.is(Items.BONE_MEAL)) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof LeapingFruitPlantRootBlock)) return;

        // 非创造模式消耗骨粉
        if (!player.isCreative()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(event.getHand(), ItemStack.EMPTY);
            }
        }

        // 15%概率成功
        if (level.random.nextFloat() < LeapingFruitPlantBlock.BONE_MEAL_CHANCE) {
            BlockState spreading = ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get().defaultBlockState()
                    .setValue(LeapingFruitPlantBlock.FACING, state.getValue(LeapingFruitPlantBlock.FACING))
                    .setValue(LeapingFruitPlantBlock.IS_NATURAL, state.getValue(LeapingFruitPlantBlock.IS_NATURAL))
                    .setValue(LeapingFruitPlantBlock.REMAINING_GROWTH, 0)
                    .setValue(LeapingFruitPlantBlock.MAXED_OUT, false);
            level.setBlock(pos, spreading, 3);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.scheduleTick(pos, ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), 1);
                // 生成粒子效果（环绕方块）
                spawnParticles(serverLevel, pos);
            }
        }
        // 失败则无效果（骨粉已消耗）
    }

    private static void spawnParticles(ServerLevel level, BlockPos pos) {
        Vector3f color = new Vector3f(1.0f, 0.8f, 0.0f);
        float scale = 0.5f;
        DustParticleOptions options = new DustParticleOptions(color, scale);
        for (int i = 0; i < 30; i++) {
            double xOffset = (level.random.nextDouble() - 0.5) * 1.2;
            double yOffset = (level.random.nextDouble() - 0.5) * 1.2 + 0.5;
            double zOffset = (level.random.nextDouble() - 0.5) * 1.2;
            level.sendParticles(options,
                    pos.getX() + 0.5 + xOffset,
                    pos.getY() + 0.5 + yOffset,
                    pos.getZ() + 0.5 + zOffset,
                    1, 0, 0, 0, 0);
        }
    }
}