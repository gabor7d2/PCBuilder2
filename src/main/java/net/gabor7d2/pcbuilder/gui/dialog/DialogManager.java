package net.gabor7d2.pcbuilder.gui.dialog;

import net.gabor7d2.pcbuilder.model.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class for managing when certain dialogs can be opened.
 */
public class DialogManager {

    /**
     * Keeps track of the currently open dialogs. Used to ensure that
     * only 1 dialog is being shown to the user.
     */
    private int currentlyOpenDialogs = 0;

    /**
     * The parent frame to set as the parent of opened dialogs.
     */
    private final JFrame parentFrame;

    /**
     * Creates a new DialogManager.
     *
     * @param parentFrame The parent frame to set as the parent of opened dialogs.
     */
    public DialogManager(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    /**
     * Shows an info dialog with the specified title and message
     *
     * @param title   The title of the dialog to display
     * @param message The message to display
     */
    public void showInfoDialog(String title, String message) {
        JOptionPane.showMessageDialog(parentFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an info dialog with the specified message
     *
     * @param message The message to display
     */
    public void showInfoDialog(String message) {
        showInfoDialog("", message);
    }

    /**
     * Shows an error dialog with the specified title and message
     *
     * @param title   The title of the dialog to display
     * @param message The message to display
     */
    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(parentFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an error dialog with title "Error" and with the specified message
     *
     * @param message The message to display
     */
    public void showErrorDialog(String message) {
        showErrorDialog("Error", message);
    }

    /**
     * Shows a question dialog with the specified title, message and button type
     *
     * @param title   The title of the dialog to display
     * @param message The message to display
     * @param type    Specifies what buttons should be displayed on the dialog.
     *                <br>
     *                Use one of the option types available in {@link JOptionPane}
     * @return The button's code that the user pressed, one from {@link JOptionPane}.
     */
    public int showQuestionDialog(String title, String message, int type) {
        return JOptionPane.showConfirmDialog(parentFrame, message, title, type);
    }

    /**
     * Shows a confirm dialog to the user with the specified title and message
     * where the user can either select YES or NO
     *
     * @param title   The title of the dialog to display
     * @param message The message to display
     * @return True if the user pressed the YES button
     */
    public boolean showConfirmDialog(String title, String message) {
        return JOptionPane.showConfirmDialog(parentFrame, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Shows an option dialog where the user can choose between one of the options by
     * pressing the corresponding button.
     *
     * @param title   The title of the dialog
     * @param message The message to display
     * @param options The options the user can choose from
     * @return The chosen option, or null if the dialog was cancelled.
     */
    public int showOptionDialog(String title, String message, Object[] options) {
        if (currentlyOpenDialogs != 0) return -1;
        currentlyOpenDialogs++;

        int option = JOptionPane.showOptionDialog(parentFrame, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        currentlyOpenDialogs--;
        return option;
    }

    /**
     * Shows an input dialog to the user with the specified title and message
     * that contains a single TextField where the user can enter one line of text
     *
     * @param title   The title of the dialog to display
     * @param message The message to display
     * @return The text inside the TextField if the OK button was pressed or null if the dialog was cancelled
     */
    public String showInputDialog(String title, String message) {
        if (currentlyOpenDialogs != 0) return null;
        currentlyOpenDialogs++;

        String input = JOptionPane.showInputDialog(parentFrame, message, title, JOptionPane.PLAIN_MESSAGE);

        currentlyOpenDialogs--;
        return input;
    }

    /**
     * Shows an input dialog to the user with the specified title and message
     * that contains a single ComboBox where the user can choose from the specified
     * possibilities.
     *
     * @param title         The title of the dialog.
     * @param message       The message to display.
     * @param possibilities The possibilities the user can choose from using the ComboBox.
     * @param <T>           The type of the data to display
     * @return The chosen option, or null if the dialog was cancelled.
     */
    public <T> T showInputDialog(String title, String message, T[] possibilities) {
        if (currentlyOpenDialogs != 0) return null;
        currentlyOpenDialogs++;

        T chosen = (T) JOptionPane.showInputDialog(parentFrame, message, title, JOptionPane.PLAIN_MESSAGE, null, possibilities, null);

        currentlyOpenDialogs--;
        return chosen;
    }

    /**
     * Shows a component image dialog displaying the enlarged image
     * of the specified component.
     *
     * @param c The component to display.
     */
    public void showComponentImageDialog(Component c) {
        if (currentlyOpenDialogs != 0) return;
        currentlyOpenDialogs++;

        JDialog d = new ComponentImageDialog(c);
        d.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                currentlyOpenDialogs--;
            }
        });
        // set visible but do not block execution
        EventQueue.invokeLater(() -> d.setVisible(true));
    }

    /**
     * Shows the given file chooser dialog, and returns the result of the file choose.
     *
     * @param fileChooser       The dialog to display.
     * @param approveButtonText The text of the approve button in the dialog.
     * @return The result of the file choose.
     */
    public int showFileChooserDialog(JFileChooser fileChooser, String approveButtonText) {
        if (currentlyOpenDialogs != 0) return -1;
        currentlyOpenDialogs++;

        int result = fileChooser.showDialog(parentFrame, approveButtonText);

        currentlyOpenDialogs--;
        return result;
    }

    /**
     * Shows a progress dialog with the given title and ProgressDialogType.
     *
     * @param title The title of the dialog.
     * @param type  The type of the progress dialog.
     * @return The created, visible progress dialog, for manipulating it's progress
     * and other properties.
     */
    public ProgressDialog showProgressDialog(String title, ProgressDialogType type) {
        // dont check if there are any open dialogs, progress dialog can be
        // opened on top of any dialog
        currentlyOpenDialogs++;

        ProgressDialog d = new ProgressDialog(parentFrame, type, title);
        d.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                currentlyOpenDialogs--;
            }
        });
        // set visible but do not block execution
        EventQueue.invokeLater(() -> d.setVisible(true));
        return d;
    }

    /**
     * Creates a {@link MultiInputDialog} that the user can use to fill out input fields.
     * The returned dialog should be used to set up the input fields it should contain,
     * and display the dialog using setVisible(true)
     *
     * @param inputDone The listener to call when the user pressed the OK button.
     * @return The created MultiInputDialog.
     */
    public MultiInputDialog createMultiInputDialog(MultiInputDialog.InputDone inputDone) {
        if (currentlyOpenDialogs != 0) return null;
        currentlyOpenDialogs++;

        MultiInputDialog inputDialog = new MultiInputDialog(parentFrame, inputDone);
        inputDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                currentlyOpenDialogs--;
            }
        });
        return inputDialog;
    }
}
