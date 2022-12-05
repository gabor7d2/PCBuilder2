package net.gabor7d2.pcbuilder.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.model.Settings;

import javax.swing.*;
import java.awt.*;

public class ThemeController {

    /**
     * The fully transparent color.
     */
    public final static Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

    /**
     * The settings instance shared with Application.
     */
    private final Settings settings;

    /**
     * Create a new ThemeController instance.
     *
     * @param settings The settings instance shared with Application.
     */
    public ThemeController(Settings settings) {
        this.settings = settings;
    }

    /**
     * Set up Java Swing look and feel and other decorative stuff
     * based on current settings (dark or light mode).
     */
    public void setupLookAndFeel() {
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
        //UIManager.put("ScrollBar.trackArc", 999);
        //UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        //UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
        FlatLaf.updateUI();
    }

    /**
     * Is the app in dark mode right now.
     *
     * @return Whether the app is in dark mode right now.
     */
    public boolean isDarkMode() {
        return settings.isDarkMode();
    }

    /**
     * Sets app to dark or light mode.
     *
     * @param darkMode true for dark mode, false for light mode.
     */
    public void setDarkMode(boolean darkMode) {
        settings.setDarkMode(darkMode);
        setupLookAndFeel();
        Application.saveSettings();
    }
}
