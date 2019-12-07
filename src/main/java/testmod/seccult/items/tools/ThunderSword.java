package testmod.seccult.items.tools;

import java.util.List;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.items.ItemMagickCore;
import testmod.seccult.items.ItemMagickable;

public class ThunderSword  extends ItemMagickable
{

	public ThunderSword(String name) {
		super(name);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		
		if(entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) entityIn;
		
			if(living.getHeldItemMainhand() == stack)
			{
				preAttack(stack, living, EnumHand.MAIN_HAND);
			}
		
			if(living.getHeldItemOffhand() == stack)
			{
				preAttack(stack, living, EnumHand.OFF_HAND);
			}
		}
	}
	
	private void preAttack(ItemStack stack, EntityLivingBase player, EnumHand hand)
	{
		List<Entity> entity = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(4));
		Entity e = null;
		for(int i = 0; i < entity.size(); i++)
		{
			if((e == null && entity.get(i) instanceof EntityLivingBase) || (entity.get(i) instanceof EntityLivingBase && e.getDistance(player) > entity.get(i).getDistance(player) && entity.get(i).isEntityAlive()))
				e = entity.get(i);
		}

		if(e != null && e.isEntityAlive())
		{
				AttackEntity(stack, player.world, e, player, hand);
		}
		else
		{
			for(int i = 0; i < entity.size(); i++)
			{
				if(e == null || (e.getDistance(player) > entity.get(i).getDistance(player)))
					e = entity.get(i);
			}
			
			if(e != null && !(e instanceof EntityItem) && !(e instanceof EntityXPOrb) && !(e instanceof EntityItemFrame) && !(e instanceof EntityMinecart) && e.isEntityAlive())
			{
				AttackEntity(stack, player.world, e, player, hand);
			}
		}
	}
	
	private void AttackEntity(ItemStack stack, World worldIn, Entity e, EntityLivingBase player, EnumHand hand)
	{
		this.faceEntity(player, e, 5, 5);
		{
			if(ItemMagickCore.getMagick(stack) != null)
			{
				ItemMagickCore.getMagick(stack).setMagickAttribute(player, e, null, (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(), 2);
			}
			e.hurtResistantTime = -1;
			player.swingArm(hand);
			e.attackEntityFrom(DamageSource.causeMobDamage(player), (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
		}
	}
	
    public void faceEntity(Entity entity, Entity entityIn, float maxYawIncrease, float maxPitchIncrease)
    {
        double d0 = entityIn.posX - entity.posX;
        double d2 = entityIn.posZ - entity.posZ;
        double d1;

        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (entity.posY + (double)entity.getEyeHeight());
        }
        else
        {
            d1 = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D - (entity.posY + (double)entity.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
        entity.rotationPitch = this.updateRotation(entity.rotationPitch, f1, maxPitchIncrease);
        entity.rotationYaw = this.updateRotation(entity.rotationYaw, f, maxYawIncrease);
    }
	
    private float updateRotation(float angle, float targetAngle, float maxIncrease)
    {
        float f = MathHelper.wrapDegrees(targetAngle - angle);

        if (f > maxIncrease)
        {
            f = maxIncrease;
        }

        if (f < -maxIncrease)
        {
            f = -maxIncrease;
        }

        return angle + f;
    }
    
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if(equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 9, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 3, 0));
        }
        
        if(equipmentSlot == EntityEquipmentSlot.OFFHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 9, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 3, 0));
        }
        return multimap;
    }
}
