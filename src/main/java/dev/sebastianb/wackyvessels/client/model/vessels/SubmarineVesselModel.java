package dev.sebastianb.wackyvessels.client.model.vessels;

import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

public class SubmarineVesselModel extends EntityModel<SubmarineVesselEntity> {


    @Override
    public void setAngles(SubmarineVesselEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        VertexConsumerProvider vertexConsumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        matrices.push(); {
            matrices.translate(-0.5,0.5,-0.5);
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity
                    (Blocks.EMERALD_BLOCK.getDefaultState(), matrices, vertexConsumerProvider, light, overlay);
        }
        matrices.pop();

    }
}
