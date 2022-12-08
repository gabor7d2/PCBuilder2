package net.gabor7d2.pcbuilder.model;

import lombok.Data;

@Data
public class Property {
    /**
     * The key (internal name) of the property.
     */
    private String key;

    /**
     * The value of the property.
     */
    private String value;
}
