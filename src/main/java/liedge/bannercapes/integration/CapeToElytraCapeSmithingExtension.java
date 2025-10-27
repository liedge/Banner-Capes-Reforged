package liedge.bannercapes.integration;

import liedge.bannercapes.CapeToElytraCapeRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipeInput;

import java.util.Arrays;
import java.util.List;

final class CapeToElytraCapeSmithingExtension extends BaseSmithingCategoryExtension<CapeToElytraCapeRecipe>
{
    @Override
    public void onDisplayedIngredientsUpdate(CapeToElytraCapeRecipe recipe, IRecipeSlotDrawable templateSlot, IRecipeSlotDrawable baseSlot, IRecipeSlotDrawable additionSlot, IRecipeSlotDrawable outputSlot, IFocusGroup focuses)
    {
        ItemStack base = baseSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        ItemStack additional = additionSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);

        SmithingRecipeInput input = new SmithingRecipeInput(ItemStack.EMPTY, base, additional);
        outputSlot.createDisplayOverrides().addItemStack(assembleRecipe(input, recipe));
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(CapeToElytraCapeRecipe recipe, T ingredientAcceptor)
    {
        List<ItemStack> stacks = Arrays.stream(recipe.additional().getItems()).toList();
        if (stacks.size() != 1 || stacks.getFirst().isEmpty()) return;

        ItemStack additionalStack = stacks.getFirst();
        ItemStack[] baseStacks = recipe.base().getItems();

        for (ItemStack ingredient : baseStacks)
        {
            SmithingRecipeInput input = new SmithingRecipeInput(ItemStack.EMPTY, ingredient, additionalStack);
            ingredientAcceptor.addItemStack(assembleRecipe(input, recipe));
        }
    }
}