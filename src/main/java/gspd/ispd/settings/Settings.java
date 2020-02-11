package gspd.ispd.settings;

import gspd.ispd.GUI;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Settings
 */
public class Settings {

    class NamePath {
        String name;
        String path;

        public NamePath(String name, String path) {
            this.name = name;
            this.path = path;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the path
         */
        public String getPath() {
            return path;
        }
    }
    
    private String filename;
    private Properties properties;
    private Locale locale;
    private List<NamePath> recents;

    public Settings(String filename) {
        this.filename = filename;
        try {
            loadProperties();
            loadLocale();
            loadRecents();
        } catch (IOException e) {

        } catch (Exception e) {

        }
    }

    public int getNumberOfSimulations() {
        return getInt("motor.simulation.number");
    }

    public int getNumberOfRecents() {
        return getInt("editor.recent.number");
    }

    /**
     * @return the recents in a map with <name, path> style
     */
    public List<NamePath> getRecentsOpened() {
        return recents;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    private void loadProperties() throws IOException {
        properties = new Properties();
        InputStream stream = GUI.class.getResourceAsStream(filename);
        properties.load(stream);
    }

    private void loadLocale() {
        String language = properties.getProperty("locale.language");
        String country = properties.getProperty("locale.country");
        locale = new Locale(language, country);
    }

    private void loadRecents() {
        recents = null;
        String recentString = properties.getProperty("editor.recent.list");
        if (recentString != null) {
            recents = new LinkedList<>();
            StringTokenizer entryTokenizer = new StringTokenizer(recentString, ":");
            while (entryTokenizer.hasMoreTokens()) {
                String entry = entryTokenizer.nextToken();
                StringTokenizer namePathTokenizer = new StringTokenizer(entry, ",");
                String name = namePathTokenizer.nextToken();
                String path = namePathTokenizer.nextToken();
                recents.add(new NamePath(name, path));
            }
        }
    }

    private int getInt(String key) {
        String number = properties.getProperty(key);
        return Integer.parseInt(number);
    }

}