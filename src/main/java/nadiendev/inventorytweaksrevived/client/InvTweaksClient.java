package nadiendev.inventorytweaksrevived.client;

import nadiendev.inventorytweaksrevived.InvTweaksNoOpApi;
import nadiendev.inventorytweaksrevived.api.IItemTreeListener;
import nadiendev.inventorytweaksrevived.api.InventoryTweaksRevivedAPI;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.api.IItemTreeCategory;
import nadiendev.inventorytweaksrevived.api.IItemTreeItem;
import nadiendev.inventorytweaksrevived.gui.InvTweaksGuiSettings;
import nadiendev.inventorytweaksrevived.itemtree.InvTweaksItemTree;
import nadiendev.inventorytweaksrevived.itemtree.InvTweaksItemTreeLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import nadiendev.inventorytweaksrevived.handler.InvTweaksHandlerMouse;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Client side entry point: sort key, client tick, screen buttons, shortcut clicks, pickup notification, tree path
 * tooltips and the config screen. Only loaded on the physical client.
 */
public final class InvTweaksClient {

    public static final KeyMapping KEY_SORT = new KeyMapping("invtweaks.key.sort", GLFW.GLFW_KEY_R, "invtweaks.key.category");

    /**
     * Whether the server has the mod (set by the login payload) and whether item moves go through it.
     */
    public static boolean serverSupportEnabled = false;
    public static boolean serverSupportDetected = false;

    /**
     * Mouse button whose release must not reach the screen, because its press ran a shortcut instead of the vanilla
     * click (-1: none).
     */
    private static int swallowedReleaseButton = -1;

    /**
     * The API until the core exists: item tree load listeners are kept (the tree is loaded with the core), the rest
     * does nothing.
     */
    private static final InventoryTweaksRevivedAPI PRE_WORLD_API = new InvTweaksNoOpApi() {
        @Override
        public void addOnLoadListener(IItemTreeListener listener) {
            InvTweaksItemTreeLoader.addOnLoadListener(listener);
        }

        @Override
        public boolean removeOnLoadListener(IItemTreeListener listener) {
            return InvTweaksItemTreeLoader.removeOnLoadListener(listener);
        }
    };

    private InvTweaksClient() {
    }

    public static void init(IEventBus modBus, ModContainer container) {
        modBus.addListener(InvTweaksClient::registerKeyMappings);

        NeoForge.EVENT_BUS.addListener(InvTweaksClient::onClientTick);
        NeoForge.EVENT_BUS.addListener(InvTweaksClient::onScreenInit);
        NeoForge.EVENT_BUS.addListener(InvTweaksClient::onMousePressed);
        NeoForge.EVENT_BUS.addListener(InvTweaksClient::onMouseReleased);
        NeoForge.EVENT_BUS.addListener(InvTweaksHandlerMouse::onMousePressed);
        NeoForge.EVENT_BUS.addListener(InvTweaksHandlerMouse::onMouseReleased);
        NeoForge.EVENT_BUS.addListener(InvTweaksHandlerMouse::onMouseDragged);
        NeoForge.EVENT_BUS.addListener(InvTweaksHandlerMouse::onMouseScrolled);
        NeoForge.EVENT_BUS.addListener(InvTweaksClient::onItemPickup);
        NeoForge.EVENT_BUS.addListener(InvTweaksClient::onItemTooltip);
        NeoForge.EVENT_BUS.addListener(InvTweaksClient::onLoggingIn);
        NeoForge.EVENT_BUS.addListener(InvTweaksClient::onLoggingOut);

        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> new InvTweaksGuiSettings(parent));
    }

    /**
     * Called by the text field mixin when a text field gains or loses focus, so typing does not trigger the sort key.
     */
    public static void setTextboxMode(boolean enabled) {
        InvTweaksCore core = InvTweaksCore.getInstance();
        if(core != null) {
            core.setTextboxMode(enabled);
        }
    }

    public static InventoryTweaksRevivedAPI getApi() {
        InvTweaksCore core = InvTweaksCore.getInstance();
        return (core != null) ? core : PRE_WORLD_API;
    }

    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KEY_SORT);
    }

    /**
     * The core loads the configuration files, so it is only created once a player is in a world. It then lives for the
     * rest of the session, like the 1.12 one.
     */
    @Nullable
    private static InvTweaksCore getOrCreateCore(Minecraft mc) {
        InvTweaksCore core = InvTweaksCore.getInstance();
        if(core == null && mc.level != null && mc.player != null) {
            core = new InvTweaksCore(mc);
        }
        return core;
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.level == null || mc.player == null) {
            return;
        }
        InvTweaksCore core = getOrCreateCore(mc);
        if(core == null) {
            return;
        }
        if(mc.screen != null) {
            core.onTickInGUI(mc.screen);
        } else {
            core.onTickInGame();
        }
    }

    /**
     * Fires after every (re)initialization of a screen, once its own widgets are in place: our buttons are added here
     * so they survive re-inits (resizing, recipe book toggles) and use the screen's widget lists properly.
     */
    private static void onScreenInit(ScreenEvent.Init.Post event) {
        // A screen whose init() opened another one (the survival inventory in creative mode) posts its event after the
        // new screen's: it is already discarded.
        if(event.getScreen() != Minecraft.getInstance().screen) {
            return;
        }
        // A release still awaited from the previous screen must not swallow a click on this one.
        swallowedReleaseButton = -1;
        InvTweaksCore core = InvTweaksCore.getInstance();
        if(core != null && event.getScreen() instanceof AbstractContainerScreen<?> containerScreen) {
            core.initGUILayout(containerScreen, event::addListener);
        }
    }

    /**
     * Our buttons and the shortcuts run before the screen sees the click; when one handles it, the vanilla click (and
     * its release) is cancelled so the screen does not also act on it.
     */
    private static void onMousePressed(ScreenEvent.MouseButtonPressed.Pre event) {
        InvTweaksCore core = InvTweaksCore.getInstance();
        if(core != null && core.clickLayoutButton(event.getScreen(), event.getMouseX(), event.getMouseY(), event.getButton())) {
            // One of our buttons took the click before a widget of the screen lying under it
            swallowedReleaseButton = event.getButton();
            event.setCanceled(true);
        } else if(core != null && event.getScreen() instanceof AbstractContainerScreen<?> containerScreen
                && core.handleShortcuts(containerScreen, event.getButton())) {
            swallowedReleaseButton = event.getButton();
            event.setCanceled(true);
        } else if(event.getButton() == swallowedReleaseButton) {
            // A release swallowed earlier never came (the screen closed or another mod cancelled it): forget it, so
            // the release of this unhandled press reaches the screen.
            swallowedReleaseButton = -1;
        }
    }

    private static void onMouseReleased(ScreenEvent.MouseButtonReleased.Pre event) {
        if(event.getButton() == swallowedReleaseButton) {
            swallowedReleaseButton = -1;
            event.setCanceled(true);
        }
    }

    /**
     * Fires on the logical server only, so this works in singleplayer (and for the host of a LAN world), as in 1.12.
     * On the integrated server it runs on the server thread: the flag is set on the client thread.
     */
    private static void onItemPickup(ItemEntityPickupEvent.Post event) {
        if(event.getPlayer().level().isClientSide()) {
            return;
        }
        UUID pickerId = event.getPlayer().getUUID();
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            InvTweaksCore core = InvTweaksCore.getInstance();
            if(core != null && mc.player != null && mc.player.getUUID().equals(pickerId)) {
                core.setItemPickupPending(true);
            }
        });
    }

    /**
     * Shows where an item sits in the item tree, when enabled in the advanced settings. Only on the client thread: the
     * event also fires while the creative search trees are built in the background, and looking an item up can add it
     * to the tree.
     */
    private static void onItemTooltip(ItemTooltipEvent event) {
        if(!Minecraft.getInstance().isSameThread()) {
            return;
        }
        ItemStack current = event.getItemStack();
        if(current.isEmpty()) {
            return;
        }
        InvTweaksConfigManager cfgManager = InvTweaksCore.getConfigManager();
        if(cfgManager == null) {
            return;
        }
        InvTweaksConfig config = cfgManager.getConfig();
        if(config == null || !config.getProperty(InvTweaksConfig.PROP_TOOLTIP_PATH).equals(InvTweaksConfig.VALUE_TRUE)) {
            return;
        }

        InvTweaksItemTree tree = config.getTree();
        List<IItemTreeItem> items = tree.getItems(current);
        if(items.isEmpty()) {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        Set<String> paths = new HashSet<>();
        IItemTreeCategory root = tree.getRootCategory();
        int unsortedZone = tree.getLastTreeOrder();
        int minOrder = Integer.MAX_VALUE;
        for(IItemTreeItem item : items) {
            String path = item.getPath();
            String altPath = root.findKeywordPath(item.getName());
            int itemOrder = item.getOrder();
            minOrder = Integer.min(minOrder, itemOrder);

            // Once a match in the tree has been listed, the unsorted matches are not.
            if(minOrder <= unsortedZone && itemOrder > unsortedZone) {
                break;
            }

            if(path.equals(altPath) || itemOrder > unsortedZone) {
                if(paths.add(path)) {
                    tooltip.add(treePathLine("", path, itemOrder));
                }
            } else {
                if(paths.add(path)) {
                    tooltip.add(treePathLine("T:", path, itemOrder));
                }
                if(paths.add(altPath)) {
                    tooltip.add(treePathLine("M:", altPath, itemOrder));
                }
            }
        }
    }

    private static Component treePathLine(String prefix, String path, int order) {
        return Component.literal(prefix + path + " (" + order + ")").withStyle(ChatFormatting.DARK_GRAY);
    }

    /**
     * Fired from the login packet handler once the level and the player exist, before any play-phase payload is
     * handled: the core is created here so the server's login payload finds the configuration loaded.
     */
    private static void onLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        serverSupportDetected = false;
        serverSupportEnabled = false;
        getOrCreateCore(Minecraft.getInstance());
    }

    private static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        serverSupportDetected = false;
        serverSupportEnabled = false;
        swallowedReleaseButton = -1;
        InvTweaksCore core = InvTweaksCore.getInstance();
        if(core != null) {
            core.clearGUILayout();
            core.setTextboxMode(false);
        }
    }
}
