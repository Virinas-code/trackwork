package edn.stratodonut.trackwork;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import com.simibubi.create.foundation.networking.SimplePacketBase.NetworkDirection;
import edn.stratodonut.trackwork.tracks.network.SimpleWheelPacket;
import edn.stratodonut.trackwork.tracks.network.SuspensionWheelPacket;
import edn.stratodonut.trackwork.tracks.network.ThrowTrackPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.function.Function;

import static com.simibubi.create.foundation.networking.SimplePacketBase.NetworkDirection.PLAY_TO_CLIENT;

public enum TrackPackets {
	SUSPENSION_WHEEL(SuspensionWheelPacket.class, SuspensionWheelPacket::new, PLAY_TO_CLIENT),
	SIMPLE_WHEEL(SimpleWheelPacket.class, SimpleWheelPacket::new, PLAY_TO_CLIENT),
	THROW_TRACK(ThrowTrackPacket.class, ThrowTrackPacket::new, PLAY_TO_CLIENT);

	public static final ResourceLocation CHANNEL_NAME = new ResourceLocation(TrackworkMod.MOD_ID, "main");
	public static final int NETWORK_VERSION = 3;
	public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
	private static SimpleChannel channel;

	private PacketType<?> packetType;

	<T extends SimplePacketBase> TrackPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
											  NetworkDirection direction) {
		packetType = new PacketType<>(type, factory, direction);
	}

	public static void registerPackets() {
		channel = new SimpleChannel(CHANNEL_NAME);
		for (AllPackets packet : values())
			packet.packetType.register();
	}

	public static SimpleChannel getChannel() {
		return channel;
	}

	public static void sendToNear(Level world, BlockPos pos, int range, Object message) {
		getChannel().sendToClientsAround((S2CPacket) message, (ServerLevel) world, pos, range);
	}

	private static class PacketType<T extends SimplePacketBase> {
		private static int index = 0;

		private Function<FriendlyByteBuf, T> decoder;
		private Class<T> type;
		private NetworkDirection direction;

		private PacketType(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
			decoder = factory;
			this.type = type;
			this.direction = direction;
		}

		private void register() {
			switch (direction) {
				case PLAY_TO_CLIENT -> getChannel().registerS2CPacket(type, index++, decoder);
				case PLAY_TO_SERVER -> getChannel().registerC2SPacket(type, index++, decoder);
			}
		}
	}

}
//
//package edn.stratodonut.trackwork;
//
//import com.simibubi.create.foundation.networking.SimplePacketBase;
//import edn.stratodonut.trackwork.tracks.network.SimpleWheelPacket;
//import edn.stratodonut.trackwork.tracks.network.SuspensionWheelPacket;
//import edn.stratodonut.trackwork.tracks.network.ThrowTrackPacket;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.network.NetworkDirection;
//import net.minecraftforge.network.NetworkEvent;
//import net.minecraftforge.network.NetworkRegistry;
//import net.minecraftforge.network.simple.SimpleChannel;
//
//import java.util.function.BiConsumer;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
//
//public enum TrackPackets {
//	SUSPENSION_WHEEL(SuspensionWheelPacket.class, SuspensionWheelPacket::new, PLAY_TO_CLIENT),
//	SIMPLE_WHEEL(SimpleWheelPacket.class, SimpleWheelPacket::new, PLAY_TO_CLIENT),
//	THROW_TRACK(ThrowTrackPacket.class, ThrowTrackPacket::new, PLAY_TO_CLIENT);
//
//	// DO NOT TOUCH ANYTHING BELOW THIS LINE, THANKS CREATE
//
//	public static final ResourceLocation CHANNEL_NAME = new ResourceLocation(TrackworkMod.MOD_ID, "main");
//	public static final int NETWORK_VERSION = 3;
//	public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
//	private static SimpleChannel channel;
//
//	private final PacketType<?> packetType;
//
//	<T extends SimplePacketBase> TrackPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
//											  NetworkDirection direction) {
//		packetType = new TrackPackets.PacketType<>(type, factory, direction);
//	}
//
//	public static void registerPackets() {
//		channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
//			.serverAcceptedVersions(NETWORK_VERSION_STR::equals)
//			.clientAcceptedVersions(NETWORK_VERSION_STR::equals)
//			.networkProtocolVersion(() -> NETWORK_VERSION_STR)
//			.simpleChannel();
//
//		for (TrackPackets packet : values())
//			packet.packetType.register();
//	}
//
//	public static SimpleChannel getChannel() {
//		return channel;
//	}
//
//	private static class PacketType<T extends SimplePacketBase> {
//		private static int index = 0;
//
//		private BiConsumer<T, FriendlyByteBuf> encoder;
//		private Function<FriendlyByteBuf, T> decoder;
//		private BiConsumer<T, Supplier<NetworkEvent.Context>> handler;
//		private Class<T> type;
//		private NetworkDirection direction;
//
//		private PacketType(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
//			encoder = T::write;
//			decoder = factory;
//			handler = (packet, contextSupplier) -> {
//				NetworkEvent.Context context = contextSupplier.get();
//				if (packet.handle(context)) {
//					context.setPacketHandled(true);
//				}
//			};
//			this.type = type;
//			this.direction = direction;
//		}
//
//		private void register() {
//			getChannel().messageBuilder(type, index++, direction)
//				.encoder(encoder)
//				.decoder(decoder)
//				.consumerNetworkThread(handler)
//				.add();
//		}
//	}
//}
