package gspd.ispd.commons;

import java.util.HashMap;
import java.util.Objects;

public class ISPDType {

    private static final HashMap<String, ISPDType> map = new HashMap<>();

    private ISPDType(ISPDType parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public static ISPDType type(ISPDType parent, String name) {
        if (!map.containsKey(name)) {
            map.put(name, new ISPDType(parent, name));
        }
        return map.get(name);
    }

    public static ISPDType type(String name) {
        return type(null, name);
    }

    private ISPDType parent;
    public ISPDType getParent() {
        return parent;
    }

    private String name;
    public String getName() {
        return name;
    }

    private String fiendlyName;
    public String getFiendlyName() {
        return fiendlyName;
    }
    public void setFiendlyName(String fiendlyName) {
        this.fiendlyName = fiendlyName;
    }

    public boolean isTypeOf(ISPDType type) {
        ISPDType currentType = this;
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
        ISPDType iconType = (ISPDType) o;
        return Objects.equals(parent, iconType.parent) &&
                name.equals(iconType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, name);
    }

    @Override
    public String toString() {
        String name = getFiendlyName();
        if (name != null && !name.equals("")) {
            return name;
        }
        return super.toString();
    }
}
