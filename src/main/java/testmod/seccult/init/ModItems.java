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
import testmod.seccult.items.armor.Chlorophyte.*;
import testmod.seccult.items.armor.Magick.*;
import testmod.seccult.items.armor.Ocean.*;
import testmod.seccult.items.armor.ShadowSky.*;
import testmod.seccult.items.armor.SilkFeather.*;
import testmod.seccult.items.armor.Sorcerer.*;
import testmod.seccult.items.tools.*;
import testmod.seccult.items.tools.TRWeapon.*;

public class ModItems 
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Material
	public static final ToolMaterial MATERIAL_SPA = EnumHelper.addToolMaterial("material_spa", 32, 0, 9999.0F, 12446F, 200);
	public static final ArmorMaterial ARMOR_MATERIAL_SPA = EnumHelper.addArmorMaterial("armor_material_spa", "seccult:spa", 10,
			new int[] {2, 3, 4, 2}, 200, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	
	public static final ArmorMaterial ARMOR_MATERIAL_SILK = EnumHelper.addArmorMaterial("armor_material_silk", "seccult:silk", 5,
			new int[] {1, 1, 1, 1}, 200, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 50.0F);
	
	public static final ArmorMaterial ARMOR_MATERIAL_SORCERER = EnumHelper.addArmorMaterial("armor_material_sorcerer", "seccult:sorcerer", 15,
			new int[] {4, 5, 5, 3}, 200, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 10.0F);
	
	public static final ArmorMaterial ARMOR_MATERIAL_SHADOW = EnumHelper.addArmorMaterial("armor_material_shadow", "seccult:shadow", 30,
			new int[] {6, 8, 8, 5}, 200, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 8.0F);
	
	public static final ArmorMaterial ARMOR_MATERIAL_CHLOROPHYTE = EnumHelper.addArmorMaterial("armor_material_chlorophyte", "seccult:chlorophyte", 25,
			new int[] {2, 3, 4, 2}, 200, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 19.0F);
	
	public static final ArmorMaterial ARMOR_MATERIAL_OCEAN = EnumHelper.addArmorMaterial("armor_material_ocean", "seccult:ocean", 30,
			new int[] {5, 7, 7, 5}, 200, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 18.0F);
	 
	//Items
	public static final Item NOTEBOOK = new ItemNoteBook("notebook");
	
	public static final Item SPA = new ItemBase("spa");
	public static final Item ANTI_MAGICK = new ItemBase("antimagick_powder");
	
	public static final Item BLUE_FEATHER = new ItemBase("blue_feather");
	public static final Item YELLOW_FEATHER = new ItemBase("yellow_feather");
	public static final Item MAGENTA_FEATHER = new ItemBase("magenta_feather");
	public static final Item GECKO_TAIL = new ItemBase("gecko_tail");
	public static final Item GECKO_EYE = new ItemBase("gecko_eye");
	public static final Item WING = new ItemBase("wing");
	public static final Item MAGICK_POWER = new ItemBase("magick_powder");
	public static final Item B_PETAL = new ItemBase("blue_petal");
	public static final Item Y_PETAL = new ItemBase("yellow_petal");
	public static final Item M_PETAL = new ItemBase("magenta_petal");
	public static final Item REFLECTIVE_CRYSTAL = new ItemBase("refractive_crystal");
	public static final Item SPACE_COLLOID = new ItemBase("space_colloid");
	public static final Item MR_THINK = new ItemBase("mr_think");

	public static final Item HYBIRD_RICE = new ItemBase("hybird_rice");
	public static final Item HYBIRD_WHEAT = new ItemBase("hybird_wheat");
	public static final Item WINDY_DUST = new ItemBase("windy_dust");
	public static final Item DIM_KERNEL = new ItemBase("dim_kernel");
	public static final Item STRAW_HAT = new ItemBase("straw_hat");
	public static final Item SCARECROW_HEART = new ItemBase("scarecrow_heart");
	public static final Item MISS_BUEATY = new ItemBase("miss_bueaty");
	public static final Item CRUEL_SCROLL = new ItemBase("cruel_scroll");

	public static final Item WITCHERED_BRANCHES = new ItemBase("withered_branches");
	public static final Item RAVEN_FEATHER = new ItemBase("raven_feather");
	public static final Item RANCID_MARSH = new ItemBase("rancid_marsh");
	public static final Item MUDDY_WATER = new ItemBase("muddy_water");
	public static final Item WITCH_KEY = new ItemBase("witch_key");
	public static final Item BLACK_CAT_FUR = new ItemBase("black_fur");
	public static final Item DRAGON_BONE = new ItemBase("dragon_bone");
	public static final Item EYE = new ItemBase("eye");
	public static final Item ALL_METAL = new ItemBase("all_metal");
	
	public static final Item Dark_M = new ItemBase("dark_m");
	public static final Item Blood_M = new ItemBase("blood_m");
	public static final Item AirRes = new ItemBase("airres");
	public static final Item WaterRes = new ItemBase("water_gem");
	public static final Item Ocean_Enssence = new ItemBase("ocean_enssence");
	public static final Item PEARL = new ItemBase("pearl");
	public static final Item SILK = new ItemBase("silk");
	public static final Item Dark_Silk = new ItemBase("dark_silk");
	public static final Item S_PLANT = new ItemBase("s_plant");
	public static final Item SHADOW_RES = new ItemBase("shadow_res");
	public static final Item TimeStopper = new ItemTimeStopper("timestopper");
	public static final Item BlackVelvetHell = new ItemBlackVelvetHell("blackvelvethell");
	public static final Item YesYourHighness = new ItemYesYourHighness("yesyourhighness");
	public static final Item EVENT_THING = new ItemTerrariaEventThing("eow");
	public static final Item EVENT_THING_C = new ItemTerrariaEventThing_C("eoc");
	public static final Item PORTAL_SUMMONER = new ItemPortalSummoner("portal_summoner");
	//public static final Item SCP173 = new ItemSCP173("scp173");
	public static final Item Wand = new ItemWand("wand");
	public static final Item ELDER_WAND = new ItemWand("elder_wand");
	public static final Item GROWER = new ItemGrower("grower");
	public static final Item SoulStone = new ItemSoulStone("soul_stone");
	public static final Item DummySystem = new ItemDummySystem("dummy_system");
	public static final Item MagickCore = new ItemMagickCore("magick_core");
	public static final Item DamageStaff = new ItemStaff("damage_staff");
	public static final Item DefenceStaff = new ItemStaff("defence_staff");
	public static final Item AlterationStaff = new ItemStaff("alteration_staff");
	public static final Item KnowledgeScroll = new ItemKnowledgeScroll("knowledge_scroll");
	public static final Item AlterScroll = new ItemAlterScroll("alter_scroll");
	public static final Item ALLKnowScroll = new ItemALLKnowScroll("allknow_scroll");
	public static final Item RecordA = new ItemModRecord("a184", ModSounds.A184, "a184");
	public static final Item RecordQ = new ItemModRecord("qualia", ModSounds.qualia, "qualia");
	public static final Item RecordZ = new ItemModRecord("zero", ModSounds.zero, "zero");
	public static final Item RecordAF = new ItemModRecord("afterglow", ModSounds.afterglow, "afterglow");
	public static final Item RecordD = new ItemModRecord("dadada", ModSounds.dadada, "dadada");
	public static final Item RecordG = new ItemModRecord("gatorix", ModSounds.gatorix, "gatorix");
	public static final Item RecordI = new ItemModRecord("imprinting", ModSounds.imprinting, "imprinting");
	public static final Item RecordL = new ItemModRecord("light", ModSounds.light, "light_r");
	public static final Item RecordS = new ItemModRecord("slit", ModSounds.slit, "slit");
	public static final Item RecordSA = new ItemModRecord("saika", ModSounds.saika, "saika");
	public static final Item RecordST = new ItemModRecord("storia", ModSounds.storia, "storia");
	public static final Item RecordT = new ItemModRecord("twentyone", ModSounds.twentyone, "twentyone");
	public static final Item RecordSU = new ItemModRecord("summer", ModSounds.summer, "summer");
	public static final Item RecordGO = new ItemModRecord("np", ModSounds.np, "np");
	
	//Tools
	public static final Item SILK_SWORD = new SwordTool("silk_sword", -1, 130, 1.0F, 3, 60);
	public static final Item SPA_SWORD = new SwordTool("spa_sword", 1, 233, 0.5F, 7, 20);
	public static final Item SORCERER_SWORD = new SwordTool("sorcerer_sword", 2, 450, 0.8F, 10, 60);
	public static final Item CHLOROPHYTE_SWORD = new SwordTool("chlorophyte_sword", 3, 360, 1.2F, 14, 80);
	public static final Item OCEAN_SWORD = new SwordTool("ocean_sword", 3, 682, 1.4F, 18, 90);
	public static final Item SHADOW_SWORD = new SwordTool("shadow_sword", 5, 1444, 30F, 24, 120);
	public static final Item Thunder_SWORD = new ThunderSword("thunder_sword");
	public static final ItemPickaxe SPA_PICKAXE = new PickaxeTool("spa_pickaxe", MATERIAL_SPA);
	
	//TRWeapon
	public static final Item Meowmere = new Meowmere("meowmere");
	public static final Item Vampire_Knives = new VampireKnives("vampire_knives");
	public static final Item Solar_Eruption = new SolarEruption("solar_eruption");
	public static final Item LastPrism = new LastPrism("last_prism");
	
	
	//TRprojectile
	public static final Item Beam = new ItemTerraBeam("light");
	//public static final Item StarFury_Beam = new ItemTerraBeam("starfury_beam");
	
	//Armor
	public static final Item SILK_FEATHER_HELMET = new SilkFeatherHelmet("silk_feather_helmet", ARMOR_MATERIAL_SILK, 1, EntityEquipmentSlot.HEAD);
	public static final Item SILK_FEATHER_CHEST = new SilkFeatherChest("silk_feather_chest", ARMOR_MATERIAL_SILK, 1, EntityEquipmentSlot.CHEST);
	public static final Item SILK_FEATHER_LEGGINGS = new SilkFeatherLegs("silk_feather_leggings", ARMOR_MATERIAL_SILK, 1, EntityEquipmentSlot.LEGS);
	public static final Item SILK_FEATHER_BOOTS = new SilkFeatherBoot("silk_feather_boots", ARMOR_MATERIAL_SILK, 1, EntityEquipmentSlot.FEET);
	
	public static final Item SPA_HELMET = new MagickHelmet("spa_helmet", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.HEAD);
	public static final Item SPA_CHEST = new MagickChest("spa_chest", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.CHEST);
	public static final Item SPA_LEGGINGS = new MagickLegs("spa_leggings", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.LEGS);
	public static final Item SPA_BOOTS = new MagickBoots("spa_boots", ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.FEET);
	
	public static final Item SORCERER_HELMET = new SorcererHelmet("sorcerer_helmet", ARMOR_MATERIAL_SORCERER, 1, EntityEquipmentSlot.HEAD);
	public static final Item SORCERER_CHEST = new SorcererChest("sorcerer_chest", ARMOR_MATERIAL_SORCERER, 1, EntityEquipmentSlot.CHEST);
	public static final Item SORCERER_LEGGINGS = new SorcererLegs("sorcerer_leggings", ARMOR_MATERIAL_SORCERER, 1, EntityEquipmentSlot.LEGS);
	public static final Item SORCERER_BOOTS = new SorcererBoot("sorcerer_boots", ARMOR_MATERIAL_SORCERER, 1, EntityEquipmentSlot.FEET);
	
	public static final Item CHLOROPHYTE_HELMET = new ChlorophyteHelmet("chlorophyte_helmet", ARMOR_MATERIAL_CHLOROPHYTE, 1, EntityEquipmentSlot.HEAD);
	public static final Item CHLOROPHYTE_CHEST = new ChlorophyteChest("chlorophyte_chest", ARMOR_MATERIAL_CHLOROPHYTE, 1, EntityEquipmentSlot.CHEST);
	public static final Item CHLOROPHYTE_LEGGINGS = new ChlorophyteLeggings("chlorophyte_leggings", ARMOR_MATERIAL_CHLOROPHYTE, 1, EntityEquipmentSlot.LEGS);
	public static final Item CHLOROPHYTE_BOOTS = new ChlorophyteBoot("chlorophyte_boots", ARMOR_MATERIAL_CHLOROPHYTE, 1, EntityEquipmentSlot.FEET);
	
	public static final Item OCEAN_HELMET = new OceanHelmet("ocean_helmet", ARMOR_MATERIAL_OCEAN, 1, EntityEquipmentSlot.HEAD);
	public static final Item OCEAN_CHEST = new OceanChest("ocean_chest", ARMOR_MATERIAL_OCEAN, 1, EntityEquipmentSlot.CHEST);
	public static final Item OCEAN_LEGGINGS = new OceanLegs("ocean_leggings", ARMOR_MATERIAL_OCEAN, 1, EntityEquipmentSlot.LEGS);
	public static final Item OCEAN_BOOTS = new OceanBoot("ocean_boots", ARMOR_MATERIAL_OCEAN, 1, EntityEquipmentSlot.FEET);
	
	public static final Item SHADOW_SKY_HELMET = new ShadowSkyHelmet("shadow_sky_helmet", ARMOR_MATERIAL_SHADOW, 1, EntityEquipmentSlot.HEAD);
	public static final Item SHADOW_SKY_CHEST = new ShadowSkyChest("shadow_sky_chest", ARMOR_MATERIAL_SHADOW, 1, EntityEquipmentSlot.CHEST);
	public static final Item SHADOW_SKY_LEGGINGS = new ShadowSkyLegs("shadow_sky_leggings", ARMOR_MATERIAL_SHADOW, 1, EntityEquipmentSlot.LEGS);
	public static final Item SHADOW_SKY_BOOTS = new ShadowSkyBoot("shadow_sky_boots", ARMOR_MATERIAL_SHADOW, 1, EntityEquipmentSlot.FEET);
	
	//Accessories
	public static final Item A_LAST_STRUGGLE = new ALastStruggle("last_struggle");
	public static final Item A_MADE_IN_HEAVEN = new AMadeInHeaven("made_in_heaven");
	public static final Item A_MAGICK_CASTER = new AMagicCaster("magick_caster");
	public static final Item RECOVERY_THING = new ARecoveryCore("item_recovery");
	//public static final Item VirtulBody = new AVirtulBody("virtul_body");
	public static final Item CANDY_RING = new ACandyRing("candy_ring");
	public static final Item Attack_REFRACT = new AAttackRefract("attack_refract");
	public static final Item EFFECT_REFRACT = new AEffectRefract("effect_refract");
	public static final Item FRIENDSHIP_RING = new AFriendshipRing("friendship_ring");
	public static final Item Medusa_Head = new AMedusaHead("medusa_head");
}
