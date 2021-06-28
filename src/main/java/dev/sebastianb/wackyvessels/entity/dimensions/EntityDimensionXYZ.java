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

    // lmao yonked this specific method from the worldshell mod
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityDimensionXYZ that)) return false;

        if (Float.compare(that.length, length) != 0) return false;
        if (Float.compare(that.width, width) != 0) return false;
        if (Float.compare(that.height, height) != 0) return false;
        return fixed == that.fixed;
    }
}
