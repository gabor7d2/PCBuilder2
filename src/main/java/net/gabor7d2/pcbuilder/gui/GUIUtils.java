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

    /**
     * Freezes the specified component's size to the specified size
     * so that it won't be resized automatically
     *
     * @param comp The component that's size should be frozen
     * @param d    The dimension to freeze the size of the component to
     */
    public static void freezeSize(Component comp, Dimension d) {
        comp.setSize(d);
        comp.setMinimumSize(d);
        comp.setMaximumSize(d);
        comp.setPreferredSize(d);
    }

    /**
     * Freezes the specified component's size to the specified width and height
     * so that it won't be resized automatically
     *
     * @param comp The component that's size should be frozen
     * @param width The width to be used to freeze the component's size
     * @param height The height to be used to freeze the component's size
     */
    public static void freezeSize(Component comp, int width, int height) {
        freezeSize(comp, new Dimension(width, height));
    }

    public static void adjustWidth(Component comp, int width) {
        freezeSize(comp, comp.getWidth() + width, comp.getHeight());
    }

    public static void adjustWidth(Component comp, Component childComp) {
        adjustWidth(comp, childComp.getPreferredSize().width);
    }

    public static void adjustHeight(Component comp, int height) {
        freezeSize(comp, comp.getWidth(), comp.getHeight() + height);
    }

    public static void adjustHeight(Component comp, Component childComp) {
        adjustHeight(comp, childComp.getPreferredSize().height);
    }
}
