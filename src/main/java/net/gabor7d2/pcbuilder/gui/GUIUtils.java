package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.general.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class containing GUI utilities.
 */
public class GUIUtils {

    /**
     * Image missing image used as a substitute when an image could not be loaded.
     */
    private final static Image IMAGE_MISSING;

    // TODO cache classpath images

    static {
        IMAGE_MISSING = loadImageFromClasspath("/image_missing_icon_general.png");
    }

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
     * @param comp   The component that's size should be frozen
     * @param width  The width to be used to freeze the component's size
     * @param height The height to be used to freeze the component's size
     */
    public static void freezeSize(Component comp, int width, int height) {
        freezeSize(comp, new Dimension(width, height));
    }

    /**
     * Loads an ImageIcon from the specified file and smooth scales it to the specified
     * resolution. If the image is not found, returns the no image icon.
     *
     * @param file   The file of the image to load.
     * @param width  The width to scale to.
     * @param height The height to scale to.
     * @return The loaded icon, or the no image icon if not found, or null if something went wrong.
     */
    public static ImageIcon loadImageIconFromFile(File file, int width, int height) {
        Image image = loadImageFromFile(file);
        if (image == null) image = IMAGE_MISSING;
        if (image != null) return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        else return null;
    }

    /**
     * Loads an ImageIcon from the specified path on the current classpath and smooth
     * scales it to the specified resolution.
     * If the image is not found, returns the no image icon.
     *
     * @param path   The path of the image on the current classpath.
     * @param width  The width to scale to.
     * @param height The height to scale to.
     * @return The loaded icon, or the no image icon if not found, or null if something went wrong.
     */
    public static ImageIcon loadImageIconFromClasspath(String path, int width, int height) {
        Image image = loadImageFromClasspath(path);
        if (image == null) image = IMAGE_MISSING;
        if (image != null) return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        else return null;
    }

    /**
     * Loads an Image from the specified file. If anything goes wrong, returns null.
     *
     * @param file The file of the image to load.
     * @return The loaded image, or null if something went wrong.
     */
    public static Image loadImageFromFile(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Error while loading image from file: " + file.getPath());
        }
        return null;
    }

    /**
     * Loads an Image from the specified path on the current classpath.
     * If anything goes wrong, returns null.
     *
     * @param path The path of the image on the current classpath.
     * @return The loaded image, or null if something went wrong.
     */
    public static Image loadImageFromClasspath(String path) {
        path = path.replace('\\', '/');

        try (InputStream is = ImageLoader.class.getResourceAsStream(path)) {
            if (is == null) return null;
            return ImageIO.read(is);
        } catch (Exception e) {
            System.out.println("Error while loading image from classpath: " + path);
        }
        return null;
    }
}
