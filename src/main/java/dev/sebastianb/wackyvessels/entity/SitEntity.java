package dev.sebastianb.wackyvessels.entity;

import dev.sebastianb.wackyvessels.entity.vessels.AbstractVesselEntity;
import dev.sebastianb.wackyvessels.network.WackyVesselsTrackedData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

// bare-bone entity used to control chair sitting
public class SitEntity extends Entity {

    private Direction direction; // TODO: Make sitting on chairs better. Prob wanna offset depending on direction

    private static final TrackedData<Direction> DIRECTION = DataTracker.registerData(SitEntity.class, new TrackedDataHandler<>() {

        @Override
        public void write(PacketByteBuf buf, Direction value) {
            buf.writeEnumConstant(value);
        }

        @Override
        public Direction read(PacketByteBuf buf) {
            return buf.readEnumConstant(Direction.class);
        }

        @Override
        public Direction copy(Direction value) {
            return value;
        }
    });

    static {
        TrackedDataHandlerRegistry.register(DIRECTION.getType());
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(DIRECTION, Direction.WEST);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setDirection(Direction.byName(nbt.getString("direction")));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (direction == null)
            return;
        for (Direction otherDirection : Direction.values()) {
            if (this.direction.asRotation() == otherDirection.asRotation()) {
                nbt.putString("direction", this.direction.asString());
                System.out.println(direction);
            }
        }
    }


    public SitEntity(EntityType<?> type, World world) {
        super(type, world);
        noClip = true;
    }

    public SitEntity(EntityType<?> type, World world, Direction direction) {
        this(type, world);
        this.setDirection(direction);

    }

    // should be the method to dismount entity at X BlockPos
    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        this.remove(RemovalReason.DISCARDED);
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        if (this.hasPassenger(passenger)) {
            passenger.setPos(this.getX() + 0.5, this.getY() - 0.2, this.getZ() + 0.5);
            passenger.setBodyYaw(getDirection().getOpposite().asRotation());
        }
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public Box getBoundingBox() {
        return new Box(this.getPos(), this.getPos().add(1,1,1));
    }


    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        dataTracker.set(DIRECTION, direction);
    }

    public Direction getDirection() {
        return dataTracker.get(DIRECTION);
    }
}
