package alphamode.core.nebula.blocks.entity;

import alexiil.mc.lib.attributes.fluid.FluidExtractable;
import alphamode.core.nebula.api.Machine;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CondenserBlockEntity extends LockableContainerBlockEntity implements Machine<GasVolume>, FluidExtractable {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public List<CondenserScreenHandler> handlers = new ArrayList<>();
    private List<GasVolume> tank = new ArrayList<>();

    public CondenserBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NebulaBlocks.CONDENSER_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound nbtTag) {
        super.readNbt(nbtTag);
        Inventories.readNbt(nbtTag, this.items);
        NbtList gasesTag = nbtTag.getList("tank", 10);
        this.tank = new ArrayList<>();
        for (int i = 0; i < gasesTag.size(); ++i) {
            this.tank.add(GasVolume.fromTag(gasesTag.getCompound(i)));
            //this.gases.add(FluidVolume.fromTag(compoundTag));
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound compoundTag) {
        super.writeNbt(compoundTag);
        Inventories.writeNbt(compoundTag, this.items);
        NbtList gasesTag = new NbtList();
        for (GasVolume gas : tank) {
            NbtCompound gasTag = new NbtCompound();
            gas.toTag(gasTag);
            gasesTag.add(gasTag);
        }
        compoundTag.put("tank", gasesTag);
        return compoundTag;
    }

    @Deprecated
    public List<GasVolume> getGases() {
        return tank;
    }

    public List<GasVolume> getTank() {
        return this.tank;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("gui.condenser.name");
    }

    @Override
    public ScreenHandler createMenu(int syncID, PlayerInventory inventory, PlayerEntity player) {
        tank.clear();
        CondenserScreenHandler handler = new CondenserScreenHandler(syncID, inventory, this);
        handlers.add(handler);
        return handler;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncID, PlayerInventory inventory) {
        //tank.add(FluidKeys.get(NebulaGases.NITROGEN).withAmount(100));
        tank.add(new GasVolume(NebulaGases.NITROGEN, 40));
        CondenserScreenHandler handler = new CondenserScreenHandler(syncID, inventory, this);
        handlers.add(handler);
        return handler;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.items)
            if (!stack.isEmpty())
                return false;
        return true;
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
        return Inventories.removeStack(items, i);
    }

    @Override
    public void setStack(int i, ItemStack itemStack) {
        items.set(i, itemStack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player.getInventory().canPlayerUse(player);
    }

    @Override
    public void clear() {
        items.clear();
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T blockEntity) {
        for (CondenserScreenHandler handle : ((CondenserBlockEntity) blockEntity).handlers) {
            handle.tick();
        }
    }

    @Override
    public GasVolume getInventory() {
        return null;
    }
}
