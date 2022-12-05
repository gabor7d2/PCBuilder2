package net.gabor7d2.pcbuilder.gui.general;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * ScrollPane that works exactly the same as {@link JScrollPane}, except that it
 * only shows the horizontal/vertical scrollbar when it's necessary, and grows to accommodate
 * the scrollbar when it's visible, instead of drawing it on top of the content.
 */
public class SmartScrollPane extends JScrollPane implements ComponentListener {

    /**
     * Creates a new SmartScrollPane.
     *
     * @param view The view to use as the viewport's view.
     */
    public SmartScrollPane(Component view) {
        super(view);

        handleScrollbars();

        setBorder(null);
        addComponentListener(this);
        revalidate();

        //putClientProperty("JScrollPane.smoothScrolling", true);
    }

    /**
     * Creates a new SmartScrollPane, with no view inside.
     */
    public SmartScrollPane() {
        this(null);
    }

    /**
     * Handle the hiding of scroll bars when they are not needed
     */
    public void handleScrollbars() {
        if (getSize().width >= getPreferredSize().width) {
            setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            getHorizontalScrollBar().setVisible(false);
        } else {
            setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            getHorizontalScrollBar().setVisible(true);
        }

        if (getSize().height >= getPreferredSize().height) {
            setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            getVerticalScrollBar().setVisible(false);
        } else {
            setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            getVerticalScrollBar().setVisible(true);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        handleScrollbars();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void updateUI() {
        super.updateUI();
        // counteract theme switching adding default border
        setBorder(null);
    }
}
