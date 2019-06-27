package testmod.seccult.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.EntityCarne;
import testmod.seccult.entity.livings.EntityChangeling;
import testmod.seccult.entity.livings.EntityEoC;
import testmod.seccult.entity.livings.EntityEoW;
import testmod.seccult.entity.livings.EntityEoWHead;
import testmod.seccult.entity.livings.EntityLight;
import testmod.seccult.entity.livings.EntityNotoriousBIG;
import testmod.seccult.entity.livings.EntitySoC;
import testmod.seccult.entity.projectile.*;

public class ModEntity  
{
	public static void registerEntities()
	{
		int id = 0;
		registerEntity("light", EntityLight.class, id++, 128, 1, true, 12654645, 000000);
		registerEntity("trueeaterofworlds", EntityEoWHead.class, id++, 256, 1, true, 52365, 000000);
		registerEntity("eyeofcthulhu", EntityEoC.class, id++, 256, 1, true, 888888, 000000);
		registerEntity("carne", EntityCarne.class, id++, 128, 1, true, 214124, 241200);
		registerEntity("changeling", EntityChangeling.class, id++, 128, 1, true, 213100, 1);
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
