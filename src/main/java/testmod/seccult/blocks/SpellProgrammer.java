package testmod.seccult.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.item.ItemBlockVariants;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.WaNB;
import testmod.seccult.util.WaNP;

public class SpellProgrammer extends Block implements WaNB, WaNP{

	private int x;
	private int y;
	private int z;
	public SpellProgrammer(String name) {
		super(Material.WOOD);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabsLoader.tab);
		setSoundType(SoundType.WOOD);
		setHardness(2.0F);
		setResistance(10.0F);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(2.0F);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		
        int id = GuiElementLoader.GUI_DEMO;
        playerIn.openGui(Seccult.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public String getSpecialName(ItemStack stack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerModels() {
		// TODO Auto-generated method stub
		
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getZ()
	{
		return this.z;
	}
}
