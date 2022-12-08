package net.gabor7d2.pcbuilder.persistence.repositoryimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.persistence.ImportResultCode;
import net.gabor7d2.pcbuilder.persistence.ZipExtractorThread;
import net.gabor7d2.pcbuilder.persistence.repository.ProgressListener;
import net.gabor7d2.pcbuilder.type.CategoryType;
import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.persistence.repository.ProfileRepository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
    final File appDirectory = new File(System.getProperty("user.home"), ".pcbuilder");

    /**
     * The profiles directory inside the appDir.
     */
    final File profilesDirectory = new File(appDirectory, "profiles");

    /**
     * The Jackson ObjectMapper instance used for serializing/deserializing JSON from/to Java objects.
     */
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public Collection<Profile> loadProfiles() {
        createDefaultsIfNotFound();

        File[] files = profilesDirectory.listFiles();
        if (files == null) return Collections.emptyList();
        return loadProfiles(Arrays.stream(files).toList());
    }

    /**
     * Tries loading the profiles from the specified list of profile directories.
     *
     * @param profileDirs The profile directories to load.
     * @return The loaded profiles, or an empty collection if profileDirs is null.
     */
    Collection<Profile> loadProfiles(Collection<File> profileDirs) {
        if (profileDirs == null) return Collections.emptyList();
        List<Profile> profiles = new ArrayList<>();

        try {
            // Go through all folders in profiles dir
            for (File f : profileDirs) {
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
    Profile loadProfile(File profileDir) {
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

            CategoryType cType = CategoryType.getCategoryTypeFromName(category.getType());

            File categoryFile = new File(p.getProfileFolder(), cType.getName().toLowerCase() + ".json");
            if (!categoryFile.isFile()) {
                System.out.println("Failed to load components from file " + categoryFile.getPath() + ": file not found.");
                continue;
            }
            try {
                List<Component> components = mapper.readValue(categoryFile,
                        TypeFactory.defaultInstance().constructParametricType(List.class, cType.getComponentClass()));

                // set category and construct image path string for components
                for (Component comp : components) {
                    comp.setCategory(category);
                    if (comp.getImagePath().isEmpty()) {
                        // only set image path if it was not set in json
                        comp.setImagePath(p.getProfileFolder().getPath()
                                + File.separator + cType.getName().toLowerCase().replace(' ', '_')
                                + File.separator + comp.getBrand().toLowerCase().replace(' ', '_')
                                + "_" + comp.getModelName().toLowerCase().replace(' ', '_') + ".png");
                    }
                }
                category.setComponents(components);
            } catch (Exception e) {
                new RuntimeException("Failed to load components from file " + categoryFile.getPath() + ": ", e).printStackTrace();
            }
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

    @Override
    public void importFromZipFile(File zipFile,
                                  ProgressListener<ImportResultCode, Collection<Profile>> progressListener,
                                  AtomicBoolean cancellationToken) {
        ZipExtractorThread thread = new ZipExtractorThread(profilesDirectory, zipFile, new ProgressListener<>() {
            @Override
            public void preparing(int maxProgress) {
                progressListener.preparing(maxProgress);
            }

            @Override
            public void starting() {
                progressListener.starting();
            }

            @Override
            public void progress(int progress) {
                progressListener.progress(progress);
            }

            @Override
            public void cancelling() {
                progressListener.cancelling();
            }

            @Override
            public void completed(ImportResultCode resultCode, Collection<File> extractedFiles) {
                if (resultCode == ImportResultCode.SUCCESS) {
                    progressListener.progress(Integer.MAX_VALUE);
                    progressListener.completed(ImportResultCode.SUCCESS,
                            loadProfiles(getProfileDirsFromExtractedFiles(extractedFiles)));
                } else {
                    progressListener.completed(resultCode, null);
                }
            }
        }, cancellationToken);
        thread.start();
    }

    private Collection<File> getProfileDirsFromExtractedFiles(Collection<File> extractedFiles) {
        List<File> profileDirs = new ArrayList<>();

        for (File f : extractedFiles) {
            try {
                if (f.getParentFile().getCanonicalPath().equals(profilesDirectory.getCanonicalPath())) {
                    profileDirs.add(f);
                }
            } catch (IOException e) {
                new RuntimeException("Failed to load profile dir.", e).printStackTrace();
            }
        }

        return profileDirs;
    }
}
