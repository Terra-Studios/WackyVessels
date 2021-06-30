package dev.sebastianb.wackyvessels.client.renderer;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class SitEntityRenderer extends EntityRenderer<Entity> {

    public SitEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return null;
    }

}
