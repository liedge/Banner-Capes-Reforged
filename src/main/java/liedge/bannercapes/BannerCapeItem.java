package liedge.bannercapes;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import java.util.List;

public class BannerCapeItem extends Item implements Equipable
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

    @Override
    public EquipmentSlot getEquipmentSlot()
    {
        return EquipmentSlot.CHEST;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
    {
        return swapWithEquipmentSlot(this, level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        BannerItem.appendHoverTextFromBannerBlockEntityTag(stack, tooltipComponents);
    }
}