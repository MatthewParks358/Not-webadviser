package edu.advising.gui;

/**
 * GuiElement - Base interface for all GUI elements (Component in Composite pattern)
 *
 * Week 6: COMPOSITE PATTERN + ITERATOR PATTERN
 * Features Implemented: Reusable GUI structure system
 * Why Now: Need hierarchical GUI elements that can be composed and iterated
 */
public interface GuiElement {

    /**
     * Gets the unique identifier for this element
     */
    String getId();

    /**
     * Gets the x position of this element
     */
    int getX();

    /**
     * Gets the y position of this element
     */
    int getY();

    /**
     * Gets the width of this element
     */
    int getWidth();

    /**
     * Gets the height of this element
     */
    int getHeight();

    /**
     * Sets the position of this element
     */
    void setPosition(int x, int y);

    /**
     * Sets the size of this element
     */
    void setSize(int width, int height);

    /**
     * Checks if this element is visible
     */
    boolean isVisible();

    /**
     * Sets the visibility of this element
     */
    void setVisible(boolean visible);

    /**
     * Checks if the given point is within this element's bounds
     */
    default boolean containsPoint(int px, int py) {
        return px >= getX() && px < getX() + getWidth()
            && py >= getY() && py < getY() + getHeight();
    }

    /**
     * Called to update the element (for animations, etc.)
     */
    default void update() {
        // Default: no-op
    }

    /**
     * Gets a description of this element for debugging/logging
     */
    default String describe() {
        return getClass().getSimpleName() + "[id=" + getId() + ", pos=(" + getX() + "," + getY() + ")]";
    }
}
