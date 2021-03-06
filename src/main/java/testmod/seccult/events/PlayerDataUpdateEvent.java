package testmod.seccult.events;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.glu.Disk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.api.PlayerSpellReleaseTool;
import testmod.seccult.api.accessorie.PlayerAccessorieHandler;
import testmod.seccult.blocks.tileEntity.tileMagickCrafting;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemWand;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.active.MagickTeleporter;

public class PlayerDataUpdateEvent {
    public static final List<String> MagicDamage  = new ArrayList<>();
    private List<MagickCompiler> compiler = new ArrayList<>();
    private List<MagickTeleporter> Teleporter = new ArrayList<>();
    
	private static ResourceLocation geass = new ResourceLocation("seccult:textures/spell/geass.png");
    
    public PlayerDataUpdateEvent()
    {
    	MagicDamage.add(ModDamage.darkMagic.damageType);
    	MagicDamage.add(ModDamage.forbiddenMagic.damageType);
    	MagicDamage.add(ModDamage.normalMagic.damageType);
    	MagicDamage.add(ModDamage.pureMagic.damageType);
    	MagicDamage.add(ModDamage.BlackVelvetHell.damageType);
    	MagicDamage.add(ModDamage.Electro.damageType);
    	MagicDamage.add(ModDamage.Fire.damageType);
    	MagicDamage.add(ModDamage.Flame.damageType);
    	MagicDamage.add(ModDamage.Flame.damageType);
    	MagicDamage.add(ModDamage.Frozen.damageType);
    	MagicDamage.add(ModDamage.Posion.damageType);
    	MagicDamage.add(ModDamage.Thunder.damageType);
	}
	
    public List<MagickCompiler> getCompiler()
    {
    	return this.compiler;
    }
    
    public List<MagickTeleporter> getTeleporter()
    {
    	return this.Teleporter;
    }
	
	@SubscribeEvent
	public void onPlayerTick(LivingUpdateEvent event) 
	{
		if(event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			PlayerDataHandler.get(player).tick();
			PlayerAccessorieHandler.get(player).tick();
			PlayerSpellReleaseTool.get(player).tick();
		}
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) 
	{
		for(int i = 0; i < compiler.size(); i++)
		{
			compiler.get(i).onUpdate();
			if(compiler.get(i).done)
				compiler.remove(i);
		}
		
		for(int i = 0; i < Teleporter.size(); i++)
		{
			Teleporter.get(i).onUpdate();
			if(Teleporter.get(i).done)
				Teleporter.remove(i);
		}
		
		if(event.phase == Phase.END) {
			PlayerDataHandler.cleanup();
			PlayerAccessorieHandler.cleanup();
			PlayerSpellReleaseTool.cleanup();
		}
	}
	
	@SubscribeEvent
	public void onHurt(LivingAttackEvent event)
	{
		if(event.getSource().getTrueSource() instanceof EntityPlayer && MagicDamage.contains(event.getSource().getDamageType()))
		{
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			PlayerDataHandler.get(player).addAttributeValue();
		}
	}
	
	@SubscribeEvent
	public void onCreatureDeath(LivingDeathEvent event)
	{
		if(event.getSource().getTrueSource() instanceof EntityPlayer && MagicDamage.contains(event.getSource().getDamageType()))
		{
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			PlayerDataHandler.get(player).levelUpper();
		}
	}
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		if(player.isSneaking() && player.world.getTileEntity(pos) instanceof tileMagickCrafting)
		{
			tileMagickCrafting tile = (tileMagickCrafting) player.world.getTileEntity(pos);
			if(player.getHeldItem(event.getHand()).isEmpty())
			{
				ItemStack stack = tile.getItemStack();
				if(stack == null)
					return;
				EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 0.5, pos.getZ(), stack);
				if(!world.isRemote)
					world.spawnEntity(item);
			}
			else if(player.getHeldItem(event.getHand()).getItem() == ModItems.SoulStone)
			{
				
			}
		}
	}
	
	public static class QAQ
	{
	    
		@SideOnly(Side.CLIENT)
		public static void render(EntityPlayer player, float partTicks) {
			RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
			double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partTicks - renderManager.viewerPosX;
			double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partTicks - renderManager.viewerPosY;
			double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partTicks - renderManager.viewerPosZ;
			
			if(!player.isSneaking())
				renderGeass(x + player.getLookVec().x / 5, y, z + player.getLookVec().z / 2, player.rotationYaw);
			else
				renderGeass(x + player.getLookVec().x / 5, y - 0.3F, z + player.getLookVec().z / 2, player.rotationYaw);
		}


		public static void renderGeass(double x, double y, double z, float yaw) {
			TextureManager tex = Minecraft.getMinecraft().getTextureManager();
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y + 1.65F, z);
			GlStateManager.rotate(180F, 0F, 0F, 1F);
			GlStateManager.rotate(yaw, 0F, 1F, 0F);
			GlStateManager.disableCull();
			GlStateManager.disableLighting();
	        
			GlStateManager.pushMatrix();
		    GlStateManager.enableBlend();
		    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		    GlStateManager.color(1.0F, 0.2F, 0.2F, 0.12F);
		    //GlStateManager.translate(x, y + 0.01, z);
		    tex.bindTexture(geass);
		    Disk disk = new Disk();
		    disk.setTextureFlag(true);
		    disk.draw(0, 0.3F, 16, 16);    
		    GlStateManager.popMatrix();
		    
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
		}
	}
}
