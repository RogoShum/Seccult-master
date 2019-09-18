package testmod.seccult.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.init.ModItems;

public class OceanArmor extends MagickArmor{
	public static int right_height = 39;
	public OceanArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(1, 50, 1);
	}
	
	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.OCEAN_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.OCEAN_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.OCEAN_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.OCEAN_BOOTS);
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
