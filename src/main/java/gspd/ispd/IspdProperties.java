package gspd.ispd;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

/**
 * IspdProperties
 * 
 * This class has just static methods to access common properties of the iSPD
 */
public class IspdProperties {

    private static final String propertiesName = "/ispd.properties";
    
    private static Properties properties;
    private static Locale locale;
    private static Path workingDirectory;

    /**
     * Load the configuration file of iSPD
     */
    public static void load() {
        try {
            properties = new Properties();
            properties.load(Main.class.getResourceAsStream(propertiesName));
            locale = new Locale(properties.getProperty("locale.language"), properties.getProperty("locale.country"));
            setWorkingDirectory(properties.getProperty("directories.default"));
        } catch (IOException e) {
            System.err.println("Failed to open iSPD properties: " + e);
        }
    }

    /**
     * @return the locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * @param workingDirectory the workingDirectory to set
     */
    public static void setWorkingDirectory(String workingDirectory) {
        IspdProperties.workingDirectory = Paths.get(workingDirectory);
    }

    /**
     * @return the workingDirectory
     */
    public static String getWorkingDirectory() {
        return workingDirectory.toAbsolutePath().toString();
    }
}