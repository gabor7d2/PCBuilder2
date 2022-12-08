package net.gabor7d2.pcbuilder.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentPropertyType {

    /**
     * List of possible ComponentPropertyTypes
     */
    private static final List<ComponentPropertyType> PROPERTY_TYPES = new ArrayList<>();

    /**
     * Map to speed up lookup of property type by name.
     */
    private static final Map<String, ComponentPropertyType> PROPERTY_TYPES_BY_NAME = new HashMap<>();

    /*static {
        // register possible property types
        PROPERTY_TYPES.add(new ComponentPropertyType("cooler_height_mm", "Cooler height", "mm"));

        PROPERTY_TYPES.add(new ComponentPropertyType("socket", "Socket"));
        PROPERTY_TYPES.add(new ComponentPropertyType("generation", "Generation"));
        PROPERTY_TYPES.add(new ComponentPropertyType("unlocked", "Unlocked"));
        PROPERTY_TYPES.add(new ComponentPropertyType("cores", "Cores"));
        PROPERTY_TYPES.add(new ComponentPropertyType("threads", "Threads"));

        PROPERTY_TYPES.add(new ComponentPropertyType("base_frequency_mhz", "Base Frequency", "mhz", "MHz"));
        PROPERTY_TYPES.add(new ComponentPropertyType("boost_frequency_mhz", "Boost Frequency", "mhz", "MHz"));
        PROPERTY_TYPES.add(new ComponentPropertyType("vram_amount_mb", "VRAM", "mhz", "MHz"));

        PROPERTY_TYPES.forEach(t -> PROPERTY_TYPES_BY_NAME.put(t.name, t));
    }*/

    /**
     * Registers a component property type if it is not yet registered.
     *
     * @param type The type to register.
     */
    public static void registerComponentPropertyType(ComponentPropertyType type) {
        if (PROPERTY_TYPES_BY_NAME.containsKey(type.getName())) return;
        PROPERTY_TYPES.add(type);
        PROPERTY_TYPES_BY_NAME.put(type.getName(), type);
    }

    /**
     * Get a component property type corresponding to the specified name.
     *
     * @param name The property's internal name.
     * @return The property type, or null if no property type by that name exists.
     */
    public static ComponentPropertyType getComponentPropertyTypeFromName(String name) {
        return PROPERTY_TYPES_BY_NAME.get(name);
    }

    /**
     * Get all registered component property types.
     */
    public static List<ComponentPropertyType> getComponentPropertyTypes() {
        return PROPERTY_TYPES;
    }

    /**
     * The internal name (key) of the property.
     */
    private final String name;

    /**
     * The display name of the property.
     */
    private final String displayName;

    /**
     * The measurement unit of the property.
     */
    private final String unit;

    /**
     * The display name of the unit.
     */
    private final String unitDisplayName;

    /**
     * Creates a new instance of ComponentPropertyType.
     *
     * @param name            The internal name (key) of the property.
     * @param displayName     The display name of the property.
     * @param unit            The measurement unit of the property.
     * @param unitDisplayName The display name of the unit. If set to null,
     *                        it will be set to the value in unit.
     */
    public ComponentPropertyType(String name, String displayName, String unit, String unitDisplayName) {
        this.name = name;
        this.displayName = displayName;
        this.unit = unit;
        if (unitDisplayName == null) unitDisplayName = unit;
        this.unitDisplayName = unitDisplayName;
    }

    public ComponentPropertyType(String name, String displayName, String unit) {
        this(name, displayName, unit, null);
    }

    public ComponentPropertyType(String name, String displayName) {
        this(name, displayName, "", null);
    }

    /**
     * Gets the internal name (key) of the property.
     *
     * @return the internal name (key) of the property.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the display name of the property.
     *
     * @return the display name of the property.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the measurement unit of the property.
     *
     * @return the unit of the property.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Gets the display name of the property's unit.
     *
     * @return the display name of the property's unit.
     */
    public String getUnitDisplayName() {
        return unitDisplayName;
    }
}
