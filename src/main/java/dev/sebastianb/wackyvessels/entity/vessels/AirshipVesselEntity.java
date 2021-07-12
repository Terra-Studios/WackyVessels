package dev.sebastianb.wackyvessels.entity.vessels;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.network.WackyVesselsPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AirshipVesselEntity extends AbstractVesselEntity{

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean forward;
    private boolean back;

    public float movementSpeed = 1.0f;

    public AirshipVesselEntity(EntityType<AirshipVesselEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (isLogicalSideForUpdatingMovement() && world.isClient) {
            readInput((ClientPlayerEntity) getPrimaryPassenger());
        }

        updateMotion();
    }

    public void updateMotion() {

        var left = this.left ? 1 : 0;
        var right = this.right ? -1 : 0;
                var up = this.up ? 1 : 0;
                var down = this.down ? -1 : 0;
                var forward = this.forward ? 1 : 0;
                var back = this.back ? -1 : 0;

        Vec3d movementRelativeToVessel = new Vec3d(left + right, up + down, forward + back);

        if (!movementRelativeToVessel.equals(Vec3d.ZERO)) {
            double yChange = -Math.sin(Math.toRadians(getPitch())) * movementRelativeToVessel.getZ();
            double horizontalChange = Math.cos(Math.toRadians(getPitch())) * movementRelativeToVessel.getZ();

            double xChange = Math.sin(Math.toRadians(getYaw())) * horizontalChange;
            double zChange = Math.cos(Math.toRadians(getYaw())) * horizontalChange;

            yChange += Math.cos(Math.toRadians(getPitch())) * movementRelativeToVessel.getY();
            horizontalChange = Math.sin(Math.toRadians(getPitch())) * movementRelativeToVessel.getY();

            xChange += Math.sin(Math.toRadians(getYaw())) * horizontalChange;
            zChange += Math.cos(Math.toRadians(getYaw())) * horizontalChange;

            xChange += Math.sin(Math.toRadians(getYaw() + 90)) * movementRelativeToVessel.getX();
            zChange += Math.cos(Math.toRadians(getYaw() + 90)) * movementRelativeToVessel.getX();


//        movementRelativeToVessel = movementRelativeToVessel.rotateX(this.getYaw());
//        movementRelativeToVessel = movementRelativeToVessel.rotateY(this.getPitch());

            this.move(MovementType.SELF, new Vec3d(xChange, yChange, zChange));
        }



        if (this.getPrimaryPassenger() != null) {
            this.setPitch(getPrimaryPassenger().getPitch());
            this.setYaw(-getPrimaryPassenger().getYaw());
        }
    }

    public void setInput(boolean left, boolean right, boolean up, boolean down, boolean forward, boolean back) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.forward = forward;
        this.back = back;
    }

    @Environment(EnvType.CLIENT)
    public void readInput(ClientPlayerEntity player) {

        Input input = player.input;

        boolean left = input.pressingLeft;
        boolean right = input.pressingRight;
        boolean up = input.jumping;
        boolean forward = input.pressingForward;
        boolean back = input.pressingBack;

        if (!(left == this.left && right == this.right && up == this.up && down == this.down && forward == this.forward && back == this.back)) {
            setInput(left, right, up, down, forward, back);
            WackyVesselsPackets.sendVesselInputPacket(this, left, right, up, down, forward, back);
        }
    }

    @Environment(EnvType.CLIENT)
    public void readInput(boolean down) {
        if (!(left == this.left && right == this.right && up == this.up && down == this.down && forward == this.forward && back == this.back)) {
            setInput(left, right, up, down, forward, back);
            WackyVesselsPackets.sendVesselInputPacket(this, left, right, up, down, forward, back);
        }
    }
}
