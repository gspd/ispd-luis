package gspd.ispd;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * IspdStrings
 */
public class IspdStrings {

    private static final String STRINGS_BUNDLE_NAME = "strings";

    private static ResourceBundle stringsBundle;
    private static Locale locale;

    public static void load() {
        try {
            locale = IspdSettings.getLocale();
            stringsBundle = ResourceBundle.getBundle(STRINGS_BUNDLE_NAME, locale);
        } catch (Exception e) {
            System.err.println("An error occurred while loading strings: " + e);
        }
    }

    public static String get(String key, String ... strings) {
        String string = stringsBundle.getString(key);
        for (int i = 1; i <= strings.length; i++) {
            string = string.replaceAll("\\$" + i, strings[i-1]);
        }
        return string;
    }
}