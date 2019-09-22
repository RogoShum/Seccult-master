package testmod.seccult.items.armor.Chlorophyte;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.PentagonFX;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.items.armor.ChlorophyteArmor;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class ChlorophyteHelmet extends ChlorophyteArmor{

	public ChlorophyteHelmet(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.2F, 1, 1F);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		//addMagickCore(itemStack, CoreType.PeaceCore);
		if(hasArmorSetItem(player))
		for (int i = 0; i < 5; i++) {
			double tx = player.posX + (player.world.rand.nextFloat() * 0.1F);
			double ty = player.posY + (player.world.rand.nextFloat() * 0.1F) + player.height + 0.4F;
			double tz = player.posZ + (player.world.rand.nextFloat() * 0.1F);
			double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
			double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
			double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

			Particle ChlorophyteCrystal = new PentagonFX(player.world, tx, ty, tz, motionX / 50, motionY / 50, motionZ / 50, player.world.rand.nextFloat() * 1.2F);
			ChlorophyteCrystal.setRBGColorF(0.2F, 1.0f, 0.0f);
	    	Minecraft.getMinecraft().effectRenderer.addEffect(ChlorophyteCrystal);
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
				if(hasArmorSetItem(player))
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
		
		if(hasArmorSetItem(player))
		{
			EntityBase base = new EntityBase(player.world);
			base.setPosition(player.posX, player.posY + player.height + 0.4F, player.posZ);
			base.faceEntity(source, 360, 360);
			
			if(base.doPoisionMagick(10, 20))
				base.setDead();
		}
	}
}
