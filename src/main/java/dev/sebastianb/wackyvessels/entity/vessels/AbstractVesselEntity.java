package dev.sebastianb.wackyvessels.entity.vessels;

import dev.sebastianb.wackyvessels.SebaUtils;
import dev.sebastianb.wackyvessels.WackyVessels;
import dev.sebastianb.wackyvessels.block.WackyVesselsBlocks;
import dev.sebastianb.wackyvessels.collision.VesselBoxDelegate;
import dev.sebastianb.wackyvessels.entity.SitEntity;
import dev.sebastianb.wackyvessels.entity.WackyVesselsEntityTypes;
import dev.sebastianb.wackyvessels.entity.dimensions.EntityDimensionXYZ;
import dev.sebastianb.wackyvessels.network.WackyVesselsTrackedData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.*;

/**
 * All new vessels **NEED** to have block model set to display (setModelData). If you want to save block entity contents for future use, use (setBlockEntityLocations)
 */
public abstract class AbstractVesselEntity extends Entity {

    protected EntityDimensionXYZ entityDimensionXYZ;
    protected HashSet<BlockPos> vesselBlockPositions = new HashSet<>();

    protected Map<BlockPos, BlockState>     relativeVesselBlockPositions    = new HashMap<>(); // coords of each block relative to helm
    protected Map<BlockPos, BlockEntity>    relativeVesselBlockEntity       = new HashMap<>(); // coords of each block entity (like chests) relative to helm
    protected Map<BlockPos, BlockState>     relativeVesselChairPositions    = new HashMap<>(); // coords of each useable seat relative to helm
    protected BlockPos                      masterChairRelPosition          = BlockPos.ORIGIN.add(1,1,1); // coords for the master chair relative to helm

    protected boolean setModelData = false;


    private static final TrackedData<Map<BlockPos, BlockState>> VESSEL_MODEL_DATA = WackyVesselsTrackedData.VESSEL_MODEL_DATA;
    private static final TrackedData<Map<BlockPos, BlockEntity>> VESSEL_BLOCK_ENTITY_DATA = WackyVesselsTrackedData.VESSEL_BLOCK_ENTITY_DATA;
    private static final TrackedData<EntityDimensionXYZ> ENTITY_DIMENSION_XYZ = WackyVesselsTrackedData.ENTITY_DIMENSION_XYZ;

    private static final TrackedData<Map<BlockPos, BlockState>> VESSEL_CHAIR_DATA = WackyVesselsTrackedData.VESSEL_CHAIR_POS;
    private static final TrackedData<BlockPos> VESSEL_MASTER_CHAIR = DataTracker.registerData(AbstractVesselEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);

    static {
        TrackedDataHandlerRegistry.register(VESSEL_MODEL_DATA.getType());
        TrackedDataHandlerRegistry.register(VESSEL_BLOCK_ENTITY_DATA.getType());
        assert TrackedDataHandlerRegistry.getId(VESSEL_BLOCK_ENTITY_DATA.getType()) != -1;
        TrackedDataHandlerRegistry.register(ENTITY_DIMENSION_XYZ.getType());
        assert TrackedDataHandlerRegistry.getId(VESSEL_CHAIR_DATA.getType()) != -1;
        TrackedDataHandlerRegistry.register(VESSEL_CHAIR_DATA.getType());
        TrackedDataHandlerRegistry.register(VESSEL_MASTER_CHAIR.getType());
    }

    public static void initClass() { //need to register ^ somehow it doesent normally??
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        int relBlocksSize = nbt.getInt("RelBlocksArraySize");
        for (int i = 1; i <= relBlocksSize; i++) {
            this.relativeVesselBlockPositions.put(
                    new BlockPos(nbt.getInt("BlockRelPosX" + "_" + i), nbt.getInt("BlockRelPosY" + "_" + i),nbt.getInt("BlockRelPosZ" + "_" + i)),
                    Block.getStateFromRawId(nbt.getInt("BlockState" + "_" + i))
            );
        }
        int relBlockEntitySize = nbt.getInt("RelBlockEntityArraySize");
        for (int i = 1; i <= relBlockEntitySize; i++) {
            NbtCompound data = nbt.getCompound("BlockEntity" + "_" + i);

            BlockEntity blockEntity = BlockEntity.createFromNbt(NbtHelper.toBlockPos(data), NbtHelper.toBlockState(data), data);
            blockEntity.readNbt(data);
            blockEntity.markDirty();
            this.relativeVesselBlockEntity.put(
                    new BlockPos(
                            nbt.getInt("BlockEntityRelPosX" + "_" + i),
                            nbt.getInt("BlockEntityRelPosY" + "_" + i),
                            nbt.getInt("BlockEntityRelPosZ" + "_" + i)
                    ), blockEntity);
        }

        int relChairLocationSize = nbt.getInt("RelChairLocationArraySize");
        for (int i = 1; i <= relChairLocationSize; i++) {
            this.relativeVesselChairPositions.put(
                    new BlockPos(
                            nbt.getInt("ChairRelPosX" + "_" + i),
                            nbt.getInt("ChairRelPosY" + "_" + i),
                            nbt.getInt("ChairRelPosZ" + "_" + i)
                    ), Block.getStateFromRawId(nbt.getInt("ChairBlockState" + "_" + i))
            );
        }

        // set master chair
        int chairX = nbt.getInt("ChairPosX");
        int chairY = nbt.getInt("ChairPosY");
        int chairZ = nbt.getInt("ChairPosZ");

        float length = nbt.getFloat("length");
        float width = nbt.getFloat("width");
        float height = nbt.getFloat("height");
        setRelativeVesselBlockPositions(this.relativeVesselBlockPositions);
        setRelativeVesselBlockEntity(this.relativeVesselBlockEntity);
        setEntityDimensionXYZ(new EntityDimensionXYZ(length,height,width, false));
        setRelativeVesselChairPositions(this.relativeVesselChairPositions);
        setMasterChairRelPosition(new BlockPos(chairX, chairY, chairZ));
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
        nbt.putInt("RelBlockEntityArraySize", this.relativeVesselBlockEntity.size());
        i = 1;
        for (Map.Entry<BlockPos, BlockEntity> blockEntityWithRelPos : this.relativeVesselBlockEntity.entrySet()) {
            NbtCompound data = blockEntityWithRelPos.getValue().writeNbt(new NbtCompound());

            nbt.putInt("BlockEntityRelPosX" + "_" + i, data.getInt("x"));
            nbt.putInt("BlockEntityRelPosY" + "_" + i, data.getInt("y"));
            nbt.putInt("BlockEntityRelPosZ" + "_" + i, data.getInt("z"));

            nbt.put("BlockEntity" + "_" + i, data);
            i++;
        }
        i = 1;
        nbt.putInt("RelChairLocationArraySize", this.relativeVesselChairPositions.size());
        for (Map.Entry<BlockPos, BlockState> posChairEntry : this.relativeVesselChairPositions.entrySet()) {
            nbt.putInt("ChairRelPosX" + "_" + i, posChairEntry.getKey().getX());
            nbt.putInt("ChairRelPosY" + "_" + i, posChairEntry.getKey().getY());
            nbt.putInt("ChairRelPosZ" + "_" + i, posChairEntry.getKey().getZ());

            nbt.putInt("ChairBlockState" + "_" + i, Block.getRawIdFromState(posChairEntry.getValue()));
            i++;
        }

        nbt.putInt("ChairPosX", this.masterChairRelPosition.getX());
        nbt.putInt("ChairPosY", this.masterChairRelPosition.getY());
        nbt.putInt("ChairPosZ", this.masterChairRelPosition.getZ());

        nbt.putFloat("length", this.entityDimensionXYZ.length);
        nbt.putFloat("width", this.entityDimensionXYZ.width);
        nbt.putFloat("height", this.entityDimensionXYZ.height);

    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(VESSEL_MODEL_DATA, new HashMap<>() {{
                put(BlockPos.ORIGIN, Blocks.GOLD_BLOCK.getDefaultState()); // init data tracker
        }});
        dataTracker.startTracking(VESSEL_BLOCK_ENTITY_DATA, new HashMap<>());
        dataTracker.startTracking(VESSEL_CHAIR_DATA, new HashMap<>() {{
            put(BlockPos.ORIGIN.add(1,1,1), WackyVesselsBlocks.VESSEL_CHAIR.getDefaultState());
        }});
        dataTracker.startTracking(VESSEL_MASTER_CHAIR, BlockPos.ORIGIN.add(1,1,1));
        dataTracker.startTracking(ENTITY_DIMENSION_XYZ, new EntityDimensionXYZ(1,1,1, false));
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ENTITY_DIMENSION_XYZ.equals(data)) {
            dimensions = getDataTracker().get(ENTITY_DIMENSION_XYZ);
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    public AbstractVesselEntity(EntityType entityType, World world) {
        super(entityType, world);
        this.noClip = false;
    }

    /**
     * Use this method to cover the following methods
     */
    public void setSetModelDataAndBlockEntityLocations(HashSet<BlockPos> vesselBlockPositions, BlockPos helmBlockPos) {
        this.setModelData(vesselBlockPositions, helmBlockPos);
        this.setBlockEntityLocations(vesselBlockPositions, helmBlockPos);

        // TODO: Save this to entity
        // let's find the entity size
        HashSet<BlockPos> relBlockPos = new HashSet<>();
        for (Map.Entry<BlockPos, BlockState> blockPosBlockStateEntry : getRelativeVesselBlockPositions().entrySet()) {
            relBlockPos.add(blockPosBlockStateEntry.getKey());
            if (blockPosBlockStateEntry.getValue().getBlock().getDefaultState() == WackyVesselsBlocks.VESSEL_CHAIR.getDefaultState()) {
                this.relativeVesselChairPositions.put(blockPosBlockStateEntry.getKey(), blockPosBlockStateEntry.getValue());
                // set all chairs inside the vessel
            }
        }
        this.setRelativeVesselChairPositions(relativeVesselChairPositions); // syncs with server the relative chairs
        // get the relative minimum and max corner
        BlockPos smallCorner = SebaUtils.MathUtils.getSmallestBlockPos(relBlockPos);
        BlockPos bigCorner = SebaUtils.MathUtils.getLargestBlockPos(relBlockPos);

        // set dimensions of vessel
        this.setEntityDimensionXYZ(new EntityDimensionXYZ(
                bigCorner.getX() - smallCorner.getX() + 1,
                bigCorner.getY() - smallCorner.getY() + 1,
                bigCorner.getZ() - smallCorner.getZ() + 1,
                false
        ));
        ServerWorld serverWorld = (ServerWorld) this.world;
        List<Entity> entities = serverWorld.getOtherEntities(this,
                // for some weird reason, it wouldn't just take one method
                new Box(
                        getBoundingBox().maxX,
                        getBoundingBox().maxY,
                        getBoundingBox().maxZ,
                        getBoundingBox().minX,
                        getBoundingBox().maxY,
                        getBoundingBox().minZ
                ),
                EntityPredicates.EXCEPT_SPECTATOR);
        // lets get rid of any existing chair entity.
        // TODO: for some reason, I'm not able to get the actual chair entity in list. Maybe looking into fixing?
        for (Entity entity : entities) {
            if (entity instanceof SitEntity chair) {
                if (chair.getFirstPassenger() instanceof PlayerEntity playerEntity) {
                    playerEntity.startRiding(this, true);
                }
                entity.remove(RemovalReason.DISCARDED); // removes chair entity any players may be sitting on
            }
        }

    }

    // test method
    @Override
    public void tick() {
        super.tick();
//        System.out.println(world.getOtherEntities(this, this.getBoundingBox(), EntityPredicates.EXCEPT_SPECTATOR));
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
                relativeVesselBlockEntity.put(newBlockEntity.getPos(), newBlockEntity);
                newBlockEntity.markDirty();
            }
            this.setRelativeVesselBlockEntity(relativeVesselBlockEntity);
        }
    }

    public void tryDisassemble() {
        this.remove(RemovalReason.DISCARDED);

        int direction = Math.round(-this.getYaw() / 90.0f) + 4;
        direction = direction % 4;

        for (Map.Entry<BlockPos, BlockState> m : this.getRelativeVesselBlockPositions().entrySet()) {

            BlockRotation rotation;
            switch (direction) {
                case 1:
                    rotation = BlockRotation.CLOCKWISE_90;
                    break;
                case 2:
                    rotation = BlockRotation.CLOCKWISE_180;
                    break;
                case 3:
                    rotation = BlockRotation.COUNTERCLOCKWISE_90;
                    break;
                default:
                    rotation = BlockRotation.NONE;
            }

            BlockPos pos = m.getKey().rotate(rotation);

            BlockState state = m.getValue().rotate(rotation);

            world.setBlockState(pos.add(this.getBlockPos()), state);
        }

        for (Map.Entry<BlockPos, BlockEntity> blockEntityWithRelPos : this.getRelativeVesselBlockEntity().entrySet()) {

            BlockRotation rotation;
            switch (direction) {
                case 1:
                    rotation = BlockRotation.CLOCKWISE_90;
                    break;
                case 2:
                    rotation = BlockRotation.CLOCKWISE_180;
                    break;
                case 3:
                    rotation = BlockRotation.COUNTERCLOCKWISE_90;
                    break;
                default:
                    rotation = BlockRotation.NONE;
            }

            NbtCompound data = blockEntityWithRelPos.getValue().writeNbt(new NbtCompound());

            BlockPos blockEntityRelPos = blockEntityWithRelPos.getKey().rotate(rotation).multiply(-1);
            BlockPos newBlockEntityLocation = this.getBlockPos().subtract(blockEntityRelPos);

            data.putInt("x", newBlockEntityLocation.getX());
            data.putInt("y", newBlockEntityLocation.getY());
            data.putInt("z", newBlockEntityLocation.getZ());

            BlockEntity newBlockEntity = BlockEntity.createFromNbt(newBlockEntityLocation, blockEntityWithRelPos.getValue().getCachedState(), data);
            newBlockEntity.readNbt(data); // write data to NBT
            world.addBlockEntity(newBlockEntity);

            newBlockEntity.markDirty();
        }
    }

    @Override
    public Box getBoundingBox() {
        // TODO: Save this to entity
        // let's find the entity size
        HashSet<BlockPos> relBlockPos = new HashSet<>();
        for (Map.Entry<BlockPos, BlockState> blockPosBlockStateEntry : getRelativeVesselBlockPositions().entrySet()) {
            relBlockPos.add(blockPosBlockStateEntry.getKey());
        }
        // get the relative minimum and max corner
        BlockPos smallCorner = SebaUtils.MathUtils.getSmallestBlockPos(relBlockPos);
        BlockPos bigCorner = SebaUtils.MathUtils.getLargestBlockPos(relBlockPos);
        return new VesselBoxDelegate(new Box(
                this.getPos().add(bigCorner.getX() + 1, bigCorner.getY() + 1, bigCorner.getZ() + 1),
                this.getPos().add(smallCorner.getX(),smallCorner.getY(),smallCorner.getZ())));
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean collides() { // Required to interact with the entity
        return true;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.isSneaking()) { //test for rotations
            this.setRotation(this.getYaw()+ 90, this.getPitch());
            return ActionResult.SUCCESS;
        }

        if (this.getPassengerList().isEmpty()) {
            player.startRiding(this);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        tryDisassemble(); // test method for disassemble
        return super.damage(source, amount);
    }

    @Override
    public double getMountedHeightOffset() {
        return 0;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        if (this.hasPassenger(passenger)) {
            passenger.setPos(this.getX(), this.getY(), this.getZ() - 1);
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    public Map<BlockPos, BlockState> getRelativeVesselBlockPositions() {
        return dataTracker.get(VESSEL_MODEL_DATA);
    }

    public Map<BlockPos, BlockEntity> getRelativeVesselBlockEntity() {
        return dataTracker.get(VESSEL_BLOCK_ENTITY_DATA);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return dataTracker.get(ENTITY_DIMENSION_XYZ);
    }

    public EntityDimensionXYZ getDimensionsXYZ() {
        return dataTracker.get(ENTITY_DIMENSION_XYZ);
    }

    public Map<BlockPos, BlockState> getRelativeVesselChairPositions() {
        return dataTracker.get(VESSEL_CHAIR_DATA);
    }

    public BlockPos getMasterChairRelPosition() {
        return dataTracker.get(VESSEL_MASTER_CHAIR);
    }

    public void setRelativeVesselBlockPositions(Map<BlockPos, BlockState> relativeVesselBlockPositions) {
        this.relativeVesselBlockPositions = relativeVesselBlockPositions;
        dataTracker.set(VESSEL_MODEL_DATA, Collections.emptyMap()); // maps are mutable
        dataTracker.set(VESSEL_MODEL_DATA, relativeVesselBlockPositions);
    }

    public void setRelativeVesselBlockEntity(Map<BlockPos, BlockEntity> relativeVesselBlockEntity) {
        this.relativeVesselBlockEntity = relativeVesselBlockEntity;
        dataTracker.set(VESSEL_BLOCK_ENTITY_DATA, Collections.emptyMap()); // maps are mutable
        dataTracker.set(VESSEL_BLOCK_ENTITY_DATA, relativeVesselBlockEntity);
    }

    public void setEntityDimensionXYZ(EntityDimensionXYZ entityDimensionXYZ) {
        this.dimensions = entityDimensionXYZ;
        this.entityDimensionXYZ = entityDimensionXYZ;
        dataTracker.set(ENTITY_DIMENSION_XYZ, entityDimensionXYZ);
    }

    public void setRelativeVesselChairPositions(Map<BlockPos, BlockState> relativeVesselChairPositions) {
        this.relativeVesselChairPositions = relativeVesselChairPositions;
        dataTracker.set(VESSEL_CHAIR_DATA, Collections.emptyMap()); // maps are mutable
        dataTracker.set(VESSEL_CHAIR_DATA, relativeVesselChairPositions);

    }

    public void setMasterChairRelPosition(BlockPos masterChairRelPosition) {
        this.masterChairRelPosition = masterChairRelPosition;
        dataTracker.set(VESSEL_MASTER_CHAIR, masterChairRelPosition);
    }
}