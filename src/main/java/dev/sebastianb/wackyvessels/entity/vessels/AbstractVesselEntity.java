package dev.sebastianb.wackyvessels.entity.vessels;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;

public class AbstractVesselEntity extends MobEntity {

    protected HashSet<BlockPos> vesselBlockPositions = new HashSet<>();

    protected HashSet<BlockPos> relativeVesselBlockPositions = new HashSet<>(); // coords of each block relative to helm
    protected HashMap<BlockEntity, BlockPos> relativeVesselBlockEntityPositions = new HashMap<>(); // coords of each block entity (like chests) relative to helm

    protected boolean setModelData = false;


    public AbstractVesselEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setModelData(HashSet<BlockPos> vesselBlockPositions, BlockPos helmBlockPos) {
        this.vesselBlockPositions = vesselBlockPositions;
        for (BlockPos pos : vesselBlockPositions) {

            if (world.getBlockEntity(pos) != null) {
                BlockEntity originalBlockEntity = world.getBlockEntity(pos);
                NbtCompound data = originalBlockEntity.writeNbt(new NbtCompound()); // :concern: I'm just grabbing the NBT and writing new pos values

                BlockPos newPos = pos.add(0,10,0);
                data.putInt("x", newPos.getX());
                data.putInt("y", newPos.getY());
                data.putInt("z", newPos.getZ());

                world.setBlockState(newPos, originalBlockEntity.getCachedState());
                BlockEntity newBlockEntity = world.getBlockEntity(newPos);
                newBlockEntity.readNbt(data);
                newBlockEntity.markDirty();

            } else {

            }

        }
        setModelData = true;
    }



    @Override
    public boolean cannotDespawn() {
        return true;
    }
}
