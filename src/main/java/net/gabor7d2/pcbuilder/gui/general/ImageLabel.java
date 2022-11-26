package net.gabor7d2.pcbuilder.gui.general;

import net.gabor7d2.pcbuilder.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

// TODO Fix memory leak inside ImageLabels (probably the Images stay in memory after the Label has been destroyed)
public class ImageLabel extends JLabel {

    // Stores what reload count was when this ImageLabel was created
    private final int currentReloadCount;

    private String imagePath;
    private int width, height;

    /**
     * Creates a new ImageLabel.
     */
    public ImageLabel() {
        currentReloadCount = ImageLoader.getReloadCount();
    }

    public void setImageFromFile(String path, int width, int height) {
        setImageParameters(path, width, height);
        setIcon(GUIUtils.loadImageIconFromFile(new File(path), width, height));
    }

    public void setImageFromFileAsync(String path, int width, int height) {
        setImageParameters(path, width, height);
        ImageLoader.queueAsyncImageLoad(this, ImageLoader.ImageSource.FILE, path);
    }

    public void setImageFromClasspath(String path, int width, int height) {
        setImageParameters(path, width, height);
        setIcon(GUIUtils.loadImageIconFromClasspath(path, width, height));
    }

    public void setImageFromClasspathAsync(String path, int width, int height) {
        setImageParameters(path, width, height);
        ImageLoader.queueAsyncImageLoad(this, ImageLoader.ImageSource.CLASSPATH, path);
    }

    private void setImageParameters(String path, int width, int height) {
        imagePath = path;
        this.width = width;
        this.height = height;
        GUIUtils.freezeSize(this, width, height);
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getLabelWidth() {
        return width;
    }

    public int getLabelHeight() {
        return height;
    }

    /**
     * Sets the border of this ImageLabel to the specified color and thickness
     *
     * @param color     The color of the border
     * @param thickness The thickness of the border (in pixels)
     */
    public void setBorder(Color color, int thickness) {
        super.setBorder(BorderFactory.createLineBorder(color, thickness));
        GUIUtils.freezeSize(this, getLabelWidth() + thickness * 2, getLabelHeight() + thickness * 2);
    }

    public int getCurrentReloadCount() {
        return currentReloadCount;
    }
}
