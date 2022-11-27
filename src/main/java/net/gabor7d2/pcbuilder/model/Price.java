package net.gabor7d2.pcbuilder.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class Price {

    private double value = 0.0;

    @NotNull
    private String shopName = "";

    @NotNull
    private String shopUrl = "";
}
