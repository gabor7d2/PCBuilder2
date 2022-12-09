package net.gabor7d2.pcbuilder.gui.general;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class for loading images from file or from the classpath.
 * <p>
 * The loaded images are cached so that images loaded multiple
 * times will only get loaded from disk once.
 * <p></p>
 * The cache for images loaded from files can be cleared using
 * {@link ImageLoader#clearCache()}.
 * <p>
 * The cache for images loaded from the classpath can not be cleared,
 * as files on the classpath are not expected to change while the program is running.
 */
public class ImageLoader {

    /**
     * Image missing image used as a substitute when an image could not be loaded.
     */
    private final static Image IMAGE_MISSING;

    /**
     * Map to cache images loaded from files.
     * <p>
     * The cache can be cleared using {@link ImageLoader#clearCache()}
     */
    private final static Map<String, Image> filePathImageCache = new HashMap<>();

    /**
     * Map to cache images loaded from classpath.
     * <p>
     * This cache can not be cleared, as files on the classpath
     * are not expected to change while the program is running.
     */
    private final static Map<String, Image> classpathImageCache = new HashMap<>();

    /**
     * Map to cache ImageIcons scaled from Images which originate from files.
     * <p>
     * The cache can be cleared using {@link ImageLoader#clearCache()}
     */
    private final static Map<ImageIconParams, ImageIcon> filePathImageIconCache = new HashMap<>();

    /**
     * Map to cache ImageIcons scaled from Images which originate from the classpath.
     * <p>
     * This cache can not be cleared, as files on the classpath
     * are not expected to change while the program is running.
     */
    private final static Map<ImageIconParams, ImageIcon> classpathImageIconCache = new HashMap<>();

    static {
        IMAGE_MISSING = loadImageFromClasspath("/image_missing_icon_general.png");
    }

    /**
     * Loads an Image from the specified file. If anything goes wrong, returns null.
     *
     * @param file The file of the image to load.
     * @return The loaded image, or null if something went wrong.
     */
    public static Image loadImageFromFile(File file) {
        // return from cache if cached
        if (filePathImageCache.containsKey(file.getPath())) {
            return classpathImageCache.get(file.getPath());
        }

        try {
            Image image = ImageIO.read(file);
            classpathImageCache.put(file.getPath(), image);
            return image;
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

        // return from cache if cached
        if (classpathImageCache.containsKey(path)) {
            return classpathImageCache.get(path);
        }

        try (InputStream is = AsyncImageLoader.class.getResourceAsStream(path)) {
            if (is == null) return null;
            Image image = ImageIO.read(is);
            classpathImageCache.put(path, image);
            return image;
        } catch (Exception e) {
            System.out.println("Error while loading image from classpath: " + path);
        }
        return null;
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
        ImageIconParams params = new ImageIconParams(file.getPath(), width, height);
        if (filePathImageIconCache.containsKey(params)) {
            return filePathImageIconCache.get(params);
        }

        Image image = loadImageFromFile(file);
        if (image == null) image = IMAGE_MISSING;
        if (image != null) {
            ImageIcon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            filePathImageIconCache.put(params, icon);
            return icon;
        } else return null;
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
        ImageIconParams params = new ImageIconParams(path, width, height);
        if (classpathImageIconCache.containsKey(params)) {
            return classpathImageIconCache.get(params);
        }

        Image image = loadImageFromClasspath(path);
        if (image == null) image = IMAGE_MISSING;
        if (image != null) {
            ImageIcon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            classpathImageIconCache.put(params, icon);
            return icon;
        } else return null;
    }

    /**
     * Clears all images from the filepath cache.
     * <p>
     * {@link ImageLoader#loadImageFromFile(File)} calls after calling
     * this will result in them loading images again from disk, useful
     * for reloading potentially changed images on the disk.
     */
    public static void clearCache() {
        filePathImageCache.clear();
        filePathImageIconCache.clear();
    }

    /**
     * Class used as the key for image icon caches.
     */
    private record ImageIconParams(String path, int width, int height) {
    }
}
