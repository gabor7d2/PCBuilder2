package net.gabor7d2.pcbuilder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import net.gabor7d2.pcbuilder.type.ComponentPropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Component {

    /**
     * Unique ID of the component, should be something like a
     * randomly generated UUID.
     */
    private String id;

    /**
     * The category this component is in.
     */
    @JsonIgnore
    private Category category;

    /**
     * The brand.
     */
    @NotNull
    private String brand = "";

    /**
     * The model name.
     */
    @NotNull
    private String modelName = "";

    /**
     * The image path for the component's image.
     */
    @NotNull
    @JsonIgnore
    private String imagePath = "";

    /**
     * The product site url.
     */
    @NotNull
    private String productSiteUrl = "";

    /**
     * The price site url.
     */
    @NotNull
    private String priceSiteUrl = "";

    /**
     * The price of the component.
     */
    private Price price;

    /**
     * Whether this component is incompatible with any other selected component.
     */
    @JsonIgnore
    private boolean incompatible;

    /**
     * Gets the image path.
     *
     * @return The image path.
     */
    @JsonIgnore
    public @NotNull String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the image path.
     * @param imagePath The image path to set.
     */
    @JsonProperty
    public void setImagePath(@NotNull String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * The properties of this component.
     */
    private List<Property> properties = new ArrayList<>();

    /**
     * Checks whether this component is compatible with the other
     * specified component.
     * <p>
     * Subclasses should override this method to specify compatibility
     * checking, this method always returns true by default.
     *
     * @param other The other component.
     * @return Whether they are compatible.
     */
    public boolean compatibleWith(Component other) {
        return true;
    }

    /**
     * Gets a list of strings that contain the properties to be displayed of
     * the component.
     *
     * @return a list of strings that contain the properties of the component.
     */
    @JsonIgnore
    public List<String> getPropertiesDisplay() {
        List<String> strings = new ArrayList<>();
        properties.forEach(p -> strings.add(getPropertyDisplay(p)));
        return strings;
    }

    /**
     * Gets the property by its key.
     *
     * @param key The key of the property.
     * @return The property, or null if no property with
     * such key exists on this component.
     */
    public Property getProperty(String key) {
        List<Property> props = properties.stream().filter(p -> p.getKey().equals(key)).toList();
        if (props.size() != 1) return null;
        return props.get(0);
    }

    /**
     * Gets the display string (property name + property value + unit)
     * of the specified property.
     *
     * @param property The property.
     * @return The display string, or an empty string if property is null.
     */
    public String getPropertyDisplay(Property property) {
        ComponentPropertyType pType = ComponentPropertyType.getComponentPropertyTypeFromName(property.getKey());

        if (pType == null) {
            return property.getKey() + ": " + property.getValue();
        } else {
            return pType.getDisplayName() + ": " + property.getValue() + (pType.getUnitDisplayName().isEmpty() ? "" : " " + pType.getUnitDisplayName());
        }
    }

    /**
     * Gets the display string (property name + property value + unit)
     * of the specified property.
     *
     * @param key The key of the property.
     * @return The display string, or an empty string if no property with
     * such key exists on this component.
     */
    public String getPropertyDisplay(String key) {
        return getPropertyDisplay(getProperty(key));
    }

    /**
     * Gets the formatted value (value + unit) of the specified property.
     *
     * @param property The key of the property.
     * @return The display value, or an empty string if property is null.
     */
    public String getPropertyValueDisplay(Property property) {
        if (property == null) return "";

        ComponentPropertyType pType = ComponentPropertyType.getComponentPropertyTypeFromName(property.getKey());
        if (pType == null) {
            return property.getValue();
        } else {
            return property.getValue() + (pType.getUnitDisplayName().isEmpty() ? "" : " " + pType.getUnitDisplayName());
        }
    }

    /**
     * Gets the formatted value (value + unit) of the specified
     * property by its key.
     *
     * @param key The key of the property.
     * @return The value, or an empty string if no property with
     * such key exists on this component.
     */
    public String getPropertyValueDisplay(String key) {
        return getPropertyValueDisplay(getProperty(key));
    }
}
