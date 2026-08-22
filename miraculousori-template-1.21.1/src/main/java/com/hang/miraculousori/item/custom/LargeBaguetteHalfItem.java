package com.hang.miraculousori.item.custom;

import com.hang.miraculousori.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LargeBaguetteHalfItem extends Item {
    public LargeBaguetteHalfItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide && entity instanceof Player player && !player.isCreative()) {
            player.getInventory().add(new ItemStack(ModItems.SMALL_BAGUETTE_HALF.get()));
        }
        return result;
    }
}