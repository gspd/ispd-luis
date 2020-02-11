package gspd.ispd.settings;

import gspd.ispd.GUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

/**
 * This class has just methods to access common properties of the iSPD.
 * To load de configurantions, use {@code load} method, to edit them, see their setters,
 * to retrieve see the getters, and to store, use {@code store} method.
 * 
 * @version 0.5
 * @author luisbaldissera
 */
public class IspdSettings extends Properties {

    private String filename;
    private Locale locale;
    private Path workingDirectory;
    /**
     * Load the configuration file of iSPD, settings.properties ans control access and
     * availability of its informations, as well as a simple way to store their
     * possibly changes.
     * 
     * @author luisbaldissera
     */
    public void load(String filename) {
        try {
            this.filename = filename;
            super.load(GUI.class.getResourceAsStream("settings/" + filename));
            locale = new Locale(getProperty("locale.language", "en"), getProperty("locale.country", "US"));
            Locale.setDefault(locale);
            setWorkingDirectory(getUserHome() + File.separator +  getProperty("directories.default", "."));
            if (getProperty("threads.max") == null) {
                setMaxThreads(getHardwareConcurrency());
            }
            initFolders();
        } catch (IOException e) {
            System.err.println("Failed to open iSPD properties: " + e);
        } catch (Exception e) {
            System.err.println("An error occurred while loading properties: " + e);
        }
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param workingDirectory the working directory to set
     */
    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = Paths.get(workingDirectory).normalize().toAbsolutePath();
    }

    /**
     * @return the working directory
     */
    public String getWorkingDirectory() {
        return workingDirectory.toString();
    }

    /**
     * @return the user home directory
     */
    public String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * @param number the maximum number of threads
     */
    public void setMaxThreads(int number) {
        setProperty("threads.max", String.valueOf(number));
    }

    /**
     * @return the maximum number of threads
     */
    public int getMaxThreads() {
        return Integer.parseInt(getProperty("threads.max"));
    }

    /**
     * @return the number of processors available to execute iSPD
     */
    public int getHardwareConcurrency() {
        return Runtime.getRuntime().availableProcessors();
    }

    private void initFolders() {
        // If the directory doesn't exist, create it
        File wdFile = workingDirectory.toFile();
        if (!wdFile.exists()) {
            // TODO: Log/Warning about operation
            wdFile.mkdir();
        }
    }
}