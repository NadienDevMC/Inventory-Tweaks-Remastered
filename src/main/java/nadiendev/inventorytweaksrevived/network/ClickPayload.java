package nadiendev.inventorytweaksrevived.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.ContainerInput;
import org.jetbrains.annotations.NotNull;

/**
 * Container click sent by a client that applies the click locally and lets the server replay it,
 * instead of waiting for the server to answer every single click.
 */
public record ClickPayload(int slot, int data, ContainerInput action, int window) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClickPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("inventorytweaksrevived", "click"));

    public static final StreamCodec<ByteBuf, ClickPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ClickPayload::slot,
            ByteBufCodecs.VAR_INT, ClickPayload::data,
            ContainerInput.STREAM_CODEC, ClickPayload::action,
            ByteBufCodecs.VAR_INT, ClickPayload::window,
            ClickPayload::new);

    @NotNull
    @Override
    public CustomPacketPayload.Type<ClickPayload> type() {
        return TYPE;
    }
}
