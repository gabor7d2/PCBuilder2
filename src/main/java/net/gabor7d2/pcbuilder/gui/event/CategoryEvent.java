package net.gabor7d2.pcbuilder.gui.event;

import net.gabor7d2.pcbuilder.model.Category;

/**
 * Class for CategoryEvents.
 */
public class CategoryEvent extends PCBuilderEvent {

    /**
     * Possible types of a CategoryEvent.
     */
    public enum CategoryEventType {
        ENABLE,
        DISABLE
    }

    /**
     * Stores the type of this CategoryEvent.
     */
    private final CategoryEventType type;

    /**
     * Stores the Category instance this event is about.
     */
    private final Category category;

    /**
     * Creates a new CategoryEvent.
     *
     * @param type     the type
     * @param category the Category instance the event is about
     */
    public CategoryEvent(CategoryEventType type, Category category) {
        this.type = type;
        this.category = category;
    }

    /**
     * Gets the CategoryEvent's type.
     *
     * @return the CategoryEvent's type
     */
    public CategoryEventType getType() {
        return type;
    }

    /**
     * Gets the Category instance this event is about.
     *
     * @return the Category instance this event is about
     */
    public Category getCategory() {
        return category;
    }
}
