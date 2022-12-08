package net.gabor7d2.pcbuilder.persistence.repository;

import net.gabor7d2.pcbuilder.model.Settings;

/**
 * Interface that can be implemented to handle {@link Settings} persistence.
 */
public interface SettingsRepository {

    /**
     * Loads the app's settings.
     *
     * @return The loaded settings.
     */
    Settings loadSettings();

    /**
     * Saves the app's settings.
     *
     * @param settings The settings to save.
     */
    void saveSettings(Settings settings);
}
