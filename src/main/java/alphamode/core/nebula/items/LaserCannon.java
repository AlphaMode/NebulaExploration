package alphamode.core.nebula.items;

import alphamode.core.nebula.entitys.LaserEntity;
import org.apache.logging.log4j.LogManager;

import java.awt.*;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LaserCannon extends Item {
    public LaserCannon(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        LogManager.getLogger().info("ok "+ world);
        if(world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            float r = world.random.nextFloat() / 2f + 0.5f;
            float g = world.random.nextFloat() / 2f + 0.5f;
            float b = world.random.nextFloat() / 2f + 0.5f;
            serverWorld.spawnEntity(new LaserEntity(world, user.getEyePos(), user.getPitch(), user.getYaw(), new Color(r, g,b,0).getRGB()));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
