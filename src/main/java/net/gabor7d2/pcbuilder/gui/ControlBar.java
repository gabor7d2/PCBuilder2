package net.gabor7d2.pcbuilder.gui;

import com.formdev.flatlaf.FlatClientProperties;
import net.gabor7d2.pcbuilder.gui.event.*;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The ControlBar is a panel that is at the bottom of the main window
 * and handles profile selection, profile management, switching to edit mode,
 * and calculating and displaying the total price of the selected components.
 */
public class ControlBar extends JPanel implements ProfileEventListener {

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
        JButton helpButton = addButton("Help", null);
        helpButton.putClientProperty("JButton.buttonType", "help");
        // TODO light/dark mode switch

        // subscribe to events
        EventBus.getInstance().subscribeToProfileEvents(this);
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
            if (e.getProfile() != null) {
                updateTotalPrice(1234, e.getProfile().getCurrencyPrefix(), e.getProfile().getCurrencySuffix());
                totalPriceLabel.setVisible(true);
            } else {
                updateTotalPrice(0, "", "");
                totalPriceLabel.setVisible(false);
            }
        }
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
        //Profile prototypeProfile = new Profile();
        //prototypeProfile.setName("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        //profileSelector.setPrototypeDisplayValue(prototypeProfile);
        //profileSelector.setMinimumSize(new Dimension(200, 1));
        //profileSelector.setPreferredSize(new Dimension(280, profileSelector.getPreferredSize().height));
        //profileSelector.setMinimumSize(new Dimension(200, 1));
        // TODO max size
        //profileSelector.setMaximumSize(new Dimension(300, 100));
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
}
