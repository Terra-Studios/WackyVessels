package dev.sebastianb.wackyvessels.block.helm;

import dev.sebastianb.wackyvessels.entity.WackyVesselsBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class VesselHelmEntity extends BlockEntity implements NamedScreenHandlerFactory {

    public VesselHelmEntity(BlockPos pos, BlockState state) {
        super(WackyVesselsBlockEntities.VESSEL_HELM_ENTITY, pos, state);
        System.out.println("HI!");
    }

    @Override
    public Text getDisplayName() {
        return LiteralText.EMPTY;
    }

    // TODO: Return new screen handler
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }
}
