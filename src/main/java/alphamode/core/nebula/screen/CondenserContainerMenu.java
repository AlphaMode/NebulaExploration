package alphamode.core.nebula.screen;

import org.apache.logging.log4j.LogManager;

import java.util.logging.Logger;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class CondenserContainerMenu extends AbstractContainerMenu {
    private Container inventory;

    public CondenserContainerMenu(int syncId, Inventory inventory) {
        this(syncId,inventory, new SimpleContainer(1));
    }

    public CondenserContainerMenu(int syncId, Inventory playerInventory, Container inventory) {
        super(NebulaScreens.CONDENSER_MENU,syncId);
        Logger.getLogger("gui").info("WORKS?");
        checkContainerSize(inventory, 1);
        this.inventory = inventory;
        inventory.startOpen(playerInventory.player);
        int m;
        int l;
        //Our inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                this.addSlot(new Slot(inventory, l + m * 3, 62 + l * 18, 17 + m * 18));
            }
        }
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

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }
}
