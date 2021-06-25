package dev.sebastianb.wackyvessels.entity.dimensions;

import net.minecraft.entity.EntityDimensions;

public class EntityDimensionXYZ extends EntityDimensions {

    // X of the entity
    public final float length;

    public EntityDimensionXYZ(float length, float height, float width, boolean fixed) {
        super(width, height, fixed);
        this.length = length;
    }

    @Override
    public String toString() {
        return String.format("EntityDimensionXYZ: X = %s, Y = %s, Z = %s, Is fixed = %s", length, height, width, fixed);
    }

}
