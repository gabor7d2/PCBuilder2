package net.gabor7d2.pcbuilder.gui.general;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for grouping a set of buttons whose selection is exclusive,
 * meaning that selecting a button in the group will deselect all other
 * buttons. Behaves the same way as {@link ButtonGroup}, except that
 * you can add {@link ButtonGroupListener ButtonGroupListeners} which
 * will fire when any button has been selected, telling you which was
 * selected.
 */
public class SmartButtonGroup extends javax.swing.ButtonGroup {

    /**
     * Whether to automatically select the first
     * button that gets added to the group.
     */
    private final boolean selectFirst;

    /**
     * List of buttons added to the group.
     */
    private final List<AbstractButton> buttons = new ArrayList<>();

    /**
     * List of listeners added to the group.
     */
    private final List<ButtonGroupListener> listeners = new ArrayList<>();

    /**
     * Creates a new SmartButtonGroup.
     */
    public SmartButtonGroup() {
        selectFirst = false;
    }

    /**
     * Creates a new SmartButtonGroup.
     *
     * @param selectFirstButton Set to true to automatically select the
     *                          first button that gets added to the group.
     */
    public SmartButtonGroup(boolean selectFirstButton) {
        selectFirst = selectFirstButton;
    }

    /**
     * Adds a button to the group. If it is the first button and
     * <code>selectFirstButton</code> was set in the constructor,
     * selects the button.
     *
     * @param button The button to be added.
     */
    @Override
    public void add(AbstractButton button) {
        if (button == null) return;
        super.add(button);
        buttons.add(button);

        if (buttons.indexOf(button) == 0 && selectFirst) {
            button.setSelected(true);
        }
    }

    /**
     * Removes a button from the group.
     *
     * @param button The button to be removed.
     */
    @Override
    public void remove(AbstractButton button) {
        if (!buttons.contains(button)) return;
        super.remove(button);
        buttons.remove(button);
    }

    /**
     * Gets selected button in group. Returns null if
     * no button is selected.
     */
    public AbstractButton getSelected() {
        for (AbstractButton button : buttons) {
            if (button.isSelected()) return button;
        }
        return null;
    }

    /**
     * Gets index of selected button in group. Returns
     * -1 if no button is selected.
     */
    public int getSelectedIndex() {
        AbstractButton button = getSelected();
        if (button == null) return -1;
        else return buttons.indexOf(button);
    }

    /**
     * Sets which button should be selected. All other
     * buttons will be deselected.
     *
     * @param index The index of the button to be selected.
     */
    public void setSelectedIndex(int index) {
        if (index < 0 || index >= buttons.size()) return;

        AbstractButton button = buttons.get(index);
        button.setSelected(true);
    }

    /**
     * Sets the specified button to selected, deselecting all other buttons.
     * <p>
     * Also notifies all added listeners.
     *
     * @param m the <code>ButtonModel</code>
     * @param b <code>true</code> if this button is to be
     *          selected, otherwise <code>false</code>
     */
    @Override
    public void setSelected(ButtonModel m, boolean b) {
        super.setSelected(m, b);

        AbstractButton currentlySelected = getSelected();
        if (currentlySelected == null) return;

        for (ButtonGroupListener l : listeners) {
            l.buttonSelected(currentlySelected, buttons.indexOf(currentlySelected));
        }
    }

    /**
     * Adds a button group listener.
     *
     * @param listener The listener to add.
     */
    public void addButtonGroupListener(ButtonGroupListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a button group listener.
     *
     * @param listener The listener to remove.
     */
    public void removeButtonGroupListener(ButtonGroupListener listener) {
        listeners.remove(listener);
    }

    public interface ButtonGroupListener {
        /**
         * Called when a button is selected from the {@link SmartButtonGroup}
         * this listener is registered on.
         *
         * @param button The button component.
         * @param index  The index of the button in the button group.
         */
        void buttonSelected(AbstractButton button, int index);
    }
}
