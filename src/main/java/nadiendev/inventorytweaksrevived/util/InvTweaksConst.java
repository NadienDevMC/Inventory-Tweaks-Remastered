package nadiendev.inventorytweaksrevived.util;

import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class InvTweaksConst {

    // Mod version
    public static final String MOD_VERSION = ModList.get().getModContainerById(InventoryTweaksRevived.MOD_ID)
            .map(container -> container.getModInfo().getVersion().toString())
            .orElse("unknown");

    // Mod tree version
    // Change only when the tree evolves significantly enough to need to override all configs
    public static final String TREE_VERSION = "1.21.1";

    // Timing constants
    public static final int RULESET_SWAP_DELAY = 1000;
    public static final int CHEST_ALGORITHM_SWAP_MAX_INTERVAL = 2000;

    // File constants
    public static final File MINECRAFT_DIR = FMLPaths.GAMEDIR.get().toFile();
    public static final File MINECRAFT_CONFIG_DIR = FMLPaths.CONFIGDIR.get().toFile();
    public static final File INVTWEAKS_CONFIG_DIR = new File(MINECRAFT_CONFIG_DIR, "InvTweaks/");
    public static final File INVTWEAKS_TREES_DIR = new File(INVTWEAKS_CONFIG_DIR, "trees/");
    public static final File CONFIG_PROPS_FILE = new File(INVTWEAKS_CONFIG_DIR, "InvTweaks.cfg");
    public static final File CONFIG_RULES_FILE = new File(INVTWEAKS_CONFIG_DIR, "InvTweaksRules.txt");
    public static final File CONFIG_TREE_FILE = new File(INVTWEAKS_CONFIG_DIR, "InvTweaksTree.txt");
    public static final File OLD_CONFIG_TREE_FILE = new File(MINECRAFT_CONFIG_DIR, "InvTweaksTree.txt");
    public static final File OLDER_CONFIG_RULES_FILE = new File(MINECRAFT_DIR, "InvTweaksRules.txt");

    public static final File TEMP_DIR = new File(System.getProperty("java.io.tmpdir"));
    public static final File MERGED_TREE_FILE = new File(TEMP_DIR, "InvTweaksTree.txt");
    public static final File MERGED_TREE_FILE_ALT = new File(INVTWEAKS_TREES_DIR, "InvTweaksTree.txt");

    public static final String INVTWEAKS_RESOURCE_DOMAIN = InventoryTweaksRevived.MOD_ID;
    public static final ResourceLocation DEFAULT_CONFIG_FILE = ResourceLocation.fromNamespaceAndPath(INVTWEAKS_RESOURCE_DOMAIN, "defaultconfig.dat");
    public static final ResourceLocation DEFAULT_CONFIG_TREE_FILE = ResourceLocation.fromNamespaceAndPath(INVTWEAKS_RESOURCE_DOMAIN, "itemtree.xml");

    public static final String HELP_URL = "http://inventory-tweaks.readthedocs.org";
    public static final String TREE_URL = "https://github.com/NadienDevMC/Inventory-Tweaks-Remastered/tree/Neo/1.21.1/trees";

    // Global mod constants
    public static final String INGAME_LOG_PREFIX = "InvTweaks: ";

    // Minecraft constants
    public static final int INVENTORY_SIZE = 36;
    public static final int INVENTORY_ROW_SIZE = 9;
    public static final int HOTBAR_SIZE = 9;
    public static final int INVENTORY_HOTBAR_SIZE = INVENTORY_ROW_SIZE;
    public static final int DAMAGE_WILDCARD = -1;
}
