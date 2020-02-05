package gspd.ispd;


import gspd.ispd.model.ISPDModel;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ISPD is the main API  (in development)
 */
public class ISPD {

    public static final ResourceBundle strings = ResourceBundle.getBundle("strings", new Locale("en", "US"));

    private static Locale locale = new Locale("en", "US");
    private static ISPDModel model;

    public static void setLocale(Locale locale) {
        ISPD.locale = locale;
    }

    public static void setLocale(String language, String country) {
        ISPD.setLocale(new Locale(language, country));
    }
}