package net.gabor7d2.pcbuilder.repositoryimpl;

import net.gabor7d2.pcbuilder.repository.ProfileRepository;
import net.gabor7d2.pcbuilder.repository.SettingsRepository;

public class RepositoryFactory {

    private final static SettingsRepository SETTINGS_REPOSITORY = new JsonSettingsRepository();

    public static SettingsRepository getSettingsRepository() {
        return SETTINGS_REPOSITORY;
    }

    private final static ProfileRepository PROFILE_REPOSITORY = new JsonProfileRepository();

    public static ProfileRepository getProfileRepository() {
        return PROFILE_REPOSITORY;
    }
}
