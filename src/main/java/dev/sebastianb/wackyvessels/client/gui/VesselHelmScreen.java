package dev.sebastianb.wackyvessels.client.gui;

import dev.sebastianb.wackyvessels.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class VesselHelmScreen extends HandledScreen<VesselHelmScreenHandler> {

    private VesselHelmScreenHandler handler;

    public VesselHelmScreen(VesselHelmScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    //This method will try to get the Position from the ScreenHandler, as ScreenRendering only happens on the client we
    //get the ScreenHandler instance here which has the correct BlockPos in it!
    private static BlockPos getBlockPosition(ScreenHandler handler) {
        if (handler instanceof VesselHelmScreenHandler) {
            return ((VesselHelmScreenHandler) handler).getPos();
        } else {
            return BlockPos.ORIGIN;
        }
    }

    @Override
    protected void init() {
        this.addAssemblyButton();
        this.playerInventoryTitle = LiteralText.EMPTY;
    }

    // will display if the vessel assembles / disassembles
    private void addAssemblyButton() {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, ScreenTexts.DONE, (button) -> {
            // sends the location of helm to server for verification and creation
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(getBlockPosition(handler));
            ClientPlayNetworking.send(Constants.Packets.VESSEL_HELM_MOUNT, buf);

        }));
    }



    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // this.renderBackground(matrices);
    }

}
