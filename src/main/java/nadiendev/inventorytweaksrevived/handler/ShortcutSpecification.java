package nadiendev.inventorytweaksrevived.handler;

public class ShortcutSpecification {
    private final Action action;
    private final Target target;
    private Scope scope;

    public ShortcutSpecification(Action a, Target t, Scope s) {
        action = a;
        target = t;
        scope = s;
    }

    public Action getAction() {
        return action;
    }

    public Target getTarget() {
        return target;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope_) {
        scope = scope_;
    }

    public enum Action {
        MOVE, DROP
    }

    public enum Target {
        UP, DOWN, HOTBAR_SLOT, UNSPECIFIED
    }

    public enum Scope {
        EVERYTHING, ALL_ITEMS, ONE_STACK, ONE_ITEM
    }
}
