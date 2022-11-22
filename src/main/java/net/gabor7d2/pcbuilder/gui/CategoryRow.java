package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.elements.ScrollPane2D;
import net.gabor7d2.pcbuilder.gui.elements.SmartScrollPane;

import javax.swing.*;
import java.awt.*;

public class CategoryRow extends ScrollPane2D.ScrollPane2DRow {

    private JPanel headerPanel;
    private final SmartScrollPane scrollPane = new SmartScrollPane();

    private final JPanel contentPanel = new JPanel();

    public CategoryRow() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createMatteBorder(8, 8, 0, 8, Color.LIGHT_GRAY));

        scrollPane.getViewport().setBackground(Color.WHITE);
        contentPanel.setBackground(Color.WHITE);

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        scrollPane.setViewportView(contentPanel);
        add(scrollPane);
    }

    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setHeaderPanel(JPanel headerPanel) {
        this.headerPanel = headerPanel;
        removeAll();
        add(headerPanel);
        add(scrollPane);
    }

    @Override
    public void placedInsideScrollPane(JScrollPane outerScrollPane) {
        // fix scrolling when placed inside another ScrollPane
        scrollPane.setWheelScrollingEnabled(false);
        scrollPane.addMouseWheelListener(e -> {
            int wheelRotation = e.getWheelRotation() * 16;

            if ((e.isShiftDown() || !outerScrollPane.getVerticalScrollBar().isVisible()) && scrollPane.getHorizontalScrollBar().isVisible()) {
                scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + wheelRotation);
            } else {
                outerScrollPane.getVerticalScrollBar().setValue(outerScrollPane.getVerticalScrollBar().getValue() + wheelRotation);
            }
        });
    }
}