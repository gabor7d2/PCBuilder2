package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.type.ComponentPropertyType;

import static net.gabor7d2.pcbuilder.type.ComponentPropertyType.registerComponentPropertyType;

public class Psu extends Component {

    static {
        registerComponentPropertyType(new ComponentPropertyType("performance_w", "Performance", "w", "W"));
        registerComponentPropertyType(new ComponentPropertyType("efficiency_rating", "Efficiency"));
        registerComponentPropertyType(new ComponentPropertyType("modularity", "Modularity"));
    }
}
