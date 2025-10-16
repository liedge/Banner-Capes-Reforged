package liedge.bannercapes.registry;

import liedge.bannercapes.BannerCapes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class BannerCapesTabs
{
    private BannerCapesTabs() {}
    
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BannerCapes.MODID);
    
    public static void register(IEventBus bus)
    {
        TABS.register(bus);
    }
    
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TABS.register("main", id -> CreativeModeTab.builder()
            .title(Component.translatable(id.toLanguageKey("creative_tab")))
            .icon(() -> BannerCapesItems.BANNER_CAPES.get(DyeColor.LIME).toStack())
            .displayItems(BannerCapesTabs::buildMainTab)
            .build());

    private static void buildMainTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output)
    {
        output.accept(BannerCapesItems.CAPE_HARNESS);

        for (DyeColor color : DyeColor.values())
        {
            output.accept(BannerCapesItems.BANNER_CAPES.get(color));
        }
    }
}