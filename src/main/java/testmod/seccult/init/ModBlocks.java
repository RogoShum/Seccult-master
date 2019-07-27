package testmod.seccult.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.IPlantable;
import testmod.seccult.blocks.*;

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
	public static final Block OreSpawn = new BlockOreSpawn("ore_spawn", Material.WOOD);
	
	//Plant
	public static final BlockPlant FLOWER = new BlockPlant("flower");
	
	public static final Block LANTERN_FRUIT = new LanternFruit("latern_fruit");
	public static final Block LANTERN_VINE = new BlockLanternVine("lantern_vine");
	public static final Block HYPHA_LIGHT = new BlockLanternVine("hypha_light");
	public static final Block HYPHA_LIGHT_BLUE = new BlockLanternVine("hypha_light_blue");
	public static final BlockBush LittleMush = new BlockLittleMushroom("blue_mushroom");
	public static final BlockBush LittleRedMush = new BlockLittleMushroom("magenta_mushroom");
	public static final Block Mush = new BlockMagickMushroom("blue_magick_mushroom", Material.WOOD, MapColor.LIGHT_BLUE, LittleMush);
	public static final Block RedMush = new BlockMagickMushroom("magenta_magick_mushroom", Material.WOOD, MapColor.MAGENTA, LittleRedMush);
	public static final Block Hypha = new BlockHypha("hypha");
	
	//generator
	public static final Block Mush_Gen = new BlockGenerator();
}
