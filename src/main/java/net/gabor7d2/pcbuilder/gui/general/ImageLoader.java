package net.gabor7d2.pcbuilder.gui.general;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static net.gabor7d2.pcbuilder.gui.GUIUtils.loadImageIconFromClasspath;
import static net.gabor7d2.pcbuilder.gui.GUIUtils.loadImageIconFromFile;

/**
 * Class used for loading ImageLabels' images.
 */
public class ImageLoader {

    // Counts how many full reloads have been done
    private static int RELOAD_COUNT = 0;

    private static final List<ImageWorker> workers = new ArrayList<>();

    public enum ImageSource {
        FILE,
        CLASSPATH
    }

    private static class ImageWorker extends SwingWorker<Icon, Object> {

        private final ImageLabel label;
        private final ImageSource type;
        private final Object source;

        private ImageWorker(ImageLabel label, ImageSource type, Object source) {
            this.label = label;
            this.type = type;
            this.source = source;
        }

        @Override
        protected Icon doInBackground() {
            if (RELOAD_COUNT > label.getCurrentReloadCount()) return null;

            switch (type) {
                case FILE -> {
                    if (source instanceof File) {
                        return loadImageIconFromFile((File) source, label.getLabelWidth(), label.getLabelHeight());
                    }
                    if (source instanceof String) {
                        return loadImageIconFromFile(new File((String) source), label.getLabelWidth(), label.getLabelHeight());
                    }
                }
                case CLASSPATH -> {
                    if (source instanceof String) {
                        return loadImageIconFromClasspath((String) source, label.getLabelWidth(), label.getLabelHeight());
                    }
                }
            }

            return null;
        }

        @Override
        protected void done() {
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
     * @param label The label to set the image of.
     * @param type The type of the image source.
     * @param source The image source. In case of LOCAL_FILE it can be
     *               a String file path or a File, in case of CLASSPATH
     *               it can be a path on the classpath.
     */
    public static void queueAsyncImageLoad(ImageLabel label, ImageSource type, Object source) {
        workers.add(new ImageWorker(label, type, source));
    }

    /**
     * Starts asynchronously loading an image for the specified label.
     *
     * @param label The label to set the image of.
     * @param type The type of the image source.
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

    public static int getReloadCount() {
        return RELOAD_COUNT;
    }
}
