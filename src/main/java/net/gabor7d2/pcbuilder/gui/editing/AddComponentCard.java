package net.gabor7d2.pcbuilder.gui.editing;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.GUIUtils;
import net.gabor7d2.pcbuilder.gui.ThemeController;
import net.gabor7d2.pcbuilder.gui.dialog.MultiInputDialog;
import net.gabor7d2.pcbuilder.gui.event.ComponentEvent;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.general.ImageLabel;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Component;
import net.gabor7d2.pcbuilder.model.Price;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

/**
 * An AddComponentCard is a panel that displays an empty card with a plus
 * sign on it, used for adding new components to a category.
 */
public class AddComponentCard extends JPanel {

    /**
     * The fixed width of AddComponentCards.
     */
    private final static int ADD_COMPONENT_CARD_WIDTH = 144;

    /**
     * The category this card belongs to.
     */
    private final Category category;

    /**
     * Creates a new AddComponentCard.
     *
     * @param category The category this AddComponentCard belongs to.
     */
    public AddComponentCard(Category category) {
        this.category = category;
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(ThemeController.TRANSPARENT_COLOR);

        setAlignmentY(TOP_ALIGNMENT);
        setAlignmentX(CENTER_ALIGNMENT);

        JPanel innerPanel = new JPanel();
        innerPanel.setOpaque(false);
        innerPanel.setBackground(ThemeController.TRANSPARENT_COLOR);
        innerPanel.setAlignmentY(CENTER_ALIGNMENT);
        ImageLabel addLabel = new ImageLabel();
        addLabel.setImageFromClasspath("/add_component_icon.png", 88, 88);
        innerPanel.add(addLabel);
        addLabel.setAlignmentY(CENTER_ALIGNMENT);
        add(innerPanel, BorderLayout.CENTER);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setToolTipText("Add a new component");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addComponent();
            }
        });

        GUIUtils.freezeSize(this, new Dimension(ADD_COMPONENT_CARD_WIDTH, getPreferredSize().height));
    }

    /**
     * Gets input from user for the details of the component,
     * then creates the component and notifies the listeners about it.
     */
    private void addComponent() {
        Profile currentProfile = Application.getCurrentlySelectedProfile();

        MultiInputDialog multiInputDialog = Application.getDialogManager().createMultiInputDialog(inputs -> {
            double price = 0;
            try {
                price = Double.parseDouble(inputs.get(4));
            } catch (Exception ignored) {
                Application.getDialogManager().showErrorDialog("Price is an invalid number!");
                return false;
            }

            Component component = new Component();
            component.setId(UUID.randomUUID().toString());
            component.setBrand(inputs.get(0));
            component.setModelName(inputs.get(1));
            component.setProductSiteUrl(inputs.get(2));
            component.setPriceSiteUrl(inputs.get(3));

            Price price1 = new Price();
            price1.setValue(price);
            price1.setShopName(inputs.get(5));
            price1.setShopUrl(inputs.get(6));
            component.setPrice(price1);

            component.setCategory(category);
            category.getComponents().add(component);
            EventBus.getInstance().postEvent(new ComponentEvent(ComponentEvent.ComponentEventType.ADD, component));

            return true;
        });
        multiInputDialog.addInputField("Brand:");
        multiInputDialog.addInputField("Model name:");
        multiInputDialog.addInputField("Product site url:");
        multiInputDialog.addInputField("Price site url:");
        multiInputDialog.addInputField("Price:");
        multiInputDialog.addInputField("Shop name:");
        multiInputDialog.addInputField("Shop url:");
        multiInputDialog.pack();
        multiInputDialog.setResizable(false);
        multiInputDialog.setVisible(true);
    }
}
