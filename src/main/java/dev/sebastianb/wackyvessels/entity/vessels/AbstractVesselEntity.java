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

/**
 * All new vessels **NEED** to have block model set to display (setModelData). If you want to save block entity contents for future use, use (setBlockEntityLocations)
 */
public class AbstractVesselEntity extends MobEntity {

    protected HashSet<BlockPos> vesselBlockPositions = new HashSet<>();

    protected HashSet<BlockPos> relativeVesselBlockPositions = new HashSet<>(); // coords of each block relative to helm
    protected HashMap<BlockEntity, BlockPos> relativeVesselBlockEntityPositions = new HashMap<>(); // coords of each block entity (like chests) relative to helm

    protected boolean setModelData = false;


    public AbstractVesselEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }


    /**
     * Use this method to cover the following methods
     */
    public void setSetModelDataAndBlockEntityLocations(HashSet<BlockPos> vesselBlockPositions, BlockPos helmBlockPos) {
        this.setModelData(vesselBlockPositions, helmBlockPos);
        this.setBlockEntityLocations(vesselBlockPositions, helmBlockPos);

    }

    /**
     * saves all block positions for display to client (entity model)
     */
    public void setModelData(HashSet<BlockPos> vesselBlockPositions, BlockPos helmBlockPos) {
        this.vesselBlockPositions = vesselBlockPositions;

        setModelData = true;
    }

    /**
     * saves all block entities for future reference when disassembled (contents of stuff like chests)
     */
    public void setBlockEntityLocations(HashSet<BlockPos> vesselBlockPositions, BlockPos helmBlockPos) {
        for (BlockPos pos : vesselBlockPositions) {

            if (world.getBlockEntity(pos) != null) {
                // save all block entities to entity when reassemble
                BlockEntity originalBlockEntity = world.getBlockEntity(pos);
                NbtCompound data = originalBlockEntity.writeNbt(new NbtCompound()); // :concern: I'm just grabbing the NBT and writing new pos values

                BlockPos newPos = pos.add(0,10,0);
                data.putInt("x", newPos.getX());
                data.putInt("y", newPos.getY());
                data.putInt("z", newPos.getZ());

                // I need to actually save later. Following code is for testing
//                world.setBlockState(newPos, originalBlockEntity.getCachedState());
//                BlockEntity newBlockEntity = world.getBlockEntity(newPos);
//                newBlockEntity.readNbt(data); // write old NBT data to new block entity
//                newBlockEntity.markDirty();

            } else {

            }

        }
    }



    @Override
    public boolean cannotDespawn() {
        return true;
    }
}
