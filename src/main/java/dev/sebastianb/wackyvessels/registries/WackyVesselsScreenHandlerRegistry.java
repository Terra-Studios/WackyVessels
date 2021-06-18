package dev.sebastianb.wackyvessels.registries;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.client.gui.VesselHelmScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class WackyVesselsScreenHandlerRegistry {

    private static final Identifier identifier = new Identifier(Constants.MOD_ID, "vessel_helm_gui");

    public static ScreenHandlerType<? extends VesselHelmScreenHandler> VESSEL_HELM_SCREEN =
            ScreenHandlerRegistry.registerExtended(identifier, VesselHelmScreenHandler::new);


    public static void register() {

    }

}
