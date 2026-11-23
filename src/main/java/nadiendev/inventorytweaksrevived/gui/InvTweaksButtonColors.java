package nadiendev.inventorytweaksrevived.gui;

import java.util.Map;

/**
 * Colours of the symbols of our buttons (RGB), in one place so they are easy to tweak. Hovered buttons use a brighter
 * shade; disabled buttons stay grey.
 */
public final class InvTweaksButtonColors {
    public static final int FILL = 0x3FBF3F;
    public static final int EMPTY = 0xD64545;
    public static final int DEFAULT_SORT = 0x4A90E2;
    public static final int COLUMNS = 0xE0A030;
    public static final int ROWS = 0xA060D0;
    public static final int N_SHAPE = 0x2FB5A8;
    public static final int SETTINGS = 0xE8C030;
    /**
     * How much of the way to white a hovered symbol goes (0 to 1).
     */
    public static final float HOVER_BRIGHTEN = 0.35F;

    private static final Map<String, Integer> BY_LABEL = Map.of(
            InvTweaksGuiTransferButton.LABEL_TO_CONTAINER, FILL,
            InvTweaksGuiTransferButton.LABEL_TO_INVENTORY, EMPTY,
            InvTweaksGuiSortingButton.LABEL_DEFAULT, DEFAULT_SORT,
            InvTweaksGuiSortingButton.LABEL_COLUMNS, COLUMNS,
            InvTweaksGuiSortingButton.LABEL_ROWS, ROWS,
            InvTweaksGuiSortingButton.LABEL_NSHAPE, N_SHAPE,
            InvTweaksGuiSettingsButton.LABEL, SETTINGS);

    private InvTweaksButtonColors() {
    }

    /**
     * @return the ARGB colour of a button symbol, or -1 if the label has no colour of its own
     */
    public static int of(String label, boolean hovered) {
        Integer rgb = BY_LABEL.get(label);
        if(rgb == null) {
            return -1;
        }
        int color = rgb;
        if(hovered) {
            int r = (color >> 16) & 0xFF, g = (color >> 8) & 0xFF, b = color & 0xFF;
            r += (int) ((255 - r) * HOVER_BRIGHTEN);
            g += (int) ((255 - g) * HOVER_BRIGHTEN);
            b += (int) ((255 - b) * HOVER_BRIGHTEN);
            color = (r << 16) | (g << 8) | b;
        }
        return 0xFF000000 | color;
    }
}
