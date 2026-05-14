package Views;

public interface ViewState {
    void enter();
    void exit();
    void render();
    void handleAction(ViewContext ctx, String action, String... args);
    String getViewName();
    boolean requiresAuthentication();
}