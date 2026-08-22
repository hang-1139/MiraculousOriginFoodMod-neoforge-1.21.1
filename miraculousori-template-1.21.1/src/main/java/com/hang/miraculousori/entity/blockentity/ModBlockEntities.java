package com.hang.miraculousori.entity.blockentity;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MiraculousOriginFoodMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MillBlockEntity>> MILL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("mill_block_entity",
                    () -> BlockEntityType.Builder.of(MillBlockEntity::new,
                            ModBlocks.MILL_BLOCK.get()).build(null));
}