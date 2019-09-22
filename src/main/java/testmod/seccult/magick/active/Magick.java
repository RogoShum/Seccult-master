package testmod.seccult.magick.active;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.init.ModMagicks;

public abstract class Magick implements Cloneable, doMagickNeedAtrribute{
	private final String nbtName;
	private final boolean hasDetailedText;
	
	Random rand = new Random();
	
	protected Entity player;
	protected Entity entity;
	protected BlockPos block;
	protected boolean doBlock;
	protected boolean doEntity;
	protected PlayerData data;
	
	protected float plo;
	protected boolean isSucceed;
	protected float strengh;
	protected float attribute;
	protected float cost;
	
	public float strenghCost;
	public float attributeCost;
	
	protected int color;
	protected float RGB[] = new float[3]; 
	
	public Magick(String nbtName, boolean hasDetailedText, float cost1, float cost2) {
		this.nbtName = nbtName;
		this.hasDetailedText = hasDetailedText;
		this.strenghCost = cost1;
		this.attributeCost = cost2;
		ModMagicks.addMagick(this);
		
			RGB[0] = (float)(getColor() >> 16 & 255) / 255.0F;
			RGB[1] = (float)(getColor() >> 8 & 255) / 255.0F;
			RGB[2] = (float)(getColor() & 255) / 255.0F;
	}
	
	public void setMagickAttribute(Entity Iplayer, Entity e, BlockPos goal, float power, float attribute)
	{
		this.setMagickAttribute(Iplayer, e, goal, power, attribute, 0);
	}
	
	public void setMagickAttribute(Entity Iplayer, Entity e, BlockPos goal, float power, float attribute, float cost)
	{
		isSucceed = true;
		this.player = Iplayer;
		block = goal;
		entity = e;

		this.strengh = power;
		this.attribute = attribute;
		this.cost = cost;
		
		if(this.player instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) this.player;
			
		data = PlayerDataHandler.get(player);
		
		if(strengh > data.getManaStrengh() && !player.isCreative())
		{
			plo += strengh - data.getManaStrengh();
			isSucceed = false;
		}
		
		if(this.attribute > data.getControlAbility() && !player.isCreative())
		{
			plo += this.attribute - data.getControlAbility();
			isSucceed = false;
		}
		
		if(cost == 0)
			isSucceed = true;
		
		if(isSucceed && data.getMana() > cost)
			doMagick();
		else if(player.isCreative())
			doMagick();
		else if(!isSucceed)
			failedToDoMagick(plo / 10);
		else if(data.getMana() > cost)
			createFailedFX();
		}
		else
		doMagick();
	}

	public String getNbtName() {
		return nbtName;
	}
	
	public boolean hasDetailedText()
	{
		return hasDetailedText;
	}

	@Override
	public String toString() {
		return nbtName;
	}
	
	public void setMagickType() 
	{
		
	}
	
	private void failedToDoMagick(float s)
	{
		doExplosion(s);
		createFailedFX();
		reduceMana();
	}
	
	private void createFailedFX() {
		player.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, player.posX, player.posY + player.height / 2, player.posZ, 0, 0, 0);
	}
	
	private void doExplosion(float r)
	{
		player.world.createExplosion(null, player.posX, player.posY, player.posZ, r, false);
	}
	
	protected void doMagick()
	{
		if(entity != null)
		toEntity();
		if(block != null)
		toBlock();
		
		if(data == null)
			return;
		reduceMana();
		if(!data.player.isCreative())
		data.addCoolDown((float)Math.sqrt(strengh) * 10);
	}
	
	abstract void toEntity();
	
	abstract void toBlock();
	
	abstract void MagickFX();
	
	public abstract int getColor();
	
	public float[] getRGB()
	{
		return RGB;
	}
	
	protected void reduceMana()
	{
		data.reduceMana(cost);
	}
	
    @Override  
    public Magick clone() {  
        Magick magick = null;  
        try{  
        	magick = (Magick)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return magick;  
    }  
}
