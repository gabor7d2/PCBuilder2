package net.gabor7d2.pcbuilder;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import net.gabor7d2.pcbuilder.gui.MainFrame;
import net.gabor7d2.pcbuilder.gui.general.ImageLoader;

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
        setupLookAndFeel();
        JFrame frame = new MainFrame();
        frame.setVisible(true);
        //frame.createBufferStrategy(2);
        ImageLoader.processQueue();
    }

    private static Image getImageByPath(String pathInJar) {
        return Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(pathInJar));
    }

    private static void setupLookAndFeel() {
        FlatIntelliJLaf.setup();
        FlatLaf.setUseNativeWindowDecorations(true);
        UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
    }
}
