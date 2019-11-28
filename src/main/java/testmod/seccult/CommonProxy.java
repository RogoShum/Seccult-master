package testmod.seccult;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.EntityBase;

public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void registerVariantRenderer(Item item, int meta, String filename, String id) {}
	
	public void entityRender() {}
	
	public void BossSound(ArrayList<Entity> bosses) {}
	
	public void SeccultFX(double[] pos, double[] vec, float[] color, float scale, int type) {}
	
	public void setOutOfWater(EntityPlayer player) {}
	
	public void renderEvent() {}
	
	public void sphereRender() {}
	
	public void init() {}
	
	public void preInit() {}
}
