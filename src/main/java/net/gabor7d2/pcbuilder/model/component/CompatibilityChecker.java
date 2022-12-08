package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Property;

public class CompatibilityChecker {

    public static boolean checkCoolerHeight(Case case2, AirCooler airCooler) {
        Property caseHeight = case2.getProperty("cooler_height_mm");
        Property coolerHeight = airCooler.getProperty("cooler_height_mm");

        if (caseHeight != null && coolerHeight != null) {
            double caseHeightMm = Double.parseDouble(caseHeight.getValue());
            double coolerHeightMm = Double.parseDouble(coolerHeight.getValue());
            return caseHeightMm >= coolerHeightMm;
        }
        return false;
    }
}
