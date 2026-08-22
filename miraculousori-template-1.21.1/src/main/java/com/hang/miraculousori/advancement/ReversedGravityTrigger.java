package com.hang.miraculousori.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;

import java.util.Optional;

public class ReversedGravityTrigger extends SimpleCriterionTrigger<ReversedGravityTrigger.TriggerInstance> {

    public static final ReversedGravityTrigger INSTANCE = new ReversedGravityTrigger();

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> instance.matches(player));
    }

    // ===== 返回 Criterion 的静态方法（供 Advancement Builder 使用） =====
    public static Criterion<TriggerInstance> criterion() {
        return new Criterion<>(ModTriggers.REVERSED_GRAVITY.get(), new TriggerInstance(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
    }

    public static Criterion<TriggerInstance> heightCriterion(int minY) {
        return new Criterion<>(ModTriggers.REVERSED_GRAVITY.get(), new TriggerInstance(
                Optional.empty(),
                Optional.of(MinMaxBounds.Ints.exactly(minY)),
                Optional.empty()
        ));
    }

    public static Criterion<TriggerInstance> effectAndHeightCriterion(Holder<MobEffect> effect, int minY) {
        return new Criterion<>(ModTriggers.REVERSED_GRAVITY.get(), new TriggerInstance(
                Optional.of(effect),
                Optional.of(MinMaxBounds.Ints.exactly(minY)),
                Optional.empty()
        ));
    }

    // ===== 返回 TriggerInstance 的静态方法（内部使用） =====
    public static TriggerInstance triggerInstance() {
        return new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static TriggerInstance heightInstance(int minY) {
        return new TriggerInstance(Optional.empty(), Optional.of(MinMaxBounds.Ints.exactly(minY)), Optional.empty());
    }

    public static TriggerInstance effectAndHeightInstance(Holder<MobEffect> effect, int minY) {
        return new TriggerInstance(Optional.of(effect), Optional.of(MinMaxBounds.Ints.exactly(minY)), Optional.empty());
    }

    public record TriggerInstance(
            Optional<Holder<MobEffect>> effect,
            Optional<MinMaxBounds.Ints> height,
            Optional<ContextAwarePredicate> player
    ) implements SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        MobEffect.CODEC.optionalFieldOf("effect").forGetter(TriggerInstance::effect),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("height").forGetter(TriggerInstance::height),
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
                ).apply(instance, TriggerInstance::new)
        );

        public boolean matches(ServerPlayer player) {
            // 检查是否拥有反转重力效果
            if (effect.isPresent() && !player.hasEffect(effect.get())) {
                return false;
            }
            // 检查高度是否达到要求
            int y = (int) player.getY();
            return height.isEmpty() || height.get().matches(y);
        }
    }
}