package testmod.seccult.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.common.IPlantable;
import testmod.seccult.blocks.BlockPlant;
import testmod.seccult.blocks.BlockPortal;
import testmod.seccult.blocks.Leaf;
import testmod.seccult.blocks.Log;
import testmod.seccult.blocks.Ores;
import testmod.seccult.blocks.Planks;
import testmod.seccult.blocks.Sapling;
import testmod.seccult.blocks.SpellProgrammer;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ORE_OVERWORLD = new Ores("ore_overworld", "overworld");
	
	public static final Block LOGS = new Log("log");
	public static final Block PLANKS = new Planks("planks");
	public static final Block LEAVES = new Leaf("leaves");
	public static final IPlantable SAPLINGS = new Sapling("sapling");
	public static final Block SPELLPROGRAMMER = new SpellProgrammer("spellprogrammer");
	public static final BlockPortal PORTAL = new BlockPortal();
	
	//Flower
	public static final BlockPlant LITTLE_FLOWER = new BlockPlant("little_flower");
	public static final BlockPlant CATCH_THE_SOUL = new BlockPlant("catch_the_soul");
}
