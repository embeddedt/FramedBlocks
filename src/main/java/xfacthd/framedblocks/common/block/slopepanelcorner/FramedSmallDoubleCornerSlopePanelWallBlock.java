package xfacthd.framedblocks.common.block.slopepanelcorner;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.util.FramedUtils;

public class FramedSmallDoubleCornerSlopePanelWallBlock extends FramedDoubleCornerSlopePanelWallBlock
{
    public FramedSmallDoubleCornerSlopePanelWallBlock(BlockType blockType)
    {
        super(blockType);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        FramedUtils.removeProperty(builder, FramedProperties.SOLID);
    }
}
