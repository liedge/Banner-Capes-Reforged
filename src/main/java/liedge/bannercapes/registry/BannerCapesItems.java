package liedge.bannercapes.registry;

import com.google.common.collect.ImmutableMap;
import liedge.bannercapes.BannerCapeItem;
import liedge.bannercapes.BannerCapes;
import liedge.bannercapes.BannerElytraCapeItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public final class BannerCapesItems
{
    private BannerCapesItems() {}

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BannerCapes.MODID);

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

    public static final DeferredItem<Item> CAPE_HARNESS = ITEMS.registerSimpleItem("cape_harness");
    public static final Map<DyeColor, DeferredItem<BannerCapeItem>> BANNER_CAPES = registerCapes("banner_cape", BannerCapeItem::new, properties -> properties.stacksTo(1).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
    public static final Map<DyeColor, DeferredItem<BannerElytraCapeItem>> BANNER_ELYTRA_CAPES = registerCapes("banner_elytra_cape", BannerElytraCapeItem::new, properties -> properties.durability(432).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY).rarity(Rarity.UNCOMMON));

    private static <T extends Item> Map<DyeColor, DeferredItem<T>> registerCapes(String name, BiFunction<Item.Properties, DyeColor, T> constructor, UnaryOperator<Item.Properties> propertiesOp)
    {
        Map<DyeColor, DeferredItem<T>> map = new EnumMap<>(DyeColor.class);
        for (DyeColor color : DyeColor.values())
        {
            DeferredItem<T> holder = ITEMS.register(color.getSerializedName() + '_' + name, () -> constructor.apply(propertiesOp.apply(new Item.Properties()), color));
            map.put(color, holder);
        }
        return ImmutableMap.copyOf(map);
    }
}