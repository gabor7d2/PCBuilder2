package net.gabor7d2.pcbuilder.gui.dialog;

import net.gabor7d2.pcbuilder.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProgressDialog extends JDialog implements WindowListener, PropertyChangeListener {

    private final static Dimension SIZE = new Dimension(384, 96);

    private final ProgressDialogType type;
    private final DialogClosingListener listener;

    private final JProgressBar progressBar;

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
        addWindowListener(this);

        JPanel contentPanel = new JPanel(new GridLayout(1, 1));
        contentPanel.setBorder(BorderFactory.createLineBorder(contentPanel.getBackground(), 24));

        progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        progressBar.setIndeterminate(type.isIndeterminate());
        contentPanel.add(progressBar, new GridBagConstraints());
        setContentPane(contentPanel);

        setLocationRelativeTo(null);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (listener != null) {
            listener.dialogClosing(e, this);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

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
