package dev.sebastianb.wackyvessels;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class SebaUtils {

    public static class MathUtils {

        public static Vec3i directionToVec3I(Direction direction) {
            return new Vec3i(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
        }
        public static Vec3d blockPosToVec3d(BlockPos blockPos) {
            return new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

    }

}
