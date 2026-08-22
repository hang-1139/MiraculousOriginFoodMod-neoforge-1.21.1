package com.hang.miraculousori.custom;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.advancement.ModTriggers;
import com.hang.miraculousori.component.ModDataComponents;
import com.hang.miraculousori.component.OwnerComponent;
import com.hang.miraculousori.datagen.tags.ModItemTagsProvider;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.item.ModItems;
import com.hang.miraculousori.particle.TrailManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@EventBusSubscriber(modid = MiraculousOriginFoodMod.MODID)
public class ModItemAbilityHandler {
    private static final Random RANDOM = new Random();
    private static final int TICKS_PER_SECOND = 20;

    public static final TagKey<Item> ASSIMILABLE_TEMPLATE = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "assimilable_template")
    );

    private static final Map<UUID, Float> CONSUMED = new HashMap<>();
    private static final Map<UUID, Integer> RESISTANCE_LEVEL = new HashMap<>();
    private static final Map<UUID, Integer> END_NEW_PATH_GRANT_TIMER = new HashMap<>();
    private static final Map<UUID, Integer> END_NEW_PATH_BLINK_COOLDOWN = new HashMap<>();

    private static boolean isOwnedByPlayer(ItemStack stack, Player player) {
        if (stack.isEmpty()) return false;
        if (!stack.is(ModItemTagsProvider.DIVINE)) return false;
        OwnerComponent owner = stack.get(ModDataComponents.OWNER.get());
        if (owner == null) return false;
        return owner.ownerName().equals(player.getName().getString());
    }

    private static boolean isHoldingOwned(ItemStack mainHand, ItemStack offHand, Player player, Item item) {
        return (mainHand.is(item) && isOwnedByPlayer(mainHand, player)) ||
                (offHand.is(item) && isOwnedByPlayer(offHand, player));
    }

    private static boolean isHolding(ItemStack mainHand, ItemStack offHand, Item item) {
        return mainHand.is(item) || offHand.is(item);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide) return;

        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
        UUID uuid = player.getUUID();

        boolean holdingBlessing = isHoldingOwned(mainHand, offHand, player, ModItems.FOOD_GOD_BLESSING_AMULET.get());
        boolean holdingCurse = isHoldingOwned(mainHand, offHand, player, ModItems.HUNGER_CURSE_ODE.get());

        // 饱和图腾
        if (isHolding(mainHand, offHand, ModItems.SATURATION_TOTEM.get())) {
            if (player.tickCount % TICKS_PER_SECOND == 0) {
                player.getFoodData().setFoodLevel(Math.min(20, player.getFoodData().getFoodLevel() + 1));
                player.getFoodData().setSaturation(Math.min(20, player.getFoodData().getSaturationLevel() + 2));
            }
        }

        // 食神之佑
        if (holdingBlessing) {
            if (player.tickCount % TICKS_PER_SECOND == 0) {
                player.getFoodData().setFoodLevel(Math.min(20, player.getFoodData().getFoodLevel() + 5));
                player.getFoodData().setSaturation(Math.min(20, player.getFoodData().getSaturationLevel() + 7));
            }
            handleFoodGodBlessing(player);
            if (player.tickCount % TICKS_PER_SECOND == 0) {
                updateResistanceLevel(player);
            }
            int levelResist = RESISTANCE_LEVEL.getOrDefault(uuid, 0);
            if (levelResist > 0 && player.tickCount % (19 * TICKS_PER_SECOND) == 0) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * TICKS_PER_SECOND, levelResist - 1));
            }
        } else {
            CONSUMED.remove(uuid);
            RESISTANCE_LEVEL.remove(uuid);
        }

        // 饥馑图腾
        if (isHolding(mainHand, offHand, ModItems.HUNGER_TOTEM.get())) {
            if (player.tickCount % TICKS_PER_SECOND == 0) {
                player.getFoodData().setFoodLevel(Math.max(0, player.getFoodData().getFoodLevel() - 1));
            }
        }

        // 饥馑诅颂
        if (holdingCurse) {
            if (player.tickCount % TICKS_PER_SECOND == 0) {
                player.getFoodData().setFoodLevel(Math.max(0, player.getFoodData().getFoodLevel() - 5));
            }
            if (player.tickCount % (19 * TICKS_PER_SECOND) == 0) {
                applyHungerCurseEffects(player);
            }
        }

        // 末途符文（无所有者限制）
        if (isHolding(mainHand, offHand, ModItems.END_ROAD_RUNE.get())) {
            if (player.tickCount % (30 * TICKS_PER_SECOND) == 0) {
                player.addEffect(new MobEffectInstance(ModMobEffects.TELEPORT_ON_DAMAGE, 10 * TICKS_PER_SECOND, 0, false, true));
            }
        }

        // 终幕新途（必须为自己所有）
        if (isHoldingOwned(mainHand, offHand, player, ModItems.END_NEW_PATH.get())) {
            int grantTimer = END_NEW_PATH_GRANT_TIMER.getOrDefault(uuid, 0);
            if (grantTimer <= 0) {
                player.addEffect(new MobEffectInstance(ModMobEffects.TELEPORT_ON_DAMAGE, 10 * TICKS_PER_SECOND, 0, false, true));
                grantTimer = 25 * TICKS_PER_SECOND;
            } else {
                grantTimer--;
            }
            END_NEW_PATH_GRANT_TIMER.put(uuid, grantTimer);

            int blinkCooldown = END_NEW_PATH_BLINK_COOLDOWN.getOrDefault(uuid, 0);
            if (blinkCooldown > 0) {
                blinkCooldown--;
                END_NEW_PATH_BLINK_COOLDOWN.put(uuid, blinkCooldown);
            }
        } else {
            END_NEW_PATH_GRANT_TIMER.remove(uuid);
            END_NEW_PATH_BLINK_COOLDOWN.remove(uuid);
        }

        // 组合清除效果
        if (holdingBlessing && holdingCurse) {
            player.removeAllEffects();
        }

        // 组合雷暴
        handleCurseAndBlessingCombo(player, mainHand, offHand);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide) return;
        if (player.isSpectator()) return;

        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);

        boolean hasAssimilable = mainHand.is(ASSIMILABLE_TEMPLATE) || offHand.is(ASSIMILABLE_TEMPLATE);
        boolean hasDivine = mainHand.is(ModItemTagsProvider.DIVINE) || offHand.is(ModItemTagsProvider.DIVINE);
        if (!hasAssimilable || !hasDivine) return;

        boolean isValid = (mainHand.is(ASSIMILABLE_TEMPLATE) && offHand.is(ModItemTagsProvider.DIVINE)) ||
                (mainHand.is(ModItemTagsProvider.DIVINE) && offHand.is(ASSIMILABLE_TEMPLATE));
        if (!isValid) return;

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(0, 6000, true, true);
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
            lightning.setPos(player.getX(), player.getY(), player.getZ());
            lightning.setDamage(5.0F);
            serverLevel.addFreshEntity(lightning);

            ItemStack targetStack = mainHand.is(ASSIMILABLE_TEMPLATE) ? mainHand : offHand;
            if (!player.isCreative()) {
                targetStack.shrink(1);
                if (targetStack.isEmpty()) {
                    player.setItemInHand(
                            targetStack == mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                            ItemStack.EMPTY
                    );
                }
            }

            ItemStack divineTemplate = new ItemStack(ModItems.DIVINE_UPGRADE_TEMPLATE.get());
            if (!player.getInventory().add(divineTemplate)) {
                player.drop(divineTemplate, false);
            }
        }
        event.setCanceled(true);
    }

    private static void handleFoodGodBlessing(Player player) {
        if (player.getHealth() <= 0) return;
        float health = player.getHealth();
        float maxHealth = player.getMaxHealth();
        if (health >= maxHealth) return;

        int food = player.getFoodData().getFoodLevel();
        float saturation = player.getFoodData().getSaturationLevel();
        float total = food + saturation;
        float missingHealth = maxHealth - health;
        float required = missingHealth * 2;

        float consumed = 0f;

        if (total >= required) {
            float toConsume = required;
            float saturationConsume = Math.min(saturation, toConsume);
            saturation -= saturationConsume;
            toConsume -= saturationConsume;
            int foodConsume = (int) Math.min(food, toConsume);
            food -= foodConsume;
            toConsume -= foodConsume;
            if (toConsume > 0) {
                food = Math.max(0, (int) (food - toConsume));
            }
            consumed = required;
            player.getFoodData().setFoodLevel(Math.max(0, food));
            player.getFoodData().setSaturation(Math.max(0, saturation));
            player.setHealth(maxHealth);
        } else {
            float healAmount = total / 2.0F;
            consumed = total;
            player.getFoodData().setFoodLevel(0);
            player.getFoodData().setSaturation(0);
            player.setHealth(Math.min(maxHealth, health + healAmount));
        }

        if (consumed > 0) {
            UUID uuid = player.getUUID();
            CONSUMED.merge(uuid, consumed, Float::sum);
        }
    }

    private static void updateResistanceLevel(Player player) {
        UUID uuid = player.getUUID();
        float consumed = CONSUMED.getOrDefault(uuid, 0f);
        int level = 0;
        if (consumed > 20) {
            level = (int) Math.floor((consumed - 20) / 40);
            level = Math.min(level, 4);
        }
        RESISTANCE_LEVEL.put(uuid, level);
    }

    private static void applyHungerCurseEffects(Player player) {
        player.addEffect(new MobEffectInstance(ModMobEffects.HUNGER_AFFLICTION, 20 * 20, 0));
        if (RANDOM.nextFloat() < 0.5F) {
            player.addEffect(new MobEffectInstance(ModMobEffects.HUNGER_AFFLICTION, 20 * 20, 1));
        }
        if (RANDOM.nextFloat() < 0.1F) {
            player.addEffect(new MobEffectInstance(ModMobEffects.HUNGER_AFFLICTION, 20 * 20, 4));
        }
    }

    private static void handleCurseAndBlessingCombo(Player player, ItemStack mainHand, ItemStack offHand) {
        boolean hasCombo = (mainHand.is(ModItems.HUNGER_CURSE_ODE.get()) && isOwnedByPlayer(mainHand, player) &&
                offHand.is(ModItems.FOOD_GOD_BLESSING_AMULET.get()) && isOwnedByPlayer(offHand, player)) ||
                (mainHand.is(ModItems.FOOD_GOD_BLESSING_AMULET.get()) && isOwnedByPlayer(mainHand, player) &&
                        offHand.is(ModItems.HUNGER_CURSE_ODE.get()) && isOwnedByPlayer(offHand, player));
        if (!hasCombo) return;
        if (player.isCreative() || player.isSpectator()) return;
        if (player.tickCount % 2 != 0) return;

        if (player instanceof ServerPlayer serverPlayer) {
            ModTriggers.HOLDING_BOTH.get().trigger(serverPlayer);
        }

        Level level = player.level();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(0, 6000, true, true);
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
            lightning.setPos(player.getX(), player.getY(), player.getZ());
            lightning.setDamage(5.0F);
            serverLevel.addFreshEntity(lightning);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        LivingEntity victim = event.getEntity();

        // 法棍伤害加成
        if (attacker instanceof Player player && !player.level().isClientSide) {
            ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);

            float bonus = 0;
            if (isHolding(mainHand, offHand, ModItems.BAGUETTE.get())) bonus += 6.0F;
            if (isHolding(mainHand, offHand, ModItems.LARGE_BAGUETTE_HALF.get())) bonus += 4.0F;
            if (isHolding(mainHand, offHand, ModItems.SMALL_BAGUETTE_HALF.get())) bonus += 2.0F;
            if (bonus > 0) {
                event.setNewDamage(event.getNewDamage() + bonus);
            }
        }

        // 终幕新途：玩家受伤害（必须为自己所有）
        if (victim instanceof Player playerVictim && !playerVictim.level().isClientSide) {
            if (isHoldingOwned(playerVictim.getMainHandItem(), playerVictim.getOffhandItem(), playerVictim, ModItems.END_NEW_PATH.get())) {
                UUID uuid = playerVictim.getUUID();

                if (playerVictim.hasEffect(ModMobEffects.TELEPORT_ON_DAMAGE)) {
                    int grantTimer = END_NEW_PATH_GRANT_TIMER.getOrDefault(uuid, 25 * TICKS_PER_SECOND);
                    int reduce = (3 + RANDOM.nextInt(5)) * TICKS_PER_SECOND;
                    grantTimer = Math.max(0, grantTimer - reduce);
                    END_NEW_PATH_GRANT_TIMER.put(uuid, grantTimer);
                }

                int blinkCooldown = END_NEW_PATH_BLINK_COOLDOWN.getOrDefault(uuid, 0);
                if (blinkCooldown > 0) {
                    blinkCooldown = Math.max(0, blinkCooldown - TICKS_PER_SECOND);
                    END_NEW_PATH_BLINK_COOLDOWN.put(uuid, blinkCooldown);
                }
            }
        }

        // 终幕新途：玩家造成伤害 → 瞬移（必须为自己所有）
        if (attacker instanceof Player playerAttacker && !playerAttacker.level().isClientSide) {
            if (victim == playerAttacker) return;
            if (isHoldingOwned(playerAttacker.getMainHandItem(), playerAttacker.getOffhandItem(), playerAttacker, ModItems.END_NEW_PATH.get())) {
                UUID uuid = playerAttacker.getUUID();
                int blinkCooldown = END_NEW_PATH_BLINK_COOLDOWN.getOrDefault(uuid, 0);
                if (blinkCooldown == 0) {
                    teleportToTargetBehind(playerAttacker, victim);
                    END_NEW_PATH_BLINK_COOLDOWN.put(uuid, 5 * TICKS_PER_SECOND);
                }
            }
        }
    }

    // 瞬移方法（含粒子效果）
    private static void teleportToTargetBehind(Player player, Entity target) {
        if (target == null) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        Vec3 oldPos = serverPlayer.position(); // 记录旧位置

        Vec3 lookVec = target.getLookAngle();
        Vec3 behind = lookVec.reverse().scale(1.5);
        Vec3 pos = target.position().add(behind).add(0, 0.5, 0);

        // 传送
        serverPlayer.teleportTo(pos.x, pos.y, pos.z);
        serverPlayer.syncPacketPositionCodec(pos.x, pos.y, pos.z);

        // 计算朝向目标的角度
        Vec3 delta = target.position().subtract(pos);
        double horizontalDist = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        float yaw = (float) (Math.atan2(delta.x, delta.z) * 180.0 / Math.PI);
        float pitch = (float) (-Math.atan2(delta.y, horizontalDist) * 180.0 / Math.PI);
        serverPlayer.setYRot(yaw);
        serverPlayer.setXRot(pitch);

        // 生成跃进粒子轨迹
        Vec3 newPos = serverPlayer.position();
        TrailManager.spawnLineParticles(serverPlayer, oldPos, newPos, 6);
    }

    @SubscribeEvent
    public static void onEntityHurtByPlayer(LivingDamageEvent.Post event) {
        DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide) return;

        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!isHoldingOwned(mainHand, offHand, player, ModItems.HUNGER_CURSE_ODE.get())) return;

        float damage = event.getNewDamage();
        if (damage > 0 && player.getHealth() > 0) {
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + damage * 0.4F));
        }
    }
}