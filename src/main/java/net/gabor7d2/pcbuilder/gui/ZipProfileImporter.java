package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.dialog.ProgressDialog;
import net.gabor7d2.pcbuilder.gui.dialog.ProgressDialogType;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.repository.ProgressListener;
import net.gabor7d2.pcbuilder.repositoryimpl.ImportResultCode;
import net.gabor7d2.pcbuilder.repositoryimpl.RepositoryFactory;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ZipProfileImporter implements ProgressListener<ImportResultCode, Collection<Profile>>, ProgressDialog.DialogClosingListener {

    private final File zipFile;

    private final AtomicBoolean cancellationToken = new AtomicBoolean();

    private volatile ProgressDialog progressDialog;

    private int maxProgress;

    public ZipProfileImporter(File zipFile) {
        this.zipFile = zipFile;
    }

    public void execute() {
        RepositoryFactory.getProfileImporterRepository().importFromZipFile(zipFile, this, cancellationToken);
    }

    private boolean first = true;

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
        cancellationToken.set(true);
    }
}
