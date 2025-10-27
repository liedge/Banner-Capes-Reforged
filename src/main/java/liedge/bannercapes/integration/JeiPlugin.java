package liedge.bannercapes.integration;

import liedge.bannercapes.BannerCapes;
import liedge.bannercapes.BannerToCapeRecipe;
import liedge.bannercapes.CapeToElytraCapeRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.IExtendableSmithingRecipeCategory;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
    private static final ResourceLocation PLUGIN_ID = BannerCapes.loc("jei_plugin");

    @Override
    public ResourceLocation getPluginUid()
    {
        return PLUGIN_ID;
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration)
    {
        IExtendableSmithingRecipeCategory smithing = registration.getSmithingCategory();
        smithing.addExtension(BannerToCapeRecipe.class, new BannerToCapeSmithingExtension());
        smithing.addExtension(CapeToElytraCapeRecipe.class, new CapeToElytraCapeSmithingExtension());
    }
}