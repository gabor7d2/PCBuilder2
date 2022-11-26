package net.gabor7d2.pcbuilder.repository;

import net.gabor7d2.pcbuilder.model.Settings;

public interface SettingsRepository {

    Settings loadSettings();

    void saveSettings(Settings settings);
}
