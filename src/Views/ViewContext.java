package Views;

// ViewContext.java
import java.util.ArrayDeque;
import java.util.Deque;

public class ViewContext {

    private ViewState currentState;
    private Deque<ViewState> history = new ArrayDeque<>();
    private String currentUser = null;
    private String currentRole = null;

    public void start() {
        currentState = GuestViewState.getInstance();
        currentState.enter();
        currentState.render();
    }

    public void navigateTo(ViewState newState) {
        if (newState.requiresAuthentication() && currentUser == null) {
            System.out.println("Access denied. Redirecting to login.");
            navigateTo(GuestViewState.getInstance());
            return;
        }
        currentState.exit();
        history.push(currentState);
        currentState = newState;
        currentState.enter();
        currentState.render();
    }

    public void back() {
        if (history.isEmpty()) {
            System.out.println("Nowhere to go back to.");
            return;
        }
        currentState.exit();
        currentState = history.pop();
        currentState.enter();
        currentState.render();
    }

    public void logout() {
        currentUser = null;
        currentRole = null;
        history.clear();
        currentState.exit();
        currentState = GuestViewState.getInstance();
        currentState.enter();
        currentState.render();
    }

    public void handleAction(String action, String... args) {
        currentState.handleAction(this, action, args);
    }

    public void setCurrentUser(String username, String role) {
        this.currentUser = username;
        this.currentRole = role;
    }

    public String getCurrentRole() { return currentRole; }
    public String getCurrentUser() { return currentUser; }
}