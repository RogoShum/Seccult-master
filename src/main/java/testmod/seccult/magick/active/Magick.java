package testmod.seccult.magick.active;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public abstract class Magick {
	private final String nbtName;
	private final String shortName;
	private final boolean hasDetailedText;
	
	Random rand = new Random();
	
	protected Entity e;
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
	
	public Magick(String nbtName, String shortName, boolean hasDetailedText) {
		this.nbtName = nbtName;
		this.shortName = shortName;
		this.hasDetailedText = hasDetailedText;
	}
	
	public void setMagickAttribute(Entity enti, Entity e, BlockPos goal, float power, float attribute)
	{
		isSucceed = true;
		this.e = enti;
		block = goal;
		entity = e;
		strengh = power;
		this.attribute = attribute;
		if(this.e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) this.e;
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

	public Magick(String name, boolean hasDetailedText)
	{
		this(name, name, hasDetailedText);
	}

	public String getShortName() {
		return shortName;
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
		return shortName;
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
		e.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, e.posX, e.posY + e.height / 2, e.posZ, 0, 0, 0);
	}
	
	private void doExplosion(float r)
	{
		e.world.createExplosion(null, e.posX, e.posY, e.posZ, r, true);
	}
	
	protected void doMagick()
	{
		reduceMana();
		if(entity != null)
		toEntity();
		if(block != null)
		toBlock();
		
		data.addCoolDown((float)Math.sqrt(strengh) + 20);
	}
	
	abstract void toEntity();
	
	abstract void toBlock();
	
	abstract void MagickFX();
	
	public abstract int getColor();
	
	protected void reduceMana()
	{
		data.reduceMana(strengh);
	}
}
