package testmod.seccult.world.gen.structures;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import testmod.seccult.Seccult;

public class SeccultStructure extends WorldGenerator
{
	public static final int VARIATION = 2;
	
	private String structureName;
	
	public SeccultStructure(String name) {
		this.structureName = name;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		WorldServer worldServer = (WorldServer) world;
		MinecraftServer minecraftServer = world.getMinecraftServer();
		TemplateManager templateManager = worldServer.getStructureTemplateManager();
		Template template = templateManager.get(minecraftServer, 
				new ResourceLocation(Seccult.MODID, this.structureName));
		
		if(template == null) {
			System.out.println("Seccult:Template did not exsit. GoodBye.");
			return false;
		}

		if(canSpawnHere(template, worldServer, position))
		{
		
		Rotation rotation = Rotation.values()[rand.nextInt(3)];
		PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation).setIgnoreStructureBlock(false);
		
		template.addBlocksToWorld(world, position, settings);
		
		Map<BlockPos, String> dataBlocks = template.getDataBlocks(position, settings);
		
		for (java.util.Map.Entry<BlockPos, String> entry : dataBlocks.entrySet()) {
			try {
				String[] data = entry.getValue().split(" ");
				if (data.length < 2)
					continue;
				Block block = Block.getBlockFromName(data[0]);
				IBlockState state = null;
				if (data.length == 3)
					state = block.getStateFromMeta(Integer.parseInt(data[2]));
				else
					state = block.getDefaultState();
				for (Entry<IProperty<?>, Comparable<?>> entry2 : block.getDefaultState().getProperties().entrySet())
				{
					if (entry2.getKey().getValueClass().equals(EnumFacing.class)
							&& entry2.getKey().getName().equals("facing")) {
						state = state.withRotation(rotation.add(Rotation.CLOCKWISE_180));
						break;
					}
				}
				world.setBlockState(entry.getKey(), state, 3);
				TileEntity te = world.getTileEntity(entry.getKey());
				if(te == null)
					continue;
				if(te instanceof TileEntityLockableLoot)
					((TileEntityLockableLoot) te).setLootTable(new ResourceLocation(data[1]), rand.nextLong());
			}
			catch (Exception e)
			{
				
			}
		}
			return true;
		}
			return false;
	}
	
	public static boolean canSpawnHere(Template template, World world, BlockPos pos) {
		return isCornerValid(world, pos) && isCornerValid(world, pos.add(template.getSize().getX(), 0, 0))
				&& isCornerValid(world, pos.add(template.getSize().getX(), 0, template.getSize().getZ()))
				&& isCornerValid(world, pos.add(0, 0, template.getSize().getZ()));
	}
	
	public static boolean isCornerValid(World world, BlockPos pos) {
		int groundY = getGroundFromAbove(world, pos.getX(), pos.getZ());
		return groundY > pos.getY() - VARIATION && groundY < pos.getY() + VARIATION;
	}
	
	public static int getGroundFromAbove(World world, int x, int z)
	{
		int y = 255;
		boolean foundGround = false;
		while (!foundGround && y-- > 0) {
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			if(block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
				y = -1;
				break;
			}
			foundGround = block == Blocks.GRASS || block == Blocks.SAND || block == Blocks.SNOW
					|| block == Blocks.SNOW_LAYER || block == Blocks.MYCELIUM || block == Blocks.STONE;
		}
		return y;
		
	}
}