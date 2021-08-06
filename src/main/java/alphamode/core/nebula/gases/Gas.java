package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class Gas {

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
        color = 0xFFFFFFFF;
        //gasTexture = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER).getFluidSprites(null, BlockPos.ORIGIN, Fluids.WATER.getDefaultState())[0];
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

}
