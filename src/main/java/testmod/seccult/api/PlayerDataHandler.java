package testmod.seccult.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModDamage;
import testmod.seccult.items.armor.ArmorBase;
import testmod.seccult.network.NetworPlayerMagickData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerWandData;

public class PlayerDataHandler {
	
	private static HashMap<Integer, PlayerData> playerData = new HashMap();
	
	public static PlayerData get(EntityPlayer player) {
		int key = getKey(player);
		if(!playerData.containsKey(key))
			playerData.put(key, new PlayerData(player));

		PlayerData data = playerData.get(key);
		if(data != null && data.player != null && data.player != player) {
			NBTTagCompound cmp = new NBTTagCompound();
			data.writeToNBT(cmp);
			playerData.remove(key);
			data = get(player);
			data.readFromNBT(cmp);
		}

		return data;
	}
	
	public static void cleanup() {
		List<Integer> removals = new ArrayList();
		Iterator<Entry<Integer, PlayerData>> it = playerData.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Integer, PlayerData> item = it.next();
			PlayerData d = item.getValue();
			if(d != null && d.player == null)
				removals.add(item.getKey());
		}

		for(int i : removals)
			playerData.remove(i);
	}
	
	private static int getKey(EntityPlayer player) {
		int t = 0;
		try {
			int i = player.hashCode() << 1;
			int r = player.getEntityWorld().isRemote ? 1 : 0;
			t = i + r;
		}
		catch(Exception e) {
			System.out.println("hashCode " + (player.hashCode() << 1));
			System.out.println("World " + player.getEntityWorld());
			System.out.println("Remote " + player.getEntityWorld().isRemote);
		}
		return t;
	}
	
	public static NBTTagCompound getDataCompoundForPlayer(EntityPlayer player) {
		NBTTagCompound forgeData = player.getEntityData();
		if(!forgeData.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			forgeData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());

		NBTTagCompound persistentData = forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if(!persistentData.hasKey(Seccult.Data))
			persistentData.setTag(Seccult.Data, new NBTTagCompound());

		return persistentData.getCompoundTag(Seccult.Data);
	}
	
	public static class PlayerData{
		static Random rand = new Random();
		private static final String TAG_MANA_TALENT_VALUE = "ManaTalentValue";
		private static final String TAG_COMTROL_ABILITY = "ControlAbility";
		private static final String TAG_MANA_STRENGH = "ManaStrengh";
		private static final String TAG_GROWTH_ABILITY = "GrowthAbility";
		
		private static final String TAG_MANA_VALUE = "ManaValue";
		private static final String TAG_MAX_MANA_VALUE = "MaxManaValue";
		
		private static final String TAG_PROFICIENCY_LEVEL = "proficiency";
		
		private NBTTagList MagickList;
		
		private float ManaTalentValue;
		private float ControlAbility;
		private float ManaStrengh;
		private float GrowthAbility;
		
		private float ManaValue;
		private float MaxManaValue;
		
		private float ExtraManaValue;
		private float lastTickMaxManaValue;
		
		private float proficiency;
		
		private float regenCooldown;
		
		private int color2;
		private int color3;
		private int color4;
		
		private int[] magickData = {0};
		
		private int wand;
		
		public EntityPlayer player;
		private final boolean client;
		
		public PlayerData(EntityPlayer player) {
			this.player = player;
			client = player.getEntityWorld().isRemote;

			load();
		}
		
		public void load() {
			if(!client) {
				if(this.player != null) {
					NBTTagCompound cmp = getDataCompoundForPlayer(this.player);
					readFromNBT(cmp);
				}
			}
		}
		
		public void readFromNBT(NBTTagCompound cmp) {
			ManaTalentValue = cmp.getFloat(TAG_MANA_TALENT_VALUE);
			MaxManaValue = cmp.getFloat(TAG_MAX_MANA_VALUE);
			ControlAbility = cmp.getFloat(TAG_COMTROL_ABILITY);
			ManaStrengh = cmp.getFloat(TAG_MANA_STRENGH);
			GrowthAbility = cmp.getFloat(TAG_GROWTH_ABILITY);
			ManaValue = cmp.getFloat(TAG_MANA_VALUE);
			proficiency = cmp.getFloat(TAG_PROFICIENCY_LEVEL);
			
			if(!client)
			{
			if(cmp.hasKey("WandStyle"))
				wand = cmp.getInteger("WandStyle");
			else
				cmp.setInteger("WandStyle", rand.nextInt(6) + 1);

			if(!cmp.hasKey("Color2") || !cmp.hasKey("Color3") || !cmp.hasKey("Color4"))
			{
				cmp.setInteger("Color2", getColor());
				cmp.setInteger("Color3", getColor());
				cmp.setInteger("Color4", getColor());
			}
			else
			{
				color2 = cmp.getInteger("Color2");
				color3 = cmp.getInteger("Color3");
				color4 = cmp.getInteger("Color4");
			}
			NetworkHandler.getNetwork().sendTo(new NetworkPlayerWandData(color2, color3, color4, wand), (EntityPlayerMP) player);
			}
			
			if(ManaTalentValue == 0)
			{
				ArrayList<Float> list = new ArrayList<Float>();
				for(int i = 0; i < 155; i++) 
				{
					if(i <= 9)
						list.add(5F);
					else if(i <= 29)
						list.add(3.5F);
					else if(i <= 59)
						list.add(2.5F);
					else if(i <= 99)
						list.add(2F);
					else if(i <= 149)
						list.add(1.5F);
					else if(i <= 154)
						list.add(7F);
				}
				float y = rand.nextFloat() * list.get(rand.nextInt(155));
				if(y < 1)
					y = 1;
				ManaTalentValue = y;
			}
			
			if(MaxManaValue < 100)
				MaxManaValue = 100;
			
			if(ControlAbility == 0)
				ControlAbility = ManaTalentValue / 1+rand.nextFloat();
			
			if(ManaStrengh == 0)
				ManaStrengh = ManaTalentValue / 1+rand.nextFloat();
			
			if(GrowthAbility == 0 )
				GrowthAbility = ManaTalentValue / 1+rand.nextFloat();
			
			if(!cmp.hasKey(Seccult.MagickList)) 
			{
				NBTTagList list = new NBTTagList();
				cmp.setTag(Seccult.MagickList, list);
				MagickList = list;
			}
			else
			{
				MagickList = cmp.getTagList(Seccult.MagickList, 10);
			}
			
			if(MagickList == null)
			{
				MagickList = new NBTTagList();
			}
			
			NBTTagList Mdata = getMagickDataForPlayer(player);
			int[] newMData = new int[Mdata.tagCount()];
			for(int i = 0; i< Mdata.tagCount(); i++)
				newMData[i] = Mdata.getIntAt(i);
			
			magickData = newMData;
		}
		
		public void save() {
			if(!client) {
				if(this.player != null) {
					NBTTagCompound cmp = getDataCompoundForPlayer(player);
					writeToNBT(cmp);
				}
			}
		}

		public void writeToNBT(NBTTagCompound cmp) {
			

			
			cmp.setFloat(TAG_MANA_TALENT_VALUE, ManaTalentValue);
			cmp.setFloat(TAG_COMTROL_ABILITY, ControlAbility);
			cmp.setFloat(TAG_MANA_STRENGH, ManaStrengh);
			cmp.setFloat(TAG_GROWTH_ABILITY, GrowthAbility);
			
			cmp.setFloat(TAG_MANA_VALUE, ManaValue);
			cmp.setFloat(TAG_MAX_MANA_VALUE, MaxManaValue);
			
			cmp.setFloat(TAG_PROFICIENCY_LEVEL, proficiency);
			cmp.setTag(Seccult.MagickList, MagickList);
			setMagickData(player, magickData);
		}
		
		public static int getColor(){
	        String red;
	        String green;
	        String blue;

	        int co = rand.nextInt(3);
	        int co2 = rand.nextInt(2);
	        
	        if(co == 0) {
	        	if(co2 == 0) 
	        	{
	        		red = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
	        		green = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
	        		blue = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
	        	}
	        	else
	        	{
			        red = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
			        green = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
		    		blue = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
	        	}
	        }
	        else if(co == 1)
	        {	
	        	if(co2 == 0) 
	        	{
	        		red = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
	        		green = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
	        		blue = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
	        	}
	        	else
	        	{
			        red = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
			        green = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
		    		blue = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
	        	}
	        }
	        else
	        {
	        	if(co2 == 0) 
	        	{
	        		red = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
	        		green = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
	        		blue = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
	        	}
	        	else
	        	{
			        red = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
			        green = Integer.toHexString(rand.nextInt(86) + 170).toUpperCase();
		    		blue = Integer.toHexString(rand.nextInt(80) + 80).toUpperCase();
	        	}
	        }
	        
	        red = red.length()==1 ? "0" + red : red ;
	        green = green.length()==1 ? "0" + green : green ;
	        blue = blue.length()==1 ? "0" + blue : blue ;
	        
	        String color = red+green+blue;
	        return Integer.parseInt(color, 16);
	    }
		
		private float getRegenPerTick() {
			//System.out.println((ManaValue / 5) + ControlAbility);
			float Regen = (float) (Math.pow((ManaValue / 10) + (ControlAbility / 10), 1.00000000001) - (ManaValue / 10));
			return Regen;
		}

		public void levelUpper() 
		{
			MaxManaValue += ManaTalentValue + 1+rand.nextFloat() / GrowthAbility;
			proficiency+=5;
		}
		
		public void addAttributeValue()
		{
			if(ManaTalentValue > 0.11)
			proficiency++;
			
			if(proficiency > ManaTalentValue * 100)
			{
				ManaTalentValue -= 0.1;
				if(ManaTalentValue < 0.1)
					ManaTalentValue = 0.1F;
				proficiency = 0;
				
				ControlAbility += GrowthAbility / ControlAbility;
				ManaStrengh += GrowthAbility / ManaStrengh;
				GrowthAbility += ManaTalentValue / 1+rand.nextFloat();
			}
		}
		
		public void reduceMana(float mana)
		{
			if(player.isCreative())
				return;
			if(ManaValue >= mana * getPlayerArmorAttribute(1))
			{
				ManaValue -= mana * getPlayerArmorAttribute(1);
			}
			else 
			{
				ExtraManaValue -= (mana * getPlayerArmorAttribute(1) - ManaValue);
				ManaValue = 0;
			}
		}
		
		public void setColor(int color2, int color3, int color4, int wand)
		{
			this.color2 = color2;
			this.color3 = color3;
			this.color4 = color4;
			this.wand = wand;
		}
		
		public void setMagickData(int[] idList)
		{
			magickData = newMagickList(idList);
		}
		
	    private int[] newMagickList(int[] list)
	    {
	    	int max = 0;
	    	int slot = 0;
	    	int[] NewList = new int[list.length];
	    	for(int i = list.length - 1; i >= 0; i--)
	    	{
	    		for(int z = 0; z < list.length; z++)
	    		{
	    			if(list[z] >= max)
	    			{
	    				max = list[z];
	    				slot = z;
	    			}
	    		}
	    		NewList[i] = max;
				list[slot] = 0;
				slot = 0;
				max = 0;
	    	}
			return NewList;
	    }
		
		public void addMagickData(int id)
		{	
			if(hasMagick(id))
				return;
			int[] NewData = new int[magickData.length + 1];
			for(int i = 0; i < magickData.length; i++)
			{
				NewData[i] = magickData[i];
			}
			NewData[magickData.length] = id;
			magickData = NewData;
		}
		
		public int[] getAllMagickData()
		{
			return magickData;
		}
		
		public void addCoolDown(float CoolDown)
		{
			regenCooldown += CoolDown;
		}
		
		public float getMaxMana() {
			if(client)
				return MaxManaValue;
			if(getPlayerArmorAttribute(2) == 0)
				return MaxManaValue;
			return MaxManaValue * getPlayerArmorAttribute(2);
		}
		
		public float getMana() {
			return ManaValue + ExtraManaValue;
		}
		
		public float getManaTalent() {
			return ManaTalentValue;
		}
		public float getManaStrengh() {
			return ManaStrengh + ManaStrengh * getPlayerArmorAttribute(0);
		}
		public float getGrowth() {
			return GrowthAbility;
		}
		public float getControlAbility() {
			return ControlAbility + ControlAbility * getPlayerArmorAttribute(0);
		}
		
		public float getPlayerArmorAttribute(int type)
		{
			float MagickEnhance = 0;
			float MagickRelief = 0;
			float MagickUpperlimit = 0;
			if(type == 0)
			{
			for(int i = 0; i < 4; i++)
			{
				ItemStack stack = this.player.inventory.armorInventory.get(i);
				Item item = stack.getItem();
				if(item instanceof ArmorBase)
				{
					ArmorBase armor = (ArmorBase) item;
					MagickEnhance += armor.getMagicEnhance();
				}
			}
				return MagickEnhance;
			}
			else if(type == 1)
			{
			for(int i = 0; i < 4; i++)
			{
				ItemStack stack = this.player.inventory.armorInventory.get(i);
				Item item = stack.getItem();
				if(item instanceof ArmorBase)
				{
					ArmorBase armor = (ArmorBase) item;
					MagickRelief += armor.getMagicRelief();
				}
			}
			if(MagickRelief > 1)
				MagickRelief = 0.1F;
			return 1 - MagickRelief;
			}
			else if(type == 2)
			{
			for(int i = 0; i < 4; i++)
			{
				ItemStack stack = this.player.inventory.armorInventory.get(i);
				Item item = stack.getItem();
				if(item instanceof ArmorBase)
				{
					ArmorBase armor = (ArmorBase) item;
					MagickUpperlimit += armor.getMagicUpperlimit();
				}
			}
			if(MagickUpperlimit == 0)
				ExtraManaValue = 0;
			return MagickUpperlimit;
			}
			else
				return 0;
				
		}
		
		public void tick() {
			if(client)
				return;
			//ManaValue = 0;
			//regenCooldown = 30;
			//System.out.println(regenCooldown);
			if(Float.isNaN(ManaValue))
				ManaValue = 0;
			
			if(Float.isNaN(player.getHealth()))
				player.setHealth(0);
			
			if(regenCooldown <= 0) {
				regenCooldown = 0;
					ManaValue = Math.min(MaxManaValue, Math.min(getMaxMana(), getMana() + getRegenPerTick()));
					if(ManaValue == MaxManaValue)
					{
						ExtraManaValue = Math.min(getMaxMana() - ManaValue, Math.min(getMaxMana(), getMana() + getRegenPerTick()));
					}
					save();
			} else {
				
				regenCooldown -= (float)Math.sqrt(getGrowth()) -  1;
				save();
			}
			
			if(ManaValue + ExtraManaValue <= 0)
			{
				player.attackEntityFrom(ModDamage.MagickOverLoad, (float)Math.sqrt(0 - getMana()));
				ManaValue = 0;
				ExtraManaValue = 0;
			}
			float[] vaule = {0, this.getMaxMana()};
			float[] vaule1 = {1, this.getMana()};
			if(!client) {
			NetworkHandler.getNetwork().sendTo(new NetworPlayerMagickData(vaule), (EntityPlayerMP)player);
			NetworkHandler.getNetwork().sendTo(new NetworPlayerMagickData(vaule1), (EntityPlayerMP)player);
			}
		}

		public int getColor2() {
			return color2;
		}
		
		public int getColor3() {
			return color3;
		}
		
		public int getColor4() {
			return color4;
		}
		
		public int getWandStyle() {
			return wand;
		}
		
		public String[] addMagick(NBTTagCompound magick)
		{
			String[] string = new String[2];
			string[0] = "false";
			string[1] = "unknow";
			if(magick.hasKey("DELETE"))
			{
				getMagickList().removeTag(magick.getInteger("DELETE"));
				return string;
			}
			for(int i = 0; i < getMagickList().tagCount(); i++)
			{
				if(getMagickList().get(i).equals(magick))
					return string;
			}
			getMagickList().appendTag(magick);
			string[0] = "true";
			return string;
		}

		public void setMagickList(NBTTagList magick)
		{
			this.MagickList = magick;
		}
		
		public boolean hasMagick(int id)
		{
			for(int i = 0; i < magickData.length; i++)
			{
				if(magickData[i] == id)
				return true;
			}
			
			return false;
		}
		
		public static void setMagickData(EntityPlayer player, int[] magicklist)
		{
			NBTTagList list = new NBTTagList();
			for(int i = 0; i < magicklist.length; i++)
			{
				NBTTagInt z = new NBTTagInt(magicklist[i]);
				list.appendTag(z);
			}
			getData(player).setTag(Seccult.MagickData, list);
		}
		
		public static NBTTagList getMagickDataForPlayer(EntityPlayer player)
		{
			if(getData(player).hasKey(Seccult.MagickData))
				return getData(player).getTagList(Seccult.MagickData, 3);
			else return new NBTTagList();
		}
		
		public static NBTTagCompound getData(EntityPlayer player)
		{
			NBTTagCompound forgeData = player.getEntityData();
			if(!forgeData.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
				forgeData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
			}

			NBTTagCompound persistentData = forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			if(!persistentData.hasKey(Seccult.Data)) {
				persistentData.setTag(Seccult.Data, new NBTTagCompound());
			}
			return persistentData.getCompoundTag(Seccult.Data);
		}
		
		public NBTTagList getMagickList()
		{
			return MagickList == null ? MagickList = new NBTTagList(): MagickList;
		}
		
		public NBTTagList getAllMagick()
		{
			return getMagickList();
		}
		
		public NBTTagCompound getMagickAt(int i)
		{
			if(getMagickList().tagCount() >= i)
				return getMagickList().getCompoundTagAt(i);
			else
				return null;
		}
	}
}
