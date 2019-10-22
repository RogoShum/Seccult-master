package testmod.seccult.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.blocks.SpellProgrammer;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerMagickData;

public class SpellProgrammerDemo extends Container
{
	private SpellProgrammer programmer;

	
    public SpellProgrammerDemo(SpellProgrammer programmer,EntityPlayer player)
    {
        super();
        this.programmer = programmer;
        NetworkHandler.getNetwork().sendToAll(new NetworkPlayerMagickData(PlayerDataHandler.get(player).getAllMagickData(), player.getUniqueID(), 
        		PlayerDataHandler.get(player).getTrueControlAbility(), PlayerDataHandler.get(player).getTrueManaStrengh()));
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return canPlayerInteract(playerIn);
	}
	
	public boolean canPlayerInteract(EntityPlayer player) {
		return !player.isDead && player.getDistanceSq((double)this.programmer.getX() + 0.5D, (double)this.programmer.getY() + 0.5D, (double)this.programmer.getZ() + 0.5D) <= 64.0D;
	}
}