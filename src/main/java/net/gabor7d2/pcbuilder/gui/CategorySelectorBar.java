package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.general.SmartScrollPane;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.CategoryType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// TODO use flowlayout instead of boxlayout
public class CategorySelectorBar extends SmartScrollPane {

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

        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, BG_COLOR));

        Category c1 = new Category();
        c1.setType(CategoryType.getCategoryTypeFromName("CPU"));

        Category c2 = new Category();
        c2.setType(CategoryType.getCategoryTypeFromName("GPU"));
        c2.setEnabled(false);

        Category c3 = new Category();
        c3.setType(CategoryType.getCategoryTypeFromName("MB"));
        c3.setEnabled(false);

        Category c4 = new Category();
        c4.setType(CategoryType.getCategoryTypeFromName("AirCooler"));

        addCategory(c1);
        addCategory(c2);
        addCategory(c3);
        addCategory(c4);
    }

    public void addCategorySelectedListener(CategorySelectedListener l) {
        selectedListeners.add(l);
    }

    public void addCategory(Category c) {
        JCheckBox ch = new JCheckBox(c.toString());
        ch.setBackground(BG_COLOR);
        ch.setForeground(FG_COLOR);
        ch.setSelected(c.isEnabled());
        ch.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, BG_COLOR));
        ch.addActionListener(e -> {
            selectedListeners.forEach(s -> s.categorySelected(c, ch.isSelected()));
        });
        contentPanel.add(ch);
    }

    public void addCategories(List<Category> categories) {

    }
}
