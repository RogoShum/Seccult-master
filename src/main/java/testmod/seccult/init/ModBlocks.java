package testmod.seccult.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.*;
import testmod.seccult.blocks.tileEntity.tileEnchantingStaff;
import testmod.seccult.blocks.tileEntity.tileGenerator;
import testmod.seccult.blocks.tileEntity.tileKillerQueen;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ORE_OVERWORLD = new Ores("ore_overworld", "overworld");
	
	public static final Block LOGS = new Log("log");
	public static final Block PLANKS = new Planks("planks");
	public static final Block LEAVES = new Leaf("leaves");
	public static final Block SAPLINGS = new Sapling("sapling");
	public static final Block SPELLPROGRAMMER = new SpellProgrammer("spellprogrammer");
	public static final BlockPortal PORTAL = new BlockPortal();
	public static final Block OreSpawn = new BlockOreSpawn("ore_spawn", Material.WOOD);
	public static final Block Unkonw = new BlockUnkonw("un_know", Material.CLAY);
	
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
	
	//tile
	public static final Block KILLER_QUEEN = new BlockKillerQueen("killer_queen");
	public static final Block Enchanting_Staff = new BlockEnchantingStaff("enchanting_staff");

	@SuppressWarnings("deprecation")
	public static void tile()
	{
		GameRegistry.registerTileEntity(tileKillerQueen.class, Seccult.MODID + ":killer_queen");
		GameRegistry.registerTileEntity(tileEnchantingStaff.class, Seccult.MODID + ":enchanting_staff");
		TileEntity.register(Seccult.MODID + "tileGenerator", tileGenerator.class);
	}
}
