package dev.sebastianb.wackyvessels.client.gui;

import dev.sebastianb.wackyvessels.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class VesselHelmScreen extends HandledScreen<VesselHelmScreenHandler> {

    private final VesselHelmScreenHandler handler;

    public VesselHelmScreen(VesselHelmScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    @Override
    protected void init() {
        this.addAssemblyButton();
        this.playerInventoryTitle = LiteralText.EMPTY;
    }

    // will display if the vessel assembles / disassembles
    private void addAssemblyButton() {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, Constants.Text.Screen.ASSEMBLY_BUTTON, (button) -> {
            // sends the location of helm to server for verification and creation
            ClientPlayNetworking.send(Constants.Packets.VESSEL_HELM_MOUNT, PacketByteBufs.empty());
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
