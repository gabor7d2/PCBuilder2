package net.gabor7d2.pcbuilder.components;

import java.util.List;

public class ComponentCategory <T extends Component> {
    private String name;
    private String url;

    private List<T> components;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<T> getComponents() {
        return components;
    }

    public void setComponents(List<T> components) {
        this.components = components;
    }
}
