package net.gabor7d2.pcbuilder.model;

import java.io.File;
import java.util.List;

public class Profile {

    private String name;
    private String currencyPrefix;
    private String currencySuffix;

    private File profileFolder;

    private List<Category> categories;

    /*public Profile(String name, List<Category> categories) {
        this.name = name;
        this.categories = categories;
    }*/

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyPrefix() {
        return currencyPrefix;
    }

    public void setCurrencyPrefix(String currencyPrefix) {
        this.currencyPrefix = currencyPrefix;
    }

    public String getCurrencySuffix() {
        return currencySuffix;
    }

    public void setCurrencySuffix(String currencySuffix) {
        this.currencySuffix = currencySuffix;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public File getProfileFolder() {
        return profileFolder;
    }

    public void setProfileFolder(File profileFolder) {
        this.profileFolder = profileFolder;
    }
}
