package dev.sebastianb.wackyvessels.entity;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.entity.dimensions.EntityDimensionXYZ;
import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WackyVesselsEntityTypes {

    public static final EntityType<SubmarineVesselEntity> SUBMARINE_VESSEL
            = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(Constants.MOD_ID, Constants.Entity.SUBMARINE_VESSEL),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SubmarineVesselEntity::new)
                    .dimensions(EntityDimensionXYZ.changing(1, 1)).trackRangeBlocks(100).trackedUpdateRate(1).build()

    );

    public static final EntityType<Entity> SIT_ENTITY
            = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(Constants.MOD_ID, Constants.Entity.SIT_ENTITY),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SitEntity::new)
                    .dimensions(EntityDimensionXYZ.changing(1, 1)).trackRangeBlocks(100).trackedUpdateRate(1).build()

    );

    public static void register() {
        // FabricDefaultAttributeRegistry.register(SUBMARINE_VESSEL, SubmarineVesselEntity.createMobAttributes());
    }
}
