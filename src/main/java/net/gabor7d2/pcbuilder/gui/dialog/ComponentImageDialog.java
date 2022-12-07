package net.gabor7d2.pcbuilder.gui.dialog;

import net.gabor7d2.pcbuilder.gui.general.ImageLabel;
import net.gabor7d2.pcbuilder.model.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import static net.gabor7d2.pcbuilder.Application.APP_ICONS;

public class ComponentImageDialog extends JDialog implements KeyListener {

    private final static int DIALOG_WIDTH = 512;

    private final static int DIALOG_HEIGHT = 512;

    private Component component;

    private final ImageLabel imageLabel = new ImageLabel();

    ComponentImageDialog(Component component) {
        setResizable(false);
        setBackground(Color.WHITE);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImages(APP_ICONS);
        addKeyListener(this);

        imageLabel.addKeyListener(this);
        imageLabel.setBackground(Color.WHITE);
        setContentPane(imageLabel);

        displayComponent(component);

        pack();
        setLocationRelativeTo(null);
    }

    private void displayComponent(Component c) {
        component = c;

        setTitle("Image: " + c.getBrand() + " " + c.getModelName());
        imageLabel.setImageFromFile(c.getImagePath(), DIALOG_WIDTH, DIALOG_HEIGHT);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int compIndex = component.getCategory().getComponents().indexOf(component);

        switch (e.getKeyCode()) {
            // Show the previous component
            case KeyEvent.VK_B:
            case KeyEvent.VK_LEFT:
                if (compIndex > 0) {
                    compIndex--;
                    displayComponent(component.getCategory().getComponents().get(compIndex));
                }
                break;
            // Show the next component
            case KeyEvent.VK_N:
            case KeyEvent.VK_RIGHT:
                if (compIndex < component.getCategory().getComponents().size() - 1) {
                    compIndex++;
                    displayComponent(component.getCategory().getComponents().get(compIndex));
                }
                break;
            case KeyEvent.VK_ESCAPE:
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
