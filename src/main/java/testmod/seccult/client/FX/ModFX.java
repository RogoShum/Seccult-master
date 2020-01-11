package testmod.seccult.client.FX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.Queues;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.entity.render.ParticleRender;
import testmod.seccult.client.entity.render.RenderGatorixEvent;

@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class ModFX {
	private static final List<ParticleFX> particles = new ArrayList<ParticleFX>();
	private static RenderManager renderer = Minecraft.getMinecraft().getRenderManager();
	
	public static TextureAtlasSprite Light;
	public static TextureAtlasSprite Star;
	public static TextureAtlasSprite Pentagon;
	public static TextureAtlasSprite ATField;
	public static TextureAtlasSprite Rainbow;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void onTick(RenderWorldLastEvent event)
    {
		Entity player = Minecraft.getMinecraft().player;
	    if(player == null)
	    	return;
	    RenderManager renderer = Minecraft.getMinecraft().getRenderManager();
	    //ParticleRender.renderLightFX(Minecraft.getMinecraft().getRenderManager(), 0, 0.9F, 0, 5, 1F, null);
	    
        if (!particles.isEmpty())
        {
            try
            {
                    for(int i = 0; i < particles.size(); ++i)
                    {
                    	if(particles.get(i) == null)
                    		continue;
                    	
                    	ParticleFX particle = particles.get(i);
                    	particle.render(event.getPartialTicks());
                    	
                    	
                    }
            }
            catch(Exception e)
            {
            	return;
            }
        }
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onParticleUpdate(ClientTickEvent event) {
		if (!particles.isEmpty())
        {
            for(int i = 0; i < particles.size(); ++i)
            {
            	if(particles.get(i) == null)
            		continue;
            	
            	ParticleFX particle = particles.get(i);
            	particle.onUpdate();
                if(particle.isDead())
                {
                	particles.remove(i);
                }
            }
        }
	}
	
	@SideOnly(Side.CLIENT)
	public static void addPar(ParticleFX par)
	{
		particles.add(par);
	}
	
    @SubscribeEvent
    public static void registerSprite(TextureStitchEvent.Pre event){
        ModFX.Light = event.getMap().registerSprite(new ResourceLocation("seccult:particle/light"));
        FogFX.test2 = event.getMap().registerSprite(new ResourceLocation("seccult:particle/light"));
        ModFX.Star = event.getMap().registerSprite(new ResourceLocation("seccult:particle/star"));
        ModFX.Pentagon = event.getMap().registerSprite(new ResourceLocation("seccult:particle/star2"));
        ModFX.ATField = event.getMap().registerSprite(new ResourceLocation("seccult:particle/ATfield"));
        ModFX.Rainbow = event.getMap().registerSprite(new ResourceLocation("seccult:particle/Rainbow"));
    }
}
