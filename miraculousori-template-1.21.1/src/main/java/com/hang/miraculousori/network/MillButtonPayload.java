package com.hang.miraculousori.network;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MillButtonPayload(int tankIndex) implements CustomPacketPayload {
    public static final Type<MillButtonPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "mill_button")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, MillButtonPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeInt(payload.tankIndex),
                    buf -> new MillButtonPayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}