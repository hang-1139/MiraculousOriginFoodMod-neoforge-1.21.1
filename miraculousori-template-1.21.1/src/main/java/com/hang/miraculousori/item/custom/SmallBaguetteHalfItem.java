package com.hang.miraculousori.item.custom;

import net.minecraft.world.item.Item;

public class SmallBaguetteHalfItem extends Item {
    public SmallBaguetteHalfItem(Properties properties) {
        super(properties);
    }
    // 无需重写 finishUsingItem，直接继承父类行为（吃完无返还）
}