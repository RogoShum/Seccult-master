package testmod.seccult.items.armor.Ocean;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import testmod.seccult.ModReclection;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.OceanArmor;

public class OceanHelmet extends OceanArmor{

	private int air;
	
	public OceanHelmet(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(itemStack.getItem() == ModItems.OCEAN_HELMET)
		{
			if (hasArmorSetItem(player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, itemStack) < 3) {
	        	itemStack.addEnchantment(Enchantments.RESPIRATION, 3);
	        }
		}
		
		if(!hasArmorSetItem(player)) 
		{
			air = 300;
			return;
		}
		try {
			if(player.world.handleMaterialAcceleration(player.getEntityBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D), Material.WATER, player))
			ModReclection.Entity_inWater(player, false);
			else
				ModReclection.Entity_inWater(player, true);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if(player.world.handleMaterialAcceleration(player.getEntityBoundingBox().grow(0.0D, -1.0D, 0.0D).offset(0, 1, 0).shrink(0.001D), Material.WATER, player))
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 201));
		
		if(player.capabilities.isCreativeMode)
			return;
		
        if (air == -20)
        {
            air = 0;
            for (int i = 0; i < 8; ++i)
            {
                float f2 = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                float f = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                float f1 = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                player.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, player.posX + (double)f2, player.posY + (double)f, player.posZ + (double)f1, player.motionX, player.motionY, player.motionZ);
            }

            player.attackEntityFrom(DamageSource.DROWN, 2.0F);
        }
		
		if(!player.isInWater())
		{
			for (int i = 0; i < 8; ++i)
            {
                float f2 = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                float f = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                float f1 = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                player.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, player.posX + (double)f2, player.posY + (double)f, player.posZ + (double)f1, player.motionX, player.motionY, player.motionZ);
            }
			this.air = 300;
			player.setAir(0);
		}
		else
		{
			player.fallDistance = 0;
            if (!player.canBreatheUnderwater() && !player.isPotionActive(MobEffects.WATER_BREATHING) && !player.capabilities.disableDamage)
            {
            	air--;
            }
		}
	}
	
	public int getAir()
	{
		return this.air;
	}
}
