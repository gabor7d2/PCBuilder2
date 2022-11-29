package net.gabor7d2.pcbuilder.repository;

import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.model.Settings;

import java.io.File;
import java.util.List;

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
     * @return The list of loaded profiles.
     */
    List<Profile> loadProfiles();

    //void addProfile(Profile p);

    //void removeProfile(Profile p);

    //void renameProfile(Profile p, String newName);

    //void saveProfiles(List<Profile> profiles);
}
