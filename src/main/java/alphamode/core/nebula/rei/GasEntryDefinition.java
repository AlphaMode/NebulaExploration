package alphamode.core.nebula.rei;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import com.google.common.collect.Lists;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksImpl;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.AbstractEntryRenderer;
import me.shedaniel.rei.api.client.entry.renderer.BatchedEntryRenderer;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.util.SpriteRenderer;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.tag.TagGroup;
import net.minecraft.tag.TagManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class GasEntryDefinition implements EntryDefinition<GasVolume>, EntrySerializer<GasVolume> {

    @Environment(EnvType.CLIENT)
    private EntryRenderer<GasVolume> renderer;

    public GasEntryDefinition() {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> renderer = new GasEntryRenderer());
    }
    @Override
    public Class<GasVolume> getValueType() {
        return GasVolume.class;
    }

    @Override
    public EntryType getType() {
        return NebulaPlugin.GAS;
    }

    @Override
    public EntryRenderer getRenderer() {
        return renderer;
    }

    @Override
    public @Nullable Identifier getIdentifier(EntryStack<GasVolume> entry, GasVolume value) {
        return NebulaRegistry.GAS.getId(value.getGas());
    }

    @Override
    public boolean isEmpty(EntryStack<GasVolume> entry, GasVolume value) {
        return value.toFluidVolume().isEmpty();
    }

    @Override
    public GasVolume copy(EntryStack<GasVolume> entry, GasVolume value) {
        return new GasVolume(value.getGas(), value.getAmount());
    }

    @Override
    public GasVolume normalize(EntryStack<GasVolume> entry, GasVolume value) {
        Gas gas = value.getGas();

        GasVolume copy = new GasVolume(gas, value.getAmount());
        copy.setAmount(1000L);
        return copy;
    }

    @Override
    public long hash(EntryStack<GasVolume> entry, GasVolume value, ComparisonContext context) {
        return 0;
    }

    @Override
    public boolean equals(GasVolume o1, GasVolume o2, ComparisonContext context) {
        return false;
    }


    @Override
    public @Nullable EntrySerializer getSerializer() {
        return this;
    }

    @Override
    public Text asFormattedText(EntryStack<GasVolume> entry, GasVolume value) {
        return value.getGas().getName();
    }

    @Override
    public Collection<Identifier> getTagsFor(TagManager tagContainer, EntryStack<GasVolume> entry, GasVolume value) {
        TagGroup<Gas> collection = tagContainer.getOrCreateTagGroup(NebulaRegistry.GAS_KEY);
        return collection == null ? Collections.emptyList() : collection.getTagsFor(value.getGas());
    }

    @Override
    public boolean supportSaving() {
        return true;
    }

    @Override
    public boolean supportReading() {
        return true;
    }

    @Override
    public NbtCompound save(EntryStack<GasVolume> entry, GasVolume value) {
        return value.toTag(new NbtCompound());
    }

    @Override
    public GasVolume read(NbtCompound tag) {
        return GasVolume.fromTag(tag);
    }

    public static class GasEntryRenderer extends AbstractEntryRenderer<GasVolume> implements BatchedEntryRenderer<GasVolume, Sprite> {

        @Override
        public Sprite getExtraData(EntryStack<GasVolume> entry) {
            return FluidStackHooksImpl.getStillTexture(Fluids.WATER);
        }

        @Override
        public int getBatchIdentifier(EntryStack<GasVolume> entry, Rectangle bounds, Sprite extraData) {
            return 0;
        }

        @Override
        public void startBatch(EntryStack<GasVolume> entry, Sprite extraData, MatrixStack matrices, float delta) {

        }

        @Override
        public void renderBase(EntryStack<GasVolume> entry, Sprite extraData, MatrixStack matrices, VertexConsumerProvider.Immediate immediate, Rectangle bounds, int mouseX, int mouseY, float delta) {
            SpriteRenderer.beginPass()
                    .setup(immediate, RenderLayer.getSolid())
                    .sprite(extraData)
                    .color(entry.getValue().getGas().getColor())
                    .light(0x00f000f0)
                    .overlay(OverlayTexture.DEFAULT_UV)
                    .alpha(0xff)
                    .normal(matrices.peek().getNormal(), 0, 0, 0)
                    .position(matrices.peek().getModel(), bounds.x, bounds.getMaxY() - bounds.height * MathHelper.clamp(entry.get(EntryStack.Settings.FLUID_RENDER_RATIO), 0, 1), bounds.getMaxX(), bounds.getMaxY(), entry.getZ())
                    .next(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        }

        @Override
        public void afterBase(EntryStack<GasVolume> entry, Sprite extraData, MatrixStack matrices, float delta) {

        }

        @Override
        public void renderOverlay(EntryStack<GasVolume> entry, Sprite extraData, MatrixStack matrices, VertexConsumerProvider.Immediate immediate, Rectangle bounds, int mouseX, int mouseY, float delta) {

        }

        @Override
        public void endBatch(EntryStack<GasVolume> entry, Sprite extraData, MatrixStack matrices, float delta) {

        }

        @Override
        public void render(EntryStack<GasVolume> entry, MatrixStack matrices, Rectangle bounds, int mouseX, int mouseY, float delta) {
            FluidVolume stack = entry.getValue().toFluidVolume();
            if (stack.isEmpty()) return;
            Sprite sprite = FluidStackHooks.getStillTexture(Fluids.WATER);
            if (sprite == null) return;
            int color = stack.getRenderColor();

            VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

            SpriteRenderer.beginPass()
                    .setup(immediate, RenderLayer.getSolid())
                    .sprite(sprite)
                    .color(color)
                    .light(0x00f000f0)
                    .overlay(OverlayTexture.DEFAULT_UV)
                    .alpha(0xff)
                    .normal(matrices.peek().getNormal(), 0, 0, 0)
                    .position(matrices.peek().getModel(), bounds.x, bounds.getMaxY() - bounds.height * MathHelper.clamp(entry.get(EntryStack.Settings.FLUID_RENDER_RATIO), 0, 1), bounds.getMaxX(), bounds.getMaxY(), entry.getZ())
                    .next(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);

            immediate.draw();
        }

        @Override
        public @Nullable Tooltip getTooltip(EntryStack<GasVolume> entry, Point mouse) {
            if (entry.isEmpty())
                return null;
            List<Text> toolTip = Lists.newArrayList(entry.asFormattedText());
            long amount = entry.getValue().getAmount();
            if (amount >= 0) {
                String amountTooltip = I18n.translate("tooltip.rei.fluid_amount.forge", entry.getValue().getAmount());
                if (amountTooltip != null) {
                    toolTip.addAll(Stream.of(amountTooltip.split("\n")).map(LiteralText::new).collect(Collectors.toList()));
                }
            }
            if (MinecraftClient.getInstance().options.advancedItemTooltips) {
                Identifier fluidId = NebulaRegistry.GAS.getId(entry.getValue().getGas());
                toolTip.add((new LiteralText(fluidId.toString())).formatted(Formatting.DARK_GRAY));
            }
            return Tooltip.create(toolTip);
        }
    }
}
