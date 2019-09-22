package testmod.seccult;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.CombatTracker;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public class ModReclection {
	
	public static void BiomeProvider_SetgenBiomes(BiomeProvider provider, GenLayer layer) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field genBiomes = BiomeProvider.class.getDeclaredField("genBiomes");
        genBiomes.setAccessible(true);
        genBiomes.set(provider, layer);
	}
	
	public static void BiomeProvider_SetbiomeIndexLayer(BiomeProvider provider, GenLayer biomeIndexLayer) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field biomeLayer = BiomeProvider.class.getDeclaredField("biomeIndexLayer");
		biomeLayer.setAccessible(true);
		biomeLayer.set(provider, biomeIndexLayer);
	}
	
	public static void Entity_inWater(Entity entity, boolean bool) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field inwater = Entity.class.getDeclaredField("inWater");
		inwater.setAccessible(true);
		inwater.set(entity, bool);
	}
	
	public static void PlayerData_MaxManaValue(PlayerData data, float vaule) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field MaxManaValue = PlayerData.class.getDeclaredField("MaxManaValue");
		MaxManaValue.setAccessible(true);
		MaxManaValue.set(data, vaule);
	}
	
	public static void PlayerData_ManaValue(PlayerData data, float vaule) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field ManaValue = PlayerData.class.getDeclaredField("ManaValue");
		ManaValue.setAccessible(true);
		ManaValue.set(data, vaule);
	}
	
	public static void EntityCombat(EntityPlayer player, EntityLivingBase living) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field combat = EntityLivingBase.class.getDeclaredField("_combatTracker");
		combat.setAccessible(true);
		CombatTracker tracker = new CombatTracker(null);
		tracker.reset();
		combat.set(living, tracker);
	}
}
