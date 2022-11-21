package net.gabor7d2.pcbuilder.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlBar extends SmartScrollPane {

    private static final Color BG_COLOR = Color.DARK_GRAY;
    private static final Color FG_COLOR = Color.WHITE;

    private final JPanel contentPanel = new JPanel();

    public ControlBar() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        setViewportView(contentPanel);
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, BG_COLOR));

        addButton("ASDOWAFW", null);
        addButton("WERW", null);
        addButton("GSRTGSRST554ETETT4353ET", null);
        addButton("YERGR EG", null);
        addButton("FES T4", null);
        addButton("453 ERRTET", null);
    }

    private void addButton(String name, ActionListener listener) {
        JButton btn = new JButton(name);
        btn.setBackground(BG_COLOR);
        btn.addActionListener(listener);
        contentPanel.add(Box.createRigidArea(new Dimension(4, 1)));
        contentPanel.add(btn);
        contentPanel.add(Box.createRigidArea(new Dimension(4, 1)));
    }
}
