package net.gabor7d2.pcbuilder.gui.dialog;

public class ProgressDialogType {

    private final int closeOperation;
    private final boolean indeterminate;
    private final boolean modal;
    private final ProgressDialog.DialogClosingListener listener;

    public ProgressDialogType(int closeOperation, boolean indeterminate, boolean modal, ProgressDialog.DialogClosingListener closingListener) {
        this.closeOperation = closeOperation;
        this.indeterminate = indeterminate;
        this.modal = modal;
        this.listener = closingListener;
    }

    public int getCloseOperation() {
        return closeOperation;
    }

    public boolean isIndeterminate() {
        return indeterminate;
    }

    public boolean isModal() {
        return modal;
    }

    public ProgressDialog.DialogClosingListener getListener() {
        return listener;
    }
}
