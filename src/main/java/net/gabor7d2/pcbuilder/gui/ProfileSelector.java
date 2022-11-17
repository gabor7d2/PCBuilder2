package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.persistence.Profile;
import net.gabor7d2.pcbuilder.persistence.ProfileManager;

import javax.swing.*;

public class ProfileSelector extends JComboBox<Profile> {

    public ProfileSelector() {
        for (Profile p : ProfileManager.getProfiles()) {
            addItem(p);
        }
    }
}
