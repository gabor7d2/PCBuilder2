package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.repositoryimpl.ImportResultCode;

import javax.swing.*;
import java.io.*;
import java.util.Collection;

import static javax.swing.JFileChooser.*;

/**
 * ProfileImporter is a class used for showing the dialogs to
 * choose which source to import a profile from, and start
 * the importing.
 */
public class ProfileImporter {

    private File previouslySelectedFolder;

    private ImportCompleteListener listener;

    /**
     * Creates a new ProfileImporter.
     *
     * @param defaultSelectedFolder The folder that is selected by default
     *                              when no folder has been ever selected previously.
     */
    public ProfileImporter(File defaultSelectedFolder) {
        previouslySelectedFolder = defaultSelectedFolder;
    }

    public void setImportCompleteListener(ImportCompleteListener l) {
        listener = l;
    }

    public void showDialog() {
        String[] dialogOptions = new String[]{"URL", "Zip file", "Folder"};

        int chosenOption = Application.getDialogManager().showOptionDialog("Add Profile",
                "Choose the data source of the profile to be loaded:", dialogOptions);

        if (chosenOption == 0 || chosenOption == 2) {
            Application.getDialogManager().showInfoDialog("Not available", "This feature is not yet available.");
        } else if (chosenOption == 1) {
            importFromZipFile();
        }
    }

    private void importFromUrl() {
        String url = Application.getDialogManager().showInputDialog("Specify URL",
                """
                        Please specify a URL pointing to a Zip archive:

                        (Note that the progress bar might not show any
                        progress being made if the url doesn't support it)""");

        if (url != null) {
            // TODO url import
        }
    }

    private void importFromZipFile() {
        JFileChooser chooser = new JFileChooser(previouslySelectedFolder);
        chooser.setFileSelectionMode(FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Choose a profile zip file");
        int returnValue = Application.getDialogManager().showFileChooserDialog(chooser, "Import");

        if (returnValue == APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file != null) {
                if (file.isFile() && file.getParentFile().isDirectory()) previouslySelectedFolder = file.getParentFile();

                ZipProfileImporter importer = new ZipProfileImporter(file);
                importer.execute();
            }
        }
    }

    private void importFromFolder() {
        JFileChooser chooser = new JFileChooser(previouslySelectedFolder);
        chooser.setFileSelectionMode(DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Choose a profile folder");
        int returnValue = Application.getDialogManager().showFileChooserDialog(chooser, "Import");

        if (returnValue == APPROVE_OPTION) {
            File directory = chooser.getSelectedFile();
            if (directory != null) {
                if (directory.isDirectory()) previouslySelectedFolder = directory;
                // TODO folder import
            }
        }
    }

    public interface ImportCompleteListener {
        void importComplete(ImportResultCode resultCode, Collection<Profile> profiles);
    }
}
