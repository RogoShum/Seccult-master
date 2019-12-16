package testmod.seccult.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.blocks.tileEntity.tileEnchantingStaff;
import testmod.seccult.blocks.tileEntity.tileMagickCrafting;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemWand;

public class BlockMagickCrafting extends BlockTileEntity{
	protected static final AxisAlignedBB UNPRESSED_AABB = new AxisAlignedBB(1D, 0.0D, 1D, 1D, 0.2D, 1D);
	
	public BlockMagickCrafting(String name, Material material) {
		super(name, material);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new tileMagickCrafting();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		tileMagickCrafting staff = (tileMagickCrafting) worldIn.getTileEntity(pos);
		
		if(player.getHeldItem(hand).getItem() instanceof ItemWand && staff.tryCrafting())
		{
				return true;
		}
		
		if(player.getHeldItem(hand).getItem() == ModItems.SoulStone)
		{
			staff.setPower(20);
			return true;
		}
		
		if(player.getHeldItem(hand).getItem() != ModItems.SoulStone && !player.getHeldItem(hand).isEmpty())
		{
			staff.pushItemStack(player.getHeldItem(hand));
			return true;
		}

		return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
	
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return super.getBoundingBox(state, source, pos);
		//return UNPRESSED_AABB;
	}
    
    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
