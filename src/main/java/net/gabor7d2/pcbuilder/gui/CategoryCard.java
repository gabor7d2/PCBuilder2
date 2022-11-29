package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.general.ClickableLabel;
import net.gabor7d2.pcbuilder.gui.general.ImageLabel;
import net.gabor7d2.pcbuilder.model.Category;

import javax.swing.*;
import java.awt.*;

/**
 * A CategoryCard is a panel that shows a category's icon and display name,
 * and has a price site url link that opens the price site when clicked.
 */
public class CategoryCard extends JPanel {

    // TODO cache category icons/panels here

    /**
     * The fixed size of the category card.
     */
    private final static Dimension CATEGORY_CARD_SIZE = new Dimension(128, 188);

    /**
     * Creates a new CategoryCard.
     *
     * @param category The category to display.
     */
    public CategoryCard(Category category) {
        // setup this
        setLayout(new GridBagLayout());
        GUIUtils.freezeSize(this, CATEGORY_CARD_SIZE);

        // setup panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // setup components
        ImageLabel imageLabel = new ImageLabel();
        imageLabel.setImageFromClasspathAsync(category.getIconImagePath(), 80, 80);
        imageLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(imageLabel);

        JLabel nameLabel = new JLabel(category.toString());
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 14));
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(nameLabel);

        if (!category.getPriceSiteUrl().isBlank()) {
            ClickableLabel priceSite = new ClickableLabel("Price site");
            priceSite.alterFont(Font.PLAIN, 14);
            priceSite.setUrl(category.getPriceSiteUrl(), "Click to open price site");
            priceSite.setAlignmentX(CENTER_ALIGNMENT);
            panel.add(priceSite);
        }

        panel.setAlignmentY(CENTER_ALIGNMENT);
        add(panel);
    }
}
