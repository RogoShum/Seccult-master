package testmod.seccult.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;
import testmod.seccult.items.*;
import testmod.seccult.items.Accessories.*;
import testmod.seccult.items.TRprojectile.ItemTerraBeam;
import testmod.seccult.items.armor.*;
import testmod.seccult.items.armor.Magick.*;
import testmod.seccult.items.tools.*;
import testmod.seccult.items.tools.TRWeapon.*;
import testmod.seccult.magick.active.NoClipMagick;

public class ModItems 
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Material
	public static final ToolMaterial MATERIAL_SPA = EnumHelper.addToolMaterial("material_spa", 32, 0, 9999.0F, 12446F, 200);
	public static final ArmorMaterial ARMOR_MATERIAL_SPA = EnumHelper.addArmorMaterial("armor_material_spa", "seccult:spa", 5,
			new int[] {15, 30, 35, 20}, 200, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	
	//Items
	public static final Item SPA = new ItemBase("spa");
	public static final Item TimeStopper = new ItemTimeStopper("timestopper");
	public static final Item BlackVelvetHell = new ItemBlackVelvetHell("blackvelvethell");
	public static final Item YesYourHighness = new ItemYesYourHighness("yesyourhighness");
	public static final Item EVENT_THING = new ItemTerrariaEventThing("eow");
	public static final Item SCP173 = new ItemSCP173("scp173");
	public static final Item Wand = new ItemWand("wand");
	
	//Tools
	public static final Item SPA_SOWRD = new SwordTool("spa_sword");
	public static final ItemPickaxe SPA_PICKAXE = new PickaxeTool("spa_pickaxe", MATERIAL_SPA);
	
	//TRWeapon
	public static final Item Enchanted_Sword = new EnchantedSword("enchanted_sword");
	public static final Item Terraria_Blade = new TerrariaBlade("terraria_blade");
	public static final Item Muramasa = new Muramasa("muramasa");
	public static final Item Night_Edge = new NightEdge("night_edge");
	public static final Item StarFury = new StarFury("starfury");
	public static final Item Blade_Of_Grass = new BladeOfGrass("blade_of_grass");
	public static final Item Fiery_GreatSword = new FieryGreatSword("fiery_greatsword");
	public static final Item Light_Bane = new LightBane("light_bane");
	public static final Item Cobalt_Sword = new CobaltSword("cobalt_sword");
	public static final Item Palladium_Sword = new PalladiumSword("palladium_sword");
	public static final Item Mythril_Sword = new MythrilSword("mythril_sword");
	public static final Item Orichalcum_Sword = new OrichalcumSword("orichalcum_sword");
	public static final Item Chlorophyte_Saber = new ChlorophyteSaber("chlorophyte_saber");
	public static final Item Breaker_Blade = new BreakerBlade("breaker_blade");
	public static final Item Adamantite_Sword = new AdamantiteSword("adamantite_sword");
	public static final Item Titanium_Sword = new TitaniumSword("titanium_sword");
	public static final Item Excalibur = new Excalibur("excalibur");
	public static final Item True_Excalibur = new TrueExcalibur("true_excalibur");
	public static final Item Chlorophyte_Claymore = new ChlorophyteClaymore("chlorophyte_claymore");
	public static final Item The_Horseman_Blade = new TheHorsemanBlade("the_horseman_blade"); 
	public static final Item True_Night_Edge = new TrueNightEdge("true_night_edge");
	public static final Item Star_Wrath = new StarWrath("star_wrath");
	public static final Item Influx_Waver = new InfluxWaver("influx_waver");
	public static final Item Meowmere = new Meowmere("meowmere");
	public static final Item Beam_Sword = new BeamSword("beam_sword");
	public static final Item Vampire_Knives = new VampireKnives("vampire_knives");
	public static final Item Daybreak = new Daybreak("daybreak");
	public static final Item Solar_Eruption = new SolarEruption("solar_eruption");
	public static final Item Scourge_of_the_Corruptor = new ScourgeoftheCorruptor("scourge_of_the_corruptor");
	public static final Item LastPrism = new LastPrism("last_prism");
	
	
	//TRprojectile
	public static final Item Beam = new ItemTerraBeam("light");
	public static final Item StarFury_Beam = new ItemTerraBeam("starfury_beam");
	
	//Armor
	public static final Item SPA_HELMET = new MagickHelmet("spa_helmet", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.HEAD);
	public static final Item SPA_CHEST = new MagickChest("spa_chest", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.CHEST);
	public static final Item SPA_LEGGINGS = new MagickLegs("spa_leggings", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.LEGS);
	public static final Item SPA_BOOTS = new MagickBoots("spa_boots", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.FEET);
	
	public static final Item RECOVERY_HELMET = new RecoveryHelmet();
	public static final Item NOCLIP_CHEST = new NoClipChest();
	
	public static final Item OCEAN_HELMET = new OceanArmor("ocean_helmet", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.HEAD);
	public static final Item OCEAN_CHEST = new OceanArmor("ocean_chest", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.CHEST);
	public static final Item OCEAN_LEGGINGS = new OceanArmor("ocean_leggings", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.LEGS);
	public static final Item OCEAN_BOOTS = new OceanArmor("ocean_boots", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.FEET);
	
	//Accessories
	public static final Item A_LAST_STRUGGLE = new ALastStruggle("last_struggle");
	public static final Item A_MADE_IN_HEAVEN = new AMadeInHeaven("made_in_heaven");
	public static final Item RECOVERY_THING = new AMagicCaster("item_recovery");
	public static final Item VirtulBody = new AVirtulBody("virtul_body");
}
