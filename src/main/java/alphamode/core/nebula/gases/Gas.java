package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.fluids.NebulaFluid;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class Gas extends NebulaFluid {

    @Nullable
    private Identifier id;
    private String translationKey;
    private final int color;
    private Sprite gasTexture;

    //Only to make it show up in REI


    public Gas(int gasColor) {
        color = gasColor;
        //gasTexture = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER).getFluidSprites(null, BlockPos.ORIGIN, Fluids.WATER.getDefaultState())[0];
    }

    public Gas() {
        super();
        color = 0xFFFFFFFF;
        //gasTexture = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER).getFluidSprites(null, BlockPos.ORIGIN, Fluids.WATER.getDefaultState())[0];
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    public Fluid getAsFluid() {
        return Registry.FLUID.get(NebulaRegistry.GAS.getId(this));
    }

    public int getColor() {
        return color;
    }

//    public Sprite getSprite() {
//        return gasTexture;
//    }

    public Identifier getId() {
        if (id == null)
            id = NebulaRegistry.GAS.getId(this);
        return id;
    }

    public String getTranslationKey() {
        if (translationKey == null)
            translationKey = Util.createTranslationKey("gas", getId());
        return translationKey;
    }

    public TranslatableText getName() {
        return new TranslatableText(getTranslationKey());
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }
}
