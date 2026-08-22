package com.hang.miraculousori.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record PotionEnhancementData(int upgradeCount, int extendCount) {
    public static final PotionEnhancementData EMPTY = new PotionEnhancementData(0, 0);
    public static final Codec<PotionEnhancementData> CODEC = Codec.INT.xmap(
            i -> new PotionEnhancementData(i >> 4, i & 0xF),
            data -> (data.upgradeCount() << 4) | data.extendCount()
    );
    public static final StreamCodec<ByteBuf, PotionEnhancementData> STREAM_CODEC =
            ByteBufCodecs.INT.map(
                    i -> new PotionEnhancementData(i >> 4, i & 0xF),
                    data -> (data.upgradeCount() << 4) | data.extendCount()
            );

    public int getTotal() { return upgradeCount + extendCount; }
    public boolean canUpgrade() { return upgradeCount < 3 && getTotal() < 5; }
    public boolean canExtend() { return extendCount < 3 && getTotal() < 5; }
    public PotionEnhancementData withUpgrade() {
        if (!canUpgrade()) return this;
        return new PotionEnhancementData(upgradeCount + 1, extendCount);
    }
    public PotionEnhancementData withExtend() {
        if (!canExtend()) return this;
        return new PotionEnhancementData(upgradeCount, extendCount + 1);
    }

    // 根据等级和延时次数计算新的时长（基础3分钟，每次延时×1.5）
    public int getDurationTicks(int baseDuration) {
        double factor = Math.pow(1.5, extendCount);
        return (int)(baseDuration * factor);
    }
}