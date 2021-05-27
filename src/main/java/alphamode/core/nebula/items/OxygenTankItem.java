package alphamode.core.nebula.items;

import alphamode.core.nebula.storage.Tank;
import org.jetbrains.annotations.Nullable;
import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class OxygenTankItem extends Item implements Tank {

    private final int maxCapacity;

    public OxygenTankItem(Settings properties, int maxCapacity) {
        super(properties);
        this.maxCapacity = maxCapacity;
    }



    @Override
    public int getMaxCapacity() {
        return 100;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World level, Entity entity, int i, boolean bl) {
        int ammount = itemStack.getOrCreateTag().getInt("oxygen");
    }

    @Override
    public void appendTooltip(ItemStack itemStack, @Nullable World level, List<Text> list, TooltipContext tooltipFlag) {
        //list.add(((MutableText)NebulaGases.OXYGEN).append(": "+itemStack.getOrCreateTag().getInt("oxygen")+"/"+maxCapacity));
        //super.appendTooltip(itemStack, level, list, tooltipFlag);
    }

}
