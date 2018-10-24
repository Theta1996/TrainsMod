package alemax.trainsmod;

import java.util.HashMap;

import alemax.trainsmod.proxy.CommonProxy;
import alemax.trainsmod.util.ChunkLoadingCallback;
import alemax.trainsmod.util.Reference;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class TrainsMod {
	
	@Instance
	public static TrainsMod instance;
	
	public static HashMap<ChunkPos, Integer> ticketList;
	private static Ticket chunkLoaderTicket;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		ticketList = new HashMap<ChunkPos, Integer>();
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoadingCallback());
	}
	
	public static void forceChunkLoad(World w, ChunkPos pos) {
		System.out.println(ticketList.size());
		if(!ticketList.containsKey(pos)) {
			if(chunkLoaderTicket == null) {
				chunkLoaderTicket = ForgeChunkManager.requestTicket(instance, w, Type.NORMAL);
			}
			ticketList.put(pos, 1);
			ForgeChunkManager.forceChunk(chunkLoaderTicket, pos);
		}
		else {
			ticketList.put(pos, ticketList.get(pos)+1);
		}
	}
	
	public static void releaseChunkLoad(World w, ChunkPos pos) {
		if(!ticketList.containsKey(pos) || chunkLoaderTicket == null) {
			return;
		}
		else {
			int num = ticketList.get(pos)-1;
			if(num > 0)
				ticketList.put(pos, num);
			else
				ForgeChunkManager.unforceChunk(chunkLoaderTicket, pos);
		}
	}
	
	
}