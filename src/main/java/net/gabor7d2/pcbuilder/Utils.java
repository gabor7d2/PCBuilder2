package net.gabor7d2.pcbuilder;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;

public class Utils {

    /**
     * Copies the specified file from the classpath (from inside the JAR file if
     * running a JAR file) to the specified destination directory.
     *
     * @param filePathInClasspath The file's path inside the current classpath.
     * @param destDir The destination directory for the file.
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
            try(BufferedInputStream bis = new BufferedInputStream(is);
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
