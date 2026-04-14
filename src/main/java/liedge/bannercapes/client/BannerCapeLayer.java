package liedge.bannercapes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import liedge.bannercapes.BannerCapeItem;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.world.item.ItemStack;

public class BannerCapeLayer extends RenderLayer<AvatarRenderState, PlayerModel>
{
    private final BannerCapeModel capeModel;
    private final SpriteGetter spriteGetter;

    public BannerCapeLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer, SpriteGetter spriteGetter)
    {
        super(renderer);
        this.capeModel = new BannerCapeModel();
        this.spriteGetter = spriteGetter;
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

        nodeCollector.submitModel(
                capeModel,
                renderState,
                poseStack,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                -1,
                Sheets.BANNER_BASE,
                spriteGetter,
                renderState.outlineColor,
                null);

        BannerRenderer.submitPatterns(
                spriteGetter,
                poseStack,
                nodeCollector,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                capeModel,
                renderState,
                true,
                capeItem.getBaseColor(),
                capeItem.getPatternLayers(chestItem),
                null);

        poseStack.popPose();
    }
}