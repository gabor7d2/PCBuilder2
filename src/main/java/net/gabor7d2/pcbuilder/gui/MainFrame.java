package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;

import javax.swing.*;
import java.awt.*;

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
    private final static int WIDTH = 1200;

    /**
     * Default height of the main window.
     */
    private final static int HEIGHT = 720;

    /**
     * Minimum width of the main window.
     */
    private final static int MIN_WIDTH = 1000;

    /**
     * Minimum height of the main window.
     */
    private final static int MIN_HEIGHT = 600;

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
    }
}
