package net.gabor7d2.pcbuilderold.gui.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class SelectableLabel extends JTextField {

    public SelectableLabel(String initialText) {
        super(initialText);
        setBorder(null);
        setEditable(false);
        setBackground(Color.WHITE);
    }

    public SelectableLabel(String initialText, String componentName) {
        this(initialText);
        setName(componentName);
    }

    public SelectableLabel(String initialText, String componentName, MouseListener listener) {
        this(initialText, componentName);
        addMouseListener(listener);
    }

    public SelectableLabel centerText() {
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
}