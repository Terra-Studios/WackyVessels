package dev.sebastianb.wackyvessels.client.renderer.vessels;

import dev.sebastianb.wackyvessels.client.model.vessels.AbstractVesselModel;
import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

import java.util.Map;

public abstract class AbstractVesselRenderer<T extends AbstractVesselEntity, Q extends AbstractVesselModel<T>> extends EntityRenderer<T> {
    public AbstractVesselRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0;
    }

    @Override
    public Identifier getTexture(AbstractVesselEntity entity) {
        return PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;
    }

    @Override
    public boolean shouldRender(T mobEntity, Frustum frustum, double d, double e, double f) {
        return true;
    }

    // TODO: Do some culling. Maybe keep in a cache
    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        VertexConsumerProvider vertexConsumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        for (Map.Entry<BlockPos, BlockState> relativePos : entity.getRelativeVesselBlockPositions().entrySet()) {
            matrices.push(); {
                matrices.translate(relativePos.getKey().getX(), relativePos.getKey().getY(), relativePos.getKey().getZ());
                MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(
                        relativePos.getValue(), matrices, vertexConsumerProvider, light, OverlayTexture.DEFAULT_UV);
            }
            matrices.pop();
        }
    }
}
