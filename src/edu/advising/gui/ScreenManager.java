package edu.advising.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * ScreenManager - Manages GUI screens and navigation
 *
 * Maintains a registry of screens and handles screen transitions.
 * Supports a history stack for back navigation.
 */
public class ScreenManager {

    private final Map<String, GuiScreen> screens;
    private final Stack<String> history;
    private GuiScreen currentScreen;

    public ScreenManager() {
        this.screens = new HashMap<>();
        this.history = new Stack<>();
        this.currentScreen = null;
    }

    /**
     * Registers a screen with this manager
     */
    public void registerScreen(GuiScreen screen) {
        screens.put(screen.getId(), screen);
    }

    /**
     * Unregisters a screen from this manager
     */
    public void unregisterScreen(String screenId) {
        if (currentScreen != null && currentScreen.getId().equals(screenId)) {
            throw new IllegalStateException("Cannot unregister the current screen");
        }
        screens.remove(screenId);
    }

    /**
     * Gets a registered screen by id
     */
    public GuiScreen getScreen(String screenId) {
        return screens.get(screenId);
    }

    /**
     * Gets the currently active screen
     */
    public GuiScreen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Changes to a different screen
     * @param screenId the id of the screen to change to
     * @param addToHistory whether to add the current screen to history
     */
    public void changeScreen(String screenId, boolean addToHistory) {
        GuiScreen newScreen = screens.get(screenId);
        if (newScreen == null) {
            throw new IllegalArgumentException("Screen not found: " + screenId);
        }

        if (currentScreen != null) {
            if (addToHistory) {
                history.push(currentScreen.getId());
            }
            currentScreen.onDeactivate();
        }

        currentScreen = newScreen;
        currentScreen.onActivate();
    }

    /**
     * Changes to a different screen, adding current to history
     */
    public void changeScreen(String screenId) {
        changeScreen(screenId, true);
    }

    /**
     * Goes back to the previous screen
     * @return true if successfully went back, false if no history
     */
    public boolean goBack() {
        if (history.isEmpty()) {
            return false;
        }

        String previousScreenId = history.pop();
        changeScreen(previousScreenId, false);
        return true;
    }

    /**
     * Checks if there is history to go back to
     */
    public boolean canGoBack() {
        return !history.isEmpty();
    }

    /**
     * Clears the navigation history
     */
    public void clearHistory() {
        history.clear();
    }

    /**
     * Gets the number of registered screens
     */
    public int getScreenCount() {
        return screens.size();
    }
}
