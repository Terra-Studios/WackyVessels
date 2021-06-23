package dev.sebastianb.wackyvessels.client.renderer.vessels;

import dev.sebastianb.wackyvessels.client.model.vessels.SubmarineVesselModel;
import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;

public class SubmarineVesselRenderer extends AbstractVesselRenderer<SubmarineVesselEntity, SubmarineVesselModel> {
    public SubmarineVesselRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}
