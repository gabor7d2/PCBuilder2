package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.event.CategoryEvent;
import net.gabor7d2.pcbuilder.gui.event.CategoryEventListener;
import net.gabor7d2.pcbuilder.gui.event.ComponentEvent;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.general.ScrollPane2D;
import net.gabor7d2.pcbuilder.gui.general.SmartScrollPane;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.component.CompatibilityChecker;

import javax.swing.*;

import static net.gabor7d2.pcbuilder.gui.event.CategoryEvent.CategoryEventType.DISABLE;
import static net.gabor7d2.pcbuilder.gui.event.CategoryEvent.CategoryEventType.ENABLE;

/**
 * CategoryRow is a ScrollPane2DRow implementation that is used for
 * displaying a category's components.
 */
public class CategoryRow extends ScrollPane2D.ScrollPane2DRow implements CategoryEventListener {

    private ScrollPane2D outerScrollPane2D;

    /**
     * The scroll pane of the row.
     */
    private final SmartScrollPane scrollPane = new SmartScrollPane();

    /**
     * Reference to the displayed category.
     */
    private Category category;

    /**
     * Creates a new CategoryRow.
     *
     * @param category The category to display.
     */
    public CategoryRow(Category category) {
        // setup scrollpane
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(scrollPane);

        // display category
        displayCategory(category);

        // subscribe to events
        EventBus.getInstance().subscribeToCategoryEvents(this);
    }

    /**
     * Displays the specified category.
     * <p>
     * Creates a CategoryCard that is shown to the left of the components,
     * and a ComponentsPanel that is the panel which consists of ComponentCards.
     * The ComponentsPanel is then placed into the scroll pane so that it is scrollable.
     *
     * @param category The category to display.
     */
    private void displayCategory(Category category) {
        this.category = category;

        // component category header
        setHeaderPanel(new CategoryCard(category));

        // components in the scroll pane
        ComponentsPanel panel = new ComponentsPanel(category.getComponents());
        scrollPane.setViewportView(panel);

        // select specified default selection
        if (category.getDefaultSelection() >= 0 && category.getDefaultSelection() < category.getComponents().size()) {
            panel.getButtonGroup().setSelectedIndex(category.getDefaultSelection());
        }

        // set button group select listener
        panel.getButtonGroup().addButtonGroupListener((b, i) -> {
            category.setSelection(i);
            EventBus.getInstance().postEvent(new ComponentEvent(ComponentEvent.ComponentEventType.SELECT, category.getComponents().get(i)));
            CompatibilityChecker.recalculateComponentCompatibility(category.getProfile());
            EventBus.getInstance().postEvent(new ComponentEvent(ComponentEvent.ComponentEventType.COMPATIBILITY_UPDATE, null));
        });
    }

    /**
     * Set the header panel (the panel that is displayed to the left
     * of the components).
     *
     * @param headerPanel The new header panel.
     */
    private void setHeaderPanel(JPanel headerPanel) {
        removeAll();
        add(headerPanel);
        add(scrollPane);
    }

    @Override
    public void placedInsideScrollPane(ScrollPane2D outerScrollPane) {
        // store ScrollPane2D so that row visibility toggling can be achieved
        outerScrollPane2D = outerScrollPane;

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
        if (e.getCategory() == category) {
            // set visibility of category row
            if (e.getType() == ENABLE || e.getType() == DISABLE) {
                outerScrollPane2D.setRowVisible(outerScrollPane2D.indexOfRow(this), e.getType() == ENABLE);
            }
        }
    }
}