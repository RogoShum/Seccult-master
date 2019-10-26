package testmod.seccult.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.init.ModItems;

public class NetworkTransFloat implements IMessage {
	private int object;
	private UUID uuid;
	private long uuidLeast;
	private long uuidMost;
	
	private int type;
	
	public NetworkTransFloat() {}

	public NetworkTransFloat(int object, Entity player, int type) {
		this.object = object;
		this.uuidLeast = player.getUniqueID().getLeastSignificantBits();
		this.uuidMost = player.getUniqueID().getMostSignificantBits();
		this.type = type;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.object = buf.readInt();
		this.uuidLeast = buf.readLong();
		this.uuidMost = buf.readLong();
		this.uuid = new UUID(uuidMost, uuidLeast);
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(object);
		buf.writeLong(uuidLeast);
		buf.writeLong(uuidMost);
		buf.writeInt(type);
	}

	public static class MessageHandler implements IMessageHandler<NetworkTransFloat, IMessage> {
		
		@Override
		public IMessage onMessage(NetworkTransFloat message, MessageContext ctx) {
			World world = ctx.getServerHandler().player.world;
        	EntityPlayer player = world.getPlayerEntityByUUID(message.uuid);
			switch(message.type)
			{
				case 1:
		        	if(player.getHeldItemMainhand().getItem() == ModItems.Wand)
		        	{
		        		
		        		player.getHeldItemMainhand().getTagCompound().setInteger("Slot", message.object);
		        	}
		        	break;
				case 2:
					player.openGui(Seccult.instance, GuiElementLoader.GUI_Accessories, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		        	break;
				case 3:
					player.openGui(Seccult.instance, GuiElementLoader.GUI_SpellSelect, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		        	break;
				case 4:
					PlayerData data = PlayerDataHandler.get(player);
			        NBTTagList magick = data.getAllMagick();
			        NBTTagCompound nbt = new NBTTagCompound();
			        nbt.setTag("M", magick);
					NetworkHandler.getNetwork().sendTo(new NetworkPlayerTransMagickToClient(nbt), (EntityPlayerMP) player);
		        	break;
			}
			
			return null;
		}
	}
}
