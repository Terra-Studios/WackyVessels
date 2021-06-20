package dev.sebastianb.wackyvessels.client.gui;

import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenHandlerRegistry;
import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VesselHelmScreenHandler extends ScreenHandler {

    private BlockPos pos;
    private ScreenHandlerContext context;

    public VesselHelmScreenHandler(int syncId, Inventory inventory, PacketByteBuf buf) {
        super(WackyVesselsScreenHandlerRegistry.VESSEL_HELM_SCREEN, syncId);
        pos = buf.readBlockPos(); // reads from block entity
    }
    public VesselHelmScreenHandler(int syncId, Inventory inventory, ScreenHandlerContext context) {
        super(WackyVesselsScreenHandlerRegistry.VESSEL_HELM_SCREEN, syncId);
        this.context = context;

    }


    // used by screen class
    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, WackyVesselsBlocks.VESSEL_HELM);
    }

}
