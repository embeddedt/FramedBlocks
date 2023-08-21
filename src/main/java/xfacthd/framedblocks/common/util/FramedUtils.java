package xfacthd.framedblocks.common.util;

import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.SlopeType;

import java.lang.invoke.MethodHandle;
import java.util.Map;
import java.util.function.Consumer;

public final class FramedUtils
{
    private static final MethodHandle MH_STATE_DEF_BUILDER_GET_PROPERTIES = Utils.unreflectField(
            StateDefinition.Builder.class, "f_61096_"
    );

    public static boolean isFramedRailSlope(BlockState state)
    {
        Block block = state.getBlock();
        if (block instanceof BaseRailBlock && block instanceof IFramedBlock)
        {
            return state.hasProperty(PropertyHolder.ASCENDING_RAIL_SHAPE);
        }
        return false;
    }

    public static boolean isRailItem(Item item)
    {
        return item == Items.RAIL ||
               item == Items.POWERED_RAIL ||
               item == Items.DETECTOR_RAIL ||
               item == Items.ACTIVATOR_RAIL ||
               item == FBContent.BLOCK_FRAMED_FANCY_RAIL.get().asItem() ||
               item == FBContent.BLOCK_FRAMED_FANCY_POWERED_RAIL.get().asItem() ||
               item == FBContent.BLOCK_FRAMED_FANCY_DETECTOR_RAIL.get().asItem() ||
               item == FBContent.BLOCK_FRAMED_FANCY_ACTIVATOR_RAIL.get().asItem();
    }

    public static Block getRailSlopeBlock(Item item)
    {
        if (item == Items.RAIL)
        {
            return FBContent.BLOCK_FRAMED_RAIL_SLOPE.get();
        }
        if (item == Items.POWERED_RAIL)
        {
            return FBContent.BLOCK_FRAMED_POWERED_RAIL_SLOPE.get();
        }
        if (item == Items.DETECTOR_RAIL)
        {
            return FBContent.BLOCK_FRAMED_DETECTOR_RAIL_SLOPE.get();
        }
        if (item == Items.ACTIVATOR_RAIL)
        {
            return FBContent.BLOCK_FRAMED_ACTIVATOR_RAIL_SLOPE.get();
        }
        if (item == FBContent.BLOCK_FRAMED_FANCY_RAIL.get().asItem())
        {
            return FBContent.BLOCK_FRAMED_FANCY_RAIL_SLOPE.get();
        }
        if (item == FBContent.BLOCK_FRAMED_FANCY_POWERED_RAIL.get().asItem())
        {
            return FBContent.BLOCK_FRAMED_FANCY_POWERED_RAIL_SLOPE.get();
        }
        if (item == FBContent.BLOCK_FRAMED_FANCY_DETECTOR_RAIL.get().asItem())
        {
            return FBContent.BLOCK_FRAMED_FANCY_DETECTOR_RAIL_SLOPE.get();
        }
        if (item == FBContent.BLOCK_FRAMED_FANCY_ACTIVATOR_RAIL.get().asItem())
        {
            return FBContent.BLOCK_FRAMED_FANCY_ACTIVATOR_RAIL_SLOPE.get();
        }
        throw new IllegalStateException("Invalid rail item: " + item);
    }

    public static Direction getSlopeBlockFacing(BlockState state)
    {
        if (isFramedRailSlope(state))
        {
            return getDirectionFromAscendingRailShape(state.getValue(PropertyHolder.ASCENDING_RAIL_SHAPE));
        }
        return state.getValue(FramedProperties.FACING_HOR);
    }

    public static SlopeType getSlopeType(BlockState state)
    {
        if (isFramedRailSlope(state))
        {
            return SlopeType.BOTTOM;
        }
        return state.getValue(PropertyHolder.SLOPE_TYPE);
    }

    public static RailShape getAscendingRailShapeFromDirection(Direction dir)
    {
        return switch (dir)
        {
            case NORTH -> RailShape.ASCENDING_NORTH;
            case EAST -> RailShape.ASCENDING_EAST;
            case SOUTH -> RailShape.ASCENDING_SOUTH;
            case WEST -> RailShape.ASCENDING_WEST;
            default -> throw new IllegalArgumentException("Invalid facing " + dir);
        };
    }

    public static Direction getDirectionFromAscendingRailShape(RailShape shape)
    {
        return switch (shape)
        {
            case ASCENDING_NORTH -> Direction.NORTH;
            case ASCENDING_EAST -> Direction.EAST;
            case ASCENDING_SOUTH -> Direction.SOUTH;
            case ASCENDING_WEST -> Direction.WEST;
            default -> throw new IllegalArgumentException("Invalid shape " + shape);
        };
    }

    public static void enqueueImmediateTask(LevelAccessor level, Runnable task, boolean allowClient)
    {
        if (level.isClientSide() && allowClient)
        {
            task.run();
        }
        else
        {
            enqueueTask(level, task, 0);
        }
    }

    public static void enqueueTask(LevelAccessor level, Runnable task, int delay)
    {
        if (!(level instanceof ServerLevel slevel))
        {
            throw new IllegalArgumentException("Utils#enqueueTask() called with a non-ServerWorld");
        }

        MinecraftServer server = slevel.getServer();
        server.tell(new TickTask(server.getTickCount() + delay, task));
    }

    public static void addPlayerInvSlots(Consumer<Slot> slotConsumer, Inventory playerInv, int x, int y)
    {
        for (int row = 0; row < 3; ++row)
        {
            for (int col = 0; col < 9; ++col)
            {
                slotConsumer.accept(new Slot(playerInv, col + row * 9 + 9, x + col * 18, y));
            }
            y += 18;
        }

        for (int col = 0; col < 9; ++col)
        {
            slotConsumer.accept(new Slot(playerInv, col, x + col * 18, y + 4));
        }
    }

    @SuppressWarnings("unchecked")
    public static void removeProperty(StateDefinition.Builder<Block, BlockState> builder, Property<?> property)
    {
        try
        {
            var properties = (Map<String, Property<?>>) MH_STATE_DEF_BUILDER_GET_PROPERTIES.invoke(builder);
            properties.remove(property.getName());
        }
        catch (Throwable e)
        {
            throw new RuntimeException("Failed to remove property from state builder", e);
        }
    }



    private FramedUtils() { }
}