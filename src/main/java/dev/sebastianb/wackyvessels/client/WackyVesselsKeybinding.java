package dev.sebastianb.wackyvessels.client;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.entity.vessels.AirshipVesselEntity;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class WackyVesselsKeybinding {
    private static KeyBinding vesselDown = KeyBindingHelper.registerKeyBinding(new KeyBinding(Constants.KeyBindings.DOWN,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            Constants.KeyBindings.CATEGORY));

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {

                if (client.player.getVehicle() instanceof AirshipVesselEntity) {
                    if (vesselDown.isPressed()) {
                        AirshipVesselEntity airship = (AirshipVesselEntity) client.player.getVehicle();

                        airship.readInput(true);
                    } else {
                        AirshipVesselEntity airship = (AirshipVesselEntity) client.player.getVehicle();

                        airship.readInput(false);
                    }
                }


            }
        });
    }
}
