package net.gabor7d2.pcbuilderold.persistence;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.File;

public class Profile {
    private String name;

    private File profileDir;

    @JsonCreator
    public Profile(String name) {
        this.name = name;
        profileDir = new File(PersistenceManager.PROFILES_DIRECTORY, name);
        if (!profileDir.isDirectory()) profileDir = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getProfileDir() {
        return profileDir;
    }

    public void setProfileDir(File profileDir) {
        this.profileDir = profileDir;
    }

    @Override
    public String toString() {
        return name;
    }
}
