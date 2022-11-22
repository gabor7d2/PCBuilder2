package net.gabor7d2.pcbuilder.gui.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class ScrollPane2D extends JScrollPane implements MouseWheelListener {

    private final ScrollablePanel outerPanel;
    private final List<ScrollPane2DRow> rows = new ArrayList<>();

    public ScrollPane2D() {
        // Setup this
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setWheelScrollingEnabled(false);
        addMouseWheelListener(this);
        setBorder(null);

        // Setup outer, vertically scrollable panel
        outerPanel = new ScrollablePanel();
        outerPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
        outerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, Color.LIGHT_GRAY));

        // Set outer panel as the outer scroll pane's (this) viewport
        setViewportView(outerPanel);
        getViewport().setBackground(Color.LIGHT_GRAY);
        revalidate();
    }

    public void addRow(int index, ScrollPane2DRow row) {
        rows.add(index, row);
        outerPanel.add(row, index);
        row.placedInsideScrollPane(this);
    }

    public void addRow(ScrollPane2DRow row) {
        rows.add(row);
        outerPanel.add(row);
        row.placedInsideScrollPane(this);
    }

    public int getRowCount() {
        return rows.size();
    }

    public ScrollPane2DRow getRow(int index) {
        return rows.get(index);
    }

    public void clearRows() {
        rows.clear();
        outerPanel.removeAll();
    }

    // Outer panel's wheel listener
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        getVerticalScrollBar().setValue(getVerticalScrollBar().getValue() + e.getWheelRotation() * 16);
    }
    
    public static class ScrollPane2DRow extends JPanel {
        public void placedInsideScrollPane(JScrollPane outerScrollPane) {
        }
    }
}
