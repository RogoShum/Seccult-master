package testmod.seccult.init;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import testmod.seccult.Seccult;
import testmod.seccult.entity.*;
import testmod.seccult.entity.livings.*;
import testmod.seccult.entity.livings.flying.EntityAirTentacle;
import testmod.seccult.entity.livings.flying.EntityBird;
import testmod.seccult.entity.livings.insect.*;
import testmod.seccult.entity.livings.water.*;
import testmod.seccult.entity.projectile.*;

public class ModEntity  
{
	public static void registerEntities()
	{
		int id = 0;
		int distance = 2000000;
		registerEntity("light", EntityLight.class, id++, distance, 1, true, 12654645, 000000);
		registerEntity("trueeaterofworlds", EntityEoWHead.class, id++, distance, 1, true, 52365, 000000);
		registerEntity("eyeofcthulhu", EntityEoC.class, id++, distance, 1, true, 888888, 000000);
		registerEntity("carne", EntityCarne.class, id++, distance, 1, true, 214124, 241200);
		registerEntity("changeling", EntityChangeling.class, id++, distance, 1, true, 213100, 1);
		registerEntity("butterfly", EntityButterfly.class, id++, distance, 1, true, 14461039, 16776960);
		registerEntity("dragonfly", EntityDragonfly.class, id++, distance, 1, true, 12654645, 000000);
		registerEntity("bird", EntityBird.class, id++, distance, 1, true, 14461039, 13223074);
		registerEntity("worm", EntityWorm.class, id++, distance, 1, true, 12654645, 12654645);
		registerEntity("fish", EntityFish.class, id++, distance, 1, true, 13223074, 14461039);
		registerEntity("jellyfish", EntityJellyfish.class, id++, distance, 1, true, 13421812, 9526516);
		registerEntity("airtentacle", EntityAirTentacle.class, id++, distance, 1, true, 0, 255);
		registerEntity("watertentacle", EntityWaterTentacle.class, id++, distance, 1, true, 0, 13010237);
		registerEntity("boneshark", EntityBoneShark.class, id++, distance, 1, true, 13223074, 0);
		registerEntity("rockshellleviathan", EntityRockShellLeviathan.class, id++, distance, 1, true, 13223074, 0);
		registerEntityWithoutEgg("scp173", EntitySCP173.class, id++, 128, 1, true);
		registerEntityWithoutEgg("laserbeam", EntityLaserBeamBase.class, id++, 128, 1, true);
		registerEntityWithoutEgg("advancelaserbeam", EntityAdvanceLaser.class, id++, 128, 1, true);
		registerEntityWithoutEgg("notoriousBIG", EntityNotoriousBIG.class, id++, 256, 1, true);
		registerEntityWithoutEgg("lmr", EntityLmr.class, id++, 128, 1, true);
		registerEntityWithoutEgg("eaterofworlds", EntityEoW.class, id++, distance, 1, true);
		registerEntityWithoutEgg("servantofcthulhu", EntitySoC.class, id++, 128, 1, true);
		registerEntityWithoutEgg("trmagick", TRMagickProjectile.class, id++, 128, 1, true);
		registerEntityWithoutEgg("trarrow", TRArrow.class, id++, 128, 1, true);
		registerEntityWithoutEgg("trbullet", TRBulletProjectile.class, id++, 128, 1, true);
		registerEntityWithoutEgg("trblade", TRBladeBeam.class, id++, 128, 1, true);
		registerEntityWithoutEgg("solar", EntitySolarEruption.class, id++, 128, 1, true);
		registerEntityWithoutEgg("bloodbeam", EntityBloodBeam.class, id++, 128, 1, true);
		registerEntityWithoutEgg("timemanager", EntityTimeManager.class, id++, 128, 1, true);
		registerEntityWithoutEgg("blackvelvethell", EntityBlackVelvetHell.class, id++, 128, 1, true);
		registerEntityWithoutEgg("dummyentity", EntityDummy.class, id++, distance, 1, true);
		registerEntityWithoutEgg("improjectile", EntityIMProjectile.class, id++, distance, 1, true);
		registerEntityWithoutEgg("imcircle", EntityIMCircle.class, id++, distance, 1, true);
		registerEntityWithoutEgg("ArrowClowCard", EntityClowCardArrow.class, id++, distance, 1, true);
		registerEntityWithoutEgg("lightingthing", EntityLightingThing.class, id++, distance, 1, true);
		registerEntityWithoutEgg("bubble", EntityMagickBubble.class, id++, distance, 1, true);
		registerEntityWithoutEgg("frozenfx", EntityFrozenFX.class, id++, distance, 1, true);
		registerEntityWithoutEgg("spirit", EntitySpirit.class, id++, distance, 1, true);
		registerEntityWithoutEgg("shield", EntityShieldFX.class, id++, distance, 1, true);
		registerEntityWithoutEgg("protection_shield", EntityProtectionShieldFX.class, id++, distance, 1, true);
		registerEntityWithoutEgg("spirit_container", EntitySpiritContainer.class, id++, distance, 1, true);
		registerEntityWithoutEgg("spirit_dummy", EntitySpiritDummy.class, id++, distance, 1, true);
		registerEntityWithoutEgg("king_crimson", EntityKingCrimson.class, id++, distance, 1, true);
	}
	
	public static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int updateFrequency, boolean svu, int color1, int color2) 
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Seccult.MODID + ":" + name), entity, name, id, Seccult.instance, range, updateFrequency, svu, color1, color2);
	}
	
	public static void registerEntityWithoutEgg(String name, Class<? extends Entity> entity, int id, int range, int updateFrequency, boolean svu) 
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Seccult.MODID + ":" + name), entity, name, id, Seccult.instance, range, updateFrequency, svu);
	}
}
