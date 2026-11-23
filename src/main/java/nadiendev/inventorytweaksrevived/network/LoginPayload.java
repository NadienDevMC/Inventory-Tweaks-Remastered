package nadiendev.inventorytweaksrevived.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Sent by a server that has the mod to each player that joins, so the client knows that it can use
 * the server assisted click mode.
 */
public record LoginPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<LoginPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("inventorytweaksrevived", "login"));

    public static final StreamCodec<ByteBuf, LoginPayload> STREAM_CODEC =
            StreamCodec.unit(new LoginPayload());

    @NotNull
    @Override
    public CustomPacketPayload.Type<LoginPayload> type() {
        return TYPE;
    }
}
