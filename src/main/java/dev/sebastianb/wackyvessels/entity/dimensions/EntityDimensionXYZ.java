package dev.sebastianb.wackyvessels.entity.dimensions;

import net.minecraft.entity.EntityDimensions;

public class EntityDimensionXYZ extends EntityDimensions {

    // X of the entity
    public final float length;

    public EntityDimensionXYZ(float length, float width, float height, boolean fixed) {
        super(width, height, fixed);
        this.length = length;
    }

}
