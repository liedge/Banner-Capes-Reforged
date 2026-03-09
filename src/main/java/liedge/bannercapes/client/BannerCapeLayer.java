package liedge.bannercapes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import liedge.bannercapes.BannerCapeItem;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.item.ItemStack;

public class BannerCapeLayer extends RenderLayer<AvatarRenderState, PlayerModel>
{
    private final BannerCapeModel capeModel;
    private final MaterialSet materials;

    public BannerCapeLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer, MaterialSet materials)
    {
        super(renderer);
        this.capeModel = new BannerCapeModel();
        this.materials = materials;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, AvatarRenderState renderState, float yRot, float xRot)
    {
        ItemStack chestItem = renderState.chestEquipment;
        if (!(chestItem.getItem() instanceof BannerCapeItem capeItem) || renderState.isInvisible) return;

        capeModel.setupAnim(renderState);

        poseStack.pushPose();
        poseStack.translate(0f, 0f, 0.125f);
        poseStack.mulPose(Axis.XP.rotationDegrees(6f + renderState.capeLean / 2f + renderState.capeFlap));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.capeLean2 / 2f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180f - renderState.capeLean2 / 2f));
        poseStack.scale(0.5f, 0.5f, 0.5f);

        BannerRenderer.submitPatterns(
                materials,
                poseStack,
                nodeCollector,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                capeModel,
                renderState,
                ModelBakery.BANNER_BASE,
                true,
                capeItem.getBaseColor(),
                capeItem.getPatternLayers(chestItem),
                false,
                null,
                0);

        poseStack.popPose();
    }
}