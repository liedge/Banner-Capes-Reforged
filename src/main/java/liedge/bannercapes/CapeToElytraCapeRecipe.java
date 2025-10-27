package liedge.bannercapes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public record CapeToElytraCapeRecipe(Ingredient base, Ingredient additional) implements CapeSmithingRecipe
{
    private static final MapCodec<CapeToElytraCapeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("base").forGetter(CapeSmithingRecipe::base),
            Ingredient.CODEC.fieldOf("additional").forGetter(CapeSmithingRecipe::additional))
            .apply(instance, CapeToElytraCapeRecipe::new));
    private static final StreamCodec<RegistryFriendlyByteBuf, CapeToElytraCapeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, CapeToElytraCapeRecipe::base,
            Ingredient.CONTENTS_STREAM_CODEC, CapeToElytraCapeRecipe::additional,
            CapeToElytraCapeRecipe::new);
    public static final RecipeSerializer<CapeToElytraCapeRecipe> SERIALIZER = new RecipeSerializer<>()
    {
        @Override
        public MapCodec<CapeToElytraCapeRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CapeToElytraCapeRecipe> streamCodec()
        {
            return STREAM_CODEC;
        }
    };

    @Override
    public Ingredient template()
    {
        return Ingredient.EMPTY;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries)
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
    public RecipeSerializer<?> getSerializer()
    {
        return BannerCapesRecipeSerializers.CAPE_TO_ELYTRA_CAPE_SMITHING.get();
    }
}