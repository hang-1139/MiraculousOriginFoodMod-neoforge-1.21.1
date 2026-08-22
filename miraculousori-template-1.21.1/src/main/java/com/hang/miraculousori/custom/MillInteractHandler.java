package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.advancement.ModTriggers;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.village.ModVillagerProfessions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class MillInteractHandler {

    private static final int MAX_DURATION_TICKS = 20 * 60 * 60; // 30 分钟

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Villager villager)) return;
        if (villager.getVillagerData().getProfession() != ModVillagerProfessions.MILLER.get()) return;

        Player player = event.getEntity();
        if (!player.isShiftKeyDown()) return;

        MobEffectInstance existing = villager.getEffect(ModMobEffects.WORKING);
        if (existing != null && existing.getDuration() >= MAX_DURATION_TICKS) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.FAIL);
            return;
        }

        int totalEmeralds = countEmeralds(player);
        if (totalEmeralds == 0) return;

        int consumeCount;
        int durationTicks;
        if (totalEmeralds * 20 * 60 * 3 <= MAX_DURATION_TICKS) {
            consumeCount = totalEmeralds;
        } else {
            consumeCount = 20;
        }
        durationTicks = consumeCount * 20 * 60 * 3 + 20 * 15;

        if (!consumeEmeralds(player, consumeCount)) return;

        int finalDuration = Math.min(durationTicks, MAX_DURATION_TICKS);
        villager.addEffect(new MobEffectInstance(
                ModMobEffects.WORKING,
                finalDuration,
                255,
                false,
                true,
                true
        ));

        if (player instanceof ServerPlayer serverPlayer) {
            ModTriggers.MILLER_GRIND.get().trigger(serverPlayer);
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    private static int countEmeralds(Player player) {
        int count = 0;
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        if (main.is(Items.EMERALD)) count += main.getCount();
        if (off.is(Items.EMERALD)) count += off.getCount();
        return count;
    }

    private static boolean consumeEmeralds(Player player, int count) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        int remaining = count;

        if (main.is(Items.EMERALD)) {
            int toTake = Math.min(main.getCount(), remaining);
            main.shrink(toTake);
            remaining -= toTake;
        }
        if (remaining > 0 && off.is(Items.EMERALD)) {
            int toTake = Math.min(off.getCount(), remaining);
            off.shrink(toTake);
            remaining -= toTake;
        }
        return remaining == 0;
    }
}