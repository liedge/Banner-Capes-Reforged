package liedge.bannercapes.registry;

import liedge.bannercapes.BannerCapes;
import liedge.bannercapes.BannerToCapeRecipe;
import liedge.bannercapes.CapeToElytraCapeRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
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

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BannerToCapeRecipe>> BANNER_TO_CAPE_SMITHING = SERIALIZERS.register("banner_to_cape_smithing", () -> BannerToCapeRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CapeToElytraCapeRecipe>> CAPE_TO_ELYTRA_CAPE_SMITHING = SERIALIZERS.register("cape_to_elytra_cape_smithing", () -> CapeToElytraCapeRecipe.SERIALIZER);
}