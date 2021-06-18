package dev.sebastianb.wackyvessels.client.model.vessels;

import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SubmarineVesselModel extends EntityModel<SubmarineVesselEntity> {


    @Override
    public void setAngles(SubmarineVesselEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

    }
}
