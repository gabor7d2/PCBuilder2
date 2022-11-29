package net.gabor7d2.pcbuilder.gui.event;

/**
 * Interface for implementing a listener for ProfileEvents.
 * <p>
 * Subscribing to such events can be achieved by using the EventBus'
 * corresponding method with an implementation of this interface.
 */
public interface ProfileEventListener {

    /**
     * Gets called when a ProfileEvent is received on the EventBus.
     *
     * @param e The event.
     */
    void processProfileEvent(ProfileEvent e);
}
