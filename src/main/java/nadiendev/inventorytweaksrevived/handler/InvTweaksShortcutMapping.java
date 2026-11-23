package nadiendev.inventorytweaksrevived.handler;

import com.mojang.blaze3d.platform.InputConstants;
import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A combination of keys that has to be held together to trigger a shortcut. Key names are the ones written in the
 * configuration file: the legacy (LWJGL 2) names are kept, so existing configurations keep working.
 *
 * @author Jimeo Wan
 */
public class InvTweaksShortcutMapping {

    /**
     * Accepted key names (without the optional {@code KEY_} prefix) and the GLFW key code they stand for.
     */
    private static final Map<String, Integer> KEY_CODES = new HashMap<>();

    static {
        for(char letter = 'A'; letter <= 'Z'; letter++) {
            KEY_CODES.put(String.valueOf(letter), GLFW.GLFW_KEY_A + (letter - 'A'));
        }
        for(int digit = 0; digit <= 9; digit++) {
            KEY_CODES.put(String.valueOf(digit), GLFW.GLFW_KEY_0 + digit);
            KEY_CODES.put("NUMPAD" + digit, GLFW.GLFW_KEY_KP_0 + digit);
        }
        for(int number = 1; number <= 12; number++) {
            KEY_CODES.put("F" + number, GLFW.GLFW_KEY_F1 + (number - 1));
        }

        KEY_CODES.put("LSHIFT", GLFW.GLFW_KEY_LEFT_SHIFT);
        KEY_CODES.put("RSHIFT", GLFW.GLFW_KEY_RIGHT_SHIFT);
        KEY_CODES.put("LCONTROL", GLFW.GLFW_KEY_LEFT_CONTROL);
        KEY_CODES.put("RCONTROL", GLFW.GLFW_KEY_RIGHT_CONTROL);
        KEY_CODES.put("LCTRL", GLFW.GLFW_KEY_LEFT_CONTROL);
        KEY_CODES.put("RCTRL", GLFW.GLFW_KEY_RIGHT_CONTROL);
        KEY_CODES.put("LALT", GLFW.GLFW_KEY_LEFT_ALT);
        KEY_CODES.put("RALT", GLFW.GLFW_KEY_RIGHT_ALT);
        KEY_CODES.put("LMENU", GLFW.GLFW_KEY_LEFT_ALT);
        KEY_CODES.put("RMENU", GLFW.GLFW_KEY_RIGHT_ALT);

        KEY_CODES.put("UP", GLFW.GLFW_KEY_UP);
        KEY_CODES.put("DOWN", GLFW.GLFW_KEY_DOWN);
        KEY_CODES.put("LEFT", GLFW.GLFW_KEY_LEFT);
        KEY_CODES.put("RIGHT", GLFW.GLFW_KEY_RIGHT);

        KEY_CODES.put("SPACE", GLFW.GLFW_KEY_SPACE);
        KEY_CODES.put("TAB", GLFW.GLFW_KEY_TAB);
        KEY_CODES.put("RETURN", GLFW.GLFW_KEY_ENTER);
        KEY_CODES.put("ENTER", GLFW.GLFW_KEY_ENTER);
        KEY_CODES.put("BACK", GLFW.GLFW_KEY_BACKSPACE);
        KEY_CODES.put("INSERT", GLFW.GLFW_KEY_INSERT);
        KEY_CODES.put("DELETE", GLFW.GLFW_KEY_DELETE);
        KEY_CODES.put("HOME", GLFW.GLFW_KEY_HOME);
        KEY_CODES.put("END", GLFW.GLFW_KEY_END);
        KEY_CODES.put("PRIOR", GLFW.GLFW_KEY_PAGE_UP);
        KEY_CODES.put("NEXT", GLFW.GLFW_KEY_PAGE_DOWN);
        KEY_CODES.put("CAPITAL", GLFW.GLFW_KEY_CAPS_LOCK);
    }

    @NotNull
    private final List<Integer> keysToHold = new ArrayList<>();

    private boolean valid = true;

    public InvTweaksShortcutMapping(int keyCode) {
        keysToHold.add(keyCode);
    }

    /**
     * An unknown key name is logged and makes the whole mapping invalid (see {@link #isValid()}), it never throws.
     */
    public InvTweaksShortcutMapping(@NotNull String... keyNames) {
        for(String keyName : keyNames) {
            // Accept both KEY_### and ###, in case someone takes the old LWJGL Javadoc at face value
            String normalizedName = keyName.trim().toUpperCase(Locale.ROOT);
            if(normalizedName.startsWith("KEY_")) {
                normalizedName = normalizedName.substring("KEY_".length());
            }
            Integer keyCode = KEY_CODES.get(normalizedName);
            if(keyCode == null) {
                InventoryTweaksRevived.LOGGER.warn("Unknown key name '{}' in a shortcut mapping, the mapping is ignored.", keyName);
                valid = false;
            } else {
                keysToHold.add(keyCode);
            }
        }
        if(keysToHold.isEmpty()) {
            valid = false;
        }
    }

    public static boolean isKeyDown(int keyCode) {
        return keyCode > 0 && InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), keyCode);
    }

    /**
     * @return false if the mapping contains an unknown key name and so can never be triggered.
     */
    public boolean isValid() {
        return valid;
    }

    public boolean isTriggered(@NotNull Map<Integer, Boolean> pressedKeys) {
        if(!valid) {
            return false;
        }
        for(Integer keyToHold : keysToHold) {
            if(!pressedKeys.getOrDefault(keyToHold, false)) {
                return false;
            }
            // AltGr also activates LCtrl, make sure the real LCtrl has been pressed
            if(keyToHold == GLFW.GLFW_KEY_LEFT_CONTROL && isKeyDown(GLFW.GLFW_KEY_RIGHT_ALT)) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public List<Integer> getKeyCodes() {
        return this.keysToHold;
    }
}
