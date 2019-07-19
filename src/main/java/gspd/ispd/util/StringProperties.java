package gspd.ispd.util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PropertiesBundle
 */
public class StringProperties {
    private Properties properties;

    public StringProperties(Properties properties) {
        this.properties = properties;
    }

    public String resolveProperties(String text) {
        Pattern pattern = Pattern.compile("\\$\\{(\\w|\\.|\\:)+\\}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            // the hole string matched
            String group = matcher.group();
            // the key name
            String key = group.substring(2, group.length() - 1);
            // the value of the specified key
            String value = properties.getProperty(key);
            // substitute in string
            String before = text.substring(0, matcher.start());
            String after = text.substring(matcher.end());
            text = before.concat(value).concat(after);
        }
        return text;
    }
    
}