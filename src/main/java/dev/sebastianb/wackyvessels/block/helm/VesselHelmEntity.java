package dev.sebastianb.wackyvessels.block.helm;

import dev.sebastianb.wackyvessels.client.gui.VesselHelmScreenHandler;
import dev.sebastianb.wackyvessels.entity.WackyVesselsBlockEntities;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.ScreenHandler;
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

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new VesselHelmScreenHandler(syncId, inv);
    }

    // writes data in the world to the client
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {

    }
}
