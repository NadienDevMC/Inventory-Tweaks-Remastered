package nadiendev.inventorytweaksrevived.network;

import nadiendev.inventorytweaksrevived.client.InvTweaksClient;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

/**
 * Optional server support. Every payload is registered as optional, so a client with the mod can join a server
 * without it (and the other way round); in that case clicks go through the vanilla path.
 */
public final class InvTweaksNetwork {
    private InvTweaksNetwork() {
    }

    public static void register(@NotNull RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1").optional();
        registrar.playToServer(ClickPayload.TYPE, ClickPayload.STREAM_CODEC, InvTweaksNetwork::handleClick);
        registrar.playToServer(SortCompletePayload.TYPE, SortCompletePayload.STREAM_CODEC, InvTweaksNetwork::handleSortComplete);
        registrar.playToClient(LoginPayload.TYPE, LoginPayload.STREAM_CODEC, InvTweaksNetwork::handleLogin);
    }

    /**
     * Server side: tells a joining player that this server can replay clicks. Subscribed to the game bus.
     */
    public static void onPlayerLoggedIn(@NotNull PlayerEvent.PlayerLoggedInEvent event) {
        // Clients without the mod (or vanilla clients) have no negotiated channel; sending to them would throw.
        if(event.getEntity() instanceof ServerPlayer player && player.connection.hasChannel(LoginPayload.TYPE)) {
            PacketDistributor.sendToPlayer(player, new LoginPayload());
        }
    }

    private static void handleClick(@NotNull ClickPayload payload, @NotNull IPayloadContext context) {
        Player player = context.player();
        if(!player.isSpectator() && player.containerMenu.containerId == payload.window()
                && player.containerMenu.isValidSlotIndex(payload.slot())) {
            player.containerMenu.clicked(payload.slot(), payload.data(), payload.action(), player);
        }
        // TODO: Might want to set a flag to ignore all packets until next sortcomplete even if client window changes.
    }

    private static void handleSortComplete(@NotNull SortCompletePayload payload, @NotNull IPayloadContext context) {
        context.player().containerMenu.sendAllDataToRemote();
    }

    private static void handleLogin(@NotNull LoginPayload payload, @NotNull IPayloadContext context) {
        InvTweaksConfigManager configManager = InvTweaksCore.getConfigManager();
        InvTweaksConfig config = configManager == null ? null : configManager.getConfig();
        boolean enabled = config == null
                || !config.getProperty(InvTweaksConfig.PROP_ENABLE_SERVER_ITEMSWAP).equals(InvTweaksConfig.VALUE_FALSE);
        InvTweaksClient.serverSupportDetected = true;
        InvTweaksClient.serverSupportEnabled = enabled;
    }

    /**
     * Client side: performs a container click. With server support the click is applied locally and replayed by the
     * server, otherwise it goes through the vanilla click packet.
     */
    public static void clickSlot(int windowId, int slot, int data, @NotNull ClickType action, @NotNull Player player) {
        if(InvTweaksClient.serverSupportEnabled) {
            player.containerMenu.clicked(slot, data, action, player);
            PacketDistributor.sendToServer(new ClickPayload(slot, data, action, windowId));
        } else {
            ClientOnly.vanillaClick(windowId, slot, data, action, player);
        }
    }

    /**
     * Client side: asks the server to resend the container once a sort is finished.
     */
    public static void sortComplete() {
        if(InvTweaksClient.serverSupportEnabled) {
            PacketDistributor.sendToServer(new SortCompletePayload());
        }
    }

    /**
     * Keeps the client-only classes out of this class, which is also loaded by a dedicated server.
     */
    private static final class ClientOnly {
        static void vanillaClick(int windowId, int slot, int data, ClickType action, Player player) {
            Minecraft.getInstance().gameMode.handleInventoryMouseClick(windowId, slot, data, action, player);
        }
    }
}
