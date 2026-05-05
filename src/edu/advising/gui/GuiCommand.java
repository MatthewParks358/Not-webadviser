package edu.advising.gui;

/**
 * GuiCommand - Command interface for GUI actions
 *
 * COMMAND PATTERN: Encapsulates a GUI action as an object, allowing
 * actions to be parameterized, queued, and undone.
 *
 * This is a simplified command interface specifically for GUI actions,
 * separate from the domain Command interface in edu.advising.commands.
 */
public interface GuiCommand {

    /**
     * Executes this command
     */
    void execute();

    /**
     * Gets a description of this command
     */
    String getDescription();

    /**
     * Checks if this command can be undone
     */
    default boolean canUndo() {
        return false;
    }

    /**
     * Undoes this command (if supported)
     */
    default void undo() {
        throw new UnsupportedOperationException("This command cannot be undone");
    }
}
