package net.gabor7d2.pcbuilder.gui.event;

/**
 * Interface for implementing a listener for generic PCBuilderEvents.
 * <p>
 * Subscribing to such events can be achieved by using the EventBus'
 * corresponding method with an implementation of this interface.
 */
public interface PCBuilderEventListener {

    /**
     * Gets called when any kind of PCBuilderEvent is received on the EventBus.
     *
     * @param e The event.
     */
    void processPCBuilderEvent(PCBuilderEvent e);
}
