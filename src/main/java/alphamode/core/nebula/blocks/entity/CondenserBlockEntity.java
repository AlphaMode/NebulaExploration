package alphamode.core.nebula.blocks.entity;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;

public class CondenserBlockEntity extends LockableContainerBlockEntity implements Tickable {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1,ItemStack.EMPTY);
    public List<CondenserScreenHandler> handlers = new ArrayList<>();
    private List<GasVolume> gases = new ArrayList<>();
    public CondenserBlockEntity() {
        super(NebulaBlocks.CONDENSER_BLOCK_ENTITY);
    }

    @Override
    public void fromTag(BlockState blockState, CompoundTag compoundTag) {
        super.fromTag(blockState, compoundTag);
        Inventories.fromTag(compoundTag, this.items);
        ListTag gasesTag = compoundTag.getList("gases", 10);
        this.gases = new ArrayList<>();
        for(int i = 0; i < gasesTag.size(); ++i) {
            //this.gases.add(FluidVolume.fromTag(compoundTag));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        super.toTag(compoundTag);
        Inventories.toTag(compoundTag, this.items);
        ListTag gasesTag = new ListTag();
        /*for(FluidVolume gas : gases) {
            CompoundTag gasTag = new CompoundTag();
            gas.toTag(compoundTag);
            gasesTag.addTag(gasesTag.size(), gasTag);
        }*/
        compoundTag.put("gases", gasesTag);
        return compoundTag;
    }


    public void onOpen(PlayerEntity player,CondenserBlockEntity be) {
        super.onOpen(player);

    }

    public List<GasVolume> getGases() {
        return gases;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("gui.condenser.name");
    }

    @Override
    public ScreenHandler createMenu(int syncID, PlayerInventory inventory, PlayerEntity player) {
        //this.gases.clear();
        gases.add(new GasVolume(NebulaGases.NITROGEN,40));
        gases.add(new GasVolume(NebulaGases.OXYGEN, 12));
        CondenserScreenHandler handler = new CondenserScreenHandler(syncID,inventory,this);
        handlers.add(handler);
        return handler;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncID, PlayerInventory inventory) {
        //gases.add(FluidKeys.get(NebulaGases.NITROGEN).withAmount(100));
        gases.add(new GasVolume(NebulaGases.NITROGEN,40));
        CondenserScreenHandler handler = new CondenserScreenHandler(syncID,inventory,this);
        handlers.add(handler);
        return handler;
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

    @Override
    public void tick() {
        for(CondenserScreenHandler handle : handlers) {
            handle.tick();
        }
    }
}
