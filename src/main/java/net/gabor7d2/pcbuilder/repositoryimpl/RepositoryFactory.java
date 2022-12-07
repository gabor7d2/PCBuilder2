package net.gabor7d2.pcbuilder.repositoryimpl;

import net.gabor7d2.pcbuilder.repository.ProfileImporterRepository;
import net.gabor7d2.pcbuilder.repository.ProfileRepository;
import net.gabor7d2.pcbuilder.repository.SettingsRepository;

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
     * The ProfileImporterRepository instance.
     */
    private final static ProfileImporterRepository PROFILE_IMPORTER_REPOSITORY = new JsonProfileImporterRepository();

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

    /**
     * Get the ProfileImporterRepository instance.
     *
     * @return The ProfileImporterRepository instance.
     */
    public static ProfileImporterRepository getProfileImporterRepository() {
        return PROFILE_IMPORTER_REPOSITORY;
    }
}
