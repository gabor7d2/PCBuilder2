package net.gabor7d2.pcbuilder.persistence.repository;

import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.persistence.ImportResultCode;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface that can be implemented to handle {@link Profile} persistence,
 * along with Profiles' {@link Category Categories} and the Categories'
 * {@link Component Components}.
 */
public interface ProfileRepository {

    /**
     * Loads all profiles found in the app directory,
     * and also loads its categories and the categories' components.
     *
     * @return The collection of loaded profiles.
     */
    Collection<Profile> loadProfiles();

    /**
     * Saves the specified list of profiles into the profiles directory.
     *
     * @param profiles The profiles to save.
     */
    void saveProfiles(Collection<Profile> profiles);

    /**
     * Saves the specified profile into the profiles directory.
     *
     * @param profile The profile to save.
     */
    void saveProfile(Profile profile);

    /**
     * Deletes the specified profile from the profiles directory.
     *
     * @param p The profile to delete.
     */
    void deleteProfile(Profile p);

    /**
     * Reloads the specified profile from the profiles directory,
     * and returns a new Profile instance. The old instance will not be modified.
     *
     * @param p The profile to reload.
     * @return The new Profile instance, or null if loading failed.
     */
    Profile reloadProfile(Profile p);

    /**
     * Imports profiles from the specified zip file into the profiles directory.
     *
     * @param zipFile The zip file that contains the profiles to import.
     * @param progressListener The listener to listen for import progress.
     * @param cancellationToken The cancellation token to cancel the operation.
     */
    void importFromZipFile(File zipFile,
                           ProgressListener<ImportResultCode, Collection<Profile>> progressListener,
                           AtomicBoolean cancellationToken);
}
