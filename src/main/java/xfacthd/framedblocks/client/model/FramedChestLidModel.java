package xfacthd.framedblocks.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import xfacthd.framedblocks.api.model.FramedBlockModel;
import xfacthd.framedblocks.api.util.*;
import xfacthd.framedblocks.api.util.client.BakedQuadTransformer;
import xfacthd.framedblocks.api.util.client.ModelUtils;
import xfacthd.framedblocks.common.data.property.LatchType;
import xfacthd.framedblocks.common.data.PropertyHolder;

import java.util.*;

public class FramedChestLidModel extends FramedBlockModel
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(FramedConstants.MOD_ID, "block/framed_chest_lock");

    private final Direction facing;
    private final LatchType latch;
    private final ChunkRenderTypeSet addLayers;

    public FramedChestLidModel(BlockState state, BakedModel baseModel)
    {
        super(state, baseModel);
        this.facing = state.getValue(FramedProperties.FACING_HOR);
        this.latch = state.getValue(PropertyHolder.LATCH_TYPE);
        this.addLayers = latch == LatchType.DEFAULT ? ModelUtils.CUTOUT : ChunkRenderTypeSet.none();
    }

    @Override
    protected void transformQuad(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad)
    {
        if (Utils.isY(quad.getDirection()))
        {
            BakedQuad topBotQuad = ModelUtils.duplicateQuad(quad);
            if (BakedQuadTransformer.createTopBottomQuad(topBotQuad, 1F/16F, 1F/16F, 15F/16F, 15F/16F))
            {
                BakedQuadTransformer.setQuadPosInFacingDir(topBotQuad, quad.getDirection() == Direction.UP ? 14F/16F : 7F/16F);
                quadMap.get(null).add(topBotQuad);
            }
        }
        else
        {
            BakedQuad sideQuad = ModelUtils.duplicateQuad(quad);
            if (BakedQuadTransformer.createSideQuad(sideQuad, 1F/16F, 9F/16F, 15F/16F, 14F/16F))
            {
                BakedQuadTransformer.setQuadPosInFacingDir(sideQuad, 15F/16F);
                quadMap.get(null).add(sideQuad);
            }
        }

        if (latch == LatchType.CAMO)
        {
            FramedChestModel.makeChestLatch(quadMap, quad, facing);
        }
    }

    @Override
    protected ChunkRenderTypeSet getAdditionalRenderTypes(RandomSource rand, ModelData extraData) { return addLayers; }

    @Override
    protected void getAdditionalQuads(Map<Direction, List<BakedQuad>> quadMap, BlockState state, RandomSource rand, ModelData data, RenderType layer)
    {
        List<BakedQuad> quads = baseModel.getQuads(state, null, rand, data, layer);
        for (BakedQuad quad : quads)
        {
            if (quad.getSprite().getName().equals(TEXTURE))
            {
                quadMap.get(null).add(quad);
            }
        }
    }
}