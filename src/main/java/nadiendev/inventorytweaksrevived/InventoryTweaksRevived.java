package nadiendev.inventorytweaksrevived;

import nadiendev.inventorytweaksrevived.api.InventoryTweaksRevivedAPI;
import nadiendev.inventorytweaksrevived.client.InvTweaksClient;
import nadiendev.inventorytweaksrevived.data.InvTweaksData;
import nadiendev.inventorytweaksrevived.network.InvTweaksNetwork;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(InventoryTweaksRevived.MOD_ID)
public class InventoryTweaksRevived {
    public static final String MOD_ID = "inventorytweaksrevived";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final InventoryTweaksRevivedAPI SERVER_API = new InvTweaksNoOpApi();

    public InventoryTweaksRevived(IEventBus modBus, ModContainer container) {
        modBus.addListener(InvTweaksData::gatherData);
        modBus.addListener(InvTweaksNetwork::register);
        NeoForge.EVENT_BUS.addListener(InvTweaksNetwork::onPlayerLoggedIn);

        if(FMLEnvironment.getDist().isClient()) {
            InvTweaksClient.init(modBus, container);
        }
    }

    /**
     * The Inventory Tweaks API for other mods. On the client it is the mod core once the player has joined a world;
     * before that only item tree load listeners are kept. On a dedicated server every call does nothing. Fetch it when
     * needed rather than keeping it.
     */
    public static InventoryTweaksRevivedAPI getApi() {
        if(FMLEnvironment.getDist().isClient()) {
            return InvTweaksClient.getApi();
        }
        return SERVER_API;
    }
}
