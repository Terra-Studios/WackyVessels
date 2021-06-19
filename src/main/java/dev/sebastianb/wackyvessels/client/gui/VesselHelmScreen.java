package dev.sebastianb.wackyvessels.client.gui;

import dev.sebastianb.wackyvessels.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class VesselHelmScreen extends HandledScreen<VesselHelmScreenHandler> {


    public VesselHelmScreen(VesselHelmScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

    }

    @Override
    protected void init() {
        this.addAssemblyButton();
        this.playerInventoryTitle = LiteralText.EMPTY;
    }

    // will display if the vessel assembles / disassembles
    private void addAssemblyButton() {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, ScreenTexts.DONE, (button) -> {
            this.client.openScreen((Screen)null);
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
