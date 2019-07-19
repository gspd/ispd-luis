package gspd.ispd.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * StringResolver
 */
public class StringBundle {

    private ResourceBundle stringsBundle;

    public StringBundle(String baseName, Locale locale) {
        stringsBundle = ResourceBundle.getBundle(baseName, locale);
    }

    public StringBundle(String baseName) {
        stringsBundle = ResourceBundle.getBundle(baseName);
    }

    public String getString(String key, Object... objects) {
        // get the total text
        String text = stringsBundle.getString(key);
        // substitutes arguments objects
        return StringBundle.replaceArguments(text, objects);
    }

    private static String replaceArguments(String mainText, Object... args) {
        for (int i = 0; i < args.length; i++) {
            mainText = mainText.replaceAll("\\$\\{" + (i+1) + "\\}", args[i].toString());
        }
        return mainText;
    }
    
}