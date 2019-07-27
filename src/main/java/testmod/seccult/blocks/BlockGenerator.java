package testmod.seccult.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.tileEntity.tileGenerator;

public class BlockGenerator extends BlockContainer{

	public BlockGenerator() {
		super(Material.ROCK);
		setRegistryName(Seccult.MODID, "Gen");
		setUnlocalizedName(Seccult.MODID + "Gen");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new tileGenerator();
	}
}
