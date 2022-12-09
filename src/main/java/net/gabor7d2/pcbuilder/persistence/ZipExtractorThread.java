package net.gabor7d2.pcbuilder.persistence;

import net.gabor7d2.pcbuilder.Utils;
import net.gabor7d2.pcbuilder.persistence.repository.ProgressListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Thread for extracting a zip file in the background.
 */
public class ZipExtractorThread extends Thread {

    /**
     * The destination directory for the zip's contents.
     */
    private final File destDir;

    /**
     * The zip file to extract.
     */
    private final File zipFile;

    /**
     * The progress listener to post progress updates to.
     */
    private final ProgressListener<ImportResultCode, Collection<File>> progressListener;

    /**
     * The cancellation token, that if set to true, will cancel the operation
     * as soon as possible.
     */
    private final AtomicBoolean cancelled;

    /**
     * The progress that has already been done.
     */
    private final AtomicLong progressInBytes = new AtomicLong();

    /**
     * Creates a new zip extractor thread.
     *
     * @param destDir           The destination directory for the zip's contents.
     * @param zipFile           The zip file to extract.
     * @param progressListener  The progress listener to post progress updates to.
     * @param cancellationToken The cancellation token, that if set to true, will cancel the operation
     *                          as soon as possible.
     */
    public ZipExtractorThread(File destDir, File zipFile,
                              ProgressListener<ImportResultCode, Collection<File>> progressListener,
                              AtomicBoolean cancellationToken) {
        this.destDir = destDir;
        this.zipFile = zipFile;
        this.progressListener = progressListener;
        this.cancelled = cancellationToken;
        progressListener.preparing((int) zipFile.length());
    }

    @Override
    public void run() {
        progressListener.starting();
        // TODO ask user if they want to overwrite existing files
        extractZipFile(destDir, zipFile, true);
    }

    /**
     * Starts a zip extraction with the specified parameters
     * <br>
     * Extracts a zip file from the specified path to inside the specified directory path
     *
     * @param destDir   The folder to extract the zip's contents into
     * @param zipFile   The zip file to be extracted
     * @param overwrite Specifies if the program should overwrite all files and folders on disk that are extracted from the zip file
     */
    public void extractZipFile(File destDir, File zipFile, boolean overwrite) {
        try {
            extractZip(destDir, new FileInputStream(zipFile), overwrite);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            progressListener.completed(ImportResultCode.FILE_DOESNT_EXIST, null);
        }
    }

    /**
     * Extracts the specified zip input stream to the specified destination directory.
     *
     * @param destDir                  The directory to extract to.
     * @param zipFileInputStream       The input stream.
     * @param overwriteWithZipContents Whether to overwrite contents in the destDir or
     *                                 leave them as is.
     */
    private void extractZip(File destDir, InputStream zipFileInputStream, boolean overwriteWithZipContents) {
        // Create dest dir if it doesn't exist
        if (!destDir.exists()) destDir.mkdirs();

        if (!destDir.isDirectory()) {
            progressListener.completed(ImportResultCode.COULD_NOT_EXTRACT_ZIP, null);
            return;
        }

        try (BufferedInputStream bis = new BufferedInputStream(zipFileInputStream);
             ZipInputStream zis = new ZipInputStream(bis)) {

            if (!isZipArchive(bis)) {
                progressListener.completed(ImportResultCode.NOT_A_ZIP_ARCHIVE, null);
                return;
            }

            List<File> extractedFiles = new ArrayList<>();
            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {
                if (cancelled.get()) {
                    progressListener.cancelling();
                    zis.closeEntry();
                    zis.close();
                    for (File file : extractedFiles) {
                        Utils.delete(file);
                    }
                    progressListener.completed(ImportResultCode.CANCELLED, null);
                    return;
                }

                String zipEntryName = ze.getName();
                File currentFile = new File(destDir, zipEntryName);

                // Skip file if it is a __MACOSX folder or a file in a __MACOSX folder or if it is a .DS_Store file
                if (currentFile.getPath().contains("__MACOSX") || currentFile.getPath().contains(".DS_Store")) {
                    zis.closeEntry();
                    continue;
                }

                // Add the current file to the list that will be returned
                extractedFiles.add(currentFile);

                // Delete the current file first if overwriteWithZipContents is enabled
                if (overwriteWithZipContents) Utils.delete(currentFile);

                // Create a directory if this zip entry is a directory or write the file to disk if it is not
                if (ze.isDirectory()) {
                    if (currentFile.exists()) {
                        zis.closeEntry();
                        zis.close();
                        progressListener.completed(ImportResultCode.COULD_NOT_EXTRACT_ZIP, null);
                        return;
                    } else currentFile.mkdirs();
                } else {
                    if (currentFile.exists()) {
                        zis.closeEntry();
                        zis.close();
                        progressListener.completed(ImportResultCode.COULD_NOT_EXTRACT_ZIP, null);
                        return;
                    } else {
                        FileOutputStream fos = new FileOutputStream(currentFile);
                        copyStream(zis, fos);
                        fos.close();
                    }
                }
                zis.closeEntry();
            }

            zis.closeEntry();

            progressListener.completed(ImportResultCode.SUCCESS, extractedFiles);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressListener.completed(ImportResultCode.COULD_NOT_EXTRACT_ZIP, null);
    }

    /**
     * Copies an input stream to an output stream with a buffer size
     * of 4096 bytes while updating the current progress
     *
     * @param in  The input stream
     * @param out The output stream
     * @throws IOException If an I/O error occurs
     */
    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            if (cancelled.get()) {
                return;
            }

            out.write(buffer, 0, length);
            progressInBytes.set(progressInBytes.get() + length);
            progressListener.progress((int) progressInBytes.get());
        }
    }

    /**
     * Checks whether an input stream is a zip archive, independent of the file extension used
     * <br>
     * Note: This method does not close the input stream, instead it resets it so it can be
     * read again from the beginning
     *
     * @param bis The <code>BufferedInputStream</code> of a zip archive
     * @return True if the specified input stream corresponds to a zip archive, false otherwise or if an error happened
     */
    private static boolean isZipArchive(BufferedInputStream bis) {
        int fileSignature = 0;
        try {
            if (bis.available() < 4) return false;
            DataInputStream din = new DataInputStream(bis);
            bis.mark(4);
            fileSignature = din.readInt();
            bis.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileSignature == 0x504B0304 || fileSignature == 0x504B0506 || fileSignature == 0x504B0708;
    }
}
