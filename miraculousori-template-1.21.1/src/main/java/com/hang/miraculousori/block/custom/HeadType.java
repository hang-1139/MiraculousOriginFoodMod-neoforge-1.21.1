package com.hang.miraculousori.block.custom;

import net.minecraft.util.StringRepresentable;

public enum HeadType implements StringRepresentable {
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM_NO_PLATFORM("bottom_no_platform"),
    BOTTOM_WITH_PLATFORM("bottom_with_platform");

    private final String name;

    HeadType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}