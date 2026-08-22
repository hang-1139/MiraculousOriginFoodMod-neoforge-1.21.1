package com.hang.miraculousori.fluid;

import java.util.HashMap;
import java.util.Map;

public class ModFluids {
    // 液体类型标识符
    public static final String WATER = "minecraft:water";
    public static final String APPLE_JUICE = "miraculousori:apple_juice";
    public static final String WATERMELON_JUICE = "miraculousori:watermelon_juice";
    public static final String CARROT_JUICE = "miraculousori:carrot_juice";
    public static final String SUGARCANE_JUICE = "miraculousori:sugarcane_juice";
    public static final String BEETROOT_JUICE = "miraculousori:beetroot_juice";
    public static final String SWEET_BERRY_JUICE = "miraculousori:sweet_berry_juice";
    public static final String GLOW_BERRY_JUICE = "miraculousori:glow_berry_juice";
    public static final String COCOA_BUTTER = "miraculousori:cocoa_butter";

    // 容器容量
    public static final int BUCKET_CAPACITY = 1000;
    public static final int GLASS_CUP_CAPACITY = 500;
    public static final int GLASS_BOTTLE_CAPACITY = 250;
    public static final int MOLD_CAPACITY = 250;

    // ========== 新增：液体 ID 映射（用于网络同步） ==========
    public static final int FLUID_ID_NONE = 0;
    public static final int FLUID_ID_WATER = 1;
    public static final int FLUID_ID_APPLE_JUICE = 2;
    public static final int FLUID_ID_WATERMELON_JUICE = 3;
    public static final int FLUID_ID_CARROT_JUICE = 4;
    public static final int FLUID_ID_SUGARCANE_JUICE = 5;
    public static final int FLUID_ID_BEETROOT_JUICE = 6;
    public static final int FLUID_ID_SWEET_BERRY_JUICE = 7;
    public static final int FLUID_ID_GLOW_BERRY_JUICE = 8;
    public static final int FLUID_ID_COCOA_BUTTER = 9;

    private static final Map<String, Integer> FLUID_TO_ID = new HashMap<>();
    private static final Map<Integer, String> ID_TO_FLUID = new HashMap<>();

    static {
        FLUID_TO_ID.put(WATER, FLUID_ID_WATER);
        FLUID_TO_ID.put(APPLE_JUICE, FLUID_ID_APPLE_JUICE);
        FLUID_TO_ID.put(WATERMELON_JUICE, FLUID_ID_WATERMELON_JUICE);
        FLUID_TO_ID.put(CARROT_JUICE, FLUID_ID_CARROT_JUICE);
        FLUID_TO_ID.put(SUGARCANE_JUICE, FLUID_ID_SUGARCANE_JUICE);
        FLUID_TO_ID.put(BEETROOT_JUICE, FLUID_ID_BEETROOT_JUICE);
        FLUID_TO_ID.put(SWEET_BERRY_JUICE, FLUID_ID_SWEET_BERRY_JUICE);
        FLUID_TO_ID.put(GLOW_BERRY_JUICE, FLUID_ID_GLOW_BERRY_JUICE);
        FLUID_TO_ID.put(COCOA_BUTTER, FLUID_ID_COCOA_BUTTER);

        for (Map.Entry<String, Integer> entry : FLUID_TO_ID.entrySet()) {
            ID_TO_FLUID.put(entry.getValue(), entry.getKey());
        }
    }

    public static int getFluidId(String fluidType) {
        if (fluidType == null || fluidType.isEmpty()) return FLUID_ID_NONE;
        return FLUID_TO_ID.getOrDefault(fluidType, FLUID_ID_NONE);
    }

    public static String getFluidType(int id) {
        return ID_TO_FLUID.getOrDefault(id, "");
    }
}