package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapes;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.limacore.data.generation.LimaTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

class ItemTagsGen extends LimaTagsProvider.RegistryTags<Item>
{
    ItemTagsGen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper)
    {
        super(packOutput, BuiltInRegistries.ITEM, BannerCapes.MODID, registries, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        TagKey<Item> bannerTag = net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath(modId, "capes"));

        buildTag(bannerTag).addHolders(List.copyOf(BannerCapesItems.BANNER_CAPES.values()));
        buildTag(CuriosTags.BACK).addTag(bannerTag);
    }
}