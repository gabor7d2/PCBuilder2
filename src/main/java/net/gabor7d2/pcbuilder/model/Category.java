package net.gabor7d2.pcbuilder.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class Category {

    private String id;

    private Profile profile;

    private CategoryType type;

    private boolean enabled = true;

    private int defaultSelection = 0;

    @NotNull
    private String priceSiteUrl = "";

    private List<Component> components = new ArrayList<>();

    @Override
    public String toString() {
        return type.getDisplayName();
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
}
