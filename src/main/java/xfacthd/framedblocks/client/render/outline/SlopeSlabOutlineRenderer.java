package xfacthd.framedblocks.client.render.outline;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.render.OutlineRenderer;
import xfacthd.framedblocks.common.data.PropertyHolder;

public final class SlopeSlabOutlineRenderer implements OutlineRenderer
{
    @Override
    public void draw(BlockState state, PoseStack poseStack, VertexConsumer builder)
    {
        //Back edges
        OutlineRenderer.drawLine(builder, poseStack, 0, 0, 1, 0, .5, 1);
        OutlineRenderer.drawLine(builder, poseStack, 1, 0, 1, 1, .5, 1);

        //Bottom face
        OutlineRenderer.drawLine(builder, poseStack, 0, 0, 0, 0, 0, 1);
        OutlineRenderer.drawLine(builder, poseStack, 0, 0, 0, 1, 0, 0);
        OutlineRenderer.drawLine(builder, poseStack, 1, 0, 0, 1, 0, 1);
        OutlineRenderer.drawLine(builder, poseStack, 0, 0, 1, 1, 0, 1);

        //Top edge
        OutlineRenderer.drawLine(builder, poseStack, 0, .5, 1, 1, .5, 1);

        //Slope
        OutlineRenderer.drawLine(builder, poseStack, 0, 0, 0, 0, .5, 1);
        OutlineRenderer.drawLine(builder, poseStack, 1, 0, 0, 1, .5, 1);
    }

    @Override
    public void rotateMatrix(PoseStack poseStack, BlockState state)
    {
        OutlineRenderer.super.rotateMatrix(poseStack, state);

        boolean top = state.getValue(FramedProperties.TOP);
        boolean topHalf = state.getValue(PropertyHolder.TOP_HALF);
        if (top)
        {
            OutlineRenderer.mirrorHorizontally(poseStack, false);
        }
        if (topHalf != top)
        {
            poseStack.translate(0, .5, 0);
        }
    }
}
