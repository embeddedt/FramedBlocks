package xfacthd.framedblocks.common.data.skippreds.slopepanelcorner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.HorizontalRotation;
import xfacthd.framedblocks.common.data.property.StairsType;
import xfacthd.framedblocks.common.data.skippreds.*;
import xfacthd.framedblocks.common.data.skippreds.slab.MasonryCornerSegmentSkipPredicate;
import xfacthd.framedblocks.common.data.skippreds.slopepanel.*;
import xfacthd.framedblocks.common.data.skippreds.slopeslab.*;
import xfacthd.framedblocks.common.data.skippreds.stairs.*;

@CullTest(BlockType.FRAMED_EXT_INNER_CORNER_SLOPE_PANEL_W)
public final class ExtendedInnerCornerSlopePanelWallSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        if (adjState.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType type)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);

            return switch (type)
            {
                case FRAMED_EXT_INNER_CORNER_SLOPE_PANEL_W -> testAgainstExtendedInnerCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_LARGE_CORNER_SLOPE_PANEL_W -> testAgainstLargeCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL_W -> testAgainstLargeInnerCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_EXT_CORNER_SLOPE_PANEL_W -> testAgainstExtendedCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_ELEVATED_SLOPE_SLAB -> testAgainstElevatedSlopeSlab(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_ELEV_SLOPE_SLAB_CORNER -> testAgainstFlatElevatedSlopeSlabCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_ELEV_INNER_SLOPE_SLAB_CORNER -> testAgainstFlatElevatedInnerSlopeSlabCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_EXTENDED_SLOPE_PANEL -> testAgainstExtendedSlopePanel(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_EXT_SLOPE_PANEL_CORNER -> testAgainstFlatExtendedSlopePanelCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_EXT_INNER_SLOPE_PANEL_CORNER -> testAgainstFlatExtendedInnerSlopePanelCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_MASONRY_CORNER_SEGMENT -> testAgainstMasonryCornerSegment(
                        dir, rot, adjState, side
                );
                case FRAMED_STAIRS -> testAgainstStairs(
                        dir, rot, adjState, side
                );
                case FRAMED_VERTICAL_STAIRS -> testAgainstVerticalStairs(
                        dir, rot, adjState, side
                );
                case FRAMED_HALF_STAIRS -> testAgainstHalfStairs(
                        dir, rot, adjState, side
                );
                default -> false;
            };
        }
        return false;
    }

    @CullTest.TestTarget(BlockType.FRAMED_EXT_INNER_CORNER_SLOPE_PANEL_W)
    private static boolean testAgainstExtendedInnerCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        if (getTriDir(dir, rot, side).isEqualTo(getTriDir(adjDir, adjRot, side.getOpposite())))
        {
            return true;
        }
        if (getStairDir(dir, rot, side).isEqualTo(getStairDir(adjDir, adjRot, side.getOpposite())))
        {
            return true;
        }
        return false;
    }

    @CullTest.TestTarget(BlockType.FRAMED_LARGE_CORNER_SLOPE_PANEL_W)
    private static boolean testAgainstLargeCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getStairDir(dir, rot, side).isEqualTo(LargeCornerSlopePanelWallSkipPredicate.getStairDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL_W)
    private static boolean testAgainstLargeInnerCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getStairDir(dir, rot, side).isEqualTo(LargeInnerCornerSlopePanelWallSkipPredicate.getStairDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_EXT_CORNER_SLOPE_PANEL_W)
    private static boolean testAgainstExtendedCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getTriDir(dir, rot, side).isEqualTo(ExtendedCornerSlopePanelWallSkipPredicate.getTriDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_ELEVATED_SLOPE_SLAB)
    private static boolean testAgainstElevatedSlopeSlab(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getTriDir(dir, rot, side).isEqualTo(ElevatedSlopeSlabSkipPredicate.getTriDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_FLAT_ELEV_SLOPE_SLAB_CORNER)
    private static boolean testAgainstFlatElevatedSlopeSlabCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getTriDir(dir, rot, side).isEqualTo(FlatElevatedSlopeSlabCornerSkipPredicate.getTriDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_FLAT_ELEV_INNER_SLOPE_SLAB_CORNER)
    private static boolean testAgainstFlatElevatedInnerSlopeSlabCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getTriDir(dir, rot, side).isEqualTo(FlatElevatedInnerSlopeSlabCornerSkipPredicate.getTriDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_EXTENDED_SLOPE_PANEL)
    private static boolean testAgainstExtendedSlopePanel(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getTriDir(dir, rot, side).isEqualTo(ExtendedSlopePanelSkipPredicate.getTriDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_FLAT_EXT_SLOPE_PANEL_CORNER)
    private static boolean testAgainstFlatExtendedSlopePanelCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getTriDir(dir, rot, side).isEqualTo(FlatExtendedSlopePanelCornerSkipPredicate.getTriDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_FLAT_EXT_INNER_SLOPE_PANEL_CORNER)
    private static boolean testAgainstFlatExtendedInnerSlopePanelCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getTriDir(dir, rot, side).isEqualTo(FlatExtendedInnerSlopePanelCornerSkipPredicate.getTriDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_MASONRY_CORNER_SEGMENT)
    private static boolean testAgainstMasonryCornerSegment(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        return getStairDir(dir, rot, side).isEqualTo(MasonryCornerSegmentSkipPredicate.getStairDir(adjDir, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_STAIRS)
    private static boolean testAgainstStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(StairBlock.FACING);
        StairsShape adjShape = adjState.getValue(StairBlock.SHAPE);
        Half adjHalf = adjState.getValue(StairBlock.HALF);

        return getStairDir(dir, rot, side).isEqualTo(StairsSkipPredicate.getStairDir(adjDir, adjShape, adjHalf, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_VERTICAL_STAIRS)
    private static boolean testAgainstVerticalStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        StairsType adjType = adjState.getValue(PropertyHolder.STAIRS_TYPE);

        return getStairDir(dir, rot, side).isEqualTo(VerticalStairsSkipPredicate.getStairDir(adjDir, adjType, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_HALF_STAIRS)
    private static boolean testAgainstHalfStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjRight = adjState.getValue(PropertyHolder.RIGHT);

        return getStairDir(dir, rot, side).isEqualTo(HalfStairsSkipPredicate.getStairDir(adjDir, adjTop, adjRight, side.getOpposite()));
    }



    public static HalfTriangleDir getTriDir(Direction dir, HorizontalRotation rot, Direction side)
    {
        Direction rotDir = rot.withFacing(dir);
        Direction perpRotDir = rot.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);
        if (side == rotDir)
        {
            return HalfTriangleDir.fromDirections(perpRotDir.getOpposite(), dir, false);
        }
        else if (side == perpRotDir)
        {
            return HalfTriangleDir.fromDirections(rotDir.getOpposite(), dir, false);
        }
        return HalfTriangleDir.NULL;
    }

    public static TriangleDir getStairDir(Direction dir, HorizontalRotation rot, Direction side)
    {
        if (side == dir.getOpposite())
        {
            Direction rotDir = rot.withFacing(dir);
            Direction perpRotDir = rot.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);
            return TriangleDir.fromDirections(rotDir.getOpposite(), perpRotDir.getOpposite());
        }
        return TriangleDir.NULL;
    }
}
