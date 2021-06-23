package dev.sebastianb.wackyvessels.client.gui;

import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import dev.sebastianb.wackyvessels.registries.WackyVesselsScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VesselHelmScreenHandler extends ScreenHandler {

    private final BlockPos pos;
    private ScreenHandlerContext context;

    public VesselHelmScreenHandler(int syncId, Inventory inventory, PacketByteBuf buf) {
        super(WackyVesselsScreenHandlerRegistry.VESSEL_HELM_SCREEN, syncId);
        pos = buf.readBlockPos(); // reads from block entity
    }

    public VesselHelmScreenHandler(int syncId, World world, BlockPos pos) {
        super(WackyVesselsScreenHandlerRegistry.VESSEL_HELM_SCREEN, syncId);
        this.context = ScreenHandlerContext.create(world, pos);
        this.pos = pos;
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
