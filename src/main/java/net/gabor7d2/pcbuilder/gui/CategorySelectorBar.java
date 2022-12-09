package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.event.*;
import net.gabor7d2.pcbuilder.gui.general.WrapLayout;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.component.CompatibilityChecker;
import net.gabor7d2.pcbuilder.type.CategoryType;

import javax.swing.*;
import java.awt.*;

import static net.gabor7d2.pcbuilder.gui.ThemeController.TRANSPARENT_COLOR;

/**
 * The CategorySelectorBar is a panel that is at the top of the main window
 * and is used to select which categories of the currently displayed profile
 * to display in the main panel.
 */
public class CategorySelectorBar extends JPanel implements ProfileEventListener, CategoryEventListener {

    /**
     * Creates a new CategorySelectorBar.
     */
    public CategorySelectorBar() {
        setLayout(new WrapLayout(FlowLayout.LEFT));
        setOpaque(false);
        setBorder(BorderFactory.createMatteBorder(4, 8, 4, 8, TRANSPARENT_COLOR));

        // subscribe to events
        EventBus.getInstance().subscribeToProfileEvents(this);
        EventBus.getInstance().subscribeToCategoryEvents(this);
    }

    /**
     * Adds a category to the selector bar in the form of a JCheckBox that,
     * when ticked or unticked, will post a CategoryEvent with type ENABLE/DISABLE on the EventBus.
     *
     * @param category The category to add.
     */
    public void addCategory(Category category) {
        CategoryType cType = CategoryType.getCategoryTypeFromName(category.getType());

        JCheckBox ch = new JCheckBox(cType.getDisplayName());
        ch.setSelected(category.isEnabledByDefault());
        ch.setBorder(BorderFactory.createMatteBorder(4, 8, 4, 8, ch.getBackground()));
        ch.addActionListener(e -> {
            // set category enabled/disabled, or default enabled/disabled if in edit mode
            if (!Application.isEditMode()) {
                category.setEnabled(ch.isSelected());
            } else {
                category.setEnabledByDefault(ch.isSelected());
            }

            // post event
            CategoryEvent.CategoryEventType type = ch.isSelected() ? CategoryEvent.CategoryEventType.ENABLE : CategoryEvent.CategoryEventType.DISABLE;
            EventBus.getInstance().postEvent(new CategoryEvent(type, category));
            CompatibilityChecker.recalculateComponentCompatibility(category.getProfile());
            EventBus.getInstance().postEvent(new ComponentEvent(ComponentEvent.ComponentEventType.COMPATIBILITY_UPDATE, null));
        });
        add(ch);
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.SELECT) {
            // replace category checkboxes to categories in the newly selected profile
            removeAll();
            if (e.getProfile() != null) e.getProfile().getCategories().forEach(this::addCategory);
            setVisible(e.getProfile() != null);
            repaint();
        }
    }

    @Override
    public void processCategoryEvent(CategoryEvent e) {
        if (e.getType() == CategoryEvent.CategoryEventType.ADD) {
            addCategory(e.getCategory());
        }
    }
}
