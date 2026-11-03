package com.artemoniy7.metrotrains.client.renderer;

import com.artemoniy7.metrotrains.block.entity.TrackBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import com.mojang.blaze3d.vertex.PoseStack;

public class TrackBlockEntityRenderer implements BlockEntityRenderer<TrackBlockEntity> {
    public TrackBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(TrackBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        var blockRenderer = Minecraft.getInstance().getBlockRenderer();

        // The block entity sits in the middle cell. The concrete bed extends one block to either side.
        poseStack.pushPose();
        poseStack.translate(-1.0D, 0.0D, 0.0D);
        poseStack.scale(3.0F, 0.25F, 1.0F);
        blockRenderer.renderSingleBlock(Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState(),
                poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.popPose();

        // Steel rails are placed at +/- 12 model units from the centerline.
        renderCuboid(0.5D - 0.75D - (5.0D / 32.0D), 2.5D / 16.0D, 0.0D,
                5.0F / 16.0F, 7.0F / 16.0F, 1.0F,
                poseStack, bufferSource, packedLight, packedOverlay);
        renderCuboid(0.5D + 0.75D - (5.0D / 32.0D), 2.5D / 16.0D, 0.0D,
                5.0F / 16.0F, 7.0F / 16.0F, 1.0F,
                poseStack, bufferSource, packedLight, packedOverlay);
    }

    private static void renderCuboid(double x, double y, double z, float width, float height, float length,
                                     PoseStack poseStack, MultiBufferSource bufferSource,
                                     int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(width, height, length);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                Blocks.IRON_BLOCK.defaultBlockState(), poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.popPose();
    }
}
