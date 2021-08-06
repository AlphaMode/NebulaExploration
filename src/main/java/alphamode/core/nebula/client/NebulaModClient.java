package alphamode.core.nebula.client;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.client.gui.screens.CondenserHandledScreen;
import alphamode.core.nebula.client.render.entity.BlockEntityRenderObj;
import alphamode.core.nebula.client.render.entity.LaserEntityRender;
import alphamode.core.nebula.entitys.LaserEntity;
import alphamode.core.nebula.entitys.NebulaEntities;
import alphamode.core.nebula.entitys.rocket.RocketEntityRenderer;
import alphamode.core.nebula.entitys.rocket.rocket;
import alphamode.core.nebula.fluids.NebulaFluids;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.network.NebulaNetwork;
import alphamode.core.nebula.packet.GasTankS2CPacket;
import alphamode.core.nebula.screen.NebulaScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import static alphamode.core.nebula.NebulaMod.LOGGER;
import static alphamode.core.nebula.NebulaMod.id;
import java.awt.*;
import java.util.function.Function;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

@Environment(EnvType.CLIENT)
public class NebulaModClient implements ClientModInitializer {
    private int getStage(int ammount) {
        return 0;
    }

    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new NebulaID("cube"), "main");
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(NebulaScreens.CONDENSER_MENU, CondenserHandledScreen::new);
        ExtendedScreenHandlerFactory ok;

//        FabricModelPredicateProviderRegistry.register(NebulaItems.BASIC_OXYGEN_TANK, id("amount"),(itemStack, clientLevel, livingEntity,ra) -> {
//            if(livingEntity == null) {
//                return 0;
//            }
//            return itemStack.getOrCreateTag().getInt("amount") == 0 ? 0 : getStage(itemStack.getOrCreateTag().getInt("amount"));
//        });
        ClientPlayNetworking.registerGlobalReceiver(GasTankS2CPacket.ID, GasTankS2CPacket::onPacket);
        ClientPlayNetworking.registerGlobalReceiver(new NebulaID("entity"), (MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) -> {
            LOGGER.info(buf.readIdentifier());
            NebulaNetwork.handleEntity(buf);
        });

        //setupGasRendering(NebulaGases.OXYGEN, new Identifier("water"),new Color(0xFFFFFF).getRGB());
        //setupGasRendering(NebulaGases.NITROGEN, new Identifier("water"), new Color(0xA6A6EC).getRGB());
        for(Gas gas : NebulaRegistry.GAS) {
            setupFluidRendering(gas.getAsFluid(), gas.getAsFluid(), new Identifier("water"), gas.getColor());
        }
        setupFluidRendering(NebulaFluids.ROCKET_FUEL.still(), NebulaFluids.ROCKET_FUEL.flowing(), new Identifier("water"), new Color(0xFFB61D).getRGB());
       // EntityRendererRegistry.INSTANCE.register(NebulaEntities.CREATIVE_ROCKET, (context) -> new RocketEntityRenderer(context));
        EntityRendererRegistry.INSTANCE.register(NebulaEntities.LASER_ENTITY, ctx -> new LaserEntityRender<>(ctx));
        EntityRendererRegistry.INSTANCE.register(NebulaEntities.CREATIVE_ROCKET, ctx -> new RocketEntityRenderer(ctx));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((provider, out) -> {
            out.accept(new NebulaID("models/misc/utah_teapot.obj"));
        });
        BlockEntityRendererRegistry.INSTANCE.register(NebulaBlocks.CONDENSER_BLOCK_ENTITY, ctx -> new BlockEntityRenderObj());
        NebulaNetwork.registerEntityPacket(new NebulaID("laser"), LaserEntity::spawn);
    }
    public static void setupGasRendering(final Gas gas, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_still");

        // If they're not already present, add the sprites to the block atlas
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
        });

        final Identifier fluidId = NebulaRegistry.GAS.getId(gas);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }

            /**
             * Get the sprites from the block atlas when resources are reloaded
             */
            @Override
            public void reload(ResourceManager resourceManager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillSpriteId);
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler()
        {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

    }

    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_still");
        final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_flow");

        // If they're not already present, add the sprites to the block atlas
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }

            /**
             * Get the sprites from the block atlas when resources are reloaded
             */
            @Override
            public void reload(ResourceManager resourceManager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler()
        {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
    }
}
