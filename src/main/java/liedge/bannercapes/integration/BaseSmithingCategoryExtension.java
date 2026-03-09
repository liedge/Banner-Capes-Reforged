package liedge.bannercapes.integration;

import liedge.bannercapes.recipe.CapeSmithingRecipe;
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
        recipe.templateIngredient().ifPresent(ingredientAcceptor::add);
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setBase(R recipe, T ingredientAcceptor)
    {
        ingredientAcceptor.add(recipe.baseIngredient());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setAddition(R recipe, T ingredientAcceptor)
    {
        recipe.additionIngredient().ifPresent(ingredientAcceptor::add);
    }

    @Override
    public abstract <T extends IIngredientAcceptor<T>> void setOutput(R recipe, T ingredientAcceptor);

    protected ItemStack assembleRecipe(SmithingRecipeInput input, CapeSmithingRecipe recipe)
    {
        RegistryAccess access = Objects.requireNonNull(Minecraft.getInstance().level).registryAccess();
        return recipe.assemble(input, access);
    }
}
