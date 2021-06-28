package dev.sebastianb.wackyvessels.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// bare-bone entity used to control chair sitting
public class SitEntity extends Entity {

    Direction direction; // TODO: Make sitting on chairs better. Prob wanna offset depending on direction

    public SitEntity(EntityType<?> type, World world) {
        super(type, world);
        noClip = true;
    }

    public SitEntity(EntityType<?> type, World world, Direction direction) {
        this(type, world);
        this.direction = direction;
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
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

}
