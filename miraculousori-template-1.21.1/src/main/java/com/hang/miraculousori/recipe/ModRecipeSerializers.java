package com.hang.miraculousori.recipe;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MiraculousOriginFoodMod.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MillRecipe>> MILL =
            RECIPE_SERIALIZERS.register("mill",
                    () -> new RecipeSerializer<>() {
                        @Override
                        public MapCodec<MillRecipe> codec() {
                            return MillRecipe.CODEC;
                        }

                        @Override
                        public StreamCodec<RegistryFriendlyByteBuf, MillRecipe> streamCodec() {
                            return MillRecipe.STREAM_CODEC;
                        }
                    });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<LiquidFillingRecipe>> LIQUID_FILLING =
            RECIPE_SERIALIZERS.register("liquid_filling",
                    () -> new RecipeSerializer<>() {
                        @Override
                        public MapCodec<LiquidFillingRecipe> codec() {
                            return LiquidFillingRecipe.CODEC;
                        }

                        @Override
                        public StreamCodec<RegistryFriendlyByteBuf, LiquidFillingRecipe> streamCodec() {
                            return LiquidFillingRecipe.STREAM_CODEC;
                        }
                    });
}