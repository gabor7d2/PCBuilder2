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

public class JsonProfileRepository implements ProfileRepository {

    private final File appDirectory = new File(System.getProperty("user.home"), ".pcbuilder");
    private final File profilesDirectory = new File(appDirectory, "profiles");

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public List<Profile> loadProfiles() {
        createDefaultsIfNotFound();
        List<Profile> profiles = new ArrayList<>();

        try {
            for (File f : Objects.requireNonNull(profilesDirectory.listFiles())) {
                if (f.isDirectory()) {
                    Profile p = loadProfile(f);
                    if (p != null) {
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

    private void loadComponentsForProfile(Profile p) {
        for (Category category : p.getCategories()) {
            File categoryFile = new File(p.getProfileFolder(), category.getShortName().toLowerCase() + ".json");
            if (!categoryFile.isFile()) {
                System.out.println("Failed to load components from file " + categoryFile.getPath() + ": file not found.");
                continue;
            }
            try {
                List<Component> components = mapper.readValue(categoryFile, new TypeReference<List<Component>>(){});

                // construct image path string for components
                for (Component comp : components) {
                    comp.setImagePath(p.getProfileFolder().getPath()
                            + File.separator + category.getShortName().toLowerCase().replace(' ', '_')
                            + File.separator + comp.getBrand().toLowerCase().replace(' ', '_')
                            + "_" + comp.getModelName().toLowerCase().replace(' ', '_') + ".png");
                }

                category.setComponents(components);
            } catch (Exception e) {
                new RuntimeException("Failed to load components from file " + categoryFile.getPath() + ": ", e).printStackTrace();
            }
        }
    }

    private void createDefaultsIfNotFound() {
        if (!appDirectory.isDirectory()) {
            appDirectory.mkdirs();
        }

        if (!profilesDirectory.isDirectory()) {
            profilesDirectory.mkdirs();
        }
    }
}
