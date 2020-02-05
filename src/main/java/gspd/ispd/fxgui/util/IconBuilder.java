package gspd.ispd.fxgui.util;

import com.jcabi.aspects.Cacheable;
import gspd.ispd.annotations.IconImage;
import gspd.ispd.fxgui.Icon;

import java.util.HashMap;
import java.util.Map;

public class IconBuilder {
    private Map<Class, Icon> icons;
    private IconBuilder builder;

    private IconBuilder() {
        icons = new HashMap<>();
    }

    public IconBuilder getInstance() {
        if (builder == null) {
            synchronized (IconBuilder.class) {
                if (builder == null) {
                    builder = new IconBuilder();
                }
            }
        }
        return builder;
    }

    @Cacheable
    public Icon makeIcon(Class aClass) {
        Icon icon = null;
        if (aClass.isAnnotationPresent(IconImage.class)) {
            /////
        } else {
            throw new IllegalArgumentException("Class " + aClass.getName() + " is not annotated with " + IconImage.class.getName());
        }
        return icon;
    }
}
