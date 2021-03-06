package dev.sebastianb.wackyvessels.client;

import dev.sebastianb.wackyvessels.client.renderer.SitEntityRenderer;
import dev.sebastianb.wackyvessels.client.renderer.vessels.AirshipVesselRenderer;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;

public class WackyVesselsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(WackyVesselsEntityTypes.AIRSHIP_VESSEL_ENTITY, AirshipVesselRenderer::new);
        EntityRendererRegistry.INSTANCE.register(WackyVesselsEntityTypes.SIT_ENTITY, SitEntityRenderer::new);

        WackyVesselsKeybinding.register();

        WackyVesselsScreenRegistry.registerScreens();
    }
}
