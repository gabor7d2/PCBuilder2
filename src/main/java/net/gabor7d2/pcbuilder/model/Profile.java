package net.gabor7d2.pcbuilder.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

@Data
public class Profile {

    private String id;

    @NotNull
    private String name = "Name missing";

    @NotNull
    private String currencyPrefix = "";

    @NotNull
    private String currencySuffix = "";

    private File profileFolder;

    private List<Category> categories;

    @Override
    public String toString() {
        return name;
    }
}
