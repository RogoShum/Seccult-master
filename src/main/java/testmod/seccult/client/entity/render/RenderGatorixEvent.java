package testmod.seccult.client.entity.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import testmod.seccult.ClientProxy;
import testmod.seccult.entity.projectile.EntitySpaceGatorix;

public class RenderGatorixEvent {
	
	private static Minecraft mc = Minecraft.getMinecraft();
    public static RenderGlobal mirrorGlobalRenderer = new MirrorRenderGlobal(mc);
    protected int quality = 64;
    private long renderEndNanoTime;
    
    private static List<Integer> pendingRemoval = Collections.synchronizedList(new ArrayList<Integer>());
    protected static Map<EntitySpaceGatorix, Integer> registerGatorix = new ConcurrentHashMap<>();

    public static void clearRegisteredMirrors()
    {
    	registerGatorix.clear();
    }
    
	@SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event)
    {
        if(event.phase.equals(TickEvent.Phase.END))
            return;

        if(!pendingRemoval.isEmpty())
        {
            for(Integer integer : pendingRemoval)
            {
                GlStateManager.deleteTexture(integer);
            }
            pendingRemoval.clear();
        }

        
        
        if(mc.inGameHasFocus)
        	
        {
            for(EntitySpaceGatorix entity : registerGatorix.keySet())
            {
                if(entity == null || entity.isDead || !Minecraft.getMinecraft().world.loadedEntityList.contains(entity))
                {
                	registerGatorix.remove(entity);
                    continue;
                }

                if(!mc.player.canEntityBeSeen(entity))
                    continue;

                if(entity.getDistance(mc.player) < 32)
                {
                	
                    GameSettings settings = mc.gameSettings;
                    RenderGlobal renderBackup = mc.renderGlobal;
                    Entity entityBackup = mc.getRenderViewEntity();
                    int thirdPersonBackup = settings.thirdPersonView;
                    boolean hideGuiBackup = settings.hideGUI;
                    int mipmapBackup = settings.mipmapLevels;
                    float fovBackup = settings.fovSetting;
                    int widthBackup = mc.displayWidth;
                    int heightBackup = mc.displayHeight;
                    mc.renderGlobal = mirrorGlobalRenderer;
                    mc.setRenderViewEntity(entity);
                    float fov = 32 - mc.player.getDistance(entity) * 2F;
                    if(fov < 0)
                    	fov = 0;

                    settings.fovSetting = fov;
                    settings.thirdPersonView = 0;
                    settings.hideGUI = true;
                    settings.mipmapLevels = 3;

                    mc.displayWidth = quality;
                    mc.displayHeight = quality;

                    ClientProxy.rendering = true;
                    ClientProxy.renderEntity = mc.player;

                    int fps = Math.max(30, settings.limitFramerate);
                    EntityRenderer entityRenderer = mc.entityRenderer;
                    entityRenderer.renderWorld(event.renderTickTime, renderEndNanoTime + (1000000000 / fps));

                    GlStateManager.bindTexture(registerGatorix.get(entity));
                    GL11.glCopyTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 0, 0, quality, quality, 0);
                    
                    renderEndNanoTime = System.nanoTime();

                    ClientProxy.renderEntity = null;
                    ClientProxy.rendering = false;

                    mc.renderGlobal = renderBackup;
                    mc.setRenderViewEntity(entityBackup);
                    settings.fovSetting = fovBackup;
                    settings.thirdPersonView = thirdPersonBackup;
                    settings.hideGUI = hideGuiBackup;
                    settings.mipmapLevels = mipmapBackup;
                    mc.displayWidth = widthBackup;
                    mc.displayHeight = heightBackup;
                }
            }
        }
    }
}
