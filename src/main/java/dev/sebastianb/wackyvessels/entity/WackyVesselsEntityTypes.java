package dev.sebastianb.wackyvessels.entity;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.entity.dimensions.EntityDimensionXYZ;
import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.snakefangox.worldshell.entity.WorldShellEntityType;

public class WackyVesselsEntityTypes {

    public static final EntityType<SubmarineVesselEntity> SUBMARINE_VESSEL
            = new WorldShellEntityType<>(SubmarineVesselEntity::new);

//            Registry.register(
//            Registry.ENTITY_TYPE,
//            new Identifier(Constants.MOD_ID, Constants.Entity.SUBMARINE_VESSEL),
//            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SubmarineVesselEntity::new)
//                    .dimensions(EntityDimensionXYZ.changing(1, 1)).trackRangeBlocks(100).trackedUpdateRate(1).build()
//
//    );


    public static void register() {

    }

}
