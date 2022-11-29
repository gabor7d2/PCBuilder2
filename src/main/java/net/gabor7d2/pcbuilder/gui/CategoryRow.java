package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.event.CategoryEvent;
import net.gabor7d2.pcbuilder.gui.event.CategoryEventListener;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.general.ScrollPane2D;
import net.gabor7d2.pcbuilder.gui.general.SmartScrollPane;
import net.gabor7d2.pcbuilder.model.Category;

import javax.swing.*;
import java.awt.*;

public class CategoryRow extends ScrollPane2D.ScrollPane2DRow implements CategoryEventListener {
    private final static Color BG_COLOR = Color.WHITE;

    private final SmartScrollPane scrollPane = new SmartScrollPane();

    private Category category;

    public CategoryRow(Category category) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        scrollPane.getViewport().setBackground(BG_COLOR);
        add(scrollPane);

        displayCategory(category);
        EventBus.getInstance().subscribeToCategoryEvents(this);
    }

    private void displayCategory(Category category) {
        this.category = category;

        // component category header
        setHeaderPanel(new CategoryCard(category));

        // components in the scroll pane
        ComponentsPanel panel = new ComponentsPanel();
        panel.displayComponents(category.getComponents());
        scrollPane.setViewportView(panel);

        // select specified default selection
        if (category.getDefaultSelection() > 0 && category.getDefaultSelection() <= category.getComponents().size()) {
            panel.getButtonGroup().setSelectedIndex(category.getDefaultSelection() - 1);
        }

        setVisible(category.isEnabled());
    }

    public void setHeaderPanel(JPanel headerPanel) {
        removeAll();
        add(headerPanel);
        add(scrollPane);
    }

    @Override
    public void placedInsideScrollPane(ScrollPane2D outerScrollPane) {
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

    @Override
    public void processCategoryEvent(CategoryEvent e) {
        // TODO fix setVisible
        if (e.getCategory() == category) {
            if (e.getType() == CategoryEvent.CategoryEventType.ENABLE) {
                setVisible(true);
            } else if (e.getType() == CategoryEvent.CategoryEventType.DISABLE) {
                setVisible(false);
            }
        }
    }
}