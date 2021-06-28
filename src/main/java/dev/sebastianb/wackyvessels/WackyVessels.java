package dev.sebastianb.wackyvessels;

import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import dev.sebastianb.wackyvessels.entity.WackyVesselsBlockEntities;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import dev.sebastianb.wackyvessels.network.WackyVesselsPackets;
import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenHandlerRegistry;
import net.fabricmc.api.ModInitializer;

public class WackyVessels implements ModInitializer {

    @Override
    public void onInitialize() {
        WackyVesselsBlocks.register();
        WackyVesselsBlockEntities.register();
        WackyVesselsEntityTypes.register();
        WackyVesselsScreenHandlerRegistry.register();
        WackyVesselsPackets.register();
        AbstractVesselEntity.initClass();
    }
}
