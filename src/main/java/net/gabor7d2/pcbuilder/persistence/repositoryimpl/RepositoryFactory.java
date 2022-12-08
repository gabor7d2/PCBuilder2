package net.gabor7d2.pcbuilder.persistence.repositoryimpl;

import net.gabor7d2.pcbuilder.persistence.repository.ProfileRepository;
import net.gabor7d2.pcbuilder.persistence.repository.SettingsRepository;

/**
 * Class for obtaining Repository instances.
 */
public class RepositoryFactory {

    private RepositoryFactory() {
    }

    /**
     * The SettingsRepository instance.
     */
    private final static SettingsRepository SETTINGS_REPOSITORY = new JsonSettingsRepository();

    /**
     * The ProfileRepository instance.
     */
    private final static ProfileRepository PROFILE_REPOSITORY = new JsonProfileRepository();

    /**
     * Get the SettingsRepository instance.
     *
     * @return The SettingsRepository instance.
     */
    public static SettingsRepository getSettingsRepository() {
        return SETTINGS_REPOSITORY;
    }

    /**
     * Get the ProfileRepository instance.
     *
     * @return The ProfileRepository instance.
     */
    public static ProfileRepository getProfileRepository() {
        return PROFILE_REPOSITORY;
    }
}
