package net.gabor7d2.pcbuilderold.gui;

import net.gabor7d2.pcbuilderold.persistence.Profile;
import net.gabor7d2.pcbuilderold.persistence.ProfileManager;

import javax.swing.*;

public class ProfileSelector extends JComboBox<Profile> {

    public ProfileSelector() {
        for (Profile p : ProfileManager.getProfiles()) {
            addItem(p);
        }
    }
}
