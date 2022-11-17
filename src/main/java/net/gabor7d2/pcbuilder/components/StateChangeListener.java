package net.gabor7d2.pcbuilder.components;

import java.util.List;

public interface StateChangeListener {

    void loaded(String type, String displayName, boolean enabled, List<Component> affectedComponents, String categoryUrl, int selIndex);

    void reloaded(String type, String displayName, boolean enabled, List<Component> affectedComponents, String categoryUrl, int selIndex);

    void removed(String type);
}
