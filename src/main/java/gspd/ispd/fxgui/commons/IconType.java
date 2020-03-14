package gspd.ispd.fxgui.commons;

import java.util.HashMap;
import java.util.Objects;

public class IconType {

    private static final HashMap<String, IconType> map = new HashMap<>();

    private IconType(IconType parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public static IconType type(IconType parent, String name) {
        if (!map.containsKey(name)) {
            map.put(name, new IconType(parent, name));
        }
        return map.get(name);
    }

    public static IconType type(String name) {
        return type(null, name);
    }

    private IconType parent;
    public IconType getParent() {
        return parent;
    }

    private String name;
    public String getName() {
        return name;
    }

    public boolean isTypeOf(IconType type) {
        IconType currentType = this;
        boolean success = false;
        while (currentType != null) {
            if (currentType == type) {
                success = true;
                break;
            }
            currentType = currentType.getParent();
        }
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IconType iconType = (IconType) o;
        return Objects.equals(parent, iconType.parent) &&
                name.equals(iconType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, name);
    }
}
