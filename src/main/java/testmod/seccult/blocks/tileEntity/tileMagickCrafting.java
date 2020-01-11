package testmod.seccult.blocks.tileEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.NBTTools;
import testmod.seccult.Seccult;
import testmod.seccult.init.CraftingHelper;
import testmod.seccult.init.ModCrafting;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModCrafting.MagickRecipe;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class tileMagickCrafting extends TileEntity implements ITickable {
	private List<ItemStack> stackList = new ArrayList<ItemStack>();
	private int magickPower;
	private int magickPowerExtra;
	private HashMap<String, Float> playerSoul = new HashMap();
	public static final int MAX_MAGICKPOWER = 600;
	
	private int takeOutCold;
	private int secCold;
	public double randDouble;
	
	private int craftingTime;
	private int craftingTimeMax;
	private ItemStack[] loot;
	
	public tileMagickCrafting() {}
	
	public int getCraftingTime() {return this.craftingTime;}
	
	public int getMaxCraftingTime() {return this.craftingTimeMax;}
	
	@Override
	public void update() 
	{
		if(this.randDouble == 0D)
			this.randDouble = Seccult.rand.nextDouble() * 30;
		
		if(this.magickPowerExtra > 1 && this.magickPower < MAX_MAGICKPOWER)
		{
			this.magickPowerExtra--;
			this.magickPower++;
		}
		
		if(!this.playerSoul.isEmpty())
		{
			float vaule = 0 ;
			Set<String> idSet = this.playerSoul.keySet();
			
			for(String id : idSet)
			{
				vaule += this.playerSoul.get(id);
			}
			
			if(this.magickPower < (int)vaule * 50)
			{
				this.magickPower = Math.min(MAX_MAGICKPOWER, (int)vaule * 50);
				this.magickPowerExtra = 0;
			}
		}
		
		if(this.magickPower <= 0 && this.craftingTime <= 0)
			this.dropItem();
		
		if(takeOutCold > 0)
			takeOutCold--;
		
		if(secCold > 0)
			secCold--;
		
		if(this.craftingTime > 0 && (this.magickPowerExtra > 0 || this.magickPower > 0))
		{
			if(this.magickPowerExtra > 0)
				this.magickPowerExtra--;
			else if(this.magickPower > 0)
				this.magickPower--;

			this.craftingTime--;
			if(this.craftingTime == 0)
			{
				this.stackList.clear();
				for(int i = 0; i < this.loot.length; ++i)
				{
					this.stackList.add(this.loot[i]);
				}
				this.craftingTimeMax = 0;
				makeSuccessP(getPos().add(0, 0.5, 0));
			}
		}
	}
	
	public void makeSuccessP(BlockPos bPos)
	{
		for(int i = 0; i < 20 ; i++) {
            double d0 = (double)((float)bPos.getX() + 0.5 + this.world.rand.nextFloat());
            double d1 = (double)((float)bPos.getY() + 0.6 + this.world.rand.nextFloat());
            double d2 = (double)((float)bPos.getZ() + 0.5 + this.world.rand.nextFloat());
            double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
            double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
            double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
            
    		double[] vec = {d3/6, d4/6, d5/6};
    		double[] pos = {(d0 + bPos.getX() + 0.5) / 2.0D, (d1 + bPos.getY() + 0.6F) / 2.0D, (d2 + bPos.getZ() + 0.5) / 2.0D};
    		float[] color = {1.0F, 0, 0.5F};

    		NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, 0.25F, 0), 
    				new TransPoint(-12450, pos[0], pos[1], pos[2], 32), world);
    		
    		double[] vec1 = {d3 / 5, d4 / 5, d5 / 5};
    		double[] pos1 = {d0, d1, d2};

    		NetworkHandler.sendToAllAround(new NetworkEffectData(pos1, vec1, color, 0.1F, 2), 
    				new TransPoint(-12450, pos[0], pos[1], pos[2], 32), world);
		}
		
		doRingSound();
	}
	
	public int getPower()
	{
		return this.magickPower;
	}
	
	public boolean setPower(int power)
	{
		boolean flag = false;
		
		if(this.magickPower < MAX_MAGICKPOWER)
		{
			this.magickPower += power;
			if(this.magickPower > MAX_MAGICKPOWER)
			{
				this.magickPowerExtra = this.magickPower - MAX_MAGICKPOWER;
				this.magickPower = MAX_MAGICKPOWER;
			}
			flag = true;
		}
		
		return flag;
	}
	
	public void setPlayerSoulEnergy(EntityPlayer player, float vaule)
	{
		this.playerSoul.put(player.getName(), vaule);
	}
	
	public float getPlayerSoulEnergy(EntityPlayer player)
	{
		float soul = 0;
		if(!this.playerSoul.isEmpty() && this.playerSoul.containsKey(player.getName()))
		{
			soul = this.playerSoul.get(player.getName());
			this.playerSoul.remove(player.getName());
		}
		return soul;
	}
	
	public void pushItemStack(ItemStack stack)
	{
		ItemStack newS = stack.copy();
		newS.setCount(1);
		this.stackList.add(newS);
		stack.shrink(1);
	}
	
	public List<ItemStack> getStackList()
	{
		return stackList;
	}
	
	public ItemStack getItemStack()
	{
		if(!this.stackList.isEmpty() && (takeOutCold <= 0 || secCold > 15))
		{
			ItemStack stack = this.stackList.get(this.stackList.size()-1);
			this.stackList.remove(stack);
			takeOutCold+=5;
			secCold+=10;
			
			if(secCold > 15)
				secCold += 10;
			
			if(secCold > 100)
			{
				int i = secCold / 100;
				int c = 0;
				while(c < i && !this.stackList.isEmpty())
				{
					ItemStack stack_ = this.stackList.get(this.stackList.size()-1);
					this.stackList.remove(stack_);
					if(!this.world.isRemote)
						this.world.spawnEntity(new EntityItem(getWorld(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), stack_));
					c++;
				}
			}
			
			return stack;
		}
		
		return null;
	}
	
	public boolean tryCrafting()
	{
		boolean flag = false;
		ItemStack[] stacks = ModCrafting.getCraftingLoot(stackList);
		
		if(stacks != null && this.craftingTime <= 0)
		{
			this.craftingTime = this.stackList.size() * 20;
			this.craftingTimeMax = craftingTime;
			this.loot = stacks;
			flag = true;
			this.world.notifyBlockUpdate(pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 2);
		}
		
		return flag;
	}
	
	public void doRingSound()
	{
		this.world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.VOICE, 1F, this.world.rand.nextFloat() / 2);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("SoulHeart"))
		{
			this.playerSoul.clear();
			NBTTagList list = compound.getTagList("SoulHeart", 10);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				NBTTagCompound newTag = list.getCompoundTagAt(i);
				this.playerSoul.put(newTag.getString("Name"), newTag.getFloat("Vaule"));
			}
		}
		
		if(compound.hasKey("MagickPower"))
			this.magickPower = compound.getInteger("MagickPower");
		
		if(compound.hasKey("MagickPowerExtra"))
			this.magickPowerExtra = compound.getInteger("MagickPowerExtra");
		
		if(compound.hasKey("CraftingTime"))
			this.craftingTime = compound.getInteger("CraftingTime");
		
		if(compound.hasKey("CraftingTimeMax"))
			this.craftingTimeMax = compound.getInteger("CraftingTimeMax");
		
		if(compound.hasKey("Loots"))
		{
			NBTTagList stacks = compound.getTagList("Loots", 10);
			this.loot = new ItemStack[stacks.tagCount()];
			for(int i = 0; i < stacks.tagCount(); ++i)
			{
				ItemStack stack = NBTTools.getItemStackFromTag(stacks.getCompoundTagAt(i));
				this.loot[i] = stack;
			}
		}
		
		if(compound.hasKey("StackList"))
		{
			NBTTagList stacks = compound.getTagList("StackList", 10);
			for(int i = 0; i < stacks.tagCount(); ++i)
			{
				ItemStack stack = NBTTools.getItemStackFromTag(stacks.getCompoundTagAt(i));
				this.stackList.add(stack);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if(!this.playerSoul.isEmpty()) {
			NBTTagList tag = new NBTTagList();
			
			Set<String> idSet = this.playerSoul.keySet();
			
			for(String id : idSet)
			{
				NBTTagCompound newTag = new NBTTagCompound();
				newTag.setString("Name", id);
				newTag.setFloat("Vaule", this.playerSoul.get(id));
			}

			compound.setTag("SoulHeart", tag);
		}
		
		compound.setInteger("MagickPower", magickPower); compound.setInteger("MagickPowerExtra", magickPowerExtra);
		compound.setInteger("CraftingTime", craftingTime); compound.setInteger("CraftingTimeMax", craftingTimeMax);
		
		if(!this.stackList.isEmpty())
		{
			NBTTagList stacks = new NBTTagList();
			for(int i = 0; i < this.stackList.size(); ++i)
			{
				NBTTagCompound stack = NBTTools.setItemStackToTag(this.stackList.get(i));
				stacks.appendTag(stack);
			}
			compound.setTag("StackList", stacks);
		}
		
		if(this.loot != null)
		{
			NBTTagList stacks = new NBTTagList();
			for(int i = 0; i < this.loot.length; ++i)
			{
				NBTTagCompound stack = NBTTools.setItemStackToTag(this.loot[i]);
				stacks.appendTag(stack);
			}
			compound.setTag("Loots", stacks);
		}
		
		return compound;
	}
	
    protected NBTTagList newFloatNBTList(float... numbers)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (float f : numbers)
        {
            nbttaglist.appendTag(new NBTTagFloat(f));
        }

        return nbttaglist;
    }
	
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
    
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
    	NBTTagCompound nbtTag = this.writeToNBT(new NBTTagCompound());
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	NBTTagCompound tag = pkt.getNbtCompound();
    	this.readFromNBT(tag);
    }
    
    @Override
    public void invalidate() {
    	dropItem();
    	super.invalidate();
    }
    
    public void dropItem()
    {
    	for(ItemStack stack : this.stackList)
    	{
    		EntityItem item = new EntityItem(this.world, this.pos.getX(), this.pos.getY() + 0.5, this.pos.getZ(), stack);
			if(!this.world.isRemote)
				this.world.spawnEntity(item);
    	}
    	this.stackList.clear();
    }
}
