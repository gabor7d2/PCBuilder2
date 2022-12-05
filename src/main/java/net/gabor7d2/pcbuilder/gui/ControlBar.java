package net.gabor7d2.pcbuilder.gui;

import com.formdev.flatlaf.FlatClientProperties;
import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.event.*;
import net.gabor7d2.pcbuilder.gui.general.ImageLoader;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Price;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The ControlBar is a panel that is at the bottom of the main window
 * and handles profile selection, profile management, switching to edit mode,
 * and calculating and displaying the total price of the selected components.
 */
public class ControlBar extends JPanel implements ProfileEventListener, CategoryEventListener, ComponentEventListener {

    /**
     * Background color of the panel.
     */
    private static final Color BG_COLOR = Color.DARK_GRAY;

    /**
     * Text color of the panel.
     */
    private static final Color TEXT_COLOR = Color.WHITE;

    /**
     * East and west panels of the control bar.
     */
    private final JPanel eastPanel, westPanel;

    /**
     * The total price label.
     */
    private JLabel totalPriceLabel;

    /**
     * The profile selector JComboBox.
     */
    private JComboBox<Profile> profileSelector;

    /**
     * Buttons that need icon swapping when theme is switched.
     */
    private JButton helpButton, themeButton;

    /**
     * Creates a new ControlBar.
     */
    public ControlBar() {
        // setup this
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        // setup west panel containing the texts
        westPanel = new JPanel();
        westPanel.setLayout(new GridBagLayout());
        westPanel.setBackground(getBackground());
        westPanel.setBorder(BorderFactory.createMatteBorder(8, 16, 8, 0, getBackground()));
        add(westPanel, BorderLayout.WEST);

        setupTotalPriceLabel();

        // setup east label containing the profile selector and the buttons
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
        eastPanel.setBackground(getBackground());
        eastPanel.setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, getBackground()));
        add(eastPanel, BorderLayout.EAST);

        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));
        setupProfileSelector();
        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));

        addButton("Import", null);
        addButton("Delete", null);
        addButton("Edit Mode", null);

        helpButton = addButton("", e -> {
            JDialog diag = new JDialog();
            diag.setModal(true);
            diag.add(new JLabel("Help text: "));
            diag.pack();
            diag.setVisible(true);
        });
        //helpButton.putClientProperty("JButton.buttonType", "help");
        //helpButton.setBorder(null);
        helpButton.setMargin(new Insets(0, 0, 0, 0));
        //helpButton.setContentAreaFilled(false);

        themeButton = addButton("", e -> {
            Application.getThemeController().setDarkMode(!themeButton.getName().equals("dark"));
        });
        themeButton.setMargin(new Insets(0, 0, 0, 0));
        //helpButton.setContentAreaFilled(false);

        updateTheme();

        // subscribe to events
        EventBus.getInstance().subscribeToProfileEvents(this);
        EventBus.getInstance().subscribeToCategoryEvents(this);
        EventBus.getInstance().subscribeToComponentEvents(this);
    }

    /**
     * Sets up total price label.
     */
    private void setupTotalPriceLabel() {
        totalPriceLabel = new JLabel();
        totalPriceLabel.setForeground(TEXT_COLOR);
        totalPriceLabel.setFont(totalPriceLabel.getFont().deriveFont(Font.BOLD, 13));

        totalPriceLabel.setVisible(false);
        westPanel.add(totalPriceLabel);
    }

    /**
     * Sets up profile selector JComboBox. When an item is selected in the ComboBox,
     * a ProfileEvent with type SELECT is posted on the EventBus.
     */
    private void setupProfileSelector() {
        profileSelector = new JComboBox<>();
        // TODO max size
        profileSelector.putClientProperty(FlatClientProperties.MINIMUM_WIDTH, 200);
        profileSelector.addActionListener(e -> {
            EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.SELECT, (Profile) profileSelector.getSelectedItem()));
        });
        eastPanel.add(profileSelector);
    }

    /**
     * Adds a button to the east panel.
     *
     * @param name     The text to show on the button.
     * @param listener The listener to call when clicked.
     * @return The created JButton.
     */
    private JButton addButton(String name, ActionListener listener) {
        JButton btn = new JButton(name);
        btn.addActionListener(listener);

        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));
        eastPanel.add(btn);
        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));

        return btn;
    }

    /**
     * Updates the text of the total price label.
     *
     * @param value  The price value.
     * @param prefix The prefix to print in front of the price.
     * @param suffix The suffix to print after the price.
     */
    private void updateTotalPrice(double value, String prefix, String suffix) {
        totalPriceLabel.setText("Total Price: " + prefix + value + suffix);
    }

    /**
     * Calculates the total price of the selected components in the specified profile.
     * Each selected component's price is added to the total if the
     * component's category is enabled.
     *
     * @param profile The profile.
     */
    private void calculateAndDisplayTotalPrice(Profile profile) {
        if (profile == null) {
            updateTotalPrice(0, "", "");
            totalPriceLabel.setVisible(false);
        } else {
            double total = 0;
            for (Category c : profile.getCategories()) {
                if (c.isEnabled() && c.getSelection() >= 0 && c.getSelection() < c.getComponents().size()) {
                    // if selection is valid and category is enabled, add selected component's price to total
                    Price p = c.getComponents().get(c.getSelection()).getPrice();
                    if (p != null)
                        total += p.getValue();
                }
            }
            updateTotalPrice(total, profile.getCurrencyPrefix(), profile.getCurrencySuffix());
            totalPriceLabel.setVisible(true);
        }
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.ADD) {
            // a new profile was added, add it into the profile selector
            if (profileSelector.getItemCount() == 0) {
                // add first item without selecting it
                ActionListener l = profileSelector.getActionListeners()[0];
                profileSelector.removeActionListener(l);
                profileSelector.addItem(e.getProfile());
                profileSelector.setSelectedIndex(-1);
                profileSelector.addActionListener(l);
            } else {
                profileSelector.addItem(e.getProfile());
            }
        }
        if (e.getType() == ProfileEvent.ProfileEventType.SELECT) {
            // switched to another profile, calculate new total price
            calculateAndDisplayTotalPrice(e.getProfile());
        }
    }

    @Override
    public void processCategoryEvent(CategoryEvent e) {
        if (e.getType() == CategoryEvent.CategoryEventType.ENABLE || e.getType() == CategoryEvent.CategoryEventType.DISABLE) {
            calculateAndDisplayTotalPrice(e.getCategory().getProfile());
        }
    }

    @Override
    public void processComponentEvent(ComponentEvent e) {
        if (e.getType() == ComponentEvent.ComponentEventType.SELECT) {
            calculateAndDisplayTotalPrice(e.getComponent().getCategory().getProfile());
        }
    }

    /**
     * Updates the components' look on theme change.
     */
    private void updateTheme() {
        boolean darkMode = Application.getThemeController().isDarkMode();

        if (helpButton != null) {
            String helpPath = darkMode ? "/help_icon_dark.png" : "/help_icon_light.png";
            helpButton.setIcon(ImageLoader.loadImageIconFromClasspath(helpPath, 20, 20));
        }

        if (themeButton != null) {
            String themePath = darkMode ? "/theme_icon_dark.png" : "/theme_icon_light.png";
            themeButton.setIcon(ImageLoader.loadImageIconFromClasspath(themePath, 20, 20));
            themeButton.setName(darkMode ? "dark" : "light");
        }
    }

    @Override
    public void updateUI() {
        // gets called on theme switching
        super.updateUI();
        updateTheme();
    }
}
