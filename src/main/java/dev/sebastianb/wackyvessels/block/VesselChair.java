package dev.sebastianb.wackyvessels.block;

import dev.sebastianb.wackyvessels.entity.SitEntity;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VesselChair extends StairsBlock {

    public VesselChair(Settings settings) {
        super(Blocks.COBBLESTONE.getDefaultState(), settings);
        // default is cobblestone since I can't be bothered rn to look into how it actually works
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        SitEntity entity = new SitEntity(WackyVesselsEntityTypes.SIT_ENTITY, world, state.get(FACING));
        entity.updatePosition(pos.getX(), pos.getY(), pos.getZ());
        player.updatePosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        world.spawnEntity(entity);
        player.startRiding(entity);
        return ActionResult.SUCCESS;
    }
}
