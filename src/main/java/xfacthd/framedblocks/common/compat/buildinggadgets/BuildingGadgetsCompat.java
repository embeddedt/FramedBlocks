package xfacthd.framedblocks.common.compat.buildinggadgets;

import com.direwolf20.buildinggadgets.common.tainted.building.tilesupport.*;
import com.direwolf20.buildinggadgets.common.tainted.registry.TopologicalRegistryBuilder;
import com.direwolf20.buildinggadgets.common.util.ref.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xfacthd.framedblocks.api.block.FramedBlockEntity;
import xfacthd.framedblocks.api.util.FramedConstants;

import java.util.function.Supplier;

public final class BuildingGadgetsCompat
{
    private static final DeferredRegister<ITileDataSerializer> SERIALIZERS = DeferredRegister.create(
            Reference.TileDataSerializerReference.REGISTRY_ID_TILE_DATA_SERIALIZER,
            FramedConstants.MOD_ID
    );
    static final RegistryObject<ITileDataSerializer> FRAMED_SERIALIZER = SERIALIZERS.register("framed_serializer", FramedBlockEntityDataSerializer::new);

    public static void init()
    {
        SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(BuildingGadgetsCompat::sendCompatImc);
    }

    private static void sendCompatImc(@SuppressWarnings("unused") final InterModEnqueueEvent event)
    {
        InterModComms.sendTo("buildinggadgets", "imc_tile_data_factory", BuildingGadgetsCompat::createDataFactory);
    }

    private static Supplier<TopologicalRegistryBuilder<ITileDataFactory>> createDataFactory()
    {
        return () ->
        {
            TopologicalRegistryBuilder<ITileDataFactory> factory = TopologicalRegistryBuilder.create();
            factory.addValue(
                    new ResourceLocation(FramedConstants.MOD_ID, "framed_block_data_factory"),
                    te -> te instanceof FramedBlockEntity ? new FramedBlockEntityData((FramedBlockEntity) te) : null
            );
            return factory;
        };
    }



    private BuildingGadgetsCompat() { }
}