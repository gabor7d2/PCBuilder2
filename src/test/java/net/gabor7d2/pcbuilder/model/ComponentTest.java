package net.gabor7d2.pcbuilder.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComponentTest {
    @Test
    void compatibleWithTest() {
        Component result = new Component();
        assertTrue(result.compatibleWith(null));
    }

    @Test
    void getPropertyDisplayTest1() {
        Component component = new Component();
        Property testerProperty1 = new Property();
        testerProperty1.setKey("testKey");
        testerProperty1.setValue("testValue");

        assertEquals(testerProperty1.getKey() + ": " + testerProperty1.getValue(), component.getPropertyDisplay(testerProperty1));

        // TODO
//        component.getProperties().add(testerProperty1);
    }

    @Test
    void getPropertiesDisplayTest() {
        Component component = new Component();
        Property testerProperty1 = new Property();
        testerProperty1.setKey("testKey");
        testerProperty1.setValue("testValue");
        component.getProperties().add(testerProperty1);
        Property testerProperty2 = new Property();
        testerProperty2.setKey("testKey");
        testerProperty2.setValue("testValue");
        component.getProperties().add(testerProperty2);
        ArrayList<String> result = new ArrayList<>();
        result.add(component.getPropertyDisplay(testerProperty1));
        result.add(component.getPropertyDisplay(testerProperty2));

        assertEquals(result, component.getPropertiesDisplay());
    }
}
