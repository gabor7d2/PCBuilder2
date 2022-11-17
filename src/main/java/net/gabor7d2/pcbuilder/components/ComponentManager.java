package net.gabor7d2.pcbuilder.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import net.gabor7d2.pcbuilder.persistence.PersistenceManager;
import net.gabor7d2.pcbuilder.persistence.Profile;
import net.gabor7d2.pcbuilder.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentManager {

    private final static List<StateChangeListener> listeners = new ArrayList<>();
    private final static Map<String, ComponentCategory<? extends Component>> components = new HashMap<>();

    public static Map<String, ComponentCategory<? extends Component>> getComponentMap() {
        return components;
    }

    public static ComponentCategory<? extends Component> getComponentCategory(String type) {
        return components.get(type);
    }

    public static void loadAll(List<Profile> profiles) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
        //objectMapper.setVisibility(FIELD, ANY);

        for (File f : profiles.stream().map(p -> new File(PersistenceManager.PROFILES_DIRECTORY + File.separator + p.getName(), "components")).toList()) {
            if (!f.isDirectory()) continue;
            for (File f2 : f.listFiles()) {
                if (!f2.isFile()) continue;
                switch (f2.getName()) {
                    case "cpus.json" -> {
                        try {
                            ComponentCategory<Cpu> cpus = objectMapper.readValue(f2, new TypeReference<ComponentCategory<Cpu>>() {});
                            System.out.println(cpus.getComponents().size());
                            components.put("CPU", cpus);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        for (Cpu c : ((ComponentCategory<Cpu>) components.get("CPU")).getComponents()) {
            System.out.println(c);
        }
        listeners.forEach(l -> l.loaded("CPU", "TEST", true,
                (List<Component>) components.get("CPU").getComponents(), "https://youtube.com", 1));
    }

    public static void reload() {
        components.clear();
        //autoLoad(null, true);
    }

    public static void remove(String type) {
        Utils.postEvent(() -> {
            components.remove(type);
            for (StateChangeListener l : listeners) {
                l.removed(type);
            }
        });
    }

    public static void clear() {
        Utils.postEvent(() -> {
            for (String type : components.keySet()) {
                remove(type);
            }
        });
    }

    public static void addStateChangeListener(StateChangeListener l) {
        listeners.add(l);
    }

    public static void removeStateChangeListener(StateChangeListener l) {
        listeners.remove(l);
    }

    private static String firstLetterUppercase(String input) {
        input = input.toLowerCase();
        if (input.length() == 0) return "";
        else return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
