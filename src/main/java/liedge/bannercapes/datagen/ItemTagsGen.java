package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapesTags;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.limacore.data.generation.LimaTagsProvider;
import liedge.limacore.lib.ModResources;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.tags.ItemTags.DURABILITY_ENCHANTABLE;
import static net.minecraft.tags.ItemTags.EQUIPPABLE_ENCHANTABLE;

class ItemTagsGen extends LimaTagsProvider.RegistryTags<Item>
{
    ItemTagsGen(PackOutput packOutput, ModResources resources, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper)
    {
        super(packOutput, BuiltInRegistries.ITEM, resources.modid(), registries, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        buildTag(BannerCapesTags.BANNER_CAPES).addHolders(List.copyOf(BannerCapesItems.BANNER_CAPES.values()));
        buildTag(BannerCapesTags.BANNER_ELYTRA_CAPES).addHolders(List.copyOf(BannerCapesItems.BANNER_ELYTRA_CAPES.values()));

        buildTag(EQUIPPABLE_ENCHANTABLE).addTags(BannerCapesTags.BANNER_CAPES, BannerCapesTags.BANNER_ELYTRA_CAPES);
        buildTag(DURABILITY_ENCHANTABLE).addTag(BannerCapesTags.BANNER_ELYTRA_CAPES);
        buildTag(CuriosTags.BACK).addTag(BannerCapesTags.BANNER_CAPES);
    }
}