package net.tslat.tes;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.tslat.tes.api.TESConstants;
import net.tslat.tes.config.TESConfig;
import net.tslat.tes.core.networking.packet.MultiloaderPacket;
import org.jetbrains.annotations.ApiStatus;

public class TESClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		TESConstants.setIsClient();
		MidnightConfig.init(TESConstants.MOD_ID, TESConfig.class);
		TESConstants.setConfig(new TESConfig());
	}

	public static void sendPacket(CustomPacketPayload packet) {
		ClientPlayNetworking.send(packet);
	}

	@ApiStatus.Internal
	public static <B extends FriendlyByteBuf, P extends MultiloaderPacket> void registerPacket(CustomPacketPayload.Type<P> packetType, StreamCodec<B, P> codec) {
		PayloadTypeRegistry.playS2C().register(packetType, (StreamCodec<FriendlyByteBuf, P>)codec);
		ClientPlayNetworking.registerGlobalReceiver(packetType, (packet, context) -> packet.receiveMessage(context.player(), context.client()::execute));
	}
}
