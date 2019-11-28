package testmod.seccult.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.items.armor.ArmorBase;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.network.CNetworkTransFloat;
import testmod.seccult.network.NetworPlayerMagickData;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerTransMagickToClient;
import testmod.seccult.network.TransPoint;

public class PlayerSpellReleaseTool {
	
	private static HashMap<Integer, PlayerSpellTool> playerData = new HashMap();
	
	public static PlayerSpellTool get(EntityPlayer player) {
		int key = getKey(player);
		if(!playerData.containsKey(key))
			playerData.put(key, new PlayerSpellTool(player));

		PlayerSpellTool data = playerData.get(key);
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
		Iterator<Entry<Integer, PlayerSpellTool>> it = playerData.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Integer, PlayerSpellTool> item = it.next();
			PlayerSpellTool d = item.getValue();
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
	
	public static class PlayerSpellTool{
		static Random rand = new Random();
		
		private static final String TAG_SPELL_SELECTED = "SpellSelected";
		
		private int cycleRealse;
		private int spellSelected;
		private float[] spellColor = new float[3];
		private String spellName = "";
		
		private PlayerData data;
		private NBTTagList MagickList = new NBTTagList();
		public EntityPlayer player;
		
		private final boolean client;
		
		public PlayerSpellTool(EntityPlayer player) {
			this.player = player;

			client = player.getEntityWorld().isRemote;

			load();
		}
		
		public void load() {
			if(!client) {
				if(this.player != null) {
					NBTTagCompound cmp = getDataCompoundForPlayer(this.player);
					readFromNBT(cmp);
					data = PlayerDataHandler.get(player);
					MagickList = data.getAllMagick();
				}
			}
		}
		
		public void readFromNBT(NBTTagCompound cmp) {
			spellSelected = cmp.getInteger(TAG_SPELL_SELECTED);
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
			try{
				cmp.setFloat(TAG_SPELL_SELECTED, spellSelected);
			}
			catch(Exception e)
			{
				System.out.println("sth. went wrong");
			}
		}
		
		public void tick() {
			if(cycleRealse > 0 && !client)
			{
				MagickCompiler ma = new MagickCompiler();
				if(player.getHeldItemMainhand().getItem() == ModItems.ELDER_WAND || player.getHeldItemOffhand().getItem() == ModItems.ELDER_WAND)
					ma.dontCost = true;
	        	ma.pushMagickData(MagickList.getCompoundTagAt(spellSelected), player);
				cycleRealse--;
				
	            double[] pos = new double[3], vec = new double[3];
				pos[0] = player.posX - player.width / 2;
				pos[1] = player.posY + player.height / 2;
				pos[2] = player.posZ - player.width / 2;
				NetworkHandler.sendToAllAround(new CNetworkTransFloat(this.SpellColor(), player, 1, 3), 
	            		new TransPoint(player.dimension, pos[0], pos[1], pos[2], 32), player.world);
	            NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, this.SpellColor(), 0.3F, 100), 
	            		new TransPoint(player.dimension, pos[0], pos[1], pos[2], 32), player.world);
			}
			else if(cycleRealse > 0)
				cycleRealse-=3;
			
			if(data != null && MagickList != data.getAllMagick())
				MagickList = data.getAllMagick();
		}
		
		public void switchSpell()
		{
			this.spellSelected += 1; 
			
			if(MagickList != null && spellSelected >= MagickList.tagCount())
			{
				spellSelected = 0;
			}
			sendColor();
		}
		
		public void releaseSpell()
		{
			if(client)
				return;
			cycleRealse++;
			
			double[] pos = new double[3];
			pos[0] = player.posX - player.width / 2;
			pos[1] = player.posY + player.height / 2;
			pos[2] = player.posZ - player.width / 2;
			float cycle[] = new float[1];
			NetworkHandler.sendToAllAround(new CNetworkTransFloat(cycle, player, 2, 1), 
            		new TransPoint(player.dimension, pos[0], pos[1], pos[2], 32), player.world);
		}
		
		public void addCycleTime()
		{
			cycleRealse+=5;
		}
		
		public void selectSpell(int i)
		{
			if(MagickList != null && i < MagickList.tagCount())
			{
				this.spellSelected = i; 
				sendColor();
			}
		}

		private void sendColor()
		{
			double[] pos = new double[3], vec = new double[3];
			pos[0] = player.posX - player.width / 2;
			pos[1] = player.posY + player.height / 2;
			pos[2] = player.posZ - player.width / 2;
			NetworkHandler.sendToAllAround(new CNetworkTransFloat(this.SpellColor(), player, 1, 3),
            		new TransPoint(player.dimension, pos[0], pos[1], pos[2], 32), player.world);
            NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, this.SpellColor(), 0.3F, 100), 
            		new TransPoint(player.dimension, pos[0], pos[1], pos[2], 32), player.world);
		}
		
		public void setSpellColor(float[] color)
		{
			spellColor = color;
		}
		
		public float[] getSpellColor()
		{
			return spellColor;
		}
		
		public int getCycle()
		{
			return cycleRealse;
		}
		
		public String getMagickName()
		{
			if(MagickList!=null)
				spellName = MagickList.getCompoundTagAt(spellSelected).getString("Magick_Name");
			return this.spellName;
		}
		
		public int getSpellColorInt()
		{
	        String red;
	        String green;
	        String blue;

	        red = Integer.toHexString((int) (spellColor[0]*255)).toUpperCase();
    		green = Integer.toHexString((int) (spellColor[1]*255)).toUpperCase();
    		blue = Integer.toHexString((int) (spellColor[2]*255)).toUpperCase();
	        
	        red = red.length()==1 ? "0" + red : red ;
	        green = green.length()==1 ? "0" + green : green ;
	        blue = blue.length()==1 ? "0" + blue : blue ;
	        
	        String color = red+green+blue;

	        int i = Integer.parseInt(color, 16);
	        return i;
		}
		
		private float[] SpellColor()
		{
			float[] color = new float[3];
			
			if(MagickList == null)
				return color;
			
			NBTTagCompound tag = MagickList.getCompoundTagAt(spellSelected);
			for(int i = 0; i < tag.getTagList("Magick", 10).tagCount(); i++)
			{
				NBTTagList LoadMagick = tag.getTagList("Magick", 10);
				NBTTagCompound MagickNBT = LoadMagick.getCompoundTagAt(i);
				
				Magick magick = ModMagicks.getMagickFromName(
						ModMagicks.GetMagickStringByID(
						MagickNBT.getInteger("Magick")));
				
				float[] mColor = magick.getRGB();

				color[0] += mColor[0] / tag.getTagList("Magick", 10).tagCount();
				color[1] += mColor[1] / tag.getTagList("Magick", 10).tagCount();
				color[2] += mColor[2] / tag.getTagList("Magick", 10).tagCount();
				
			}

			return color;
		}
	}
}
