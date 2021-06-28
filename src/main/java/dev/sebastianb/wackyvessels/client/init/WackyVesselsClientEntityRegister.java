package dev.sebastianb.wackyvessels.client.init;

import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import dev.sebastianb.wackyvessels.client.renderer.SitEntityRenderer;
import dev.sebastianb.wackyvessels.client.renderer.vessels.SubmarineVesselRenderer;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class WackyVesselsClientEntityRegister {

    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlock(WackyVesselsBlocks.CONSOLE, RenderLayer.getCutout());

        EntityRendererRegistry.INSTANCE.register(WackyVesselsEntityTypes.SUBMARINE_VESSEL, SubmarineVesselRenderer::new);
        EntityRendererRegistry.INSTANCE.register(WackyVesselsEntityTypes.SIT_ENTITY, SitEntityRenderer::new);
    }
}
