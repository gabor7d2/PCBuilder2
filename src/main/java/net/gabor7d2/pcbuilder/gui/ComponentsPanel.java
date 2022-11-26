package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.general.SmartButtonGroup;
import net.gabor7d2.pcbuilder.model.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ComponentsPanel extends JPanel {

    private SmartButtonGroup buttonGroup = new SmartButtonGroup();

    private List<ComponentCard> componentCards = new ArrayList<>();

    public ComponentsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.WHITE);
    }

    public SmartButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public void displayComponents(List<Component> components) {
        removeAll();
        componentCards.clear();

        for (Component comp : components) {
            ComponentCard card = new ComponentCard(comp, this);
            componentCards.add(card);
            add(card);
        }
    }
}
