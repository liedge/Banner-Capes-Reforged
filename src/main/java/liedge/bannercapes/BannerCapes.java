package liedge.bannercapes;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(BannerCapes.MODID)
public class BannerCapes
{
    public static final String MODID = "bannercapes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BannerCapes(IEventBus modBus, ModContainer modContainer)
    {
        modBus.register(new CommonSetup());
    }

    private static class CommonSetup
    {
        @SubscribeEvent
        private void onSetup(final FMLCommonSetupEvent event)
        {
            LOGGER.debug("Mod test loaded.");
        }
    }
}