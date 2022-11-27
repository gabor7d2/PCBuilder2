package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.gui.event.ProfileEventListener;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlBar extends JPanel implements ProfileEventListener {

    private static final Color BG_COLOR = Color.DARK_GRAY;
    private static final Color FG_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = Color.WHITE;
    private final ProfileEventListener parentProfileEventListener;

    private JLabel totalPriceLabel;

    private JComboBox<Profile> profileSelector;

    public ControlBar(ProfileEventListener parentProfileEventListener) {
        this.parentProfileEventListener = parentProfileEventListener;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, getBackground()));

        setupTotalPriceLabel();

        add(Box.createRigidArea(new Dimension(4, 1)));
        setupProfileSelector();
        add(Box.createRigidArea(new Dimension(4, 1)));

        addButton("Import", null);
        addButton("Delete", null);
        addButton("Edit Mode", null);
        JButton helpButton = addButton("Help", null);
        helpButton.putClientProperty("JButton.buttonType", "help");
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

    private void setupTotalPriceLabel() {
        totalPriceLabel = new JLabel();
        totalPriceLabel.setForeground(TEXT_COLOR);
        totalPriceLabel.setFont(totalPriceLabel.getFont().deriveFont(Font.BOLD, 13));
        totalPriceLabel.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, getBackground()));

        totalPriceLabel.setVisible(false);
        add(totalPriceLabel);
    }

    private void updateTotalPrice(double value, String prefix, String suffix) {
        totalPriceLabel.setText("Total Price: " + prefix + value + suffix);
    }

    private void setupProfileSelector() {
        profileSelector = new JComboBox<>();
        profileSelector.addActionListener(e -> {
            parentProfileEventListener.processProfileEvent(new ProfileEvent(ProfileEvent.ProfileEventType.SELECT, (Profile) profileSelector.getSelectedItem()));
        });
        add(profileSelector);
    }

    private JButton addButton(String name, ActionListener listener) {
        JButton btn = new JButton(name);
        btn.addActionListener(listener);

        add(Box.createRigidArea(new Dimension(4, 1)));
        add(btn);
        add(Box.createRigidArea(new Dimension(4, 1)));

        return btn;
    }
}
