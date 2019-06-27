package testmod.seccult.magick.active;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.init.ModMagicks;

public abstract class Magick implements Cloneable{
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
	
	protected int color;
	protected float RGB[] = new float[3]; 
	
	public Magick(String nbtName, boolean hasDetailedText) {
		this.nbtName = nbtName;
		this.hasDetailedText = hasDetailedText;
		ModMagicks.addMagick(this);
		
			RGB[0] = (float)(getColor() >> 16 & 255) / 255.0F;
			RGB[1] = (float)(getColor() >> 8 & 255) / 255.0F;
			RGB[2] = (float)(getColor() & 255) / 255.0F;
	}
	
	public void setMagickAttribute(Entity Iplayer, Entity e, BlockPos goal, float power, float attribute)
	{
		isSucceed = true;
		this.player = Iplayer;
		block = goal;
		entity = e;
		
		strengh = power;
		this.attribute = attribute;
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
		
		if(isSucceed && data.getMana() > strengh)
			doMagick();
		else if(!isSucceed)
			failedToDoMagick(plo);
		else if(data.getMana() > strengh)
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
		reduceMana();
	}
	
	private void createFailedFX() {
		player.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, player.posX, player.posY + player.height / 2, player.posZ, 0, 0, 0);
	}
	
	private void doExplosion(float r)
	{
		player.world.createExplosion(null, player.posX, player.posY, player.posZ, r, true);
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
		data.addCoolDown((float)Math.sqrt(strengh) + 20);
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
		data.reduceMana(strengh);
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
