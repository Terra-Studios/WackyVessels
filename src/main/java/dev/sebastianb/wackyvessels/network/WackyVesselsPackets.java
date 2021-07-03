package dev.sebastianb.wackyvessels.network;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.SebaUtils;
import dev.sebastianb.wackyvessels.client.gui.VesselHelmScreenHandler;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import dev.sebastianb.wackyvessels.entity.vessels.AirshipVesselEntity;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

import java.util.HashSet;
import java.util.UUID;

public class WackyVesselsPackets {

    private static final HashSet<BlockPos> vesselBlockPositions = new HashSet<>(); // all BlockPos of vessel to be sent off

    // check if player that sent packet is actually with-in the location of block lmao
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.VESSEL_HELM_MOUNT, ((server, player, handler, buf, responseSender) -> {

            ServerWorld serverWorld = player.getServerWorld();
            BlockPos playerLocation = player.getBlockPos();

            server.execute((() -> {
                // FUCK DO STUFF WITH THING

                if (player.currentScreenHandler instanceof VesselHelmScreenHandler && (((VesselHelmScreenHandler) player.currentScreenHandler).getPos().isWithinDistance(playerLocation, 10))) { // to prevent malicious actors from messing with my clientside cod 😡 😡 😡
                    BlockPos vesselHelmLocation = ((VesselHelmScreenHandler) player.currentScreenHandler).getPos();

                    vesselBlockPositions.add(vesselHelmLocation); // starting block added to vessel
                    checkSurroundingBlocks(serverWorld, vesselHelmLocation, true);

                    AirshipVesselEntity sub = new AirshipVesselEntity(WackyVesselsEntityTypes.AIRSHIP_VESSEL_ENTITY, serverWorld);
                    sub.setPosition(SebaUtils.MathUtils.blockPosToVec3d(vesselHelmLocation));
                    sub.setSetModelDataAndBlockEntityLocations(vesselBlockPositions, vesselHelmLocation);
//                    sub.setPosition(SebaUtils.MathUtils.blockPosToVec3d(vesselHelmLocation));
                    serverWorld.spawnEntity(sub);


                    vesselBlockPositions.add(vesselHelmLocation); // reads the helm and adds again for deletion
                    for (BlockPos pos : vesselBlockPositions) {
                        serverWorld.removeBlockEntity(pos);
                        serverWorld.removeBlock(pos, true);
                    }
                    player.closeHandledScreen();
                } else {
                    player.networkHandler.disconnect(new LiteralText("NO CHEATING!!!")); // I already handled this but u never know + this line of code is funny
                }
            }));
        }));
        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.VESSEL_INPUT_PACKET, ((server, player, handler, buf, responseSender) -> {
            boolean left = buf.readBoolean();
            boolean right = buf.readBoolean();
            boolean up = buf.readBoolean();
            boolean down = buf.readBoolean();
            boolean forward = buf.readBoolean();
            boolean back = buf.readBoolean();

            int id = buf.readInt();

            server.execute(() -> {
                Entity entity = player.world.getEntityById(id);
                if (entity instanceof AirshipVesselEntity) {
                    AirshipVesselEntity airship = (AirshipVesselEntity) entity;

                    airship.setInput(left, right, up, down, forward, back);
                }
            });
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
        if (vesselBlockSize > 2000) { // for some reason, the size is slightly mismatched with the actual value but works lol
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

    public static void sendVesselInputPacket(AirshipVesselEntity entity, boolean left, boolean right, boolean up, boolean down, boolean forward, boolean back) {
        PacketByteBuf buffer = PacketByteBufs.create();

        buffer.writeBoolean(left);
        buffer.writeBoolean(right);
        buffer.writeBoolean(up);
        buffer.writeBoolean(down);
        buffer.writeBoolean(forward);
        buffer.writeBoolean(back);

        buffer.writeInt(entity.getId());

        ClientPlayNetworking.send(Constants.Packets.VESSEL_INPUT_PACKET, buffer);
    }
}
