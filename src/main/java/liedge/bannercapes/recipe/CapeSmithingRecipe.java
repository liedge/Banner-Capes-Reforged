package liedge.bannercapes.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SmithingRecipeDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class CapeSmithingRecipe implements SmithingRecipe
{
    private final Ingredient base;

    private @Nullable PlacementInfo placementInfo;

    protected CapeSmithingRecipe(Ingredient base)
    {
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
    public PlacementInfo placementInfo()
    {
        if (placementInfo == null)
            placementInfo = PlacementInfo.createFromOptionals(List.of(templateIngredient(), Optional.of(base), additionIngredient()));

        return placementInfo;
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

    public record Serializer<R extends CapeSmithingRecipe>(MapCodec<R> codec, StreamCodec<RegistryFriendlyByteBuf, R> streamCodec) implements RecipeSerializer<R>
    { }
}