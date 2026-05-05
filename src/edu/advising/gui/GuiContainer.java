package edu.advising.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * GuiContainer - Composite element that can contain child GUI elements
 *
 * COMPOSITE PATTERN: This class is the "Composite" - it holds children
 * and implements the same interface as leaf elements.
 *
 * ITERATOR PATTERN: Implements Iterable to allow traversal of children.
 * Provides both shallow iteration (direct children only) and deep iteration
 * (all descendants recursively).
 */
public class GuiContainer extends BaseGuiElement implements Iterable<GuiElement> {

    private final List<GuiElement> children;

    public GuiContainer(String id) {
        super(id);
        this.children = new ArrayList<>();
    }

    public GuiContainer(String id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        this.children = new ArrayList<>();
    }

    /**
     * Adds a child element to this container
     */
    public void addChild(GuiElement element) {
        children.add(element);
    }

    /**
     * Removes a child element from this container
     */
    public boolean removeChild(GuiElement element) {
        return children.remove(element);
    }

    /**
     * Removes a child element by id
     */
    public boolean removeChild(String id) {
        return children.removeIf(e -> e.getId().equals(id));
    }

    /**
     * Gets the number of direct children
     */
    public int getChildCount() {
        return children.size();
    }

    /**
     * Gets a child by index
     */
    public GuiElement getChild(int index) {
        return children.get(index);
    }

    /**
     * Finds a child by id (searches only direct children)
     */
    public GuiElement findChild(String id) {
        for (GuiElement child : children) {
            if (child.getId().equals(id)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Finds an element by id recursively (searches all descendants)
     */
    public GuiElement findDescendant(String id) {
        for (GuiElement child : children) {
            if (child.getId().equals(id)) {
                return child;
            }
            if (child instanceof GuiContainer container) {
                GuiElement found = container.findDescendant(id);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    /**
     * Standard iterator - iterates over direct children only (shallow)
     * ITERATOR PATTERN: Returns an iterator for direct children
     */
    @Override
    public Iterator<GuiElement> iterator() {
        return children.iterator();
    }

    /**
     * Deep iterator - iterates over all descendants recursively
     * ITERATOR PATTERN: Returns an iterator that traverses the entire tree
     */
    public Iterator<GuiElement> deepIterator() {
        return new DeepIterator(this);
    }

    /**
     * Returns an iterable for deep traversal
     */
    public Iterable<GuiElement> allDescendants() {
        return () -> deepIterator();
    }

    @Override
    public void update() {
        for (GuiElement child : children) {
            child.update();
        }
    }

    /**
     * DeepIterator - Iterates through all descendants recursively (pre-order)
     * ITERATOR PATTERN: Concrete iterator for tree traversal
     */
    private static class DeepIterator implements Iterator<GuiElement> {
        private final List<GuiElement> flatList;
        private int currentIndex;

        DeepIterator(GuiContainer root) {
            this.flatList = new ArrayList<>();
            this.currentIndex = 0;
            flatten(root);
        }

        private void flatten(GuiElement element) {
            flatList.add(element);
            if (element instanceof GuiContainer container) {
                for (GuiElement child : container.children) {
                    flatten(child);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < flatList.size();
        }

        @Override
        public GuiElement next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return flatList.get(currentIndex++);
        }
    }
}
