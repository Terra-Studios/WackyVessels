package dev.sebastianb.wackyvessels.client.init;

import dev.sebastianb.wackyvessels.client.renderer.vessels.SubmarineVesselRenderer;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class WackyVesselsClientEntityRegister {

    public static void register() {
        EntityRendererRegistry.INSTANCE.register(WackyVesselsEntityTypes.SUBMARINE_VESSEL, SubmarineVesselRenderer::new);

    }

}
