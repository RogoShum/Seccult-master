package testmod.seccult.magick;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import testmod.seccult.events.PlayerDataUpdateEvent;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.magick.implementation.Implementation;
import testmod.seccult.magick.implementation.ImplementationStoreable;

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
	List<BlockPos> block = new ArrayList<>();
	
	private boolean doEntity = true;
	private boolean doBlock = true;
	
	public MagickCompiler() {}

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
    	NBTTagCompound MagickAttribute = new NBTTagCompound();
    	MagickAttribute.setBoolean("doEntity", e);
    	MagickAttribute.setBoolean("doBlock", b);
    	
    	NewMagickNBTList.setTag("Magick", Magick);
    	NewMagickNBTList.setTag("Selector", Select);
    	NewMagickNBTList.setTag("MagickAttribute", MagickAttribute);
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
			
			NBTTagCompound MagickAttribute = magickNbt.getCompoundTag("MagickAttribute");
			
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
			if(e == null || e.world.isRemote)
				return;
			newMagick.toDo = true;
			newMagick.doEntity = MagickAttribute.getBoolean("doEntity");
			newMagick.doBlock = MagickAttribute.getBoolean("doBlock");
    		if(magick == null && !MagickNBT.hasNoTags()) {
    			magick = ModMagicks.getMagickFromName(
				ModMagicks.GetMagickStringByID(
				MagickNBT.getInteger("Magick")));
			if(magick != null)
			{
				color = magick.getRGB();
			}
    		}
			
			PlayerDataUpdateEvent.compiler.add(newMagick);
		}
	}
	
	public float[] getColor()
	{
		return this.color;
	}
	
	public void pushMagickData(NBTTagCompound magickNbt, NBTTagList selectNbt, Entity e, boolean doEntity, boolean doBlock)
	{
			MagickCompiler newMagick = new MagickCompiler();
			newMagick.MagickNBT = magickNbt;
			newMagick.Select = selectNbt;
			newMagick.e = e; 
			if(e == null || e.world.isRemote)
				return;
			newMagick.toDo = true;
			newMagick.doEntity = doEntity;
			newMagick.doBlock = doBlock;
			PlayerDataUpdateEvent.compiler.add(newMagick);
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
		
			if(Select == null || Select.hasNoTags())
			{
				cycle = true;
			}
			
			
    		if(magick == null && !MagickNBT.hasNoTags()) {
    			magick = ModMagicks.getMagickFromName(
				ModMagicks.GetMagickStringByID(
				MagickNBT.getInteger("Magick")));
			if(magick == null)
				return;
			
			magickpower = MagickNBT.getInteger("MagickPower");
			magickattribute = MagickNBT.getInteger("MagickAttribute");
			color = magick.getRGB();
			entity.add(getEntityHit());
			block.add(getBlockHit());
    		}
    		
    		
    		
    		if(!cycle) {
			NBTTagCompound SelectNBT = Select.getCompoundTagAt(order);
			Implementation imples = (ImplementationHandler.getImplementationFromName(
									ModMagicks.GetMagickStringByID(
										SelectNBT.getInteger("Selector"))));
			int implesattributeBase = SelectNBT.getInteger("SelectorPower");
			int implesattributeAddtion =  SelectNBT.getInteger("SelectorAttribute");
			imples.color = this.color;
			if(this.doEntity)
				imples.doEntity();
			if(this.doBlock)
				imples.doBlock();
			imples.setPlayer(e);
			imples.getTarget();
			imples.setAttribute(implesattributeBase, implesattributeAddtion);
			imples.setEntity(entity);
			imples.setBlock(block);
			if(imples instanceof ImplementationStoreable)
			{
				NBTTagList NewList = new NBTTagList();
				for(int i = 0; i < Select.tagCount() - order - 1; i++)
				{
					NewList.appendTag(Select.getCompoundTagAt(order+1+i));
				}
				if(NewList.hasNoTags())
					NewList = null;
				ImplementationStoreable IMStore = (ImplementationStoreable) imples;
				ModMagicks.getMagickFromName(
						ModMagicks.GetMagickStringByID(
								MagickNBT.getInteger("Magick")));
				IMStore.setData(MagickNBT.copy(), NewList, this.doEntity, this.doBlock);
				IMStore.setScale(implesattributeBase + implesattributeAddtion);
				IMStore.getTarget();
				done = true;
				return;
			}
			imples.getTarget();
			entity = imples.getEntity();
			block = imples.getBlock();
			toDo = false;
			if(order >= Select.tagCount() - 1)
			{
				cycle = true;
			}
    		}
    		
    		if(magick != null && cycle && tick > 15) {
    			float cost = magick.strenghCost * magickpower;
    			cost = cost * (magickattribute + 1);

    			if(block != null)
				{
					for(int x = 0; x < block.size(); x++)
					magick.setMagickAttribute(e, null, block.get(x), magickpower, magickattribute, cost);
					done = true;
				}
 
				if(entity != null)
				{
					for(int x = 0; x < entity.size(); x++)
					magick.setMagickAttribute(e, entity.get(x), null, magickpower, magickattribute, cost);
					done = true;
				}
    		}
	}
	
	public Entity getEntityHit()
	{
		Entity hit = null;
		if(MagickNBT.hasKey("EntityHit" + "Most") && MagickNBT.hasKey("EntityHit" + "Least"))
		{
			UUID id = MagickNBT.getUniqueId("EntityHit");
			if (id != null)
	        {
				hit = this.getEntityByUUID(id);

	            if (hit == null && e.world instanceof WorldServer)
	            {
	                try
	                {
	                	hit = ((WorldServer)e.world).getEntityFromUuid(id);
	                }
	                catch (Throwable var2)
	                {
	                    hit = null;
	                }
	            }
	        }
		}
		return hit;
	}
	
	public BlockPos getBlockHit()
	{
		BlockPos hit = null;
		if(MagickNBT.hasKey("BlockHit"))
		{
			NBTTagList pos = MagickNBT.getTagList("BlockHit", 6);
			if (pos != null)
	        {
				hit = new BlockPos(pos.getDoubleAt(0), pos.getDoubleAt(1), pos.getDoubleAt(2));
	        }
		}
		return hit;
	}
	
    @Nullable
    public Entity getEntityByUUID(UUID uuid)
    {
        for (int j2 = 0; j2 < e.world.getLoadedEntityList().size(); ++j2)
        {
            Entity entity = e.world.getLoadedEntityList().get(j2);

            if (uuid.equals(entity.getUniqueID()))
            {
                return entity;
            }
        }

        return null;
    }
	
    public static NBTTagList getMagickCode(NBTTagCompound item)
    {
        return item.hasKey("MagickCodeData") ? item.getTagList("MagickCodeData", 10) : null;
    }
}
