package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapes;
import liedge.bannercapes.BannerCapesTags;
import liedge.bannercapes.recipe.BannerToCapeRecipe;
import liedge.bannercapes.recipe.CapeToElytraCapeRecipe;
import liedge.bannercapes.registry.BannerCapesItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

class RecipesGen extends RecipeProvider
{
    private RecipesGen(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
    }

    private ResourceKey<Recipe<?>> key(String path)
    {
        return ResourceKey.create(Registries.RECIPE, BannerCapes.id(path));
    }

    @Override
    protected void buildRecipes()
    {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);

        output.accept(key("smithing/banner_to_cape"), new BannerToCapeRecipe(Ingredient.of(items.getOrThrow(ItemTags.BANNERS)), Ingredient.of(BannerCapesItems.CAPE_HARNESS)), null);
        output.accept(key("smithing/cape_to_elytra_cape"), new CapeToElytraCapeRecipe(Ingredient.of(items.getOrThrow(BannerCapesTags.BANNER_CAPES)), Ingredient.of(Items.ELYTRA)), null);

        shaped(RecipeCategory.MISC, BannerCapesItems.CAPE_HARNESS)
                .define('i', Items.IRON_INGOT)
                .define('s', Items.STRING)
                .pattern("i i")
                .pattern("s s")
                .pattern(" s ")
                .unlockedBy("get_banner", has(ItemTags.BANNERS))
                .save(output, key("cape_harness"));
    }

    static class Runner extends RecipeProvider.Runner
    {
        Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries)
        {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
        {
            return new RecipesGen(registries, output);
        }

        @Override
        public String getName()
        {
            return "Banner Capes Recipes";
        }
    }
}