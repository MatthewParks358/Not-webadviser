package edu.advising.gui;

/**
 * GuiLabel - A simple text label element
 *
 * COMPOSITE PATTERN: This is a "Leaf" element - it cannot contain children.
 */
public class GuiLabel extends BaseGuiElement {

    private String text;

    public GuiLabel(String id, String text) {
        super(id);
        this.text = text;
    }

    public GuiLabel(String id, String text, int x, int y) {
        super(id, x, y, 0, 0);
        this.text = text;
    }

    /**
     * Gets the text content of this label
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text content of this label
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String describe() {
        return "GuiLabel[id=" + getId() + ", text=\"" + text + "\"]";
    }
}
