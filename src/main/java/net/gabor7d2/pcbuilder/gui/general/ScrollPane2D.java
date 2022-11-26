package net.gabor7d2.pcbuilder.gui.general;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class ScrollPane2D extends JScrollPane implements MouseWheelListener {

    private final static Color BORDER_COLOR = Color.LIGHT_GRAY;

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
        outerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, BORDER_COLOR));

        // Set outer panel as the outer scroll pane's (this) viewport
        setViewportView(outerPanel);
        getViewport().setBackground(Color.LIGHT_GRAY);
        revalidate();
    }

    public void addRow(int index, ScrollPane2DRow row) {
        // Wrap the row in a panel to give it a border
        // (the row might have some border already, we don't want to replace that)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createMatteBorder(8, 8, 0, 8, BORDER_COLOR));
        panel.add(row);

        if (index >= 0 && index < rows.size()) {
            outerPanel.add(panel, index);
            rows.add(index, row);
        } else {
            outerPanel.add(panel);
            rows.add(row);
        }

        row.placedInsideScrollPane(this);
    }

    public void addRow(ScrollPane2DRow row) {
        this.addRow(-1, row);
    }

    public int getRowCount() {
        return rows.size();
    }

    public ScrollPane2DRow getRow(int index) {
        return rows.get(index);
    }

    public void setRowVisible(int index, boolean visible) {
        //outerPanel.getComponent(index).setVisible(visible);
        rows.get(index).setVisible(visible);
    }

    public boolean isRowVisible(int index) {
        //return outerPanel.getComponent(index).isVisible();
        return rows.get(index).isVisible();
    }

    public void toggleRowVisible(int index) {
        setRowVisible(index, !isRowVisible(index));
    }

    public void clearRows() {
        rows.clear();
        outerPanel.removeAll();
    }

    public void removeRow(int index) {
        rows.remove(index);
        outerPanel.remove(index);
    }

    // Outer panel's wheel listener
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        getVerticalScrollBar().setValue(getVerticalScrollBar().getValue() + e.getWheelRotation() * 16);
    }

    /**
     * Class for constructing a Row to be added to a ScrollPane2D using its
     * {@link ScrollPane2D#addRow(ScrollPane2DRow) addRow()} methods.
     * <p>
     * Since ScrollPane2D is itself a JScrollPane, placing any component under it
     * that's also a JScrollPane will cause weird behaviour. If your row
     * contains scroll panes, override {@link ScrollPane2DRow#placedInsideScrollPane(JScrollPane)}
     * to set them up properly. This method is called when the row is added to a ScrollPane2D.
     */
    public static class ScrollPane2DRow extends JPanel {
        public void placedInsideScrollPane(JScrollPane outerScrollPane) {
        }
    }
}
