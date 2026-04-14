package liedge.bannercapes.client;

import liedge.bannercapes.BannerCapeItem;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.entity.ClientAvatarState;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public final class BannerCapeModel extends Model<AvatarRenderState>
{
    public static <T extends Avatar & ClientAvatarEntity> void extractCapeState(T entity, AvatarRenderState renderState, float partialTick)
    {
        ClientAvatarState avatarState = entity.avatarState();

        double desiredX = avatarState.getInterpolatedCloakX(partialTick) - Mth.lerp(partialTick, entity.xo, entity.getX());
        double desiredY = avatarState.getInterpolatedCloakY(partialTick) - Mth.lerp(partialTick, entity.yo, entity.getY());
        double desiredZ = avatarState.getInterpolatedCloakZ(partialTick) - Mth.lerp(partialTick, entity.zo, entity.getZ());
        float bodyYaw = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        double backDirX = Mth.sin(bodyYaw * Mth.DEG_TO_RAD);
        double backDirZ = -Mth.cos(bodyYaw * Mth.DEG_TO_RAD);

        float pitchMax = entity.isFallFlying() ? 16f : 32f;
        renderState.capeFlap = Mth.clamp((float) desiredY * 10f, -6f, pitchMax);
        float speed = avatarState.getInterpolatedBob(partialTick);
        renderState.capeFlap += Mth.sin(avatarState.getInterpolatedWalkDistance(partialTick) * 6f) * 32f * speed;
        if (entity.isCrouching()) renderState.capeFlap += 25f;

        renderState.capeLean = (float)(desiredX * backDirX + desiredZ * backDirZ) * 100f;
        final float speedPitchMin;
        final float speedPitchMax;
        if (entity.isFallFlying())
        {
            speedPitchMin = 5f;
            speedPitchMax = 10f;
        }
        else
        {
            speedPitchMin = 0f;
            speedPitchMax = 150f;
        }
        renderState.capeLean = Mth.clamp(renderState.capeLean, speedPitchMin, speedPitchMax);

        renderState.capeLean2 = (float)(desiredX * backDirZ - desiredZ * backDirX) * 100f;
        renderState.capeLean2 = Mth.clamp(renderState.capeLean2, -20f, 20f);

        ItemStack stack = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (stack.getItem() instanceof BannerCapeItem) renderState.chestEquipment = stack.copy();
    }

    private static ModelPart createRoot()
    {
        ModelPart.Cube capeCube = new ModelPart.Cube(0, 0, -10f, 0, -1f, 20f, 40f, 1f, 0f, 0f, 0f, false, 64, 64, EnumSet.allOf(Direction.class));
        return new ModelPart(List.of(capeCube), Map.of());
    }

    public BannerCapeModel()
    {
        super(createRoot(), RenderTypes::entitySolid);
    }

    @Override
    public void setupAnim(AvatarRenderState renderState)
    {
        if (renderState.isCrouching)
        {
            root.z = 3f;
            root.y = 3.75f;
        }
        else
        {
            root.z = 0;
            root.y = 0;
        }
    }
}