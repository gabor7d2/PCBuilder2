package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.general.ScrollPane2D;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.model.Settings;
import net.gabor7d2.pcbuilder.repositoryimpl.RepositoryFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final static String TITLE = "PC Builder";
    private final static int WIDTH = 900;
    private final static int HEIGHT = 720;
    private final static int MIN_WIDTH = 680;
    private final static int MIN_HEIGHT = 360;

    public MainFrame() {
        setIconImages(Application.APP_ICONS);
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        CategorySelectorBar categorySelectorBar = new CategorySelectorBar();
        add(categorySelectorBar, BorderLayout.NORTH);
        categorySelectorBar.addCategorySelectedListener((n, s) -> System.out.println(n + ":" + s));

        ControlBar controlBar = new ControlBar();
        add(controlBar, BorderLayout.SOUTH);

        ScrollPane2D panel = new ScrollPane2D(new Color(160, 160, 160));

        Settings settings = RepositoryFactory.getSettingsRepository().loadSettings();
        System.out.println(settings.getSelectedProfile());
        settings.setSelectedProfile("Testing");
        RepositoryFactory.getSettingsRepository().saveSettings(settings);

        List<Profile> profiles = RepositoryFactory.getProfileRepository().loadProfiles();
        System.out.println(profiles.get(0).getCategories().get(0).isEnabled());
/*        for (Component comp : profiles.get(0).getCategories().get(0).getComponents()) {
            System.out.println(comp.getBrand() + " " + comp.getModelName());
            System.out.println(comp.getImagePath());
        }*/

        CategoryRow crow = new CategoryRow();
        crow.displayCategory(profiles.get(0).getCategories().get(0));
        panel.addRow(crow);

        add(panel, BorderLayout.CENTER);
    }
}
