package net.gabor7d2.pcbuilder.gui.event;

import net.gabor7d2.pcbuilder.model.Category;

public class CategoryEvent extends PCBuilderEvent {

    public enum CategoryEventType {
        ENABLE,
        DISABLE
    }

    private final CategoryEventType type;

    private final Category category;

    public CategoryEvent(CategoryEventType type, Category category) {
        this.type = type;
        this.category = category;
    }

    public CategoryEventType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }
}
