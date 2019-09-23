package testmod.seccult.blocks;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.blocks.tileEntity.tileKillerQueen;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemSoulStone;
import testmod.seccult.items.armor.MagickArmor;

public class BlockKillerQueen extends BlockContainer{

	public BlockKillerQueen(String name) {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setRegistryName(name);
		setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabsLoader.tab);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new tileKillerQueen();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(player.getHeldItem(hand).getItem() == ModItems.SoulStone)
		{
			if(ItemSoulStone.getSoul(player.getHeldItem(hand), worldIn) != null)
			{
				tileKillerQueen killer = (tileKillerQueen) worldIn.getTileEntity(pos);
				if(killer.putEntity(ItemSoulStone.getSoul(player.getHeldItem(hand), worldIn)))
					player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
				else return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
			}
			
		}
		
		return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		MagickArmor.addStringToTooltip("&c来自老板的热心关照", tooltip);
	}
	
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
