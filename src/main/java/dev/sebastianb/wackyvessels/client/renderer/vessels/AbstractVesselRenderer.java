package dev.sebastianb.wackyvessels.client.renderer.vessels;

import dev.sebastianb.wackyvessels.client.model.vessels.AbstractVesselModel;
import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public abstract class AbstractVesselRenderer<T extends AbstractVesselEntity, Q extends AbstractVesselModel<T>> extends MobEntityRenderer<T, Q> {
    public AbstractVesselRenderer(EntityRendererFactory.Context context) {
        super(context, (Q) new AbstractVesselModel(), 1);
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

}
