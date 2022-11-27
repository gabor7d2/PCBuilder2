package net.gabor7d2.pcbuilder.model;

// TODO separate models into model json-s
public class Component {

    private String id;

    private Category category;

    private String brand = "";
    private String modelName = "";
    private String imagePath = "";

    private String productSiteUrl = "";
    private String priceSiteUrl = "";

    private Price price;

    /*public Component(String brand, String modelName, String imagePath, String productSite, String priceSite, String shopSite, double price) {
        this.brand = brand;
        this.modelName = modelName;
        this.imagePath = imagePath;
        this.productSite = productSite;
        this.priceSite = priceSite;
        this.shopSite = shopSite;
        this.price = price;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProductSiteUrl() {
        return productSiteUrl;
    }

    public void setProductSiteUrl(String productSiteUrl) {
        this.productSiteUrl = productSiteUrl;
    }

    public String getPriceSiteUrl() {
        return priceSiteUrl;
    }

    public void setPriceSiteUrl(String priceSiteUrl) {
        this.priceSiteUrl = priceSiteUrl;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
