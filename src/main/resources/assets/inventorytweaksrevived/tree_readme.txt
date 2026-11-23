MODDED TREE FILES

Put files named "something.tree" in this folder (config/InvTweaks/trees/). They are merged into the item tree: InvTweaksTree.txt, or "minecraft.tree" if you keep one in this folder. Files are merged in alphabetical order of their names, ignoring case. A file that is not valid XML is skipped with a warning in the log, and the others still load.

WHEN IT IS READ
When you join a world, and again on the next sort, item pickup or middle click (or when you open the Inventory Tweaks settings) after a .tree file was added, changed or removed. No restart needed.

FORMAT
A .tree file has the same structure as InvTweaksTree.txt, with "stuff" as the root element:
- An element with id="namespace:path" is an item ("minecraft:" is assumed if you leave the namespace out).
- An element with tag="namespace:path" is every item of an item tag.
- Any other element is a category. Its name is a keyword you can use in InvTweaksRules.txt, and it contains items, tags and other categories.
The name of every element is a keyword too. Do not use spaces or colons in it.

HOW FILES ARE MERGED
- A category is merged into one of the base tree only when it has the same name AND the same parent: categories are matched by name only under the same parent, so repeat the full path from the root (for example equipment > weapon > sword). A category with the same name under a different parent is a new category. Anything new is added at the end of its category, and a new category is added at the end of its parent.
- Items are sorted in the order they appear in the tree.
- mergePrevious="true": the entry is sorted together with the entry before it, as if they were one.
- mergeChildren="true" (on a category): all its entries are sorted together.
- treeOrder="N" (optional, on any entry of a .tree file): decides where a new entry goes inside its category when files are merged. It goes before the first entry of that category whose number is higher. Entries of the base tree count as 0, and an entry without treeOrder takes the number of the entry just before it in the same file (the first one counts as the highest possible), so without treeOrder new entries end up at the end. A negative number puts it at the start.

EXAMPLE
<?xml version="1.0" encoding="utf-8" ?>
<stuff>
    <equipment>
        <weapon>
            <sword>
                <rubySword id="examplemod:ruby_sword"/>
            </sword>
        </weapon>
    </equipment>
    <resources>
        <miningResources>
            <rubyGems tag="c:gems/ruby"/>
        </miningResources>
    </resources>
</stuff>
This adds the ruby sword to the sword category, and every item of the c:gems/ruby tag to the mining resources. The names rubySword and rubyGems can be used in the rules file.

NOT SUPPORTED
oreDictName, data= and the old class names of Minecraft 1.12 (ItemSword and so on) do not work in this version: use id and tag instead. Tree files made for Minecraft 1.12 or 1.16 will mostly not match anything, because item ids changed. An unknown id is only reported as a warning in the log.

THIS README
This readme.txt is managed by the mod: it is refreshed every time the game starts if it is missing or different from the one bundled with the mod, so do not edit it.

SHARED TREE FILES
https://github.com/NadienDevMC/Inventory-Tweaks-Remastered/tree/Neo/1.21.1/trees
