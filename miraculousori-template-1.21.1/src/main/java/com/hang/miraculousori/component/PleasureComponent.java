package com.hang.miraculousori.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PleasureComponent(int duration, int amplifier) {
    public static final Codec<PleasureComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("duration").forGetter(PleasureComponent::duration),
                    Codec.INT.fieldOf("amplifier").forGetter(PleasureComponent::amplifier)
            ).apply(instance, PleasureComponent::new)
    );
}