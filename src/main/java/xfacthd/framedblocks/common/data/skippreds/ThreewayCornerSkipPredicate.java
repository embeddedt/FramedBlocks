package xfacthd.framedblocks.common.data.skippreds;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import xfacthd.framedblocks.common.block.IFramedBlock;
import xfacthd.framedblocks.common.data.*;
import xfacthd.framedblocks.common.util.SideSkipPredicate;
import xfacthd.framedblocks.common.util.Utils;

public class ThreewayCornerSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(IBlockReader world, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        if (SideSkipPredicate.CTM.test(world, pos, state, adjState, side)) { return true; }
        if (!(adjState.getBlock() instanceof IFramedBlock)) { return false; }

        BlockType adjBlock = ((IFramedBlock) adjState.getBlock()).getBlockType();
        Direction dir = state.getValue(PropertyHolder.FACING_HOR);
        boolean top = state.getValue(PropertyHolder.TOP);

        switch (adjBlock)
        {
            case FRAMED_PRISM_CORNER:
            case FRAMED_THREEWAY_CORNER: return testAgainstThreewayCorner(world, pos, dir, top, adjState, side);
            case FRAMED_INNER_PRISM_CORNER:
            case FRAMED_INNER_THREEWAY_CORNER: return testAgainstInnerThreewayCorner(world, pos, dir, top, adjBlock, adjState, side);
            case FRAMED_DOUBLE_PRISM_CORNER:
            case FRAMED_DOUBLE_THREEWAY_CORNER: return testAgainstDoubleThreewayCorner(world, pos, dir, top, adjState, side);
            case FRAMED_SLOPE:
            case FRAMED_RAIL_SLOPE: return testAgainstSlope(world, pos, dir, top, adjState, side);
            case FRAMED_DOUBLE_SLOPE: return testAgainstDoubleSlope(world, pos, dir, top, adjState, side);
            case FRAMED_CORNER_SLOPE: return testAgainstCorner(world, pos, dir, top, adjState, side);
            case FRAMED_INNER_CORNER_SLOPE: return testAgainstInnerCorner(world, pos, dir, top, adjState, side);
            case FRAMED_DOUBLE_CORNER: return testAgainstDoubleCorner(world, pos, dir, top, adjState, side);
        }

        return false;
    }

    private boolean testAgainstThreewayCorner(IBlockReader world, BlockPos pos, Direction dir, boolean top, BlockState adjState, Direction side)
    {
        Direction adjDir = adjState.getValue(PropertyHolder.FACING_HOR);
        boolean adjTop = adjState.getValue(PropertyHolder.TOP);

        if (side.getAxis() == Direction.Axis.Y && adjTop != top && adjDir == dir && (side == Direction.UP) == top)
        {
            return SideSkipPredicate.compareState(world, pos, side, side);
        }
        else if (adjTop == top && ((side == dir && adjDir == dir.getCounterClockWise()) || (side == dir.getCounterClockWise() && adjDir == dir.getClockWise())))
        {
            return SideSkipPredicate.compareState(world, pos, side, side);
        }
        return false;
    }

    private boolean testAgainstInnerThreewayCorner(IBlockReader world, BlockPos pos, Direction dir, boolean top, BlockType adjBlock, BlockState adjState, Direction side)
    {
        Direction adjDir = adjState.getValue(PropertyHolder.FACING_HOR);
        boolean adjTop = adjState.getValue(PropertyHolder.TOP);

        if (adjBlock == BlockType.FRAMED_INNER_THREEWAY_CORNER) { adjDir = adjDir.getClockWise(); } //Correct rotation discrepancy of the threeway corner

        if (adjTop == top && adjDir == dir && (side == dir || side == dir.getCounterClockWise() || (side == Direction.DOWN && !top) || (side == Direction.UP && top)))
        {
            return SideSkipPredicate.compareState(world, pos, side, top ? Direction.UP : Direction.DOWN);
        }
        return false;
    }

    private boolean testAgainstDoubleThreewayCorner(IBlockReader world, BlockPos pos, Direction dir, boolean top, BlockState adjState, Direction side)
    {
        Direction adjDir = adjState.getValue(PropertyHolder.FACING_HOR);
        boolean adjTop = adjState.getValue(PropertyHolder.TOP);

        if (adjTop == top && adjDir == dir && (side == dir || side == dir.getCounterClockWise() || (side == Direction.UP && top) || (side == Direction.DOWN && !top)))
        {
            return SideSkipPredicate.compareState(world, pos, side, dir);
        }
        else if (adjTop == top && adjDir == dir.getOpposite() && ((side == Direction.UP && top) || (side == Direction.DOWN || !top)))
        {
            return SideSkipPredicate.compareState(world, pos, side, dir);
        }
        else if (adjTop != top && ((side == dir.getCounterClockWise() && adjDir == dir.getCounterClockWise()) || (side == dir && adjDir == dir.getClockWise())))
        {
            return SideSkipPredicate.compareState(world, pos, side, adjDir.getOpposite());
        }
        return false;
    }

    private boolean testAgainstSlope(IBlockReader world, BlockPos pos, Direction dir, boolean top, BlockState adjState, Direction side)
    {
        Direction adjDir = Utils.getBlockFacing(adjState);
        SlopeType adjType = Utils.getSlopeType(adjState);

        if ((side == dir && adjDir == dir.getCounterClockWise()) || (side == dir.getCounterClockWise() && adjDir == dir))
        {
            return adjType != SlopeType.HORIZONTAL && (adjType == SlopeType.TOP) == top && SideSkipPredicate.compareState(world, pos, side, side);
        }
        else if ((!top && side == Direction.DOWN) || (top && side == Direction.UP))
        {
            return adjType == SlopeType.HORIZONTAL && adjDir == dir && SideSkipPredicate.compareState(world, pos, side, side);
        }
        return false;
    }

    private boolean testAgainstDoubleSlope(IBlockReader world, BlockPos pos, Direction dir, boolean top, BlockState adjState, Direction side)
    {
        Direction adjDir = adjState.getValue(PropertyHolder.FACING_HOR);
        SlopeType adjType = adjState.getValue(PropertyHolder.SLOPE_TYPE);

        if (adjType != SlopeType.HORIZONTAL)
        {
            if ((side == dir.getCounterClockWise() && adjDir == dir) || (side == dir && adjDir == dir.getCounterClockWise()))
            {
                return (adjType == SlopeType.TOP) == top && SideSkipPredicate.compareState(world, pos, side, adjDir.getOpposite());
            }
            else if ((side == dir.getCounterClockWise() && adjDir == dir.getOpposite()) || (side == dir && adjDir == dir.getClockWise()))
            {
                return (adjType == SlopeType.TOP) != top && SideSkipPredicate.compareState(world, pos, side, adjDir.getOpposite());
            }
        }
        else if ((!top && side == Direction.DOWN || top && side == Direction.UP) && (adjDir == dir || adjDir == dir.getOpposite()))
        {
            return SideSkipPredicate.compareState(world, pos, side, dir);
        }
        return false;
    }

    private boolean testAgainstCorner(IBlockReader world, BlockPos pos, Direction dir, boolean top, BlockState adjState, Direction side)
    {
        Direction adjDir = adjState.getValue(PropertyHolder.FACING_HOR);
        CornerType adjType = adjState.getValue(PropertyHolder.CORNER_TYPE);

        if (((side == dir && adjDir == dir.getCounterClockWise()) || (side == dir.getCounterClockWise() && adjDir == dir.getClockWise())) && !adjType.isHorizontal())
        {
            return adjType.isTop() == top && SideSkipPredicate.compareState(world, pos, side, side);
        }
        else if ((side == dir && adjDir == dir.getCounterClockWise() && !adjType.isRight()) || (side == dir.getCounterClockWise() && adjDir == dir && adjType.isRight()))
        {
            return adjType.isTop() == top && adjType.isHorizontal() && SideSkipPredicate.compareState(world, pos, side, side);
        }
        else if (((!top && side == Direction.DOWN) || (top && side == Direction.UP)) &&
                ((adjDir == dir.getCounterClockWise() && adjType.isRight()) || (adjDir == dir && !adjType.isRight()))
        )
        {
            return adjType.isTop() != top && adjType.isHorizontal() && SideSkipPredicate.compareState(world, pos, side, side);
        }
        return false;
    }

    private boolean testAgainstInnerCorner(IBlockReader world, BlockPos pos, Direction dir, boolean top, BlockState adjState, Direction side)
    {
        Direction adjDir = adjState.getValue(PropertyHolder.FACING_HOR);
        CornerType adjType = adjState.getValue(PropertyHolder.CORNER_TYPE);

        if (!adjType.isHorizontal() && adjDir == dir.getCounterClockWise() && (side == dir || side == dir.getCounterClockWise()) && adjType.isTop() == top)
        {
            return SideSkipPredicate.compareState(world, pos, side, top ? Direction.UP : Direction.DOWN);
        }
        else if (adjType.isHorizontal() && ((side == dir && adjType.isRight()) || (side == dir.getCounterClockWise() && !adjType.isRight())) && adjType.isTop() == top)
        {
            return SideSkipPredicate.compareState(world, pos, side, top ? Direction.UP : Direction.DOWN);
        }
        else if (adjType.isHorizontal() && ((side == Direction.DOWN && !top) || (side == Direction.UP && top)) && adjType.isTop() == top)
        {
            return ((!adjType.isRight() && adjDir == dir) || (adjType.isRight() && adjDir == dir.getCounterClockWise())) &&
                    SideSkipPredicate.compareState(world, pos, side, top ? Direction.UP : Direction.DOWN);
        }
        return false;
    }

    private boolean testAgainstDoubleCorner(IBlockReader world, BlockPos pos, Direction dir, boolean top, BlockState adjState, Direction side)
    {
        Direction adjDir = adjState.getValue(PropertyHolder.FACING_HOR);
        CornerType adjType = adjState.getValue(PropertyHolder.CORNER_TYPE);

        if (!adjType.isHorizontal())
        {
            if (adjDir == dir && adjType.isTop() == top && (side == dir || side == dir.getCounterClockWise()))
            {
                return SideSkipPredicate.compareState(world, pos, side, top ? Direction.UP : Direction.DOWN);
            }
            else if (adjType.isTop() != top && (side == dir && adjDir == dir.getClockWise() || side == dir.getCounterClockWise() && adjDir == dir.getCounterClockWise()))
            {
                return SideSkipPredicate.compareState(world, pos, side, top ? Direction.UP : Direction.DOWN);
            }
        }
        else if (adjType.isTop() == top)
        {
            if ((!adjType.isRight() && adjDir == dir) || (adjType.isRight() && adjDir == dir.getCounterClockWise()))
            {
                if ((side == Direction.DOWN && !top) || (side == Direction.UP && top))
                {
                    return SideSkipPredicate.compareState(world, pos, side, adjDir);
                }
                else if ((side == dir && adjType.isRight()) || (side == dir.getCounterClockWise() && !adjType.isRight()))
                {
                    return SideSkipPredicate.compareState(world, pos, side, adjDir);
                }
            }
            else if (side.getAxis() == Direction.Axis.Y && ((!adjType.isRight() && adjDir == dir.getOpposite()) || (adjType.isRight() && adjDir == dir.getClockWise())))
            {
                if ((side == Direction.DOWN && !top) || (side == Direction.UP && top))
                {
                    return SideSkipPredicate.compareState(world, pos, side, adjDir.getOpposite());
                }
            }
        }
        else if ((side == dir && adjDir == dir.getClockWise() && !adjType.isRight()) || (side == dir.getCounterClockWise() && adjDir == dir.getOpposite() && adjType.isRight()))
        {
            return SideSkipPredicate.compareState(world, pos, side, adjDir.getOpposite());
        }
        return false;
    }
}