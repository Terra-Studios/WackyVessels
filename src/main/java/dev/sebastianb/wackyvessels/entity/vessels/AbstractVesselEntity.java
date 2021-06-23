package dev.sebastianb.wackyvessels.entity.vessels;

import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.SerializationUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * All new vessels **NEED** to have block model set to display (setModelData). If you want to save block entity contents for future use, use (setBlockEntityLocations)
 */
public abstract class AbstractVesselEntity extends MobEntity {

    protected HashSet<BlockPos> vesselBlockPositions = new HashSet<>();

    protected HashMap<BlockPos, BlockState> relativeVesselBlockPositions = new HashMap<>(); // coords of each block relative to helm
    protected HashSet<BlockEntity> relativeVesselBlockEntityPositions = new HashSet<>(); // coords of each block entity (like chests) relative to helm

    protected boolean setModelData = false;


    private static final TrackedData<HashMap<BlockPos, BlockState>> VESSEL_MODEL_DATA = DataTracker.registerData(AbstractVesselEntity.class, new TrackedDataHandler<>() {
        @Override
        public void write(PacketByteBuf buf, HashMap<BlockPos, BlockState> value) {
            buf.writeByteArray(SerializationUtils.serialize(value)); // converts the hashmap
        }

        @Override
        public HashMap<BlockPos, BlockState> read(PacketByteBuf buf) {
            return SerializationUtils.deserialize(buf.readByteArray()); // probably reads the hashmap from buf
        }

        @Override
        public HashMap<BlockPos, BlockState> copy(HashMap<BlockPos, BlockState> value) {
            return value;
        }
    });

    private static final TrackedData<HashSet<BlockEntity>> VESSEL_BLOCK_ENTITY_DATA = DataTracker.registerData(AbstractVesselEntity.class, new TrackedDataHandler<>() {
        @Override
        public void write(PacketByteBuf buf, HashSet<BlockEntity> value) {
            buf.writeByteArray(SerializationUtils.serialize(value)); // converts the hashmap
        }

        @Override
        public HashSet<BlockEntity> read(PacketByteBuf buf) {
            return SerializationUtils.deserialize(buf.readByteArray()); // probably reads the hashmap from buf
        }

        @Override
        public HashSet<BlockEntity> copy(HashSet<BlockEntity> value) {
            return value;
        }
    });

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        int relBlocksSize = nbt.getInt("RelBlocksArraySize");
        for (int i = 1; i <= relBlocksSize; i++) {
            this.relativeVesselBlockPositions.put(
                    new BlockPos(nbt.getInt("BlockRelPosX" + "_" + i), nbt.getInt("BlockRelPosY" + "_" + i),nbt.getInt("BlockRelPosZ" + "_" + i)),
                    Block.getStateFromRawId(nbt.getInt("BlockState" + "_" + i))
            );
            System.out.println("");
        }
        int relBlockEntitySize = nbt.getInt("RelBlockEntityArraySize");
        for (int i = 1; i <= relBlockEntitySize; i++) {
            NbtCompound data = nbt.getCompound("BlockEntity" + "_" + i);
            BlockEntity blockEntity = BlockEntity.createFromNbt(NbtHelper.toBlockPos(data), NbtHelper.toBlockState(data), data);
            System.out.println(blockEntity + " read");

            this.relativeVesselBlockEntityPositions.add(blockEntity);
        }
        setRelativeVesselBlockPositions(this.relativeVesselBlockPositions);
        setRelativeVesselBlockEntityPositions(this.relativeVesselBlockEntityPositions);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        int i = 1;
        nbt.putInt("RelBlocksArraySize", this.relativeVesselBlockPositions.size());
        for (Map.Entry<BlockPos, BlockState> posBlockStateEntry : this.relativeVesselBlockPositions.entrySet()) {
            nbt.putInt("BlockRelPosX" + "_" + i, posBlockStateEntry.getKey().getX());
            nbt.putInt("BlockRelPosY" + "_" + i, posBlockStateEntry.getKey().getY());
            nbt.putInt("BlockRelPosZ" + "_" + i, posBlockStateEntry.getKey().getZ());

            nbt.putInt("BlockState" + "_" + i, Block.getRawIdFromState(posBlockStateEntry.getValue()));
            i++;
        }
        nbt.putInt("RelBlockEntityArraySize", this.relativeVesselBlockEntityPositions.size());
        i = 1;
        for (BlockEntity blockEntity : this.relativeVesselBlockEntityPositions) {
            nbt.put("BlockEntity" + "_" + i, blockEntity.writeNbt(new NbtCompound()));
            System.out.println(blockEntity + " write");
            i++;
        }

    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(VESSEL_MODEL_DATA, new HashMap<>() {{
                put(new BlockPos(0,0,0), Blocks.GOLD_BLOCK.getDefaultState()); // init data tracker
            }});
        dataTracker.startTracking(VESSEL_BLOCK_ENTITY_DATA, new HashSet<>() {{

        }});
    }

    static {
        TrackedDataHandlerRegistry.register(VESSEL_MODEL_DATA.getType());
        TrackedDataHandlerRegistry.register(VESSEL_BLOCK_ENTITY_DATA.getType());
    }

    public AbstractVesselEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
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

        relativeVesselBlockPositions.put(new BlockPos(0,0,0), world.getBlockState(helmBlockPos)); // sets the helm to be the origin of vessel
        vesselBlockPositions.remove(helmBlockPos); // removes the helm so not added in the following for loop
        for (BlockPos pos : vesselBlockPositions) {
            // for each block, get the relative position to the helm
            relativeVesselBlockPositions.put(
                    pos.subtract(helmBlockPos),
                    world.getBlockState(pos)
            );
        }
        this.setRelativeVesselBlockPositions(this.relativeVesselBlockPositions);
        setModelData = true;
    }

    /**
     * saves all block entities for future reference when disassembled (contents of stuff like chests)
     */
    public void setBlockEntityLocations(HashSet<BlockPos> vesselBlockPositions, BlockPos helmBlockPos) {
        vesselBlockPositions.add(helmBlockPos); // helm isn't added
        for (BlockPos pos : vesselBlockPositions) {

            if (world.getBlockEntity(pos) != null) {
                // save all block entities to entity when reassemble
                BlockEntity originalBlockEntity = world.getBlockEntity(pos);
                NbtCompound data = originalBlockEntity.writeNbt(new NbtCompound()); // :concern: I'm just grabbing the NBT and writing new pos values
                BlockPos newPos = pos.subtract(helmBlockPos); // sets position to be relative
                data.putInt("x", newPos.getX());
                data.putInt("y", newPos.getY());
                data.putInt("z", newPos.getZ());
                BlockEntity newBlockEntity = BlockEntity.createFromNbt(newPos, originalBlockEntity.getCachedState(), data);
                newBlockEntity.readNbt(data); // write data to NBT
                relativeVesselBlockEntityPositions.add(newBlockEntity);



//                 I need to actually save later. Following code is for testing
//                world.setBlockState(newPos, originalBlockEntity.getCachedState());
//                newBlockEntity.markDirty();
            }
            this.setRelativeVesselBlockEntityPositions(relativeVesselBlockEntityPositions);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (0 > this.getHealth() - amount) {
            this.remove(RemovalReason.DISCARDED);
            for (Map.Entry<BlockPos, BlockState> m : this.getRelativeVesselBlockPositions().entrySet()) {
                world.setBlockState(m.getKey().add(this.getBlockPos()), m.getValue());
            }
            for (BlockEntity blockEntity : this.getRelativeVesselBlockEntityPositions()) {
                NbtCompound data = blockEntity.writeNbt(new NbtCompound());
                BlockPos blockEntityRelPos = new BlockPos(data.getInt("x"), data.getInt("y"), data.getInt("z"));
                BlockPos newBlockEntityLocation = this.getBlockPos().subtract(blockEntityRelPos);
                data.putInt("x", newBlockEntityLocation.getX());
                data.putInt("y", newBlockEntityLocation.getY());
                data.putInt("z", newBlockEntityLocation.getZ());
                BlockEntity newBlockEntity = BlockEntity.createFromNbt(newBlockEntityLocation, blockEntity.getCachedState(), data);

                world.addBlockEntity(newBlockEntity);
                newBlockEntity.readNbt(data); // write data to NBT
                System.out.println(newBlockEntity.getPos() + " " + newBlockEntity.getCachedState());

                newBlockEntity.markDirty();

            }
        }

        return super.damage(source, amount);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        super.takeKnockback(0, 0, 0);
    }

    public HashMap<BlockPos, BlockState> getRelativeVesselBlockPositions() {
        return dataTracker.get(VESSEL_MODEL_DATA);
    }

    public HashSet<BlockEntity> getRelativeVesselBlockEntityPositions() {
        return dataTracker.get(VESSEL_BLOCK_ENTITY_DATA);
    }

    public void setRelativeVesselBlockPositions(HashMap<BlockPos, BlockState> relativeVesselBlockPositions) {
        this.relativeVesselBlockPositions = relativeVesselBlockPositions;
        dataTracker.set(VESSEL_MODEL_DATA, relativeVesselBlockPositions);
    }

    public void setRelativeVesselBlockEntityPositions(HashSet<BlockEntity> relativeVesselBlockEntityPositions) {
        this.relativeVesselBlockEntityPositions = relativeVesselBlockEntityPositions;
        dataTracker.set(VESSEL_BLOCK_ENTITY_DATA, relativeVesselBlockEntityPositions);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }
}