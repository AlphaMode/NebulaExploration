package alphamode.core.nebula.items;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.storage.Tank;
import org.jetbrains.annotations.Nullable;
import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class GasTankItem extends Item implements Tank {

    private int maxCapacity;

    public GasTankItem(Settings properties, int maxCapacity) {
        super(properties);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public int getMaxCapacity() {
        return 1000;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateNbt().putInt("gas", 1000);
            stacks.add(stack);
        }
    }

    @Override
    public Text getName() {
        return new TranslatableText(NebulaGases.EMPTY.getTranslationKey());
    }

    @Override
    public void appendTooltip(ItemStack itemStack, @Nullable World level, List<Text> list, TooltipContext tooltipFlag) {
        Gas gas = NebulaRegistry.GAS.get(new Identifier(itemStack.getOrCreateNbt().getString("gas_id")));
        itemStack.setCustomName(gas.getName());
        list.add(gas.getName().append(": "+itemStack.getOrCreateNbt().getInt("gas")+"/"+maxCapacity+" mb").formatted(Formatting.GRAY));
        super.appendTooltip(itemStack, level, list, tooltipFlag);
    }

}
