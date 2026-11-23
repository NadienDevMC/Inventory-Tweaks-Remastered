package nadiendev.inventorytweaksrevived.itemtree;


import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import nadiendev.inventorytweaksrevived.api.IItemTree;
import nadiendev.inventorytweaksrevived.api.IItemTreeCategory;
import nadiendev.inventorytweaksrevived.api.IItemTreeItem;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

/**
 * Contains the whole hierarchy of categories and items, as defined in the XML item tree. Is used to recognize keywords
 * and store item orders.
 *
 * @author Jimeo Wan
 */
public class InvTweaksItemTree implements IItemTree {
    /**
     * All categories, stored by name
     */
    @NotNull
    private final Map<String, IItemTreeCategory> categories = new HashMap<>();
    /**
     * Items stored by registry id. A same ID can hold several names. Entries without an id (tags and predicates) are
     * not here, see {@link #matchers}.
     */
    @NotNull
    private final Map<String, List<IItemTreeItem>> itemsById = new HashMap<>(500);
    /**
     * Items stored by name. A same name can match several IDs.
     */
    @NotNull
    private final Map<String, List<IItemTreeItem>> itemsByName = new HashMap<>(500);
    /**
     * Entries that match by tag or predicate, in the order they were added. They are evaluated on every query because
     * tags are only available once a world is loaded.
     */
    @NotNull
    private final List<IItemTreeItem> matchers = new ArrayList<>();
    /**
     * Position at which each entry was added to the tree, to return the entries covering a stack in the XML order.
     */
    @NotNull
    private final Map<IItemTreeItem, Integer> addedAt = new IdentityHashMap<>(1000);

    private String rootCategory;

    private int highestOrder = 0;

    private int lastTreeOrder = 0;

    /**
     * Checks if given item ID matches a given keyword (either the item's name is the keyword, or it is in the keyword
     * category)
     */
    @Override
    public boolean matches(@Nullable List<IItemTreeItem> items, @NotNull String keyword) {

        if(items == null) {
            return false;
        }

        // The keyword is an item
        for(@NotNull IItemTreeItem item : items) {
            if(item.getName() != null && item.getName().equals(keyword)) {
                return true;
            }
        }

        // The keyword is a category
        IItemTreeCategory category = getCategory(keyword);
        if(category != null) {
            for(IItemTreeItem item : items) {
                if(category.contains(item)) {
                    return true;
                }
            }
        }

        // Everything is stuff
        return keyword.equals(rootCategory);

    }

    @Override
    public int getKeywordDepth(String keyword) {
        try {
            return getRootCategory().findKeywordDepth(keyword);
        } catch(NullPointerException e) {
            InventoryTweaksRevived.LOGGER.error("The root category is missing: {}", e.getMessage());
            return 0;
        }
    }

    @Override
    public int getKeywordOrder(String keyword) {
        List<IItemTreeItem> items = getItems(keyword);
        if(items != null && items.size() != 0) {
            return items.get(0).getOrder();
        } else {
            try {
                return getRootCategory().findCategoryOrder(keyword);
            } catch(NullPointerException e) {
                InventoryTweaksRevived.LOGGER.error("The root category is missing: {}", e.getMessage());
                return -1;
            }
        }
    }

    /**
     * Checks if the given keyword is valid (i.e. represents either a registered item or a registered category)
     */
    @Override
    public boolean isKeywordValid(String keyword) {

        // Is the keyword an item?
        if(containsItem(keyword)) {
            return true;
        }

        // Or maybe a category ?
        else {
            IItemTreeCategory category = getCategory(keyword);
            return category != null;
        }
    }

    /**
     * Returns a reference to all categories.
     */
    @NotNull
    @Override
    public Collection<IItemTreeCategory> getAllCategories() {
        return categories.values();
    }

    @Override
    public IItemTreeCategory getRootCategory() {
        return categories.get(rootCategory);
    }

    @Override
    public void setRootCategory(@NotNull IItemTreeCategory category) {
        rootCategory = category.getName();
        categories.put(rootCategory, category);
    }

    @Override
    public IItemTreeCategory getCategory(String keyword) {
        return categories.get(keyword);
    }

    /**
     * An item is unknown if the tree neither lists it nor covers it with a tag or a predicate.
     */
    @Override
    public boolean isItemUnknown(@NotNull Identifier id) {
        if(itemsById.containsKey(id.toString())) {
            return false;
        }
        if(!BuiltInRegistries.ITEM.containsKey(id)) {
            return true;
        }
        ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.getValue(id));
        return matchers.stream().noneMatch(matcher -> matcher.matches(stack));
    }

    /**
     * Returns the entries covering the stack, in the order they appear in the XML. An item that the tree does not know
     * gets a new entry in the root category, sorted after everything that was already there.
     */
    @NotNull
    @Override
    public List<IItemTreeItem> getItems(@NotNull ItemStack stack) {
        if(stack.isEmpty()) {
            return new ArrayList<>();
        }

        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        @NotNull List<IItemTreeItem> items = new ArrayList<>();
        List<IItemTreeItem> itemsWithId = itemsById.get(id);
        if(itemsWithId != null) {
            items.addAll(itemsWithId);
        }

        boolean matched = false;
        for(IItemTreeItem matcher : matchers) {
            if(matcher.matches(stack)) {
                items.add(matcher);
                matched = true;
            }
        }
        if(matched) {
            items.sort(Comparator.comparingInt(addedAt::get));
        }

        // If there's no matching item, create a new one
        if(items.isEmpty()) {
            @NotNull IItemTreeItem newItem = new InvTweaksItemTreeItem(id, id, highestOrder + 1, getRootCategory().getName() + "\\_uncategorized\\" + id);
            addItem(getRootCategory().getName(), newItem);
            items.add(newItem);
        }

        return items;

    }

    @NotNull
    @Override
    public List<IItemTreeItem> getItems(@Nullable String id, int damage) {
        Identifier location = id == null ? null : Identifier.tryParse(InvTweaksObfuscation.getNamespacedID(id));
        if(location == null || !BuiltInRegistries.ITEM.containsKey(location)) {
            return new ArrayList<>();
        }
        return getItems(new ItemStack(BuiltInRegistries.ITEM.getValue(location)));
    }

    @Override
    public List<IItemTreeItem> getItems(String name) {
        return itemsByName.get(name);
    }

    /**
     * Returns the names of the tree entries that cover the stack and of every category containing any of them.
     */
    @NotNull
    @Override
    public List<String> getKeywordsFromItem(@NotNull ItemStack stack) {
        List<IItemTreeItem> items = getItems(stack);
        Set<String> keywords = new LinkedHashSet<>();
        for(IItemTreeItem item : items) {
            keywords.add(item.getName());
        }
        for(IItemTreeCategory category : categories.values()) {
            for(IItemTreeItem item : items) {
                if(category.contains(item)) {
                    keywords.add(category.getName());
                    break;
                }
            }
        }
        return new ArrayList<>(keywords);
    }

    @Override
    public boolean containsItem(String name) {
        return itemsByName.containsKey(name);
    }

    @Override
    public boolean containsCategory(String name) {
        return categories.containsKey(name);
    }

    @NotNull
    @Override
    public IItemTreeCategory addCategory(String parentCategory, String newCategory) throws NullPointerException {
        @NotNull IItemTreeCategory addedCategory = new InvTweaksItemTreeCategory(newCategory);
        addCategory(parentCategory, addedCategory);
        return addedCategory;
    }

    /**
     * @param damage ignored, items have no damage variants any more
     */
    @NotNull
    @Override
    public IItemTreeItem addItem(String parentCategory, String name, String id, int damage, int order) throws NullPointerException {
        @NotNull InvTweaksItemTreeItem addedItem = new InvTweaksItemTreeItem(name, id, order, getRootCategory().findKeywordPath(parentCategory) + "\\" + name);
        addItem(parentCategory, addedItem);
        return addedItem;
    }

    @Override
    public void addCategory(String parentCategory, @NotNull IItemTreeCategory newCategory) throws NullPointerException {
        // Build tree
        categories.get(parentCategory).addCategory(newCategory);

        // Register category
        categories.put(newCategory.getName(), newCategory);
    }

    @Override
    public void addItem(String parentCategory, @NotNull IItemTreeItem newItem) throws NullPointerException {
        highestOrder = Math.max(highestOrder, newItem.getOrder());

        // Build tree
        categories.get(parentCategory).addItem(newItem);
        addedAt.put(newItem, addedAt.size());

        // Register item
        itemsByName.computeIfAbsent(newItem.getName(), k -> new ArrayList<>()).add(newItem);
        if(newItem.getId() != null) {
            itemsById.computeIfAbsent(newItem.getId(), k -> new ArrayList<>()).add(newItem);
        } else {
            matchers.add(newItem);
        }
    }

    public int getLastTreeOrder() {
        return lastTreeOrder;
    }

    @Override
    public void registerTag(String category, String name, @NotNull TagKey<Item> tag, int order, String path) {
        registerPredicate(category, name, stack -> stack.is(tag), order, path);
    }

    @Override
    public void registerPredicate(String category, String name, @NotNull Predicate<ItemStack> predicate, int order, String path) {
        addItem(category, new InvTweaksItemTreeItem(name, predicate, order, path));
    }

    public void endFileRead() {
        //Remember where the last entry was placed in the tree for the API to leave these unsorted.
        lastTreeOrder = highestOrder;
    }
}
