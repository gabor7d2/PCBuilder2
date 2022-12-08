package net.gabor7d2.pcbuilder.type;

import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.component.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryType {

    /**
     * List of possible CategoryTypes
     */
    private static final List<CategoryType> CATEGORY_TYPES = new ArrayList<>();

    /**
     * Map to speed up lookup of category type by name.
     */
    private static final Map<String, CategoryType> CATEGORY_TYPES_BY_NAME = new HashMap<>();

    static {
        // register possible category types
        CATEGORY_TYPES.add(new CategoryType("MB", "Motherboard", "/category_icons/mb.png", Motherboard.class));
        CATEGORY_TYPES.add(new CategoryType("CPU", null, "/category_icons/cpu.png", Cpu.class));
        CATEGORY_TYPES.add(new CategoryType("RAM", null, "/category_icons/ram.png", Ram.class));
        CATEGORY_TYPES.add(new CategoryType("GPU", "Graphics Card", "/category_icons/gpu.png", Gpu.class));
        CATEGORY_TYPES.add(new CategoryType("SSD", null, "/category_icons/ssd.png", Ssd.class));
        CATEGORY_TYPES.add(new CategoryType("HDD", null, "/category_icons/hdd.png", Hdd.class));
        CATEGORY_TYPES.add(new CategoryType("AirCooler", "Air Cooler", "/category_icons/aircooler.png", AirCooler.class));
        CATEGORY_TYPES.add(new CategoryType("WaterCooler", "Water Cooler", "/category_icons/watercooler.png", Component.class));
        CATEGORY_TYPES.add(new CategoryType("PSU", "Power Supply", "/category_icons/psu.png", Psu.class));
        CATEGORY_TYPES.add(new CategoryType("Case", null, "/category_icons/case.png", Case.class));

        CATEGORY_TYPES.forEach(t -> CATEGORY_TYPES_BY_NAME.put(t.name, t));
    }

    /**
     * Get a category type corresponding to the specified name.
     *
     * @param name The category's internal name.
     * @return The category type, or null if no category type by that name exists.
     */
    public static CategoryType getCategoryTypeFromName(String name) {
        return CATEGORY_TYPES_BY_NAME.get(name);
    }

    /**
     * Get all registered category types.
     */
    public static List<CategoryType> getCategoryTypes() {
        return CATEGORY_TYPES;
    }

    /**
     * The internal name of the category.
     */
    private final String name;

    /**
     * The display name of the category.
     */
    private final String displayName;

    /**
     * The category icon path on the classpath.
     */
    private final String iconImagePath;

    /**
     * The class type of the components of the category.
     */
    private final Class<? extends Component> componentClass;

    /**
     * Creates a new instance of CategoryType.
     *
     * @param name           The internal name of the category.
     * @param displayName    The display name of the category. If set to null,
     *                       it will be set to the internal name.
     * @param iconImagePath  The path to the icon on classpath for this category
     * @param componentClass The class type of the components of the category.
     */
    private CategoryType(String name, String displayName, String iconImagePath, Class<? extends Component> componentClass) {
        this.name = name;
        this.componentClass = componentClass;
        if (displayName == null) displayName = name;
        this.displayName = displayName;
        this.iconImagePath = iconImagePath;
    }

    /**
     * Get name of category.
     *
     * @return The internal name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Get display name of category.
     *
     * @return The display name of the category.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get category icon path on the classpath.
     *
     * @return The category icon path on the classpath.
     */
    public String getIconImagePath() {
        return iconImagePath;
    }

    /**
     * Gets the class type of the components of the category.
     *
     * @return The class type of the components of the category.
     */
    public Class<? extends Component> getComponentClass() {
        return componentClass;
    }
}
