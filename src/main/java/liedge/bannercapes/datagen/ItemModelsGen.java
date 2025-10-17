package liedge.bannercapes.datagen;

import liedge.limacore.data.generation.LimaItemModelProvider;
import liedge.limacore.lib.ModResources;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static liedge.bannercapes.registry.BannerCapesItems.*;

class ItemModelsGen extends LimaItemModelProvider
{
    ItemModelsGen(PackOutput output, ModResources resources, ExistingFileHelper existingFileHelper)
    {
        super(output, resources, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        generated(CAPE_HARNESS);

        for (DyeColor color : DyeColor.values())
        {
            generated(BANNER_CAPES.get(color));
            generated(BANNER_ELYTRA_CAPES.get(color));
        }
    }
}