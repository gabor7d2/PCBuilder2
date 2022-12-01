package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.event.*;
import net.gabor7d2.pcbuilder.gui.general.SmartScrollPane;
import net.gabor7d2.pcbuilder.model.Category;

import javax.swing.*;

/**
 * The CategorySelectorBar is a panel that is at the top of the main window
 * and is used to select which categories of the currently displayed profile
 * to display in the main panel.
 */
public class CategorySelectorBar extends SmartScrollPane implements ProfileEventListener {

    /**
     * The content panel, which is the viewport's view.
     */
    private final JPanel contentPanel = new JPanel();

    /**
     * Creates a new CategorySelectorBar.
     */
    public CategorySelectorBar() {
        // TODO use flowlayout instead of boxlayout
        // setup viewport view
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        setViewportView(contentPanel);
        contentPanel.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, contentPanel.getBackground()));

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
        ch.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, ch.getBackground()));
        ch.addActionListener(e -> {
            // set category enabled/disabled
            c.setEnabled(ch.isSelected());

            // post event
            CategoryEvent.CategoryEventType type = ch.isSelected() ? CategoryEvent.CategoryEventType.ENABLE : CategoryEvent.CategoryEventType.DISABLE;
            EventBus.getInstance().postEvent(new CategoryEvent(type, c));
        });
        contentPanel.add(ch);
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.SELECT) {
            // replace category checkboxes to categories in the newly selected profile
            setVisible(e.getProfile() != null);
            contentPanel.removeAll();
            if (e.getProfile() != null) e.getProfile().getCategories().forEach(this::addCategory);
        }
    }
}
