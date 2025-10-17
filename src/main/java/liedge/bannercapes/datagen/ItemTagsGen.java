package liedge.bannercapes.datagen;

import liedge.bannercapes.registry.BannerCapesItems;
import liedge.limacore.data.generation.LimaTagsProvider;
import liedge.limacore.lib.ModResources;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
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
    private final ModResources resources;

    ItemTagsGen(PackOutput packOutput, ModResources resources, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper)
    {
        super(packOutput, BuiltInRegistries.ITEM, resources.modid(), registries, helper);
        this.resources = resources;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        TagKey<Item> bannerCapes = resources.itemTag("capes");
        TagKey<Item> bannerElytraCapes = resources.itemTag("elytra_capes");

        buildTag(bannerCapes).addHolders(List.copyOf(BannerCapesItems.BANNER_CAPES.values()));
        buildTag(bannerElytraCapes).addHolders(List.copyOf(BannerCapesItems.BANNER_ELYTRA_CAPES.values()));

        buildTag(EQUIPPABLE_ENCHANTABLE).addTags(bannerCapes, bannerElytraCapes);
        buildTag(DURABILITY_ENCHANTABLE).addTag(bannerElytraCapes);
        buildTag(CuriosTags.BACK).addTag(bannerCapes);
    }
}