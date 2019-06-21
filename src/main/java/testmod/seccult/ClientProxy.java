package testmod.seccult;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy
{
	public static int sphereIdOutside;
	public static int sphereIdInside;
	
	public static int cylinderIdOutside;
	public static int cylinderIdInside;
	
	public static float height;
	
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	public void registerVariantRenderer(Item item, int meta, String filename, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Seccult.MODID, filename), id));
	}
	
	public static void setheight(float h) {
		height = h;
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
	
	public void cylinderRender() {
		Cylinder Cylinder = new Cylinder();
		//Set up paramters that are common to both outside and inside.

		//GLU_FILL as a solid.
		Cylinder.setDrawStyle(GLU.GLU_FILL);
		//GLU_SMOOTH will try to smoothly apply lighting
		Cylinder.setNormals(GLU.GLU_SMOOTH);
		Cylinder.setTextureFlag(true);
		//First make the call list for the outside of the sphere

		Cylinder.setOrientation(GLU.GLU_OUTSIDE);

		cylinderIdOutside = GlStateManager.glGenLists(1);
		//Create a new list to hold our sphere data.
		GlStateManager.glNewList(cylinderIdOutside, GL11.GL_COMPILE);
		//binds the texture 
		//The drawing the sphere is automatically doing is getting added to our list. Careful, the last 2 variables
		//control the detail, but have a massive impact on performance. 32x32 is a good balance on my machine.s
		Cylinder.draw(1F, 1F, height, 64, 64);
		GlStateManager.glEndList();

		//Now make the call list for the inside of the sphere
		Cylinder.setOrientation(GLU.GLU_INSIDE);
		cylinderIdInside = GlStateManager.glGenLists(1);
		//Create a new list to hold our sphere data.
		GlStateManager.glNewList(cylinderIdInside, GL11.GL_COMPILE);
		Cylinder.draw(1F, 1F, height, 64, 64);
		GlStateManager.glEndList();
	}
}
