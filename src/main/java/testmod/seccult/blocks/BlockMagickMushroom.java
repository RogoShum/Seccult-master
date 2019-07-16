package testmod.seccult.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;

public class BlockMagickMushroom extends BlockHugeMushroom implements registerModel{
	private final String name;
	public BlockMagickMushroom(String name, Material materialIn, MapColor color, Block smallBlockIn) {
		super(materialIn, color, smallBlockIn);
		setRegistryName(name);
		setUnlocalizedName(name);
		setLightLevel(0.5F);
		this.name = name;
		this.setTickRandomly(true);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setCreativeTab(CreativeTabsLoader.tab);
	}

	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), 0, this.name, "inventory");
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		
    	/*if(rand.nextInt(10) == 0)
    	{
    		Particle par = new ParticleEnchantment(worldIn, pos.getX(), pos.getY(), pos.getZ(), 0, -rand.nextFloat() / 3, 0);
    		Minecraft.getMinecraft().effectRenderer.addEffect(par);
    		double[] Bpos = {pos.getX(), pos.getY() + 0.5, pos.getZ()};
    		double[] vec = {0, 0, 0};
			float[] color = {0.2F, 0.7F, 0.7F};
    		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(Bpos, vec, color, 5, 0));
    	}*/
	}
}
