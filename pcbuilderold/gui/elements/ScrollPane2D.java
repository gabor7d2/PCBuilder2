package net.gabor7d2.pcbuilderold.gui.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class ScrollPane2D extends JScrollPane implements MouseWheelListener, MouseListener {

    public int addRow(List<JComponent> components, boolean isEnabled) {
        // Fill in the horizontal scrollable panel with the components specified, aligning in X axis
        for (JComponent comp : components) {
            comp.addMouseListener(this);
            innerScrollPanel.getContentPanel().add(comp);
        }

        innerScrollPanel.setVisible(false);

        outerPanel.add(innerScrollPanel);
        innerScrollPanels.add(innerScrollPanel);

        if (isEnabled) {
            EventQueue.invokeLater(() -> innerScrollPanel.setVisible(true));
        }

        return innerScrollPanels.indexOf(innerScrollPanel);
    }

    public int addRow(List<JComponent> components) {
        return addRow(components, true);
    }

    public int addRow(JComponent component) {
        int index = addRow();
        addComponent(index, component);
        return index;
    }

    public int addRow() {
        return addRow(new ArrayList<>());
    }

    public int addRow(List<JComponent> components, JPanel previewPanel, boolean isEnabled) {
        int index = addRow(components, isEnabled);
        setPreviewPanel(index, previewPanel);
        return index;
    }

    public int addRow(List<JComponent> components, JPanel previewPanel) {
        return addRow(components, previewPanel, true);
    }

    public int addRow(JComponent component, JPanel previewPanel) {
        int index = addRow(component);
        setPreviewPanel(index, previewPanel);
        return index;
    }

    public void addComponent(int index, JComponent comp) {
        ((JPanel) innerScrollPanels.get(index).getScrollPane().getViewport().getView()).add(comp);
        innerScrollPanels.get(index).getScrollPane().getViewport().getView().revalidate();
    }

    public void addComponents(int index, List<JComponent> components) {
        JPanel innerPanel = (JPanel) innerScrollPanels.get(index).getScrollPane().getViewport().getView();
        for (JComponent comp : components) {
            innerPanel.add(comp);
        }
        innerScrollPanels.get(index).getScrollPane().getViewport().getView().revalidate();
    }

    public void clearRow(int index) {
        JPanel innerPanel = (JPanel) innerScrollPanels.get(index).getScrollPane().getViewport().getView();
        innerPanel.removeAll();
    }
}
