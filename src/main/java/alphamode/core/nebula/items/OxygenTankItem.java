package alphamode.core.nebula.items;

import alphamode.core.nebula.storage.Tank;
import org.jetbrains.annotations.Nullable;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class OxygenTankItem extends Item implements Tank {

    private final int maxCapacity;

    public OxygenTankItem(Properties properties, int maxCapacity) {
        super(properties);
        this.maxCapacity = maxCapacity;
    }



    @Override
    public int getMaxCapacity() {
        return 100;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        int ammount = itemStack.getOrCreateTag().getInt("oxygen");
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(new TextComponent("Oxygen: "+itemStack.getOrCreateTag().getInt("oxygen")+"/"+maxCapacity));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

}
