package net.gabor7d2.pcbuilder.gui;

import com.formdev.flatlaf.FlatClientProperties;
import net.gabor7d2.pcbuilder.gui.event.*;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlBar extends JPanel implements ProfileEventListener {

    private static final Color BG_COLOR = Color.DARK_GRAY;
    private static final Color FG_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = Color.WHITE;

    private final JPanel eastPanel, westPanel;

    private JLabel totalPriceLabel;

    private JComboBox<Profile> profileSelector;

    public ControlBar() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        westPanel = new JPanel();
        westPanel.setLayout(new GridBagLayout());
        westPanel.setBackground(getBackground());
        westPanel.setBorder(BorderFactory.createMatteBorder(8, 16, 8, 0, getBackground()));
        add(westPanel, BorderLayout.WEST);

        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
        eastPanel.setBackground(getBackground());
        eastPanel.setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, getBackground()));
        add(eastPanel, BorderLayout.EAST);

        setupTotalPriceLabel();

        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));
        setupProfileSelector();
        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));

        addButton("Import", null);
        addButton("Delete", null);
        addButton("Edit Mode", null);
        JButton helpButton = addButton("Help", null);
        helpButton.putClientProperty("JButton.buttonType", "help");

        EventBus.getInstance().subscribeToProfileEvents(this);
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.ADD) {
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
            if (e.getProfile() != null) {
                updateTotalPrice(1234, e.getProfile().getCurrencyPrefix(), e.getProfile().getCurrencySuffix());
                totalPriceLabel.setVisible(true);
            } else {
                updateTotalPrice(0, "", "");
                totalPriceLabel.setVisible(false);
            }
        }
    }

    private void updateTotalPrice(double value, String prefix, String suffix) {
        totalPriceLabel.setText("Total Price: " + prefix + value + suffix);
    }

    private void setupTotalPriceLabel() {
        totalPriceLabel = new JLabel();
        totalPriceLabel.setForeground(TEXT_COLOR);
        totalPriceLabel.setFont(totalPriceLabel.getFont().deriveFont(Font.BOLD, 13));

        totalPriceLabel.setVisible(false);
        westPanel.add(totalPriceLabel);
    }

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

    private JButton addButton(String name, ActionListener listener) {
        JButton btn = new JButton(name);
        btn.addActionListener(listener);

        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));
        eastPanel.add(btn);
        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));

        return btn;
    }
}
