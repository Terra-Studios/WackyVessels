package dev.sebastianb.wackyvessels.registries;

import dev.sebastianb.wackyvessels.client.gui.VesselHelmScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class WackyVesselsScreenRegistry {

    public static void registerScreens() {
        ScreenRegistry.register(WackyVesselsScreenHandlerRegistry.VESSEL_HELM_SCREEN, VesselHelmScreen::new);
    }
}
