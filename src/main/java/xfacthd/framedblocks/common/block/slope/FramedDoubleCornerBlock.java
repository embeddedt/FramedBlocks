package xfacthd.framedblocks.common.block.slope;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.camo.CamoContainer;
import xfacthd.framedblocks.api.predicate.CtmPredicate;
import xfacthd.framedblocks.api.util.*;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.AbstractFramedDoubleBlock;
import xfacthd.framedblocks.common.data.*;
import xfacthd.framedblocks.common.blockentity.*;
import xfacthd.framedblocks.common.data.property.CornerType;

public class FramedDoubleCornerBlock extends AbstractFramedDoubleBlock
{
    public static final CtmPredicate CTM_PREDICATE = (state, side) ->
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        CornerType type = state.getValue(PropertyHolder.CORNER_TYPE);

        if (type.isHorizontal())
        {
            if (side == null) { return false; }

            return  side == dir || side == dir.getOpposite() ||
                   (side == dir.getCounterClockWise() && !type.isRight()) || (side == dir.getClockWise() && type.isRight()) ||
                   (side == Direction.DOWN && !type.isTop()) || (side == Direction.UP && type.isTop());
        }
        else
        {
            return (side != null && Utils.isY(side)) || side == dir || side == dir.getCounterClockWise();
        }
    };

    public FramedDoubleCornerBlock() { super(BlockType.FRAMED_DOUBLE_CORNER); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.FACING_HOR, PropertyHolder.CORNER_TYPE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = defaultBlockState();

        Direction side = context.getClickedFace();
        Vec3 hitPoint = Utils.fraction(context.getClickLocation());
        if (!Utils.isY(side))
        {
            if (hitPoint.y() < (3D / 16D))
            {
                side = Direction.UP;
            }
            else if (hitPoint.y() > (13D / 16D))
            {
                side = Direction.DOWN;
            }
        }

        return withCornerType(state, context, side, hitPoint, context.getHorizontalDirection());
    }

    @Override
    @SuppressWarnings("deprecation")
    public SoundType getCamoSound(BlockState state, LevelReader level, BlockPos pos)
    {
        CornerType type = state.getValue(PropertyHolder.CORNER_TYPE);
        if (level.getBlockEntity(pos) instanceof FramedDoubleBlockEntity dbe)
        {
            CamoContainer camo = (type.isHorizontal() || type.isTop()) ? dbe.getCamo() : dbe.getCamoTwo();
            if (!camo.isEmpty())
            {
                return camo.getSoundType();
            }

            camo = (type.isHorizontal() || type.isTop()) ? dbe.getCamoTwo() : dbe.getCamo();
            if (!camo.isEmpty())
            {
                return camo.getSoundType();
            }
        }
        return getSoundType(state);
    }

    @Override
    public BlockState rotate(BlockState state, Direction side, Rotation rot)
    {
        CornerType type = state.getValue(PropertyHolder.CORNER_TYPE);
        if (type.isHorizontal())
        {
            return state.setValue(PropertyHolder.CORNER_TYPE, type.rotate(rot));
        }

        return rotate(state, rot);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot)
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        return state.setValue(FramedProperties.FACING_HOR, rot.rotate(dir));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        CornerType type = state.getValue(PropertyHolder.CORNER_TYPE);
        if (type.isHorizontal())
        {
            BlockState newState = Utils.mirrorFaceBlock(state, mirror);
            if (newState != state)
            {
                return newState.setValue(PropertyHolder.CORNER_TYPE, type.horizontalOpposite());
            }
            return state;
        }
        else
        {
            return Utils.mirrorCornerBlock(state, mirror);
        }
    }

    @Override
    protected Tuple<BlockState, BlockState> getBlockPair(BlockState state)
    {
        Direction facing = state.getValue(FramedProperties.FACING_HOR);
        CornerType type = state.getValue(PropertyHolder.CORNER_TYPE);

        return new Tuple<>(
                FBContent.blockFramedInnerCornerSlope.get().defaultBlockState()
                        .setValue(PropertyHolder.CORNER_TYPE, type)
                        .setValue(FramedProperties.FACING_HOR, facing),
                FBContent.blockFramedCornerSlope.get().defaultBlockState()
                        .setValue(PropertyHolder.CORNER_TYPE, type.verticalOpposite())
                        .setValue(FramedProperties.FACING_HOR, facing.getOpposite())
        );
    }

    @Override
    public final BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FramedDoubleCornerBlockEntity(pos, state);
    }
}