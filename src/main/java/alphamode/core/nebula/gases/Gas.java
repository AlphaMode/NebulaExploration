package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class Gas extends Fluid {
    @Nullable
    private String translationKey;
    private int color;

    public Gas(int gasColor) {
        color = gasColor;
    }

    public Gas() {
        color = new Color(0xFFFFFF).getRGB();
    }

    @Override
    public Item getBucketItem() {
        return null;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    protected Vec3d getVelocity(BlockView world, BlockPos pos, FluidState state) {
        return null;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 0;
    }

    @Override
    protected float getBlastResistance() {
        return 0;
    }

    @Override
    public float getHeight(FluidState state, BlockView world, BlockPos pos) {
        return 0;
    }

    @Override
    public float getHeight(FluidState state) {
        return 0;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return null;
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    @Override
    public VoxelShape getShape(FluidState state, BlockView world, BlockPos pos) {
        return null;
    }

    public int getColor() {
        return color;
    }

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
