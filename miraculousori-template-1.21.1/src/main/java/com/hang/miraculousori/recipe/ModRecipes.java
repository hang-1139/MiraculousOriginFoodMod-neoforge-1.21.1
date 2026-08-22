package com.hang.miraculousori.recipe;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MiraculousOriginFoodMod.MODID);

    public static final Supplier<RecipeSerializer<VerySweetBreadRecipe>> VERY_SWEET_BREAD_SERIALIZER =
            RECIPE_SERIALIZERS.register("very_sweet_bread", () -> new RecipeSerializer<>() {
                @Override
                public MapCodec<VerySweetBreadRecipe> codec() {
                    return CraftingBookCategory.CODEC.fieldOf("category")
                            .xmap(VerySweetBreadRecipe::new, VerySweetBreadRecipe::category);
                }

                @Override
                public StreamCodec<RegistryFriendlyByteBuf, VerySweetBreadRecipe> streamCodec() {
                    return new StreamCodec<>() {
                        @Override
                        public void encode(RegistryFriendlyByteBuf buf, VerySweetBreadRecipe recipe) {
                            buf.writeEnum(recipe.category());
                        }

                        @Override
                        public VerySweetBreadRecipe decode(RegistryFriendlyByteBuf buf) {
                            return new VerySweetBreadRecipe(buf.readEnum(CraftingBookCategory.class));
                        }
                    };
                }
            });
}