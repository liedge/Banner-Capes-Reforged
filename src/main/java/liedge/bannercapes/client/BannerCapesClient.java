package liedge.bannercapes.client;

import liedge.bannercapes.BannerCapes;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@Mod(value = BannerCapes.MODID, dist = Dist.CLIENT)
public class BannerCapesClient
{
    public BannerCapesClient(IEventBus modBus, ModContainer modContainer)
    {
        modBus.register(new ClientSetup());
    }

    private static class ClientSetup
    {
        @SubscribeEvent
        private void addLayers(final EntityRenderersEvent.AddLayers event)
        {
            for (PlayerModelType modelType : event.getSkins())
            {
                AvatarRenderer<?> renderer = event.getPlayerRenderer(modelType);
                if (renderer != null)
                {
                    renderer.addLayer(new BannerCapeLayer(renderer, event.getContext().getSprites()));
                }
            }
        }

        @SubscribeEvent
        private void registerRenderStateModifiers(final RegisterRenderStateModifiersEvent event)
        {
            event.registerAvatarEntityModifier(new AvatarRenderStateModifier()
            {
                @Override
                public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState renderState)
                {
                    BannerCapeModel.extractCapeState(avatar, renderState, renderState.partialTick);
                }
            });
        }
    }
}