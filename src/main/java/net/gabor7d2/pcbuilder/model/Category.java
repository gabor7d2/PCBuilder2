package net.gabor7d2.pcbuilder.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String id;

    private Profile profile;

    private CategoryType type;

    private boolean enabled = true;

    private int defaultSelection = 0;

    private String priceSiteUrl = "";

    private final List<Component> components = new ArrayList<>();

    @Override
    public String toString() {
        return type.getDisplayName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getShortName() {
        return type.getName();
    }

    public String getDisplayName() {
        return type.getDisplayName();
    }

    public String getIconImagePath() {
        return type.getIconImagePath();
    }

    public void setType(String typeName) {
        this.type = CategoryType.getCategoryTypeFromName(typeName);
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(int defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    public String getPriceSiteUrl() {
        return priceSiteUrl;
    }

    public void setPriceSiteUrl(String priceSiteUrl) {
        this.priceSiteUrl = priceSiteUrl;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> list) {
        components.clear();
        components.addAll(list);
    }
}
