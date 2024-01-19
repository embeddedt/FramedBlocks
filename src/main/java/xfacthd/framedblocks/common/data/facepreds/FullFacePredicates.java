package xfacthd.framedblocks.common.data.facepreds;

import xfacthd.framedblocks.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.facepreds.door.*;
import xfacthd.framedblocks.common.data.facepreds.misc.*;
import xfacthd.framedblocks.common.data.facepreds.prism.*;
import xfacthd.framedblocks.common.data.facepreds.slab.*;
import xfacthd.framedblocks.common.data.facepreds.slope.*;
import xfacthd.framedblocks.common.data.facepreds.slopepanel.*;
import xfacthd.framedblocks.common.data.facepreds.slopepanelcorner.*;
import xfacthd.framedblocks.common.data.facepreds.slopeslab.*;
import xfacthd.framedblocks.common.data.facepreds.stairs.*;
import xfacthd.framedblocks.common.util.BlockTypeMap;

public final class FullFacePredicates extends BlockTypeMap<FullFacePredicate>
{
    public static final FullFacePredicates PREDICATES = new FullFacePredicates();

    private FullFacePredicates()
    {
        super(FullFacePredicate.FALSE);
    }

    @Override
    protected void fill()
    {
        put(BlockType.FRAMED_CUBE, FullFacePredicate.TRUE);
        put(BlockType.FRAMED_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_DOUBLE_SLOPE, new DoubleSlopeFullFacePredicate());
        put(BlockType.FRAMED_HALF_SLOPE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_HALF_SLOPE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_DIVIDED_SLOPE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_HALF_SLOPE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_DOUBLE_HALF_SLOPE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CORNER_SLOPE, new CornerFullFacePredicate());
        put(BlockType.FRAMED_INNER_CORNER_SLOPE, new InnerCornerFullFacePredicate());
        put(BlockType.FRAMED_DOUBLE_CORNER, new DoubleCornerFullFacePredicate());
        put(BlockType.FRAMED_PRISM_CORNER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_INNER_PRISM_CORNER, InnerThreewayCornerFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_DOUBLE_PRISM_CORNER, DoubleThreewayCornerFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_THREEWAY_CORNER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_INNER_THREEWAY_CORNER, InnerThreewayCornerFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_DOUBLE_THREEWAY_CORNER, DoubleThreewayCornerFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_SLAB, FullFacePredicate.TOP);
        put(BlockType.FRAMED_DOUBLE_SLAB, FullFacePredicate.Y_AXIS);
        put(BlockType.FRAMED_DIVIDED_SLAB, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SLAB_EDGE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SLAB_CORNER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_PANEL, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_DOUBLE_PANEL, new DoublePanelFullFacePredicate());
        put(BlockType.FRAMED_DIVIDED_PANEL_HORIZONTAL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_DIVIDED_PANEL_VERTICAL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CORNER_PILLAR, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_STAIRS, new StairsFullFacePredicate());
        put(BlockType.FRAMED_DOUBLE_STAIRS, new DoubleStairsFullFacePredicate());
        put(BlockType.FRAMED_HALF_STAIRS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_DIVIDED_STAIRS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_HALF_STAIRS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SLICED_STAIRS_SLAB, FullFacePredicate.TOP);
        put(BlockType.FRAMED_SLICED_STAIRS_PANEL, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_SLOPED_STAIRS, new SlopedStairsFullFacePredicate());
        put(BlockType.FRAMED_VERTICAL_STAIRS, new VerticalStairsFullFacePredicate());
        put(BlockType.FRAMED_VERTICAL_DOUBLE_STAIRS, new VerticalDoubleStairsFullFacePredicate());
        put(BlockType.FRAMED_VERTICAL_HALF_STAIRS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_DIVIDED_STAIRS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_DOUBLE_HALF_STAIRS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_SLICED_STAIRS, new VerticalSlicedStairsFullFacePredicate());
        put(BlockType.FRAMED_VERTICAL_SLOPED_STAIRS, new VerticalSlopedStairsFullFacePredicate());
        put(BlockType.FRAMED_THREEWAY_CORNER_PILLAR, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_THREEWAY_CORNER_PILLAR, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WALL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FENCE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FENCE_GATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_DOOR, DoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_DOOR, DoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_TRAPDOOR, TrapdoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_TRAPDOOR, TrapdoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_STONE_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_STONE_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_OBSIDIAN_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_OBSIDIAN_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_GOLD_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_GOLD_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_IRON_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_IRON_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_LADDER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_BUTTON, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_STONE_BUTTON, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_LARGE_BUTTON, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_LARGE_STONE_BUTTON, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_LEVER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SIGN, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WALL_SIGN, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_HANGING_SIGN, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WALL_HANGING_SIGN, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_TORCH, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WALL_TORCH, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SOUL_TORCH, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SOUL_WALL_TORCH, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_REDSTONE_TORCH, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_REDSTONE_WALL_TORCH, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FLOOR_BOARD, FullFacePredicate.TOP);
        put(BlockType.FRAMED_WALL_BOARD, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_LATTICE_BLOCK, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_THICK_LATTICE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CHEST, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SECRET_STORAGE, FullFacePredicate.TRUE);
        put(BlockType.FRAMED_BARS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_PANE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_HORIZONTAL_PANE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_RAIL_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_POWERED_RAIL_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_DETECTOR_RAIL_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_ACTIVATOR_RAIL_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FANCY_RAIL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FANCY_POWERED_RAIL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FANCY_DETECTOR_RAIL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FANCY_ACTIVATOR_RAIL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FANCY_RAIL_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FANCY_POWERED_RAIL_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FANCY_DETECTOR_RAIL_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FANCY_ACTIVATOR_RAIL_SLOPE, SlopeFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FLOWER_POT, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_PILLAR, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_HALF_PILLAR, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_POST, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_COLLAPSIBLE_BLOCK, new CollapsibleBlockFullFacePredicate());
        put(BlockType.FRAMED_BOUNCY_CUBE, FullFacePredicate.TRUE);
        put(BlockType.FRAMED_REDSTONE_BLOCK, FullFacePredicate.TRUE);
        put(BlockType.FRAMED_PRISM, new PrismFullFacePredicate());
        put(BlockType.FRAMED_INNER_PRISM, new InnerPrismFullFacePredicate());
        put(BlockType.FRAMED_DOUBLE_PRISM, new DoublePrismFullFacePredicate());
        put(BlockType.FRAMED_SLOPED_PRISM, new SlopedPrismFullFacePredicate());
        put(BlockType.FRAMED_INNER_SLOPED_PRISM, new InnerSlopedPrismFullFacePredicate());
        put(BlockType.FRAMED_DOUBLE_SLOPED_PRISM, new DoubleSlopedPrismFullFacePredicate());
        put(BlockType.FRAMED_SLOPE_SLAB, SlopeSlabFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_ELEVATED_SLOPE_SLAB, new ElevatedSlopeSlabFullFacePredicate());
        put(BlockType.FRAMED_DOUBLE_SLOPE_SLAB, new DoubleSlopeSlabFullFacePredicate());
        put(BlockType.FRAMED_INV_DOUBLE_SLOPE_SLAB, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB, new ElevatedDoubleSlopeSlabFullFacePredicate());
        put(BlockType.FRAMED_STACKED_SLOPE_SLAB, FullFacePredicate.TOP);
        put(BlockType.FRAMED_FLAT_SLOPE_SLAB_CORNER, SlopeSlabFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER, SlopeSlabFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FLAT_ELEV_SLOPE_SLAB_CORNER, new FlatElevatedSlopeSlabCornerFullFacePredicate());
        put(BlockType.FRAMED_FLAT_ELEV_INNER_SLOPE_SLAB_CORNER, new FlatElevatedInnerSlopeSlabCornerFullFacePredicate());
        put(BlockType.FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER, new FlatDoubleSlopeSlabCornerFullFacePredicate());
        put(BlockType.FRAMED_FLAT_INV_DOUBLE_SLOPE_SLAB_CORNER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FLAT_ELEV_DOUBLE_SLOPE_SLAB_CORNER, FullFacePredicate.Y_AXIS);
        put(BlockType.FRAMED_FLAT_ELEV_INNER_DOUBLE_SLOPE_SLAB_CORNER, new FlatElevatedInnerDoubleSlopeSlabCornerFullFacePredicate());
        put(BlockType.FRAMED_FLAT_STACKED_SLOPE_SLAB_CORNER, FullFacePredicate.TOP);
        put(BlockType.FRAMED_FLAT_STACKED_INNER_SLOPE_SLAB_CORNER, FullFacePredicate.TOP);
        put(BlockType.FRAMED_SLOPE_PANEL, SlopePanelFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_EXTENDED_SLOPE_PANEL, new ExtendedSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_DOUBLE_SLOPE_PANEL, new DoubleSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_INV_DOUBLE_SLOPE_PANEL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_EXTENDED_DOUBLE_SLOPE_PANEL, new ExtendedDoubleSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_STACKED_SLOPE_PANEL, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_FLAT_SLOPE_PANEL_CORNER, SlopePanelFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER, SlopePanelFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_FLAT_EXT_SLOPE_PANEL_CORNER, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_FLAT_EXT_INNER_SLOPE_PANEL_CORNER, new FlatExtendedInnerSlopePanelCornerFullFacePredicate());
        put(BlockType.FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER, new FlatDoubleSlopePanelCornerFullFacePredicate());
        put(BlockType.FRAMED_FLAT_INV_DOUBLE_SLOPE_PANEL_CORNER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FLAT_EXT_DOUBLE_SLOPE_PANEL_CORNER, FullFacePredicate.HOR_DIR_AXIS);
        put(BlockType.FRAMED_FLAT_EXT_INNER_DOUBLE_SLOPE_PANEL_CORNER, new FlatExtendedDoubleSlopePanelCornerFullFacePredicate());
        put(BlockType.FRAMED_FLAT_STACKED_SLOPE_PANEL_CORNER, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_FLAT_STACKED_INNER_SLOPE_PANEL_CORNER, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_SMALL_CORNER_SLOPE_PANEL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SMALL_CORNER_SLOPE_PANEL_W, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_LARGE_CORNER_SLOPE_PANEL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_LARGE_CORNER_SLOPE_PANEL_W, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL_W, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL, new LargeInnerCornerSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL_W, new LargeInnerCornerSlopePanelWallFullFacePredicate());
        put(BlockType.FRAMED_EXT_CORNER_SLOPE_PANEL, FullFacePredicate.TOP);
        put(BlockType.FRAMED_EXT_CORNER_SLOPE_PANEL_W, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_EXT_INNER_CORNER_SLOPE_PANEL, new ExtendedInnerCornerSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_EXT_INNER_CORNER_SLOPE_PANEL_W, new ExtendedInnerCornerSlopePanelWallFullFacePredicate());
        put(BlockType.FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL_W, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL, new LargeDoubleCornerSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL_W, new LargeDoubleCornerSlopePanelWallFullFacePredicate());
        put(BlockType.FRAMED_INV_DOUBLE_CORNER_SLOPE_PANEL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_INV_DOUBLE_CORNER_SLOPE_PANEL_W, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_EXT_DOUBLE_CORNER_SLOPE_PANEL, new ExtendedDoubleCornerSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_EXT_DOUBLE_CORNER_SLOPE_PANEL_W, new ExtendedDoubleCornerSlopePanelWallFullFacePredicate());
        put(BlockType.FRAMED_EXT_INNER_DOUBLE_CORNER_SLOPE_PANEL, new ExtendedInnerDoubleCornerSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_EXT_INNER_DOUBLE_CORNER_SLOPE_PANEL_W, new ExtendedInnerDoubleCornerSlopePanelWallFullFacePredicate());
        put(BlockType.FRAMED_STACKED_CORNER_SLOPE_PANEL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_STACKED_CORNER_SLOPE_PANEL_W, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_STACKED_INNER_CORNER_SLOPE_PANEL, new StackedInnerCornerSlopePanelFullFacePredicate());
        put(BlockType.FRAMED_STACKED_INNER_CORNER_SLOPE_PANEL_W, new StackedInnerCornerSlopePanelWallFullFacePredicate());
        put(BlockType.FRAMED_GLOWING_CUBE, FullFacePredicate.TRUE);
        put(BlockType.FRAMED_PYRAMID, FullFacePredicate.DIR_OPPOSITE);
        put(BlockType.FRAMED_PYRAMID_SLAB, FullFacePredicate.DIR_OPPOSITE);
        put(BlockType.FRAMED_TARGET, FullFacePredicate.TRUE);
        put(BlockType.FRAMED_GATE, DoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_GATE, DoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_ITEM_FRAME, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_GLOWING_ITEM_FRAME, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_MINI_CUBE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_ONE_WAY_WINDOW, new OneWayWindowFullFacePredicate());
        put(BlockType.FRAMED_BOOKSHELF, FullFacePredicate.Y_AXIS);
        put(BlockType.FRAMED_CHISELED_BOOKSHELF, FullFacePredicate.NOT_HOR_DIR);
        put(BlockType.FRAMED_CENTERED_SLAB, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CENTERED_PANEL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_MASONRY_CORNER_SEGMENT, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_MASONRY_CORNER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_CUBE_SEGMENT, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_CUBE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_SLAB_SEGMENT, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_SLAB, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_PANEL_SEGMENT, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_PANEL, FullFacePredicate.FALSE);
    }
}
