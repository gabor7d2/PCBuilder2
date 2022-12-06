package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.general.SmartButtonGroup;
import net.gabor7d2.pcbuilder.model.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A ComponentsPanel is a panel that contains a horizontal list of ComponentCards,
 * and their RadioButtons are grouped together in a button group so that at most one
 * may be selected at any time.
 */
public class ComponentsPanel extends JPanel {

    /**
     * The background color to use for components panels in light theme.
     */
    private final static Color COMPONENT_BG_LIGHT_THEME = Color.WHITE;

    /**
     * The background color to use for components panels in dark theme.
     */
    private final static Color COMPONENT_BG_DARK_THEME = new Color(51, 54, 56);

    /**
     * The button group that groups the ComponentCards' radio buttons.
     */
    private final SmartButtonGroup buttonGroup = new SmartButtonGroup();

    /**
     * The component cards this panel contains.
     */
    private final List<ComponentCard> componentCards = new ArrayList<>();

    /**
     * Creates a new ComponentsPanel.
     *
     * @param components The components to display in ComponentCard form.
     */
    public ComponentsPanel(List<Component> components) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        displayComponents(components);
    }

    public SmartButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    /**
     * Display the specified list of components, creating a ComponentCard
     * for each of them and displaying the cards in a horizontal list.
     *
     * @param components The list of components to display.
     */
    private void displayComponents(List<Component> components) {
        removeAll();
        componentCards.clear();

        for (Component comp : components) {
            ComponentCard card = new ComponentCard(comp, buttonGroup);
            componentCards.add(card);
            add(card);
        }
    }

    /**
     * Updates the background color on theme change.
     */
    private void updateTheme() {
        setBackground(Application.getThemeController().isDarkMode() ? COMPONENT_BG_DARK_THEME : COMPONENT_BG_LIGHT_THEME);
    }

    @Override
    public void updateUI() {
        // gets called on theme switching
        super.updateUI();
        updateTheme();
    }
}
