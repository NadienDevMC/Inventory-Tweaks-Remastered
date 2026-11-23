package nadiendev.inventorytweaksrevived.itemtree;

import nadiendev.inventorytweaksrevived.api.IItemTreeItem;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Representation of an item in the item tree. It is either a single item, identified by its registry id, or a matcher
 * (a tag or a predicate) covering any number of items, which has no id.
 *
 * @author Jimeo Wan
 */
public class InvTweaksItemTreeItem implements IItemTreeItem {

    private final String name;
    @Nullable
    private final String id;
    @Nullable
    private final Predicate<ItemStack> matcher;
    private final int order;
    private final String path;

    /**
     * @param name_  The item name
     * @param id_    The item registry id, {@code namespace:path} (the namespace defaults to {@code minecraft})
     * @param order_ The item order while sorting
     */
    public InvTweaksItemTreeItem(String name_, String id_, int order_, String path_) {
        name = name_;
        id = InvTweaksObfuscation.getNamespacedID(id_);
        matcher = null;
        order = order_;
        path = path_;
    }

    /**
     * Creates an entry that covers every stack accepted by the matcher.
     */
    public InvTweaksItemTreeItem(String name_, @NotNull Predicate<ItemStack> matcher_, int order_, String path_) {
        name = name_;
        id = null;
        matcher = matcher_;
        order = order_;
        path = path_;
    }

    @Override
    public String getName() {
        return name;
    }

    @Nullable
    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getDamage() {
        return InvTweaksConst.DAMAGE_WILDCARD;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean matches(@NotNull ItemStack stack) {
        if(matcher != null) {
            return matcher.test(stack);
        }
        return Objects.equals(id, BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
    }

    /**
     * Warning: the item equality is not reflective. Single items are equal to any entry with the same id; matchers are
     * only equal to themselves.
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if(!(o instanceof IItemTreeItem)) {
            return false;
        }
        if(id == null) {
            return this == o;
        }
        return id.equals(((IItemTreeItem) o).getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NotNull IItemTreeItem item) {
        return item.getOrder() - order;
    }

}
