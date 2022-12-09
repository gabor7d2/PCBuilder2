package net.gabor7d2.pcbuilder.model;

import net.gabor7d2.pcbuilder.type.ComponentPropertyType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static net.gabor7d2.pcbuilder.type.ComponentPropertyType.registerComponentPropertyType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComponentTest {

    @BeforeAll
    static void init() {
        registerComponentPropertyType(new ComponentPropertyType("cores", "Cores"));
        registerComponentPropertyType(new ComponentPropertyType("threads", "Threads"));
        registerComponentPropertyType(new ComponentPropertyType("turbo_frequency", "Turbo frequency", "mhz", "MHz"));
    }

    private Component component;
    Property coresProperty;
    Property threadsProperty;
    Property turboFreqProperty;

    @BeforeEach
    void constructComponent() {
        component = new Component();
        coresProperty = new Property();
        coresProperty.setKey("cores");
        coresProperty.setValue("8");
        component.getProperties().add(coresProperty);

        threadsProperty = new Property();
        threadsProperty.setKey("threads");
        threadsProperty.setValue("20");
        component.getProperties().add(threadsProperty);

        turboFreqProperty = new Property();
        turboFreqProperty.setKey("turbo_frequency");
        turboFreqProperty.setValue("4000");
        component.getProperties().add(turboFreqProperty);
    }

    @Test
    void compatibleWithTest() {
        assertTrue(component.compatibleWith(null));
    }

    @Test
    void getPropertyTest() {
        assertEquals(coresProperty, component.getProperty("cores"));
        assertEquals(threadsProperty, component.getProperty("threads"));
        assertEquals(turboFreqProperty, component.getProperty("turbo_frequency"));
    }

    @Test
    void getPropertyDisplayByKeyTest() {
        assertEquals("Cores: 8", component.getPropertyDisplay("cores"));
        assertEquals("Threads: 20", component.getPropertyDisplay("threads"));
        assertEquals("Turbo frequency: 4000 MHz", component.getPropertyDisplay("turbo_frequency"));
    }

    @Test
    void getPropertyDisplayByPropertyTest() {
        assertEquals("Cores: 8", component.getPropertyDisplay(coresProperty));
        assertEquals("Threads: 20", component.getPropertyDisplay(threadsProperty));
        assertEquals("Turbo frequency: 4000 MHz", component.getPropertyDisplay(turboFreqProperty));
    }

    @Test
    void getPropertyValueDisplayByKeyTest() {
        assertEquals("8", component.getPropertyValueDisplay("cores"));
        assertEquals("20", component.getPropertyValueDisplay("threads"));
        assertEquals("4000 MHz", component.getPropertyValueDisplay("turbo_frequency"));
    }

    @Test
    void getPropertyValueDisplayByPropertyTest() {
        assertEquals("8", component.getPropertyValueDisplay(coresProperty));
        assertEquals("20", component.getPropertyValueDisplay(threadsProperty));
        assertEquals("4000 MHz", component.getPropertyValueDisplay(turboFreqProperty));
    }

    @Test
    void getPropertiesDisplayTest() {
        List<String> lines = component.getPropertiesDisplay();
        assertEquals(3, lines.size());
        assertEquals("Cores: 8", lines.get(0));
        assertEquals("Threads: 20", lines.get(1));
        assertEquals("Turbo frequency: 4000 MHz", lines.get(2));
    }
}
