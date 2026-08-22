package com.hang.miraculousori.datagen.loot;

import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.block.custom.NetherSoulWheatCrop;
import com.hang.miraculousori.block.custom.WarpedVines;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider {
    public ModBlockLootTablesProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // ----- 糖块 -----
        add(ModBlocks.SUGAR_BLOCK.get(),
                createSilkTouchDispatchTable(ModBlocks.SUGAR_BLOCK.get(),
                        LootItem.lootTableItem(ModItems.SUGAR_CUBE.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)))));

        // ----- 枯魂野草 -----
        add(ModBlocks.WITHERED_SOULWEED.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.NETHER_SOUL_WHEAT_SEEDS.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        )
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.WITHERED_SOULWEED.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                )
                        )
                )
        );

        // ----- 下界缠魂麦 -----
        add(ModBlocks.NETHER_SOUL_WHEAT.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.NETHER_SOUL_WHEAT_SEEDS.get()))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.NETHER_SOUL_WHEAT.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(NetherSoulWheatCrop.HALF, DoubleBlockHalf.LOWER)
                                )
                        )
                )
        );

        // ----- 猩红藤蔓 -----
        add(ModBlocks.CRIMSON_VINES.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.CRIMSON_FRUIT.get()))
                )
        );

        // ----- 畸形藤蔓平台（无掉落）-----
        add(ModBlocks.WARPED_VINE_PLATFORM.get(), LootTable.lootTable());

        // ----- 畸形藤蔓（浆果）-----
        add(ModBlocks.WARPED_VINES_HEAD.get(), createBerriesDropTable(ModBlocks.WARPED_VINES_HEAD.get()));
        add(ModBlocks.WARPED_VINES_PLANT.get(), createBerriesDropTable(ModBlocks.WARPED_VINES_PLANT.get()));

        // ----- 磨台 -----
        add(ModBlocks.MILL_BLOCK.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModBlocks.MILL_BLOCK.get())
                                .when(MatchTool.toolMatches(
                                        ItemPredicate.Builder.item().of(net.minecraft.tags.ItemTags.PICKAXES)
                                ))
                        )
                )
        );

        Holder<Enchantment> fortune = this.registries.lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.FORTUNE);

        // ----- 盐岩 -----
        add(ModBlocks.SALT_ROCK.get(),
                createSilkTouchDispatchTable(ModBlocks.SALT_ROCK.get(),
                        LootItem.lootTableItem(ModItems.CRUSHED_SALT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F)))
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                        fortune, 1.0F, 0
                                ))
                )
        );

        // ----- 深层盐岩 -----
        add(ModBlocks.DEEPSLATE_SALT_ROCK.get(),
                createSilkTouchDispatchTable(ModBlocks.DEEPSLATE_SALT_ROCK.get(),
                        LootItem.lootTableItem(ModItems.CRUSHED_SALT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F)))
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                        fortune, 1.0F, 0
                                ))
                )
        );

        // ----- 浮瓜块（无掉落，由代码控制）-----
        add(ModBlocks.FLOATING_MELON_BLOCK.get(), LootTable.lootTable());

        // ----- 浮瓜苗（种子）-----
        add(ModBlocks.FLOATING_MELON_CROP.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FLOATING_MELON_SEEDS.get()))
                )
        );

        // ==================== 跃进果植株方块（掉落自身） ====================
        add(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get(), createSingleItemTable(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get()));
        add(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get(), createSingleItemTable(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get()));
        add(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get(), createSingleItemTable(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    private LootTable.Builder createBerriesDropTable(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.WARPED_FRUIT.get())
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(WarpedVines.BERRIES, true)
                                        )
                                )
                        )
                );
    }
}