package liedge.bannercapes.integration;

import liedge.bannercapes.BannerToCapeRecipe;
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

final class BannerToCapeSmithingExtension extends BaseSmithingCategoryExtension<BannerToCapeRecipe>
{
    @Override
    public void onDisplayedIngredientsUpdate(BannerToCapeRecipe recipe, IRecipeSlotDrawable templateSlot, IRecipeSlotDrawable baseSlot, IRecipeSlotDrawable additionSlot, IRecipeSlotDrawable outputSlot, IFocusGroup focuses)
    {
        ItemStack template = templateSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        ItemStack base = baseSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);

        SmithingRecipeInput input = new SmithingRecipeInput(template, base, ItemStack.EMPTY);
        outputSlot.createDisplayOverrides().add(assembleRecipe(input, recipe));
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(BannerToCapeRecipe recipe, T ingredientAcceptor)
    {
        ContextMap ctx = SlotDisplayContext.fromLevel(Objects.requireNonNull(Minecraft.getInstance().level));

        ItemStack base = recipe.baseIngredient().display().resolveForFirstStack(ctx);
        if (base.isEmpty()) return;

        List<ItemStack> templateStacks = recipe.templateIngredient().map(i -> i.display().resolveForStacks(ctx)).orElse(List.of());
        for (ItemStack template : templateStacks)
        {
            SmithingRecipeInput input = new SmithingRecipeInput(template, base, ItemStack.EMPTY);
            ingredientAcceptor.add(assembleRecipe(input, recipe));
        }
    }
}