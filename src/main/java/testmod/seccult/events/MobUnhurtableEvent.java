package testmod.seccult.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.ATFX;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.entity.EntityBloodBeam;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.entity.livings.EntityLight;
import testmod.seccult.entity.livings.EntityNotoriousBIG;
import testmod.seccult.init.ModItems;
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
				Minecraft.getMinecraft().effectRenderer.addEffect(new ATFX(light.world, QAQ.x, QAQ.y, QAQ.z));
				light.ShieldDecrease();
				event.setCanceled(true);
			}else
				if(!light.world.isRemote && shield > 0){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ATFX(light.world, light.posX, light.posY, light.posZ));
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
	}
	
	@SubscribeEvent
	public void YesYourHighnessUpdate(LivingUpdateEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;
			if(player.getEntityData().hasKey("YesYourHighness") && player.getEntityData().getInteger("YesYourHighness") == 1) {
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
			if(player.getEntityData().hasKey("YesYourHighness") && player.getEntityData().getInteger("YesYourHighness") == 1) {
				player.hurtResistantTime = 20000;
		        player.isDead = false;
				player.deathTime = 0;
		        player.setHealth(player.getMaxHealth());
		        event.setCanceled(true);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void ParticleA(double x, double y, double z, EntityLivingBase e) {
	    for (int i = 0; i < 8; ++i)
	    {
		double d0 = x + e.world.rand.nextInt(2);
		double d1 = y + e.world.rand.nextInt(2);
		double d2 = z + e.world.rand.nextInt(2);
		Minecraft.getMinecraft().effectRenderer.addEffect(new LightFX(e.world, d0, d1, d2, 0, 0, 0));
	    }
	}
	
	public Vec3d onLook(Vec3d look, Vec3d AP, Vec3d LP, Entity e) {
		AP.addVector(0, e.height / 2, 0);
		double distance = AP.distanceTo(LP);
		double dis = distance - 1;
		return AP.addVector(look.x * dis, look.y * dis + 1, look.z * dis);
	}
}
