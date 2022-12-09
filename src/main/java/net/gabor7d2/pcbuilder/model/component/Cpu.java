package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.type.ComponentPropertyType;

import static net.gabor7d2.pcbuilder.type.ComponentPropertyType.registerComponentPropertyType;

public class Cpu extends Component {

    static {
        registerComponentPropertyType(new ComponentPropertyType("socket", "Socket"));
        registerComponentPropertyType(new ComponentPropertyType("generation", "Generation"));
        registerComponentPropertyType(new ComponentPropertyType("unlocked", "Unlocked"));
        registerComponentPropertyType(new ComponentPropertyType("cores", "Cores"));
        registerComponentPropertyType(new ComponentPropertyType("threads", "Threads"));
        registerComponentPropertyType(new ComponentPropertyType("base_frequency_mhz", "Base frequency", "mhz", "MHz"));
        registerComponentPropertyType(new ComponentPropertyType("turbo_frequency_mhz", "Turbo frequency", "mhz", "MHz"));
        registerComponentPropertyType(new ComponentPropertyType("ram_max_mb", "Max RAM", "mb", "MB"));
        registerComponentPropertyType(new ComponentPropertyType("tdp_w", "TDP", "w", "W"));
    }

    @Override
    public boolean compatibleWith(Component other) {
        if (other instanceof Motherboard) {
            return CompatibilityChecker.checkCpuSocket(this, (Motherboard) other);
        }
        return super.compatibleWith(other);
    }
}
