# Porting Inventory Tweaks to a newer Minecraft version

Moving the code to the new Minecraft/NeoForge APIs is only half of a port. Several **data files** and **version pinned lists** describe the game itself, so they go stale silently (nothing crashes: items just stop being sorted well, or buttons disappear in some menus). Update all of these on every port.

## 1. Vanilla item tree: `src/main/resources/assets/inventorytweaksrevived/itemtree.xml`
- Every vanilla item of the new version must be categorized, otherwise it falls into the general bucket at the end and sorts badly. Items added or renamed since the previous version are the usual gap.
- Set `treeVersion` to the new version: the mod renames an older user file to `.bak` and extracts the new one when the versions differ.
- How to find what exists: the vanilla client jar of the new version (in the Gradle caches) lists the items. For 1.21.1 the oracle was the file names of `assets/minecraft/models/item/*.json`, checked against the `item.minecraft.*` / `block.minecraft.*` keys of `lang/en_us.json`. In newer versions the per-item definitions may live somewhere else (for example an `assets/<namespace>/items/` folder): check where before relying on the same rule.
- Check mechanically that every `id=` in the file is a real item and that every real item appears exactly once, and that the file parses as XML. Use `tag="minecraft:..."` only for tags that exist in `data/minecraft/tags/item/`.

## 2. Modded tree files: `trees/*.tree` (repository root)
- One file per mod; players copy them into `config/InvTweaks/trees/` (the settings button "Browse modded tree files" opens this folder of the repository, see `InvTweaksConst.TREE_URL`).
- They are version specific: item ids change between versions and mods get renamed, merged or abandoned. Re-check each file against the real mod jars of the new version (read `assets/<modid>/models/item/*.json` inside the jar) and drop ids that no longer exist. Remember to add the mods that appeared since the last port and to map renamed ones.
- Only `id="namespace:path"`, `tag="namespace:path"`, categories and `mergePrevious` are supported by the loader. `data=`, `oreDictName`, numeric damage and the old `class="ItemXxx"` names are ignored.
- The old third party repository `IMarvinTPA/InventoryTweaksTrees` only has trees up to 1.16.

## 3. Menu compatibility: `menu_compat.json`
- Menus of other mods are matched by class name. Class names and package names change between mod versions: re-check them against the jars of the new versions (Sophisticated Storage/Backpacks, Applied Energistics 2, AE2 Wireless Terminal Library, ...).
- Methods named by `chestSlots` (for Sophisticated: `StorageContainerMenuBase.getNumberOfStorageInventorySlots()`, which counts the storage slots that start the menu) must still exist, be public and take no parameters (`javap -p` on the jar); otherwise the mod logs a warning and falls back to guessing the chest, which then includes any extra slot of the menu.
- Vanilla menus are listed in `container/VanillaSlotMaps`: compare each one with the new version's menu class (slot order and counts change when menus are reworked).

## 4. Code that depends on Minecraft internals
- `mixin/EditBoxMixin` targets `EditBox.setFocused(boolean)`: confirm the method still exists on that class and has no early return before the injection point.
- Tags used by the tool/armor comparators (`ItemTags.PICKAXES`, `SWORDS`, ...), data components (`DataComponents.FOOD`, `CUSTOM_NAME`), `TieredItem`/`Tier` (the harvest-level proxy is `Tier.getAttackDamageBonus()`), `ArmorItem`, `EnchantmentHelper.getEnchantmentsForCrafting`: verify each against the new version's sources; several of them change between releases.
- GUI sprites for the buttons (`widget/button`, `widget/button_highlighted`, `widget/button_disabled`) and the `GuiGraphics`/`Screen` API.
- Screen events and their cancel semantics (`ScreenEvent.Init.Post`, `MouseButtonPressed.Pre`, `MouseButtonReleased.Pre`, `ItemEntityPickupEvent.Post`, `ClientPlayerNetworkEvent.LoggingIn`).
- Network: payload registration (`RegisterPayloadHandlersEvent`, optional payloads) and the login check `connection.hasChannel(...)`.

## 5. Build and test setup
- `gradle.properties`: `minecraft_version`, `neo_version`, `loader_version`, Parchment mappings, and the test only mods loaded by `runClient` through CurseMaven (`ae2_*`, `guideme_*`, `sophisticatedcore_*`, `sophisticatedbackpacks_*`, `sophisticatedstorage_*`, `ae2wtlib_*`): each needs its CurseForge project id and the file id of a build for the NEW version (the public CFWidget API, `https://api.cfwidget.com/<projectId>`, lists the file ids).
- Language files are generated: `./gradlew runData` regenerates `src/generated/resources`. Translations live in the providers under `data/lang`.
- Gradle 8.11 needs a JDK it supports to run (JDK 21 worked; very new JDKs such as 25 failed with "Unsupported class file major version").
- `.github/workflows/build.yml`: Minecraft version, Java version and the CurseForge game version names.

## 6. After porting
Run the in game checklist: sorting (R key, middle click, chest buttons), shortcuts, auto refill, settings screens, creative inventory tabs, text field focus (typing `r` in an anvil must not sort), the Sophisticated and AE2 menus, a server with and without the mod, and check `logs/latest.log` for `Unknown item id in tree` after sorting once.

## Tree port notes: 1.21.1 -> 26.1.2
- Vanilla oracle in 26.1.2: `assets/minecraft/items/*.json` of the client jar (1506 items; per-item models are no longer the source). 174 items added (pale oak set, shelves, copper tools/armor/chests/bars/chains/lanterns/golem statues, spears, nautilus armor, colored bundles and harnesses, resin, new spawn eggs and discs...), `chain` renamed to `iron_chain`, tag `minecraft:trim_templates` no longer exists.
- Modded trees were checked against the jars of the ATM11 (26.1.2) instance. Mods with no 26.1.2 jar there were left untouched and are unverified. Mystical Agriculture crop items (`*_essence`/`*_seeds`) are data driven, so they cannot be checked from the jar and were kept.

## Tree port notes: 26.1.2 -> 26.2
- Vanilla oracle: `assets/minecraft/items/*.json` of `minecraft_26.2_client.jar` (1537 items, 31 added, none removed or renamed): sulfur and cinnabar stone sets (block, polished, bricks, chiseled, slab, stairs, wall), `sulfur_spike`, `potent_sulfur`, `sulfur_cube_spawn_egg`, `sulfur_cube_bucket`, `music_disc_bounce`. `treeVersion` is `26.2`.
- Modded trees: no 26.2 mod jar was available (the CurseForge instances are 26.1.2 and 1.21.1), so all `trees/*.tree` files were left unchanged and are unverified for 26.2.

## Tree port notes: 26.2 -> 26.3
- Vanilla oracle: `assets/minecraft/items/*.json` of `minecraft_26.3_client.jar` (1658 items, 121 added, none removed or renamed): poplar wood set (log, wood, stripped, planks, stairs, slab, fence, gate, door, trapdoor, button, plate, sign, hanging sign, shelf, boat, chest boat, sapling) and three poplar leaves, `red_shrub`, `shelf_mushroom`, `straw_bed`, 16 cushions, 16 wool slabs/stairs and 16 concrete slabs/stairs, 16 structure/village maps. `treeVersion` is `26.3`.
- Modded trees: no 26.3 mod jar was available (the CurseForge instances are 26.1.2 and 1.21.1), so all `trees/*.tree` files were left unchanged and are unverified for 26.3.
