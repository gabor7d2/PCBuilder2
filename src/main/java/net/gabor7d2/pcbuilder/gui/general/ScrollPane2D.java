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

    /**
     * The JScrollPane's viewport panel.
     */
    private final ScrollablePanel outerPanel;

    /**
     * The list of rows.
     */
    private final List<ScrollPane2DRow> rows = new ArrayList<>();

    /**
     * The color to use for the background.
     */
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
        outerPanel.setBackground(backgroundColor);
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
     * @param row   The row to be added.
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

    @Override
    public void removeAll() {
        outerPanel.removeAll();
    }

    /**
     * Gets row count.
     *
     * @return The amount of rows.
     */
    public int getRowCount() {
        return rows.size();
    }

    /**
     * Gets the index of the specified row.
     *
     * @param row The row to look up.
     * @return The index of the row, or -1 if it is
     * not inside this ScrollPane2D.
     */
    public int indexOfRow(ScrollPane2DRow row) {
        return rows.indexOf(row);
    }

    /**
     * Gets a specific row.
     *
     * @param index The index of the row to retrieve.
     * @return The row at the index.
     */
    public ScrollPane2DRow getRow(int index) {
        return rows.get(index);
    }

    /**
     * Sets the visibility of the row at the specified index.
     *
     * @param index   The index of the row.
     * @param visible Whether the row should be visible or not.
     */
    public void setRowVisible(int index, boolean visible) {
        rows.get(index).setVisible(visible);
    }

    /**
     * Gets whether the row at the specified index is visible.
     *
     * @param index The index of the row.
     * @return whether the row at the specified index is visible.
     */
    public boolean isRowVisible(int index) {
        return rows.get(index).isVisible();
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
     *
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
     * contains scroll panes, override {@link ScrollPane2DRow#placedInsideScrollPane(ScrollPane2D)}
     * to set them up properly. This method is called when the row is added to a ScrollPane2D.
     */
    public abstract static class ScrollPane2DRow extends JPanel {

        /**
         * Called when the row is added to a ScrollPane2D.
         * <p>
         * Set up the JScrollPanes in the row to forward mouse wheel events to
         * the given ScrollPane2D so that the horizontal scrolling is done on the
         * outer JScrollPane instead of in a JScrollPane inside the row.
         *
         * @param outerScrollPane The ScrollPane2D instance the row is added to.
         */
        public abstract void placedInsideScrollPane(ScrollPane2D outerScrollPane);
    }
}
