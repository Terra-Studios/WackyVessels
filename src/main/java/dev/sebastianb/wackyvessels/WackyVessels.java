package dev.sebastianb.wackyvessels;

import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import dev.sebastianb.wackyvessels.entity.WackyVesselsBlockEntities;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import dev.sebastianb.wackyvessels.network.WackyVesselsPackets;
import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenHandlerRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class WackyVessels implements ModInitializer {

    public static final Tag<Block> MATERIAL_BLACKLIST = TagFactory.BLOCK.create(new Identifier(Constants.MOD_ID, "material_blacklist"));

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
