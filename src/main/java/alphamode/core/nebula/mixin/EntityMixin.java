package alphamode.core.nebula.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;

@Mixin(Entity.class)
public class EntityMixin {
    @Redirect(method = "updateMovementInFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"))
    private boolean isIn(FluidState state, Tag<Fluid> tag) {
        if (!state.getFluid().isIn(tag) && !tag.equals(FluidTags.LAVA) && state.getFluid() != Fluids.EMPTY) {
            return !state.isEmpty();
        } else {
            return state.isIn(tag);
        }
    }
}
