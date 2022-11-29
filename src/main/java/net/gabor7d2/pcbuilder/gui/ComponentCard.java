package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.general.ImageLabel;
import net.gabor7d2.pcbuilder.gui.general.ClickableLabel;
import net.gabor7d2.pcbuilder.model.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A ComponentCard is a panel that displays a given Component, with
 * its image, brand, model name, etc. and a radio button above it, that
 * is used to select the component.
 */
public class ComponentCard extends JPanel implements MouseListener {

    /**
     * The fixed width of ComponentCards.
     */
    private final static int COMPONENT_CARD_WIDTH = 144;

    /**
     * The ComponentsPanel this card is in.
     */
    private final ComponentsPanel componentsPanel;

    /**
     * Reference to the displayed component.
     */
    private Component component;

    /**
     * Creates a new ComponentCard.
     *
     * @param component The component to display.
     * @param componentsPanel The ComponentsPanel this card is placed in.
     */
    public ComponentCard(Component component, ComponentsPanel componentsPanel) {
        this.componentsPanel = componentsPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.WHITE));

        addMouseListener(this);
        setBackground(Color.WHITE);
        setAlignmentY(TOP_ALIGNMENT);
        setAlignmentX(CENTER_ALIGNMENT);

        displayComponent(component);
    }

    /**
     * Displays the specified component.
     *
     * @param c The component to display.
     */
    private void displayComponent(Component c) {
        component = c;
        removeAll();

        JRadioButton radioButton = new JRadioButton();
        radioButton.setBorder(BorderFactory.createMatteBorder(4, 0, 8, 0, Color.WHITE));
        radioButton.setBackground(Color.WHITE);
        componentsPanel.getButtonGroup().add(radioButton);
        JPanel rPanel = new JPanel();
        rPanel.setBackground(Color.WHITE);
        rPanel.add(radioButton);
        add(rPanel);

        ImageLabel imageLabel = new ImageLabel();
        imageLabel.setImageFromFileAsync(c.getImagePath(), 104, 104);
        imageLabel.setBorder(Color.WHITE, 4);
        imageLabel.setBackground(Color.WHITE);
        imageLabel.setName("image");
        imageLabel.addMouseListener(this);
        imageLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(imageLabel);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 4, Color.WHITE));
        textPanel.setBackground(Color.WHITE);
        textPanel.setAlignmentX(CENTER_ALIGNMENT);
        add(textPanel);

        ClickableLabel brand = new ClickableLabel(c.getBrand());
        brand.alterFont(Font.BOLD, 13);
        brand.setUrl(c.getProductSiteUrl(), "Click to open product site");
        brand.setHorizontalAlignment(SwingConstants.CENTER);
        brand.setAlignmentX(CENTER_ALIGNMENT);
        textPanel.add(brand);

        ClickableLabel modelNumber = new ClickableLabel(c.getModelName());
        modelNumber.alterFont(Font.PLAIN, 13);
        modelNumber.setUrl(c.getProductSiteUrl(), "Click to open product site");
        modelNumber.setHorizontalAlignment(SwingConstants.CENTER);
        modelNumber.setAlignmentX(CENTER_ALIGNMENT);
        textPanel.add(modelNumber);

        textPanel.add(Box.createRigidArea(new Dimension(0, 4)));

        if (c.getPrice() != null) {
            JPanel pricePanel = new JPanel();
            pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
            pricePanel.setBackground(Color.WHITE);
            textPanel.add(pricePanel);

            String prefix = c.getCategory().getProfile().getCurrencyPrefix();
            String suffix = c.getCategory().getProfile().getCurrencySuffix();
            JLabel priceLabel = new JLabel(prefix + c.getPrice().getValue() + suffix);
            priceLabel.setFont(priceLabel.getFont().deriveFont(Font.PLAIN, 12));
            pricePanel.add(priceLabel);

            if (!c.getPrice().getShopName().isBlank()) {
                JLabel hyphen = new JLabel(" - ");
                hyphen.setFont(hyphen.getFont().deriveFont(Font.PLAIN, 12));
                pricePanel.add(hyphen);

                ClickableLabel shopName = new ClickableLabel(c.getPrice().getShopName());
                shopName.alterFont(Font.PLAIN, 12);
                shopName.setUrl(c.getPrice().getShopUrl(), "Click to open " + c.getPrice().getShopName());
                pricePanel.add(shopName);
            }
        }

        if (!c.getPriceSiteUrl().isBlank()) {
            ClickableLabel priceSite = new ClickableLabel("Price site");
            priceSite.alterFont(Font.PLAIN, 12);
            priceSite.setUrl(c.getPriceSiteUrl(), "Click to open price site");
            priceSite.setHorizontalAlignment(SwingConstants.CENTER);
            priceSite.setAlignmentX(CENTER_ALIGNMENT);
            textPanel.add(priceSite);
        }

        GUIUtils.freezeSize(this, new Dimension(COMPONENT_CARD_WIDTH, getPreferredSize().height));
    }

    /**
     * Get currently displayed Component.
     */
    public Component getComponent() {
        return component;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        java.awt.Component comp = e.getComponent();

        if ("image".equals(comp.getName())) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                System.out.println("image left click");
                // TODO component image viewer
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
