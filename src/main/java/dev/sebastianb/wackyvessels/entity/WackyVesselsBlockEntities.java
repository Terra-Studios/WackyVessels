package dev.sebastianb.wackyvessels.entity;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import dev.sebastianb.wackyvessels.block.helm.VesselHelmEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WackyVesselsBlockEntities {


    public static final BlockEntityType<VesselHelmEntity> VESSEL_HELM_ENTITY =
            register(FabricBlockEntityTypeBuilder.create(
                    VesselHelmEntity::new, WackyVesselsBlocks.VESSEL_HELM).build(null),
                    new Identifier(Constants.MOD_ID, Constants.Blocks.VESSEL_HELM));


    private static <T extends BlockEntity> BlockEntityType<T> register(BlockEntityType<T> build, Identifier identifier) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, identifier, build);
        return build;
    }

    public static void register() {

    }


}
