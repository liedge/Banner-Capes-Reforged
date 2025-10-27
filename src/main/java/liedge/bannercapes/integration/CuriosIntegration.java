package liedge.bannercapes.integration;

import liedge.bannercapes.client.BannerCapeCurioRenderer;
import liedge.bannercapes.registry.BannerCapesItems;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public final class CuriosIntegration
{
    private CuriosIntegration() {}

    public static boolean isCuriosLoaded()
    {
        return ModList.get().isLoaded("curios");
    }

    public static void registerCuriosRenderers()
    {
        BannerCapesItems.BANNER_CAPES.values().forEach(holder -> CuriosRendererRegistry.register(holder.get(), BannerCapeCurioRenderer::new));
    }
}