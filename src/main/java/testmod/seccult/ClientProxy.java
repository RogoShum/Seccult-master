package testmod.seccult;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
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
	
	public void init()
	{
		keyBindings = new KeyBinding[3]; 
		  
		// instantiate the key bindings
		keyBindings[0] = new KeyBinding("key.keyboard.desc", Keyboard.KEYBOARD_SIZE, "key.seccult.keyboard");
		keyBindings[1] = new KeyBinding("key.c.desc", Keyboard.KEY_C, "key.seccult.c");
		keyBindings[2] = new KeyBinding("key.b.desc", Keyboard.KEY_B, "key.seccult.b");
		  
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
}
