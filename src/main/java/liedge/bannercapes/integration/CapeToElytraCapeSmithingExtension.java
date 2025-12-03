package liedge.bannercapes.integration;

import liedge.bannercapes.CapeToElytraCapeRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.client.Minecraft;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;

import java.util.List;
import java.util.Objects;

final class CapeToElytraCapeSmithingExtension extends BaseSmithingCategoryExtension<CapeToElytraCapeRecipe>
{
    @Override
    public void onDisplayedIngredientsUpdate(CapeToElytraCapeRecipe recipe, IRecipeSlotDrawable templateSlot, IRecipeSlotDrawable baseSlot, IRecipeSlotDrawable additionSlot, IRecipeSlotDrawable outputSlot, IFocusGroup focuses)
    {
        ItemStack base = baseSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        ItemStack additional = additionSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);

        SmithingRecipeInput input = new SmithingRecipeInput(ItemStack.EMPTY, base, additional);
        outputSlot.createDisplayOverrides().add(assembleRecipe(input, recipe));
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(CapeToElytraCapeRecipe recipe, T ingredientAcceptor)
    {
        ContextMap ctx = SlotDisplayContext.fromLevel(Objects.requireNonNull(Minecraft.getInstance().level));

        ItemStack addition = recipe.additionIngredient().map(i -> i.display().resolveForFirstStack(ctx)).orElse(ItemStack.EMPTY);
        if (addition.isEmpty()) return;
        List<ItemStack> baseStacks = recipe.baseIngredient().display().resolveForStacks(ctx);

        for (ItemStack base : baseStacks)
        {
            SmithingRecipeInput input = new SmithingRecipeInput(ItemStack.EMPTY, base, addition);
            ingredientAcceptor.add(assembleRecipe(input, recipe));
        }
    }
}