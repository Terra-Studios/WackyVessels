package dev.sebastianb.wackyvessels.block.helm;

import dev.sebastianb.wackyvessels.client.gui.VesselHelmScreenHandler;
import dev.sebastianb.wackyvessels.entity.WackyVesselsBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class VesselHelmEntity extends BlockEntity implements ExtendedScreenHandlerFactory {

    public VesselHelmEntity(BlockPos pos, BlockState state) {
        super(WackyVesselsBlockEntities.VESSEL_HELM_ENTITY, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return LiteralText.EMPTY;
    }

    // creates menu with context needed
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new VesselHelmScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos));
    }

    // writes data in the world to the client. Called on the server and sends data off to the client
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

}
