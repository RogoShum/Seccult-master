package testmod.seccult.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import testmod.seccult.Seccult;

public class PlayerMagickDataHandler {
	
	public static void setMagickData(EntityPlayer pl, String s)
	{
		if(!hasMagick(pl, s))
		{
			NBTTagInt i = new NBTTagInt(ModMagicks.GetMagickIDByString(s));
			PgetDataListForPlayer(pl).appendTag(i);
		}
	}
	
	public static boolean hasMagick(EntityPlayer pl, String s)
	{
		NBTTagList nbt = PgetDataListForPlayer(pl);
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < nbt.tagCount(); i++)
		{
			
			list.add(nbt.getIntAt(i));
		}

		if(list != null && list.contains(ModMagicks.GetMagickIDByString(s))) 
		{
			return true;
		}
		
		return false;
	}
	
	private static NBTTagCompound getForgeData(EntityPlayer player)
	{
		NBTTagCompound forgeData = player.getEntityData();
		if(!forgeData.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			forgeData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());

		NBTTagCompound persistentData = forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if(!persistentData.hasKey(Seccult.Data))
			persistentData.setTag(Seccult.Data, new NBTTagCompound());
		
		return persistentData.getCompoundTag(Seccult.Data);
	}
	
	private static NBTTagList PgetDataListForPlayer(EntityPlayer player) {
		NBTTagCompound forgeData = player.getEntityData();
		if(!forgeData.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			forgeData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		}

		NBTTagCompound persistentData = forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if(!persistentData.hasKey(Seccult.Data)) {
			persistentData.setTag(Seccult.Data, new NBTTagCompound());
		}
		
		if(!persistentData.getCompoundTag(Seccult.Data).hasKey(Seccult.MagickData)) 
		{
			NBTTagList list = new NBTTagList();
			persistentData.getCompoundTag(Seccult.Data).setTag(Seccult.MagickData, list);
		}
		
		
		return persistentData.getCompoundTag(Seccult.Data).getTagList(Seccult.MagickData, 3);
	}
	
	private static NBTTagList getMagickListForPlayer(EntityPlayer player) {
		NBTTagCompound data = getForgeData(player);
		
		if(!data.hasKey(Seccult.MagickList)) 
		{
			NBTTagList list = new NBTTagList();
			data.setTag(Seccult.MagickList, list);
		}
		
		return data.getTagList(Seccult.MagickList, 10);
	}
	
	public static NBTTagList ClientGetDataListForPlayer(EntityPlayer player) {
		NBTTagList list = PgetDataListForPlayer(player).copy();
		return list;
	}
	
	public static NBTTagList getDataListForPlayer(EntityPlayer player) {
		NBTTagList list = PgetDataListForPlayer(player).copy();
		return list;
	}
}
