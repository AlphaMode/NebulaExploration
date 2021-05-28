package alphamode.core.nebula.screen;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class BaseHandler<T extends BlockEntity> extends ScreenHandler {


    protected BaseHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getInventory().canPlayerUse(player);
    }
}
