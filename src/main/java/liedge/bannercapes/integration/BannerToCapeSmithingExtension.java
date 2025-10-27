package liedge.bannercapes.integration;

import liedge.bannercapes.BannerToCapeRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipeInput;

import java.util.Arrays;
import java.util.List;

final class BannerToCapeSmithingExtension extends BaseSmithingCategoryExtension<BannerToCapeRecipe>
{
    @Override
    public void onDisplayedIngredientsUpdate(BannerToCapeRecipe recipe, IRecipeSlotDrawable templateSlot, IRecipeSlotDrawable baseSlot, IRecipeSlotDrawable additionSlot, IRecipeSlotDrawable outputSlot, IFocusGroup focuses)
    {
        ItemStack template = templateSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        ItemStack base = baseSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);

        SmithingRecipeInput input = new SmithingRecipeInput(template, base, ItemStack.EMPTY);
        outputSlot.createDisplayOverrides().addItemStack(assembleRecipe(input, recipe));
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(BannerToCapeRecipe recipe, T ingredientAcceptor)
    {
        List<ItemStack> stacks = Arrays.stream(recipe.base().getItems()).toList();
        if (stacks.size() != 1 || stacks.getFirst().isEmpty()) return;

        ItemStack baseStack = stacks.getFirst();
        ItemStack[] templateStacks = recipe.template().getItems();

        for (ItemStack ingredient : templateStacks)
        {
            SmithingRecipeInput input = new SmithingRecipeInput(ingredient, baseStack, ItemStack.EMPTY);
            ingredientAcceptor.addItemStack(assembleRecipe(input, recipe));
        }
    }
}