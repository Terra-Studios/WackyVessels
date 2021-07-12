package dev.sebastianb.wackyvessels.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {

    // prevents oxygen bubbles
    @Inject(at = @At("HEAD"), method = "isSubmergedIn", cancellable = true)
    private void isSubmergedIn(CallbackInfoReturnable<Boolean> cir) {
        //cir.setReturnValue(false);
    }

    // cancels swimming animation
    @Inject(at = @At("HEAD"), method = "updateMovementInFluid", cancellable = true)
    private void updateMovementInFluid(CallbackInfoReturnable<Boolean> cir) {
        //cir.setReturnValue(false);
    }
}
