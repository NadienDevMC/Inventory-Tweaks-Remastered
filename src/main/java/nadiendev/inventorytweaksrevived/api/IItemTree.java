/*
 * Copyright (c) 2013 Andrew Crocker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nadiendev.inventorytweaksrevived.api;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public interface IItemTree {
    /**
     * Registers an entry matching every item of a tag. The tag is evaluated on each query, as tags only exist once a
     * world is loaded.
     */
    void registerTag(String category, String name, TagKey<Item> tag, int order, String path);

    void registerPredicate(String category, String name, Predicate<ItemStack> predicate, int order, String path);

    boolean matches(List<IItemTreeItem> items, String keyword);

    boolean isKeywordValid(String keyword);

    Collection<IItemTreeCategory> getAllCategories();

    IItemTreeCategory getRootCategory();

    void setRootCategory(IItemTreeCategory category);

    IItemTreeCategory getCategory(String keyword);

    boolean isItemUnknown(Identifier id);

    List<IItemTreeItem> getItems(ItemStack stack);

    /**
     * @param damage ignored, kept for rules written for older versions
     */
    List<IItemTreeItem> getItems(String id, int damage);

    List<IItemTreeItem> getItems(String name);

    /**
     * @return the names of every item entry and category of the tree that contain the stack
     */
    List<String> getKeywordsFromItem(ItemStack stack);

    boolean containsItem(String name);

    boolean containsCategory(String name);

    IItemTreeCategory addCategory(String parentCategory, String newCategory) throws NullPointerException;

    void addCategory(String parentCategory, IItemTreeCategory newCategory) throws NullPointerException;

    IItemTreeItem addItem(String parentCategory, String name, String id, int damage, int order) throws NullPointerException;

    void addItem(String parentCategory, IItemTreeItem newItem) throws NullPointerException;

    int getKeywordDepth(String keyword);

    int getKeywordOrder(String keyword);
}
