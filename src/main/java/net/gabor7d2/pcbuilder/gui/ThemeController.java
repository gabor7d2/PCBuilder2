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
     * The background color to use for component cards in light theme.
     */
    private final static Color COMPONENT_BG_LIGHT_THEME = Color.WHITE;

    /**
     * The background color to use for component cards in dark theme.
     */
    private final static Color COMPONENT_BG_DARK_THEME = new Color(51, 54, 56);

    /**
     * The background color to use for component cards in light theme, if the component is incompatible.
     */
    private final static Color COMPONENT_BG_LIGHT_THEME_INCOMPATIBLE = new Color(255, 186, 186);

    /**
     * The background color to use for component cards in dark theme, if the component is incompatible.
     */
    private final static Color COMPONENT_BG_DARK_THEME_INCOMPATIBLE = new Color(70, 54, 56);

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

    /**
     * Gets the background color to use for a component card
     * in the current theme, and depending on if the card is incompatible or not.
     */
    public Color getComponentCardBackgroundColor(boolean incompatible) {
        if (isDarkMode()) {
            if (incompatible) return COMPONENT_BG_DARK_THEME_INCOMPATIBLE;
            else return COMPONENT_BG_DARK_THEME;
        } else {
            if (incompatible) return COMPONENT_BG_LIGHT_THEME_INCOMPATIBLE;
            else return COMPONENT_BG_LIGHT_THEME;
        }
    }
}
