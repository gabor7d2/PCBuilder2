package net.gabor7d2.pcbuilder.gui.profileimporter;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.dialog.ProgressDialog;
import net.gabor7d2.pcbuilder.gui.dialog.ProgressDialogType;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.persistence.repository.ProgressListener;
import net.gabor7d2.pcbuilder.persistence.ImportResultCode;
import net.gabor7d2.pcbuilder.persistence.repositoryimpl.RepositoryFactory;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 * Class used for bridging the zip importing file task and the GUI
 * using a progress dialog.
 */
public class ZipProfileImporter implements ProgressListener<ImportResultCode, Collection<Profile>>, ProgressDialog.DialogClosingListener {

    /**
     * The zip file to be loaded.
     */
    private final File zipFile;

    /**
     * The cancellation token for cancelling the operation.
     */
    private final AtomicBoolean cancellationToken = new AtomicBoolean();

    /**
     * The progress dialog for displaying the progress of the operation.
     */
    private volatile ProgressDialog progressDialog;

    /**
     * Maximum progress of the operation.
     */
    private int maxProgress;

    /**
     * Creates a new ZipProfileImporter.
     *
     * @param zipFile The zip file to be imported.
     */
    public ZipProfileImporter(File zipFile) {
        this.zipFile = zipFile;
    }

    /**
     * Starts importing the zip file.
     */
    public void execute() {
        RepositoryFactory.getProfileRepository().importFromZipFile(zipFile, this, cancellationToken);
    }

    private boolean first = true;

    /**
     * Updates the progress of the progress dialog.
     *
     * @param currentProgress The current progress of the operation.
     */
    private void updateProgress(int currentProgress) {
        if (currentProgress < 1 || maxProgress < 1) return;

        if (currentProgress > maxProgress) {
            // extraction is complete, starting import
            progressDialog.setTitle("Importing...");
            progressDialog.getProgressBar().setIndeterminate(true);
            progressDialog.getProgressBar().setStringPainted(false);
        } else {
            double progress = ((double) currentProgress / maxProgress) * 100;

            if (progressDialog != null) {
                if (first) {
                    first = false;
                    progressDialog.getProgressBar().setIndeterminate(false);
                    progressDialog.getProgressBar().setStringPainted(true);
                }
                progressDialog.setProgress((int) progress);
            }
        }
    }

    @Override
    public void preparing(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    @Override
    public void starting() {
        // open progress dialog on non-UI thread (opening on UI thread doesn't work)
        progressDialog = Application.getDialogManager().showProgressDialog("Extracting...",
                new ProgressDialogType(DO_NOTHING_ON_CLOSE, true, true, this));
    }

    @Override
    public void progress(int currentProgress) {
        // update progress on UI thread
        EventQueue.invokeLater(() -> updateProgress(currentProgress));
    }

    @Override
    public void cancelling() {
        // set title to Cancelling on UI thread
        EventQueue.invokeLater(() -> {
            if (progressDialog != null) {
                progressDialog.setTitle("Cancelling...");
                progressDialog.getProgressBar().setIndeterminate(true);
                progressDialog.getProgressBar().setStringPainted(false);
            }
        });
    }

    @Override
    public void completed(ImportResultCode resultCode, Collection<Profile> result) {
        EventQueue.invokeLater(() -> {
            if (progressDialog != null) {
                progressDialog.dispose();
            }

            if (resultCode == ImportResultCode.SUCCESS) {
                result.forEach(p -> {
                    // TODO check if profile is already added
                    EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.ADD, p));
                });
                Application.getDialogManager().showInfoDialog("Import successful.");
            } else {
                Application.getDialogManager().showErrorDialog("Import failed", resultCode.getMessage());
            }
        });
    }

    @Override
    public void dialogClosing(WindowEvent e, ProgressDialog dialog) {
        // cancel operation if user closes the dialog.
        cancellationToken.set(true);
    }
}
