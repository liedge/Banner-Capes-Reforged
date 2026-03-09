package liedge.bannercapes.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import liedge.bannercapes.BannerCapesTags;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import java.util.Optional;

public final class BannerToCapeRecipe extends CapeSmithingRecipe
{
    public static final MapCodec<BannerToCapeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("template").forGetter(o -> o.template),
            Ingredient.CODEC.fieldOf("base").forGetter(SmithingRecipe::baseIngredient))
            .apply(instance, BannerToCapeRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BannerToCapeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, o -> o.template,
            Ingredient.CONTENTS_STREAM_CODEC, SmithingRecipe::baseIngredient,
            BannerToCapeRecipe::new);

    private final Ingredient template;

    public BannerToCapeRecipe(Ingredient template, Ingredient base)
    {
        super(base);
        this.template = template;
    }

    @Override
    protected SlotDisplay resultDisplay()
    {
        return new SlotDisplay.TagSlotDisplay(BannerCapesTags.BANNER_CAPES);
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries)
    {
        ItemStack templateInput = input.template();
        if (!(templateInput.getItem() instanceof BannerItem bannerItem)) return ItemStack.EMPTY;

        ItemStack result = BannerCapesItems.BANNER_CAPES.get(bannerItem.getColor()).toStack();
        result.set(DataComponents.BANNER_PATTERNS, templateInput.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
        return result;
    }

    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer()
    {
        return BannerCapesRecipeSerializers.BANNER_TO_CAPE_SMITHING.get();
    }

    @Override
    public Optional<Ingredient> templateIngredient()
    {
        return Optional.of(template);
    }
}