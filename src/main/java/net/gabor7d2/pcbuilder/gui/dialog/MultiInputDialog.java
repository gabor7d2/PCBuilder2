package net.gabor7d2.pcbuilder.gui.dialog;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog that can be used to request multiple fields of input
 * from the user.
 */
public class MultiInputDialog extends JDialog {

    /**
     * The input fields the dialog has.
     */
    private final List<JTextField> inputFields = new ArrayList<>();

    /**
     * Panel that contains the input fields.
     */
    private final JPanel inputFieldsPanel = new JPanel();

    /**
     * Creates a new MultiInputDialog.
     *
     * @param parentFrame The parent frame of the dialog.
     * @param inputDone   The listener to call when the user finished inputting data,
     *                    and pressed the accept button.
     */
    MultiInputDialog(JFrame parentFrame, InputDone inputDone) {
        super(parentFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);

        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.setBorder(BorderFactory.createMatteBorder(16, 16, 16, 16, getBackground()));
        add(innerPanel);

        inputFieldsPanel.setLayout(new GridLayout(0, 2, 4, 4));
        innerPanel.add(inputFieldsPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createMatteBorder(24, 8, 8, 8, getBackground()));
        innerPanel.add(southPanel, BorderLayout.SOUTH);

        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(e -> {
            boolean close = inputDone.inputDone(inputFields.stream().map(JTextComponent::getText).toList());
            if (close) dispose();
        });
        southPanel.add(acceptButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dispose();
        });
        southPanel.add(cancelButton);

        setLocationRelativeTo(null);
    }

    /**
     * Adds an input field to the dialog.
     *
     * @param name The name of the input to ask for,
     *             displayed to the left of the input field.
     */
    public void addInputField(String name) {
        JLabel nameLabel = new JLabel(name);
        inputFieldsPanel.add(nameLabel);

        JTextField field = new JTextField(20);
        inputFieldsPanel.add(field);

        inputFields.add(field);
    }

    public interface InputDone {

        /**
         * Called when the user has finished entering input.
         *
         * @param inputs The inputs the user entered.
         * @return True if the dialog should be closed, false if it should be kept open.
         */
        boolean inputDone(List<String> inputs);
    }
}
