package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapesTags;
import liedge.bannercapes.BannerToCapeRecipe;
import liedge.bannercapes.CapeToElytraCapeRecipe;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.limacore.data.generation.LimaRecipeProvider;
import liedge.limacore.lib.ModResources;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

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
        output.accept(modResources.location("smithing/banner_to_cape"), new BannerToCapeRecipe(Ingredient.of(ItemTags.BANNERS), Ingredient.of(BannerCapesItems.CAPE_HARNESS)), null);
        output.accept(modResources.location("smithing/cape_to_elytra_cape"), new CapeToElytraCapeRecipe(Ingredient.of(BannerCapesTags.BANNER_CAPES), Ingredient.of(Items.ELYTRA)), null);

        shaped(BannerCapesItems.CAPE_HARNESS).input('i', Items.IRON_INGOT).input('s', Items.STRING).patterns("i i", "s s", " s ").save(output);
    }
}