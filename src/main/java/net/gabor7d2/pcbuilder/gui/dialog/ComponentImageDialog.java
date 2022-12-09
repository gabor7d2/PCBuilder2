package net.gabor7d2.pcbuilder.gui.dialog;

import net.gabor7d2.pcbuilder.gui.general.ImageLabel;
import net.gabor7d2.pcbuilder.model.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import static net.gabor7d2.pcbuilder.Application.APP_ICONS;

/**
 * A dialog that displays the specified components' image enlarged.
 * The user can press the left or right arrows to navigate between components
 * in the same category row.
 */
public class ComponentImageDialog extends JDialog implements KeyListener {

    /**
     * The fixed width of the dialog.
     */
    private final static int DIALOG_WIDTH = 512;

    /**
     * The fixed height of the dialog.
     */
    private final static int DIALOG_HEIGHT = 512;

    /**
     * The component that's currently being displayed.
     */
    private Component component;

    /**
     * The image label that contains the components' image.
     */
    private final ImageLabel imageLabel = new ImageLabel();

    /**
     * Creates a new ComponentImageDialog.
     *
     * @param component The component that should be displayed.
     */
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

    /**
     * Displays the given component.
     *
     * @param c The component to display.
     */
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
                    // jump to the previous image in the category
                    compIndex--;
                    displayComponent(component.getCategory().getComponents().get(compIndex));
                }
                break;
            // Show the next component
            case KeyEvent.VK_N:
            case KeyEvent.VK_RIGHT:
                if (compIndex < component.getCategory().getComponents().size() - 1) {
                    // jump to the next image in the category
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
