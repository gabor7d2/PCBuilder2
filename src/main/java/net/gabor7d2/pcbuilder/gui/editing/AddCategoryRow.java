package net.gabor7d2.pcbuilder.gui.editing;

import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.ThemeController;
import net.gabor7d2.pcbuilder.gui.event.CategoryEvent;
import net.gabor7d2.pcbuilder.gui.event.EventBus;
import net.gabor7d2.pcbuilder.gui.general.ImageLabel;
import net.gabor7d2.pcbuilder.gui.general.ScrollPane2D;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.type.CategoryType;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

/**
 * AddCategoryRow is a ScrollPane2DRow implementation that is used for
 * displaying an empty row with a plus icon, used for adding new categories
 * to the profile.
 */
public class AddCategoryRow extends ScrollPane2D.ScrollPane2DRow {

    /**
     * Creates a new AddCategoryRow.
     */
    public AddCategoryRow() {
        ImageLabel addLabel = new ImageLabel();
        addLabel.setImageFromClasspath("/add_category_icon.png", 120, 120);
        addLabel.setOpaque(false);
        addLabel.setBorder(ThemeController.TRANSPARENT_COLOR, 16);
        add(addLabel);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setToolTipText("Add a new category");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addCategory();
            }
        });
    }

    /**
     * Gets input from user for the details of the category,
     * then creates the category and notifies the listeners about it.
     */
    private void addCategory() {
        Profile currentProfile = Application.getCurrentlySelectedProfile();

        Object[] types = CategoryType.getCategoryTypes().stream()
                .filter(t -> currentProfile.getCategories().stream().noneMatch(c -> c.getType().equals(t.getName()))).toArray();

        // request category type
        CategoryType type = (CategoryType) Application.getDialogManager().showInputDialog("Add category", "Choose category to add:", types);
        if (type == null) return;

        // request price site url
        String priceSiteUrl = Application.getDialogManager().showInputDialog("", "Specify price site url (leave blank for none):");
        if (priceSiteUrl == null) return;

        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setType(type.getName());
        category.setPriceSiteUrl(priceSiteUrl);
        category.setProfile(currentProfile);
        currentProfile.getCategories().add(category);
        EventBus.getInstance().postEvent(new CategoryEvent(CategoryEvent.CategoryEventType.ADD, category));
    }

    @Override
    public void placedInsideScrollPane(ScrollPane2D outerScrollPane) {
        // do nothing as there is no scroll pane inside this component
    }
}
