package net.gabor7d2.pcbuilder.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ComponentPropertyTypeTest {

    @Test
    void registerComponentPropertyTypeTest() {
        ComponentPropertyType propertyType = new ComponentPropertyType("cores", "Cores");
        ComponentPropertyType.registerComponentPropertyType(propertyType);

        assertEquals(1, ComponentPropertyType.getComponentPropertyTypes().size());
        assertEquals(propertyType, ComponentPropertyType.getComponentPropertyTypes().get(0));
    }

    @Test
    void getComponentPropertyTypeFromNameTest() {
        // before adding
        assertEquals(0, ComponentPropertyType.getComponentPropertyTypes().size());
        assertNull(ComponentPropertyType.getComponentPropertyTypeFromName("cores"));

        ComponentPropertyType propertyType = new ComponentPropertyType("cores", "Cores");
        ComponentPropertyType.registerComponentPropertyType(propertyType);

        assertEquals(1, ComponentPropertyType.getComponentPropertyTypes().size());
        assertEquals(propertyType, ComponentPropertyType.getComponentPropertyTypeFromName("cores"));
    }
}
