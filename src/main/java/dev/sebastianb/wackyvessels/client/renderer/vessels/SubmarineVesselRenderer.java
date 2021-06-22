package dev.sebastianb.wackyvessels.client.renderer.vessels;

import dev.sebastianb.wackyvessels.client.model.vessels.SubmarineVesselModel;
import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

public class SubmarineVesselRenderer extends MobEntityRenderer<SubmarineVesselEntity, SubmarineVesselModel> {
    public SubmarineVesselRenderer(EntityRendererFactory.Context context) {
        super(context, new SubmarineVesselModel(), 1);
        this.shadowRadius = 0;
    }

    @Override
    public Identifier getTexture(SubmarineVesselEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    @Override
    public boolean shouldRender(SubmarineVesselEntity mobEntity, Frustum frustum, double d, double e, double f) {
        return true;
    }

}
