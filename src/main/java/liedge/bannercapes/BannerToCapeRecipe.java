package liedge.bannercapes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public record BannerToCapeRecipe(Ingredient template, Ingredient base) implements CapeSmithingRecipe
{
    private static final MapCodec<BannerToCapeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("template").forGetter(CapeSmithingRecipe::template),
            Ingredient.CODEC.fieldOf("base").forGetter(CapeSmithingRecipe::base))
            .apply(instance, BannerToCapeRecipe::new));
    private static final StreamCodec<RegistryFriendlyByteBuf, BannerToCapeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, BannerToCapeRecipe::template,
            Ingredient.CONTENTS_STREAM_CODEC, BannerToCapeRecipe::base,
            BannerToCapeRecipe::new);
    public static final RecipeSerializer<BannerToCapeRecipe> SERIALIZER = new RecipeSerializer<>()
    {
        @Override
        public MapCodec<BannerToCapeRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BannerToCapeRecipe> streamCodec()
        {
            return STREAM_CODEC;
        }
    };

    @Override
    public Ingredient additional()
    {
        return Ingredient.EMPTY;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries)
    {
        ItemStack bannerStack = input.template();
        if (!(bannerStack.getItem() instanceof BannerItem bannerItem)) return ItemStack.EMPTY;

        ItemStack result = BannerCapesItems.BANNER_CAPES.get(bannerItem.getColor()).toStack();
        result.set(DataComponents.BANNER_PATTERNS, bannerStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
        return result;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries)
    {
        return BannerCapesItems.BANNER_CAPES.get(DyeColor.WHITE).toStack();
    }

    @Override
    public boolean isIncomplete()
    {
        return template.isEmpty() || base.isEmpty();
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return BannerCapesRecipeSerializers.BANNER_TO_CAPE_SMITHING.get();
    }
}