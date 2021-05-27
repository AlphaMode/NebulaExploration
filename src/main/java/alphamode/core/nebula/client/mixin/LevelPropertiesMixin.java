package alphamode.core.nebula.client.mixin;

import com.mojang.serialization.Lifecycle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.LevelProperties;

@Mixin(LevelProperties.class)
public abstract class LevelPropertiesMixin {
    @Inject(method = "getLifecycle",at = @At("RETURN"), cancellable = true)
    public void annoyingmessagegoaway(CallbackInfoReturnable<Lifecycle> cir) {
        cir.setReturnValue(Lifecycle.stable());
    }
}
