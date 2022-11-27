package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.gui.event.ProfileEventListener;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.model.Settings;
import net.gabor7d2.pcbuilder.repositoryimpl.RepositoryFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame implements ProfileEventListener {

    private final static String TITLE = "PC Builder";
    private final static int WIDTH = 900;
    private final static int HEIGHT = 720;
    private final static int MIN_WIDTH = 750;
    private final static int MIN_HEIGHT = 360;

    private final CategorySelectorBar categorySelectorBar;

    private final ControlBar controlBar;

    private final MainPanel mainPanel;

    public MainFrame() {
        setIconImages(Application.APP_ICONS);
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        categorySelectorBar = new CategorySelectorBar();
        categorySelectorBar.setVisible(false);
        add(categorySelectorBar, BorderLayout.NORTH);
        categorySelectorBar.addCategorySelectedListener((n, s) -> System.out.println(n + ":" + s));

        controlBar = new ControlBar(this);
        add(controlBar, BorderLayout.SOUTH);

        List<Profile> profiles = RepositoryFactory.getProfileRepository().loadProfiles();

        mainPanel = new MainPanel();
        add(mainPanel, BorderLayout.CENTER);

        profiles.forEach(p -> {
            ProfileEvent pe = new ProfileEvent(ProfileEvent.ProfileEventType.ADD, p);
            processProfileEvent(pe);
        });

/*        for (Component comp : profiles.get(0).getCategories().get(0).getComponents()) {
            System.out.println(comp.getBrand() + " " + comp.getModelName());
            System.out.println(comp.getImagePath());
        }*/
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        categorySelectorBar.processProfileEvent(e);
        controlBar.processProfileEvent(e);
        mainPanel.processProfileEvent(e);
        //revalidate();
    }
}
