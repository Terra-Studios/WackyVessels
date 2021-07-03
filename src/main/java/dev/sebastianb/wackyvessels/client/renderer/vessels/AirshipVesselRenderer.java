package dev.sebastianb.wackyvessels.client.renderer.vessels;

import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import dev.sebastianb.wackyvessels.entity.vessels.AirshipVesselEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class AirshipVesselRenderer extends AbstractVesselRenderer {
    public AirshipVesselRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}
