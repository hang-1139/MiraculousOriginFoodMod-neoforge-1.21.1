package com.hang.miraculousori.advancement;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class FloatMelonTrigger extends SimpleCriterionTrigger<FloatMelonTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class TriggerInstance implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = Codec.unit(new TriggerInstance());

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }

    public static Criterion<TriggerInstance> criterion() {
        return new Criterion<>(ModTriggers.FLOAT_MELON.get(), new TriggerInstance());
    }
}