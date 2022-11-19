package net.gabor7d2.pcbuilder;

import net.gabor7d2.pcbuilder.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public final static List<Image> APP_ICONS = new ArrayList<>();

    static {
        APP_ICONS.add(getImageByPath("/icons/icon.png"));
        APP_ICONS.add(getImageByPath("/icons/icon-512.png"));
        APP_ICONS.add(getImageByPath("/icons/icon-256.png"));
        APP_ICONS.add(getImageByPath("/icons/icon-128.png"));
        APP_ICONS.add(getImageByPath("/icons/icon-64.png"));
        APP_ICONS.add(getImageByPath("/icons/icon-32.png"));
        APP_ICONS.add(getImageByPath("/icons/icon-16.png"));
    }

    public static void main(String[] args) {
        JFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    private static Image getImageByPath(String pathInJar) {
        return Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(pathInJar));
    }
}
