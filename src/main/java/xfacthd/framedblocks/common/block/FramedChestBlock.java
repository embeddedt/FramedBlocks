package xfacthd.framedblocks.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import xfacthd.framedblocks.common.data.*;
import xfacthd.framedblocks.common.tileentity.FramedChestTileEntity;

import javax.annotation.Nullable;
import java.util.List;

public class FramedChestBlock extends FramedBlock
{
    public FramedChestBlock() { super(BlockType.FRAMED_CHEST); }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(PropertyHolder.FACING_HOR, PropertyHolder.CHEST_STATE, BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState state = getDefaultState().with(PropertyHolder.FACING_HOR, context.getPlacementHorizontalFacing().getOpposite());
        return withWater(state, context.getWorld(), context.getPos());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
    {
        ActionResultType result = super.onBlockActivated(state, world, pos, player, hand, hit);
        if (result != ActionResultType.PASS) { return result; }

        if (!world.isRemote())
        {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof FramedChestTileEntity)
            {
                FramedChestTileEntity fte = (FramedChestTileEntity) te;

                if (state.get(PropertyHolder.CHEST_STATE) != ChestState.OPENING)
                {
                    world.setBlockState(pos, state.with(PropertyHolder.CHEST_STATE, ChestState.OPENING));
                    world.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                }

                fte.open();
                NetworkHooks.openGui((ServerPlayerEntity) player, fte, pos);
            }
        }
        return ActionResultType.func_233537_a_(world.isRemote());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> drops = super.getDrops(state, builder);

        TileEntity te = builder.get(LootParameters.BLOCK_ENTITY);
        if (te instanceof FramedChestTileEntity)
        {
            ((FramedChestTileEntity) te).addDrops(drops);
        }

        return drops;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) { return new FramedChestTileEntity(); }
}