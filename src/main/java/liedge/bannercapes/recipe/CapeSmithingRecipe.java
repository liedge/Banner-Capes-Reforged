package liedge.bannercapes.recipe;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.SimpleSmithingRecipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SmithingRecipeDisplay;

import java.util.List;
import java.util.Optional;

public abstract class CapeSmithingRecipe extends SimpleSmithingRecipe
{
    private static final CommonInfo COMMON_INFO = new CommonInfo(true);

    private final Ingredient base;

    protected CapeSmithingRecipe(Ingredient base)
    {
        super(COMMON_INFO);
        this.base = base;
    }

    protected abstract SlotDisplay resultDisplay();

    @Override
    public Optional<Ingredient> templateIngredient()
    {
        return Optional.empty();
    }

    @Override
    public Ingredient baseIngredient()
    {
        return base;
    }

    @Override
    public Optional<Ingredient> additionIngredient()
    {
        return Optional.empty();
    }

    @Override
    protected PlacementInfo createPlacementInfo()
    {
        return PlacementInfo.createFromOptionals(List.of(templateIngredient(), Optional.of(base), additionIngredient()));
    }

    @Override
    public final List<RecipeDisplay> display()
    {
        SmithingRecipeDisplay recipeDisplay = new SmithingRecipeDisplay(
                Ingredient.optionalIngredientToDisplay(templateIngredient()),
                base.display(),
                Ingredient.optionalIngredientToDisplay(additionIngredient()),
                resultDisplay(),
                new SlotDisplay.ItemSlotDisplay(Items.SMITHING_TABLE));
        return List.of(recipeDisplay);
    }
}