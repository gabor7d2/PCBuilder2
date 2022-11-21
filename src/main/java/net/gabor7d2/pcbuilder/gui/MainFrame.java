package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final static String TITLE = "PC Builder";
    private final static int WIDTH = 900;
    private final static int HEIGHT = 720;
    private final static int MIN_WIDTH = 480;
    private final static int MIN_HEIGHT = 360;

    public MainFrame() {
        setIconImages(Application.APP_ICONS);
        setupLookAndFeel();
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        ControlBar controlBar = new ControlBar();
        add(controlBar, BorderLayout.SOUTH);

        CategorySelectorBar categorySelectorBar = new CategorySelectorBar();
        add(categorySelectorBar, BorderLayout.NORTH);
        categorySelectorBar.addCategorySelectedListener((n, s) -> System.out.println(n + ":" + s));
    }

    private void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if (info.getName().equals("Nimbus")) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ignored2) {
            }
        }
    }
}
