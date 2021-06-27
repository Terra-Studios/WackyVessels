package dev.sebastianb.wackyvessels.collision;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/**
 * The delegated box for a entity bounding box. Overrides the vanilla system and used in getting the walkable hull
 */
public class VesselBoxDelegate extends Box {

    public VesselBoxDelegate(Box box) {
        super(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public VesselBoxDelegate(BlockPos pos1, BlockPos pos2) {
        super(pos1, pos2);
    }
}
