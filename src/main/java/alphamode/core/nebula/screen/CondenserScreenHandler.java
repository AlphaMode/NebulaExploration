package alphamode.core.nebula.screen;

import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;
import alphamode.core.nebula.packet.GasTankS2CPacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class CondenserScreenHandler extends ScreenHandler {
    private ServerPlayerEntity playerEntity;
    private Inventory inventory;

    public CondenserScreenHandler(int syncId,PlayerInventory inventory) {
        this(syncId,inventory,new SimpleInventory(1));
    }

    public CondenserScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(NebulaScreens.CONDENSER_MENU,syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        if(playerInventory.player instanceof ServerPlayerEntity)
            this.playerEntity = (ServerPlayerEntity) playerInventory.player;
        inventory.onOpen(playerInventory.player);
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
    }

    public void tick() {
        playerEntity.networkHandler.sendPacket(GasTankS2CPacket.create(((CondenserBlockEntity)inventory).getTank()));
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        if(playerEntity instanceof ServerPlayerEntity)
            ((CondenserBlockEntity)inventory).handlers.remove(this);
    }

    public Inventory getInventory() {
        return inventory;
    }

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
