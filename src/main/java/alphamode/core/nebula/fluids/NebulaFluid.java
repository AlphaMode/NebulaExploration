package alphamode.core.nebula.fluids;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class NebulaFluid extends AbstractNebulaFluid {

    public NebulaFluid(boolean infinite) {
        this.isInfinite = infinite;
    }

    public NebulaFluid() {
        this.isInfinite = false;
    }

    @Override
    public BlockState toBlockState(FluidState state) {
        return state.getBlockState().getBlock().getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    protected ParticleEffect getParticle() {
        return ParticleTypes.DRIPPING_LAVA;
    }

    public static class Still extends NebulaFluid {
        public Still(boolean infinite) {
            super(infinite);
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
    public static class Flowing extends NebulaFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }
}
