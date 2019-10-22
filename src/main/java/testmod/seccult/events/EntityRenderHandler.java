package testmod.seccult.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.items.armor.ShadowSkyArmor;

public class EntityRenderHandler {
	public EntityRenderHandler() {}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onShadowMode(RenderPlayerEvent.Pre event)
	{
		EntityPlayer player = (EntityPlayer)event.getEntityPlayer();
		if(ShadowSkyArmor.hasArmorSetItem(player) && player.isSneaking())
		{
			event.setCanceled(true);
		}
	}

	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onStaffParticle(RenderPlayerEvent.Post event)
	{
		EntityPlayer player = (EntityPlayer)event.getEntityPlayer();
		if(player.getHeldItemMainhand().getItem() instanceof ItemStaff)
		{
			float[] color = ItemStaff.getMagickColor(player.getHeldItemMainhand());

			if(color != null)
			for (int i = 0; i < 5; i++) {
				double tx = player.posX + (player.world.rand.nextFloat() * 0.1F);
				double ty = player.posY + (player.world.rand.nextFloat() * 0.1F) + player.height;
				double tz = player.posZ + (player.world.rand.nextFloat() * 0.1F);
				double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

				Vec3d handVec = player.getLookVec().rotateYaw(-0.45F);
				Vec3d right = new Vec3d(tx, ty, tz).addVector(handVec.x, handVec.y, handVec.z);
				Particle ChlorophyteCrystal = new PentagonFX(player.world, right.x, right.y, right.z, motionX / 50, motionY / 50, motionZ / 50, player.world.rand.nextFloat() * 0.5F);
				ChlorophyteCrystal.setRBGColorF(color[0], color[1], color[2]);
		    	Minecraft.getMinecraft().effectRenderer.addEffect(ChlorophyteCrystal);
			}
		}
		
		if(player.getHeldItemMainhand().getItem() instanceof ItemStaff)
		{
			float[] color = ItemStaff.getMagickColor(player.getHeldItemOffhand());

			if(color != null)
			for (int i = 0; i < 5; i++) {
				double tx = player.posX + (player.world.rand.nextFloat() * 0.1F);
				double ty = player.posY + (player.world.rand.nextFloat() * 0.1F) + player.height;
				double tz = player.posZ + (player.world.rand.nextFloat() * 0.1F);
				double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

				Vec3d handVec = player.getLookVec().rotateYaw(0.45F);
				Vec3d right = new Vec3d(tx, ty, tz).addVector(handVec.x, handVec.y, handVec.z);
				Particle ChlorophyteCrystal = new PentagonFX(player.world, right.x, right.y, right.z, motionX / 50, motionY / 50, motionZ / 50, player.world.rand.nextFloat() * 0.5F);
				ChlorophyteCrystal.setRBGColorF(color[0], color[1], color[2]);
		    	Minecraft.getMinecraft().effectRenderer.addEffect(ChlorophyteCrystal);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onStaffParticle(RenderLivingEvent<EntityLivingBase> event)
	{
		EntityLivingBase entity = (EntityLivingBase)event.getEntity();
	}*/
}

