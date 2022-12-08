package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.type.ComponentPropertyType;

import static net.gabor7d2.pcbuilder.type.ComponentPropertyType.registerComponentPropertyType;

public class Motherboard extends Component {

    static {
        registerComponentPropertyType(new ComponentPropertyType("form_factor", "Form factor"));
        registerComponentPropertyType(new ComponentPropertyType("socket", "Socket"));
        registerComponentPropertyType(new ComponentPropertyType("chipset", "Chipset"));
        registerComponentPropertyType(new ComponentPropertyType("ram_slot", "RAM slot"));
        registerComponentPropertyType(new ComponentPropertyType("ram_slot_count", "RAM slots"));
        registerComponentPropertyType(new ComponentPropertyType("ram_max_mb", "Max RAM", "mb", "MB"));
        registerComponentPropertyType(new ComponentPropertyType("pcie_version", "PCIe version"));
    }
}
