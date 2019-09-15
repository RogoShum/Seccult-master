package testmod.seccult.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.entity.livings.EntitySpirit;

public class NetworkMutekiGamer implements IMessage {

	private boolean object;
	private UUID uuid;
	private long uuidLeast;
	private long uuidMost;
	
	private int type;
	
	public NetworkMutekiGamer() {}

	public NetworkMutekiGamer(boolean object, Entity player, int type) {
		this.object = object;
		this.uuidLeast = player.getUniqueID().getLeastSignificantBits();
		this.uuidMost = player.getUniqueID().getMostSignificantBits();
		this.type = type;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.object = buf.readBoolean();
		this.uuidLeast = buf.readLong();
		this.uuidMost = buf.readLong();
		this.uuid = new UUID(uuidMost, uuidLeast);
		
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(object);
		buf.writeLong(uuidLeast);
		buf.writeLong(uuidMost);
		buf.writeInt(type);
	}

	public static class PacketMessageHandler implements IMessageHandler<NetworkMutekiGamer, IMessage> {

		@Override
		public IMessage onMessage(NetworkMutekiGamer message, MessageContext ctx) {
			
			if(message.type == 1)
			{
			try {
					EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(message.uuid);
					PlayerData data = PlayerDataHandler.get(player);
					boolean b = message.object;
					data.setMutekiGamer(b);
			}
			catch (Exception e){
				
			}
			}
			else if(message.type == 2)
			{
				try {
				Entity entity = NetworkEntityMoving.getEntityByUUID(message.uuid, Minecraft.getMinecraft().world);
				if(entity instanceof EntitySpirit)
					((EntitySpirit) entity).setRelease();
				}
				catch (Exception e){
					
				}
			}
			return null;
		}
		
	}
}
