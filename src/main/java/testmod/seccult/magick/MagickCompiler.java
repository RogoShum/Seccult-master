package testmod.seccult.magick;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import testmod.seccult.events.PlayerDataUpdateEvent;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.magick.implementation.Implementation;

public class MagickCompiler {
	
	private  NBTTagList LoadMagick;
	private  NBTTagList LoadSelect;
	
	private int tick;
	private Magick magick;
	private int magickpower;
	private int magickattribute;
	
	private NBTTagCompound MagickNBT;
	private NBTTagList Select;
	
	private int order;
	
	private Entity e;
	public boolean done;
	private boolean toDo;
	private boolean cycle;
	public float[] color = {0, 0, 0};
	
	List<Entity> entity = new ArrayList<>();
	
	public MagickCompiler() {
	}
	
	public static NBTTagCompound compileMagick(int[][] MagickData, int[][] Selector, int[][] SelectorPower, int[][] SelectorAttribute, int amount,  boolean e, boolean b)
	{
		NBTTagCompound NewMagickNBTList = new NBTTagCompound();
		NBTTagList Magick = new NBTTagList();
		NBTTagList Select = new NBTTagList();
    	for(int i = 0; i < amount; i++)
    	{	
    		NBTTagCompound MagickNBT = new NBTTagCompound();
    		
        	MagickNBT.setInteger("Magick", MagickData[0][i]);
        	MagickNBT.setInteger("MagickPower", MagickData[1][i]);
        	MagickNBT.setInteger("MagickAttribute", MagickData[2][i]);
        	MagickNBT.setInteger("SelectorLength", MagickData[3][i]);
        	MagickNBT.setInteger("Slot", i);
        	for(int x = 0; x < MagickData[3][i]; x++)
        	{
        		NBTTagCompound SelectNBT = new NBTTagCompound();
        		SelectNBT.setInteger("Selector", Selector[i][x]);
        		SelectNBT.setInteger("SelectorPower", SelectorPower[i][x]);
        		SelectNBT.setInteger("SelectorAttribute", SelectorAttribute[i][x]);
        		SelectNBT.setInteger("Slot", i);
        		Select.appendTag(SelectNBT);
        	}
        	
        	Magick.appendTag(MagickNBT);
    	}
    	
    	NewMagickNBTList.setTag("Magick", Magick);
    	NewMagickNBTList.setTag("Selector", Select);

		return NewMagickNBTList;
	}
	
	public void pushMagickData(NBTTagCompound magickNbt, Entity e)
	{
		for(int i = 0; i < magickNbt.getTagList("Magick", 10).tagCount(); i++)
		{
			LoadMagick = magickNbt.getTagList("Magick", 10);
			LoadSelect = magickNbt.getTagList("Selector", 10);
			
			NBTTagCompound MagickNBT = LoadMagick.getCompoundTagAt(i);
			int slot = MagickNBT.getInteger("Slot");
			
			NBTTagList selector = new NBTTagList();
			for(int x = 0; x < LoadSelect.tagCount(); x++)
			{
				if(LoadSelect.getCompoundTagAt(x).getInteger("Slot") == slot) {
					NBTTagCompound SelectNBT = LoadSelect.getCompoundTagAt(x);
					selector.appendTag(SelectNBT);
				}
			}
			MagickCompiler newMagick = new MagickCompiler();
			newMagick.MagickNBT = MagickNBT;
			newMagick.Select = selector;
			newMagick.e = e;
			newMagick.toDo = true;
			PlayerDataUpdateEvent.compiler.add(newMagick);
		}
	}
	
	public void onUpdate()
	{
		if(done)
			return;
		tick++;
		if(!cycle && tick >= 20)
		{
			toDo = true;
			order++;
			tick = 0;
		}
		else if(cycle)
			toDo = true;
		
		if(toDo)
		doMagick();
		if(order == 0)
		{
			toDo = true;
			order++;
			tick = 0;
		}
	}
	
	private void doMagick()
	{
			if(MagickNBT == null)
			{
				done = true;
				return;
			}
		
    		if(magick == null) {
			magick = ModMagicks.getAttributeFromName(
					ModMagicks.GetMagickStringByID(
					MagickNBT.getInteger("Magick")));
			magickpower = MagickNBT.getInteger("MagickPower");
			magickattribute = MagickNBT.getInteger("MagickAttribute");
			color = magick.getRGB();
			entity.add(e);
    		}
    		
    		if(!cycle) {
			NBTTagCompound SelectNBT = Select.getCompoundTagAt(order);
			Implementation imples = (ImplementationHandler.getImplementationFromName(
						ModMagicks.GetMagickStringByID(
								SelectNBT.getInteger("Selector"))));
			int implesattribute = SelectNBT.getInteger("SelectorPower") + SelectNBT.getInteger("SelectorAttribute");

			imples.color = this.color;
			imples.setPlayer(e);
			imples.setAttribute(implesattribute);
			imples.setEntity(entity);
			imples.getTarget();
			entity = imples.getEntity();
			toDo = false;
			if(order == Select.tagCount() - 1)
			{
				cycle = true;
			}
    		}
			if(magick != null && entity != null && cycle && tick > 15)
			{
				for(int x = 0; x < entity.size(); x++)
					magick.setMagickAttribute(e, entity.get(x), null, magickpower, magickattribute);
				done = true;
			}
	}
	
    public static NBTTagList getMagickCode(NBTTagCompound item)
    {
        return item.hasKey("MagickCodeData") ? item.getTagList("MagickCodeData", 10) : null;
    }
}
