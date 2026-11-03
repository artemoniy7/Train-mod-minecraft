package com.artemoniy7.metrotrains.client.renderer;

import com.artemoniy7.metrotrains.block.TrackBlock;
import com.artemoniy7.metrotrains.block.entity.TrackBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class TrackBlockEntityRenderer implements BlockEntityRenderer<TrackBlockEntity> {
    public TrackBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(TrackBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        // OBJ coordinates are centered around (0, 0, 0), while the block origin is its lower corner.
        poseStack.translate(0.5D, 0.0D, 0.5D);

        // The OBJ is authored along the Z axis. Rotate around its own center for east-west placement.
        if (blockEntity.getBlockState().getValue(TrackBlock.FACING).getAxis() == Direction.Axis.X) {
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        }

        TrackObjModel.render(poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(TrackBlockEntity blockEntity) {
        // The OBJ is 1.8 blocks long, so it extends beyond its owning block's bounds.
        return true;
    }
}
