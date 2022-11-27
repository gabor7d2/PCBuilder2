package net.gabor7d2.pcbuilder.gui.event;

import net.gabor7d2.pcbuilder.model.Profile;

public class ProfileEvent {

    public enum ProfileEventType {
        ADD,
        SELECT
    }

    private final ProfileEventType type;

    private final Profile profile;

    public ProfileEvent(ProfileEventType type, Profile profile) {
        this.type = type;
        this.profile = profile;
    }

    public ProfileEventType getType() {
        return type;
    }

    public Profile getProfile() {
        return profile;
    }
}
