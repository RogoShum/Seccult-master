package testmod.seccult.potions;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionReflect extends PotionMod {
	//private static final Random rand = new Random();
	
	public PotionReflect() {
		super("riiflect", false, 0XFFAB4B, 0);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
	
	@SubscribeEvent
	public void onEntityAttacked(LivingHurtEvent event) {
		Entity attacker = event.getSource().getTrueSource();
		if(event.getEntityLiving() instanceof EntityLivingBase) {
			EntityLivingBase player = (EntityLivingBase) event.getEntityLiving();
			boolean rii = player.isPotionActive(ModPotions.reflect);
			boolean kx = player.isPotionActive(MobEffects.RESISTANCE);
			if(rii && kx && attacker != null && attacker instanceof EntityLivingBase && !(attacker.equals(player))) {
				boolean kxl = player.getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier() >= 5 ;
				if(kxl) {
				event.setCanceled(true);
				attacker.attackEntityFrom(event.getSource(), event.getAmount());
				EntityLivingBase mob = (EntityLivingBase) attacker;
				mob.addPotionEffect(new PotionEffect(ModPotions.lostmind, 20, 0));
				return;
				}
			}
			
			else if(player.isPotionActive(ModPotions.reflect) && attacker != null && attacker instanceof EntityLivingBase && !(attacker.equals(player))) {
				attacker.attackEntityFrom(event.getSource(), event.getAmount());
				EntityLivingBase mob = (EntityLivingBase) attacker;
				mob.addPotionEffect(new PotionEffect(ModPotions.lostmind, 20, 0));
			}
		}
	}
	
	/*@SubscribeEvent
	public void onEntityGetArmor(LivingUpdateEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player= (EntityPlayer) event.getEntityLiving();
			if(hasArmorSet(player) && player.ticksExisted % 200 == 0){
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 2));
			}
		}
	}
	
	@SubscribeEvent
	public void onDead(LivingDeathEvent event) {
		if(event.getEntityLiving() instanceof IMob &&  event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityLivingBase mon = (EntityLivingBase) event.getEntityLiving();
			mon.world.spawnEntity(new EntityFireworkRocket(mon.world, mon.lastTickPosX, mon.lastTickPosY, mon.lastTickPosZ, getFire()));
			ItemStack fire = getFire();
			mon.entityDropItem(fire, 1);
			mon.entityDropItem(fire, 1);
			mon.entityDropItem(fire, 1);
		}
	}
	
	public static @Nonnull ItemStack getFire() {
		    ItemStack firework = new ItemStack(Items.FIREWORKS);
		    firework.setTagCompound(new NBTTagCompound());
		    NBTTagCompound expl = new NBTTagCompound();
		    expl.setBoolean("Flicker", true);
		    expl.setBoolean("Trail", true);

		    int[] colors = new int[rand.nextInt(8) + 1];
		    for (int i = 0; i < colors.length; i++) {
		      colors[i] = ItemDye.DYE_COLORS[rand.nextInt(16)];
		    }
		    expl.setIntArray("Colors", colors);
		    byte type = (byte) (rand.nextInt(3) + 1);
		    type = type == 3 ? 4 : type;
		    expl.setByte("Type", type);

		    NBTTagList explosions = new NBTTagList();
		    explosions.appendTag(expl);

		    NBTTagCompound fireworkTag = new NBTTagCompound();
		    fireworkTag.setTag("Explosions", explosions);
		    fireworkTag.setByte("Flight", (byte) 1);
		    firework.setTagInfo("Fireworks", fireworkTag);
		    
		    return firework;
		  }
	  
	public boolean hasArmorSet(EntityPlayer player) {
        return this.hasArmorSetItem(player, 0) && this.hasArmorSetItem(player, 1) && this.hasArmorSetItem(player, 2) && this.hasArmorSetItem(player, 3);
    }
    
    public boolean hasArmorSetItem(EntityPlayer player,   int i) {
        if (player == null || player.inventory == null || player.inventory.armorInventory == null) {
            return false;
        }
          ItemStack stack = (ItemStack)player.inventory.armorInventory.get(3 - i);
        if (stack.isEmpty()) {
            return false;
        }
        switch (i) {
            case 0: {
                return stack.getItem() != null;
            }
            case 1: {
                return stack.getItem() != null;
            }
            case 2: {
                return stack.getItem() != null;
            }
            case 3: {
                return stack.getItem() != null;
            }
            default: {
                return false;
            }
        }
    }*/
}