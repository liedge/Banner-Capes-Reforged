package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapes;
import liedge.limacore.lib.ModResources;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BannerCapes.MODID)
final class BannerCapesDatagen
{
    private BannerCapesDatagen() {}

    @SubscribeEvent
    public static void runDataGeneration(final GatherDataEvent event)
    {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();

        ModResources resources = new ModResources(BannerCapes.MODID);

        event.addProvider(new CuriosDataGen(packOutput, helper, registries));
        event.addProvider(new ItemModelsGen(packOutput, resources, helper));
        event.addProvider(new ItemTagsGen(packOutput, registries, helper));
        event.addProvider(new LanguageGen(packOutput, resources));
        event.addProvider(new RecipesGen(packOutput, registries, resources));
    }
}