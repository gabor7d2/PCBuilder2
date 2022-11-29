package net.gabor7d2.pcbuilder.repositoryimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.gabor7d2.pcbuilder.Utils;
import net.gabor7d2.pcbuilder.model.Settings;
import net.gabor7d2.pcbuilder.repository.SettingsRepository;

import java.io.File;

/**
 * Implementation for handling {@link Settings} persistence using JSON.
 */
public class JsonSettingsRepository implements SettingsRepository {

    /**
     * The root directory of the app.
     */
    private final File appDirectory = new File(System.getProperty("user.home"), ".pcbuilder");

    /**
     * The settings file inside appDir.
     */
    private final File settingsFile = new File(appDirectory, "settings.json");

    /**
     * The Jackson ObjectMapper instance used for serializing/deserializing JSON from/to Java objects.
     */
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public Settings loadSettings() {
        createDefaultsIfNotFound();
        try {
            return mapper.readValue(settingsFile, Settings.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load settings.", e);
        }
    }

    @Override
    public void saveSettings(Settings settings) {
        createDefaultsIfNotFound();
        try {
            mapper.writeValue(settingsFile, settings);
        } catch (Exception e) {
            new RuntimeException("Failed to save settings.", e).printStackTrace();
        }
    }

    /**
     * Creates default folders and files if not found.
     */
    private void createDefaultsIfNotFound() {
        if (!appDirectory.isDirectory()) {
            appDirectory.mkdirs();
        }

        if (!settingsFile.isFile()) {
            Utils.copyFileFromClasspath("/settings.json", appDirectory);
        }
    }
}
