package dev.sebastianb.wackyvessels.client.model.vessels;

import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

import java.util.Map;

public class AbstractVesselModel<T extends AbstractVesselEntity> extends EntityModel<T> {

    T entity;

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.entity = entity;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        VertexConsumerProvider vertexConsumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        for (Map.Entry<BlockPos, BlockState> relativePos : entity.getRelativeVesselBlockPositions().entrySet()) {
            matrices.push(); {
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
                matrices.translate(relativePos.getKey().getX(), relativePos.getKey().getY() - 1.5,relativePos.getKey().getZ());
                MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(
                        relativePos.getValue(), matrices, vertexConsumerProvider, light, overlay);
            }
            matrices.pop();
        }
    }
}