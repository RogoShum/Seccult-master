package testmod.seccult.entity.livings.water;

import java.util.Random;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
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
        this.getEntityAttribute(SWIM_SPEED).setBaseValue(1);
	}
	
	@Override
	protected Item getDropItem() {
		return Items.SLIME_BALL;
	}
	
	@Override
	protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {

        if (getDropItem() != null)
        {
            this.dropFewItems(wasRecentlyHit, lootingModifier);
        }

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
