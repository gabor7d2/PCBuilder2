package net.gabor7d2.pcbuilder.repository;

import net.gabor7d2.pcbuilder.model.Profile;

import java.io.File;
import java.util.List;

public interface ProfileRepository {

    List<Profile> loadProfiles();

    //void addProfile(Profile p);

    //void removeProfile(Profile p);

    //void renameProfile(Profile p, String newName);

    //void saveProfiles(List<Profile> profiles);
}
