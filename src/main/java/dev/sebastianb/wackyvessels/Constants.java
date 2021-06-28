package dev.sebastianb.wackyvessels;

import net.minecraft.util.Identifier;

public class Constants {

    public static String MOD_ID = "wackyvessels";

    public static class Entity {
        public static String SUBMARINE_VESSEL = "submarine_vessel";
        public static String SIT_ENTITY = "sit_entity";

    }

    public static class Blocks {
        public static String VESSEL_HELM = "vessel_helm";
        public static String VESSEL_CHAIR = "vessel_chair";

    }

    public static class Text {
        public static class Screen {
            public static String ASSEMBLY_BUTTON = MOD_ID + ".gui.helm.assembly";
            public static String DISASSEMBLY_BUTTON = MOD_ID + ".gui.helm.disassembly";

        }
    }

    public static class Packets {
        public static Identifier VESSEL_HELM_MOUNT = new Identifier(MOD_ID, "vessel_helm_mount");
    }


}
