package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.model.Property;

public class CompatibilityChecker {

    public static void recalculateComponentCompatibility(Profile profile) {
        // clear incompatibility on all components
        for (Category cat : profile.getCategories()) {
            for (Component comp : cat.getComponents()) {
                comp.setIncompatible(false);
            }
        }

        // calculate incompatibility for all currently selected components
        for (Category cat : profile.getCategories()) {
            if (!cat.isEnabled()) continue;
            if (cat.getSelection() >= 0 && cat.getSelection() < cat.getComponents().size()) {
                Component comp = cat.getComponents().get(cat.getSelection());
                boolean isCompatible = isCompatibleWithAllOtherComponents(cat.getComponents().get(cat.getSelection()), profile);
                comp.setIncompatible(!isCompatible);
            }
        }
    }

    public static boolean isCompatibleWithAllOtherComponents(Component component, Profile profile) {
        for (Category cat : profile.getCategories()) {
            if (!cat.isEnabled()) continue;
            if (cat.getSelection() >= 0 && cat.getSelection() < cat.getComponents().size()) {
                Component comp = cat.getComponents().get(cat.getSelection());
                if (comp == component) continue;
                boolean isCompatible = component.compatibleWith(comp);
                if (!isCompatible) return false;
            }
        }
        return true;
    }

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

    public static boolean checkCpuSocket(Cpu cpu, Motherboard mb) {
        return checkPropertyValuesAreEqual(cpu, mb, "socket");
    }

    public static boolean checkRamSlot(Ram ram, Motherboard mb) {
        // check ram slot
        if (!checkPropertyValuesAreEqual(ram, mb, "ram_slot")) {
            return false;
        }

        // check amount of ram sticks
        Property stickCount = ram.getProperty("stick_count");
        Property ramSlotCount = mb.getProperty("ram_slot_count");

        if (stickCount != null && ramSlotCount != null) {
            int stickCount2 = Integer.parseInt(stickCount.getValue());
            int ramSlotCount2 = Integer.parseInt(ramSlotCount.getValue());
            if (stickCount2 > ramSlotCount2) return false;
        }

        return true;
    }

    public static boolean checkPcieSlot(Gpu gpu, Motherboard mb) {
        Property gpuVersion = gpu.getProperty("pcie_version");
        Property mbVersion = mb.getProperty("pcie_version");

        if (gpuVersion != null && mbVersion != null) {
            double gpuVersion2 = Double.parseDouble(gpuVersion.getValue());
            double mbVersion2 = Double.parseDouble(mbVersion.getValue());
            return mbVersion2 >= gpuVersion2;
        }
        return false;
    }

    public static boolean checkPropertyValuesAreEqual(Component c1, Component c2, String propertyKey) {
        return checkPropertyValuesAreEqual(c1, c2, propertyKey, propertyKey);
    }

    public static boolean checkPropertyValuesAreEqual(Component c1, Component c2, String prop1Key, String prop2Key) {
        Property p1 = c1.getProperty(prop1Key);
        Property p2 = c2.getProperty(prop2Key);
        return p1 != null && p2 != null && p1.getValue().equals(p2.getValue());
    }
}
