package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompatibilityCheckerTest {

    @Test
    void checkCoolerHeightTest() {
        Case c = new Case();
        Property caseHeight = new Property();
        caseHeight.setKey("cooler_height_mm");
        c.getProperties().add(caseHeight);

        AirCooler cooler = new AirCooler();
        Property coolerHeight = new Property();
        coolerHeight.setKey("cooler_height_mm");
        cooler.getProperties().add(coolerHeight);

        caseHeight.setValue("100");
        coolerHeight.setValue("100");
        assertTrue(CompatibilityChecker.checkCoolerHeight(c, cooler));

        caseHeight.setValue("110");
        coolerHeight.setValue("100");
        assertTrue(CompatibilityChecker.checkCoolerHeight(c, cooler));

        caseHeight.setValue("100");
        coolerHeight.setValue("101");
        assertFalse(CompatibilityChecker.checkCoolerHeight(c, cooler));
    }

    @Test
    void checkCpuSocketTest() {
        Cpu cpu = new Cpu();
        Property cpuSocket = new Property();
        cpuSocket.setKey("socket");
        cpu.getProperties().add(cpuSocket);

        Motherboard mb = new Motherboard();
        Property mbSocket = new Property();
        mbSocket.setKey("socket");
        mbSocket.setValue("LGA1700");
        mb.getProperties().add(mbSocket);

        cpuSocket.setValue("LGA1700");
        assertTrue(CompatibilityChecker.checkCpuSocket(cpu, mb));

        cpuSocket.setValue("Lerter");
        assertFalse(CompatibilityChecker.checkCpuSocket(cpu, mb));
    }
}
