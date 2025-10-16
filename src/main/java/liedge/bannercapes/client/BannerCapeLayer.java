package liedge.bannercapes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import liedge.bannercapes.BannerCapeItem;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class BannerCapeLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{
    private final BannerCapeModel capeModel;

    public BannerCapeLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer)
    {
        super(renderer);
        this.capeModel = new BannerCapeModel();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!(chestItem.getItem() instanceof BannerCapeItem capeItem)) return;

        capeModel.render(poseStack, bufferSource, packedLight, player, chestItem, chestItem, capeItem, partialTick);
    }
}