package net.gabor7d2.pcbuilderold.components;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Case extends Component {

    private final double coolerHeightMM;

    @JsonCreator
    public Case(String brand, String modelNumber/*, String imagePath*/, String productSite, String priceSite, String shopSite, double price, double coolerHeightMM) {
        super(brand, modelNumber/*, imagePath*/, productSite, priceSite, shopSite, price);
        this.coolerHeightMM = coolerHeightMM;
    }

    public double getCoolerHeightMM() {
        return coolerHeightMM;
    }
}
