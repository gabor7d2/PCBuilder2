package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.gui.event.ProfileEventListener;
import net.gabor7d2.pcbuilder.gui.general.SmartScrollPane;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.CategoryType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// TODO use flowlayout instead of boxlayout
public class CategorySelectorBar extends SmartScrollPane implements ProfileEventListener {

    private static final Color BG_COLOR = Color.DARK_GRAY;
    private static final Color FG_COLOR = Color.WHITE;

    public interface CategorySelectedListener {
        void categorySelected(Category name, boolean selected);
    }

    private final List<CategorySelectedListener> selectedListeners = new ArrayList<>();

    private final JPanel contentPanel = new JPanel();

    private List<Category> categories;

    public CategorySelectorBar() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        setViewportView(contentPanel);

        //contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, contentPanel.getBackground()));
    }

    public void addCategorySelectedListener(CategorySelectedListener l) {
        selectedListeners.add(l);
    }

    public void addCategory(Category c) {
        JCheckBox ch = new JCheckBox(c.toString());
        //ch.setBackground(BG_COLOR);
        //ch.setForeground(FG_COLOR);
        ch.setSelected(c.isEnabled());
        ch.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, ch.getBackground()));
        ch.addActionListener(e -> {
            selectedListeners.forEach(s -> s.categorySelected(c, ch.isSelected()));
        });
        contentPanel.add(ch);
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.SELECT) {
            setVisible(e.getProfile() != null);
            contentPanel.removeAll();
            if (e.getProfile() != null) e.getProfile().getCategories().forEach(this::addCategory);
        }
    }
}