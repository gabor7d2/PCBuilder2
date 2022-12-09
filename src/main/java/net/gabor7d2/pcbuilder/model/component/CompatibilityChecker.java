package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.model.Property;

/**
 * Class containing static methods for checking compatibility between components.
 */
public class CompatibilityChecker {

    /**
     * Recalculate compatibility of all components with each other in the specified profile.
     *
     * @param profile The profile.
     */
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

    /**
     * Check if the specified component is compatible with all other selected components
     * in the profile.
     *
     * @param component The component to check.
     * @param profile   The profile the component is in.
     * @return Whether it is compatible with all other components in the profile.
     */
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

    /**
     * Checks if the case has enough room for the specified air cooler.
     *
     * @param case2     The case.
     * @param airCooler The air cooler.
     * @return Whether they are compatible.
     */
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

    /**
     * Checks if the cpu's socket is compatible with the motherboard's socket.
     *
     * @param cpu The cpu.
     * @param mb  The motherboard.
     * @return Whether they are compatible.
     */
    public static boolean checkCpuSocket(Cpu cpu, Motherboard mb) {
        return checkPropertyValuesAreEqual(cpu, mb, "socket");
    }

    /**
     * Checks if the ram's type is compatible with the motherboard's ram slot.
     *
     * @param ram The ram.
     * @param mb  The motherboard.
     * @return Whether they are compatible.
     */
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

    /**
     * Checks if the gpu's pcie version is compatible with the motherboard's pcie slots.
     *
     * @param gpu The gpu.
     * @param mb  The motherboard.
     * @return Whether they are compatible.
     */
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

    /**
     * Checks if both of the specified components has a property with the specified key,
     * and that the properties' values are equal.
     *
     * @param c1          The first component.
     * @param c2          The second component.
     * @param propertyKey The property key to check.
     * @return Whether the property value of the 2 components are equal.
     */
    public static boolean checkPropertyValuesAreEqual(Component c1, Component c2, String propertyKey) {
        Property p1 = c1.getProperty(propertyKey);
        Property p2 = c2.getProperty(propertyKey);
        return p1 != null && p2 != null && p1.getValue().equals(p2.getValue());
    }
}
