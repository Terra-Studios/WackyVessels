package dev.sebastianb.wackyvessels.mixin;

import dev.sebastianb.wackyvessels.entity.vessels.AirshipVesselEntity;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Shadow
    public Input input;

    @Inject(method = "tickRiding", at = @At("TAIL"))
    public void setVesselInputs(CallbackInfo ci) {
        ClientPlayerEntity self = (ClientPlayerEntity) (Object) this;
        if (self.getVehicle() instanceof AirshipVesselEntity) {
            AirshipVesselEntity airship = (AirshipVesselEntity) self.getVehicle();
            airship.readInput(input.pressingLeft, input.pressingRight, input.jumping, input.sneaking, input.pressingForward, input.pressingBack);
        }
    }
}
