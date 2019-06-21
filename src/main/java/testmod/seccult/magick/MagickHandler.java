package testmod.seccult.magick;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.magick.implementation.Implementation;

public class MagickHandler {
	private NBTTagList magick;
	private ArrayList<NBTTagCompound> MagickList = new ArrayList<NBTTagCompound>();
	private NBTTagList newlist = new NBTTagList();
	
	public MagickHandler() {}
	
	public void packUp(NBTTagCompound item, int slot)
	{
		NBTTagList tag = getMagickCode(item);
		if(tag != null) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("Slot", slot);
			for(int i = 0; i < MagickList.size(); i++)
			{
				newlist.appendTag(MagickList.get(i));
			}
			nbt.setTag("MagickList", newlist);
			item.getTagList("MagickCodeData", 10).appendTag(nbt);
		}
		else
		{
			NBTTagList list = new NBTTagList();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("Slot", slot);
			for(int i = 0; i < MagickList.size(); i++)
			{
				newlist.appendTag(MagickList.get(i));
			}
			nbt.setTag("MagickList", newlist);
			list.appendTag(nbt);
			item.setTag("MagickCodeData", list);
		}
	}
	
	public void packUp(NBTTagCompound item)
	{
		NBTTagList tag = getMagickCode(item);
		if(tag != null) {
			NBTTagCompound nbt = new NBTTagCompound();
			for(int i = 0; i < MagickList.size(); i++)
			{
				newlist.appendTag(MagickList.get(i));
			}
			nbt.setTag("MagickList", newlist);
			item.getTagList("MagickCodeData", 10).appendTag(nbt);
		}
		else
		{
			NBTTagList list = new NBTTagList();
			NBTTagCompound nbt = new NBTTagCompound();
			for(int i = 0; i < MagickList.size(); i++)
			{
				newlist.appendTag(MagickList.get(i));
			}
			nbt.setTag("MagickList", newlist);
			list.appendTag(nbt);
			item.setTag("MagickCodeData", list);
		}
	}
	
	public void compileMagick(String magick, float power, float attribute, String way, boolean e, boolean b)
	{
			NBTTagCompound newNbt = new NBTTagCompound();
			newNbt.setString("Magick", magick);
			newNbt.setFloat("power", power);
			newNbt.setFloat("attribute", attribute);
			newNbt.setString("Way", way);
			int doWho = 0;
			if(e)
				doWho = 1;
			if(b)
				doWho = 2;
			if(e && b)
				doWho = 3;
			newNbt.setInteger("doWho", doWho);
			MagickList.add(newNbt);
	}
	
	public static void decompileMagick(NBTTagList tag, Entity player)
	{
		if(tag != null)
		{
			for (int i = 0; i < tag.tagCount(); ++i)
	        {
				NBTTagCompound nbt = tag.getCompoundTagAt(i);
					makeMagick(nbt, player);	
	        }
		}
	}
	
	public static void makeMagick(NBTTagCompound magickNbt, Entity e)
	{	
			int doEorB = magickNbt.getInteger("doWho");
			Magick magick = new ActiveHandler().getAttributeFromName(magickNbt.getString("Magick"));
			Implementation im = new ImplemrntationHandler().getImplementationFromName(magickNbt.getString("Way"));
			if(e instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) e;
				if (im == null || doEorB == 0)
				{
					PlayerData data = PlayerDataHandler.get(player);
					
					magick.setMagickAttribute(player, player, im.getBlock(), data.getManaStrengh() + magickNbt.getInteger("power"), data.getControlAbility() + magickNbt.getInteger("attribute"));
					return;
				}
			}
			
			switch(doEorB)
			{
				case 1:
					
					im.doEntity();
					break;
				case 2:
					im.doBlock();
					break;
				case 3:
					im.doBlock();
					im.doEntity();
			}
			im.getTarget(e);
			magick.setMagickAttribute(e, im.getEntity(), im.getBlock(), magickNbt.getInteger("power"), magickNbt.getInteger("attribute"));
	}
    
    public static NBTTagList getMagickCode(NBTTagCompound item)
    {
        return item.hasKey("MagickCodeData") ? item.getTagList("MagickCodeData", 10) : null;
    }
}
