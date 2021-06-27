package dev.sebastianb.wackyvessels.mixin;

import dev.sebastianb.wackyvessels.collision.VesselBoxDelegate;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Box.class)
public class MixinBox {

    // useful method to cancel interactions between all blocks. We'll assume to use our own
    @Inject(method = "intersects(Lnet/minecraft/util/math/Box;)Z", at = @At("HEAD"), cancellable = true)
    private void intersects(Box box, CallbackInfoReturnable<Boolean> cir) {
        if (box instanceof VesselBoxDelegate) // doesn't work rn but I wanna cancel vanillas system for our own
            cir.setReturnValue(false);
    }
}
