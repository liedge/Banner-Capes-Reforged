package liedge.bannercapes.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import liedge.bannercapes.BannerCapeItem;
import liedge.bannercapes.BannerCapesTags;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesRecipeSerializers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import java.util.Optional;

public final class CapeToElytraCapeRecipe extends CapeSmithingRecipe
{
    public static final MapCodec<CapeToElytraCapeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("base").forGetter(SmithingRecipe::baseIngredient),
            Ingredient.CODEC.fieldOf("addition").forGetter(o -> o.addition))
            .apply(instance, CapeToElytraCapeRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CapeToElytraCapeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, SmithingRecipe::baseIngredient,
            Ingredient.CONTENTS_STREAM_CODEC, o -> o.addition,
            CapeToElytraCapeRecipe::new);

    private final Ingredient addition;

    public CapeToElytraCapeRecipe(Ingredient base, Ingredient addition)
    {
        super(base);
        this.addition = addition;
    }

    @Override
    protected SlotDisplay resultDisplay()
    {
        return new SlotDisplay.TagSlotDisplay(BannerCapesTags.BANNER_ELYTRA_CAPES);
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input)
    {
        ItemStack capeStack = input.base();
        ItemStack elytraStack = input.addition();
        if (!(capeStack.getItem() instanceof BannerCapeItem capeItem)) return ItemStack.EMPTY;

        BannerPatternLayers patternsToCopy = capeStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
        ItemEnchantments enchantmentsToCopy = elytraStack.getTagEnchantments();

        ItemStack result = BannerCapesItems.BANNER_ELYTRA_CAPES.get(capeItem.getBaseColor()).toStack();
        result.set(DataComponents.BANNER_PATTERNS, patternsToCopy);
        result.set(DataComponents.ENCHANTMENTS, enchantmentsToCopy);

        return result;
    }

    @Override
    public RecipeSerializer<? extends CapeSmithingRecipe> getSerializer()
    {
        return BannerCapesRecipeSerializers.CAPE_TO_ELYTRA_CAPE_SMITHING.get();
    }

    @Override
    public Optional<Ingredient> additionIngredient()
    {
        return Optional.of(addition);
    }
}