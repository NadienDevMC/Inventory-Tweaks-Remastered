package nadiendev.inventorytweaksrevived.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Sent by the client once a sort is finished, so the server resends the whole container and any
 * desynchronization left by the locally applied clicks is corrected.
 */
public record SortCompletePayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SortCompletePayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("inventorytweaksrevived", "sortcomplete"));

    public static final StreamCodec<ByteBuf, SortCompletePayload> STREAM_CODEC =
            StreamCodec.unit(new SortCompletePayload());

    @NotNull
    @Override
    public CustomPacketPayload.Type<SortCompletePayload> type() {
        return TYPE;
    }
}
