package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.elements.SmartScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategorySelectorBar extends SmartScrollPane {

    private static final Color BG_COLOR = Color.DARK_GRAY;
    private static final Color FG_COLOR = Color.WHITE;

    public interface CategorySelectedListener {
        void categorySelected(String name, boolean selected);
    }

    private final List<CategorySelectedListener> selectedListeners = new ArrayList<>();

    private final JPanel contentPanel = new JPanel();

    public CategorySelectorBar() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        setViewportView(contentPanel);

        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, BG_COLOR));

        addCategory("CPU", true);
        addCategory("GPU", false);
        addCategory("Motherboard", false);
        addCategory("Cooler", true);
    }

    public void addCategorySelectedListener(CategorySelectedListener l) {
        selectedListeners.add(l);
    }

    public void addCategory(String name, boolean enabled) {
        JCheckBox ch = new JCheckBox(name);
        ch.setBackground(BG_COLOR);
        ch.setForeground(FG_COLOR);
        ch.setSelected(enabled);
        ch.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, BG_COLOR));
        ch.addActionListener(e -> {
            selectedListeners.forEach(s -> s.categorySelected(name, ch.isSelected()));
        });
        contentPanel.add(ch);
    }
}
