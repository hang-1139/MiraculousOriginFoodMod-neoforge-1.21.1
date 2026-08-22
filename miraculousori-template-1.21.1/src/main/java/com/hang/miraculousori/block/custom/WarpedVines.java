package com.hang.miraculousori.block.custom;

import com.hang.miraculousori.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface WarpedVines {
    BooleanProperty BERRIES = BooleanProperty.create("berries");
    BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    BooleanProperty TOP = BooleanProperty.create("top");
    BooleanProperty CAN_PLATFORM = BooleanProperty.create("can_platform");
    BooleanProperty BELOW_AIR = BooleanProperty.create("below_air");
    BooleanProperty BELOW_HEAD = BooleanProperty.create("below_head");

    static InteractionResult use(Player player, BlockState state, Level level, BlockPos pos) {
        if (state.getValue(BERRIES)) {
            if (!level.isClientSide) {
                Block.popResource(level, pos, new ItemStack(ModItems.WARPED_FRUIT.get()));
                level.setBlock(pos, state.setValue(BERRIES, false), 3);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}