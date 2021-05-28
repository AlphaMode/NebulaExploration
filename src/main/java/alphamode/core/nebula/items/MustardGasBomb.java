package alphamode.core.nebula.items;

import alphamode.core.nebula.items.Throwables.MustardBomb;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MustardGasBomb extends Item {
    public MustardGasBomb(Settings properties) {
        super(properties);
    }

    @Override
    public TypedActionResult<ItemStack> use(World level, PlayerEntity player, Hand interactionHand) {
        ItemStack itemStack = player.getStackInHand(interactionHand);

        level.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (/*RANDOM.nextFloat() */0.4F + 0.8F));
        if (!level.isClient) {
            MustardBomb mustardBomb = new MustardBomb(level, player);
            mustardBomb.setItem(itemStack);
            mustardBomb.setProperties(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 1.0F);
            level.spawnEntity(mustardBomb);
        }

        player.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!player.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, level.isClient());
    }
}
