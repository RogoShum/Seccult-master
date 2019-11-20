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
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.init.ModItems;

public class NetworkTransString implements IMessage {
	private String s;
	private int sort;
	private UUID uuid;
	private long uuidLeast;
	private long uuidMost;
	
	private int type;
	
	public NetworkTransString() {}

	public NetworkTransString(int sort, String s, Entity player, int type) {
		this.s = s;
		this.sort = sort;
		this.uuidLeast = player.getUniqueID().getLeastSignificantBits();
		this.uuidMost = player.getUniqueID().getMostSignificantBits();
		this.type = type;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.s = ByteBufUtils.readUTF8String(buf);
		this.sort = buf.readInt();
		this.uuidLeast = buf.readLong();
		this.uuidMost = buf.readLong();
		this.uuid = new UUID(uuidMost, uuidLeast);
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, s);
		buf.writeInt(sort);
		buf.writeLong(uuidLeast);
		buf.writeLong(uuidMost);
		buf.writeInt(type);
	}

	public static class MessageHandler implements IMessageHandler<NetworkTransString, IMessage> {
		
		@Override
		public IMessage onMessage(NetworkTransString message, MessageContext ctx) {
			World world = ctx.getServerHandler().player.world;
        	EntityPlayer player = world.getPlayerEntityByUUID(message.uuid);
			switch(message.type)
			{
				case 1:
		        	PlayerData data = PlayerDataHandler.get(player);
		        	data.modifiySpellName(message.sort, message.s);
		        	NBTTagList magick = data.getAllMagick();
			        NBTTagCompound nbt = new NBTTagCompound();
			        nbt.setTag("M", magick);
					NetworkHandler.getNetwork().sendTo(new NetworkPlayerTransMagickToClient(nbt), (EntityPlayerMP) player);
			}
			
			return null;
		}
	}
}
