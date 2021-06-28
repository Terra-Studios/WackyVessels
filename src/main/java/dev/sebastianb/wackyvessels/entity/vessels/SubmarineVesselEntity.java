package dev.sebastianb.wackyvessels.entity.vessels;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class SubmarineVesselEntity extends AbstractVesselEntity {
    public SubmarineVesselEntity(EntityType<SubmarineVesselEntity> entityType, World world) {
        super(entityType, world);
    }
}
