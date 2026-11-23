package nadiendev.inventorytweaksrevived.handler;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.sdl.SDLMouse;
import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

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
     * Accepted key names (without the optional {@code KEY_} prefix) and the SDL scancode they stand for (the key codes of InputConstants).
     */
    private static final Map<String, Integer> KEY_CODES = new HashMap<>();

    static {
        for(char letter = 'A'; letter <= 'Z'; letter++) {
            KEY_CODES.put(String.valueOf(letter), InputConstants.KEY_A + (letter - 'A'));
        }
        for(int digit = 0; digit <= 9; digit++) {
            KEY_CODES.put(String.valueOf(digit), digit == 0 ? InputConstants.KEY_0 : InputConstants.KEY_1 + (digit - 1));
            KEY_CODES.put("NUMPAD" + digit, digit == 0 ? InputConstants.KEY_NUMPAD0 : InputConstants.KEY_NUMPAD1 + (digit - 1));
        }
        for(int number = 1; number <= 12; number++) {
            KEY_CODES.put("F" + number, InputConstants.KEY_F1 + (number - 1));
        }

        KEY_CODES.put("LSHIFT", InputConstants.KEY_LSHIFT);
        KEY_CODES.put("RSHIFT", InputConstants.KEY_RSHIFT);
        KEY_CODES.put("LCONTROL", InputConstants.KEY_LCONTROL);
        KEY_CODES.put("RCONTROL", InputConstants.KEY_RCONTROL);
        KEY_CODES.put("LCTRL", InputConstants.KEY_LCONTROL);
        KEY_CODES.put("RCTRL", InputConstants.KEY_RCONTROL);
        KEY_CODES.put("LALT", InputConstants.KEY_LALT);
        KEY_CODES.put("RALT", InputConstants.KEY_RALT);
        KEY_CODES.put("LMENU", InputConstants.KEY_LALT);
        KEY_CODES.put("RMENU", InputConstants.KEY_RALT);

        KEY_CODES.put("UP", InputConstants.KEY_UP);
        KEY_CODES.put("DOWN", InputConstants.KEY_DOWN);
        KEY_CODES.put("LEFT", InputConstants.KEY_LEFT);
        KEY_CODES.put("RIGHT", InputConstants.KEY_RIGHT);

        KEY_CODES.put("SPACE", InputConstants.KEY_SPACE);
        KEY_CODES.put("TAB", InputConstants.KEY_TAB);
        KEY_CODES.put("RETURN", InputConstants.KEY_RETURN);
        KEY_CODES.put("ENTER", InputConstants.KEY_RETURN);
        KEY_CODES.put("BACK", InputConstants.KEY_BACKSPACE);
        KEY_CODES.put("INSERT", InputConstants.KEY_INSERT);
        KEY_CODES.put("DELETE", InputConstants.KEY_DELETE);
        KEY_CODES.put("HOME", InputConstants.KEY_HOME);
        KEY_CODES.put("END", InputConstants.KEY_END);
        KEY_CODES.put("PRIOR", InputConstants.KEY_PAGEUP);
        KEY_CODES.put("NEXT", InputConstants.KEY_PAGEDOWN);
        KEY_CODES.put("CAPITAL", InputConstants.KEY_CAPSLOCK);
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
        return keyCode > 0 && InputConstants.isKeyDown(keyCode);
    }

    /**
     * Polls a mouse button (InputConstants.MOUSE_BUTTON_* numbering, 1 = left) straight from SDL.
     */
    public static boolean isMouseButtonDown(int button) {
        return button > 0 && (SDLMouse.SDL_GetMouseState(null, null) & (1 << (button - 1))) != 0;
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
            if(keyToHold == InputConstants.KEY_LCONTROL && isKeyDown(InputConstants.KEY_RALT)) {
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
