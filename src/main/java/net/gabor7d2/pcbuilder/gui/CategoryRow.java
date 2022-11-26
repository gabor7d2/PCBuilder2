package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.general.ScrollPane2D;
import net.gabor7d2.pcbuilder.gui.general.SmartScrollPane;
import net.gabor7d2.pcbuilder.model.Category;

import javax.swing.*;
import java.awt.*;

public class CategoryRow extends ScrollPane2D.ScrollPane2DRow {
    private final static Color BG_COLOR = Color.WHITE;

    private JPanel headerPanel;
    private final SmartScrollPane scrollPane = new SmartScrollPane();

    //private final JPanel contentPanel = new JPanel();

    public CategoryRow() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        scrollPane.getViewport().setBackground(BG_COLOR);
        //contentPanel.setBackground(BG_COLOR);

        //contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        //scrollPane.setViewportView(contentPanel);
        add(scrollPane);
    }

    public void displayCategory(Category category) {
        setHeaderPanel(new CategoryCard(category));

        //contentPanel.removeAll();
        ComponentsPanel panel = new ComponentsPanel();
        panel.displayComponents(category.getComponents());
        scrollPane.setViewportView(panel);
        //contentPanel.add(panel);
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