package net.gabor7d2.pcbuilder.gui.event;

/**
 * Interface for implementing a listener for ComponentEvents.
 * <p>
 * Subscribing to such events can be achieved by using the EventBus'
 * corresponding method with an implementation of this interface.
 */
public interface ComponentEventListener {

    /**
     * Gets called when a ComponentEvent is received on the EventBus.
     *
     * @param e The event.
     */
    void processComponentEvent(ComponentEvent e);
}