package dev.sebastianb.wackyvessels.client.init;

import dev.sebastianb.wackyvessels.client.renderer.SitEntityRenderer;
import dev.sebastianb.wackyvessels.client.renderer.vessels.SubmarineVesselRenderer;
import dev.sebastianb.wackyvessels.entity.SitEntity;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class WackyVesselsClientEntityRegister {

    public static void register() {
        EntityRendererRegistry.INSTANCE.register(WackyVesselsEntityTypes.SUBMARINE_VESSEL, SubmarineVesselRenderer::new);
        EntityRendererRegistry.INSTANCE.register(WackyVesselsEntityTypes.SIT_ENTITY, SitEntityRenderer::new);


    }

}
