package net.gabor7d2.pcbuilder.gui.general;

import net.gabor7d2.pcbuilder.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.Collections;

/**
 * ClickableLabel is a JLabel that can have an URL set, which
 * is opened when the user clicks on the label. If it has a URL
 * set, it is shown visually by underlining the text and changing
 * the cursor to the Hand Cursor when hovering over the label.
 */
public class ClickableLabel extends JLabel implements MouseListener {

    private String url = "";

    /**
     * Create clickable label.
     */
    public ClickableLabel() {
        addMouseListener(this);
    }

    /**
     * Create clickable label displaying the specified text.
     * @param initialText The text to display.
     */
    public ClickableLabel(String initialText) {
        this();
        setText(initialText);
    }

    /**
     * Alters the font of this label to use the specified style and size.
     * @param style Font style.
     * @param size Font size.
     */
    public void alterFont(int style, float size) {
        setFont(getFont().deriveFont(style, size));
    }

    /**
     * Sets the url that gets opened when this label is clicked.
     * If the specified url isn't blank, the label's text will be
     * underlined, and the Hand Cursor will be shown when hovering
     * above it with the cursor.
     * @param url The url to open when clicked.
     * @param tooltip Optional tooltip that gets displayed when
     *                mouse is hovering over this label.
     */
    public void setUrl(String url, String tooltip) {
        this.url = url;
        if (!url.isBlank()) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setFont(getFont().deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON)));

            if (!tooltip.isBlank()) {
                setToolTipText(tooltip);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !url.isBlank()) {
            Utils.openWebsite(url);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
