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

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface IItemTreeItem extends Comparable<IItemTreeItem> {
    String getName();

    /**
     * @return the registry id ({@code namespace:path}) of the item, or null if this entry is a tag or a predicate that
     * can match several items.
     */
    @Nullable
    String getId();

    /**
     * Items have no damage variants any more. Always {@link nadiendev.inventorytweaksrevived.util.InvTweaksConst#DAMAGE_WILDCARD},
     * kept for rules written for older versions.
     */
    int getDamage();

    int getOrder();

    String getPath();

    /**
     * @return true if the stack is covered by this entry.
     */
    boolean matches(ItemStack stack);
}
