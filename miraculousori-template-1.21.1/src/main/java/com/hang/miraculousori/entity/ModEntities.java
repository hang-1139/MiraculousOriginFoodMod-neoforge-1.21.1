package com.hang.miraculousori.entity;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.entity.custom.DivineItemEntity;
import com.hang.miraculousori.entity.custom.FloatingMelonEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, MiraculousOriginFoodMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<FloatingMelonEntity>> FLOATING_MELON =
            ENTITIES.register("floating_melon",
                    () -> EntityType.Builder.<FloatingMelonEntity>of(
                                    (EntityType<FloatingMelonEntity> type, Level level) ->
                                            new FloatingMelonEntity(type, level),
                                    MobCategory.MISC
                            )
                            .sized(1.0f, 1.0f)
                            .build("floating_melon")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<DivineItemEntity>> DIVINE_ITEM =
            ENTITIES.register("divine_item", () -> EntityType.Builder.<DivineItemEntity>of(DivineItemEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)        // 与普通掉落物大小一致
                    .eyeHeight(0.13F)
                    .clientTrackingRange(6)
                    .updateInterval(20)
                    .build("divine_item"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}