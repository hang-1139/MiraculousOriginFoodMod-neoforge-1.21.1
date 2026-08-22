package com.hang.miraculousori.datagen.tags;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MiraculousOriginFoodMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.MILL_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SALT_ROCK.get())
                .add(ModBlocks.DEEPSLATE_SALT_ROCK.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.SALT_ROCK.get())
                .add(ModBlocks.DEEPSLATE_SALT_ROCK.get());

        // 跃进果植株 - 仅非裸露方块，使用镐子，石镐以上
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get())
                 .add(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get())
                 .add(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.LEAPING_FRUIT_PLANT_SPREADING_ROOT.get())
                .add(ModBlocks.LEAPING_FRUIT_PLANT_ROOT.get())
                .add(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get());
    }
}