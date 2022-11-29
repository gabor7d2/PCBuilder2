package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.repositoryimpl.RepositoryFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The main window of the app.
 */
public class MainFrame extends JFrame {

    /**
     * The title of the main window.
     */
    private final static String TITLE = "PC Builder";

    /**
     * Default width of the main window.
     */
    private final static int WIDTH = 900;

    /**
     * Default height of the main window.
     */
    private final static int HEIGHT = 720;

    /**
     * Minimum width of the main window.
     */
    private final static int MIN_WIDTH = 750;

    /**
     * Minimum height of the main window.
     */
    private final static int MIN_HEIGHT = 360;

    /**
     * CategorySelectorBar component in the main window.
     */
    private final CategorySelectorBar categorySelectorBar;

    /**
     * ControlBar component in the main window.
     */
    private final ControlBar controlBar;

    /**
     * MainPanel component in the main window.
     */
    private final MainPanel mainPanel;

    /**
     * Creates the main window.
     */
    public MainFrame() {
        // Set looks and behaviour
        setIconImages(Application.APP_ICONS);
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        // Create category selector bar on the north
        categorySelectorBar = new CategorySelectorBar();
        categorySelectorBar.setVisible(false);
        add(categorySelectorBar, BorderLayout.NORTH);

        // Create control bar on the south
        controlBar = new ControlBar();
        add(controlBar, BorderLayout.SOUTH);

        // Create main panel on the center
        mainPanel = new MainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Load all profiles
        List<Profile> profiles = RepositoryFactory.getProfileRepository().loadProfiles();

        // Notify listeners of loaded profiles
        profiles.forEach(p -> {
            EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.ADD, p));
        });
    }
}
