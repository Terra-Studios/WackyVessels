package dev.sebastianb.wackyvessels.network;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.WackyVessels;
import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

public class WackyVesselsPackets {

    // check if player that sent packet is actually with-in the location of block lmao
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.VESSEL_HELM_MOUNT, ((server, player, handler, buf, responseSender) -> {

            ServerWorld serverWorld = player.getServerWorld();
            BlockPos vesselHelmLocation = buf.readBlockPos();
            BlockPos playerLocation = player.getBlockPos();

            server.execute((() -> {
                // FUCK DO STUFF WITH THING
                boolean canUseVessel = (vesselHelmLocation.isWithinDistance(playerLocation, 8)) && (serverWorld.getBlockState(vesselHelmLocation) == WackyVesselsBlocks.VESSEL_HELM.getDefaultState());
                if (canUseVessel) { // to prevent malicious actors from messing with my clientside cod 😡 😡 😡
                    serverWorld.setBlockState(vesselHelmLocation, Blocks.DIAMOND_BLOCK.getDefaultState());
                } else {
                    player.sendMessage(new LiteralText("NO CHEATING!!!"), false);
                    player.closeHandledScreen();
                }
            }));
        }));
    }

}
