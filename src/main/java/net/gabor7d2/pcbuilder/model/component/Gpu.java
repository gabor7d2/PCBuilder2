package net.gabor7d2.pcbuilder.model.component;

import net.gabor7d2.pcbuilder.type.ComponentPropertyType;
import net.gabor7d2.pcbuilder.model.Component;

import java.util.ArrayList;
import java.util.List;

import static net.gabor7d2.pcbuilder.type.ComponentPropertyType.registerComponentPropertyType;

public class Gpu extends Component {

    static {
        registerComponentPropertyType(new ComponentPropertyType("base_frequency_mhz", "Base frequency", "mhz", "MHz"));
        registerComponentPropertyType(new ComponentPropertyType("boost_frequency_mhz", "Boost frequency", "mhz", "MHz"));
        registerComponentPropertyType(new ComponentPropertyType("vram_amount_mb", "VRAM", "mb", "MB"));
        registerComponentPropertyType(new ComponentPropertyType("vram_frequency_mhz", "VRAM frequency", "mhz", "MHz"));
        registerComponentPropertyType(new ComponentPropertyType("cuda_core_amount", "CUDA cores"));
        registerComponentPropertyType(new ComponentPropertyType("memory_bus_width", "Memory bus width", "bit", "bit"));
        registerComponentPropertyType(new ComponentPropertyType("pcie_version", "PCIe version"));
    }

    @Override
    public List<String> getPropertiesDisplay() {
        List<String> list = new ArrayList<>();

        String baseF = getPropertyValueDisplay("base_frequency_mhz");
        String boostF = getPropertyValueDisplay("boost_frequency_mhz");
        if (!baseF.isEmpty() && !boostF.isEmpty()) list.add(baseF + " / " + boostF);

        return list;
    }
}
