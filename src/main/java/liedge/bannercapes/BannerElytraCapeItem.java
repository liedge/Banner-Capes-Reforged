package liedge.bannercapes;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;

public class BannerElytraCapeItem extends BannerCapeItem
{
    public BannerElytraCapeItem(Properties properties, DyeColor baseColor)
    {
        super(properties, baseColor);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate)
    {
        return repairCandidate.is(Items.PHANTOM_MEMBRANE);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        return ElytraItem.isFlyEnabled(stack);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        if (!entity.level().isClientSide())
        {
            int nextTick = flightTicks + 1;
            if (nextTick % 10 == 0)
            {
                if (nextTick % 20 == 0) stack.hurtAndBreak(1, entity, EquipmentSlot.CHEST);

                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }

        return true;
    }

    @Override
    public Holder<SoundEvent> getEquipSound()
    {
        return SoundEvents.ARMOR_EQUIP_ELYTRA;
    }
}