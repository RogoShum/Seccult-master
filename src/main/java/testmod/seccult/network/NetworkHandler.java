package testmod.seccult.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import testmod.seccult.Seccult;

public class NetworkHandler {
	private static final SimpleNetworkWrapper network = new SimpleNetworkWrapper(Seccult.MODID);
	
	public static void init() 
	{
		int i = 0;
		network.registerMessage(NetworkPlayerWandData.PacketMessageHandler.class, NetworkPlayerWandData.class, i++, Side.CLIENT);
		network.registerMessage(NetworkPlayerMagickData.PacketMessageHandler.class, NetworkPlayerMagickData.class, i++, Side.CLIENT);
		network.registerMessage(NetworkEffectData.PacketMessageHandler.class, NetworkEffectData.class, i++, Side.CLIENT);
		network.registerMessage(NetworkEntityMoving.PacketMessageHandler.class, NetworkEntityMoving.class, i++, Side.CLIENT);
		network.registerMessage(NetworkPlayerTransMagickToClient.PacketMessageHandler.class, NetworkPlayerTransMagickToClient.class, i++, Side.CLIENT);
		network.registerMessage(NetworPlayerMagickData.PacketMessageHandler.class, NetworPlayerMagickData.class, i++, Side.CLIENT);
		network.registerMessage(NetworkMutekiGamer.PacketMessageHandler.class, NetworkMutekiGamer.class, i++, Side.CLIENT);
		network.registerMessage(CNetworkTransFloat.PacketMessageHandler.class, CNetworkTransFloat.class, i++, Side.CLIENT);
		network.registerMessage(NetworkPlayerAddMagick.PacketMessageHandler.class, NetworkPlayerAddMagick.class, i++, Side.SERVER);
		network.registerMessage(NetworkTransFloat.MessageHandler.class, NetworkTransFloat.class, i++, Side.SERVER);
		network.registerMessage(NetworkTransString.MessageHandler.class, NetworkTransString.class, i++, Side.SERVER);
	}
	
	public static SimpleNetworkWrapper getNetwork()
	{
		return network;
	}
	
	public static void sendToAllAround(IMessage message, TransPoint point, World world)
	{
		for(int i = 0; i < world.playerEntities.size(); ++i)
		{
			EntityPlayer player = world.playerEntities.get(i);
			if(player instanceof EntityPlayerMP && player.getDistance(point.x, point.y, point.z) < point.range)
				network.sendTo(message, (EntityPlayerMP) player);
		}
	}
}
