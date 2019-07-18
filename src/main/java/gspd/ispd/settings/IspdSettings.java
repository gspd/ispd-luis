package gspd.ispd.settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

import gspd.ispd.Main;

/**
 * This class has just static methods to access common properties of the iSPD.
 * To load de configurantions, use {@code load} method, to edit them, see their setters,
 * to retrieve see the getters, and to store, use {@code store} method.
 * 
 * @version 0.5
 * @author luisbaldissera
 */
public class IspdSettings {

    private static final String PROPERTIES_FILE_NAME = "settings.properties";
    
    private static Properties properties;
    private static Locale locale;
    private static Path workingDirectory;

    /**
     * Load the configuration file of iSPD, settings.properties ans control access and
     * availability of its informations, as well as a simple way to store their
     * possibly changes.
     * 
     * @author luisbaldissera
     */
    public static void load() {
        try {
            properties = new Properties();
            properties.load(Main.class.getResourceAsStream("/" + PROPERTIES_FILE_NAME));
            locale = new Locale(properties.getProperty("locale.language", "en"), properties.getProperty("locale.country", "US"));
            Locale.setDefault(locale);
            setWorkingDirectory(getUserHome() + File.separator +  properties.getProperty("directories.default", "."));
            if (properties.getProperty("threads.max") == null) {
                setMaxThreads(getHardwareConcurrency());
            }
            // If the directory doesn't exist, create it
            File wdFile = workingDirectory.toFile();
            if (!wdFile.exists()) {
                // TODO: Log/Warning about operation
                wdFile.mkdir();
            }
        } catch (IOException e) {
            System.err.println("Failed to open iSPD properties: " + e);
        } catch (Exception e) {
            System.err.println("An error occurred while loading properties: " + e);
        }
    }

    /**
     * @return the locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * @param workingDirectory the working directory to set
     */
    public static void setWorkingDirectory(String workingDirectory) {
        IspdSettings.workingDirectory = Paths.get(workingDirectory).normalize().toAbsolutePath();
    }

    /**
     * @return the working directory
     */
    public static String getWorkingDirectory() {
        return workingDirectory.toString();
    }

    /**
     * @return the user home directory
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * @param number the maximum number of threads
     */
    public static void setMaxThreads(int number) {
        properties.setProperty("threads.max", String.valueOf(number));
    }

    /**
     * @return the maximum number of threads
     */
    public static int getMaxThreads() {
        return Integer.parseInt(properties.getProperty("threads.max"));
    }

    /**
     * @return the number of processors available to execute iSPD
     */
    public static int getHardwareConcurrency() {
        return Runtime.getRuntime().availableProcessors();
    }
}