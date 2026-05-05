package edu.advising.gui;

/**
 * BaseGuiElement - Abstract base implementation of GuiElement
 *
 * Provides common functionality for all GUI elements.
 */
public abstract class BaseGuiElement implements GuiElement {

    private final String id;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;

    protected BaseGuiElement(String id) {
        this(id, 0, 0, 0, 0);
    }

    protected BaseGuiElement(String id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
