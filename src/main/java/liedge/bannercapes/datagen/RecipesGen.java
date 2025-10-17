package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapeRecipe;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.limacore.data.generation.LimaRecipeProvider;
import liedge.limacore.lib.ModResources;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

class RecipesGen extends LimaRecipeProvider
{
    RecipesGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ModResources resources)
    {
        super(output, registries, resources);
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider registries)
    {
        SpecialRecipeBuilder.special(BannerCapeRecipe.BannerToCape::new).save(output, modResources.location("banner_to_cape"));
        SpecialRecipeBuilder.special(BannerCapeRecipe.CapeToElytraCape::new).save(output, modResources.location("cape_to_elytra_cape"));

        shaped(BannerCapesItems.CAPE_HARNESS).input('i', Items.IRON_INGOT).input('s', Items.STRING).patterns("i i", "s s", " s ").save(output);
    }
}