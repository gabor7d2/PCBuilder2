package net.gabor7d2.pcbuilder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Category {

    /**
     * Unique ID of the category, should be something like a
     * randomly generated UUID.
     */
    private String id;

    /**
     * The profile this category resides in.
     */
    @JsonIgnore
    private Profile profile;

    /**
     * The type of this category.
     */
    private String type;

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
    @JsonIgnore
    private boolean enabled = true;

    /**
     * The index (starting from 0) of the component that is currently selected on the UI.
     */
    @JsonIgnore
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
    @JsonIgnore
    private List<Component> components = new ArrayList<>();

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
