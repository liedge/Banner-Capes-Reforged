package liedge.bannercapes;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public class BannerCapeItem extends Item
{
    private final DyeColor baseColor;

    public BannerCapeItem(Properties properties, DyeColor baseColor)
    {
        super(properties);
        this.baseColor = baseColor;
    }

    public BannerPatternLayers getPatternLayers(ItemStack stack)
    {
        return stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
    }

    public DyeColor getBaseColor()
    {
        return baseColor;
    }
}