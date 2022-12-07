package net.gabor7d2.pcbuilder.gui.event;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventBus {

    // The EventBus singleton instance.
    private final static EventBus INSTANCE = new EventBus();

    /**
     * PCBuilderEventListeners list.
     */
    private final List<PCBuilderEventListener> pcBuilderEventListeners = new ArrayList<>();

    /**
     * ProfileEventListeners list.
     */
    private final List<ProfileEventListener> profileEventListeners = new ArrayList<>();

    /**
     * CategoryEventListeners list.
     */
    private final List<CategoryEventListener> categoryEventListeners = new ArrayList<>();

    /**
     * ComponentEventListeners list.
     */
    private final List<ComponentEventListener> componentEventListeners = new ArrayList<>();

    private EventBus() {
    }

    /**
     * Subscribe to generic PCBuilderEvents with the specified listener implementation.
     * <p>
     * The listener will be called when any PCBuilderEvent is received by the EventBus.
     *
     * @param listener The listener implementation to subscribe.
     */
    public void subscribeToPCBuilderEvents(PCBuilderEventListener listener) {
        pcBuilderEventListeners.add(listener);
    }

    /**
     * Subscribe to ProfileEvents with the specified listener implementation.
     * <p>
     * The listener will be called when any ProfileEvent is received by the EventBus.
     *
     * @param listener The listener implementation to subscribe.
     */
    public void subscribeToProfileEvents(ProfileEventListener listener) {
        profileEventListeners.add(listener);
    }

    /**
     * Subscribe to CategoryEvents with the specified listener implementation.
     * <p>
     * The listener will be called when any CategoryEvent is received by the EventBus.
     *
     * @param listener The listener implementation to subscribe.
     */
    public void subscribeToCategoryEvents(CategoryEventListener listener) {
        categoryEventListeners.add(listener);
    }

    /**
     * Subscribe to ComponentEvents with the specified listener implementation.
     * <p>
     * The listener will be called when any ComponentEvent is received by the EventBus.
     *
     * @param listener The listener implementation to subscribe.
     */
    public void subscribeToComponentEvents(ComponentEventListener listener) {
        componentEventListeners.add(listener);
    }

    /**
     * Posts an event that gets dispatched to the event listeners subscribed to it.
     *
     * @param e The event to dispatch.
     */
    public void postEvent(PCBuilderEvent e) {
        // Makes sure that the listeners get called on the UI thread
        EventQueue.invokeLater(() -> {
            pcBuilderEventListeners.forEach(l -> l.processPCBuilderEvent(e));

            if (e instanceof ProfileEvent) {
                profileEventListeners.forEach(l -> l.processProfileEvent((ProfileEvent) e));
            }
            if (e instanceof CategoryEvent) {
                categoryEventListeners.forEach(l -> l.processCategoryEvent((CategoryEvent) e));
            }
            if (e instanceof ComponentEvent) {
                componentEventListeners.forEach(l -> l.processComponentEvent((ComponentEvent) e));
            }
        });
    }

    /**
     * Gets the EventBus singleton.
     *
     * @return the EventBus singleton
     */
    public static EventBus getInstance() {
        return INSTANCE;
    }
}
