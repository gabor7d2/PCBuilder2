package net.gabor7d2.pcbuilder.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

// TODO separate models into json models, normal models and view models
@Data
public class Component {

    private String id;

    private Category category;

    @NotNull
    private String brand = "";

    @NotNull
    private String modelName = "";

    @NotNull
    private String imagePath = "";

    @NotNull
    private String productSiteUrl = "";

    @NotNull
    private String priceSiteUrl = "";

    private Price price;
}
