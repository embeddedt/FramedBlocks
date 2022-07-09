package xfacthd.framedblocks.client.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;

public class FramedIronDoorModel extends FramedDoorModel
{
    public FramedIronDoorModel(BlockState state, BakedModel baseModel)
    {
        super(state, baseModel);
    }

    @Override
    protected boolean forceUngeneratedBaseModel() { return true; }
}