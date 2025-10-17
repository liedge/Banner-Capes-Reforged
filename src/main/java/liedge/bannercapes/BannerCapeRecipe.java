package liedge.bannercapes;

import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public abstract class BannerCapeRecipe<T extends Item> extends CustomRecipe
{
    protected BannerCapeRecipe(CraftingBookCategory category)
    {
        super(category);
    }

    protected abstract Item getConversionItem();

    protected abstract Class<T> getBannerItemClass();

    protected abstract ItemStack assemble(ItemStack bannerStack, T bannerItem);

    @Override
    public boolean matches(CraftingInput input, Level level)
    {
        boolean foundConversionItem = false;
        boolean foundBanner = false;

        for (int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            // Allow 1 banner item
            if (getBannerItemClass().isInstance(stack.getItem()))
            {
                if (!foundBanner) foundBanner = true;
                else return false;
            }
            // Allow 1 conversion item (harness or elytra)
            else if (stack.is(getConversionItem()))
            {
                if (!foundConversionItem) foundConversionItem = true;
                else return false;
            }
            else
            {
                return false;
            }
        }

        return foundConversionItem && foundBanner;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries)
    {
        boolean foundConversionItem = false;
        ItemStack bannerStack = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            if (getBannerItemClass().isInstance(stack.getItem()))
            {
                if (bannerStack.isEmpty()) bannerStack = stack.copy();
                else return ItemStack.EMPTY;
            }
            else if (stack.is(getConversionItem()))
            {
                if (!foundConversionItem) foundConversionItem = true;
                else return ItemStack.EMPTY;
            }
            else
            {
                return ItemStack.EMPTY;
            }
        }

        if (foundConversionItem && !bannerStack.isEmpty())
        {
            return assemble(bannerStack, getBannerItemClass().cast(bannerStack.getItem()));
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width >= 3 && height >= 3;
    }

    public static class BannerToCape extends BannerCapeRecipe<BannerItem>
    {
        public BannerToCape(CraftingBookCategory category)
        {
            super(category);
        }

        @Override
        protected Item getConversionItem()
        {
            return BannerCapesItems.CAPE_HARNESS.get();
        }

        @Override
        protected Class<BannerItem> getBannerItemClass()
        {
            return BannerItem.class;
        }

        @Override
        protected ItemStack assemble(ItemStack bannerStack, BannerItem bannerItem)
        {
            ItemStack result = BannerCapesItems.BANNER_CAPES.get(bannerItem.getColor()).toStack();
            result.set(DataComponents.BANNER_PATTERNS, bannerStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
            return result;
        }

        @Override
        public RecipeSerializer<?> getSerializer()
        {
            return BannerCapesRecipeSerializers.BANNER_TO_CAPE.get();
        }
    }

    public static class CapeToElytraCape extends BannerCapeRecipe<BannerCapeItem>
    {
        public CapeToElytraCape(CraftingBookCategory category)
        {
            super(category);
        }

        @Override
        protected Item getConversionItem()
        {
            return Items.ELYTRA;
        }

        @Override
        protected Class<BannerCapeItem> getBannerItemClass()
        {
            return BannerCapeItem.class;
        }

        @Override
        protected ItemStack assemble(ItemStack bannerStack, BannerCapeItem bannerItem)
        {
            ItemStack result = BannerCapesItems.BANNER_ELYTRA_CAPES.get(bannerItem.getBaseColor()).toStack();
            result.set(DataComponents.BANNER_PATTERNS, bannerStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
            return result;
        }

        @Override
        public RecipeSerializer<?> getSerializer()
        {
            return BannerCapesRecipeSerializers.CAPE_TO_ELYTRA_CAPE.get();
        }
    }
}