package liedge.bannercapes.datagen;

import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesTabs;
import liedge.limacore.data.generation.LimaLanguageProvider;
import liedge.limacore.lib.ModResources;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;

class LanguageGen extends LimaLanguageProvider
{
    LanguageGen(PackOutput output, ModResources resources)
    {
        super(output, resources);
    }

    @Override
    protected void addTranslations()
    {
        addItem(BannerCapesItems.CAPE_HARNESS, "Cape Harness");
        for (DyeColor color : DyeColor.values())
        {
            String localizedColor = localizeSimpleName(color);
            addItem(BannerCapesItems.BANNER_CAPES.get(color), localizedColor + " Banner Cape");
        }

        creativeTab(BannerCapesTabs.MAIN_TAB, "Banner Capes");
    }
}