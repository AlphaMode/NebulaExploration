package alphamode.core.nebula.blocks.entity;

import alexiil.mc.lib.attributes.fluid.FluidExtractable;
import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.api.Machine;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.packet.GasTankS2CPacket;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import alphamode.core.nebula.util.Util;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerQueryNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.collection.SortedArraySet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CondenserBlockEntity extends LockableContainerBlockEntity implements Machine<GasVolume>, FluidExtractable {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private boolean collecting = true;
    private List<GasVolume> atmosphericGases;
    private List<GasVolume> tank = new ArrayList<>();
    private int cooldown = 10;

    public CondenserBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NebulaBlocks.CONDENSER_BLOCK_ENTITY, blockPos, blockState);
        NebulaMod.LOGGER.info(world);
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
        return handler;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncID, PlayerInventory inventory) {
        CondenserScreenHandler handler = new CondenserScreenHandler(syncID, inventory, this);
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

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T BE) {
        CondenserBlockEntity blockEntity = (CondenserBlockEntity) BE;
        if(blockEntity.tank.size() == 0)
            Util.getAtmosphereGas(blockEntity.world).forEach((gasVolume -> blockEntity.tank.add(new GasVolume(gasVolume.getGas(), 0))));
        if (world instanceof ServerWorld serverWorld) {
            blockEntity.cooldown--;
            for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                if (player.currentScreenHandler instanceof CondenserScreenHandler screenHandler) {
                    serverWorld.sendToPlayerIfNearby(player, false, blockPos.getX(), blockPos.getY(), blockPos.getZ(), GasTankS2CPacket.create(screenHandler.syncId, Util.getAtmosphereGas(serverWorld), GasTankS2CPacket.Type.INFO));
                }
            }
            if (blockEntity.collecting & blockEntity.cooldown == 0) {
                blockEntity.cooldown = 10;
                GasVolume current = Util.getAtmosphereGas(serverWorld).get(0);
                for (GasVolume gasVolume : blockEntity.tank) {
                    if (gasVolume.getGas() == current.getGas()) {
                        for (ServerPlayerEntity player : serverWorld.getPlayers())
                            if (player.currentScreenHandler instanceof CondenserScreenHandler screenHandler)
                                serverWorld.sendToPlayerIfNearby(player, false, blockPos.getX(), blockPos.getY(), blockPos.getZ(), GasTankS2CPacket.create(screenHandler.syncId, ((CondenserBlockEntity) BE).getTank(), GasTankS2CPacket.Type.TANK));
                        gasVolume.setAmount(gasVolume.getAmount() + current.getAmount());
                    }
                }
            }
        }
    }

    @Override
    public GasVolume getInventory() {
        return null;
    }
}
