package liedge.bannercapes.registry;

import liedge.bannercapes.BannerCapeRecipe;
import liedge.bannercapes.BannerCapes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class BannerCapesRecipeSerializers
{
    private BannerCapesRecipeSerializers() {}

    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BannerCapes.MODID);

    public static void register(IEventBus bus)
    {
        SERIALIZERS.register(bus);
    }

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BannerCapeRecipe>> BANNER_CAPE_CRAFTING = SERIALIZERS.register("banner_cape", () -> new SimpleCraftingRecipeSerializer<>(BannerCapeRecipe::new));
}