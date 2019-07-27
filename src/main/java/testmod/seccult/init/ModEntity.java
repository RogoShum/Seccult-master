package testmod.seccult.init;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import testmod.seccult.Seccult;
import testmod.seccult.entity.*;
import testmod.seccult.entity.livings.*;
import testmod.seccult.entity.livings.insect.*;
import testmod.seccult.entity.livings.water.*;
import testmod.seccult.entity.projectile.*;

public class ModEntity  
{
	public static void registerEntities()
	{
		int id = 0;
		int distance = Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16;
		if(distance < 128)
			distance = 128;
		registerEntity("light", EntityLight.class, id++, 128, 1, true, 12654645, 000000);
		registerEntity("trueeaterofworlds", EntityEoWHead.class, id++, distance, 1, true, 52365, 000000);
		registerEntity("eyeofcthulhu", EntityEoC.class, id++, distance, 1, true, 888888, 000000);
		registerEntity("carne", EntityCarne.class, id++, 128, 1, true, 214124, 241200);
		registerEntity("changeling", EntityChangeling.class, id++, 128, 1, true, 213100, 1);
		registerEntity("butterfly", EntityButterfly.class, id++, 128, 1, true, 12654645, 000000);
		registerEntity("dragonfly", EntityDragonfly.class, id++, 128, 1, true, 12654645, 000000);
		registerEntity("bird", EntityBird.class, id++, 128, 1, true, 12654645, 12654645);
		registerEntity("worm", EntityWorm.class, id++, 128, 1, true, 12654645, 12654645);
		registerEntity("fish", EntityFish.class, id++, 128, 1, true, 12654645, 12654645);
		registerEntity("jellyfish", EntityJellyfish.class, id++, 128, 1, true, 12654645, 12654645);
		registerEntityWithoutEgg("scp173", EntitySCP173.class, id++, 128, 1, true);
		registerEntityWithoutEgg("laserbeam", EntityLaserBeamBase.class, id++, 128, 1, true);
		registerEntityWithoutEgg("notoriousBIG", EntityNotoriousBIG.class, id++, 256, 1, true);
		registerEntityWithoutEgg("lmr", EntityLmr.class, id++, 128, 1, true);
		registerEntityWithoutEgg("eaterofworlds", EntityEoW.class, id++, 256, 1, true);
		registerEntityWithoutEgg("servantofcthulhu", EntitySoC.class, id++, 128, 1, true);
		registerEntityWithoutEgg("trmagick", TRMagickProjectile.class, id++, 128, 1, true);
		registerEntityWithoutEgg("trarrow", TRArrow.class, id++, 128, 1, true);
		registerEntityWithoutEgg("trbullet", TRBulletProjectile.class, id++, 128, 1, true);
		registerEntityWithoutEgg("trblade", TRBladeBeam.class, id++, 128, 1, true);
		registerEntityWithoutEgg("solar", EntitySolarEruption.class, id++, 128, 1, true);
		registerEntityWithoutEgg("bloodbeam", EntityBloodBeam.class, id++, 128, 1, true);
		registerEntityWithoutEgg("timemanager", EntityTimeManager.class, id++, 128, 1, true);
		registerEntityWithoutEgg("blackvelvethell", EntityBlackVelvetHell.class, id++, 128, 1, true);
		registerEntityWithoutEgg("dummyentity", EntityDummy.class, id++, 128, 1, true);
		registerEntityWithoutEgg("improjectile", EntityIMProjectile.class, id++, 128, 1, true);
		registerEntityWithoutEgg("imcircle", EntityIMCircle.class, id++, 128, 1, true);
		registerEntityWithoutEgg("ArrowClowCard", EntityClowCardArrow.class, id++, 128, 1, true);
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
