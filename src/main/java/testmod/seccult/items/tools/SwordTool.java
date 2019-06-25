package testmod.seccult.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;

public class SwordTool extends Item implements registerModel
{
	public static final DamageSource VOID = (new DamageSource("Void")).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();
	
	public SwordTool(String name )
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabsLoader.tab);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase target, EntityLivingBase attacker) {
		float hh = target.getMaxHealth();
		float hh2 = target.getHealth();
		float hh1 = attacker.getHealth();
		float hh3 = hh / 3;
		if(hh < 200) {
        	if(hh2 - hh1 > 0) {
          target.setHealth(hh2 - hh1);
        	}
        	else {
        		target.getCombatTracker().trackDamage(VOID, target.getHealth(), target.getHealth());
        		target.setHealth(0);
        		target.onDeath(DamageSource.causeMobDamage(attacker));
        	}
		}
		else {
			if(hh2 < hh3) {
				target.getCombatTracker().trackDamage(VOID, target.getHealth(), target.getHealth());
				target.setHealth(0);
				target.onDeath(DamageSource.causeMobDamage(attacker));
			}
			else {
				target.setHealth(hh2 - hh3);
			}
		}
		return false;
	}
	
	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer victim = (EntityPlayer) entity;
    		float hh = victim.getMaxHealth();
    		float hh2 = victim.getHealth();
    		float hh1 = player.getHealth();
    		float hh3 = hh / 3;
            if (victim.getHealth() > 0) {
        		if(hh < 200) {
                	if(hh2 - hh1 > 0) {
                  victim.setHealth(hh2 - hh1);
                	}
                	else {
                		victim.getCombatTracker().trackDamage(VOID, victim.getHealth(), victim.getHealth());
                		victim.setHealth(0);
                		victim.onDeath(DamageSource.causeMobDamage(player));
                	}
        		}
        		else {
        			if(hh2 < hh3) {
        				victim.getCombatTracker().trackDamage(VOID, victim.getHealth(), victim.getHealth());
        				victim.setHealth(0);
        				victim.onDeath(DamageSource.causeMobDamage(player));
        			}
        			else {
        				victim.setHealth(hh2 - hh3);
        			}
        		}
                return true;
            }
        }
        return false;
    }
	
	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
