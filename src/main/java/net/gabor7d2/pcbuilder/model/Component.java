package net.gabor7d2.pcbuilder.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

// TODO separate models into json models, normal models and view models
@Data
public class Component {

    /**
     * Unique ID of the component, should be something like a
     * randomly generated UUID.
     */
    private String id;

    /**
     * The category this component is in.
     */
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
}
