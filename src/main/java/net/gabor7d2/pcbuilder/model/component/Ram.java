package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.type.ComponentPropertyType;

import static net.gabor7d2.pcbuilder.type.ComponentPropertyType.registerComponentPropertyType;

public class Ram extends Component {

    static {
        registerComponentPropertyType(new ComponentPropertyType("ram_slot", "RAM slot"));
        registerComponentPropertyType(new ComponentPropertyType("frequency_mhz", "Frequency", "mhz", "MHz"));
        registerComponentPropertyType(new ComponentPropertyType("capacity_per_stick_mb", "Capacity/stick", "mb", "MB"));
        registerComponentPropertyType(new ComponentPropertyType("stick_count", "Sticks"));
        registerComponentPropertyType(new ComponentPropertyType("latency", "Latency"));
    }
}
