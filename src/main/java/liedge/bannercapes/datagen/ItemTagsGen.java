package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapes;
import liedge.bannercapes.BannerCapesTags;
import liedge.bannercapes.registry.BannerCapesItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.tags.ItemTags.DURABILITY_ENCHANTABLE;
import static net.minecraft.tags.ItemTags.EQUIPPABLE_ENCHANTABLE;

class ItemTagsGen extends ItemTagsProvider
{
    ItemTagsGen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(packOutput, registries, BannerCapes.MODID);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        List<Item> capes = BannerCapesItems.BANNER_CAPES.values().stream().map(DeferredItem::asItem).toList();
        List<Item> elytraCapes = BannerCapesItems.BANNER_ELYTRA_CAPES.values().stream().map(DeferredItem::asItem).toList();

        tag(BannerCapesTags.BANNER_CAPES).addAll(capes);
        tag(BannerCapesTags.BANNER_ELYTRA_CAPES).addAll(elytraCapes);
        tag(EQUIPPABLE_ENCHANTABLE).addTags(BannerCapesTags.BANNER_CAPES, BannerCapesTags.BANNER_ELYTRA_CAPES);
        tag(DURABILITY_ENCHANTABLE).addTag(BannerCapesTags.BANNER_ELYTRA_CAPES);
    }
}