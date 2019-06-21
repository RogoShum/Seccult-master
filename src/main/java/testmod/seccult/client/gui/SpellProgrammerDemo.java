package testmod.seccult.client.gui;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerMagickDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.blocks.SpellProgrammer;
import testmod.seccult.init.ModItems;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerMagickData;
import testmod.seccult.network.NetworkPlayerWandData;

public class SpellProgrammerDemo extends Container
{
	private SpellProgrammer programmer;
	private ItemStackHandler items = new ItemStackHandler(4);
    private EntityPlayer player;
	
    public SpellProgrammerDemo(SpellProgrammer programmer,EntityPlayer player)
    {
        super();
        this.programmer = programmer;
        this.player = player;
        NetworkHandler.getNetwork().sendToAll(new NetworkPlayerMagickData(PlayerMagickDataHandler.getDataListForPlayer(player), player.getUniqueID()));
    }
    
    private void setMagickList(NBTTagList list)
    {
    	if(list != null && !list.hasNoTags())
    	{
    		Iterator it = list.iterator();
    		while(it.hasNext())
    		{
    			//magicklist.add(it.next());
    		}
    	}
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return canPlayerInteract(playerIn);
	}
	
	public boolean canPlayerInteract(EntityPlayer player) {
		return !player.isDead && player.getDistanceSq((double)this.programmer.getX() + 0.5D, (double)this.programmer.getY() + 0.5D, (double)this.programmer.getZ() + 0.5D) <= 64.0D;
	}
}