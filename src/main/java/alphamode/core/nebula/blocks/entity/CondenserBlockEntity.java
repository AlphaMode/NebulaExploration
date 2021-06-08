package alphamode.core.nebula.blocks.entity;

import alphamode.core.nebula.api.Machine;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockState;
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

public class CondenserBlockEntity extends LockableContainerBlockEntity implements Machine<GasVolume> {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public List<CondenserScreenHandler> handlers = new ArrayList<>();
    private List<GasVolume> gases = new ArrayList<>();

    public CondenserBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NebulaBlocks.CONDENSER_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound compoundTag) {
        super.readNbt(compoundTag);
        Inventories.readNbt(compoundTag, this.items);
        NbtList gasesTag = compoundTag.getList("gases", 10);
        this.gases = new ArrayList<>();
        for (int i = 0; i < gasesTag.size(); ++i) {
            //this.gases.add(FluidVolume.fromTag(compoundTag));
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound compoundTag) {
        super.writeNbt(compoundTag);
        Inventories.writeNbt(compoundTag, this.items);
        NbtList gasesTag = new NbtList();
        for (GasVolume gas : gases) {
            NbtCompound gasTag = new NbtCompound();
            gas.toTag(compoundTag);
            gasesTag.add(gasTag);
        }
        compoundTag.put("gases", gasesTag);
        return compoundTag;
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
        gases.clear();
        gases.add(new GasVolume(NebulaGases.NITROGEN, 600));
        gases.add(new GasVolume(NebulaGases.OXYGEN, 12));
        CondenserScreenHandler handler = new CondenserScreenHandler(syncID, inventory, this);
        handlers.add(handler);
        return handler;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncID, PlayerInventory inventory) {
        //gases.add(FluidKeys.get(NebulaGases.NITROGEN).withAmount(100));
        gases.add(new GasVolume(NebulaGases.NITROGEN, 40));
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


    public static void tick(World world, BlockPos pos, BlockState state, CondenserBlockEntity blockEntity) {

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
