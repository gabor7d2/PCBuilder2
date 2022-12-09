package net.gabor7d2.pcbuilder.gui.dialog;

import net.gabor7d2.pcbuilder.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Dialog used for showing progress.
 */
public class ProgressDialog extends JDialog implements PropertyChangeListener {

    /**
     * The fixed size of ProgressDialogs.
     */
    private final static Dimension SIZE = new Dimension(384, 96);

    /**
     * The type of the progress dialog.
     */
    private final ProgressDialogType type;

    /**
     * The listener to call when the dialog is closing.
     */
    private final DialogClosingListener listener;

    /**
     * The progress bar that shows the progress.
     */
    private final JProgressBar progressBar;

    /**
     * Creates a new ProgressDialog.
     *
     * @param parentFrame The parent frame of the dialog.
     * @param type The type of the progress dialog.
     * @param title The title of the dialog.
     */
    ProgressDialog(JFrame parentFrame, ProgressDialogType type, String title) {
        super(parentFrame);
        this.type = type;

        if (type.getListener() != null) {
            this.listener = type.getListener();
        } else {
            this.listener = (e, dialog) -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            //this.listener = null;
        }

        GUIUtils.freezeSize(this, SIZE);
        setResizable(false);
        setModalityType(type.isModal() ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);

        setTitle(title);

        setDefaultCloseOperation(type.getCloseOperation());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listener != null) {
                    listener.dialogClosing(e, ProgressDialog.this);
                }
            }
        });

        JPanel contentPanel = new JPanel(new GridLayout(1, 1));
        contentPanel.setBorder(BorderFactory.createLineBorder(contentPanel.getBackground(), 24));

        progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        progressBar.setIndeterminate(type.isIndeterminate());
        contentPanel.add(progressBar, new GridBagConstraints());
        setContentPane(contentPanel);

        setLocationRelativeTo(null);
    }

    /**
     * Get the progress bar that shows the progress.
     *
     * @return the progress bar that shows the progress.
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * Sets the ProgressBar of this dialog to the specified percent
     *
     * @param percent The percent, between 0 and 100
     */
    public void setProgress(int percent) {
        progressBar.setValue(percent);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (progressBar.isIndeterminate()) return;
        if (evt.getPropertyName().equals("progress")) {
            setProgress((Integer) evt.getNewValue());
        }
    }

    public interface DialogClosingListener {

        void dialogClosing(WindowEvent e, ProgressDialog dialog);
    }
}
