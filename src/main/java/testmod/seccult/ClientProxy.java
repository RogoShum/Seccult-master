package testmod.seccult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import com.google.common.collect.AbstractIterator;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.FrozenBlockFX;
import testmod.seccult.client.FX.ModFX;
import testmod.seccult.client.FX.PThunderFX;
import testmod.seccult.client.FX.ParticleFX;
import testmod.seccult.client.entity.render.RenderGatorix;
import testmod.seccult.client.entity.render.RenderGatorixEvent;
import testmod.seccult.client.entity.render.RenderHandler;
import testmod.seccult.client.textlib.TextLib;
import testmod.seccult.entity.EntitySound;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.entity.livings.IBossBase;
import testmod.seccult.events.EntityRenderHandler;
import testmod.seccult.events.HUDHandler;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemMagickCore;
import testmod.seccult.items.ItemWand;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class ClientProxy extends CommonProxy
{
    public static boolean rendering = false;
    public static Entity renderEntity = null;
    public static Entity backupEntity = null;
	
	public static KeyBinding[] keyBindings;
	
	public static int sphereIdOutside;
	public static int sphereIdInside;
	
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	public void registerVariantRenderer(Item item, int meta, String filename, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Seccult.MODID, filename), id));
	}
	
	@SideOnly(Side.CLIENT)
	public static void callSphere()
	{
	    GlStateManager.callList(ClientProxy.sphereIdOutside);
	    GlStateManager.callList(ClientProxy.sphereIdInside);
	}

	public void entityRender()
	{
		RenderHandler.registerEntityRenders();
	}
	
	public void BossSound(ArrayList<Entity> bosses)
	{
		Entity entity = bosses.get(0);

		if(bosses.size() <= 1)
		{
			if(!entity.world.isRemote) return;
			Minecraft.getMinecraft().getSoundHandler().playSound(new BossMusic(entity));
		}
		else
		{
			if(!entity.world.isRemote) return;
			Minecraft.getMinecraft().getSoundHandler().playSound(new BossMusic(entity));
		}
	}
	
    @SubscribeEvent
    public void onClientWorldLoad(WorldEvent.Load event)
    {
        if(event.getWorld() instanceof WorldClient)
        {
        	RenderGatorixEvent.mirrorGlobalRenderer.setWorldAndLoadRenderers((WorldClient) event.getWorld());
        }
    }

    @SubscribeEvent
    public void onClientWorldUnload(WorldEvent.Unload event)
    {
        if(event.getWorld() instanceof WorldClient)
        {
        	RenderGatorixEvent.clearRegisteredMirrors();
        }
    }
	
    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre event)
    {
        if(!rendering) return;

        if(event.getEntityPlayer() == renderEntity)
        {
            backupEntity = Minecraft.getMinecraft().getRenderManager().renderViewEntity;
            Minecraft.getMinecraft().getRenderManager().renderViewEntity = renderEntity;
        }
    }

    @SubscribeEvent
    public void onPostPlayerRender(RenderPlayerEvent.Post event)
    {
        if(!rendering) return;

        if(event.getEntityPlayer() == renderEntity)
        {
            Minecraft.getMinecraft().getRenderManager().renderViewEntity = backupEntity;
            renderEntity = null;
        }
    }
	
	public void setOutOfWater(EntityPlayer player)
	{
		try {
			if(player.world.handleMaterialAcceleration(player.getEntityBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D), Material.WATER, player))
				ModReclection.Entity_inWater(player, false);
			else
				ModReclection.Entity_inWater(player, true);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void renderEvent()
	{
		MinecraftForge.EVENT_BUS.register(new HUDHandler());
		MinecraftForge.EVENT_BUS.register(new EntityRenderHandler());
		MinecraftForge.EVENT_BUS.register(new RenderGatorixEvent());
		ItemColoerRegister();
	}

	public void preInit()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	private static class BossMusic extends MovingSound {
	
		private final Entity guardian;

		public BossMusic(Entity guardian) {
			super(((IBossBase)guardian).getBGM(), SoundCategory.RECORDS);
			this.guardian = guardian;
			this.repeat = true;
			this.volume = 5;
		}

		@Override
		public void update() {
			if (!guardian.isEntityAlive() || guardian.isDead) {
				this.volume = 0;
				donePlaying = true;
			}
			else
			{
				this.xPosF = (float) guardian.posX;
				this.yPosF = (float) guardian.posY;
				this.zPosF = (float) guardian.posZ;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static class BossMusicMult extends MovingSound {
		private ArrayList<EntityBase> bosses;
		private BlockPos pos = null;
		
		public BossMusicMult(ArrayList<EntityBase> guardian) {
			super(((IBossBase)guardian.get(0)).getBGM(), SoundCategory.RECORDS);
			this.bosses = guardian;
			this.repeat = true;
			this.volume = 5;
		}

		@Override
		public void update() {
			boolean stillAlive = false;
			for(int i = 0; i < bosses.size(); ++i)
			{
				EntityBase boss = bosses.get(i);
				if(boss.isEntityAlive() && !boss.isDead)
				{
					if(pos == null)
						pos = boss.getPosition();
					
					BlockPos newPos = new BlockPos((boss.posX + pos.getX()) / 2,(
							boss.posY + pos.getY()) / 2,
							(boss.posZ + pos.getZ()) / 2);
					
					pos = newPos;
					stillAlive = true;
				}
			}
			
			if(!stillAlive)
			{
				this.volume = 0;
				donePlaying = true;
			}

			this.xPosF = (float) pos.getX();
			this.yPosF = (float) pos.getY();
			this.zPosF = (float) pos.getZ();
		}
	}
	
	 @SideOnly(Side.CLIENT)
	public void ItemColoerRegister()
	{
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				if(tintIndex == 1)
					return ItemWand.getMagickColor(stack);

					return ItemWand.getWandStyle(stack, tintIndex);
			}
		}, ModItems.Wand);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.MagickCore);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.CHLOROPHYTE_SWORD);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.OCEAN_SWORD);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.SHADOW_SWORD);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.SILK_SWORD);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.SORCERER_SWORD);
	}
	
	public void init()
	{
		keyBindings = new KeyBinding[12]; 
		  
		// instantiate the key bindings
		keyBindings[0] = new KeyBinding("key.keyboard.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[1] = new KeyBinding("key.c.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[2] = new KeyBinding("key.b.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[3] = new KeyBinding("key.1.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[4] = new KeyBinding("key.2.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[5] = new KeyBinding("key.3.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[6] = new KeyBinding("key.4.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[7] = new KeyBinding("key.5.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[8] = new KeyBinding("key.6.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[9] = new KeyBinding("key.7.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[10] = new KeyBinding("key.8.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		keyBindings[11] = new KeyBinding("key.9.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyBind");
		  
		// register all the key bindings
		for (int i = 0; i < keyBindings.length; ++i) 
		{
		    ClientRegistry.registerKeyBinding(keyBindings[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public static void onEvent(KeyInputEvent event)
	{
	    // DEBUG
	    System.out.println("Key Input Event");

	    // make local copy of key binding array
	    KeyBinding[] keyBindings = ClientProxy.keyBindings;
	   
	    // check each enumerated key binding type for pressed and take appropriate action
	    if (keyBindings[0].isPressed()) 
	    {
	        // DEBUG
	        System.out.println("Key binding ="+keyBindings[0].getKeyDescription());
	            
	        // do stuff for this key binding here
	        // remember you may need to send packet to server
	    }
	    if (keyBindings[1].isPressed()) 
	    {
	        // DEBUG
	        System.out.println("Key binding ="+keyBindings[1].getKeyDescription());
	            
	        // do stuff for this key binding here
	        // remember you may need to send packet to server
	    }
	}
	
	public void sphereRender() {
		Sphere sphere = new Sphere();
		//Set up paramters that are common to both outside and inside.

		//GLU_FILL as a solid.
		sphere.setDrawStyle(GLU.GLU_FILL);
		//GLU_SMOOTH will try to smoothly apply lighting
		sphere.setNormals(GLU.GLU_SMOOTH);
		sphere.setTextureFlag(true);
		//First make the call list for the outside of the sphere

		sphere.setOrientation(GLU.GLU_OUTSIDE);

		sphereIdOutside = GlStateManager.glGenLists(1);
		//Create a new list to hold our sphere data.
		GlStateManager.glNewList(sphereIdOutside, GL11.GL_COMPILE);
		//binds the texture 
		//The drawing the sphere is automatically doing is getting added to our list. Careful, the last 2 variables
		//control the detail, but have a massive impact on performance. 32x32 is a good balance on my machine.s
		sphere.draw(0.5F, 64, 64);
		GlStateManager.glEndList();

		//Now make the call list for the inside of the sphere
		sphere.setOrientation(GLU.GLU_INSIDE);
		sphereIdInside = GlStateManager.glGenLists(1);
		//Create a new list to hold our sphere data.
		GlStateManager.glNewList(sphereIdInside, GL11.GL_COMPILE);
		sphere.draw(0.5F, 64, 64);
		GlStateManager.glEndList();
	}
	
	@SideOnly(Side.CLIENT)
	public void SeccultFX(double[] pos, double[] vec, float[] color, float scale, int type)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ParticleFX par = null;
    	
    	double x = pos[0];
    	double y = pos[1];
    	double z = pos[2];

    	double xx = vec[0];
    	double yy = vec[1];
    	double zz = vec[2];
    	
    	float r = color[0];
    	float g = color[1];
    	float b = color[2];
    	
		switch(type)
    	{
    		case 0:
    			par = new ParticleFX(ParticleFX.ParticleType.Light,x,y,z, xx, yy, zz, scale);
    			par.setRBGColorF(r, g, b);
    			break;
    		case 1:
    			par = new ParticleFX(ParticleFX.ParticleType.Star,x,y,z, xx, yy, zz, scale);
    			par.setRBGColorF(r, g, b);
    			break;
    		case 2:
    			par = new ParticleFX(ParticleFX.ParticleType.Pentagon,x,y,z, xx, yy, zz, scale);
    			par.setRBGColorF(r, g, b);
    			break;
    		case 3:
    		    for (int sn = 0; sn < 4; ++sn)
    		    {
    			double d0 = x + 3 - mc.world.rand.nextInt(6);
    			double d1 = y + 3 - mc.world.rand.nextInt(6);
    			double d2 = z + 3 - mc.world.rand.nextInt(6);
    			mc.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, d0, d1, d2, 0, 0, 0);
    		    }
    			break;
    		case 4:
    			par = new PThunderFX(x,y,z, xx, yy, zz, r, g, scale);
    			break;
    		case 5:
    			int distancec = (int) scale;

    			for (int i = 0; i < distancec; i++) {
    				double trailFactor = i / (distancec - 1.0D);
    				double tx = x + (xx - x) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double ty = y + (yy - y) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double tz = z + (zz - z) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double motionX = 1 - 2*mc.world.rand.nextFloat();
    				double motionY = 1 - 2*mc.world.rand.nextFloat();
    				double motionZ = 1 - 2*mc.world.rand.nextFloat();
    				
    				mc.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, tx, ty, tz, motionX / 25, motionY / 25, motionZ / 25);
    			}
    			break;
    		case 6:
    			int distanceee = (int) scale;
    			for (int i = 0; i < distanceee; i++) {
    				double trailFactor = i / (distanceee - 1.0D);
    				double tx = x + (xx - x) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double ty = y + (yy - y) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double tz = z + (zz - z) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double motionX = 0.25F - 0.5 * mc.world.rand.nextFloat();
    				double motionY = 1.8F - 2*mc.world.rand.nextFloat();
    				double motionZ = 0.25F - 0.5 * mc.world.rand.nextFloat();
    				
    				ParticleFX big = new ParticleFX(ParticleFX.ParticleType.Light, tx, ty, tz, motionX / 50, motionY / 50, motionZ / 50, 1.0F + mc.world.rand.nextFloat());
    		    	big.setRBGColorF(r, g, b);
    		    	ModFX.addPar(big);
    			}

    			for (int i = 0; i < distanceee; i++) {
    				double trailFactor = i / (distanceee - 1.0D);
    				double tx = x + (xx - x) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double ty = y + (yy - y) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double tz = z + (zz - z) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double motionX = 0.5F - mc.world.rand.nextFloat();
    				double motionY = 0.8F - mc.world.rand.nextFloat();
    				double motionZ = 0.5F - mc.world.rand.nextFloat();
    				
    				ParticleFX big = new ParticleFX(ParticleFX.ParticleType.Light, tx, ty, tz, motionX / 50, motionY / 50, motionZ / 50, 0.5F);
    		    	big.setRBGColorF(1, 1, 1);
    		    	
    		    	ParticleFX bigW = new ParticleFX(ParticleFX.ParticleType.Light, tx, ty, tz, motionX / 100, motionY / 50, motionZ / 100, 0.8F);
    		    	bigW.setRBGColorF(0.8F, 0.7F, 0.1F);
    		    	
    		    	//Particle aa = new LightFX(mc.world, tx, ty, tz, motionX / 50, motionY / 50, motionZ / 50, 0.6F + mc.world.rand.nextFloat());
    		    	//aa.setRBGColorF(r, g, b);
    		    	//ModFX.addPar(aa);
    		    	ModFX.addPar(bigW);
    		    	ModFX.addPar(big);
    			}
    			
    			break;
    		case 7:
    			par = new ParticleFX(ParticleFX.ParticleType.ATField, x, y, z, 0, 0, 0, 2);
    			break;
    		case 8:
    			par = new ParticleFX(ParticleFX.ParticleType.Rainbow, x, y, z, 0, 0, 0, 2);
    			break;
    		case 100:
    			for(int i = 0; i < 20 ; i++) {
    	            double d0 = (double)((float)pos[0] + mc.world.rand.nextFloat());
    	            double d1 = (double)((float)pos[1] + mc.world.rand.nextFloat());
    	            double d2 = (double)((float)pos[2] + mc.world.rand.nextFloat());
    	            double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
    	            double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
    	            double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
    	            ParticleFX me = new ParticleFX(ParticleFX.ParticleType.Light, (d0 +x) / 2.0D, (d1 +y) / 2.0D, (d2 +z) / 2.0D, d3/6, d4/6, d5/6, scale);
    	        	me.setRBGColorF(r, g, b);
    	        	ParticleFX smoke = new ParticleFX(ParticleFX.ParticleType.Star, d0, d1, d2, d3 / 5, d4 / 5, d5 / 5, scale / 3);
    	        	ModFX.addPar(me);
    	        	ModFX.addPar(smoke);
    			}

    			break;
    		case 101:
    			int distance = (int) scale;
    			for (int i = 0; i < distance; i++) {
    				double trailFactor = i / (distance - 1.0D);
    				double tx = x + (xx - x) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double ty = y + (yy - y) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double tz = z + (zz - z) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
    				double motionX = 1 - 2*mc.world.rand.nextFloat();
    				double motionY = 1 - 2*mc.world.rand.nextFloat();
    				double motionZ = 1 - 2*mc.world.rand.nextFloat();
    				
    				ParticleFX big = new ParticleFX(ParticleFX.ParticleType.Pentagon, tx, ty, tz, motionX / 25, motionY / 25, motionZ / 25, 0.4F);
    		    	big.setRBGColorF(r, g, b);
    		    	ModFX.addPar(big);
    			}
    			break;
    		case 102:
    			for(int i = 0; i < (scale * 2); i++) {
    			       double d0 = (double)((float)x + mc.world.rand.nextFloat());
    			       double d1 = (double)((float)y + mc.world.rand.nextFloat());
    			       double d2 = (double)((float)z + mc.world.rand.nextFloat());
    			       double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
    			       double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
    			       double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
    			       ParticleFX big = new ParticleFX(ParticleFX.ParticleType.Light, d0, d1, d2, -d3 / 20, -d4 / 20, -d5 / 20, scale / 2);
    			    	big.setRBGColorF(r, g, b);
    			    	big.setMaxAge(60);
    			    	ModFX.addPar(big);
    				}
    			break;
    		case 103:
    			for (int i = 0; i < scale * 30; i++) {
    				double motionX = 1 - 2*mc.world.rand.nextFloat();
    				double motionY = 1 - 2*mc.world.rand.nextFloat();
    				double motionZ = 1 - 2*mc.world.rand.nextFloat();
    				motionX = motionX * scale / 4;
    				motionY = motionY * scale / 4;
    				motionZ = motionZ * scale / 4;
    				ParticleFX big = new ParticleFX(ParticleFX.ParticleType.Light, x, y, z, motionX / 4, motionY / 4, motionZ / 4, scale * 5);
    		    	big.setRBGColorF(r, g, b);
    				big.setMaxAge(10);
    		    	ModFX.addPar(big);
    			}
    			break;
    		case 104:
    			int scale2 = (int)(scale / 2);
    				Iterable<BlockPos> Blocks = getAllInBox((int)x - scale2, (int)y, (int)z - scale2, (int)x + scale2, (int)y, (int)z + scale2);
    				for(BlockPos pos2: Blocks)
    				{
    						float randF = mc.world.rand.nextFloat()*2;
    						if(randF > 1.85 || randF < 0.15)
    						{
    							ParticleFX cc = new ParticleFX(ParticleFX.ParticleType.Light, pos2.getX(), pos2.getY(), pos2.getZ(), xx, yy, zz, (scale2) / 6  * randF);
    							cc.setRBGColorF(r, g, b);
    							ModFX.addPar(cc);
    						}
    				}
    			break;
    		case 105:
    				Particle cc = new FrozenBlockFX(mc.world, x, y, z, r, g, 3);
        			Minecraft.getMinecraft().effectRenderer.addEffect(cc);
    				
    			break;	
    			
    		case 106:
    			for (int i = 0; i < scale * 30; i++) {
    				double motionX = 1 - 2*mc.world.rand.nextFloat();
    				double motionY = 1 - 2*mc.world.rand.nextFloat();
    				double motionZ = 1 - 2*mc.world.rand.nextFloat();
    				motionX = motionX * scale / 3;
    				motionY = motionY * scale / 3;
    				motionZ = motionZ * scale / 3;
    				ParticleFX big = new ParticleFX(ParticleFX.ParticleType.Pentagon, x, y, z, motionX / 4, motionY / 4, motionZ / 4, scale * 5);
    		    	big.setRBGColorF(r, g, b);
    		    	ModFX.addPar(big);
    			}
				
			break;	
    }

    	if(par != null)
    		ModFX.addPar(par);
	}
	
	public static Iterable<BlockPos> getAllInBox(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2)
    {
        return new Iterable<BlockPos>()
        {
            public Iterator<BlockPos> iterator()
            {
                return new AbstractIterator<BlockPos>()
                {
                    private boolean first = true;
                    private double lastPosX;
                    private double lastPosY;
                    private double lastPosZ;
                    protected BlockPos computeNext()
                    {
                        if (this.first)
                        {
                            this.first = false;
                            this.lastPosX = x1;
                            this.lastPosY = y1;
                            this.lastPosZ = z1;
                            return new BlockPos(x1, y1, z1);
                        }
                        else if (this.lastPosX == x2 && this.lastPosY == y2 && this.lastPosZ == z2)
                        {
                            return (BlockPos)this.endOfData();
                        }
                        else
                        {
                            if (this.lastPosX < x2)
                            {
                                ++this.lastPosX;
                            }
                            else if (this.lastPosY < y2)
                            {
                                this.lastPosX = x1;
                                ++this.lastPosY;
                            }
                            else if (this.lastPosZ < z2)
                            {
                                this.lastPosX = x1;
                                this.lastPosY = y1;
                                ++this.lastPosZ;
                            }

                            return new BlockPos(this.lastPosX, this.lastPosY, this.lastPosZ);
                        }
                    }
                };
            }
        };
    }
}
