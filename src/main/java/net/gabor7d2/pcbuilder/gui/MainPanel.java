package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.components.ComponentManager;
import net.gabor7d2.pcbuilder.components.StateChangeListener;
import net.gabor7d2.pcbuilder.gui.elements.*;
import net.gabor7d2.pcbuilder.persistence.ProfileManager;
import net.gabor7d2.pcbuilder.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPanel extends JPanel implements ActionListener, StateChangeListener {

    private final Map<String, ComponentCategoryPanel> categoryMap = new HashMap<>();
    private final Map<String, Integer> categoryIndexMap = new HashMap<>();

    private final ScrollPane2D scrollPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;

    private AutoScrollPane headerPane;

    private JLabel totalPrice;

    public MainPanel(int windowWidth, int windowHeight) {
        super(new BorderLayout());
        scrollPanel = new ScrollPane2D(windowWidth, windowHeight) {
            @Override
            public void mouseEntered(MouseEvent e) {
                MainFrame.setHoveredHorizontalScrollPane((JScrollPane) e.getComponent().getParent().getParent().getParent());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                MainFrame.setHoveredHorizontalScrollPane(null);
            }
        };

        EventQueue.invokeLater(() -> {
            MainFrame.setHoveredVerticalScrollPane(scrollPanel);
            add(scrollPanel);

            headerPane = new AutoScrollPane(SwingConstants.HORIZONTAL, true);
            headerPane.getViewport().setBackground(Color.DARK_GRAY);
            headerPanel = headerPane.getContentPanel();
            headerPanel.setBackground(Color.DARK_GRAY);
            headerPanel.setBorder(BorderFactory.createMatteBorder(8, 4, 8, 4, Color.DARK_GRAY));
            add(headerPane, BorderLayout.NORTH);

            AutoScrollPane footerPane = new AutoScrollPane(SwingConstants.HORIZONTAL, true);
            footerPane.getViewport().setBackground(Color.DARK_GRAY);
            footerPanel = footerPane.getContentPanel();
            footerPanel.setBackground(Color.DARK_GRAY);
            footerPanel.setBorder(BorderFactory.createMatteBorder(12, 16, 12, 16, Color.DARK_GRAY));
            add(footerPane, BorderLayout.SOUTH);

            //String totalStr = Format.formatCurrency(String.valueOf(10000000), "", " Ft");
            //totalPrice = new JLabel("Total Price: " + totalStr);
            totalPrice = new JLabel("Total Price: 0 Ft");

            totalPrice.setForeground(Color.WHITE);
            totalPrice.setFont(totalPrice.getFont().deriveFont(Font.BOLD, 13));

            //Utils.fixSize(totalPrice, totalPrice.getPreferredSize());
            //totalPrice.setText("Total Price: 0 Ft");
            totalPrice.setVisible(false);
            totalPrice.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 16, new Color(0, 0, 0, 0)));
            footerPanel.add(totalPrice);

            footerPanel.add(new ProfileSelector());
            footerPanel.add(Box.createRigidArea(new Dimension(8, 0)));

            ComponentManager.loadAll(ProfileManager.getProfiles());

            JButton rename = new JButton("Rename");
            rename.setBackground(Color.DARK_GRAY);
            rename.addActionListener(e -> System.out.println("Rename profile")/*profileManager.renameProfile()*/);
            footerPanel.add(rename);
            footerPanel.add(Box.createRigidArea(new Dimension(8, 0)));

            JButton remove = new JButton("Remove");
            remove.setBackground(Color.DARK_GRAY);
            remove.addActionListener(e -> System.out.println("Remove profile")/*profileManager.removeProfile()*/);
            footerPanel.add(remove);
            footerPanel.add(Box.createRigidArea(new Dimension(8, 0)));

            JButton add = new JButton("Add");
            add.setBackground(Color.DARK_GRAY);
            add.addActionListener(e -> System.out.println("Add profile")/*profileManager.addProfile()*/);
            footerPanel.add(add);
            footerPanel.add(Box.createRigidArea(new Dimension(8, 0)));

            JButton reload = new JButton("Reload");
            reload.setBackground(Color.DARK_GRAY);
            reload.addActionListener(e -> reloadEverything());
            footerPanel.add(reload);
            footerPanel.add(Box.createRigidArea(new Dimension(8, 0)));

            JButton help = new JButton("Help");
            help.setBackground(Color.DARK_GRAY);
            help.addActionListener(e -> System.out.println("Help dialog")/*VersionManager.showHelpDialog(parentFrame)*/);
            footerPanel.add(help);

            revalidate();
        });

        ComponentManager.addStateChangeListener(this);
    }

    public void updateTotalPrice() {
        double total = 0;

        for (Component comp : headerPanel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (checkBox.isSelected()) {
                    ComponentCategoryPanel.CategoryItem item = categoryMap.get(checkBox.getText()).getSelectedItem();
                    if (item == null) continue;
                    try {
                        total += item.getComponent().getPrice();
                    } catch (Exception ignored) {

                    }
                }
            }
        }

        //String totalStr = Format.formatCurrency(String.valueOf(total));
        totalPrice.setVisible(total != 0);
        totalPrice.setText("Total Price: " + total);
    }

    public int addCategory(ComponentCategoryPanel category, boolean isEnabled) {
        int index;

        category.addCategoryItemListener((category1, item, index1) -> updateTotalPrice());

        if (!categoryIndexMap.containsKey(category.getDisplayName())) {
            index = scrollPanel.addRow(category.getItemComponents(), category.getPreviewPanel(), isEnabled);
            categoryIndexMap.put(category.getDisplayName(), index);
            categoryMap.put(category.getDisplayName(), category);

            JCheckBox checkBox = new JCheckBox(category.getDisplayName());
            checkBox.setSelected(isEnabled);
            checkBox.setForeground(Color.WHITE);
            checkBox.setBackground(Color.DARK_GRAY);
            checkBox.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, Color.DARK_GRAY));
            checkBox.addActionListener(this);
            checkBox.setVisible(false);
            headerPanel.add(checkBox);
            headerPanel.revalidate();

            EventQueue.invokeLater(() -> {
                checkBox.setVisible(true);
                revalidate();
                headerPane.handleScrollbars();
                updateTotalPrice();
            });
        } else {
            index = categoryIndexMap.get(category.getDisplayName());
            scrollPanel.clearRow(index);
            scrollPanel.setPreviewPanel(index, category.getPreviewPanel());
            scrollPanel.addComponents(index, category.getItemComponents());
        }
        return index;
    }

    public void removeCategory(String categoryName) {
        if (categoryIndexMap.containsKey(categoryName)) {
            int index = categoryIndexMap.get(categoryName);
            scrollPanel.removeRow(index);
            headerPanel.remove(findCheckBoxByName(categoryName));
            headerPanel.revalidate();

            categoryIndexMap.remove(categoryName);
            updateTotalPrice();
        }
    }

    public void enableCategory(String categoryName) {
        Utils.postEvent(() -> {
            JCheckBox cb = findCheckBoxByName(categoryName);
            if (cb == null) return;
            if (!cb.isSelected()) actionPerformed(new ActionEvent(cb, 0, cb.getText()));
            cb.setSelected(true);
            updateTotalPrice();
        });
    }

    public void disableCategory(String categoryName) {
        Utils.postEvent(() -> {
            JCheckBox cb = findCheckBoxByName(categoryName);
            if (cb == null) return;
            if (cb.isSelected()) actionPerformed(new ActionEvent(cb, 0, cb.getText()));
            cb.setSelected(false);
            updateTotalPrice();
        });
    }

    private JCheckBox findCheckBoxByName(String categoryName) {
        for (Component component : headerPanel.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.getText().equals(categoryName)) return checkBox;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = categoryIndexMap.get(e.getActionCommand());
        scrollPanel.toggleRowVisible(index);
        //parentFrame.revalidate();
        updateTotalPrice();
    }

    @Override
    public void loaded(String type, String displayName, boolean enabled, List<net.gabor7d2.pcbuilder.components.Component> affectedComponents, String categoryUrl, int selIndex) {
        addCategory(new ComponentCategoryPanel(type, displayName, affectedComponents, categoryUrl, selIndex), enabled);
    }

    @Override
    public void reloaded(String type, String displayName, boolean enabled, List<net.gabor7d2.pcbuilder.components.Component> affectedComponents, String categoryUrl, int selIndex) {
        addCategory(new ComponentCategoryPanel(type, displayName, affectedComponents, categoryUrl, selIndex), enabled);
    }

    @Override
    public void removed(String type) {
        removeCategory(type);
    }

    // TODO recycle components instead of removing and readding them
    public void reloadEverything() {
        // A new set of images are going to be loaded, so discard any
        // image loading that may still be running to fill old ImageLabels
        ImageLabel.discardBackgroundTasks();

        /*for (ComponentCategory cat : categoryMap.values()) {
            ImageLabel previewImage = cat.getPreview().getImageLabel();
            if (previewImage != null && previewImage.getIcon() != null && previewImage.getIcon() instanceof ImageIcon) {
                System.out.println("Removing preview image!");
                ImageIcon icon = (ImageIcon) previewImage.getIcon();
                icon.getImage().flush();
            }

            for (ComponentCategory.CategoryItem item : cat.getItems()) {
                ImageLabel itemImage = item.getImageLabel();
                if (itemImage != null && itemImage.getIcon() != null && itemImage.getIcon() instanceof ImageIcon) {
                    System.out.println("Removing item image!");
                    ImageIcon icon = (ImageIcon) itemImage.getIcon();
                    icon.getImage().flush();
                }
            }
        }*/

        scrollPanel.removeAllRows();
        headerPanel.removeAll();
        revalidate();
        categoryIndexMap.clear();

        ComponentManager.reload();

        updateTotalPrice();
    }
}
