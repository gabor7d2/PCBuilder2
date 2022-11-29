package net.gabor7d2.pcbuilder.gui.event;

import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private final static EventBus INSTANCE = new EventBus();

    private final List<PCBuilderEventListener> pcBuilderEventListeners = new ArrayList<>();
    private final List<ProfileEventListener> profileEventListeners = new ArrayList<>();
    private final List<CategoryEventListener> categoryEventListeners = new ArrayList<>();

    private EventBus() {

    }

    public void subscribeToPCBuilderEvents(PCBuilderEventListener listener) {
        pcBuilderEventListeners.add(listener);
    }

    public void subscribeToProfileEvents(ProfileEventListener listener) {
        profileEventListeners.add(listener);
    }

    public void subscribeToCategoryEvents(CategoryEventListener listener) {
        categoryEventListeners.add(listener);
    }

    /**
     * Posts an event that gets dispatched to the corresponding event listeners.
     * @param e The event to dispatch.
     */
    public void postEvent(PCBuilderEvent e) {
        pcBuilderEventListeners.forEach(l -> l.processPCBuilderEvent(e));

        if (e instanceof ProfileEvent) {
            profileEventListeners.forEach(l -> l.processProfileEvent((ProfileEvent) e));
        }
        if (e instanceof CategoryEvent) {
            categoryEventListeners.forEach(l -> l.processCategoryEvent((CategoryEvent) e));
        }
    }

    public static EventBus getInstance() {
        return INSTANCE;
    }
}
