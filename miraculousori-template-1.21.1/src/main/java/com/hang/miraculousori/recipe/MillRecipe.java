package com.hang.miraculousori.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MillRecipe implements Recipe<RecipeInput> {
    private final Ingredient ingredient;
    private final int ingredientCount;
    private final List<ItemStack> outputs;
    private final String liquidOutput;
    private final int liquidAmount;
    private final float experience;
    private final int grindingTime;
    private final int foodCost;
    private final String group;

    public static final MapCodec<MillRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(r -> r.ingredient),
                    Codec.INT.fieldOf("ingredient_count").orElse(1).forGetter(r -> r.ingredientCount),
                    ItemStack.CODEC.listOf().fieldOf("outputs").forGetter(r -> r.outputs),
                    Codec.STRING.optionalFieldOf("liquid_output", "").forGetter(r -> r.liquidOutput),
                    Codec.INT.optionalFieldOf("liquid_amount", 0).forGetter(r -> r.liquidAmount),
                    Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(r -> r.experience),
                    Codec.INT.fieldOf("grinding_time").forGetter(r -> r.grindingTime),
                    Codec.INT.fieldOf("food_cost").forGetter(r -> r.foodCost),
                    Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group)
            ).apply(instance, MillRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, MillRecipe> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public MillRecipe decode(RegistryFriendlyByteBuf buf) {
                    Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                    int ingredientCount = buf.readVarInt();
                    int outputCount = buf.readVarInt();
                    List<ItemStack> outputs = new ArrayList<>();
                    for (int i = 0; i < outputCount; i++) {
                        outputs.add(ItemStack.OPTIONAL_STREAM_CODEC.decode(buf));
                    }
                    String liquidOutput = buf.readUtf();
                    int liquidAmount = buf.readVarInt();
                    float experience = buf.readFloat();
                    int grindingTime = buf.readVarInt();
                    int foodCost = buf.readVarInt();
                    String group = buf.readUtf();
                    return new MillRecipe(ingredient, ingredientCount, outputs, liquidOutput, liquidAmount,
                            experience, grindingTime, foodCost, group);
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf, MillRecipe recipe) {
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
                    buf.writeVarInt(recipe.ingredientCount);
                    buf.writeVarInt(recipe.outputs.size());
                    for (ItemStack stack : recipe.outputs) {
                        ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack);
                    }
                    buf.writeUtf(recipe.liquidOutput);
                    buf.writeVarInt(recipe.liquidAmount);
                    buf.writeFloat(recipe.experience);
                    buf.writeVarInt(recipe.grindingTime);
                    buf.writeVarInt(recipe.foodCost);
                    buf.writeUtf(recipe.group);
                }
            };

    public MillRecipe(Ingredient ingredient, int ingredientCount, List<ItemStack> outputs,
                      String liquidOutput, int liquidAmount, float experience,
                      int grindingTime, int foodCost, String group) {
        this.ingredient = ingredient;
        this.ingredientCount = ingredientCount;
        this.outputs = outputs;
        this.liquidOutput = liquidOutput;
        this.liquidAmount = liquidAmount;
        this.experience = experience;
        this.grindingTime = grindingTime;
        this.foodCost = foodCost;
        this.group = group;
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        ItemStack stack = input.getItem(0);
        return !stack.isEmpty() && ingredient.test(stack) && stack.getCount() >= ingredientCount;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.get(0).copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MILL.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.MILL.get();
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        return list;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    // Getters
    public Ingredient getIngredient() { return ingredient; }
    public int getIngredientCount() { return ingredientCount; }
    public List<ItemStack> getOutputs() { return outputs; }
    public String getLiquidOutput() { return liquidOutput; }
    public int getLiquidAmount() { return liquidAmount; }
    public float getExperience() { return experience; }
    public int getGrindingTime() { return grindingTime; }
    public int getFoodCost() { return foodCost; }
}