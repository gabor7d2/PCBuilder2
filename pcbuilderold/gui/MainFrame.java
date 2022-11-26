package net.gabor7d2.pcbuilderold.gui;

import net.gabor7d2.pcbuilderold.persistence.PersistenceManager;
import net.gabor7d2.pcbuilderold.persistence.Profile;
import net.gabor7d2.pcbuilderold.persistence.ProfileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

// TODO make a details pane for components that will be shown when the user clicks a component
// TODO add profile and component editing capabilities
// TODO add error dialogs for Xml parse errors

// TODO document classes

// TODO implement shops to choose from and separate section for displaying the price of each category
public class MainFrame extends JFrame implements KeyEventDispatcher {

    private final ComparisonPane comparisonPane;

    private AtomicBoolean loading = new AtomicBoolean(true);

    public MainFrame() {
        super();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

        System.out.println(PersistenceManager.getSettings().getSelectedProfile());

        for (Profile p : ProfileManager.getProfiles()) {
            System.out.println(p.getName());
        }

        ProgressDialog progressDialog = new ProgressDialog(this, ProgressDialogType.STARTUP);
        progressDialog.setVisible(loading);

        comparisonPane = new ComparisonPane(WIDTH, HEIGHT, this);
        ComponentManager.autoLoad(progressDialog);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        /*if (loading.get() || welcomeDialog.get()) return false;
        if (e.getID() != KeyEvent.KEY_PRESSED) return false;

        if (ProfileManager.getInstance() == null) return false;
        if (ProfileManager.getInstance().getOpenDialogCount() != 0) return false;
        if (e.isMetaDown() && e.getKeyCode() == KeyEvent.VK_H) return false;

        // Access basic functions with keyboard shortcuts
        switch (e.getKeyCode()) {
            case KeyEvent.VK_H:
                VersionManager.showHelpDialog(this);
                return true;
            case KeyEvent.VK_R:
                comparisonPane.reloadEverything();
                return true;
            case KeyEvent.VK_N:
                comparisonPane.getProfileManager().renameProfile();
                return true;
            case KeyEvent.VK_T:
            case KeyEvent.VK_PLUS:
            case KeyEvent.VK_ADD:
            case KeyEvent.VK_INSERT:
            case KeyEvent.VK_HELP:
                comparisonPane.getProfileManager().addProfile();
                return true;
            case KeyEvent.VK_W:
            case KeyEvent.VK_MINUS:
            case KeyEvent.VK_SUBTRACT:
            case KeyEvent.VK_DELETE:
                comparisonPane.getProfileManager().removeProfile();
                return true;
            case KeyEvent.VK_1:
                comparisonPane.getProfileManager().selectProfile(0);
                return true;
            case KeyEvent.VK_2:
                comparisonPane.getProfileManager().selectProfile(1);
                return true;
            case KeyEvent.VK_3:
                comparisonPane.getProfileManager().selectProfile(2);
                return true;
            case KeyEvent.VK_4:
                comparisonPane.getProfileManager().selectProfile(3);
                return true;
            case KeyEvent.VK_5:
                comparisonPane.getProfileManager().selectProfile(4);
                return true;
            case KeyEvent.VK_6:
                comparisonPane.getProfileManager().selectProfile(5);
                return true;
            case KeyEvent.VK_7:
                comparisonPane.getProfileManager().selectProfile(6);
                return true;
            case KeyEvent.VK_8:
                comparisonPane.getProfileManager().selectProfile(7);
                return true;
            case KeyEvent.VK_9:
                comparisonPane.getProfileManager().selectProfile(8);
                return true;
        }

        return false;
    }
}