package gspd.ispd.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * StringBundle is a wrapper and helper class to access the
 * resource bundle properties using pattern to replace strings
 * </p>
 * <p>
 * Suppose a bundle {@code string.properties} as below
 * </p>
 *  
 */
public class StringBundle {

    /**
     * The resource bundle
     */
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

    public String resolveString(String text) {
        Pattern pattern = Pattern.compile("\\$\\{(\\w|\\.|\\:)+\\}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            // the hole string matched
            String group = matcher.group();
            // the key name
            String key = group.substring(2, group.length() - 1);
            // the value of the specified key
            String value = stringsBundle.getString(key);
            // substitute in string
            String before = text.substring(0, matcher.start());
            String after = text.substring(matcher.end());
            text = before.concat(value).concat(after);
        }
        return text;
    }

    private static String replaceArguments(String mainText, Object... args) {
        for (int i = 0; i < args.length; i++) {
            mainText = mainText.replaceAll("\\$\\{" + (i+1) + "\\}", args[i].toString());
        }
        return mainText;
    }
    
}