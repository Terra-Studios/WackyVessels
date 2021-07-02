package dev.sebastianb.wackyvessels.entity.vessels;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class AirshipVesselEntity extends AbstractVesselEntity{
    public AirshipVesselEntity(EntityType<AirshipVesselEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
