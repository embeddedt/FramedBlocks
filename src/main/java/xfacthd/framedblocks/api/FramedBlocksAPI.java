package xfacthd.framedblocks.api;

import net.minecraft.core.Registry;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.blueprint.AuxBlueprintData;
import xfacthd.framedblocks.api.camo.CamoContainerFactory;
import xfacthd.framedblocks.api.util.Utils;

@SuppressWarnings("unused")
public interface FramedBlocksAPI
{
    FramedBlocksAPI INSTANCE = Utils.loadService(FramedBlocksAPI.class);



    /**
     * Returns the default {@link BlockState} used as a camo source when the block's camo state is set to air
     */
    BlockState getDefaultModelState();

    /**
     * Returns the {@link CreativeModeTab} that contains the FramedBlocks items
     */
    CreativeModeTab getDefaultCreativeTab();

    /**
     * Returns the registry of camo container factories
     */
    Registry<CamoContainerFactory<?>> getCamoContainerFactoryRegistry();

    /**
     * Returns the registry of auxiliary blueprint data types
     */
    Registry<AuxBlueprintData.Type<?>> getAuxBlueprintDataTypeRegistry();
}
