package edu.advising.gui;

/**
 * GuiButton - An interactive button element that executes a command when clicked
 *
 * COMPOSITE PATTERN: This is a "Leaf" element - it cannot contain children.
 * COMMAND PATTERN: The button holds a reference to a GuiCommand and executes it.
 */
public class GuiButton extends BaseGuiElement {

    private String label;
    private GuiCommand command;
    private boolean enabled;

    public GuiButton(String id, String label) {
        super(id);
        this.label = label;
        this.command = null;
        this.enabled = true;
    }

    public GuiButton(String id, String label, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        this.label = label;
        this.command = null;
        this.enabled = true;
    }

    /**
     * Gets the button label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the button label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the command attached to this button
     */
    public GuiCommand getCommand() {
        return command;
    }

    /**
     * Sets the command to execute when this button is clicked
     * COMMAND PATTERN: Decouples the button from the action
     */
    public void setCommand(GuiCommand command) {
        this.command = command;
    }

    /**
     * Checks if this button is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether this button is enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Simulates a click on this button, executing the attached command
     */
    public void click() {
        if (enabled && command != null) {
            command.execute();
        }
    }

    @Override
    public String describe() {
        String cmdDesc = (command != null) ? command.getDescription() : "none";
        return "GuiButton[id=" + getId() + ", label=\"" + label + "\", command=" + cmdDesc + "]";
    }
}
