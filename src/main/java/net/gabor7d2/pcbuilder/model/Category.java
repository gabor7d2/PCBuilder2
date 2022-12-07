package net.gabor7d2.pcbuilder.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class Category {

    /**
     * Unique ID of the category, should be something like a
     * randomly generated UUID.
     */
    private String id;

    /**
     * The profile this category resides in.
     */
    private Profile profile;

    /**
     * The type of this category.
     */
    private CategoryType type;

    /**
     * Whether the category should be enabled by default when displaying the profile.
     */
    private boolean enabledByDefault = true;

    /**
     * The index (starting from 0) of the component that should be selected by default.
     * If out of bounds, no component will be selected.
     */
    private int defaultSelection = -1;

    /**
     * Whether the category is currently enabled and visible on the UI.
     */
    private boolean enabled = true;

    /**
     * The index (starting from 0) of the component that is currently selected on the UI.
     */
    private int selection = -1;

    /**
     * The price site url.
     */
    @NotNull
    private String priceSiteUrl = "";

    /**
     * The components in this category.
     */
    @NotNull
    private List<Component> components = new ArrayList<>();

    @Override
    public String toString() {
        return type.getDisplayName();
    }

    /**
     * @return The short name of this category.
     */
    public String getShortName() {
        return type.getName();
    }

    /**
     * @return The display name of this category.
     */
    public String getDisplayName() {
        return type.getDisplayName();
    }

    /**
     * @return The icon image path of this category.
     */
    public String getIconImagePath() {
        return type.getIconImagePath();
    }

    /**
     * Set the type of the category by its internal type name.
     * If no type exists by that name, the type will be set to null.
     *
     * @param typeName The internal type name.
     */
    public void setType(String typeName) {
        this.type = CategoryType.getCategoryTypeFromName(typeName);
    }

    /**
     * Sets whether this category is enabled and visible by default when opening
     * the profile.
     * Also sets currently enabled to the specified value.
     *
     * @param enabledByDefault Whether the category should be enabled by default.
     */
    public void setEnabledByDefault(boolean enabledByDefault) {
        this.enabledByDefault = enabledByDefault;
        this.enabled = enabledByDefault;
    }

    /**
     * Sets the default selection index to the specified value.
     * Setting this also sets the currently selected index.
     *
     * @param defaultSelection The index to set to.
     */
    public void setDefaultSelection(int defaultSelection) {
        this.defaultSelection = defaultSelection;
        this.selection = defaultSelection;
    }
}
