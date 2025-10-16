package liedge.bannercapes;

import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public class BannerCapeRecipe extends CustomRecipe
{
    public BannerCapeRecipe(CraftingBookCategory category)
    {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level)
    {
        boolean foundHarness = false;
        boolean foundBanner = false;

        for (int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty())
            {
                if (stack.getItem() instanceof BannerItem)
                {
                    if (!foundBanner) foundBanner = true;
                    else return false;
                }
                else if (stack.is(BannerCapesItems.CAPE_HARNESS))
                {
                    if (!foundHarness) foundHarness = true;
                    else return false;
                }
                else
                {
                    return false;
                }
            }
        }

        return foundHarness && foundBanner;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries)
    {
        boolean foundHarness = false;
        ItemStack bannerStack = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty())
            {
                if (stack.getItem() instanceof BannerItem)
                {
                    if (bannerStack.isEmpty()) bannerStack = stack.copy();
                    else return ItemStack.EMPTY;
                }
                else if (stack.is(BannerCapesItems.CAPE_HARNESS))
                {
                    if (!foundHarness) foundHarness = true;
                    else return ItemStack.EMPTY;
                }
                else
                {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (foundHarness && !bannerStack.isEmpty() && bannerStack.getItem() instanceof BannerItem bannerItem)
        {
            ItemStack result = BannerCapesItems.BANNER_CAPES.get(bannerItem.getColor()).toStack();
            result.set(DataComponents.BANNER_PATTERNS, bannerStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
            return result;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return BannerCapesRecipeSerializers.BANNER_CAPE_CRAFTING.get();
    }
}