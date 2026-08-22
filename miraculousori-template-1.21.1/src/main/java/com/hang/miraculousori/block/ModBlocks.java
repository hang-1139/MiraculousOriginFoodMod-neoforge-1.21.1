package com.hang.miraculousori.block;

import com.hang.miraculousori.block.custom.*;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.hang.miraculousori.MiraculousOriginFoodMod.MODID;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> SUGAR_BLOCK = registerBlocks("misc/sugar_block",
            () -> new Block(Block.Properties.of().sound(SoundType.SAND).mapColor(MapColor.SAND).strength(0.4F)));

    public static final DeferredBlock<MillBlock> MILL_BLOCK = registerBlocks("misc/mill_block",
            () -> new MillBlock(Block.Properties.of()
                    .sound(SoundType.STONE)
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
            ),
            block -> new BlockItem(block, new Item.Properties().stacksTo(64)));

    // ==================== 植物方块 ====================
    public static final DeferredBlock<WitheredSoulweedBlock> WITHERED_SOULWEED =
            BLOCKS.register("plant/withered_soulweed",
                    () -> new WitheredSoulweedBlock(Block.Properties.of()
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .mapColor(MapColor.PLANT)
                            .strength(0.0F)
                    ));

    public static final DeferredBlock<NetherSoulWheatCrop> NETHER_SOUL_WHEAT = BLOCKS.register("plant/nether_soul_wheat",
            () -> new NetherSoulWheatCrop(Block.Properties.of()
                    .noCollission()
                    .instabreak()
                    .randomTicks()
                    .sound(SoundType.CROP)
                    .mapColor(MapColor.PLANT)
                    .pushReaction(PushReaction.DESTROY)
                    .strength(0.0F)));

    public static final DeferredBlock<CrimsonVinesBlock> CRIMSON_VINES = BLOCKS.register("plant/crimson_vines",
            () -> new CrimsonVinesBlock(Block.Properties.of()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .mapColor(MapColor.COLOR_RED)
                    .strength(0.0F)
            ));

    public static final DeferredBlock<WarpedVinesHeadBlock> WARPED_VINES_HEAD =
            BLOCKS.register("plant/warped_vines_head",
                    () -> new WarpedVinesHeadBlock(Block.Properties.of()
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.CROP)
                            .mapColor(MapColor.COLOR_CYAN)
                            .lightLevel(state -> state.getValue(WarpedVines.BERRIES) ? 12 : 0)
                            .strength(0.0F)
                    ));

    public static final DeferredBlock<WarpedVinesPlantBlock> WARPED_VINES_PLANT =
            BLOCKS.register("plant/warped_vines_plant",
                    () -> new WarpedVinesPlantBlock(Block.Properties.of()
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.CROP)
                            .mapColor(MapColor.COLOR_CYAN)
                            .lightLevel(state -> state.getValue(WarpedVines.BERRIES) ? 12 : 0)
                            .strength(0.0F)
                    ));

    public static final DeferredBlock<WarpedVinePlatformBlock> WARPED_VINE_PLATFORM =
            BLOCKS.register("plant/warped_vine_platform",
                    () -> new WarpedVinePlatformBlock(Block.Properties.of()
                            .noOcclusion()
                            .strength(0.0F)
                            .sound(SoundType.WOOD)
                            .mapColor(MapColor.COLOR_CYAN)
                    ));

    // ----------  盐岩 ----------
    public static final DeferredBlock<Block> SALT_ROCK = registerBlocks("misc/salt_rock",
            () -> new Block(Block.Properties.of()
                    .sound(SoundType.STONE)
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.QUARTZ)
            ));

    public static final DeferredBlock<Block> DEEPSLATE_SALT_ROCK = registerBlocks("misc/deepslate_salt_rock",
            () -> new Block(Block.Properties.of()
                    .sound(SoundType.STONE)
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.DEEPSLATE)
            ));

    // ---------- 浮瓜块 ----------
    public static final DeferredBlock<FloatingMelonBlock> FLOATING_MELON_BLOCK = registerBlocks("plant/floating_melon_block",
            () -> new FloatingMelonBlock(Block.Properties.of()
                    .sound(SoundType.WOOD)
                    .strength(0.6F)
                    .mapColor(MapColor.COLOR_GREEN)
            )
    );

    // 浮瓜苗
    public static final DeferredBlock<FloatingMelonCropBlock> FLOATING_MELON_CROP =
            BLOCKS.register("plant/floating_melon_crop",
                    () -> new FloatingMelonCropBlock(Block.Properties.of()
                            .noCollission()
                            .instabreak()
                            .randomTicks()
                            .sound(SoundType.CROP)
                            .mapColor(MapColor.PLANT)
                            .pushReaction(PushReaction.DESTROY)
                            .strength(0.0F)
                    ));

    // ==================== 跃进果植株 ====================
    public static final DeferredBlock<LeapingFruitPlantSpreadingRootBlock> LEAPING_FRUIT_PLANT_SPREADING_ROOT =
            registerBlocks("plant/leaping_fruit_plant_spreading_root",
                    () -> new LeapingFruitPlantSpreadingRootBlock(Block.Properties.of()
                            .sound(SoundType.CROP)
                            .strength(0.6F)
                            .mapColor(MapColor.PLANT)
                            .requiresCorrectToolForDrops(),  // 添加这一行
                            0.3f, false)
            );

    public static final DeferredBlock<LeapingFruitPlantRootBlock> LEAPING_FRUIT_PLANT_ROOT =
            registerBlocks("plant/leaping_fruit_plant_root",
                    () -> new LeapingFruitPlantRootBlock(Block.Properties.of()
                            .sound(SoundType.CROP)
                            .strength(0.6F)
                            .mapColor(MapColor.PLANT)
                            .requiresCorrectToolForDrops(),  // 添加这一行
                            0.7f, false)
            );

    public static final DeferredBlock<LeapingFruitPlantTopBlock> LEAPING_FRUIT_PLANT_TOP =
            registerBlocks("plant/leaping_fruit_plant_top",
                    () -> new LeapingFruitPlantTopBlock(Block.Properties.of()
                            .sound(SoundType.CROP)
                            .strength(0.6F)
                            .mapColor(MapColor.PLANT)
                            .requiresCorrectToolForDrops(),  // 添加这一行
                            0f, true)
            );
    // ==================== 手动注册特殊物品 ====================
    static {
        ModItems.ITEMS.register("plant/warped_vine_platform", () -> new BlockItem(WARPED_VINE_PLATFORM.get(), new Item.Properties()));
    }

    // ==================== 辅助方法 ====================

    private static <T extends Block> DeferredBlock<T> registerBlocks(String name, Supplier<T> block) {
        return registerBlocks(name, block, b -> new BlockItem(b, new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlocks(String name, Supplier<T> block, Function<T, Item> itemFactory) {
        DeferredBlock<T> deferredBlock = BLOCKS.register(name, block);
        // 使用 Supplier 延迟获取，确保在注册完成后才调用 get()
        ModItems.ITEMS.register(name, () -> {
            T blockInstance = deferredBlock.get();
            return itemFactory.apply(blockInstance);
        });
        return deferredBlock;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}