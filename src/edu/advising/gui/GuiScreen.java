package edu.advising.gui;

/**
 * GuiScreen - A specialized container representing a full screen
 *
 * COMPOSITE PATTERN: GuiScreen is a Composite that typically serves as
 * the root of a GUI hierarchy for a particular screen/view.
 */
public class GuiScreen extends GuiContainer {

    private final String title;
    private boolean active;

    public GuiScreen(String id, String title) {
        super(id);
        this.title = title;
        this.active = false;
    }

    public GuiScreen(String id, String title, int width, int height) {
        super(id, 0, 0, width, height);
        this.title = title;
        this.active = false;
    }

    /**
     * Gets the title of this screen
     */
    public String getTitle() {
        return title;
    }

    /**
     * Checks if this screen is currently active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Called when this screen becomes active
     */
    public void onActivate() {
        this.active = true;
    }

    /**
     * Called when this screen becomes inactive
     */
    public void onDeactivate() {
        this.active = false;
    }

    @Override
    public String describe() {
        return "GuiScreen[id=" + getId() + ", title=\"" + title + "\", active=" + active + "]";
    }
}
