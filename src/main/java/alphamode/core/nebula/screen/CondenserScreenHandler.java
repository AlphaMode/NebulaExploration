package alphamode.core.nebula.screen;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alexiil.mc.lib.attributes.fluid.volume.NormalFluidVolume;
import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;
import alphamode.core.nebula.gases.NebulaGases;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import static alphamode.core.nebula.NebulaMod.id;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class CondenserScreenHandler extends ScreenHandler {
    private CondenserBlockEntity inventory;
    private PropertyDelegate propertyDelegate;

    public List<FluidVolume> getGases() {
        return inventory.getGases();
    }

    public CondenserScreenHandler(int syncId, PlayerInventory playerInventory, CondenserBlockEntity inventory/*, PropertyDelegate propertyDelegate*/) {
        super(NebulaScreens.CONDENSER_MENU,syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        //this.addProperties(propertyDelegate);
        //this.propertyDelegate = propertyDelegate;
        int m;
        int l;
        this.addSlot(new Slot(inventory,0 ,66,52));
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

        PacketByteBuf buf = PacketByteBufs.create();
        CompoundTag tag = new CompoundTag();
        ListTag listTag = new ListTag();
        //listTag.add(NormalFluidVolume.create(NebulaGases.NITROGEN, 100).toTag());
        tag.put("gases", listTag);
        buf.writeCompoundTag(tag);
        ServerPlayNetworking.send((ServerPlayerEntity) playerInventory.player, id("condenser_update"), buf);
        //NebulaComponents.GAS_COMPONENT.get(this.inventory).setFluids(temp);
        //LogManager.getLogger("c").info(NebulaComponents.GAS_COMPONENT.get(this.inventory).getFluids().get(0).localizeInTank(FluidAmount.of(1,1000)));
        //NebulaComponents.GAS_COMPONENT.sync(this.inventory);
    }

    public CondenserScreenHandler(int i, PlayerInventory playerInventory) {
        super(NebulaScreens.CONDENSER_MENU,i);

    }

    @Environment(EnvType.CLIENT)
    public int getAmount(int index) {
        return propertyDelegate.get(index);
    }

    @Environment(EnvType.SERVER)
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
