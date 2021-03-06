package alemax.trainsmod.networking;

import alemax.trainsmod.global.trackmarker.TrackMarker;
import alemax.trainsmod.global.trackmarker.TrackMarkerInstances;
import alemax.trainsmod.init.TMPackets;
import alemax.trainsmod.util.TrackBuildUtils;
import alemax.trainsmod.util.TrackType;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PacketC2STrackBuild extends TMPacket {


    public PacketC2STrackBuild() {
        super("track_build");
    }

    public void send(String channel) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

        buf.writeString(channel, TrackMarker.MAX_CHANNEL_LENGTH);

        ClientSidePacketRegistry.INSTANCE.sendToServer(new CustomPayloadC2SPacket(this.identifier, buf));
    }

    @Override
    public void register() {
        ServerSidePacketRegistry.INSTANCE.register(this.identifier, (packetContext, buf) -> {
            String channel = buf.readString(TrackMarker.MAX_CHANNEL_LENGTH);

            World world = packetContext.getPlayer().world;

            packetContext.getTaskQueue().execute(() -> {
                TrackBuildUtils.buildStandardTrackOverworld(channel, world);
            });
        });
    }

    //player.networkHandler.sendPacket(new CustomPayloadS2CPacket(ident, buf));
    //PlayerStream.around(world, pos, distance).forEach(x -> ((ServerPlayerEntity)x).networkHandler.sendPacket(new CustomPayloadS2CPacket(ident, buf)));
    //server.getPlayerManager().sendToAll(new CustomPayloadS2CPacket(ident, buf));
    //ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, new CustomPayloadS2CPacket(ident, buf));
    //ClientSidePacketRegistry.INSTANCE.sendToServer(new CustomPayloadC2SPacket(ident, buf));
    //MinecraftClient.getInstance().getNetworkHandler().getConnection().send(new CustomPayloadC2SPacket(ident, buf));
}
