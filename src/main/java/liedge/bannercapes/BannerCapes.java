package liedge.bannercapes;

import com.mojang.logging.LogUtils;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesRecipeSerializers;
import liedge.bannercapes.registry.BannerCapesTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(BannerCapes.MODID)
public class BannerCapes
{
    public static final String MODID = "bannercapes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BannerCapes(IEventBus modBus, ModContainer modContainer)
    {
        BannerCapesItems.register(modBus);
        BannerCapesRecipeSerializers.register(modBus);
        BannerCapesTabs.register(modBus);
    }
}