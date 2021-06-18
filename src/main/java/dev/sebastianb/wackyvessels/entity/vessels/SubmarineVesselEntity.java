package dev.sebastianb.wackyvessels.entity.vessels;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class SubmarineVesselEntity extends AbstractVesselEntity {

    public SubmarineVesselEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
}
