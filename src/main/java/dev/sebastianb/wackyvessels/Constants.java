package dev.sebastianb.wackyvessels;

import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public interface Constants {

    String MOD_ID = "wackyvessels";

    interface Entity {
        String AIRSHIP_VESSEL = "airship_vessel";
        String SIT_ENTITY = "sit_entity";
    }

    interface Blocks {
        String CONSOLE = "console";
        String WHEEL = "wheel";
        String VESSEL_HELM = "vessel_helm";
        String VESSEL_CHAIR = "vessel_chair";
    }

    interface Text {
        interface Screen {
            TranslatableText ASSEMBLY_BUTTON = new TranslatableText(String.format("gui.%s.assemble", MOD_ID));
            String DISASSEMBLY_BUTTON = MOD_ID + ".gui.helm.disassembly";
        }
    }

    interface Packets {
        Identifier VESSEL_HELM_MOUNT = new Identifier(MOD_ID, "vessel_helm_mount");
        Identifier VESSEL_INPUT_PACKET = new Identifier(MOD_ID, "vessel_input_packet");
    }

    interface KeyBindings {
        String CATEGORY = "category." + MOD_ID + ".main";
        String DOWN = "key." + MOD_ID + ".down";
    }

    interface Tags {
        Identifier MATERIAL_BLACKLIST = new Identifier(MOD_ID, "material_blacklist");
    }
}
