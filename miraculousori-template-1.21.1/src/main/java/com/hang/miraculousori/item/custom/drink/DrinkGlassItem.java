package com.hang.miraculousori.item.custom.drink;

import com.hang.miraculousori.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public class DrinkGlassItem extends Item {
    private final int nutrition;
    private final float saturation;
    private final List<FoodProperties.PossibleEffect> effects;

    public DrinkGlassItem(Properties properties, int nutrition, float saturation, List<FoodProperties.PossibleEffect> effects) {
        super(properties);
        this.nutrition = nutrition;
        this.saturation = saturation;
        this.effects = effects;
    }

    @Override
    public FoodProperties getFoodProperties(ItemStack stack, LivingEntity entity) {
        FoodProperties.Builder builder = new FoodProperties.Builder()
                .nutrition(this.nutrition)
                .saturationModifier(this.saturation)
                .alwaysEdible();
        for (FoodProperties.PossibleEffect effect : this.effects) {
            builder.effect(effect.effect(), effect.probability());
        }
        return builder.build();
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 60;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            player.getFoodData().eat(this.nutrition, this.saturation);
            for (FoodProperties.PossibleEffect possible : this.effects) {
                MobEffectInstance effect = possible.effect();
                float chance = possible.probability();
                if (chance >= 1.0F || level.random.nextFloat() < chance) {
                    if (effect != null) {
                        player.addEffect(new MobEffectInstance(effect));
                    }
                }
            }
            if (!player.isCreative()) {
                ItemStack container = new ItemStack(ModItems.GLASS_CUP.get());
                if (stack.getCount() == 1) {
                    return container;
                } else {
                    player.getInventory().add(container);
                    stack.shrink(1);
                    return stack;
                }
            } else {
                return stack;
            }
        }
        return stack;
    }
}