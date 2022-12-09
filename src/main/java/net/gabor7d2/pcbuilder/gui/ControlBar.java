package net.gabor7d2.pcbuilder.gui;

import com.formdev.flatlaf.FlatClientProperties;
import net.gabor7d2.pcbuilder.Application;
import net.gabor7d2.pcbuilder.gui.dialog.MultiInputDialog;
import net.gabor7d2.pcbuilder.gui.event.*;
import net.gabor7d2.pcbuilder.gui.general.ImageLoader;
import net.gabor7d2.pcbuilder.gui.profileimporter.ProfileImporter;
import net.gabor7d2.pcbuilder.model.Category;
import net.gabor7d2.pcbuilder.model.Price;
import net.gabor7d2.pcbuilder.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.UUID;

/**
 * The ControlBar is a panel that is at the bottom of the main window
 * and handles profile selection, profile management, switching to edit mode,
 * and calculating and displaying the total price of the selected components.
 */
public class ControlBar extends JPanel implements ProfileEventListener, CategoryEventListener, ComponentEventListener {

    /**
     * Background color of the panel.
     */
    private static final Color BG_COLOR = Color.DARK_GRAY;

    /**
     * Text color of the panel.
     */
    private static final Color TEXT_COLOR = Color.WHITE;

    /**
     * West panel of the control bar.
     */
    private JPanel westPanel;

    /**
     * East panel of the control bar.
     */
    private JPanel eastPanel;

    /**
     * East panel of the control bar in viewer mode.
     */
    private JPanel viewerEastPanel;

    /**
     * East panel of the control bar in editor mode.
     */
    private JPanel editorEastPanel;

    /**
     * The total price label.
     */
    private JLabel totalPriceLabel;

    /**
     * The profile selector JComboBox.
     */
    private JComboBox<Profile> profileSelector;

    /**
     * Action listener of profile selector.
     */
    private ActionListener profileSelectorActionListener;

    /**
     * Theme button that needs icon swapping when theme is switched.
     */
    private JButton themeButton;

    private final ProfileImporter importer = new ProfileImporter(new File(System.getProperty("user.home")));

    /**
     * Creates a new ControlBar.
     */
    public ControlBar() {
        // setup this
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        setupWestPanel();
        setupEastPanel();

        updateTheme();

        // subscribe to events
        EventBus.getInstance().subscribeToProfileEvents(this);
        EventBus.getInstance().subscribeToCategoryEvents(this);
        EventBus.getInstance().subscribeToComponentEvents(this);
    }

    private void setupWestPanel() {
        // setup west panel containing the texts
        westPanel = new JPanel();
        westPanel.setLayout(new GridBagLayout());
        westPanel.setBackground(getBackground());
        westPanel.setBorder(BorderFactory.createMatteBorder(8, 16, 8, 0, getBackground()));
        add(westPanel, BorderLayout.WEST);

        // set up total price label
        totalPriceLabel = new JLabel();
        totalPriceLabel.setForeground(TEXT_COLOR);
        totalPriceLabel.setFont(totalPriceLabel.getFont().deriveFont(Font.BOLD, 13));

        totalPriceLabel.setVisible(false);
        westPanel.add(totalPriceLabel);
    }

    private void setupEastPanel() {
        eastPanel = new JPanel();
        eastPanel.setLayout((new BoxLayout(eastPanel, BoxLayout.X_AXIS)));
        eastPanel.setBackground(getBackground());
        eastPanel.setBorder(BorderFactory.createMatteBorder(12, 8, 12, 8, getBackground()));
        add(eastPanel, BorderLayout.EAST);

        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));
        setupProfileSelector();
        eastPanel.add(Box.createRigidArea(new Dimension(4, 1)));

        setupViewerEastPanel();
        setupEditorEastPanel();

        themeButton = addButton("", eastPanel, e -> {
            Application.getThemeController().setDarkMode(!themeButton.getName().equals("dark"));
        });
        themeButton.setMargin(new Insets(0, 0, 0, 0));

        eastPanel.setMaximumSize(eastPanel.getMinimumSize());
    }

    private void setupViewerEastPanel() {
        // setup viewer east label containing the profile selector and the buttons
        viewerEastPanel = new JPanel();
        viewerEastPanel.setLayout(new BoxLayout(viewerEastPanel, BoxLayout.X_AXIS));
        viewerEastPanel.setBackground(getBackground());
        eastPanel.add(viewerEastPanel);

        addButton("Import", viewerEastPanel, e -> {
            importer.showDialog();
        });

        addButton("Delete", viewerEastPanel, e -> {
            if (profileSelector.getSelectedItem() != null) {
                int result = Application.getDialogManager().showQuestionDialog(
                        "Confirm", "Are you sure? This operation cannot be undone.", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    Application.deleteProfile((Profile) profileSelector.getSelectedItem());
                }
            }
        });

        addButton("Editor Mode", viewerEastPanel, e -> switchEditorMode(true));
    }

    private void setupEditorEastPanel() {
        // setup editor east label containing buttons
        editorEastPanel = new JPanel();
        editorEastPanel.setLayout(new BoxLayout(editorEastPanel, BoxLayout.X_AXIS));
        editorEastPanel.setBackground(getBackground());

        addButton("New", editorEastPanel, e -> {
            MultiInputDialog inputDialog = Application.getDialogManager().createMultiInputDialog(inputs -> {
                Profile profile = new Profile();
                profile.setId(UUID.randomUUID().toString());
                profile.setName(inputs.get(0));
                profile.setCurrencyPrefix(inputs.get(1));
                profile.setCurrencySuffix(inputs.get(2));

                EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.ADD, profile));
                EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.SELECT, profile));
                return true;
            });
            inputDialog.addInputField("Profile name:");
            inputDialog.addInputField("Currency prefix:");
            inputDialog.addInputField("Currency suffix:");
            inputDialog.pack();
            inputDialog.setResizable(false);
            inputDialog.setVisible(true);
        });

        addButton("Delete", editorEastPanel, e -> {
            if (profileSelector.getSelectedItem() != null) {
                int result = Application.getDialogManager().showQuestionDialog(
                        "Confirm", "Are you sure? This operation cannot be undone.", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    Application.deleteProfile((Profile) profileSelector.getSelectedItem());
                }
            }
        });

        addButton("Reload", editorEastPanel, e -> {
            if (profileSelector.getSelectedItem() != null) {
                if (Application.isEditMode()) {
                    int result = Application.getDialogManager().showQuestionDialog(
                            "Confirm", "Are you sure? Unsaved work will be lost.", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        Application.reloadProfile((Profile) profileSelector.getSelectedItem());
                    }
                } else {
                    Application.reloadProfile((Profile) profileSelector.getSelectedItem());
                }
            }
        });

        addButton("Save", editorEastPanel, e -> {
            if (profileSelector.getSelectedItem() != null) {
                Application.saveProfile((Profile) profileSelector.getSelectedItem());
                Application.getDialogManager().showInfoDialog("Save successful.");
            }
        });

        addButton("Viewer Mode", editorEastPanel, e -> switchEditorMode(false));
    }

    /**
     * Switches between the editor mode and the viewer mode buttons.
     *
     * @param editorMode Whether the editor mode or the viewer mode should be shown.
     */
    private void switchEditorMode(boolean editorMode) {
        if (editorMode) {
            eastPanel.remove(viewerEastPanel);
            eastPanel.add(editorEastPanel, 3);
            EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.EDIT_MODE, null));
        } else {
            eastPanel.remove(editorEastPanel);
            eastPanel.add(viewerEastPanel, 3);
            EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.VIEW_MODE, null));
        }
        revalidate();
        repaint();
    }

    /**
     * Sets up profile selector JComboBox. When an item is selected in the ComboBox,
     * a ProfileEvent with type SELECT is posted on the EventBus.
     */
    private void setupProfileSelector() {
        profileSelector = new JComboBox<>();
        profileSelector.putClientProperty(FlatClientProperties.MINIMUM_WIDTH, 200);
        profileSelectorActionListener = e -> {
            EventBus.getInstance().postEvent(new ProfileEvent(ProfileEvent.ProfileEventType.SELECT, (Profile) profileSelector.getSelectedItem()));
        };
        profileSelector.addActionListener(profileSelectorActionListener);
        eastPanel.add(profileSelector);
    }

    /**
     * Adds a button to the specified panel.
     *
     * @param name     The text to show on the button.
     * @param panel     Panel to add the button to.
     * @param listener The listener to call when clicked.
     * @return The created JButton.
     */
    private JButton addButton(String name, JPanel panel, ActionListener listener) {
        JButton btn = new JButton(name);
        btn.addActionListener(listener);

        panel.add(Box.createRigidArea(new Dimension(4, 1)));
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(4, 1)));

        return btn;
    }

    /**
     * Updates the text of the total price label.
     *
     * @param value  The price value.
     * @param prefix The prefix to print in front of the price.
     * @param suffix The suffix to print after the price.
     */
    private void updateTotalPrice(double value, String prefix, String suffix) {
        totalPriceLabel.setText("Total Price: " + prefix + value + suffix);
    }

    /**
     * Calculates the total price of the selected components in the specified profile.
     * Each selected component's price is added to the total if the
     * component's category is enabled.
     *
     * @param profile The profile.
     */
    private void calculateAndDisplayTotalPrice(Profile profile) {
        if (profile == null) {
            updateTotalPrice(0, "", "");
            totalPriceLabel.setVisible(false);
        } else {
            double total = 0;
            for (Category c : profile.getCategories()) {
                if (c.isEnabled() && c.getSelection() >= 0 && c.getSelection() < c.getComponents().size()) {
                    // if selection is valid and category is enabled, add selected component's price to total
                    Price p = c.getComponents().get(c.getSelection()).getPrice();
                    if (p != null)
                        total += p.getValue();
                }
            }
            updateTotalPrice(total, profile.getCurrencyPrefix(), profile.getCurrencySuffix());
            totalPriceLabel.setVisible(true);
        }
    }

    @Override
    public void processProfileEvent(ProfileEvent e) {
        if (e.getType() == ProfileEvent.ProfileEventType.ADD) {
            // a new profile was added, add it into the profile selector
            if (profileSelector.getItemCount() == 0) {
                // add first item without selecting it
                profileSelector.removeActionListener(profileSelectorActionListener);
                profileSelector.addItem(e.getProfile());
                profileSelector.setSelectedIndex(-1);
                profileSelector.addActionListener(profileSelectorActionListener);
            } else {
                profileSelector.addItem(e.getProfile());
            }
        }
        if (e.getType() == ProfileEvent.ProfileEventType.SELECT) {
            // switched to another profile, calculate new total price
            calculateAndDisplayTotalPrice(e.getProfile());

            profileSelector.removeActionListener(profileSelectorActionListener);
            profileSelector.setSelectedItem(e.getProfile());
            profileSelector.addActionListener(profileSelectorActionListener);
        }
        if (e.getType() == ProfileEvent.ProfileEventType.DELETE) {
            profileSelector.removeItem(e.getProfile());
        }
        if (e.getType() == ProfileEvent.ProfileEventType.RELOAD) {
            // replace profile entry in selector without triggering the listener
            profileSelector.removeActionListener(profileSelectorActionListener);
            int index = profileSelector.getSelectedIndex();
            profileSelector.removeItemAt(index);
            profileSelector.addItem(e.getProfile());
            profileSelector.addActionListener(profileSelectorActionListener);
            profileSelector.setSelectedItem(e.getProfile());
        }
    }

    @Override
    public void processCategoryEvent(CategoryEvent e) {
        if (e.getType() == CategoryEvent.CategoryEventType.ENABLE || e.getType() == CategoryEvent.CategoryEventType.DISABLE) {
            calculateAndDisplayTotalPrice(e.getCategory().getProfile());
        }
    }

    @Override
    public void processComponentEvent(ComponentEvent e) {
        if (e.getType() == ComponentEvent.ComponentEventType.SELECT) {
            calculateAndDisplayTotalPrice(e.getComponent().getCategory().getProfile());
        }
    }

    /**
     * Updates the components' look on theme change.
     */
    private void updateTheme() {
        boolean darkMode = Application.getThemeController().isDarkMode();

        if (themeButton != null) {
            String themePath = darkMode ? "/theme_icon_dark.png" : "/theme_icon_light.png";
            themeButton.setIcon(ImageLoader.loadImageIconFromClasspath(themePath, 20, 20));
            themeButton.setName(darkMode ? "dark" : "light");
        }
    }

    @Override
    public void updateUI() {
        // gets called on theme switching
        super.updateUI();
        updateTheme();
    }
}
