package liedge.bannercapes;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static liedge.bannercapes.BannerCapes.loc;

public final class BannerCapesTags
{
    private BannerCapesTags() {}

    public static final TagKey<Item> BANNER_CAPES = ItemTags.create(loc("capes"));
    public static final TagKey<Item> BANNER_ELYTRA_CAPES = ItemTags.create(loc("elytra_capes"));
}