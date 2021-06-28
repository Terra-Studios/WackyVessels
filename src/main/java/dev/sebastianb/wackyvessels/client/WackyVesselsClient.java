package dev.sebastianb.wackyvessels.client;

import dev.sebastianb.wackyvessels.client.init.WackyVesselsClientEntityRegister;
import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenRegistry;
import net.fabricmc.api.ClientModInitializer;

public class WackyVesselsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WackyVesselsClientEntityRegister.register();
        WackyVesselsScreenRegistry.registerScreens();
    }
}
