package testmod.seccult.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import testmod.seccult.blocks.Leaf;
import testmod.seccult.blocks.Log;
import testmod.seccult.blocks.Ores;
import testmod.seccult.blocks.Planks;
import testmod.seccult.blocks.Sapling;
import testmod.seccult.blocks.SpellProgrammer;
import testmod.seccult.items.TRprojectile.BlockNeow;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ORE_OVERWORLD = new Ores("ore_overworld", "overworld");
	
	public static final Block LOGS = new Log("log");
	public static final Block PLANKS = new Planks("planks");
	public static final Block LEAVES = new Leaf("leaves");
	public static final Block SAPLINGS = new Sapling("sapling");
	public static final Block SPELLPROGRAMMER = new SpellProgrammer("spellprogrammer");
}
