package net.gabor7d2.pcbuilder.repositoryimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.gabor7d2.pcbuilder.Utils;
import net.gabor7d2.pcbuilder.model.Settings;
import net.gabor7d2.pcbuilder.repository.SettingsRepository;

import java.io.File;

public class JsonSettingsRepository implements SettingsRepository {

    private final File appDirectory = new File(System.getProperty("user.home"), ".pcbuilder");
    private final File settingsFile = new File(appDirectory, "settings.json");

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
        try {
            mapper.writeValue(settingsFile, settings);
        } catch (Exception e) {
            new RuntimeException("Failed to save settings.", e).printStackTrace();
        }
    }

    private void createDefaultsIfNotFound() {
        if (!appDirectory.isDirectory()) {
            appDirectory.mkdirs();
        }

        if (!settingsFile.isFile()) {
            Utils.copyFileFromClasspath("/settings.json", appDirectory);
        }
    }
}
