# ⚡ InventoryTweaksRevived — Changelog

## 🚀 [1.0.0-26.3] 23/11/2026 12:00

### Additions
- Compatibility with the menus of Sophisticated Storage and Sophisticated Backpacks: sorting buttons and middle click sort the storage slots only, never the upgrades
- Compatibility with the terminals of Applied Energistics 2 and AE2 Wireless Terminal Library: their sorting buttons only sort the player inventory, never the network or the terminal grids
- `menu_compat.json` entries can tell the chest slots (`chestSlots`), count the chest columns on the screen (`"rowSize": "auto"`) and put the sorting buttons on the player inventory (`inventoryButtons`)
- Our buttons move left when the screen already has its own widgets where they go
- Two transfer buttons on chests and Sophisticated storages/backpacks, next to the inventory label: ⇑ (fill) and ⇓ (empty): a click moves one stack of an item the other side already has, Shift/Ctrl+click moves everything
- New ♛ "Sort in N shape" button: lays items out along an N (down the first column, down the diagonal from the top-left corner to the bottom-right one, down the last column), each item family together; extra items fill the other slots row by row
- Built-in Mouse Tweaks behaviours (setting "Mouse Tweaks", on by default): Shift+drag quick-moves every stack the cursor passes over, dragging with a stack collects the same items, and the mouse wheel over a stack moves one item to the other inventory (up) or brings one from it (down)
- In the survival and creative inventory screens the sorting buttons stand in a column to the right of the panel
- New setting "Transfer arrows use hotbar" (Advanced settings, off by default): fill also takes items from the hotbar and empty also puts items into it
- In Sophisticated Storage/Backpacks screens our buttons replace Sophisticated's sort button and transfer arrows (its search box, "sort by" button and settings stay)

### Fixes
- The Ctrl+Alt drop shortcut no longer drops a second item
- Netherite armor now sorts before diamond armor
- Enchanted books are now sorted by their stored enchantments
- Golden tools no longer sort ahead of better tiers
- Sort on pickup no longer moves the tool you are holding
- A rules file without any valid rule is backed up and regenerated
- Sorting buttons did nothing in Sophisticated Storage/Backpacks screens (and other item-handler chests): empty slots were treated as locked
- Our buttons now get the click even when another widget of the screen lies under them (Sophisticated's search box)
- `.tree` files placed in `config/InvTweaks/trees/` are now merged into the item tree (they were looked for in `config/InvTweaks/` by mistake), in alphabetical order. The readme in that folder was rewritten

### Changes
- Ported to NeoForge 26.3 and Java 25
- Chest, inventory and creative screens, and the menus of other mods, are detected again for the 26.3 menus (`menu_compat.json` can describe menus the mod does not know)
- The item tree uses 26.3 item ids and item tags. Potions are grouped as potion, splash and lingering. The legacy `data=` NBT matching and the ore dictionary names no longer exist
- Translations are now JSON files (24 languages)
- The sort key is a normal key binding
- The "Browse modded tree files" button now opens the `trees` folder of this project. The old tree files for Minecraft 1.12 and 1.16 are not compatible with the 26.3 item ids and will mostly not match
- Settings screens rebuilt with the 26.3 UI
- JEI no longer reorders its ingredient list
- New coloured button symbols: ↺ default sort, ☲ columns, ☷ rows, ✎ settings
- Rows and columns sorting keep each item family together: all planks, all slabs, all logs... share one row (rows button) or one column (columns button), with no empty slots between families

### Lineas de codigo cambiadas
+1724 −1397
