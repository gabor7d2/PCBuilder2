package net.gabor7d2.pcbuilder.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlBar extends JPanel {

    private static final Color BG_COLOR = Color.DARK_GRAY;
    private static final Color FG_COLOR = Color.WHITE;

    public ControlBar() {
        setLayout(new GridBagLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, BG_COLOR));

        addButton("ASDOWAFW", 0.1, null);
        addButton("WERW", 0.1, null);
        addButton("GSRTGSRST554ETETT4353ET", 0.4, null);
        addButton("YERGR EG", 0.2, null);
        addButton("FES T4", 0.1, null);
        addButton("453 ERRTET", 0.1, null);
    }

    private void addButton(String name, double weight, ActionListener listener) {
        JButton btn = new JButton(name);
        btn.setBackground(BG_COLOR);
        btn.addActionListener(listener);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 0.0;
        add(Box.createRigidArea(new Dimension(4, 1)), c);
        c.weightx = weight;
        add(btn, c);
        c.weightx = 0.0;
        add(Box.createRigidArea(new Dimension(4, 1)), c);
    }
}
