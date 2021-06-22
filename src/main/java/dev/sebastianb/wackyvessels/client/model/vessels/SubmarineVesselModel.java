package dev.sebastianb.wackyvessels.client.model.vessels;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

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
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
                matrices.translate(relativePos.getKey().getX(), relativePos.getKey().getY() - 1.5,relativePos.getKey().getZ());
                MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity
                        (relativePos.getValue(), matrices, vertexConsumerProvider, light, overlay);
            }
            matrices.pop();
        }
    }
}
