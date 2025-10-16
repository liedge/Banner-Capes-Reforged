package liedge.bannercapes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import liedge.bannercapes.BannerCapeItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class BannerCapeCurioRenderer implements ICurioRenderer
{
    private final BannerCapeModel capeModel;

    public BannerCapeCurioRenderer()
    {
        this.capeModel = new BannerCapeModel();
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (!(stack.getItem() instanceof BannerCapeItem capeItem)) return;
        if (!(slotContext.entity() instanceof AbstractClientPlayer player)) return;

        ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestItem.is(Items.ELYTRA) || chestItem.getItem() instanceof BannerCapeItem) return;

        capeModel.render(poseStack, bufferSource, light, player, stack, chestItem, capeItem, partialTicks);
    }
}