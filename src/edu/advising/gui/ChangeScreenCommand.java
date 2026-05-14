package edu.advising.gui;

/**
 * ChangeScreenCommand - Command to change the active screen
 *
 * COMMAND PATTERN: Concrete command that encapsulates screen navigation.
 * Can be attached to buttons or other interactive elements.
 */
public class ChangeScreenCommand implements GuiCommand {

    private final ScreenManager screenManager;
    private final String targetScreenId;
    private String previousScreenId;

    public ChangeScreenCommand(ScreenManager screenManager, String targetScreenId) {
        this.screenManager = screenManager;
        this.targetScreenId = targetScreenId;
        this.previousScreenId = null;
    }

    @Override
    public void execute() {
        GuiScreen current = screenManager.getCurrentScreen();
        previousScreenId = (current != null) ? current.getId() : null;
        screenManager.changeScreen(targetScreenId);
    }

    @Override
    public String getDescription() {
        return "Change screen to: " + targetScreenId;
    }

    @Override
    public boolean canUndo() {
        return previousScreenId != null;
    }

    @Override
    public void undo() {
        if (previousScreenId == null) {
            throw new IllegalStateException("Cannot undo: no previous screen recorded");
        }
        screenManager.changeScreen(previousScreenId, false);
    }
}
