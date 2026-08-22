package com.hang.miraculousori.advancement;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class LeapingFruitActivateTrigger extends SimpleCriterionTrigger<LeapingFruitActivateTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    // 静态触发方法
    public static void trigger(ServerPlayer player) {
        ModTriggers.LEAPING_FRUIT_ACTIVATE.get().trigger(player, instance -> true);
    }

    public static class TriggerInstance implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = Codec.unit(new TriggerInstance());

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }

    public static Criterion<TriggerInstance> criterion() {
        return new Criterion<>(ModTriggers.LEAPING_FRUIT_ACTIVATE.get(), new TriggerInstance());
    }
}