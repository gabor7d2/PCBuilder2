package net.gabor7d2.pcbuilder.gui.event;

import net.gabor7d2.pcbuilder.model.Profile;

/**
 * Class for ProfileEvents.
 */
public class ProfileEvent extends PCBuilderEvent {

    /**
     * Possible types of a ProfileEvent.
     */
    public enum ProfileEventType {
        ADD,
        SELECT
    }

    /**
     * Stores the type of this ProfileEvent.
     */
    private final ProfileEventType type;

    /**
     * Stores the Profile instance this event is about.
     */
    private final Profile profile;

    /**
     * Creates a new ProfileEvent.
     *
     * @param type    the type
     * @param profile the Profile instance the event is about
     */
    public ProfileEvent(ProfileEventType type, Profile profile) {
        this.type = type;
        this.profile = profile;
    }

    /**
     * Gets the ProfileEvent's type.
     *
     * @return the ProfileEvent's type
     */
    public ProfileEventType getType() {
        return type;
    }

    /**
     * Gets the Profile instance this event is about.
     *
     * @return the Profile instance this event is about
     */
    public Profile getProfile() {
        return profile;
    }
}
