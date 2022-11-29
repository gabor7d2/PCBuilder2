package net.gabor7d2.pcbuilder.model;

import lombok.Data;

@Data
public class Settings {

    private String selectedProfile;

    /**
     * Whether the app should be in dark mode (true) or light mode (false)
     */
    private boolean darkMode;
}
