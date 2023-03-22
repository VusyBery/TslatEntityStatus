package net.tslat.tes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.tslat.tes.api.TESConstants;
import net.tslat.tes.networking.ParticleClaimPacket;
import net.tslat.tes.networking.SyncEffectsPacket;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class TESClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		TESConstants.setIsClient();

		ClientPlayNetworking.registerGlobalReceiver(SyncEffectsPacket.ID, (client, handler, buf, responseSender) -> SyncEffectsPacket.decode(buf).handleMessage(client::submit));
		ClientPlayNetworking.registerGlobalReceiver(ParticleClaimPacket.ID, (client, handler, buf, responseSender) -> ParticleClaimPacket.decode(buf).handleMessage(client::submit));
	}

	public static void sendPacket(ResourceLocation packetId, FriendlyByteBuf buffer) {
		ClientPlayNetworking.send(packetId, buffer);
	}
}
