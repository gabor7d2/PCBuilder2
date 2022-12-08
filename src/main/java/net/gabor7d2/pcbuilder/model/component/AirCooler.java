package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.type.ComponentPropertyType;
import net.gabor7d2.pcbuilder.model.Component;

import static net.gabor7d2.pcbuilder.type.ComponentPropertyType.registerComponentPropertyType;

public class AirCooler extends Component {

    static {
        registerComponentPropertyType(new ComponentPropertyType("cooler_height_mm", "Cooler height", "mm"));
    }

    @Override
    public boolean compatibleWith(Component other) {
        if (other instanceof Case) {
            return CompatibilityChecker.checkCoolerHeight((Case) other, this);
        }
        return super.compatibleWith(other);
    }
}
