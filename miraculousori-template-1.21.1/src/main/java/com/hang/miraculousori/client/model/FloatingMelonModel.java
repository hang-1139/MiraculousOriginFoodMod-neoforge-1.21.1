package com.hang.miraculousori.client.model;

import com.hang.miraculousori.MiraculousOriginFoodMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FloatingMelonModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath(MiraculousOriginFoodMod.MODID, "float_melon_model"), "main"
	);

	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart bone;
	private final ModelPart bone4;
	private final ModelPart bone3;
	private final ModelPart bone2;
	private final ModelPart bone1;

	public FloatingMelonModel(ModelPart root) {
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.bone = this.all.getChild("bone");
		this.bone4 = this.bone.getChild("bone4");
		this.bone3 = this.bone.getChild("bone3");
		this.bone2 = this.bone.getChild("bone2");
		this.bone1 = this.bone.getChild("bone1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = all.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(0.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, -8.0F, 8.0F)
		);

		PartDefinition bone = all.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone4 = bone.addOrReplaceChild("bone4",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(0.0F, 0.0F, -3.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(4.0F, 0.0F, 0.0F)
		);

		PartDefinition bone3 = bone.addOrReplaceChild("bone3",
				CubeListBuilder.create().texOffs(0, 32)
						.addBox(-3.0F, 0.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 4.0F)
		);

		PartDefinition bone2 = bone.addOrReplaceChild("bone2",
				CubeListBuilder.create().texOffs(0, 5)
						.addBox(0.0F, 0.0F, -3.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-4.0F, 0.0F, 0.0F)
		);

		PartDefinition bone1 = bone.addOrReplaceChild("bone1",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-3.0F, 0.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, -4.0F)
		);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// 重置所有部件位置
		all.getAllParts().forEach(ModelPart::resetPose);

		// 手动计算动画 (基于 ageInTicks，循环周期 2.0 秒)
		float cycle = ageInTicks / 20.0F;
		float t = cycle % 2.0F;

		// bone1: 从0到-10度在0.5秒，到5度在1.5秒，回0在2秒
		float angle1;
		if (t < 0.5F) {
			angle1 = Mth.lerp(t / 0.5F, 0.0F, -10.0F);
		} else if (t < 1.5F) {
			angle1 = Mth.lerp((t - 0.5F) / 1.0F, -10.0F, 5.0F);
		} else {
			angle1 = Mth.lerp((t - 1.5F) / 0.5F, 5.0F, 0.0F);
		}
		bone1.xRot = (float) Math.toRadians(angle1);

		// bone2: 到10度在0.5秒，到-5度在1.5秒，回0
		float angle2;
		if (t < 0.5F) {
			angle2 = Mth.lerp(t / 0.5F, 0.0F, 10.0F);
		} else if (t < 1.5F) {
			angle2 = Mth.lerp((t - 0.5F) / 1.0F, 10.0F, -5.0F);
		} else {
			angle2 = Mth.lerp((t - 1.5F) / 0.5F, -5.0F, 0.0F);
		}
		bone2.zRot = (float) Math.toRadians(angle2);

		// bone3: 到10度在0.5秒，到-5度在1.5秒，回0
		float angle3;
		if (t < 0.5F) {
			angle3 = Mth.lerp(t / 0.5F, 0.0F, 10.0F);
		} else if (t < 1.5F) {
			angle3 = Mth.lerp((t - 0.5F) / 1.0F, 10.0F, -5.0F);
		} else {
			angle3 = Mth.lerp((t - 1.5F) / 0.5F, -5.0F, 0.0F);
		}
		bone3.xRot = (float) Math.toRadians(angle3);

		// bone4: 旋转到-10度在0.5秒，回0在1.0秒，到15度在1.5秒，回0
		float angle4;
		if (t < 0.2083F) {
			angle4 = Mth.lerp(t / 0.2083F, 0.0F, -4.82F);
		} else if (t < 0.5F) {
			angle4 = Mth.lerp((t - 0.2083F) / 0.2917F, -4.82F, -10.0F);
		} else if (t < 1.0F) {
			angle4 = Mth.lerp((t - 0.5F) / 0.5F, -10.0F, 0.0F);
		} else if (t < 1.5F) {
			angle4 = Mth.lerp((t - 1.0F) / 0.5F, 0.0F, 15.0F);
		} else {
			angle4 = Mth.lerp((t - 1.5F) / 0.5F, 15.0F, 0.0F);
		}
		bone4.zRot = (float) Math.toRadians(angle4);

		// body 微小旋转
		float bodyRot = (t < 1.0F) ? Mth.lerp(t / 1.0F, 0.0F, 0.002F) : Mth.lerp((t - 1.0F) / 1.0F, 0.002F, 0.0F);
		body.zRot = (float) Math.toRadians(bodyRot);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}