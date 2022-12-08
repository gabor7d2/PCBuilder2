package net.gabor7d2.pcbuilder.gui.event;

import net.gabor7d2.pcbuilder.model.Component;

/**
 * Class for ComponentEvents.
 */
public class ComponentEvent extends PCBuilderEvent {

    /**
     * Possible types of a ComponentEvent.
     */
    public enum ComponentEventType {
        SELECT,
        COMPATIBILITY_UPDATE
    }

    /**
     * Stores the type of this ComponentEvent.
     */
    private final ComponentEventType type;

    /**
     * Stores the Component instance this event is about.
     */
    private final Component component;

    /**
     * Creates a new ComponentEvent.
     *
     * @param type      the type
     * @param component the Component instance the event is about
     */
    public ComponentEvent(ComponentEventType type, Component component) {
        this.type = type;
        this.component = component;
    }

    /**
     * Gets the ComponentEvent's type.
     *
     * @return the ComponentEvent's type
     */
    public ComponentEventType getType() {
        return type;
    }

    /**
     * Gets the Component instance this event is about.
     *
     * @return the Component instance this event is about
     */
    public Component getComponent() {
        return component;
    }
}
