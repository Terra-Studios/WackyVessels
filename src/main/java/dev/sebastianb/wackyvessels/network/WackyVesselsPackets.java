package dev.sebastianb.wackyvessels.network;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.SebaUtils;
import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import dev.sebastianb.wackyvessels.entity.vessels.SubmarineVesselEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.HashSet;

public class WackyVesselsPackets {

    private static HashSet<BlockPos> vesselBlockPositions = new HashSet<>(); // all BlockPos of vessel to be sent off

    // check if player that sent packet is actually with-in the location of block lmao
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.VESSEL_HELM_MOUNT, ((server, player, handler, buf, responseSender) -> {

            ServerWorld serverWorld = player.getServerWorld();
            BlockPos vesselHelmLocation = buf.readBlockPos();
            BlockPos playerLocation = player.getBlockPos();

            server.execute((() -> {
                // FUCK DO STUFF WITH THING

                boolean canUseVessel = (vesselHelmLocation.isWithinDistance(playerLocation, 10)) && (serverWorld.getBlockState(vesselHelmLocation) == WackyVesselsBlocks.VESSEL_HELM.getDefaultState());
                if (canUseVessel) { // to prevent malicious actors from messing with my clientside cod 😡 😡 😡

                    vesselBlockPositions.add(vesselHelmLocation); // starting block added to vessel

                    checkSurroundingBlocks(serverWorld, vesselHelmLocation, true);

                    SubmarineVesselEntity sub = new SubmarineVesselEntity(WackyVesselsEntityTypes.SUBMARINE_VESSEL, serverWorld);
                    sub.setPosition(new Vec3d(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ()));
                    sub.setModelData(vesselBlockPositions, vesselHelmLocation);
                    serverWorld.spawnEntity(sub);

                } else {
                    player.sendMessage(new LiteralText("NO CHEATING!!!"), false); // I already handled this but u never know + this line of code is funny
                }
                player.closeHandledScreen();
            }));
        }));
    }

    private static int vesselBlockSize = 0;

    private static void checkSurroundingBlocks(ServerWorld world, BlockPos vesselHelmLocation, boolean ranFirstTime) {
        BlockPos blockBeingHandled;
        if (ranFirstTime) {
            blockBeingHandled = vesselHelmLocation;
            vesselBlockPositions.clear();
            vesselBlockSize = 0;
        } else {
            blockBeingHandled = vesselHelmLocation;
        }
        if (vesselBlockSize > 1000) { // for some reason, the size is slightly mismatched with the actual value but works lol
            return;
        }
        HashSet<BlockPos> validAroundBlocks = new HashSet<>(); // surronding blocks that are valid to be changed
        for (Direction pos : Direction.values()) {
            if (world.getBlockState(blockBeingHandled.add(SebaUtils.MathUtils.directionToVec3I(pos))).getBlock() != Blocks.AIR) {
                Vec3i currentBlockHandled = SebaUtils.MathUtils.directionToVec3I(pos).add(blockBeingHandled); // Grabs all blocks around the specified block
                if (!vesselBlockPositions.contains(new BlockPos(currentBlockHandled))) {
                    vesselBlockPositions.add(new BlockPos(currentBlockHandled));
                    vesselBlockSize++;
                    validAroundBlocks.add(new BlockPos(currentBlockHandled));
                }
            }
        }

        for (BlockPos pos : validAroundBlocks) {
            checkSurroundingBlocks(world, pos, false);
        }
    }

}