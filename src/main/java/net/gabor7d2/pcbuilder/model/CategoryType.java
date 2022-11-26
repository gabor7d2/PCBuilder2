package net.gabor7d2.pcbuilder.model;

import java.util.HashMap;
import java.util.Map;

public class CategoryType {
    private static final Map<String, CategoryType> CATEGORY_TYPES = new HashMap<>();

    static {
        CATEGORY_TYPES.put("MB", new CategoryType("MB", "Motherboard", "/category_icons/mb.png"));
        CATEGORY_TYPES.put("CPU", new CategoryType("CPU", null, "/category_icons/cpu.png"));
        CATEGORY_TYPES.put("RAM", new CategoryType("RAM", null, "/category_icons/ram.png"));
        CATEGORY_TYPES.put("GPU", new CategoryType("GPU", "Graphics Card", "/category_icons/gpu.png"));
        CATEGORY_TYPES.put("SSD", new CategoryType("SSD", null, "/category_icons/ssd.png"));
        CATEGORY_TYPES.put("HDD", new CategoryType("HDD", null, "/category_icons/hdd.png"));
        CATEGORY_TYPES.put("AirCooler", new CategoryType("AirCooler", "Air Cooler", "/category_icons/aircooler.png"));
        CATEGORY_TYPES.put("WaterCooler", new CategoryType("WaterCooler", "Water Cooler", "/category_icons/watercooler.png"));
        CATEGORY_TYPES.put("PSU", new CategoryType("PSU", "Power Supply", "/category_icons/psu.png"));
        CATEGORY_TYPES.put("Case", new CategoryType("Case", null, "/category_icons/case.png"));
    }

    /**
     * Get a category type corresponding to the specified name.
     * @param name The category's internal name.
     * @return The category type, or null if no category type by that name exists.
     */
    public static CategoryType getCategoryTypeFromName(String name) {
        return CATEGORY_TYPES.get(name);
    }

    private final String name;
    private final String displayName;
    private final String iconImagePath;

    /**
     * Creates a new instance of CategoryType.
     *
     * @param name        The internal name of the category.
     * @param displayName The display name of the category. If set to null,
     *                    it will be set to the internal name.
     * @param iconImagePath   The path to the icon on classpath for this category
     */
    private CategoryType(String name, String displayName, String iconImagePath) {
        this.name = name;
        if (displayName == null) displayName = name;
        this.displayName = displayName;
        this.iconImagePath = iconImagePath;
    }

    /**
     * Get name of category.
     */
    public String getName() {
        return name;
    }

    /**
     * Get display name of category.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get category icon path on classpath.
     */
    public String getIconImagePath() {
        return iconImagePath;
    }
}
