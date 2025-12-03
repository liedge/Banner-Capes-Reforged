package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapes;
import liedge.bannercapes.registry.BannerCapesItems;
import liedge.bannercapes.registry.BannerCapesTabs;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.LanguageProvider;

class LanguageGen extends LanguageProvider
{
    LanguageGen(PackOutput output)
    {
        super(output, BannerCapes.MODID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        addItem(BannerCapesItems.CAPE_HARNESS, "Cape Harness");
        for (DyeColor color : DyeColor.values())
        {
            String localizedColor = switch (color)
            {
                case WHITE -> "White";
                case ORANGE -> "Orange";
                case MAGENTA -> "Magenta";
                case LIGHT_BLUE -> "Light Blue";
                case YELLOW -> "Yellow";
                case LIME -> "Lime";
                case PINK -> "Pink";
                case GRAY -> "Gray";
                case LIGHT_GRAY -> "Light Gray";
                case CYAN -> "Cyan";
                case PURPLE -> "Purple";
                case BLUE -> "Blue";
                case BROWN -> "Brown";
                case GREEN -> "Green";
                case RED -> "Red";
                case BLACK -> "Black";
            };
            addItem(BannerCapesItems.BANNER_CAPES.get(color), localizedColor + " Banner Cape");
            addItem(BannerCapesItems.BANNER_ELYTRA_CAPES.get(color), localizedColor + " Banner Elytra Cape");
        }
        add(((TranslatableContents) BannerCapesTabs.MAIN_TAB.get().getDisplayName().getContents()).getKey(), "Banner Capes");
    }
}