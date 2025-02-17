package xfacthd.framedblocks.common.data.camo.block;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.camo.TriggerRegistrar;
import xfacthd.framedblocks.api.camo.block.AbstractBlockCamoContainerFactory;
import xfacthd.framedblocks.api.util.*;

public final class BlockCamoContainerFactory extends AbstractBlockCamoContainerFactory<BlockCamoContainer>
{
    private static final Codec<BlockCamoContainer> CODEC = BlockState.CODEC.xmap(BlockCamoContainer::new, BlockCamoContainer::getState);
    public static final Component MSG_BLOCK_ENTITY = Utils.translate("msg", "camo.block_entity");
    public static final Component MSG_NON_SOLID = Utils.translate("msg", "camo.non_solid");

    @Override
    protected void writeToDisk(CompoundTag tag, BlockCamoContainer container)
    {
        tag.put("state", NbtUtils.writeBlockState(container.getState()));
    }

    @Override
    protected BlockCamoContainer readFromDisk(CompoundTag tag)
    {
        BlockState state = NbtUtils.readBlockState(Utils.getBlockHolderLookup(null), tag.getCompound("state"));
        return new BlockCamoContainer(state);
    }

    @Override
    protected void writeToNetwork(CompoundTag tag, BlockCamoContainer container)
    {
        tag.putInt("state", Block.getId(container.getState()));
    }

    @Override
    protected BlockCamoContainer readFromNetwork(CompoundTag tag)
    {
        BlockState state = Block.stateById(tag.getInt("state"));
        return new BlockCamoContainer(state);
    }

    @Override
    public boolean canTriviallyConvertToItemStack()
    {
        return true;
    }

    @Override
    public ItemStack dropCamo(BlockCamoContainer container)
    {
        return new ItemStack(container.getState().getBlock());
    }

    @Override
    protected BlockCamoContainer createContainer(BlockState camoState, Level level, BlockPos pos, Player player, ItemStack stack)
    {
        return new BlockCamoContainer(camoState);
    }

    @Override
    protected BlockCamoContainer copyContainerWithState(BlockCamoContainer original, BlockState newCamoState)
    {
        return new BlockCamoContainer(newCamoState);
    }

    @Override
    protected ItemStack createItemStack(Level level, BlockPos pos, Player player, ItemStack stack, BlockCamoContainer container)
    {
        return new ItemStack(container.getState().getBlock());
    }

    @Override
    protected boolean isValidBlock(BlockState camoState, BlockGetter level, BlockPos pos, @Nullable Player player)
    {
        Block block = camoState.getBlock();
        if (block instanceof IFramedBlock)
        {
            return false;
        }

        if (camoState.is(Utils.BLOCK_BLACKLIST))
        {
            displayValidationMessage(player, MSG_BLACKLISTED, CamoMessageVerbosity.DEFAULT);
            return false;
        }
        if (camoState.hasBlockEntity() && !ConfigView.Server.INSTANCE.allowBlockEntities() && !camoState.is(Utils.BE_WHITELIST))
        {
            displayValidationMessage(player, MSG_BLOCK_ENTITY, CamoMessageVerbosity.DEFAULT);
            return false;
        }
        if (!camoState.isSolidRender(level, pos) && !camoState.is(Utils.FRAMEABLE))
        {
            displayValidationMessage(player, MSG_NON_SOLID, CamoMessageVerbosity.DETAILED);
            return false;
        }
        return true;
    }

    @Override
    public Codec<BlockCamoContainer> codec()
    {
        return CODEC;
    }

    @Override
    public void registerTriggerItems(TriggerRegistrar registrar) { }
}
