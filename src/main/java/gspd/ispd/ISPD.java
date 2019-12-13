package gspd.ispd;


import gspd.ispd.model.ISPDModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ISPD is the main API  (in development)
 */
public class ISPD {

    private static final String STRINGS_BASE_NAME = "strings";
    private static Locale locale = new Locale("en", "US");
    private static ISPDModel model;

    public static ResourceBundle getStrings() {
        return ResourceBundle.getBundle(STRINGS_BASE_NAME, getLocale());
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        ISPD.locale = locale;
    }

    public static void setLocale(String language, String country) {
        ISPD.setLocale(new Locale(language, country));
    }

    public static ISPDModel getModel() {
        return model;
    }

    public static void setModel(ISPDModel model) {
        ISPD.model = model;
    }

    // TODO: get recents
    public static List<URL> getRecentFiles() {
        try {
            List<URL> list = new ArrayList<>();
            list.add(new URL("file:/path/to/file1"));
            list.add(new URL("file:/path/to/file2"));
            list.add(new URL("file:/path/to/file3"));
            return list;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}