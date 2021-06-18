package dev.sebastianb.wackyvessels.block.helm;

import dev.sebastianb.wackyvessels.entity.WackyVesselsBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class VesselHelm extends BlockWithEntity {


    public VesselHelm(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VesselHelmEntity(pos, state);
    }


}
