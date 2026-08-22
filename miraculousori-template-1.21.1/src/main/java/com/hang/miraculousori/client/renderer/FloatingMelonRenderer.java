package com.hang.miraculousori.client.renderer;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.hang.miraculousori.client.model.FloatingMelonModel;
import com.hang.miraculousori.entity.custom.FloatingMelonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class FloatingMelonRenderer extends LivingEntityRenderer<FloatingMelonEntity, FloatingMelonModel<FloatingMelonEntity>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            MiraculousOriginFoodMod.MODID, "textures/entity/float_melon.png"
    );

    public FloatingMelonRenderer(EntityRendererProvider.Context context) {
        super(context, new FloatingMelonModel<>(context.bakeLayer(FloatingMelonModel.LAYER_LOCATION)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingMelonEntity entity) {
        return TEXTURE;
    }
}