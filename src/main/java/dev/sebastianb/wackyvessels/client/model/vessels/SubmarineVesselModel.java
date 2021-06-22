package dev.sebastianb.wackyvessels.client.model.vessels;

import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class SubmarineVesselModel extends EntityModel<SubmarineVesselEntity> {


    SubmarineVesselEntity entity;

    @Override
    public void setAngles(SubmarineVesselEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.entity = entity;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        VertexConsumerProvider vertexConsumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();


        for (Map.Entry<BlockPos, BlockState> relativePos : entity.getRelativeVesselBlockPositions().entrySet()) {
            matrices.push(); {
                matrices.translate(-0.5 + relativePos.getKey().getX(),0.5 + relativePos.getKey().getY(),-0.5 + relativePos.getKey().getZ());
                MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity
                        (relativePos.getValue(), matrices, vertexConsumerProvider, light, overlay);
            }
            matrices.pop();
        }


    }
}
