package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.dialog.ProgressDialog;
import net.gabor7d2.pcbuilder.gui.dialog.ProgressDialogType;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.gui.event.ProfileEventListener;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main window of the app.
 */
public class MainFrame extends JFrame implements ProfileEventListener {

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

    private final List<Profile> profiles = new ArrayList<>();

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
        CategorySelectorBar categorySelectorBar = new CategorySelectorBar();
        categorySelectorBar.setVisible(false);
        add(categorySelectorBar, BorderLayout.NORTH);

        // Create control bar on the south
        ControlBar controlBar = new ControlBar();
        add(controlBar, BorderLayout.SOUTH);

        // Create main panel on the center
        MainPanel mainPanel = new MainPanel();
        add(mainPanel, BorderLayout.CENTER);

        EventBus.getInstance().subscribeToProfileEvents(this);
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.ADD && e.getProfile() != null) {
            profiles.add(e.getProfile());
        }
    }
}
