package gspd.ispd;


import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ISPD is the main API  (in development)
 */
public class ISPD {

    public static ResourceBundle getStrings() {
        return ResourceBundle.getBundle("strings", getLocale());
    }

    public static Locale getLocale() {
        return new Locale("en", "US");
    }
}