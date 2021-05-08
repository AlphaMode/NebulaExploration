package alphamode.core.nebula.blocks;

import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;
import alphamode.core.nebula.screen.CondenserContainerMenu;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CondenserBlock extends BaseEntityBlock  {

    public CondenserBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide) {
            //This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity casted to
            //a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
            MenuProvider screenHandlerFactory = blockState.getMenuProvider(level, blockPos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openMenu(screenHandlerFactory);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean bl) {
        if (blockState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CondenserBlockEntity) {
                Containers.dropContents(level, pos, (CondenserBlockEntity)blockEntity);
            }
            super.onRemove(blockState, level, pos, newState, bl);
        }
    }



    @Override
    public BlockEntity newBlockEntity(BlockGetter blockGetter) {
        return new CondenserBlockEntity();
    }
}
