package testmod.seccult.items.armor.ShadowSky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.FogFX;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.ShadowSkyArmor;
import testmod.seccult.items.armor.MagickArmor.CoreType;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class ShadowSkyChest extends ShadowSkyArmor{

	public ShadowSkyChest(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.3F, 4, 3F);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
			addMagickCore(itemStack, CoreType.FlyingCore);
		if(!hasArmorSetItem(player))
			return;
		
		if(player.isSneaking())
		{
			for (int i = 0; i < 10; i++) {
				double tx = player.posX + player.world.rand.nextFloat() - player.width / 2;
				double ty = player.posY + player.world.rand.nextFloat() + player.height / 2;
				double tz = player.posZ + player.world.rand.nextFloat() - player.width / 2;
				double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

				Particle blackFog = new FogFX(player.world, tx, ty, tz, motionX / 50, motionY / 50, motionZ / 50, player.world.rand.nextFloat() * 5);
				blackFog.setRBGColorF(0, 0, 0);
		    	Minecraft.getMinecraft().effectRenderer.addEffect(blackFog);
			}
			
			if(Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown())
			{
				Vec3d look = player.getLookVec();
				player.motionX = look.x * player.getAIMoveSpeed() * 3;
				player.motionY = look.y * player.getAIMoveSpeed() * 3;
				player.motionZ = look.z * player.getAIMoveSpeed() * 3;
				
				player.noClip = true;
			}
			
			if(Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown())
			{
				Vec3d look = player.getLookVec();
				player.motionX = -look.x * player.getAIMoveSpeed() * 3;
				player.motionY = -look.y * player.getAIMoveSpeed() * 3;
				player.motionZ = -look.z * player.getAIMoveSpeed() * 3;
				
				player.noClip = true;
			}
		}
	}
	
	@SubscribeEvent
	public static void shadowDodge(LivingAttackEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		
		int chance = 7;
		
		if(hasShadowHelmet(player))
			chance--;
		
		if(hasArmorSetItem(player, 1, ModItems.SHADOW_SKY_CHEST))
			chance--;
		
		if(hasArmorSetItem(player, 2, ModItems.SHADOW_SKY_LEGGINGS))
			chance--;
		
		if(hasArmorSetItem(player, 3, ModItems.SHADOW_SKY_BOOTS))
			chance--;
		
		if(player.isSneaking())
			chance--;
		
		if(!player.world.isDaytime())
			chance--;
		
		 if(Seccult.rand.nextInt(chance) == 0)
			 event.setCanceled(true);
			 
		if(hasArmorSetItem(player) && event.getSource().damageType.equals(DamageSource.FALL.damageType))
			event.setCanceled(true);
		
		if(hasArmorSetItem(player) && event.getSource().damageType.equals(DamageSource.IN_WALL.damageType))
			event.setCanceled(true);
		
		if(hasArmorSetItem(player) && event.getSource().damageType.equals(DamageSource.FLY_INTO_WALL.damageType))
			event.setCanceled(true);
			
	}
	
	public static boolean hasShadowHelmet(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.SHADOW_SKY_HELMET);
	}
}
