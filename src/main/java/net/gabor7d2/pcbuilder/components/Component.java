package net.gabor7d2.pcbuilder.components;

// TODO support for more than one shop
public class Component {

    private final String brand;
    private final String modelNumber;
    //private final String imagePath;

    private final String productSite;
    private final String priceSite;
    private final String shopSite;
    private final double price;

    /**
     * Creates a new Component
     */
    public Component(String brand, String modelNumber, /*String imagePath, */String productSite, String priceSite, String shopSite, double price) {
        this.brand = brand;
        this.modelNumber = modelNumber;
        this.productSite = productSite;
        this.priceSite = priceSite;
        this.shopSite = shopSite;
        //this.imagePath = imagePath;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getImagePath() {
        return "";
    }

    public String getProductSite() {
        return productSite;
    }

    public String getPriceSite() {
        return priceSite;
    }

    public String getShopSite() {
        return shopSite;
    }

    public double getPrice() {
        return price;
    }

//    public String getFormattedPrice() {
//        return Format.formatCurrency(price);
//    }
}
