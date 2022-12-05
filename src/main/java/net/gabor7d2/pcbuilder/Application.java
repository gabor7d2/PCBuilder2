package net.gabor7d2.pcbuilder;

import net.gabor7d2.pcbuilder.gui.MainFrame;
import net.gabor7d2.pcbuilder.gui.ThemeController;
import net.gabor7d2.pcbuilder.model.Settings;
import net.gabor7d2.pcbuilder.repositoryimpl.RepositoryFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

    private static ThemeController themeController;

    public static void main(String[] args) {
        // load settings
        settings = RepositoryFactory.getSettingsRepository().loadSettings();

        // create theme controller and setup theme
        themeController = new ThemeController(settings);
        themeController.setupLookAndFeel();

        // show frame
        JFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    public static ThemeController getThemeController() {
        return themeController;
    }

    /**
     * Persist Settings changes.
     */
    public static void saveSettings() {
        RepositoryFactory.getSettingsRepository().saveSettings(settings);
    }
}
