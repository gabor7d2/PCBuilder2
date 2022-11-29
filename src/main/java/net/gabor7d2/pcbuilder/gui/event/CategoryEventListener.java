package net.gabor7d2.pcbuilder.gui.event;

/**
 * Interface for implementing a listener for CategoryEvents.
 * <p>
 * Subscribing to such events can be achieved by using the EventBus'
 * corresponding method with an implementation of this interface.
 */
public interface CategoryEventListener {

    /**
     * Gets called when a CategoryEvent is received on the EventBus.
     * @param e The event.
     */
    void processCategoryEvent(CategoryEvent e);
}
