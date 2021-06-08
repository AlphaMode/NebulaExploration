package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

public class Gas {

    @Nullable
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

    public int getColor() {
        return color;
    }

//    public Sprite getSprite() {
//        return gasTexture;
//    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.createTranslationKey("gas", NebulaRegistry.GAS.getId(this));
        }
        return this.translationKey;
    }

    public Text getName() {
        return new TranslatableText(getTranslationKey());
    }

}
