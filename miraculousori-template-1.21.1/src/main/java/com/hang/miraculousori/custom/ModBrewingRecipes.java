package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.item.ModItems;
import com.hang.miraculousori.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModBrewingRecipes {

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        // ===== 基础药水酿造：粗制药水 + 熟土豆饼 = 振奋药水 =====
        // 直接传入 DeferredHolder，自动转为 Holder<Potion>
        builder.addMix(Potions.AWKWARD, ModItems.COOKED_POTATO_PATTY.get(), ModPotions.INVIGORATE_POTION);

        // ===== 延时酿造 =====
        builder.addMix(ModPotions.INVIGORATE_POTION, Items.REDSTONE, ModPotions.INVIGORATE_LONG);
        builder.addMix(ModPotions.INVIGORATE_LONG, Items.REDSTONE, ModPotions.INVIGORATE_LONGER);
        builder.addMix(ModPotions.INVIGORATE_LONGER, Items.REDSTONE, ModPotions.INVIGORATE_LONGEST);

        // ===== 升级酿造 =====
        builder.addMix(ModPotions.INVIGORATE_POTION, Items.GLOWSTONE_DUST, ModPotions.INVIGORATE_STRONG);
        builder.addMix(ModPotions.INVIGORATE_STRONG, Items.GLOWSTONE_DUST, ModPotions.INVIGORATE_STRONGER);
        builder.addMix(ModPotions.INVIGORATE_STRONGER, Items.GLOWSTONE_DUST, ModPotions.INVIGORATE_STRONGEST);

        // ===== 组合酿造（先延后升） =====
        builder.addMix(ModPotions.INVIGORATE_LONG, Items.GLOWSTONE_DUST, ModPotions.INVIGORATE_LONG_STRONG);
        builder.addMix(ModPotions.INVIGORATE_LONGER, Items.GLOWSTONE_DUST, ModPotions.INVIGORATE_LONGER_STRONGER);
        builder.addMix(ModPotions.INVIGORATE_LONGEST, Items.GLOWSTONE_DUST, ModPotions.INVIGORATE_LONGEST_STRONGEST);
    }
}