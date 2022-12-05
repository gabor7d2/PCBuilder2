package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.event.*;
import net.gabor7d2.pcbuilder.model.Category;

import javax.swing.*;
import java.awt.*;

import static net.gabor7d2.pcbuilder.gui.ThemeController.TRANSPARENT_COLOR;

/**
 * The CategorySelectorBar is a panel that is at the top of the main window
 * and is used to select which categories of the currently displayed profile
 * to display in the main panel.
 */
public class CategorySelectorBar extends JPanel implements ProfileEventListener {

    /**
     * Creates a new CategorySelectorBar.
     */
    public CategorySelectorBar() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setOpaque(false);
        setBorder(BorderFactory.createMatteBorder(4, 8, 4, 8, TRANSPARENT_COLOR));

        // subscribe to events
        EventBus.getInstance().subscribeToProfileEvents(this);
    }

    /**
     * Adds a category to the selector bar in the form of a JCheckBox that,
     * when ticked or unticked, will post a CategoryEvent with type ENABLE/DISABLE on the EventBus.
     *
     * @param c The category to add.
     */
    public void addCategory(Category c) {
        JCheckBox ch = new JCheckBox(c.toString());
        ch.setSelected(c.isEnabledByDefault());
        ch.setBorder(BorderFactory.createMatteBorder(4, 8, 4, 8, ch.getBackground()));
        ch.addActionListener(e -> {
            // set category enabled/disabled
            c.setEnabled(ch.isSelected());

            // post event
            CategoryEvent.CategoryEventType type = ch.isSelected() ? CategoryEvent.CategoryEventType.ENABLE : CategoryEvent.CategoryEventType.DISABLE;
            EventBus.getInstance().postEvent(new CategoryEvent(type, c));
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
        }
    }
}
