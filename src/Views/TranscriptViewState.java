package Views;

public class TranscriptViewState implements ViewState {

    private static TranscriptViewState instance;

    private TranscriptViewState() {}

    public static TranscriptViewState getInstance() {
        if (instance == null) instance = new TranscriptViewState();
        return instance;
    }

    @Override
    public void enter() { System.out.println("Entering Transcript View"); }

    @Override
    public void exit() { System.out.println("Leaving Transcript View"); }

    @Override
    public void render() {
        System.out.println("=== Student Transcript ===");
        System.out.println("Actions: LOGOUT");
    }

    @Override
    public void handleAction(ViewContext ctx, String action, String... args) {
        if (action.equals("LOGOUT")) {
            ctx.logout();
        } else {
            System.out.println("Unknown action: " + action);
        }
    }

    @Override
    public String getViewName() { return "TRANSCRIPT"; }

    @Override
    public boolean requiresAuthentication() { return true; }
}