package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BannerCapes.MODID)
final class BannerCapesDatagen
{
    private BannerCapesDatagen() {}

    @SubscribeEvent
    public static void runDataGeneration(final GatherDataEvent.Client event)
    {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();

        event.addProvider(new ModelsGen(packOutput));
        event.addProvider(new ItemTagsGen(packOutput, registries));
        event.addProvider(new LanguageGen(packOutput));
        event.addProvider(new RecipesGen.Runner(packOutput, registries));
    }
}