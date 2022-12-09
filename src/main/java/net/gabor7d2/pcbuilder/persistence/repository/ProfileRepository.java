package net.gabor7d2.pcbuilder.persistence.repository;

import net.gabor7d2.pcbuilder.Utils;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.model.Settings;
import net.gabor7d2.pcbuilder.persistence.ImportResultCode;

import java.io.File;
import java.util.Collection;
import java.util.List;
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

    void importFromZipFile(File zipFile,
                           ProgressListener<ImportResultCode, Collection<Profile>> progressListener,
                           AtomicBoolean cancellationToken);

    //void addProfile(Profile p);

    //void saveProfiles(List<Profile> profiles);
}
