package testmod.seccult.entity.livings.water;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityJellyfish extends EntitySquid{
	private float swing;
	private boolean swingUp;
	
	public EntityJellyfish(World worldIn) {
		super(worldIn);
		float size = rand.nextFloat();
		if(size > 0.6F) size = 0.6F;
		this.setSize(size, size);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(swingUp)
		{
				swing++;
		}
		else
		{
				swing--;
		}
		
		if(swing > 90)
			swingUp = false;
		else if(swing <= 14)
			swingUp = true;
	}
	
	public float getSwing()
	{
		return this.swing;
	}
	
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }
    
    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 15.0F;
    }
}
