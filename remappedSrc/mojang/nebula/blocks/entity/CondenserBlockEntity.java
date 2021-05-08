package alphamode.core.nebula.blocks.entity;

import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.screen.CondenserContainerMenu;

import java.util.Iterator;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;

public class CondenserBlockEntity extends BaseContainerBlockEntity implements TickableBlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    public CondenserBlockEntity() {
        super(NebulaBlocks.Condenser_BLOCK_ENTITY);
    }

    @Override
    public void load(BlockState blockState, CompoundTag compoundTag) {
        super.load(blockState, compoundTag);
        ContainerHelper.loadAllItems(compoundTag, this.items);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        super.save(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.items);
        return compoundTag;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("gui.condenser.name");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncID, Inventory inventory, Player player) {
        return new CondenserContainerMenu(syncID,inventory, this);
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncID, Inventory inventory) {
        return new CondenserContainerMenu(syncID,inventory,this);
    }


    @Override
    public void tick() {

    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        Iterator var1 = this.items.iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return items.get(i);
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack result = ContainerHelper.removeItem(items, slot, count);
        if (!result.isEmpty()) {
            setChanged();
        }
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(items,i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        items.set(i, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.inventory.stillValid(player);
    }

    @Override
    public void clearContent() {
        items.clear();
    }
}
