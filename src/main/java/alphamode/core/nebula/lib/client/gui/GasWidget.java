package alphamode.core.nebula.lib.client.gui;

import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.lib.client.GuiUtil;
import alphamode.core.nebula.util.Util;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;

import static alphamode.core.nebula.lib.client.GuiUtil.setColorRGBA;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;

public class GasWidget implements Widget {

    private int width,height,x,y;
    private ImmutableList<GasVolume> gasVolumes;

    public GasWidget(GasVolume gasVolume, int x, int y) {
        this.gasVolumes = ImmutableList.of(gasVolume);
        this.x = x;
        this.y = y;
    }

    public GasWidget(ImmutableList<GasVolume> gases, int x, int y) {
        this.gasVolumes = gases;
        this.x = x;
        this.y = y;
    }

    public void setGases(ImmutableList<GasVolume> gasVolumes) {
        this.gasVolumes = gasVolumes;
    }

    @Override
    public void render(MatrixStack matrixStack) {
        MinecraftClient client = MinecraftClient.getInstance();
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getStill());
        Sprite[] sprites = fluidRenderHandler.getFluidSprites(client.world, client.world == null ? null : BlockPos.ORIGIN, Fluids.WATER.getStill().getDefaultState());

    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
