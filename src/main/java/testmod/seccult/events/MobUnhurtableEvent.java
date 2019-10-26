package testmod.seccult.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.entity.EntityBloodBeam;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.entity.livings.EntityLight;
import testmod.seccult.entity.livings.EntityNotoriousBIG;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.items.ItemMagickable;
import testmod.seccult.items.armor.ChlorophyteArmor;
import testmod.seccult.items.armor.MagickArmor;
import testmod.seccult.items.armor.Chlorophyte.ChlorophyteHelmet;
import testmod.seccult.items.armor.ShadowSky.ShadowSkyChest;
import testmod.seccult.magick.ImplementationHandler;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.util.RogoDamage;

public class MobUnhurtableEvent {
    public static final List<String> Damage  = new ArrayList<>();
    public static final List<String> DamageII  = new ArrayList<>();
    
    public MobUnhurtableEvent() {
        Damage.add(DamageSource.IN_WALL.damageType);
        Damage.add(RogoDamage.VOID.damageType);
        DamageII.add(DamageSource.IN_WALL.damageType);
        DamageII.add(DamageSource.DROWN.damageType);
        DamageII.add(DamageSource.FALL.damageType);
        DamageII.add(DamageSource.CRAMMING.damageType);
	}

	@SubscribeEvent
	public void OnLightHurt(LivingAttackEvent event) {
		if(event.getEntityLiving() instanceof EntityLight && Damage.contains(event.getSource().damageType)) {
			EntityLivingBase light = (EntityLivingBase) event.getEntityLiving();
			double x,y,z;
			x = light.posX;
			y = light.posY;
			z = light.posZ;
			ParticleA(x, y, z, light);
			event.setCanceled(true);
		}
	}

	public void ATFX(World world, Vec3d QAQ)
	{
		double[] vec = {0, 0, 0};
		double[] pos = {QAQ.x, QAQ.y, QAQ.z};
		float[] color = {1F, 0.9F, 0.5F};
		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 1, 7));
		
	}
	
	@SubscribeEvent
	public void OnHurt(LivingAttackEvent event) {
		if(event.getEntityLiving() instanceof EntityLight) {
			EntityLight light = (EntityLight) event.getEntityLiving();
			Entity attacker = event.getSource().getTrueSource();
			int shield = light.getShield();
			
			if(event.getSource().getImmediateSource() != attacker) {
				event.setCanceled(true);
				return;
			}
			
			if(!light.world.isRemote && shield > 0 && attacker != null){
				Vec3d QAQ = onLook(attacker.getLookVec(), attacker.getPositionVector(), light.getPositionVector(), light);
				ATFX(light.world, QAQ);
				light.ShieldDecrease();
				event.setCanceled(true);
			}else
				if(!light.world.isRemote && shield > 0){
					ATFX(light.world, light.getPositionVector());
					light.ShieldDecrease();
					event.setCanceled(true);
				}
		}
		
		if(event.getEntityLiving() instanceof EntityBase) {
			EntityBase entity = (EntityBase) event.getEntityLiving();
			if(entity.isTRboss && DamageII.contains(event.getSource().getDamageType())) {
				event.setCanceled(true);
			}
		}
		
		if(event.getSource().getTrueSource() instanceof EntityPlayer) {
			Entity beam = event.getEntity();
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if(player.getHeldItemMainhand().getItem() == ModItems.Vampire_Knives) {
				EntityBloodBeam blood = new EntityBloodBeam(beam.world, player, event.getAmount() * 0.75F);
				blood.setPosition(beam.posX, beam.posY, beam.posZ);
				if(!beam.world.isRemote)
				beam.world.spawnEntity(blood);
			}
		}
		
		if(event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if(player.getEntityData().hasKey("TimeStop") && player.getEntityData().getBoolean("TimeStop")) {
				event.setCanceled(true);
			}
		}
		
		if(event.getEntityLiving() instanceof EntityNotoriousBIG && DamageII.contains(event.getSource().getDamageType())) {
			event.setCanceled(true);
		}
		
		EntityLivingBase e = event.getEntityLiving();
		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;
			PlayerData data = PlayerDataHandler.get(player);
			
			if(data.isMutekiGamer()) {
				event.setCanceled(true);
				player.hurtResistantTime = 20000;
				player.isDead = false;
				player.deathTime = 0;
				player.setHealth(player.getMaxHealth());
			}
		}
	}
	
	@SubscribeEvent
	public void YesYourHighnessUpdate(LivingUpdateEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;
			PlayerData data = PlayerDataHandler.get(player);
			
			if(data.isMutekiGamer()) {
				player.hurtResistantTime = 20000;
				player.isDead = false;
				player.deathTime = 0;
				player.setHealth(player.getMaxHealth());
			}
		}
	}
	
	@SubscribeEvent
	public void YesYourHighness(LivingDeathEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;
			PlayerData data = PlayerDataHandler.get(player);
			
			if(data.isMutekiGamer()) {
				player.hurtResistantTime = 20000;
		        player.isDead = false;
				player.deathTime = 0;
		        player.setHealth(player.getMaxHealth());
		        event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void dropScroll(LivingDeathEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		if(!e.isNonBoss() && !(e instanceof EntityBase)) {
			if(!e.world.isRemote)
			e.dropItem(ModItems.AlterScroll, 1);
		}
		
		if(e instanceof EntityWitch && event.getSource().isMagicDamage() && Seccult.rand.nextInt(5) == 0)
		{
			int i = Seccult.rand.nextInt(50);
			if(i >= 35 && i < 40)
				e.dropItem(ModItems.DefenceStaff, 1);
			if(i >= 40)
				e.dropItem(ModItems.AlterationStaff, 1);
			else
				e.dropItem(ModItems.DamageStaff, 1);
		}
	}
	
	@SubscribeEvent
	public void dropScroll(LivingHurtEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		Entity Tsource = event.getSource().getTrueSource();
		Entity Isource = event.getSource().getImmediateSource();
		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;
			if(Tsource instanceof EntityCaveSpider && Seccult.rand.nextInt(20) == 0) {
				ItemStack s = new ItemStack(ModItems.KnowledgeScroll);
				ItemMagickable.storeMagickString(s, ModMagicks.PosionMagick);
				if(!player.world.isRemote)
				player.dropItem(s, true);
			}
			
			if(Tsource instanceof EntityLight && Seccult.rand.nextInt(20) == 0) {
				ItemStack s = new ItemStack(ModItems.KnowledgeScroll);
				ItemMagickable.storeMagickString(s,ImplementationHandler.FocuseI);
				if(!player.world.isRemote)
				player.dropItem(s, true);
			}
			
			if(Isource instanceof EntityFireball && Seccult.rand.nextInt(20) == 0) {
				ItemStack s = new ItemStack(ModItems.KnowledgeScroll);
				ItemMagickable.storeMagickString(s, ModMagicks.FlameMagick);
				if(!player.world.isRemote)
				player.dropItem(s, true);
			}
			
			if(Isource instanceof EntityArrow && Seccult.rand.nextInt(70) == 0) {
				ItemStack s = new ItemStack(ModItems.KnowledgeScroll);
				ItemMagickable.storeMagickString(s, ModMagicks.ArrowMagick);
				if(!player.world.isRemote)
				player.dropItem(s, true);
			}
			
			if((event.getSource().getDamageType() == "lightningBolt" || event.getSource().getDamageType() == "seccult-frozen" ) && Seccult.rand.nextInt(7) == 0) {
				ItemStack s = new ItemStack(ModItems.KnowledgeScroll);
				ItemMagickable.storeMagickString(s,ModMagicks.ElectroMagick);
				if(!player.world.isRemote)
				player.dropItem(s, true);
			}
		}
		
	}
	
	@SubscribeEvent
	public static void jumpCore(LivingJumpEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		if(MagickArmor.hasJumpCore(player))
		{
			player.motionY += 0.2F;
			StateManager.setPlayerMove(player, player.motionX, player.motionY += 0.2F, player.motionZ, 1);
		}
		
	}
	
	@SubscribeEvent
	public static void attackCore(LivingAttackEvent event)
	{
		if(!(event.getSource().getTrueSource() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
		if(MagickArmor.hasAttackCore(player))
		{
			if(event.getEntityLiving() != null)
				event.getEntityLiving().hurtTime = -1;
		}
	}
	
	@SubscribeEvent
	public static void attackCore(LivingHurtEvent event)
	{
		if(!(event.getSource().getTrueSource() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
		if(MagickArmor.hasAttackCore(player))
		{
			if(event.getEntityLiving() != null)
				event.getEntityLiving().hurtTime = -1;
		}
	}
	
	@SubscribeEvent
	public static void shadowDodge(LivingAttackEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		
		int chance = 7;
		
		if(ShadowSkyChest.hasShadowHelmet(player))
			chance--;
		
		if(ShadowSkyChest.hasArmorSetItem(player, 1, ModItems.SHADOW_SKY_CHEST))
			chance--;
		
		if(ShadowSkyChest.hasArmorSetItem(player, 2, ModItems.SHADOW_SKY_LEGGINGS))
			chance--;
		
		if(ShadowSkyChest.hasArmorSetItem(player, 3, ModItems.SHADOW_SKY_BOOTS))
			chance--;
		
		if(player.isSneaking())
			chance--;
		
		if(!player.world.isDaytime())
			chance--;
		
		 if(Seccult.rand.nextInt(chance) == 0)
			 event.setCanceled(true);
			 
		if(ShadowSkyChest.hasArmorSetItem(player) && event.getSource().damageType.equals(DamageSource.FALL.damageType))
			event.setCanceled(true);
		
		if(ShadowSkyChest.hasArmorSetItem(player) && event.getSource().damageType.equals(DamageSource.IN_WALL.damageType))
			event.setCanceled(true);
		
		if(ShadowSkyChest.hasArmorSetItem(player) && event.getSource().damageType.equals(DamageSource.FLY_INTO_WALL.damageType))
			event.setCanceled(true);
			
	}

	public void ParticleA(double x, double y, double z, EntityLivingBase e) {
	    for (int i = 0; i < 8; ++i)
	    {
	    	double d0 = x + e.world.rand.nextInt(2);
	    	double d1 = y + e.world.rand.nextInt(2);
	    	double d2 = z + e.world.rand.nextInt(2);

	    	double[] vec = {0, 0, 0};
	    	double[] pos = {d0, d1, d2};
	    	float[] color = {1F, 0.9F, 0.5F};
	    	NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, Seccult.rand.nextFloat() * 0.2F + 0.7F, 2));
	    }
	}
	
	@SubscribeEvent
	public static void ChlorophyteHalo(LivingHurtEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer) || event.getSource().getTrueSource() == null)
		{
			if(event.getSource().getTrueSource() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
				if(ChlorophyteHelmet.hasArmorSetItem(player))
				{
					EntityBase base = new EntityBase(player.world);
					base.setPosition(player.posX, player.posY + player.height + 0.4F, player.posZ);
					base.faceEntity(event.getEntityLiving(), 360, 360);
					
					if(base.doPoisionMagick(10, 20))
						base.setDead();

				}
			}
			
			return;
		}
		
		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		
		Entity source = event.getSource().getTrueSource();
		
		if(ChlorophyteHelmet.hasArmorSetItem(player))
		{
			EntityBase base = new EntityBase(player.world);
			base.setPosition(player.posX, player.posY + player.height + 0.4F, player.posZ);
			base.faceEntity(source, 360, 360);
			
			if(base.doPoisionMagick(10, 20))
				base.setDead();
		}
	}
	
	@SubscribeEvent
	public static void ChlorophyteArmor(LivingHurtEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer) || !event.getSource().isFireDamage())
			return;
		
		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		
		if(ChlorophyteArmor.hasArmorSetItem(player, 0, ModItems.CHLOROPHYTE_HELMET))
			ChlorophyteArmor.damageArmorSetItem(player, 0, 2);
		
		if(ChlorophyteArmor.hasArmorSetItem(player, 1, ModItems.CHLOROPHYTE_CHEST))
			ChlorophyteArmor.damageArmorSetItem(player, 1, 2);
		
		if(ChlorophyteArmor.hasArmorSetItem(player, 2, ModItems.CHLOROPHYTE_LEGGINGS))
			ChlorophyteArmor.damageArmorSetItem(player, 2, 2);
		
		if(ChlorophyteArmor.hasArmorSetItem(player, 3, ModItems.CHLOROPHYTE_BOOTS))
			ChlorophyteArmor.damageArmorSetItem(player, 3, 2);
	}
	
	public Vec3d onLook(Vec3d look, Vec3d AP, Vec3d LP, Entity e) {
		AP.addVector(0, e.height / 2, 0);
		double distance = AP.distanceTo(LP);
		double dis = distance - 1;
		return AP.addVector(look.x * dis, look.y * dis + 1, look.z * dis);
	}
}
