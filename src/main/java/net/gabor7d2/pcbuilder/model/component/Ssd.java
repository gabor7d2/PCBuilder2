package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.type.ComponentPropertyType;

import static net.gabor7d2.pcbuilder.type.ComponentPropertyType.registerComponentPropertyType;

public class Ssd extends Component {

    static {
        registerComponentPropertyType(new ComponentPropertyType("capacity_gb", "Capacity", "gb", "GB"));
    }
}
