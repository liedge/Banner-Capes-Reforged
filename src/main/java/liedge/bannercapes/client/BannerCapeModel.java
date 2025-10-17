package liedge.bannercapes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import liedge.bannercapes.BannerCapeItem;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public final class BannerCapeModel
{
    private final ModelPart cape;

    public BannerCapeModel()
    {
        ModelPart.Cube capeCube = new ModelPart.Cube(0, 0, -10f, 0, -1f, 20f, 40f, 1f, 0f, 0f, 0f, false, 64, 64, EnumSet.allOf(Direction.class));
        this.cape = new ModelPart(List.of(capeCube), Map.of());
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, ItemStack capeStack, ItemStack chestItem, BannerCapeItem capeItem, float partialTick)
    {
        if (!player.isInvisible() && player.isModelPartShown(PlayerModelPart.CAPE))
        {
            setupCapeAnimation(player, chestItem);

            poseStack.pushPose();

            poseStack.translate(0f, 0f, 0.125f);
            rotateCape(poseStack, player, partialTick);
            poseStack.scale(0.5f, 0.5f, 0.5f);

            BannerRenderer.renderPatterns(poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY, cape, ModelBakery.BANNER_BASE, true, capeItem.getBaseColor(), capeItem.getPatternLayers(capeStack));

            poseStack.popPose();
        }
    }

    private void setupCapeAnimation(AbstractClientPlayer player, ItemStack chestItem)
    {
        if (chestItem.isEmpty() || chestItem.getItem() instanceof BannerCapeItem)
        {
            if (player.isCrouching())
            {
                cape.z = 3f;
                cape.y = 3.75f;
            }
            else
            {
                cape.z = 0;
                cape.y = 0;
            }
        }
        else
        {
            if (player.isCrouching())
            {
                cape.z = 1f;
                cape.y = 2f;
            }
            else
            {
                cape.z = -2f;
                cape.y = 0f;
            }
        }
    }

    private void rotateCape(PoseStack poseStack, AbstractClientPlayer player, float partialTick)
    {
        double desiredX = Mth.lerp(partialTick, player.xCloakO, player.xCloak) - Mth.lerp(partialTick, player.xo, player.getX());
        double desiredY = Mth.lerp(partialTick, player.yCloakO, player.yCloak) - Mth.lerp(partialTick, player.yo, player.getY());
        double desiredZ = Mth.lerp(partialTick, player.zCloakO, player.zCloak) - Mth.lerp(partialTick, player.zo, player.getZ());

        float bodyYaw = Mth.rotLerp(partialTick, player.yBodyRotO, player.yBodyRot);
        double backDirX = Mth.sin(bodyYaw * Mth.DEG_TO_RAD);
        double backDirZ = -Mth.cos(bodyYaw * Mth.DEG_TO_RAD);

        float pitchMax = player.isFallFlying() ? 16f : 32f;
        float pitch = Mth.clamp((float)desiredY * 10.0F, -6.0F, pitchMax);

        float speedPitch = (float)(desiredX * backDirX + desiredZ * backDirZ) * 100.0F;

        final float speedPitchMin;
        final float speedPitchMax;
        if (player.isFallFlying())
        {
            speedPitchMin = 5f;
            speedPitchMax = 10f;
        }
        else
        {
            speedPitchMin = 0f;
            speedPitchMax = 150f;
        }

        speedPitch = Mth.clamp(speedPitch, speedPitchMin, speedPitchMax);

        float speed = Mth.lerp(partialTick, player.oBob, player.bob);
        pitch += Mth.sin(Mth.lerp(partialTick, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * speed;
        if (player.isCrouching()) pitch += 25f;

        float yaw = (float)(desiredX * backDirZ - desiredZ * backDirX) * 100.0F;
        yaw = Mth.clamp(yaw, -20.0F, 20.0F);

        poseStack.mulPose(Axis.XP.rotationDegrees(6.0F + speedPitch / 2.0F + pitch));
        poseStack.mulPose(Axis.ZP.rotationDegrees(yaw / 2.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw / 2.0F));
    }
}