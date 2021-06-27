package dev.sebastianb.wackyvessels.client.init;

import dev.sebastianb.wackyvessels.client.renderer.vessels.SubmarineVesselRenderer;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.snakefangox.worldshell.client.WorldShellEntityRenderer;

public class WackyVesselsClientEntityRegister {

    public static void register() {
        EntityRendererRegistry.INSTANCE.register(WackyVesselsEntityTypes.SUBMARINE_VESSEL, WorldShellEntityRenderer::new);

    }

}
