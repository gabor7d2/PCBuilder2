package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.gui.event.ProfileEventListener;
import net.gabor7d2.pcbuilder.gui.general.AsyncImageLoader;
import net.gabor7d2.pcbuilder.gui.general.ScrollPane2D;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The MainPanel is a panel that is at the middle of the main window,
 * its purpose is to display and switch between the ScrollPane2Ds of the
 * profiles.
 * <p>
 * The different views of the MainPanel are all added to a card layout,
 * which can be used to switch to the desired view with a String "key".
 */
public class MainPanel extends JPanel implements ProfileEventListener {

    /**
     * The background color of the profiles' ScrollPane2Ds in light theme.
     */
    private final static Color PROFILE_SCROLL_PANE_BG_LIGHT = new Color(200, 200, 200);

    /**
     * The background color of the profiles' ScrollPane2Ds in dark theme.
     */
    private final static Color PROFILE_SCROLL_PANE_BG_DARK = new Color(78, 81, 84);

    /**
     * The CardLayout of the panel.
     */
    private final CardLayout cardLayout = new CardLayout();

    /**
     * The set of profile ids that were displayed on this panel and that still exist.
     */
    private final Set<String> profileIds = new HashSet<>();

    /**
     * Creates a new MainPanel.
     */
    public MainPanel() {
        // set the layout to a card layout
        setLayout(cardLayout);
        // add welcome panel with name "null", which is the default panel displayed
        add(new WelcomePanel(), "null");

        // subscribe to events
        EventBus.getInstance().subscribeToProfileEvents(this);
    }

    /**
     * Displays the specified profile.
     * Creates a ScrollPane2D and adds all categories of the profile as
     * individual CategoryRows into the ScrollPane2D.
     * <p>
     * Then adds the created ScrollPane2D to this main panel alongside the profile's id,
     * which can then be used by the card layout to switch to the profile by its id.
     *
     * @param profile The profile to display.
     */
    private void displayProfile(Profile profile) {
        if (profile == null) {
            // if profile is null, show welcome panel
            cardLayout.show(this, "null");
            return;
        }

        if (!profileIds.contains(profile.getId())) {
            // if the profile with this id was never displayed before, create the ScrollPane2D
            // and the CategoryRows for it and add it to the MainPanel
            profileIds.add(profile.getId());
            ScrollPane2D profilePane = new ScrollPane2D();
            profilePane.setBackgroundColor(
                    Application.getThemeController().isDarkMode() ? PROFILE_SCROLL_PANE_BG_DARK : PROFILE_SCROLL_PANE_BG_LIGHT);
            profile.getCategories().forEach(c -> profilePane.addRow(new CategoryRow(c)));
            add(profilePane, profile.getId());
        }

        // show the profile's view by its id
        cardLayout.show(this, profile.getId());
        // process async
        AsyncImageLoader.processQueue();
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.SELECT) {
            // display the newly selected profile
            displayProfile(e.getProfile());
        }
    }

    /**
     * Updates the ScrollPane2Ds' background color on theme change.
     */
    private void updateTheme() {
        for (Component c : getComponents()) {
            if (c instanceof ScrollPane2D) {
                ((ScrollPane2D) c).setBackgroundColor(
                        Application.getThemeController().isDarkMode() ? PROFILE_SCROLL_PANE_BG_DARK : PROFILE_SCROLL_PANE_BG_LIGHT);
            }
        }
    }

    @Override
    public void updateUI() {
        // gets called on theme switching
        super.updateUI();
        updateTheme();
    }

    /**
     * Panel that gets shown by default when the user opens the app.
     */
    private static class WelcomePanel extends JPanel {

        /**
         * Inner panel to place components on.
         */
        JPanel innerPanel = new JPanel();

        /**
         * Creates a WelcomePanel.
         */
        public WelcomePanel() {
            // setup inner panel
            setLayout(new GridBagLayout());
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
            add(innerPanel);

            // setup components
            JLabel welcome = new JLabel("Welcome!");
            welcome.setAlignmentX(CENTER_ALIGNMENT);
            welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 26));
            innerPanel.add(welcome);

            JLabel helpText = new JLabel("Please select a profile below or import one.");
            helpText.setAlignmentX(CENTER_ALIGNMENT);
            helpText.setFont(helpText.getFont().deriveFont(Font.PLAIN, 18));
            innerPanel.add(helpText);

            // TODO profile chooser JList
            /*DefaultListModel<String> model = new DefaultListModel<>();
            model.addElement("hello");
            model.addElement("world");
            JList<String> profileList = new JList<>(model);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(profileList);
            innerPanel.add(scrollPane);*/
        }
    }
}
