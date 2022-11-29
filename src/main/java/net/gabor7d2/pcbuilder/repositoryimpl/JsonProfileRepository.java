package net.gabor7d2.pcbuilder.repositoryimpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.repository.ProfileRepository;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Implementation for handling {@link Profile} persistence, loading and storing
 * profiles from/to json files,
 * along with Profiles' {@link Category Categories} and the Categories'
 * {@link Component Components}.
 */
public class JsonProfileRepository implements ProfileRepository {

    /**
     * The root directory of the app.
     */
    private final File appDirectory = new File(System.getProperty("user.home"), ".pcbuilder");

    /**
     * The profiles directory inside the appDir.
     */
    private final File profilesDirectory = new File(appDirectory, "profiles");

    /**
     * The Jackson ObjectMapper instance used for serializing/deserializing JSON from/to Java objects.
     */
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public List<Profile> loadProfiles() {
        createDefaultsIfNotFound();
        List<Profile> profiles = new ArrayList<>();

        try {
            // Go through all folders in profiles dir
            for (File f : Objects.requireNonNull(profilesDirectory.listFiles())) {
                if (f.isDirectory()) {
                    // Try loading the profile.json file
                    Profile p = loadProfile(f);
                    if (p != null) {
                        // Set and load extra parameters of profile, add it to the list of profiles
                        p.setProfileFolder(f);
                        loadComponentsForProfile(p);
                        profiles.add(p);
                    }
                }
            }
        } catch (Exception e) {
            new RuntimeException("Failed to load profiles.", e).printStackTrace();
        }

        return profiles;
    }

    /**
     * Tries loading a profile from the specified directory.
     *
     * @param profileDir The profile directory.
     * @return The loaded profile, or null if the profile.json file
     * is not present in this dir or if an error occurred.
     */
    private Profile loadProfile(File profileDir) {
        File profileFile = new File(profileDir, "profile.json");
        if (!profileFile.isFile()) {
            System.out.println("Failed to load profile from " + profileDir.getPath() + ": profile.json not found.");
            return null;
        }

        try {
            return mapper.readValue(profileFile, Profile.class);
        } catch (IOException e) {
            new RuntimeException("Failed to load profile from " + profileDir.getPath() + ": ", e).printStackTrace();
            return null;
        }
    }

    /**
     * Tries loading all components for the categories of
     * the specified profile.
     *
     * @param p The profile.
     */
    private void loadComponentsForProfile(Profile p) {
        // Go through all categories of the profile.
        for (Category category : p.getCategories()) {
            category.setProfile(p);

            File categoryFile = new File(p.getProfileFolder(), category.getShortName().toLowerCase() + ".json");
            if (!categoryFile.isFile()) {
                System.out.println("Failed to load components from file " + categoryFile.getPath() + ": file not found.");
                continue;
            }
            try {
                List<Component> components = mapper.readValue(categoryFile, new TypeReference<List<Component>>() {
                });

                // construct image path string for components
                for (Component comp : components) {
                    comp.setCategory(category);
                    comp.setImagePath(p.getProfileFolder().getPath()
                            + File.separator + category.getShortName().toLowerCase().replace(' ', '_')
                            + File.separator + comp.getBrand().toLowerCase().replace(' ', '_')
                            + "_" + comp.getModelName().toLowerCase().replace(' ', '_') + ".png");
                }

                category.setComponents(components);
            } catch (Exception e) {
                new RuntimeException("Failed to load components from file " + categoryFile.getPath() + ": ", e).printStackTrace();
            }
            if (category.getComponents() == null) category.setComponents(new ArrayList<>());
        }
    }

    /**
     * Creates default folders and files if not found.
     */
    private void createDefaultsIfNotFound() {
        if (!appDirectory.isDirectory()) {
            appDirectory.mkdirs();
        }

        if (!profilesDirectory.isDirectory()) {
            profilesDirectory.mkdirs();
        }
    }
}
