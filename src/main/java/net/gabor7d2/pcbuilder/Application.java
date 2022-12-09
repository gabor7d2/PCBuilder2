package net.gabor7d2.pcbuilder;

import lombok.Getter;
import net.gabor7d2.pcbuilder.gui.MainFrame;
import net.gabor7d2.pcbuilder.gui.ThemeController;
import net.gabor7d2.pcbuilder.gui.dialog.DialogManager;
import net.gabor7d2.pcbuilder.gui.event.ComponentEvent;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.gui.event.ProfileEventListener;
import net.gabor7d2.pcbuilder.gui.general.ImageLoader;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.model.Settings;
import net.gabor7d2.pcbuilder.model.component.CompatibilityChecker;
import net.gabor7d2.pcbuilder.persistence.repository.ProfileRepository;
import net.gabor7d2.pcbuilder.persistence.repositoryimpl.RepositoryFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.gabor7d2.pcbuilder.gui.general.ImageLoader.loadImageFromClasspath;

public class Application {

    /**
     * List of different resolution app icons.
     */
    public final static List<Image> APP_ICONS = new ArrayList<>();

    static {
        APP_ICONS.add(loadImageFromClasspath("/icons/icon.png"));
        APP_ICONS.add(loadImageFromClasspath("/icons/icon-512.png"));
        APP_ICONS.add(loadImageFromClasspath("/icons/icon-256.png"));
        APP_ICONS.add(loadImageFromClasspath("/icons/icon-128.png"));
        APP_ICONS.add(loadImageFromClasspath("/icons/icon-64.png"));
        APP_ICONS.add(loadImageFromClasspath("/icons/icon-32.png"));
        APP_ICONS.add(loadImageFromClasspath("/icons/icon-16.png"));
    }

    /**
     * App settings.
     */
    private static Settings settings;

    /**
     * The dialog manager used to open dialogs.
     */
    @Getter
    private static DialogManager dialogManager;

    /**
     * The theme controller used to query what color to use
     * on components.
     */
    @Getter
    private static ThemeController themeController;

    private static ProfileRepository profileRepository = RepositoryFactory.getProfileRepository();

    private static Profile currentlySelectedProfile;

    public static void main(String[] args) {
        // load settings
        settings = RepositoryFactory.getSettingsRepository().loadSettings();

        // create theme controller and setup theme
        themeController = new ThemeController(settings);
        themeController.setupLookAndFeel();

        // create frame
        JFrame frame = new MainFrame();

        // create dialog manager
        dialogManager = new DialogManager(frame);

        // load profiles
        Collection<Profile> profiles = profileRepository.loadProfiles();

        // show frame
        frame.setVisible(true);

        // notify listeners of loaded profiles
        profiles.forEach(p -> {
            EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.ADD, p));
            // calculate initial incompatibilities
            CompatibilityChecker.recalculateComponentCompatibility(p);
        });

        // notify all component cards of initial compatibility update
        EventBus.getInstance().postEvent(new ComponentEvent(ComponentEvent.ComponentEventType.COMPATIBILITY_UPDATE, null));

        EventBus.getInstance().subscribeToProfileEvents(e -> {
           if (e.getType() == ProfileEvent.ProfileEventType.SELECT) {
               currentlySelectedProfile = e.getProfile();
           }
        });
    }

    public static Profile getCurrentlySelectedProfile() {
        return currentlySelectedProfile;
    }

    public static void deleteProfile(Profile p) {
        EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.DELETE, p));
        profileRepository.deleteProfile(p);
    }

    public static void reloadProfile(Profile p) {
        // load new profile data
        Profile newProfile = profileRepository.reloadProfile(p);

        // clear image cache so that if some images were changed, they will be loaded again
        ImageLoader.clearCache();

        // notify of reload
        EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.RELOAD, newProfile));
    }

    /**
     * Persist Settings changes.
     */
    public static void saveSettings() {
        RepositoryFactory.getSettingsRepository().saveSettings(settings);
    }
}
