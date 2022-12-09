package net.gabor7d2.pcbuilder.gui.profileimporter;

import net.gabor7d2.pcbuilder.Application;

import javax.swing.*;
import java.io.*;

import static javax.swing.JFileChooser.*;

/**
 * ProfileImporter is a class used for showing the dialogs to
 * choose which source to import a profile from, and start
 * the importing.
 */
public class ProfileImporter {

    private File previouslySelectedFolder;

    /**
     * Creates a new ProfileImporter.
     *
     * @param defaultSelectedFolder The folder that is selected by default
     *                              when no folder has been ever selected previously.
     */
    public ProfileImporter(File defaultSelectedFolder) {
        previouslySelectedFolder = defaultSelectedFolder;
    }

    /**
     * Shows the dialog where the user can start importing a profile.
     */
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

    /**
     * Asks user for a zip file, then starts importing it.
     */
    private void importFromZipFile() {
        JFileChooser chooser = new JFileChooser(previouslySelectedFolder);
        chooser.setFileSelectionMode(FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Choose a profile zip file");
        int returnValue = Application.getDialogManager().showFileChooserDialog(chooser, "Import");

        if (returnValue == APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file != null) {
                if (file.isFile() && file.getParentFile().isDirectory())
                    previouslySelectedFolder = file.getParentFile();

                ZipProfileImporter importer = new ZipProfileImporter(file);
                importer.execute();
            }
        }
    }
}
