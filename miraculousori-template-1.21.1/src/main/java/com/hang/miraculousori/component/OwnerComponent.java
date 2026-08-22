package com.hang.miraculousori.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record OwnerComponent(String ownerName) {
    public static final Codec<OwnerComponent> CODEC = Codec.STRING.xmap(OwnerComponent::new, OwnerComponent::ownerName);
    public static final StreamCodec<ByteBuf, OwnerComponent> STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8.map(OwnerComponent::new, OwnerComponent::ownerName);
}