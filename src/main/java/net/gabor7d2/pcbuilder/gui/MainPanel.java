package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.event.ProfileEvent;
import net.gabor7d2.pcbuilder.gui.event.ProfileEventListener;
import net.gabor7d2.pcbuilder.gui.general.ImageLoader;
import net.gabor7d2.pcbuilder.gui.general.ScrollPane2D;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MainPanel extends JPanel implements ProfileEventListener {

    private final CardLayout cardLayout = new CardLayout();

    private final Set<String> profileIds = new HashSet<>();

    public MainPanel() {
        setLayout(cardLayout);
        add(new WelcomePanel(), "null");
        EventBus.getInstance().subscribeToProfileEvents(this);
    }

    private void displayProfile(Profile profile) {
        if (profile == null) {
            cardLayout.show(this, "null");
            return;
        }
        if (!profileIds.contains(profile.getId())) {
            profileIds.add(profile.getId());
            ScrollPane2D profilePane = new ScrollPane2D(new Color(180, 180, 180));
            profile.getCategories().forEach(c -> profilePane.addRow(new CategoryRow(c)));
            add(profilePane, profile.getId());
        }
        cardLayout.show(this, profile.getId());
        ImageLoader.processQueue();
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.SELECT) {
            displayProfile(e.getProfile());
        }
    }

    private static class WelcomePanel extends JPanel {

        JPanel innerPanel = new JPanel();

        public WelcomePanel() {
            setLayout(new GridBagLayout());
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
            add(innerPanel);

            JLabel welcome = new JLabel("Welcome!");
            welcome.setAlignmentX(CENTER_ALIGNMENT);
            welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 26));
            innerPanel.add(welcome);

            JLabel helpText = new JLabel("Please select a profile below or import one.");
            helpText.setAlignmentX(CENTER_ALIGNMENT);
            helpText.setFont(helpText.getFont().deriveFont(Font.PLAIN, 18));
            innerPanel.add(helpText);

            /*DefaultListModel<String> model = new DefaultListModel<>();
            model.addElement("hello");
            model.addElement("world");
            JList<String> profileList = new JList<>(model);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(profileList);
            innerPanel.add(scrollPane);*/
        }
    }
}
