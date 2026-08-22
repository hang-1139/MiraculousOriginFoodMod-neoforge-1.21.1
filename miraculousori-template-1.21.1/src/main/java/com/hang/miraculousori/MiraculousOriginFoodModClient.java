package com.hang.miraculousori;

import com.hang.miraculousori.client.model.FloatingMelonModel;
import com.hang.miraculousori.client.renderer.FloatingMelonRenderer;
import com.hang.miraculousori.entity.ModEntities;
import com.hang.miraculousori.menu.ModMenuTypes;
import com.hang.miraculousori.screen.MillScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static com.hang.miraculousori.MiraculousOriginFoodMod.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class MiraculousOriginFoodModClient {

    public MiraculousOriginFoodModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MiraculousOriginFoodMod.LOGGER.info("HELLO FROM CLIENT SETUP");
        MiraculousOriginFoodMod.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.MILL_MENU.get(), MillScreen::new);
    }

    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // 飞浮瓜渲染器
        event.registerEntityRenderer(ModEntities.FLOATING_MELON.get(), FloatingMelonRenderer::new);

        // 神圣物品实体渲染器
        event.registerEntityRenderer(ModEntities.DIVINE_ITEM.get(), ItemEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FloatingMelonModel.LAYER_LOCATION, FloatingMelonModel::createBodyLayer);
    }
}