package gspd.ispd;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manage strings in Ispd in the default locale set by {@link IspdSettings IspdSettings}
 * 
 * @author luisbaldissera
 */
public class IspdStrings {

    private static final String STRINGS_BUNDLE_NAME = "strings";

    private static ResourceBundle stringsBundle;
    private static Locale locale;

    /**
     * Load the default string configuration based in the defined default locale.
     * <ul>
     *  <li>
     *   <b>Notes:</b>
     *   <ul>
     *    <li> {@code IspdSettings.load()} should be called before. See {@link gspd.ispd.IspdSettings#load() IspdSettings.load()}.</li>
     *   </ul>
     *  </li>
     * </ul>
     */
    public static void load() {
        try {
            locale = IspdSettings.getLocale();
            stringsBundle = ResourceBundle.getBundle(STRINGS_BUNDLE_NAME, locale);
        } catch (Exception e) {
            System.err.println("An error occurred while loading strings: " + e);
        }
    }

    /**
     * <p>
     * Get a string in the default language set in iSPD, and replaces substrings
     * {@code $1 , $2 , ... , $n}, by the strings in 'replaces' respectively.
     * </p>
     * </p>
     * <p>
     * Example: if a string is set in the string bundle as 
     * {@code some.key=this is an $1 string}, this method should be called as
     * {@code IspdString.get("some.key", "example");}, and it would return
     * {@code "this is example string"}.
     * </p>
     * <ul>
     *  <li>
     *   <b>Notes:</b></p>
     *   <ul>
     *    <li>If the string has $i multiple times, then all them should be replaced.
     *    Example: other.key=Hi $1, you are a $1, called as {@code IspdStringd.get("other.key", "friend")},
     *    would be replaced as {@code "Hi friend, you are a friend"}.
     *    </li>
     *    <li>Currently, it just supports {@code $1 ... $9}.</li>
     *   </ul>
     *  </li>
     * </ul>
     * 
     * @param key the key of the string to be retrieved in the default language
     * @param replaces the strings those are replace {@code $1 , $2 , ... , $n} , respectively
     * @return the string with the iSPD set language replaced correctly
     */
    public static String get(String key, String ... replaces) {
        String string = stringsBundle.getString(key);
        for (int i = 1; i <= replaces.length; i++) {
            string = string.replaceAll("\\$" + i, replaces[i-1]);
        }
        return string;
    }
}