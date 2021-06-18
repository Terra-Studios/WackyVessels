package dev.sebastianb.wackyvessels.entity;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
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
                    .dimensions(EntityDimensions.fixed(0.75f, 2.5f)).build()

    );


    public static void register() {
        FabricDefaultAttributeRegistry.register(SUBMARINE_VESSEL, SubmarineVesselEntity.createMobAttributes());
    }

}