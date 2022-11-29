package net.gabor7d2.pcbuilder.gui.general;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static net.gabor7d2.pcbuilder.gui.GUIUtils.loadImageIconFromClasspath;
import static net.gabor7d2.pcbuilder.gui.GUIUtils.loadImageIconFromFile;

/**
 * Class used for asynchronously loading ImageLabels' images. The image loading tasks
 * are queued in this class, and after queuing, the queue should be processed by calling
 * {@link ImageLoader#processQueue()} when desired, which will start executing the image load tasks.
 * <p>
 * If you wish to discard the tasks that haven't finished loading images yet (because you do not need them anymore),
 * use {@link ImageLoader#discardBackgroundTasks()},
 * which will increment the Reload counter, and any tasks that were added before will stop as soon as possible,
 * and discard their results.
 */
public class ImageLoader {

    /**
     * Counts how many full reloads have been done.
     */
    private static int RELOAD_COUNT = 0;

    /**
     * List of queued ImageWorkers.
     */
    private static final List<ImageWorker> workers = new ArrayList<>();

    /**
     * Image sources.
     */
    public enum ImageSource {
        FILE,
        CLASSPATH
    }

    /**
     * ImageWorker that loads an image in the background and places it on the label.
     */
    private static class ImageWorker extends SwingWorker<Icon, Object> {

        /**
         * The label to place the loaded image on.
         */
        private final ImageLabel label;

        /**
         * The type of the image source.
         */
        private final ImageSource type;

        /**
         * The image source.
         */
        private final Object source;

        /**
         * Create a new ImageWorker.
         *
         * @param label  The label to place the loaded image on.
         * @param type   The type of the image source.
         * @param source The image source. In case of FILE it can be
         *               a String file path or a File, in case of CLASSPATH
         *               it can be a path on the classpath.
         */
        private ImageWorker(ImageLabel label, ImageSource type, Object source) {
            this.label = label;
            this.type = type;
            this.source = source;
        }

        /**
         * Load image in a background thread.
         *
         * @return The loaded icon, or null if loading failed.
         */
        @Override
        protected Icon doInBackground() {
            if (RELOAD_COUNT > label.getCurrentReloadCount()) return null;

            switch (type) {
                case FILE -> {
                    if (source instanceof File) {
                        return loadImageIconFromFile((File) source, label.getImageWidth(), label.getImageHeight());
                    }
                    if (source instanceof String) {
                        return loadImageIconFromFile(new File((String) source), label.getImageWidth(), label.getImageHeight());
                    }
                }
                case CLASSPATH -> {
                    if (source instanceof String) {
                        return loadImageIconFromClasspath((String) source, label.getImageWidth(), label.getImageHeight());
                    }
                }
            }
            return null;
        }

        /**
         * Set the loaded icon as the label's image.
         */
        @Override
        protected void done() {
            // Set icon on the UI thread
            EventQueue.invokeLater(() -> {
                try {
                    label.setIcon(get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Queues asynchronous loading of an image for the specified label.
     * The queue has to be manually processed by calling {@link ImageLoader#processQueue()}
     * when desired.
     *
     * @param label  The label to set the image of.
     * @param type   The type of the image source.
     * @param source The image source. In case of FILE it can be
     *               a String file path or a File, in case of CLASSPATH
     *               it can be a path on the classpath.
     */
    public static void queueAsyncImageLoad(ImageLabel label, ImageSource type, Object source) {
        workers.add(new ImageWorker(label, type, source));
    }

    /**
     * Starts asynchronously loading an image for the specified label.
     *
     * @param label  The label to set the image of.
     * @param type   The type of the image source.
     * @param source The image source. In case of FILE it can be
     *               a String file path or a File, in case of CLASSPATH
     *               it can be a path on the classpath.
     */
    public static void immediateAsyncImageLoad(ImageLabel label, ImageSource type, Object source) {
        new ImageWorker(label, type, source).execute();
    }

    /**
     * Starts the queued workers to load images for the labels.
     */
    public static void processQueue() {
        for (ImageWorker w : workers) {
            w.execute();
        }
    }

    /**
     * Discards all currently running background tasks, so any images that
     * didn't start loading yet gets cancelled and left as an empty label.
     * Use if the previously created labels are no longer needed.
     */
    public static void discardBackgroundTasks() {
        RELOAD_COUNT++;
    }

    /**
     * Gets current reload count.
     *
     * @return the current reload count
     */
    public static int getReloadCount() {
        return RELOAD_COUNT;
    }
}
