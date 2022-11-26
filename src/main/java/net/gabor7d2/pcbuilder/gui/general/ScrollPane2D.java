package net.gabor7d2.pcbuilder.gui.general;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for displaying a scrollable panel, which can contain 'rows' - horizontal
 * panels - that can also contain scrolling content. This class coordinates the
 * scrolling of the outer panel and the inner rows so that their behaviour is intuitive.
 * <p>
 * Rows can be constructed by extending {@link ScrollPane2DRow} and then adding instances
 * using the {@link ScrollPane2D#addRow(ScrollPane2DRow) addRow()} methods.
 */
public class ScrollPane2D extends JScrollPane implements MouseWheelListener {

    // The scroll pane's panel
    private final ScrollablePanel outerPanel;

    // The list of rows
    private final List<ScrollPane2DRow> rows = new ArrayList<>();

    private final Color backgroundColor;

    /**
     * Creates a new ScrollPane2D.
     *
     * @param backgroundColor The background color, set to null to use default color.
     */
    public ScrollPane2D(Color backgroundColor) {
        // Setup this
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setWheelScrollingEnabled(false);
        addMouseWheelListener(this);
        setBorder(null);

        this.backgroundColor = backgroundColor;
        if (backgroundColor == null) backgroundColor = getViewport().getBackground();

        // Setup outer, vertically scrollable panel
        outerPanel = new ScrollablePanel();
        outerPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
        outerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, backgroundColor));

        // Set outer panel as the outer scroll pane's (this) viewport
        setViewportView(outerPanel);
        getViewport().setBackground(backgroundColor);
        revalidate();
    }

    /**
     * Creates a new ScrollPane2D.
     */
    public ScrollPane2D() {
        this(null);
    }

    /**
     * Adds a row to this scrollpane.
     *
     * @param index Which position to add the row to.
     * @param row The row to be added.
     */
    public void addRow(int index, ScrollPane2DRow row) {
        // Wrap the row in a panel to give it a border
        // (the row might have some border already, we don't want to replace that)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createMatteBorder(8, 8, 0, 8, backgroundColor));
        panel.add(row);

        if (index >= 0 && index < rows.size()) {
            outerPanel.add(panel, index);
            rows.add(index, row);
        } else {
            outerPanel.add(panel);
            rows.add(row);
        }

        // notify row that it has been placed inside a scroll pane
        row.placedInsideScrollPane(this);
    }

    /**
     * Adds a row to this scrollpane, to the last position.
     *
     * @param row The row to be added.
     */
    public void addRow(ScrollPane2DRow row) {
        this.addRow(-1, row);
    }

    /**
     * Gets row count.
     */
    public int getRowCount() {
        return rows.size();
    }

    /**
     * Gets a specific row.
     * @param index The index of the row to retrieve.
     * @return The row at the index.
     */
    public ScrollPane2DRow getRow(int index) {
        return rows.get(index);
    }

    /**
     * Sets the visibility of the row at the specified index.
     * @param index The index of the row.
     * @param visible Whether the row should be visible or not.
     */
    public void setRowVisible(int index, boolean visible) {
        //outerPanel.getComponent(index).setVisible(visible);
        rows.get(index).setVisible(visible);
    }

    /**
     * Gets whether the row at the specified index is visible.
     * @param index The index of the row.
     */
    public boolean isRowVisible(int index) {
        //return outerPanel.getComponent(index).isVisible();
        return rows.get(index).isVisible();
    }

    /**
     * Toggles visibility of the row at the specified index.
     * @param index The index of the row.
     */
    public void toggleRowVisible(int index) {
        setRowVisible(index, !isRowVisible(index));
    }

    /**
     * Clears all rows from this scrollpane.
     */
    public void clearRows() {
        rows.clear();
        outerPanel.removeAll();
    }

    /**
     * Removes a row from this scrollpane.
     * @param index The index of the row to remove.
     */
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
     * Abstract class for constructing a Row to be added to a ScrollPane2D using its
     * {@link ScrollPane2D#addRow(ScrollPane2DRow) addRow()} methods.
     * <p>
     * Since ScrollPane2D is itself a JScrollPane, placing any component under it
     * that's also a JScrollPane will cause weird behaviour. If your row
     * contains scroll panes, override {@link ScrollPane2DRow#placedInsideScrollPane(JScrollPane)}
     * to set them up properly. This method is called when the row is added to a ScrollPane2D.
     */
    public abstract static class ScrollPane2DRow extends JPanel {

        public abstract void placedInsideScrollPane(JScrollPane outerScrollPane);
    }
}
