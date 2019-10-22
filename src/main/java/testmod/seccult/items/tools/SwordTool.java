package testmod.seccult.items.tools;

import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.items.ItemMagickable;
import testmod.seccult.magick.active.Magick;

public class SwordTool extends ItemMagickable
{
	private final float attackDamage;
	private final int harvestLevel;
	private final int enchantability;
	private final float useSpeed;
	
	public SwordTool(String name, int harvestLevel, int maxUses, float useSpeed, float attackDamage, int enchantability)
    {
		super(name);
        this.harvestLevel = harvestLevel;
        this.setMaxDamage(maxUses);
        this.useSpeed = useSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
	}
 
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		
		return nberHit(stack, target, attacker);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		return this.harvestLevel;
	}
	
	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!entity.world.isRemote && entity instanceof EntityLivingBase) {
          return nberHit(stack, (EntityLivingBase)entity, player);
        }
 
        return false;
    }
	
	public boolean nberHit(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		Magick magick = ItemMagickable.getMagick(stack);
		if(magick != null)
		{
			if(attacker instanceof EntityPlayer)
			{
				int cost = (int) (magick.strenghCost * this.attackDamage / 5);
				PlayerData data = PlayerDataHandler.get((EntityPlayer)attacker);
				//if(data.getMana() > cost)
				{
					magick.setMagickAttribute(attacker, target, null, attackDamage, harvestLevel);
					stack.damageItem(1, attacker);
					return true;
				}
			}
			else if(attacker instanceof EntityBase)
			{
				stack.damageItem(1, attacker);
				magick.setMagickAttribute(attacker, target, null, attackDamage, harvestLevel);
				return true;
			}
		}
		
		return false;
	}
	
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }

        return true;
    }
	
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.useSpeed, 0));
        }

        return multimap;
    }
	
    @Override
    public int getItemEnchantability() {
    	return this.enchantability;
    }
    
	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
