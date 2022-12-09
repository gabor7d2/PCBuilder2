package net.gabor7d2.pcbuilder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class Profile {

    /**
     * Unique ID of the profile, should be something like a
     * randomly generated UUID.
     */
    private String id;

    /**
     * The name of the profile.
     */
    @NotNull
    private String name = "Name missing";

    /**
     * The currency prefix printed in front of price values.
     */
    @NotNull
    private String currencyPrefix = "";

    /**
     * The currency suffix printed after price values.
     */
    @NotNull
    private String currencySuffix = "";

    /**
     * The profile's folder.
     */
    @JsonIgnore
    private File profileFolder;

    /**
     * The categories in this profile.
     */
    private List<Category> categories = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
