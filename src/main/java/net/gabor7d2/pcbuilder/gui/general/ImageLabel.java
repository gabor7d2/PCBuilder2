package net.gabor7d2.pcbuilder.gui.general;

import net.gabor7d2.pcbuilder.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * JLabel with additional methods to easily set the image of it to
 * an image from classpath or from a file, and handle asynchronous image loading.
 */
public class ImageLabel extends JLabel {

    /**
     * Stores what the reload count was when this ImageLabel was created
     */
    private final int currentReloadCount;

    /**
     * Stores the path of the image displayed on the label.
     */
    private String imagePath;

    /**
     * Stores whether the imagePath refers to an image on the classpath (true)
     * or in the normal filesystem (false)
     */
    private boolean isPathOnClasspath = false;

    /**
     * Stores the width/height of the image on the label.
     */
    private int imageWidth, imageHeight;

    /**
     * Creates a new ImageLabel.
     */
    public ImageLabel() {
        currentReloadCount = ImageLoader.getReloadCount();
    }

    /**
     * Sets the image of the label, scaling it to the specified dimensions.
     *
     * @param path   The path of the image file on the file system.
     * @param width  The width the label should be without padding.
     * @param height The height the label should be without padding.
     */
    public void setImageFromFile(String path, int width, int height) {
        setImageParameters(path, false, width, height);
        setIcon(GUIUtils.loadImageIconFromFile(new File(path), width, height));
    }

    /**
     * Sets the image of the label, scaling it to the specified dimensions.
     * <p>
     * This method doesn't load the image, just queues the loading in {@link ImageLoader}.
     * The queue can then be processed when desired using {@link ImageLoader#processQueue()}
     *
     * @param path   The path of the image file on the file system.
     * @param width  The width the label should be without padding.
     * @param height The height the label should be without padding.
     */
    public void setImageFromFileAsync(String path, int width, int height) {
        setImageParameters(path, false, width, height);
        ImageLoader.queueAsyncImageLoad(this, ImageLoader.ImageSource.FILE, path);
    }

    /**
     * Sets the image of the label, scaling it to the specified dimensions.
     *
     * @param path   The path of the image file on the classpath.
     * @param width  The width the label should be without padding.
     * @param height The height the label should be without padding.
     */
    public void setImageFromClasspath(String path, int width, int height) {
        setImageParameters(path, true, width, height);
        setIcon(GUIUtils.loadImageIconFromClasspath(path, width, height));
    }

    /**
     * Sets the image of the label, scaling it to the specified dimensions.
     * <p>
     * This method doesn't load the image, just queues the loading in {@link ImageLoader}.
     * The queue can then be processed when desired using {@link ImageLoader#processQueue()}
     *
     * @param path   The path of the image file on the classpath.
     * @param width  The width the label should be without padding.
     * @param height The height the label should be without padding.
     */
    public void setImageFromClasspathAsync(String path, int width, int height) {
        setImageParameters(path, true, width, height);
        ImageLoader.queueAsyncImageLoad(this, ImageLoader.ImageSource.CLASSPATH, path);
    }

    /**
     * Sets image parameters.
     */
    private void setImageParameters(String path, boolean pathOnClasspath, int width, int height) {
        imagePath = path;
        isPathOnClasspath = pathOnClasspath;
        this.imageWidth = width;
        this.imageHeight = height;
        GUIUtils.freezeSize(this, width, height);
    }

    /**
     * Gets the path of the image displayed on the label.
     *
     * @return the path of the image displayed on the label
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Gets the width of the image on the label.
     *
     * @return The width of the image on the label
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * Gets the height of the image on the label.
     *
     * @return The height of the image on the label
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * Sets the border of this ImageLabel to the specified color and thickness
     *
     * @param color     The color of the border
     * @param thickness The thickness of the border (in pixels)
     */
    public void setBorder(Color color, int thickness) {
        super.setBorder(BorderFactory.createLineBorder(color, thickness));
        GUIUtils.freezeSize(this, imageWidth + thickness * 2, imageHeight + thickness * 2);
    }

    /**
     * Gets what the reload count was when this ImageLabel was created
     *
     * @return what the reload count was when this ImageLabel was created
     */
    public int getCurrentReloadCount() {
        return currentReloadCount;
    }
}
