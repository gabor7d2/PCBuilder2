package net.gabor7d2.pcbuilderold.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gabor7d2.pcbuilderold.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class PersistenceManager {

    public final static String APP_DIRECTORY_NAME = System.getProperty("user.home") + File.separator + ".pcbuilder";
    public final static String SETTINGS_FILE_NAME = "settings.json";
    public final static File APP_DIRECTORY = new File(APP_DIRECTORY_NAME);
    public final static File SETTINGS_FILE = new File(APP_DIRECTORY_NAME + File.separator + SETTINGS_FILE_NAME);
    public final static File PROFILES_DIRECTORY = new File(APP_DIRECTORY_NAME + File.separator + "profiles");

    private static Settings settings;

    static {
        checkIO();
        loadSetings();
    }

    private PersistenceManager() {

    }

    /**
     * Check if the required files and folders exist and create them if they don't
     */
    private static void checkIO() {
        if (!APP_DIRECTORY.exists()) APP_DIRECTORY.mkdirs();
        if (!PROFILES_DIRECTORY.exists()) {
            PROFILES_DIRECTORY.mkdirs();
        }
        if (!SETTINGS_FILE.exists()) {
            FileUtils.copyFileFromJar(SETTINGS_FILE_NAME, SETTINGS_FILE, false).printResultIfError();
        }
    }

    /**
     * Load settings.json to Settings object
     */
    private static void loadSetings() {
        try {
            settings = new ObjectMapper().readValue(SETTINGS_FILE, Settings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveSettings() {

    }

    public static Settings getSettings() {
        return settings;
    }
}
