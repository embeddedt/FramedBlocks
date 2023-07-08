package xfacthd.framedblocks.common.data.skippreds.slopeslab;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.predicate.SideSkipPredicate;
import xfacthd.framedblocks.common.block.AbstractFramedDoubleBlock;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.SlopeType;
import xfacthd.framedblocks.common.data.skippreds.HalfDir;
import xfacthd.framedblocks.common.data.skippreds.HalfTriangleDir;
import xfacthd.framedblocks.common.data.skippreds.slab.SlabEdgeSkipPredicate;
import xfacthd.framedblocks.common.data.skippreds.slab.SlabSkipPredicate;
import xfacthd.framedblocks.common.data.skippreds.stairs.SlopedStairsSkipPredicate;
import xfacthd.framedblocks.common.data.skippreds.stairs.StairsSkipPredicate;
import xfacthd.framedblocks.common.data.skippreds.stairs.VerticalHalfStairsSkipPredicate;

public final class FlatInnerSlopeSlabCornerSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        boolean top = state.getValue(FramedProperties.TOP);
        boolean topHalf = state.getValue(PropertyHolder.TOP_HALF);

        if (top == topHalf && ((topHalf && side == Direction.UP) || (!topHalf && side == Direction.DOWN)))
        {
            return SideSkipPredicate.FULL_FACE.test(level, pos, state, adjState, side);
        }

        if (adjState.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType blockType)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);

            return switch (blockType)
            {
                case FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER -> testAgainstFlatInnerSlopeSlabCorner(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_FLAT_SLOPE_SLAB_CORNER -> testAgainstFlatSlopeSlabCorner(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_SLOPE_SLAB -> testAgainstSlopeSlab(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_ELEVATED_SLOPE_SLAB -> testAgainstElevatedSlopeSlab(
                        dir, topHalf, adjState, side
                );
                case FRAMED_DOUBLE_SLOPE_SLAB -> testAgainstDoubleSlopeSlab(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_INV_DOUBLE_SLOPE_SLAB -> testAgainstInverseDoubleSlopeSlab(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB -> testAgainstElevatedDoubleSlopeSlab(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_STACKED_SLOPE_SLAB -> testAgainstStackedSlopeSlab(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_FLAT_ELEV_SLOPE_SLAB_CORNER -> testAgainstFlatElevatedSlopeSlabCorner(
                        dir, topHalf, adjState, side
                );
                case FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER -> testAgainstFlatDoubleSlopeSlabCorner(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_FLAT_INV_DOUBLE_SLOPE_SLAB_CORNER -> testAgainstFlatInverseDoubleSlopeSlabCorner(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_FLAT_ELEV_DOUBLE_SLOPE_SLAB_CORNER -> testAgainstFlatElevatedDoubleSlopeSlabCorner(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_FLAT_ELEV_INNER_DOUBLE_SLOPE_SLAB_CORNER -> testAgainstFlatElevatedInnerDoubleSlopeSlabCorner(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_FLAT_STACKED_SLOPE_SLAB_CORNER -> testAgainstFlatStackedSlopeSlabCorner(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_FLAT_STACKED_INNER_SLOPE_SLAB_CORNER -> testAgainstFlatStackedInnerSlopeSlabCorner(
                        dir, top, topHalf, adjState, side
                );
                case FRAMED_SLAB -> testAgainstSlab(
                        dir, topHalf, adjState, side
                );
                case FRAMED_DOUBLE_SLAB -> testAgainstDoubleSlab(
                        dir, topHalf, adjState, side
                );
                case FRAMED_SLAB_EDGE -> testAgainstSlabEdge(
                        dir, topHalf, adjState, side
                );
                case FRAMED_DIVIDED_SLAB -> testAgainstDividedSlab(
                        dir, topHalf, adjState, side
                );
                case FRAMED_DIVIDED_PANEL_HORIZONTAL -> testAgainstDividedPanelHor(
                        dir, topHalf, adjState, side
                );
                case FRAMED_STAIRS -> testAgainstStairs(
                        dir, topHalf, adjState, side
                );
                case FRAMED_DOUBLE_STAIRS -> testAgainstDoubleStairs(
                        dir, topHalf, adjState, side
                );
                case FRAMED_VERTICAL_HALF_STAIRS, FRAMED_VERTICAL_HALF_SLOPE -> testAgainstVerticalHalfStairs(
                        dir, topHalf, adjState, side
                );
                case FRAMED_VERTICAL_DIVIDED_STAIRS -> testAgainstVerticalDividedStairs(
                        dir, topHalf, adjState, side
                );
                case FRAMED_DIVIDED_SLOPE -> testAgainstDividedSlope(
                        dir, topHalf, adjState, side
                );
                case FRAMED_VERTICAL_DOUBLE_HALF_SLOPE -> testAgainstVerticalDoubleHalfSlope(
                        dir, topHalf, adjState, side
                );
                case FRAMED_SLOPED_STAIRS -> testAgainstSlopedStairs(
                        dir, topHalf, adjState, side
                );
                default -> false;
            };
        }
        return false;
    }

    private static boolean testAgainstFlatInnerSlopeSlabCorner(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjTopHalf = adjState.getValue(PropertyHolder.TOP_HALF);

        if (getHalfDir(dir, topHalf, side).isEqualTo(getHalfDir(adjDir, adjTopHalf, side.getOpposite())))
        {
            return true;
        }
        else if (getTriDir(dir, topHalf, top, side).isEqualTo(getTriDir(adjDir, adjTopHalf, adjTop, side.getOpposite())))
        {
            return true;
        }
        return false;
    }

    private static boolean testAgainstFlatSlopeSlabCorner(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjTopHalf = adjState.getValue(PropertyHolder.TOP_HALF);

        return getTriDir(dir, topHalf, top, side).isEqualTo(FlatSlopeSlabCornerSkipPredicate.getTriDir(adjDir, adjTopHalf, adjTop, side.getOpposite()));
    }

    private static boolean testAgainstSlopeSlab(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjTopHalf = adjState.getValue(PropertyHolder.TOP_HALF);

        if (getHalfDir(dir, topHalf, side).isEqualTo(SlopeSlabSkipPredicate.getHalfDir(adjDir, adjTopHalf, side.getOpposite())))
        {
            return true;
        }
        else if (getTriDir(dir, topHalf, top, side).isEqualTo(SlopeSlabSkipPredicate.getTriDir(adjDir, adjTopHalf, adjTop, side.getOpposite())))
        {
            return true;
        }
        return false;
    }

    private static boolean testAgainstElevatedSlopeSlab(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getHalfDir(dir, topHalf, side).isEqualTo(ElevatedSlopeSlabSkipPredicate.getHalfDir(adjDir, adjTop, side.getOpposite()));
    }

    private static boolean testAgainstDoubleSlopeSlab(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlopeSlab(dir, top, topHalf, states.getA(), side) ||
               testAgainstSlopeSlab(dir, top, topHalf, states.getB(), side);
    }

    private static boolean testAgainstInverseDoubleSlopeSlab(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlopeSlab(dir, top, topHalf, states.getA(), side) ||
               testAgainstSlopeSlab(dir, top, topHalf, states.getB(), side);
    }

    private static boolean testAgainstElevatedDoubleSlopeSlab(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstElevatedSlopeSlab(dir, topHalf, states.getA(), side) ||
               testAgainstSlopeSlab(dir, top, topHalf, states.getB(), side);
    }

    private static boolean testAgainstStackedSlopeSlab(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlab(dir, topHalf, states.getA(), side) ||
               testAgainstSlopeSlab(dir, top, topHalf, states.getB(), side);
    }

    private static boolean testAgainstFlatElevatedSlopeSlabCorner(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getHalfDir(dir, topHalf, side).isEqualTo(FlatElevatedSlopeSlabCornerSkipPredicate.getHalfDir(adjDir, adjTop, side.getOpposite()));
    }

    private static boolean testAgainstFlatDoubleSlopeSlabCorner(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatInnerSlopeSlabCorner(dir, top, topHalf, states.getA(), side);
    }

    private static boolean testAgainstFlatInverseDoubleSlopeSlabCorner(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatInnerSlopeSlabCorner(dir, top, topHalf, states.getA(), side);
    }

    private static boolean testAgainstFlatElevatedDoubleSlopeSlabCorner(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatElevatedSlopeSlabCorner(dir, topHalf, states.getA(), side) ||
               testAgainstFlatInnerSlopeSlabCorner(dir, top, topHalf, states.getB(), side);
    }

    private static boolean testAgainstFlatElevatedInnerDoubleSlopeSlabCorner(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatSlopeSlabCorner(dir, top, topHalf, states.getB(), side);
    }

    private static boolean testAgainstFlatStackedSlopeSlabCorner(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlab(dir, topHalf, states.getA(), side) ||
               testAgainstFlatSlopeSlabCorner(dir, top, topHalf, states.getB(), side);
    }

    private static boolean testAgainstFlatStackedInnerSlopeSlabCorner(
            Direction dir, boolean top, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlab(dir, topHalf, states.getA(), side) ||
               testAgainstFlatInnerSlopeSlabCorner(dir, top, topHalf, states.getB(), side);
    }

    private static boolean testAgainstSlab(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        return getHalfDir(dir, topHalf, side).isEqualTo(SlabSkipPredicate.getHalfDir(adjTop, side.getOpposite()));
    }

    private static boolean testAgainstDoubleSlab(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlab(dir, topHalf, states.getA(), side) ||
               testAgainstSlab(dir, topHalf, states.getB(), side);
    }

    private static boolean testAgainstSlabEdge(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getHalfDir(dir, topHalf, side).isEqualTo(SlabEdgeSkipPredicate.getHalfDir(adjDir, adjTop, side.getOpposite()));
    }

    private static boolean testAgainstDividedSlab(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlabEdge(dir, topHalf, states.getA(), side) ||
               testAgainstSlabEdge(dir, topHalf, states.getB(), side);
    }

    private static boolean testAgainstDividedPanelHor(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlabEdge(dir, topHalf, states.getA(), side) ||
               testAgainstSlabEdge(dir, topHalf, states.getB(), side);
    }

    private static boolean testAgainstStairs(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(StairBlock.FACING);
        StairsShape adjShape = adjState.getValue(StairBlock.SHAPE);
        Half adjHalf = adjState.getValue(StairBlock.HALF);

        return getHalfDir(dir, topHalf, side).isEqualTo(StairsSkipPredicate.getHalfDir(adjDir, adjShape, adjHalf, side.getOpposite()));
    }

    private static boolean testAgainstDoubleStairs(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstStairs(dir, topHalf, states.getA(), side) ||
               testAgainstSlabEdge(dir, topHalf, states.getB(), side);
    }

    private static boolean testAgainstVerticalHalfStairs(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getHalfDir(dir, topHalf, side).isEqualTo(VerticalHalfStairsSkipPredicate.getHalfDir(adjDir, adjTop, side.getOpposite()));
    }

    private static boolean testAgainstVerticalDividedStairs(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstVerticalHalfStairs(dir, topHalf, states.getA(), side) ||
               testAgainstVerticalHalfStairs(dir, topHalf, states.getB(), side);
    }

    private static boolean testAgainstDividedSlope(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        if (adjState.getValue(PropertyHolder.SLOPE_TYPE) != SlopeType.HORIZONTAL)
        {
            return false;
        }

        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        //Half slopes re-use the half stairs check
        return testAgainstVerticalHalfStairs(dir, topHalf, states.getA(), side) ||
               testAgainstVerticalHalfStairs(dir, topHalf, states.getB(), side);
    }

    private static boolean testAgainstVerticalDoubleHalfSlope(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        //Half slopes re-use the half stairs check
        return testAgainstVerticalHalfStairs(dir, topHalf, states.getA(), side) ||
               testAgainstVerticalHalfStairs(dir, topHalf, states.getB(), side);
    }

    private static boolean testAgainstSlopedStairs(
            Direction dir, boolean topHalf, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getHalfDir(dir, topHalf, side).isEqualTo(SlopedStairsSkipPredicate.getHalfDir(adjDir, adjTop, side.getOpposite()));
    }



    public static HalfTriangleDir getTriDir(Direction dir, boolean topHalf, boolean top, Direction side)
    {
        if (side == dir.getOpposite() || side == dir.getClockWise())
        {
            Direction longEdge = top ? Direction.UP : Direction.DOWN;
            Direction shortEdge = side == dir.getOpposite() ? dir.getCounterClockWise() : dir;
            return HalfTriangleDir.fromDirections(longEdge, shortEdge, topHalf == top);
        }
        return HalfTriangleDir.NULL;
    }

    public static HalfDir getHalfDir(Direction dir, boolean topHalf, Direction side)
    {
        if (side == dir || side == dir.getCounterClockWise())
        {
            return HalfDir.fromDirections(
                    side,
                    topHalf ? Direction.UP : Direction.DOWN
            );
        }
        return HalfDir.NULL;
    }
}
