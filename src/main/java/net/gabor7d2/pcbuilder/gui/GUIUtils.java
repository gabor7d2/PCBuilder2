package net.gabor7d2.pcbuilder.gui;

import java.awt.*;

public class GUIUtils {

    /**
     * Freezes the specified component's size to it's preferred size
     * so that it won't be resized automatically
     *
     * @param comp The component that's size should be frozen
     */
    public static void freezeSize(Component comp) {
        Dimension d = comp.getPreferredSize();
        comp.setSize(d);
        comp.setMinimumSize(d);
        comp.setMaximumSize(d);
        comp.setPreferredSize(d);
    }
}
