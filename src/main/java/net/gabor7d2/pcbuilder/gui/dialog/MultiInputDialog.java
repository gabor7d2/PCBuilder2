package net.gabor7d2.pcbuilder.gui.dialog;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MultiInputDialog extends JDialog {

    private final List<JTextField> inputFields = new ArrayList<>();

    private final JPanel inputFieldsPanel = new JPanel();

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

    public void addInputField(String name) {
        JLabel nameLabel = new JLabel(name);
        inputFieldsPanel.add(nameLabel);

        JTextField field = new JTextField(30);
        //field.setMinimumSize(new Dimension(120, 30));
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
