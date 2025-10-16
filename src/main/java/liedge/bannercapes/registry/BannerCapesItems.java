package liedge.bannercapes.registry;

import com.google.common.collect.ImmutableMap;
import liedge.bannercapes.BannerCapeItem;
import liedge.bannercapes.BannerCapes;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class BannerCapesItems
{
    private BannerCapesItems() {}

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BannerCapes.MODID);

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

    public static final DeferredItem<Item> CAPE_HARNESS = ITEMS.registerSimpleItem("cape_harness");

    public static final Map<DyeColor, DeferredItem<BannerCapeItem>> BANNER_CAPES = Util.make(() ->
    {
        Map<DyeColor, DeferredItem<BannerCapeItem>> map = new EnumMap<>(DyeColor.class);
        for (DyeColor color : DyeColor.values())
        {
            map.put(color, ITEMS.registerItem(color.getSerializedName() + "_banner_cape", properties -> new BannerCapeItem(properties, color), new Item.Properties().stacksTo(1).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)));
        }
        return ImmutableMap.copyOf(map);
    });
}