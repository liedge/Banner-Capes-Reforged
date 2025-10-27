package liedge.bannercapes.client;

import liedge.bannercapes.BannerCapes;
import liedge.bannercapes.integration.CuriosIntegration;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

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
        private void clientSetup(final FMLClientSetupEvent event)
        {
            if (CuriosIntegration.isCuriosLoaded())
            {
                CuriosIntegration.registerCuriosRenderers();
                BannerCapes.LOGGER.info("Registered banner capes curios renderers.");
            }
            else
            {
                BannerCapes.LOGGER.info("Skipping banner capes curios renderer registration since Curios is not installed.");
            }
        }

        @SubscribeEvent
        private void addLayers(final EntityRenderersEvent.AddLayers event)
        {
            for (PlayerSkin.Model model : event.getSkins())
            {
                PlayerRenderer renderer = event.getSkin(model);
                if (renderer != null) renderer.addLayer(new BannerCapeLayer(renderer));
            }
        }
    }
}