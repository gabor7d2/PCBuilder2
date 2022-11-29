package net.gabor7d2.pcbuilder.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class Price {

    /**
     * The price value.
     */
    private double value = 0.0;

    /**
     * The shop's name where this price is from.
     */
    @NotNull
    private String shopName = "";

    /**
     * The shop's url where this price is from.
     */
    @NotNull
    private String shopUrl = "";
}
