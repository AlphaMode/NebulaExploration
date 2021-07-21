package alphamode.core.nebula.mixin;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.fluids.UpsideDownFluid;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {
    @Shadow protected abstract void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos);

    @Shadow @Final private Fluid fluid;

    @Shadow public abstract boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult);

    @Inject(method = "placeFluid", at = @At("HEAD"), cancellable = true)
    public void UpsideDownFluidPlace(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        if (!(this.fluid instanceof FlowableFluid || this.fluid instanceof UpsideDownFluid)) {
            cir.setReturnValue(false);
        } else {
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            Material material = blockState.getMaterial();
            boolean bl = blockState.canBucketPlace(this.fluid);
            boolean bl2 = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable)block).canFillWithFluid(world, pos, blockState, this.fluid);
            if (!bl2) {
                cir.setReturnValue(hitResult != null && this.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), null));
            } else if (world.getDimension().isUltrawarm() && this.fluid.isIn(FluidTags.WATER)) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                for(int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                }

                cir.setReturnValue(true);
            } else if (block instanceof FluidFillable && this.fluid == Fluids.WATER) {
                ((FluidFillable)block).tryFillWithFluid(world, pos, blockState, ((FlowableFluid)this.fluid).getStill(false));
                this.playEmptyingSound(player, world, pos);
                cir.setReturnValue(true);
            } else {
                if (!world.isClient && bl && !material.isLiquid()) {
                    world.breakBlock(pos, true);
                }

                if (!world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD) && !blockState.getFluidState().isStill()) {
                    cir.setReturnValue(false);
                } else {
                    this.playEmptyingSound(player, world, pos);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
