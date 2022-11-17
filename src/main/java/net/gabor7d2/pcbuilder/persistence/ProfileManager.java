package net.gabor7d2.pcbuilder.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileManager {

    public final static String PROFILE_INFO_FILE_NAME = "profile.json";

    private static final List<Profile> profiles = new ArrayList<>();

    static {
        loadProfiles();
    }

    /**
     * Load all profiles from the profiles folder
     */
    private static void loadProfiles() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());

        for (File profileDir : PersistenceManager.PROFILES_DIRECTORY.listFiles()) {
            if (!profileDir.isDirectory()) continue;

            File profileFile = new File(profileDir, PROFILE_INFO_FILE_NAME);
            if (!profileFile.exists()) continue;

            try {
                Profile p = objectMapper.readValue(profileFile, new TypeReference<Profile>() {});
                //p.setProfileDir(profileDir);
                profiles.add(p);
                System.out.println("Read profile");
                System.out.println(p.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Profile> getProfiles() {
        return profiles;
    }
}
