package dev.sebastianb.wackyvessels.client.renderer.vessels;

import dev.sebastianb.wackyvessels.client.model.vessels.AbstractVesselModel;
import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.snakefangox.worldshell.client.WorldShellEntityRenderer;

public abstract class AbstractVesselRenderer<S extends AbstractVesselEntity> extends WorldShellEntityRenderer<AbstractVesselEntity> {
    public AbstractVesselRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0;
    }


}
