package liedge.bannercapes.registry;

import com.google.common.collect.ImmutableMap;
import liedge.bannercapes.BannerCapeItem;
import liedge.bannercapes.BannerCapes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;
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
    public static final Map<DyeColor, DeferredItem<BannerCapeItem>> BANNER_CAPES = registerCapes("banner_cape", properties -> properties
            .stacksTo(1)
            .component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.CHEST)
                    .setEquipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
                    .build()));
    public static final Map<DyeColor, DeferredItem<BannerCapeItem>> BANNER_ELYTRA_CAPES = registerCapes("banner_elytra_cape", properties -> properties
            .durability(432)
            .rarity(Rarity.EPIC)
            .component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
            .component(DataComponents.GLIDER, Unit.INSTANCE)
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.CHEST)
                    .setEquipSound(SoundEvents.ARMOR_EQUIP_ELYTRA)
                    .setDamageOnHurt(false)
                    .build()));

    private static Map<DyeColor, DeferredItem<BannerCapeItem>> registerCapes(String name, UnaryOperator<Item.Properties> propertiesOp)
    {
        Map<DyeColor, DeferredItem<BannerCapeItem>> map = new EnumMap<>(DyeColor.class);
        for (DyeColor color : DyeColor.values())
        {
            DeferredItem<BannerCapeItem> holder = ITEMS.registerItem(color.getSerializedName() + "_" + name, properties -> new BannerCapeItem(properties, color), propertiesOp);
            map.put(color, holder);
        }
        return ImmutableMap.copyOf(map);
    }
}