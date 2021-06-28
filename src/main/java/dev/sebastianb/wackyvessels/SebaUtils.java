package dev.sebastianb.wackyvessels;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.Collection;

public class SebaUtils {

    public static class MathUtils {

        public static Vec3i directionToVec3I(Direction direction) {
            return new Vec3i(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
        }
        public static Vec3d blockPosToVec3d(BlockPos blockPos) {
            return new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        // TODO: I should find a better way for the following methods but I can't be bothered right now
        public static BlockPos getSmallestBlockPos(Collection<BlockPos> relBlockPos) {
            BlockPos smallCorner = new BlockPos(0,0,0);
            for (BlockPos pos : relBlockPos) {
                if (pos.getX() < smallCorner.getX()) {
                    smallCorner = new BlockPos(pos.getX(), smallCorner.getY(), smallCorner.getZ());
                }
                if (pos.getY() < smallCorner.getY()) {
                    smallCorner = new BlockPos(smallCorner.getX(), pos.getY(), smallCorner.getZ());
                }
                if (pos.getZ() < smallCorner.getZ()) {
                    smallCorner = new BlockPos(smallCorner.getX(), smallCorner.getY(), pos.getZ());
                }
            }
            return smallCorner;
        }

        public static BlockPos getLargestBlockPos(Collection<BlockPos> relBlockPos) {
            BlockPos bigCorner = new BlockPos(0,0,0);
            for (BlockPos pos : relBlockPos) {
                if (pos.getX() > bigCorner.getX()) {
                    bigCorner = new BlockPos(pos.getX(), bigCorner.getY(), bigCorner.getZ());
                }
                if (pos.getY() > bigCorner.getY()) {
                    bigCorner = new BlockPos(bigCorner.getX(), pos.getY(), bigCorner.getZ());

                }
                if (pos.getZ() > bigCorner.getZ()) {
                    bigCorner = new BlockPos(bigCorner.getX(), bigCorner.getY(), pos.getZ());
                }
            }
            return bigCorner;
        }
    }
}
