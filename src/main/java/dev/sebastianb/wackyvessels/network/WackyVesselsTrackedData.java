package dev.sebastianb.wackyvessels.network;

import dev.sebastianb.wackyvessels.entity.dimensions.EntityDimensionXYZ;
import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class WackyVesselsTrackedData {

    public static final TrackedData<Map<BlockPos, BlockState>> VESSEL_MODEL_DATA = DataTracker.registerData(AbstractVesselEntity.class, new TrackedDataHandler<>() {
        @Override
        public void write(PacketByteBuf buf, Map<BlockPos, BlockState> value) {
            buf.writeInt(value.size());
            for (Map.Entry<BlockPos, BlockState> e : value.entrySet()) {
                buf.writeLong(e.getKey().asLong());
                buf.writeInt(Block.getRawIdFromState(e.getValue()));
            }
        }

        @Override
        public Map<BlockPos, BlockState> read(PacketByteBuf buf) {
            int size = buf.readInt();
            Map<BlockPos, BlockState> map = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                map.put(BlockPos.fromLong(buf.readLong()), Block.getStateFromRawId(buf.readInt()));
            }
            return map;
        }

        @Override
        public Map<BlockPos, BlockState> copy(Map<BlockPos, BlockState> value) {
            return new HashMap<>(value);
        }
    });

    public static final TrackedData<Map<BlockPos, BlockEntity>> VESSEL_BLOCK_ENTITY_DATA = DataTracker.registerData(AbstractVesselEntity.class, new TrackedDataHandler<>() {
        @Override
        public void write(PacketByteBuf buf, Map<BlockPos, BlockEntity> value) {
            buf.writeInt(value.size());
            for (Map.Entry<BlockPos, BlockEntity> e : value.entrySet()) {
                buf.writeLong(e.getKey().asLong());
                buf.writeString(Registry.BLOCK_ENTITY_TYPE.getId(e.getValue().getType()).toString());
                buf.writeNbt(e.getValue().writeNbt(new NbtCompound()));
            }
        }

        @Override
        public Map<BlockPos, BlockEntity> read(PacketByteBuf buf) {
            int size = buf.readInt();
            Map<BlockPos, BlockEntity> map = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                BlockPos blockPos = BlockPos.fromLong(buf.readLong());
                BlockEntity be = Registry.BLOCK_ENTITY_TYPE.get(new Identifier(buf.readString())).instantiate(blockPos, Blocks.AIR.getDefaultState());
                be.readNbt(buf.readNbt());
                map.put(blockPos, be); //figure out blocks from type?
            }
            return map;
        }

        @Override
        public Map<BlockPos, BlockEntity> copy(Map<BlockPos, BlockEntity> value) {
            return new HashMap<>(value);
        }
    });

    public static final TrackedData<EntityDimensionXYZ> ENTITY_DIMENSION_XYZ = DataTracker.registerData(AbstractVesselEntity.class, new TrackedDataHandler<>() {
        @Override
        public void write(PacketByteBuf data, EntityDimensionXYZ object) {
            data.writeFloat(object.length);
            data.writeFloat(object.width);
            data.writeFloat(object.height);
            data.writeBoolean(object.fixed);
        }

        @Override
        public EntityDimensionXYZ read(PacketByteBuf buf) {
            float l = buf.readFloat();
            float w = buf.readFloat();
            float h = buf.readFloat();
            boolean f = buf.readBoolean();
            return new EntityDimensionXYZ(l, w, h, f);
        }

        @Override
        public EntityDimensionXYZ copy(EntityDimensionXYZ object) {
            return object;
        }
    });

}
