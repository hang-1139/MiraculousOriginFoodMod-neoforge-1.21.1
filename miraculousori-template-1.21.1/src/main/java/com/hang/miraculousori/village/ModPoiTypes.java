package com.hang.miraculousori.village;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.stream.Collectors;

public class ModPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, MiraculousOriginFoodMod.MODID);

    // 磨台 POI：将磨台的所有方块状态添加为有效工作站点
    public static final DeferredHolder<PoiType, PoiType> MILL_POI =
            POI_TYPES.register("mill_poi",
                    () -> new PoiType(
                            Set.copyOf(
                                    ModBlocks.MILL_BLOCK.get().getStateDefinition().getPossibleStates()
                            ),
                            1, // 匹配距离
                            1  // 占用数量
                    ));
}