package alphamode.core.nebula.rei;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.ClientHelper;
import me.shedaniel.rei.api.ConfigObject;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.fractions.Fraction;
import me.shedaniel.rei.api.widgets.Tooltip;
import me.shedaniel.rei.impl.AbstractEntryStack;
import me.shedaniel.rei.impl.SimpleFluidRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class GasEntryStack extends AbstractEntryStack {
    private static final Fraction IGNORE_AMOUNT = Fraction.of(ThreadLocalRandom.current().nextLong(), ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)).simplify();
    private Gas gas;
    private Fraction amount;
    private int hashIgnoreAmount;
    private int hash;

    public GasEntryStack(Gas gas) {
        this.gas = gas;
        amount = IGNORE_AMOUNT;

        rehash();
    }

    private void rehash() {
        hashIgnoreAmount = 31 + getType().ordinal();
        hashIgnoreAmount = 31 * hashIgnoreAmount + gas.hashCode();

        hash = 31 * hashIgnoreAmount + amount.hashCode();
    }

    @Override
    public Optional<Identifier> getIdentifier() {
        return Optional.ofNullable(NebulaRegistry.GAS.getId(gas));
    }

    @Override
    public Type getType() {
        return Type.EMPTY;
    }

    @Override
    public Fraction getAccurateAmount() {
        return Fraction.of(1,1000);
    }

    @Override
    public void setAmount(Fraction amount) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public EntryStack copy() {
        EntryStack stack = new GasEntryStack(this.gas);
        for (Short2ObjectMap.Entry<Object> entry : getSettings().short2ObjectEntrySet()) {
            stack.setting(EntryStack.Settings.getById(entry.getShortKey()), entry.getValue());
        }
        return stack;
    }

    @Override
    public Object getObject() {
        return gas;
    }

    @Override
    public boolean equalsIgnoreTagsAndAmount(EntryStack stack) {
        if (stack.getType() == Type.ITEM)
            return EntryStack.copyItemToFluids(stack).anyMatch(this::equalsIgnoreTagsAndAmount);
        if (stack.getType() != Type.FLUID)
            return false;
        return gas == stack.getObject();
    }

    @Override
    public boolean equalsIgnoreTags(EntryStack stack) {
        if (stack.getType() == Type.ITEM)
            return EntryStack.copyItemToFluids(stack).anyMatch(this::equalsIgnoreTags);
        if (stack.getType() != Type.FLUID)
            return false;
        return gas == stack.getObject() && (amount.equals(IGNORE_AMOUNT) || stack.getAccurateAmount().equals(IGNORE_AMOUNT) || amount.equals(stack.getAccurateAmount()));
    }

    @Override
    public boolean equalsIgnoreAmount(EntryStack stack) {
        if (stack.getType() == Type.ITEM)
            return EntryStack.copyItemToFluids(stack).anyMatch(this::equalsIgnoreAmount);
        if (stack.getType() != Type.FLUID)
            return false;
        return gas == stack.getObject();
    }

    @Override
    public boolean equalsAll(EntryStack stack) {
        if (stack.getType() != Type.FLUID)
            return false;
        return gas == stack.getObject() && (amount.equals(IGNORE_AMOUNT) || stack.getAccurateAmount().equals(IGNORE_AMOUNT) || amount.equals(stack.getAccurateAmount()));
    }

    @Override
    public int hashOfAll() {
        return hash;
    }

    @Override
    public int hashIgnoreAmountAndTags() {
        return hashIgnoreAmount;
    }

    @Override
    public int hashIgnoreTags() {
        return hash;
    }

    @Override
    public int hashIgnoreAmount() {
        return hashIgnoreAmount;
    }

    @Override
    public void render(MatrixStack matrices, Rectangle bounds, int mouseX, int mouseY, float delta) {
        if (get(Settings.RENDER).get()) {
            SimpleFluidRenderer.FluidRenderingData renderingData = SimpleFluidRenderer.fromFluid(Fluids.WATER);
            if (renderingData != null) {
                Sprite sprite = renderingData.getSprite();
                int color = gas.getColor();
                int a = 255;
                int r = (color >> 16 & 255);
                int g = (color >> 8 & 255);
                int b = (color & 255);
                MinecraftClient.getInstance().getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                Tessellator tess = Tessellator.getInstance();
                BufferBuilder bb = tess.getBuffer();
                Matrix4f matrix = matrices.peek().getModel();
                bb.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
                bb.vertex(matrix, bounds.getMaxX(), bounds.y, getZ()).texture(sprite.getMaxU(), sprite.getMinV()).color(r, g, b, a).next();
                bb.vertex(matrix, bounds.x, bounds.y, getZ()).texture(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).next();
                bb.vertex(matrix, bounds.x, bounds.getMaxY(), getZ()).texture(sprite.getMinU(), sprite.getMaxV()).color(r, g, b, a).next();
                bb.vertex(matrix, bounds.getMaxX(), bounds.getMaxY(), getZ()).texture(sprite.getMaxU(), sprite.getMaxV()).color(r, g, b, a).next();
                tess.draw();
            }
        }
    }

    @Override
    public @Nullable Tooltip getTooltip(Point mouse) {
        if (!get(Settings.TOOLTIP_ENABLED).get() || isEmpty())
            return null;
        List<Text> toolTip = Lists.newArrayList(asFormattedText());
        if (!amount.isLessThan(Fraction.empty()) && !amount.equals(IGNORE_AMOUNT)) {
            String amountTooltip = get(Settings.Fluid.AMOUNT_TOOLTIP).apply(this);
            if (amountTooltip != null)
                toolTip.addAll(Stream.of(amountTooltip.split("\n")).map(LiteralText::new).collect(Collectors.toList()));
        }
        if (MinecraftClient.getInstance().options.advancedItemTooltips) {
            toolTip.add((new LiteralText(NebulaRegistry.GAS.getId((Gas) this.getObject()).toString())).formatted(Formatting.DARK_GRAY));
        }
        toolTip.addAll(get(Settings.TOOLTIP_APPEND_EXTRA).apply(this));
        if (get(Settings.TOOLTIP_APPEND_MOD).get() && ConfigObject.getInstance().shouldAppendModNames()) {
            ClientHelper.getInstance().appendModIdToTooltips(toolTip, NebulaRegistry.GAS.getId((Gas)getObject()).getNamespace());
        }
        return Tooltip.create(toolTip);
    }

    @NotNull
    @Override
    public Text asFormattedText() {
        return gas.getName();
    }
}
