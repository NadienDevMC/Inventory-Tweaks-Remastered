package nadiendev.inventorytweaksrevived.itemtree;

import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import nadiendev.inventorytweaksrevived.api.IItemTreeListener;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Loads the item tree by parsing the XML file.
 * <p>
 * A node is either a category, an item ({@code id}), a tag ({@code tag}) or, for trees written for older versions, a
 * legacy ore dictionary name ({@code oreDictName}) or Java class name ({@code class}). Nothing that is wrong in a node
 * (unknown id, unknown legacy name, unsupported attribute) stops the load: the problem is logged and the node skipped.
 *
 * @author Jimeo Wan
 */
public class InvTweaksItemTreeLoader extends DefaultHandler {

    public final static String ATTR_ID = "id";
    public final static String ATTR_DAMAGE = "damage"; // Ignored, items have no damage variants any more
    public final static String ATTR_RANGE_DMIN = "dmin"; // Damage ranges, not supported any more
    public final static String ATTR_TAG = "tag"; // Item tags, "namespace:path"
    public final static String ATTR_OREDICT_NAME = "oreDictName"; // Legacy OreDictionary names
    public final static String ATTR_DATA = "data"; // Item NBT, not supported any more
    public final static String ATTR_CLASS = "class"; // Legacy item class names
    public final static String ATTR_LAST_ORDER = "mergePrevious";
    public final static String ATTR_MERGE_CHILDREN = "mergeChildren";
    public final static String ATTR_TREE_VERSION = "treeVersion";
    public final static String ATTR_TREE_ORDER = "treeOrder";

    /**
     * OreDictionary names that are not covered by a prefix rule in {@link #legacyOreTag(String)}.
     */
    private static final Map<String, String> LEGACY_ORE_TAGS = Map.ofEntries(
            Map.entry("logWood", "minecraft:logs"),
            Map.entry("plankWood", "minecraft:planks"),
            Map.entry("slabWood", "minecraft:wooden_slabs"),
            Map.entry("stairWood", "minecraft:wooden_stairs"),
            Map.entry("fenceWood", "minecraft:wooden_fences"),
            Map.entry("stickWood", "c:rods/wooden"),
            Map.entry("treeLeaves", "minecraft:leaves"),
            Map.entry("treeSapling", "minecraft:saplings"),
            Map.entry("stone", "c:stones"),
            Map.entry("cobblestone", "c:cobblestones"),
            Map.entry("sand", "minecraft:sand"),
            Map.entry("gravel", "c:gravels"),
            Map.entry("glass", "c:glass_blocks"),
            Map.entry("paneGlass", "c:glass_panes"),
            Map.entry("netherrack", "c:netherracks"),
            Map.entry("obsidian", "c:obsidians"),
            Map.entry("endstone", "c:end_stones"),
            Map.entry("chest", "c:chests"),
            Map.entry("string", "c:strings"),
            Map.entry("slimeball", "c:slime_balls"),
            Map.entry("feather", "c:feathers"),
            Map.entry("bone", "c:bones"),
            Map.entry("gunpowder", "c:gunpowders"),
            Map.entry("enderpearl", "c:ender_pearls"),
            Map.entry("leather", "c:leathers"),
            Map.entry("egg", "c:eggs"),
            Map.entry("record", "c:music_discs"));

    /**
     * OreDictionary prefixes followed by a capitalized material ({@code ingotIron}, {@code dyeLightBlue}) and the
     * folder of the convention tags they became ({@code c:ingots/iron}, {@code c:dyes/light_blue}).
     */
    private static final Map<String, String> LEGACY_ORE_PREFIXES = Map.of(
            "ingot", "ingots",
            "ore", "ores",
            "dust", "dusts",
            "gem", "gems",
            "nugget", "nuggets",
            "dye", "dyes",
            "block", "storage_blocks",
            "rod", "rods",
            "crop", "crops");

    /**
     * Legacy item class names (lowercase, without package) and the predicate that replaces the {@code instanceof} test
     * the old loader did on the 1.12 class hierarchy.
     */
    private static final Map<String, Predicate<ItemStack>> LEGACY_CLASSES = Map.ofEntries(
            Map.entry("item", stack -> true),
            Map.entry("itemsword", stack -> stack.is(ItemTags.SWORDS)),
            Map.entry("itempickaxe", stack -> stack.is(ItemTags.PICKAXES)),
            Map.entry("itemaxe", stack -> stack.is(ItemTags.AXES)),
            Map.entry("itemspade", stack -> stack.is(ItemTags.SHOVELS)),
            Map.entry("itemhoe", stack -> stack.is(ItemTags.HOES)),
            Map.entry("itemtool", stack -> stack.is(ItemTags.PICKAXES) || stack.is(ItemTags.AXES) || stack.is(ItemTags.SHOVELS) || stack.is(ItemTags.HOES)),
            Map.entry("itembow", stack -> stack.getItem() instanceof BowItem),
            Map.entry("itemcrossbow", stack -> stack.getItem() instanceof CrossbowItem),
            Map.entry("itemshield", stack -> stack.getItem() instanceof ShieldItem),
            Map.entry("itemshears", stack -> stack.getItem() instanceof ShearsItem),
            Map.entry("itemfishingrod", stack -> stack.getItem() instanceof FishingRodItem),
            Map.entry("itemarmor", stack -> InvTweaksCore.isArmor(stack)),
            Map.entry("itemelytra", stack -> stack.has(DataComponents.GLIDER)),
            Map.entry("itemfood", stack -> stack.has(DataComponents.FOOD)),
            Map.entry("itemarrow", stack -> stack.is(ItemTags.ARROWS)),
            Map.entry("arrow", stack -> stack.is(ItemTags.ARROWS)),
            Map.entry("itempotion", stack -> stack.getItem() instanceof PotionItem),
            Map.entry("itemblock", stack -> stack.getItem() instanceof BlockItem),
            Map.entry("itembucket", stack -> stack.getItem() instanceof BucketItem),
            Map.entry("itemmonsterplacer", stack -> stack.getItem() instanceof SpawnEggItem));

    private static final List<IItemTreeListener> onLoadListeners = new ArrayList<>();
    private static InvTweaksItemTree tree;
    @Nullable
    private static String treeVersion;
    private static int itemOrder;
    private static int mergeChildren;
    private static LinkedList<String> categoryStack;
    private static LinkedList<Boolean> categoryPushStack;
    private static LinkedList<Boolean> mergeStack;
    private static boolean treeLoaded = false;

    private static void init() {
        treeVersion = null;
        tree = new InvTweaksItemTree();
        itemOrder = 0;
        mergeChildren = 0;
        categoryStack = new LinkedList<>();
        categoryPushStack = new LinkedList<>();
        mergeStack = new LinkedList<>();
    }

    public synchronized static InvTweaksItemTree load(@NotNull File file) throws Exception {
        init();

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        parser.parse(file, new InvTweaksItemTreeLoader());

        // Tree loaded event
        synchronized(onLoadListeners) {
            treeLoaded = true;
            for(@NotNull IItemTreeListener onLoadListener : onLoadListeners) {
                onLoadListener.onTreeLoaded(tree);
            }
        }

        return tree;
    }

    public synchronized static boolean isValidVersion(@NotNull File file) throws Exception {
        if(file.exists()) {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();

            VersionLoader loader = new VersionLoader();
            parser.parse(file, loader);
            return InvTweaksConst.TREE_VERSION.equals(loader.version);
        } else {
            return false;
        }
    }

    public synchronized static void addOnLoadListener(@NotNull IItemTreeListener listener) {
        onLoadListeners.add(listener);
        if(treeLoaded) {
            // Late event triggering
            listener.onTreeLoaded(tree);
        }
    }

    public synchronized static boolean removeOnLoadListener(IItemTreeListener listener) {
        return onLoadListeners.remove(listener);
    }

    /**
     * @return the tag a legacy OreDictionary name became, or null if there is none.
     */
    @Nullable
    static TagKey<Item> legacyOreTag(@NotNull String oreName) {
        String tag = LEGACY_ORE_TAGS.get(oreName);
        if(tag == null) {
            for(Map.Entry<String, String> prefix : LEGACY_ORE_PREFIXES.entrySet()) {
                String start = prefix.getKey();
                if(oreName.length() > start.length() && oreName.startsWith(start) && Character.isUpperCase(oreName.charAt(start.length()))) {
                    String material = oreName.substring(start.length()).replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
                    tag = "c:" + prefix.getValue() + "/" + material;
                    break;
                }
            }
        }
        return tag == null ? null : itemTag(tag);
    }

    /**
     * @return the predicate replacing a legacy item class name, or null if the name is unknown.
     */
    @Nullable
    static Predicate<ItemStack> legacyClass(@NotNull String className) {
        return LEGACY_CLASSES.get(className.substring(className.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT));
    }

    @Nullable
    private static TagKey<Item> itemTag(@NotNull String tag) {
        Identifier location = Identifier.tryParse(tag);
        return location == null ? null : TagKey.create(Registries.ITEM, location);
    }

    private int getNextItemOrder(boolean lastOrder) {
        if((lastOrder || mergeChildren > 0) && itemOrder > 0) { return itemOrder - 1; }
        return itemOrder++;
    }

    @Override
    public synchronized void startElement(String uri, String localName, String name, @NotNull Attributes attributes) throws SAXException {

        String newTreeVersion = attributes.getValue(ATTR_TREE_VERSION);
        String id = attributes.getValue(ATTR_ID);
        String tagAttr = attributes.getValue(ATTR_TAG);
        String oreDictNameAttr = attributes.getValue(ATTR_OREDICT_NAME);
        String className = attributes.getValue(ATTR_CLASS);
        String lastOrderValue = attributes.getValue(ATTR_LAST_ORDER);
        String mergeChildrenValue = attributes.getValue(ATTR_MERGE_CHILDREN);
        lastOrderValue = lastOrderValue == null ? "" : lastOrderValue.toLowerCase();
        boolean lastOrder = (lastOrderValue.equals("1") || lastOrderValue.equals("true") || lastOrderValue.equals("yes") || lastOrderValue.equals("t") || lastOrderValue.equals("y"));
        mergeChildrenValue = mergeChildrenValue == null ? "" : mergeChildrenValue.toLowerCase();
        boolean willMergeChildren = (mergeChildrenValue.equals("1") || mergeChildrenValue.equals("true") || mergeChildrenValue.equals("yes") || mergeChildrenValue.equals("t") || mergeChildrenValue.equals("y"));
        mergeStack.add(willMergeChildren);

        // Tree version
        if(treeVersion == null) {
            treeVersion = newTreeVersion;
        }

        boolean isMatcher = id != null || tagAttr != null || oreDictNameAttr != null || className != null;
        boolean isCategory = !isMatcher;
        String path = String.join("\\", categoryStack) + "\\" + name;

        if(attributes.getValue(ATTR_DATA) != null) {
            InventoryTweaksRevived.LOGGER.warn("Tree entry '{}': the data attribute is not supported any more{}", name, isMatcher ? ", skipping the entry" : "");
        } else if(isMatcher && attributes.getValue(ATTR_DAMAGE) != null) {
            InventoryTweaksRevived.LOGGER.warn("Tree entry '{}': items have no damage values any more, the damage attribute is ignored", name);
        }

        if(isMatcher) {
            if(attributes.getValue(ATTR_DATA) == null) {
                addMatcher(name, id, tagAttr, oreDictNameAttr, className, lastOrder, path);
            }
        } else {
            // Category
            if(categoryStack.isEmpty()) {
                // Root category
                tree.setRootCategory(new InvTweaksItemTreeCategory(name));
            } else {
                // Normal category
                tree.addCategory(categoryStack.getLast(), new InvTweaksItemTreeCategory(name));
            }

            if(attributes.getValue(ATTR_RANGE_DMIN) != null) {
                InventoryTweaksRevived.LOGGER.warn("Tree category '{}': damage ranges are not supported any more, ignoring them", name);
            }
            if(willMergeChildren) {
                //Try to get a new ID for the children to use.
                //(If an ancestor already set the flag, this will do nothing.)
                getNextItemOrder(lastOrder);
            }
            categoryStack.add(name);
        }
        categoryPushStack.add(isCategory);

        //This happens last so if this node got an ID and it was supposed to be new, it did.
        if(willMergeChildren) { mergeChildren++; }

    }

    private void addMatcher(String name, @Nullable String id, @Nullable String tagAttr, @Nullable String oreDictName, @Nullable String className, boolean lastOrder, String path) {
        if(categoryStack.isEmpty()) {
            InventoryTweaksRevived.LOGGER.warn("Tree entry '{}' is outside of any category, skipping it", name);
            return;
        }
        String category = categoryStack.getLast();
        if(id != null) {
            Identifier location = Identifier.tryParse(InvTweaksObfuscation.getNamespacedID(id));
            if(location == null || !BuiltInRegistries.ITEM.containsKey(location)) {
                InventoryTweaksRevived.LOGGER.warn("Unknown item id in tree: {}", id);
            }
            tree.addItem(category, new InvTweaksItemTreeItem(name, id, getNextItemOrder(lastOrder), path));
        } else if(tagAttr != null) {
            TagKey<Item> tag = itemTag(tagAttr);
            if(tag == null) {
                InventoryTweaksRevived.LOGGER.warn("Invalid item tag in tree: {}", tagAttr);
            } else {
                tree.registerTag(category, name, tag, getNextItemOrder(lastOrder), path);
            }
        } else if(oreDictName != null) {
            TagKey<Item> tag = legacyOreTag(oreDictName);
            if(tag == null) {
                InventoryTweaksRevived.LOGGER.warn("Unknown legacy ore name in tree: {}", oreDictName);
            } else {
                tree.registerTag(category, name, tag, getNextItemOrder(lastOrder), path);
            }
        } else if(className != null) {
            Predicate<ItemStack> predicate = legacyClass(className);
            if(predicate == null) {
                InventoryTweaksRevived.LOGGER.warn("Unknown legacy item class in tree: {}", className);
            } else {
                tree.registerPredicate(category, name, predicate, getNextItemOrder(lastOrder), path);
            }
        }
    }

    @Override
    public synchronized void endElement(String uri, String localName, @NotNull String name) throws SAXException {
        if(!categoryPushStack.isEmpty() && categoryPushStack.removeLast() && !categoryStack.isEmpty()) {
            categoryStack.removeLast();
        }
        if(!mergeStack.isEmpty()) {
            if(mergeStack.getLast() && mergeChildren > 0) { mergeChildren--; }
            mergeStack.removeLast();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        tree.endFileRead();
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        InventoryTweaksRevived.LOGGER.warn("Tree XML Warning: ", e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        InventoryTweaksRevived.LOGGER.error("Tree XML Error: ", e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        InventoryTweaksRevived.LOGGER.error("Tree XML Fatal Error: ", e);
    }

    private static class VersionLoader extends DefaultHandler {
        @Nullable
        String version;

        @Override
        public synchronized void startElement(String uri, String localName, String name, @NotNull Attributes attributes) throws SAXException {
            if(version == null) {
                version = attributes.getValue(ATTR_TREE_VERSION);
            }
        }
    }
}
