package testmod.seccult.items.armor;

import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
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
import testmod.seccult.potions.ModPotions;

public class OceanArmor extends FunctionArmor{
	private int air;
	public static int right_height = 39;
	public OceanArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(1, 50, 1);
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
	
	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.OCEAN_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.OCEAN_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.OCEAN_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.OCEAN_BOOTS);
	}

	public int getAir()
	{
		return this.air;
	}
	
	public float reduceMotion(float value, float limit)
	{
		float reduce = value;
		if(reduce > limit)
			reduce = limit;
		if(reduce < -limit)
			reduce = -limit;
		return reduce;
	}
	
	/*@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		return new OceanModel();
	}*/
	
	public class OceanModel extends ModelBiped{
		
	    public OceanModel()
	    {
	        this(0.0F);
	    }

	    public OceanModel(float modelSize)
	    {
	        this(modelSize, 0.0F, 64, 32);
	    }
		
	    public OceanModel(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn)
	    {
	        this.leftArmPose = ModelBiped.ArmPose.EMPTY;
	        this.rightArmPose = ModelBiped.ArmPose.EMPTY;
	        this.textureWidth = textureWidthIn;
	        this.textureHeight = textureHeightIn;
	        this.bipedHead = new ModelRenderer(this, 0, 0);
	        this.bipedHead.addBox(-4.0F, -6.0F, -10.0F, 8, 8, 8, modelSize);
	        this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
	        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
	        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
	        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
	        this.bipedBody = new ModelRenderer(this, 16, 16);
	        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
	        this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
	        this.bipedRightArm = new ModelRenderer(this, 40, 16);
	        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
	        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
	        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
	        this.bipedLeftArm.mirror = true;
	        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
	        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
	        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
	        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
	        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
	        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
	        this.bipedLeftLeg.mirror = true;
	        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
	        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);
	    }
		
	}
}
