package com.hang.miraculousori.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class HoldingBothTrigger extends SimpleCriterionTrigger<HoldingBothTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> instance.matches(player));
    }

    public static class TriggerInstance implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                inst -> inst.group(
                        BuiltInRegistries.ITEM.byNameCodec().fieldOf("item1").forGetter(i -> i.item1),
                        BuiltInRegistries.ITEM.byNameCodec().fieldOf("item2").forGetter(i -> i.item2)
                ).apply(inst, TriggerInstance::new)
        );

        private final Item item1;
        private final Item item2;

        public TriggerInstance(Item item1, Item item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

        public boolean matches(ServerPlayer player) {
            ItemStack main = player.getMainHandItem();
            ItemStack off = player.getOffhandItem();
            return (main.is(item1) && off.is(item2)) || (main.is(item2) && off.is(item1));
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }

    public static Criterion<TriggerInstance> holdingBoth(Item item1, Item item2) {
        return new Criterion<>(ModTriggers.HOLDING_BOTH.get(), new TriggerInstance(item1, item2));
    }
}