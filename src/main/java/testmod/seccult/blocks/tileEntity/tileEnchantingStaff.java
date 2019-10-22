package testmod.seccult.blocks.tileEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.client.FX.PentagonFX;
import testmod.seccult.client.FX.StarFX;
import testmod.seccult.init.ModSounds;
import testmod.seccult.items.ItemMagickable;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.magick.magickState.StateManager;

public class tileEnchantingStaff extends TileEntity implements ITickable {
	private ItemStack stack = null;
	private EntityItem entityItem;
	private NBTTagCompound prveTag;
	
	private int magickPower;
	private int powerLose;
	private int magickCost;
	private float[] magickColor;
	private String MagickType;
	
	@SuppressWarnings("deprecation")
	public tileEnchantingStaff() {
		GameRegistry.registerTileEntity(this.getClass(), Seccult.MODID + ":enchanting_staff");
	}
	
	@Override
	public void update() 
	{
		if(this.stack != null && this.entityItem == null)
		{
			EntityItem item = new EntityItem(this.world, this.pos.getX(), this.pos.getY() + 0.5, this.pos.getZ(), stack);
			item.noClip = true;
			if(this.world.isRemote)
				this.world.spawnEntity(item);
			this.entityItem = item;
		}
		else if(this.stack == null && this.entityItem != null)
		{
			this.entityItem.setDead();
			this.entityItem = null;
		}
		
		if(this.entityItem != null)
		{
			entityItem.noClip = true;
			this.entityItem.setPosition(this.pos.getX() + 0.5, this.pos.getY() + 0.7, this.pos.getZ() + 0.5);
		}
		
		if(this.stack != null && !this.stack.getTagCompound().equals(this.prveTag))
		{
			if(this.entityItem != null)
				this.entityItem.setDead();
			this.entityItem = null;
			this.prveTag = this.stack.getTagCompound().copy();
		}
		

			if(this.powerLose > 0)
				this.powerLose--;
		
			if(this.powerLose <= 0)
			{
				if(this.magickPower > 0)
					this.magickPower--;
				this.powerLose = this.magickCost;
			}
			
			if(this.magickPower > 10)
			{
				if(this.magickColor != null)
					makeParticle(getPos());
			
				if(this.magickColor != null && this.stack != null)
					makeParticleTo(getPos());
			}
	}

	public void makeParticle(BlockPos bPos)
	{
			Minecraft mc = Minecraft.getMinecraft();
        	Particle par = new PentagonFX(mc.world, bPos.getX() + 0.5, bPos.getY() + 0.36F, bPos.getZ() + 0.5, 0, 0, 0, (float)this.magickPower/(float)5000);
			par.setRBGColorF(this.magickColor[0], this.magickColor[1], this.magickColor[2]);
			mc.effectRenderer.addEffect(par);
	}
	
	public void makeParticleTo(BlockPos bPos)
	{
		Minecraft mc = Minecraft.getMinecraft();
    	Particle par = new LightFX(mc.world, bPos.getX() + 0.5, bPos.getY() + 0.36F, bPos.getZ() + 0.5, 0, 0.1, 0, 0.3F);
		par.setRBGColorF(this.magickColor[0], this.magickColor[1], this.magickColor[2]);
		mc.effectRenderer.addEffect(par);
	}
	
	public void makeSuccessP(BlockPos bPos)
	{
		Minecraft mc = Minecraft.getMinecraft();
		for(int i = 0; i < 20 ; i++) {
            double d0 = (double)((float)bPos.getX() + 0.5 + mc.world.rand.nextFloat());
            double d1 = (double)((float)bPos.getY() + 0.6 + mc.world.rand.nextFloat());
            double d2 = (double)((float)bPos.getZ() + 0.5 + mc.world.rand.nextFloat());
            double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
            double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
            double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        	Particle me = new LightFX(mc.world, (d0 + bPos.getX() + 0.5) / 2.0D, (d1 + bPos.getY() + 0.6F) / 2.0D, (d2 + bPos.getZ() + 0.5) / 2.0D, d3/6, d4/6, d5/6, 2F);
        	me.setRBGColorF(this.magickColor[0], this.magickColor[1], this.magickColor[2]);
        	Particle smoke = new StarFX(mc.world, d0, d1, d2, d3 / 5, d4 / 5, d5 / 5, 0.5F);
        	Minecraft.getMinecraft().effectRenderer.addEffect(me);
        	Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
			}
		
		doRingSound();
	}
	
	public void doRingSound()
	{
		this.world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.VOICE, 1F, this.world.rand.nextFloat() / 2);
	}
	
	public boolean putMagickableIn(ItemStack stack)
	{
		if(this.stack == null)
		{
			this.stack = stack.copy();
			if(!this.stack.hasTagCompound())
				this.stack.setTagCompound(new NBTTagCompound());
			this.prveTag = this.stack.getTagCompound().copy();
			return true;
		}
		else
			return false;
	}
	
	public boolean trandMagick(Magick magick, int magickPower)
	{
		if(this.stack != null)
		{
			if(this.MagickType != magick.getNbtName())
			{
				this.MagickType = magick.getNbtName();
				
				this.magickPower=0;
			}

			this.magickPower += magickPower;
			
			this.magickColor = magick.getRGB();
			this.magickCost = (int) magick.strenghCost;
			this.powerLose = (int) (magick.strenghCost * 10);
			
			if(this.magickPower >= 5000)
			{
				this.magickPower = 0;
				makeSuccessP(getPos());
				this.world.notifyBlockUpdate(pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 2);
				return ItemMagickable.storeMagick(stack, magick);
			}
			this.world.notifyBlockUpdate(pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 2);
			return true;
		}
		return false;
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	public void getMagickable()
	{
		if(this.stack != null)
		{
			EntityItem item = new EntityItem(this.world, this.pos.getX(), this.pos.getY() + 0.5, this.pos.getZ(), stack);
			if(!this.world.isRemote)
				this.world.spawnEntity(item);
			this.stack = null;
			if(this.entityItem != null)
				this.entityItem.setDead();
			this.entityItem = null;
			this.prveTag = null;
			
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("StoredTag"))
		{
			NBTTagCompound tag = compound.getCompoundTag("StoredTag");
			Item item = Item.getByNameOrId(tag.getString("id"));
			ItemStack stack = new ItemStack(item, tag.getInteger("Count"), tag.getInteger("Damege"));
			if(tag.hasKey("tag"))
				stack.setTagCompound(tag.getCompoundTag("tag"));
			else
				stack.setTagCompound(new NBTTagCompound());
			this.stack = stack.copy();
		}
		
		if(compound.hasKey("magickPower"))
			this.magickPower = compound.getInteger("magickPower");
		
		if(compound.hasKey("magickCost"))
			this.magickCost = compound.getInteger("magickCost");
		
		if(compound.hasKey("powerLose"))
			this.powerLose = compound.getInteger("powerLose");
		
		if(compound.hasKey("magickColor"))
		{
			NBTTagList colorList = compound.getTagList("magickColor", 5);
			float[] c = new float[3];
			for(int i = 0; i< colorList.tagCount(); ++i)
			{
				c[i] = colorList.getFloatAt(i);
			}
			this.magickColor = c;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

			if(stack != null) {
				NBTTagCompound nbt = new NBTTagCompound();
				ItemStack item = stack;
				ResourceLocation id = item.getItem().getRegistryName();
				if(id.equals(Items.AIR.getRegistryName()))
					return compound;
				nbt.setString("id", id.toString());
				nbt.setInteger("Count", item.getCount());
				if(item.getCount() == 0)
					return compound;
				nbt.setInteger("Damege", item.getItemDamage());
				if(item.hasTagCompound())
					nbt.setTag("tag", item.getTagCompound());
				
				compound.setTag("StoredTag", nbt);
				compound.setInteger("magickPower", this.magickPower);
				compound.setInteger("magickCost", this.magickCost);
				compound.setInteger("powerLose", this.powerLose);
				if(this.magickColor != null)
				compound.setTag("magickColor", this.newFloatNBTList(this.magickColor[0], this.magickColor[1], this.magickColor[2]));
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
		this.getMagickable();
    	super.invalidate();
    }
    
}
