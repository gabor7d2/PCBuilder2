package net.gabor7d2.pcbuilder;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;

/**
 * Class containing common utilities.
 */
public class Utils {

    /**
     * Copies the specified file from the classpath (from inside the JAR file if
     * running a JAR file) to the specified destination directory.
     *
     * @param filePathInClasspath The file's path inside the current classpath.
     * @param destDir             The destination directory for the file.
     * @return The file instance that points to the newly created file. The copy can
     * fail if the destDir doesn't exist, or if the file couldn't be found on the classpath,
     * or some other file access error occurs, in these cases it returns null.
     */
    public static File copyFileFromClasspath(String filePathInClasspath, File destDir) {
        if (!destDir.isDirectory()) return null;

        filePathInClasspath = filePathInClasspath.replace('\\', '/');
        File destFile = new File(destDir, new File(filePathInClasspath).getName());
        System.out.println(destFile.getAbsolutePath());

        try (InputStream is = Utils.class.getResourceAsStream(filePathInClasspath)) {
            if (is == null) return null;
            try (BufferedInputStream bis = new BufferedInputStream(is);
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile))) {
                while (bis.available() > 0) {
                    bos.write(bis.readNBytes(32768));
                }
                return destFile;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        destFile.delete();
        return null;
    }

    /**
     * Deletes the specified file, if the file is a normal file or if the file is a directory.
     * If deleting fails for some reason, some files might stay undeleted.
     *
     * @param file The normal file or directory to delete
     */
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) deleteDirectory(file);
    }

    /**
     * Deletes a directory and all of its subdirectories, including all files contained in them.
     * If deleting fails for some reason, some files might stay undeleted.
     *
     * @param directory The directory to delete
     */
    private static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files == null) return;
            for (File file : files) {
                if (file.isDirectory()) deleteDirectory(file);
                else file.delete();
            }
            directory.delete();
        }
    }

    /**
     * Opens the specified address in the default browser
     *
     * @param uri The uri which should be opened
     * @return True if the website was successfully opened or
     * false if the address is null or empty, or if an error occurred
     */
    public static boolean openWebsite(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Opens the specified address in the default browser
     *
     * @param url The url which should be opened
     * @return True if the website was successfully opened or
     * false if the address is null or empty, or if an error occurred
     */
    public static boolean openWebsite(URL url) {
        try {
            return openWebsite(url.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Opens the specified address in the default browser
     *
     * @param address The website's address which should be opened
     * @return True if the website was successfully opened or
     * false if the address is null or empty, or if an error occurred
     */
    public static boolean openWebsite(String address) {
        if (address == null || address.isEmpty()) return false;
        try {
            return openWebsite(new URI(address));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
