package net.gabor7d2.pcbuilder.gui.dialog;

import lombok.Getter;

@Getter
public class ProgressDialogType {

    /**
     * The operation to do when the dialog is closed.
     */
    private final int closeOperation;

    /**
     * Whether the progress is indeterminate, i.e. does not have a
     * precalculated max progress amount.
     */
    private final boolean indeterminate;

    /**
     * Whether the progress dialog should be in front of the window and block focusing the window.
     */
    private final boolean modal;

    /**
     * The listener to call when the dialog is closing.
     */
    private final ProgressDialog.DialogClosingListener listener;

    /**
     * Creates a new ProgressDialogType.
     *
     * @param closeOperation  The operation to do when the dialog is closed.
     * @param indeterminate   Whether the progress is indeterminate, i.e. does not have a
     *                        precalculated max progress amount.
     * @param modal           Whether the progress dialog should be in front of the window and block focusing the window.
     * @param closingListener The listener to call when the dialog is closing.
     */
    public ProgressDialogType(int closeOperation, boolean indeterminate, boolean modal, ProgressDialog.DialogClosingListener closingListener) {
        this.closeOperation = closeOperation;
        this.indeterminate = indeterminate;
        this.modal = modal;
        this.listener = closingListener;
    }
}
