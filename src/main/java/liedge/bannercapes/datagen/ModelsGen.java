package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapes;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;

import static liedge.bannercapes.registry.BannerCapesItems.*;

class ModelsGen extends ModelProvider
{
    ModelsGen(PackOutput output)
    {
        super(output, BannerCapes.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels)
    {
        itemModels.generateFlatItem(CAPE_HARNESS.asItem(), ModelTemplates.FLAT_ITEM);
        for (DyeColor color : DyeColor.values())
        {
            itemModels.generateFlatItem(BANNER_CAPES.get(color).asItem(), ModelTemplates.FLAT_ITEM);
            itemModels.generateFlatItem(BANNER_ELYTRA_CAPES.get(color).asItem(), ModelTemplates.FLAT_ITEM);
        }
    }
}