package alphamode.core.nebula.blocks.entity;

import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import java.util.Iterator;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;

public class CondenserBlockEntity extends LockableContainerBlockEntity {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1,ItemStack.EMPTY);
    public CondenserBlockEntity() {
        super(NebulaBlocks.CONDENSER_BLOCK_ENTITY);
    }

    @Override
    public void fromTag(BlockState blockState, CompoundTag compoundTag) {
        super.fromTag(blockState, compoundTag);
        Inventories.fromTag(compoundTag, this.items);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        super.toTag(compoundTag);
        Inventories.toTag(compoundTag, this.items);
        return compoundTag;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("gui.condenser.name");
    }

    @Override
    public ScreenHandler createMenu(int syncID, PlayerInventory inventory, PlayerEntity player) {
        return new CondenserScreenHandler(syncID,inventory, this);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncID, PlayerInventory inventory) {
        return new CondenserScreenHandler(syncID,inventory,this);
    }

    @Override
    public int size() {
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
    public ItemStack getStack(int i) {
        return items.get(i);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(items, slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int i) {
        return Inventories.removeStack(items,i);
    }

    @Override
    public void setStack(int i, ItemStack itemStack) {
        items.set(i, itemStack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player.inventory.canPlayerUse(player);
    }

    @Override
    public void clear() {
        items.clear();
    }
}
