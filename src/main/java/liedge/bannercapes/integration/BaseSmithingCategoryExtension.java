package liedge.bannercapes.integration;

import liedge.bannercapes.CapeSmithingRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipeInput;

import java.util.Objects;

abstract class BaseSmithingCategoryExtension<R extends CapeSmithingRecipe> implements ISmithingCategoryExtension<R>
{
    @Override
    public <T extends IIngredientAcceptor<T>> void setTemplate(R recipe, T ingredientAcceptor)
    {
        ingredientAcceptor.addIngredients(recipe.template());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setBase(R recipe, T ingredientAcceptor)
    {
        ingredientAcceptor.addIngredients(recipe.base());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setAddition(R recipe, T ingredientAcceptor)
    {
        ingredientAcceptor.addIngredients(recipe.additional());
    }

    @Override
    public abstract <T extends IIngredientAcceptor<T>> void setOutput(R recipe, T ingredientAcceptor);

    protected ItemStack assembleRecipe(SmithingRecipeInput input, CapeSmithingRecipe recipe)
    {
        RegistryAccess access = Objects.requireNonNull(Minecraft.getInstance().level).registryAccess();
        return recipe.assemble(input, access);
    }
}
