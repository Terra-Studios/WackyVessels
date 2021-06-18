package dev.sebastianb.wackyvessels.client.gui;

import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenHandlerRegistry;
import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class VesselHelmScreenHandler extends ScreenHandler {

    public VesselHelmScreenHandler(int syncId, Inventory inventory) {
        super(WackyVesselsScreenHandlerRegistry.VESSEL_HELM_SCREEN, syncId);
    }

    public VesselHelmScreenHandler(int syncId, Inventory inventory, PacketByteBuf buf) {
        super(WackyVesselsScreenHandlerRegistry.VESSEL_HELM_SCREEN, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
