package net.gabor7d2.pcbuilder;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import net.gabor7d2.pcbuilder.gui.MainFrame;
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

    public static void main(String[] args) {
        // load settings
        settings = RepositoryFactory.getSettingsRepository().loadSettings();
        //System.out.println(settings.getSelectedProfile());
        //settings.setSelectedProfile("Testing");
        //RepositoryFactory.getSettingsRepository().saveSettings(settings);

        setupLookAndFeel();
        JFrame frame = new MainFrame();
        frame.setVisible(true);
        //frame.createBufferStrategy(2);
    }

    /**
     * Set up Java Swing look and feel and other decorative stuff.
     */
    private static void setupLookAndFeel() {
        if (settings.isDarkMode()) {
            FlatDarculaLaf.setup();
        } else {
            FlatIntelliJLaf.setup();
        }
        FlatLaf.setUseNativeWindowDecorations(true);
        UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
    }

    /**
     * Get the mutable {@link Settings} instance.
     * Call {@link Application#saveSettings()} to persist changes if modified.
     */
    public static Settings getSettings() {
        return settings;
    }

    /**
     * Persist Settings changes.
     */
    public static void saveSettings() {
        RepositoryFactory.getSettingsRepository().saveSettings(settings);
    }
}
