package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlBar extends JPanel {

    private static final Color BG_COLOR = Color.DARK_GRAY;
    private static final Color FG_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = Color.WHITE;

    private JLabel totalPriceLabel;

    private JComboBox<Profile> profileSelector;

    public ControlBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, BG_COLOR));

        setupTotalPriceLabel();

        add(Box.createRigidArea(new Dimension(4, 1)));
        setupProfileSelector();
        add(Box.createRigidArea(new Dimension(4, 1)));

        addButton("Rename", null);
        addButton("Add", null);
        addButton("Remove", null);
        addButton("Reload", null);
        addButton("Help", null);
    }

    private void setupTotalPriceLabel() {
        totalPriceLabel = new JLabel("Total Price: 7 849 485 Ft");
        totalPriceLabel.setForeground(TEXT_COLOR);
        totalPriceLabel.setFont(totalPriceLabel.getFont().deriveFont(Font.BOLD, 13));
        totalPriceLabel.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, BG_COLOR));

        //totalPriceLabel.setVisible(false);
        add(totalPriceLabel);
    }

    private void setupProfileSelector() {
        profileSelector = new JComboBox<>();
        //profileSelector.setBorder(BorderFactory.createMatteBorder(8, 0, 8, 0, BG_COLOR));
        profileSelector.addActionListener(e -> {
            System.out.println(profileSelector.getSelectedItem());
        });

        add(profileSelector);
    }

    private void addButton(String name, ActionListener listener) {
        JButton btn = new JButton(name);
        //btn.setBackground(BG_COLOR);
        //btn.setForeground(FG_COLOR);
        btn.addActionListener(listener);

        add(Box.createRigidArea(new Dimension(4, 1)));
        add(btn);
        add(Box.createRigidArea(new Dimension(4, 1)));
    }
}
