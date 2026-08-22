package com.hang.miraculousori.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class LiquidFillingRecipe implements Recipe<LiquidFillingRecipeInput> {
    private final Ingredient container;
    private final String fluid;
    private final int fluidAmount;
    private final ItemStack result;

    public LiquidFillingRecipe(Ingredient container, String fluid, int fluidAmount, ItemStack result) {
        this.container = container;
        this.fluid = fluid;
        this.fluidAmount = fluidAmount;
        this.result = result;
    }

    @Override
    public boolean matches(LiquidFillingRecipeInput input, Level level) {
        if (!container.test(input.container())) return false;
        return input.fluid().equals(fluid);
    }

    @Override
    public ItemStack assemble(LiquidFillingRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.LIQUID_FILLING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.LIQUID_FILLING.get();
    }

    public Ingredient getContainer() { return container; }
    public String getFluid() { return fluid; }
    public int getFluidAmount() { return fluidAmount; }
    public ItemStack getResult() { return result; }

    public static final MapCodec<LiquidFillingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC_NONEMPTY.fieldOf("container").forGetter(r -> r.container),
                    Codec.STRING.fieldOf("fluid").forGetter(r -> r.fluid),
                    Codec.INT.fieldOf("fluid_amount").forGetter(r -> r.fluidAmount),
                    ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result)
            ).apply(instance, LiquidFillingRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, LiquidFillingRecipe> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public LiquidFillingRecipe decode(RegistryFriendlyByteBuf buf) {
                    Ingredient container = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                    String fluid = buf.readUtf();
                    int fluidAmount = buf.readVarInt();
                    ItemStack result = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
                    return new LiquidFillingRecipe(container, fluid, fluidAmount, result);
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf, LiquidFillingRecipe recipe) {
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.container);
                    buf.writeUtf(recipe.fluid);
                    buf.writeVarInt(recipe.fluidAmount);
                    ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, recipe.result);
                }
            };
}