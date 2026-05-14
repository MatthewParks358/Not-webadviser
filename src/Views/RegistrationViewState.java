package Views;

public class RegistrationViewState implements ViewState {

    private static RegistrationViewState instance;

    private RegistrationViewState() {}

    public static RegistrationViewState getInstance() {
        if (instance == null) instance = new RegistrationViewState();
        return instance;
    }

    @Override
    public void enter() { System.out.println("Entering Registration View"); }

    @Override
    public void exit() { System.out.println("Leaving Registration View"); }

    @Override
    public void render() {
        System.out.println("=== Course Registration ===");
        System.out.println("Actions: CHECK_STATUS, LOGOUT");
    }

    @Override
    public void handleAction(ViewContext ctx, String action, String... args) {
        if (action.equals("CHECK_STATUS")) {
            String semester = args[0];
            String year = args[1];
            System.out.println("Checking registration status for " + semester + " " + year);
        } else if (action.equals("LOGOUT")) {
            ctx.logout();
        } else {
            System.out.println("Unknown action: " + action);
        }
    }

    @Override
    public String getViewName() { return "REGISTRATION"; }

    @Override
    public boolean requiresAuthentication() { return true; }
}