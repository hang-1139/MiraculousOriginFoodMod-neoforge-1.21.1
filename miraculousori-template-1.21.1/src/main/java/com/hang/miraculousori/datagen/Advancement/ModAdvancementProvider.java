package com.hang.miraculousori.datagen.Advancement;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.advancement.*;
import com.hang.miraculousori.block.ModBlocks;
import com.hang.miraculousori.effect.ModMobEffects;
import com.hang.miraculousori.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper fileHelper) {
        super(output, registries, fileHelper, List.of(new ModAdvancementGenerator()));
    }

    public static class ModAdvancementGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper fileHelper) {

            // ============================================================
            // 根节点：芥源奇食
            // ============================================================
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(ModItems.WET_CLAY_BALL.get(),
                            Component.translatable("advancements.miraculousori.root.title"),
                            Component.translatable("advancements.miraculousori.root.description"),
                            ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID,
                                    "textures/gui/advancements/backgrounds/mill_top.png"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("has_clay_ball", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "root"));

            // ============================================================
            // 主世界进度树
            // ============================================================

            // 子节点：食物？
            AdvancementHolder foodQ = Advancement.Builder.advancement()
                    .parent(root)
                    .display(ModItems.COOKED_CLAY_CAKE.get(),
                            Component.translatable("advancements.miraculousori.food_question.title"),
                            Component.translatable("advancements.miraculousori.food_question.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_veggie_cake", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.VEGETABLE_COOKED_CLAY_CAKE.get()))
                    .addCriterion("has_sweet_cake", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SWEET_COOKED_CLAY_CAKE.get()))
                    .addCriterion("has_cooked_cake", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKED_CLAY_CAKE.get()))
                    .addCriterion("has_ender_cake", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ENDER_COOKED_CLAY_CAKE.get()))
                    .addCriterion("has_nether_cake", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHER_CLAY_CAKE.get()))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "food_question"));

            // 压榨剩余渣汁
            AdvancementHolder pressResidue = Advancement.Builder.advancement()
                    .parent(foodQ)
                    .display(ModBlocks.MILL_BLOCK.get(),
                            Component.translatable("advancements.miraculousori.press_residue.title"),
                            Component.translatable("advancements.miraculousori.press_residue.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("press_residue", PressResidueTrigger.criterion())
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "press_residue"));

            // 有钱能使鬼推磨
            AdvancementHolder moneyAdv = Advancement.Builder.advancement()
                    .parent(pressResidue)
                    .display(Items.EMERALD,
                            Component.translatable("advancements.miraculousori.money_makes_the_miller_grind.title"),
                            Component.translatable("advancements.miraculousori.money_makes_the_miller_grind.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("miller_grind", MillerGrindTrigger.criterion())
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "money_makes_the_miller_grind"));

            // 鸡蛋壳呢？
            AdvancementHolder eggshell = Advancement.Builder.advancement()
                    .parent(foodQ)
                    .display(ModItems.COOKIE_DOUGH.get(),
                            Component.translatable("advancements.miraculousori.wheres_the_eggshell.title"),
                            Component.translatable("advancements.miraculousori.wheres_the_eggshell.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_dough", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKIE_DOUGH.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "wheres_the_eggshell"));

            // 超级压缩
            AdvancementHolder compress = Advancement.Builder.advancement()
                    .parent(eggshell)
                    .display(ModItems.COMPRESSED_WHEAT_BISCUIT.get(),
                            Component.translatable("advancements.miraculousori.super_compression.title"),
                            Component.translatable("advancements.miraculousori.super_compression.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_biscuit", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COMPRESSED_WHEAT_BISCUIT.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "super_compression"));

            // 祂投下注视
            AdvancementHolder godGaze = Advancement.Builder.advancement()
                    .parent(compress)
                    .display(ModItems.GOD_GAZE.get(),
                            Component.translatable("advancements.miraculousori.god_gaze.title"),
                            Component.translatable("advancements.miraculousori.god_gaze.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_god_gaze", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOD_GAZE.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "god_gaze"));

            // 佑护加身
            AdvancementHolder blessingAmulet = Advancement.Builder.advancement()
                    .parent(godGaze)
                    .display(ModItems.FOOD_GOD_BLESSING_AMULET.get(),
                            Component.translatable("advancements.miraculousori.blessing_amulet.title"),
                            Component.translatable("advancements.miraculousori.blessing_amulet.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_blessing", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FOOD_GOD_BLESSING_AMULET.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "blessing_amulet"));

            // 真是把好......包
            AdvancementHolder loaf = Advancement.Builder.advancement()
                    .parent(eggshell)
                    .display(ModItems.BAGUETTE.get(),
                            Component.translatable("advancements.miraculousori.what_a_fine_loaf.title"),
                            Component.translatable("advancements.miraculousori.what_a_fine_loaf.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_baguette", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BAGUETTE.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "what_a_fine_loaf"));

            // 再来点蜂蜜
            AdvancementHolder honey = Advancement.Builder.advancement()
                    .parent(foodQ)
                    .display(ModItems.VERY_SWEET_BREAD.get(),
                            Component.translatable("advancements.miraculousori.more_honey.title"),
                            Component.translatable("advancements.miraculousori.more_honey.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_bread", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.VERY_SWEET_BREAD.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "more_honey"));

            // ============================================================
            // 下界进度树
            // ============================================================

            // 俺寻思这玩意能吃
            AdvancementHolder netherRoot = Advancement.Builder.advancement()
                    .parent(root)
                    .display(ModItems.CRIMSON_FRUIT.get(),
                            Component.translatable("advancements.miraculousori.nether_root.title"),
                            Component.translatable("advancements.miraculousori.nether_root.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_crimson_fruit", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRIMSON_FRUIT.get()))
                    .addCriterion("has_warped_fruit", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WARPED_FRUIT.get()))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "nether_root"));

            // 下界小伙能量大
            AdvancementHolder netherEnergy = Advancement.Builder.advancement()
                    .parent(netherRoot)
                    .display(ModItems.NETHER_CLAY_CAKE.get(),
                            Component.translatable("advancements.miraculousori.nether_energy.title"),
                            Component.translatable("advancements.miraculousori.nether_energy.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_nether_cake", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHER_CLAY_CAKE.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "nether_energy"));

            // 枯死之魂的生机
            AdvancementHolder netherSeed = Advancement.Builder.advancement()
                    .parent(netherRoot)
                    .display(ModItems.NETHER_SOUL_WHEAT_SEEDS.get(),
                            Component.translatable("advancements.miraculousori.nether_seed.title"),
                            Component.translatable("advancements.miraculousori.nether_seed.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_seeds", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NETHER_SOUL_WHEAT_SEEDS.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "nether_seed"));

            // 下界特殊物种
            AdvancementHolder netherWheat = Advancement.Builder.advancement()
                    .parent(netherSeed)
                    .display(ModItems.SOUL_WHEAT.get(),
                            Component.translatable("advancements.miraculousori.nether_wheat.title"),
                            Component.translatable("advancements.miraculousori.nether_wheat.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_wheat", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SOUL_WHEAT.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "nether_wheat"));

            // 我真的吃东西了吗？(挑战)
            AdvancementHolder eat = Advancement.Builder.advancement()
                    .parent(netherWheat)
                    .display(ModItems.NETHER_SOUL_WHEAT_COOKIE.get(),
                            Component.translatable("advancements.miraculousori.did_i_really_eat.title"),
                            Component.translatable("advancements.miraculousori.did_i_really_eat.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("eat_cookie", ConsumeItemTrigger.TriggerInstance.usedItem(ModItems.NETHER_SOUL_WHEAT_COOKIE.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "did_i_really_eat"));

            // 祂降下咒罚
            AdvancementHolder netherCurse = Advancement.Builder.advancement()
                    .parent(netherRoot)
                    .display(ModItems.HUNGER_CURSE_PUNISHMENT.get(),
                            Component.translatable("advancements.miraculousori.nether_curse.title"),
                            Component.translatable("advancements.miraculousori.nether_curse.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_curse_punishment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HUNGER_CURSE_PUNISHMENT.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "nether_curse"));

            // 诅咒亦有其形
            AdvancementHolder netherOde = Advancement.Builder.advancement()
                    .parent(netherCurse)
                    .display(ModItems.HUNGER_CURSE_ODE.get(),
                            Component.translatable("advancements.miraculousori.nether_ode.title"),
                            Component.translatable("advancements.miraculousori.nether_ode.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_ode", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HUNGER_CURSE_ODE.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "nether_ode"));

            // 天理难容 (挑战)
            AdvancementHolder heaven = Advancement.Builder.advancement()
                    .parent(netherOde)
                    .display(Items.LIGHTNING_ROD,
                            Component.translatable("advancements.miraculousori.heavenly_injustice.title"),
                            Component.translatable("advancements.miraculousori.heavenly_injustice.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("holding_both", HoldingBothTrigger.holdingBoth(
                            ModItems.FOOD_GOD_BLESSING_AMULET.get(),
                            ModItems.HUNGER_CURSE_ODE.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "heavenly_injustice"));

            // ============================================================
            // 末地进度树
            // ============================================================

            // 末地美食家
            AdvancementHolder endRoot = Advancement.Builder.advancement()
                    .parent(root)
                    .display(ModItems.FLOATING_MELON_SEEDS.get(),
                            Component.translatable("advancements.miraculousori.end_root.title"),
                            Component.translatable("advancements.miraculousori.end_root.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_seeds", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FLOATING_MELON_SEEDS.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "end_root"));

            // 它飞走了
            AdvancementHolder fly = Advancement.Builder.advancement()
                    .parent(endRoot)
                    .display(ModItems.FLOATING_MELON.get(),
                            Component.translatable("advancements.miraculousori.it_flew_away.title"),
                            Component.translatable("advancements.miraculousori.it_flew_away.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("float_melon", FloatMelonTrigger.criterion())
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "it_flew_away"));

            // 吃完这个会不会飞起来？
            AdvancementHolder endMelon = Advancement.Builder.advancement()
                    .parent(fly)
                    .display(ModItems.FLOATING_MELON.get(),
                            Component.translatable("advancements.miraculousori.end_melon.title"),
                            Component.translatable("advancements.miraculousori.end_melon.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_melon", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FLOATING_MELON.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "end_melon"));

            // 地球大炮 (挑战)
            AdvancementHolder earthCannon = Advancement.Builder.advancement()
                    .parent(endMelon)
                    .display(ModItems.FLOATING_MELON_PIE.get(),
                            Component.translatable("advancements.miraculousori.earth_cannon.title"),
                            Component.translatable("advancements.miraculousori.earth_cannon.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("reversed_gravity_height",
                            ReversedGravityTrigger.effectAndHeightCriterion(
                                    ModMobEffects.REVERSED_GRAVITY, 800))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "earth_cannon"));

            // 近程折跃
            AdvancementHolder endJump = Advancement.Builder.advancement()
                    .parent(fly)
                    .display(ModItems.LEAPING_FRUIT.get(),
                            Component.translatable("advancements.miraculousori.end_jump.title"),
                            Component.translatable("advancements.miraculousori.end_jump.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("eat_leaping_fruit", ConsumeItemTrigger.TriggerInstance.usedItem(ModItems.LEAPING_FRUIT.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "end_jump"));

            // 末地石里的植物
            AdvancementHolder endPlant = Advancement.Builder.advancement()
                    .parent(endJump)
                    .display(ModBlocks.LEAPING_FRUIT_PLANT_TOP.get(),
                            Component.translatable("advancements.miraculousori.end_plant.title"),
                            Component.translatable("advancements.miraculousori.end_plant.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("activate_plant", LeapingFruitActivateTrigger.criterion())
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "end_plant"));

            // 聆音终末
            AdvancementHolder endSpeech = Advancement.Builder.advancement()
                    .parent(fly)
                    .display(ModItems.ENDING_SPEECH.get(),
                            Component.translatable("advancements.miraculousori.end_speech.title"),
                            Component.translatable("advancements.miraculousori.end_speech.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_speech", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ENDING_SPEECH.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "end_speech"));

            // 末地之旅
            AdvancementHolder endJourney = Advancement.Builder.advancement()
                    .parent(endSpeech)
                    .display(ModItems.END_NEW_PATH.get(),
                            Component.translatable("advancements.miraculousori.end_journey.title"),
                            Component.translatable("advancements.miraculousori.end_journey.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_end_new_path", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.END_NEW_PATH.get()))
                    .build(ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "end_journey"));

            // ============================================================
            // 输出所有进度
            // ============================================================

            // 根节点
            consumer.accept(root);

            // 主世界
            consumer.accept(foodQ);
            consumer.accept(pressResidue);
            consumer.accept(moneyAdv);
            consumer.accept(eggshell);
            consumer.accept(compress);
            consumer.accept(godGaze);
            consumer.accept(blessingAmulet);
            consumer.accept(loaf);
            consumer.accept(honey);

            // 下界
            consumer.accept(netherRoot);
            consumer.accept(netherEnergy);
            consumer.accept(netherSeed);
            consumer.accept(netherWheat);
            consumer.accept(eat);
            consumer.accept(netherCurse);
            consumer.accept(netherOde);
            consumer.accept(heaven);

            // 末地
            consumer.accept(endRoot);
            consumer.accept(fly);
            consumer.accept(endMelon);
            consumer.accept(earthCannon);
            consumer.accept(endJump);
            consumer.accept(endPlant);
            consumer.accept(endSpeech);
            consumer.accept(endJourney);
        }
    }
}