package testmod.seccult.network;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import testmod.seccult.Seccult;

public class NetworkHandler {
	private static final SimpleNetworkWrapper network = new SimpleNetworkWrapper(Seccult.MODID);
	
	public static void init() 
	{
		network.registerMessage(NetworkPlayerWandData.PacketMessageHandler.class, NetworkPlayerWandData.class, 0, Side.CLIENT);
		network.registerMessage(NetworkPlayerMagickData.PacketMessageHandler.class, NetworkPlayerMagickData.class, 1, Side.CLIENT);
		network.registerMessage(NetworkPlayerParticleData.PacketMessageHandler.class, NetworkPlayerParticleData.class, 2, Side.CLIENT);
	}
	
	public static SimpleNetworkWrapper getNetwork()
	{
		return network;
	}
}
