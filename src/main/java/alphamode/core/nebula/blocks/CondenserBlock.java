package alphamode.core.nebula.blocks;

import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CondenserBlock extends BlockWithEntity  {

    public CondenserBlock(Settings properties) {
        super(properties);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World level, BlockPos blockPos, PlayerEntity player, Hand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClient) {
            //This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity casted to
            //a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = blockState.createScreenHandlerFactory(level, blockPos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState blockState, World level, BlockPos pos, BlockState newState, boolean bl) {
        if (blockState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CondenserBlockEntity) {
                ItemScatterer.spawn(level, pos, (CondenserBlockEntity)blockEntity);
            }
            super.onStateReplaced(blockState, level, pos, newState, bl);
        }
    }



    @Override
    public BlockEntity createBlockEntity(BlockView blockGetter) {
        return new CondenserBlockEntity();
    }
}
