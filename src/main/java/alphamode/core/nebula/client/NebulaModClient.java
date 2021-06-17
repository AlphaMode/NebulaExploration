package alphamode.core.nebula.client;

import alphamode.core.nebula.client.screens.CondenserHandledScreen;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.packet.GasTankS2CPacket;
import alphamode.core.nebula.screen.NebulaScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import static alphamode.core.nebula.NebulaMod.id;
import java.util.function.Function;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
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
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(NebulaScreens.CONDENSER_MENU, CondenserHandledScreen::new);
        FabricModelPredicateProviderRegistry.register(NebulaItems.BASIC_OXYGEN_TANK, id("amount"),(itemStack, clientLevel, livingEntity,ra) -> {
            if(livingEntity == null) {
                return 0;
            }
            return itemStack.getOrCreateTag().getInt("amount") == 0 ? 0 : getStage(itemStack.getOrCreateTag().getInt("amount"));
        });
        ClientPlayNetworking.registerGlobalReceiver(GasTankS2CPacket.ID, GasTankS2CPacket::onPacket);
        /*ClientPlayNetworking.registerGlobalReceiver(id("condenser_update"),(client, handler, buf, responseSender) -> {
            client.execute(() -> {
                List<FluidVolume> gases = new ArrayList<>();
                ListTag gasesTag = buf.readCompoundTag().getList("gases", 0);
                for(int v = 0; v < gasesTag.size(); ++v) {
                    gases.add(FluidVolume.fromTag((CompoundTag) gasesTag.get(v)));
                }
                //tank = gases;
            });
        });*/
        //setupFluidRendering(NebulaFluids.STILL_ROCKET_FUEL,NebulaFluids.FLOWING_ROCKET_FUEL, new Identifier("minecraft", "water"), 0x4CC248);
        //BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), NebulaFluids.STILL_ROCKET_FUEL,NebulaFluids.FLOWING_ROCKET_FUEL);
        setupFluidRendering(NebulaGases.OXYGEN,null, new Identifier("water"),NebulaGases.OXYGEN.getColor());
        setupFluidRendering(NebulaGases.NITROGEN,null, new Identifier("water"), NebulaGases.NITROGEN.getColor());
        setupFluidRendering(NebulaGases.EMPTY, null, new Identifier("water"), NebulaGases.EMPTY.getColor());
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), NebulaGases.OXYGEN);
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
