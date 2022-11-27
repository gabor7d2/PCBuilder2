package net.gabor7d2.pcbuilder.gui;

import net.gabor7d2.pcbuilder.gui.general.ClickableLabel;
import net.gabor7d2.pcbuilder.gui.general.ImageLabel;
import net.gabor7d2.pcbuilder.model.Category;

import javax.swing.*;
import java.awt.*;

public class CategoryCard extends JPanel {

    // TODO cache category icons here
    private final static Dimension CATEGORY_CARD_SIZE = new Dimension(128, 188);

    public CategoryCard(Category category) {
        setLayout(new GridBagLayout());
        GUIUtils.freezeSize(this, CATEGORY_CARD_SIZE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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
