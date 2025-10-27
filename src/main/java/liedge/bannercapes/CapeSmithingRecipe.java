package liedge.bannercapes;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

public interface CapeSmithingRecipe extends SmithingRecipe
{
    Ingredient template();

    Ingredient base();

    Ingredient additional();

    @Override
    default boolean isTemplateIngredient(ItemStack stack)
    {
        return template().test(stack);
    }

    @Override
    default boolean isBaseIngredient(ItemStack stack)
    {
        return base().test(stack);
    }

    @Override
    default boolean isAdditionIngredient(ItemStack stack)
    {
        return additional().test(stack);
    }

    @Override
    default boolean matches(SmithingRecipeInput input, Level level)
    {
        return isTemplateIngredient(input.template()) && isBaseIngredient(input.base()) && isAdditionIngredient(input.addition());
    }

    @Override
    default ItemStack getResultItem(HolderLookup.Provider registries)
    {
        return ItemStack.EMPTY;
    }
}